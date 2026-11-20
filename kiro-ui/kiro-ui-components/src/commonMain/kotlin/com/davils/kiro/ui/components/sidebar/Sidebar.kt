/*
 * Copyright 2026 Davils
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.davils.kiro.ui.components.sidebar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.layout.mirroredInRtl
import com.davils.kiro.ui.components.primitive.motion.animatedWidth
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border

@Composable
public fun SidebarProvider(
    state: SidebarState,
    modifier: Modifier = Modifier,
    variant: SidebarVariant = SidebarVariant.Sidebar,
    isShortcutEnabled: Boolean = true,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    val shortcut = Modifier.onPreviewKeyEvent { event ->
        val isModified = event.isCtrlPressed || event.isMetaPressed
        val isToggle = isShortcutEnabled && isModified && event.type == KeyEventType.KeyDown && event.key == Key.B
        if (isToggle) state.toggle()
        isToggle
    }
    val backdrop = style.container.takeIf { variant == SidebarVariant.Inset } ?: style.background
    CompositionLocalProvider(LocalSidebar provides SidebarContext(state = state, variant = variant)) {
        Row(modifier = modifier.fillMaxSize().background(backdrop).then(shortcut), content = content)
    }
}

@Composable
public fun Sidebar(
    modifier: Modifier = Modifier,
    side: Side = Side.Start,
    collapsible: SidebarCollapsible = SidebarCollapsible.Offcanvas,
    isRailShown: Boolean = false,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = LocalSidebar.current
    val isOpen = context.state.isOpen || collapsible == SidebarCollapsible.None
    val isPadded = context.variant != SidebarVariant.Sidebar
    val iconWidth = style.iconWidth + (style.floatingPadding * 2).takeIf { isPadded }.orZero()
    val target = targetWidthOf(isOpen = isOpen, collapsible = collapsible, full = style.width, icon = iconWidth)
    val width = animateDpAsState(targetValue = target, animationSpec = style.widthAnimation)
    val isIconCollapsed = !isOpen && collapsible == SidebarCollapsible.Icon
    val iconPanel = Modifier.animatedWidth(width)
    val panelWidth = iconPanel.takeIf { collapsible == SidebarCollapsible.Icon } ?: Modifier.width(style.width)
    val alignment = Alignment.End.takeIf { side == Side.Start } ?: Alignment.Start
    CompositionLocalProvider(
        LocalIconCollapsed provides isIconCollapsed,
        LocalContentColor provides style.content,
        LocalTextStyle provides style.textStyle,
    ) {
        Box(modifier = modifier.animatedWidth(width).fillMaxHeight()) {
            Box(modifier = Modifier.matchParentSize().clipToBounds()) {
                val unbounded = Modifier.wrapContentWidth(align = alignment, unbounded = true)
                Box(modifier = unbounded.then(panelWidth).fillMaxHeight()) {
                    SidebarPanel(variant = context.variant, side = side, style = style, content = content)
                }
            }
            if (isRailShown) SidebarRail(side = side, style = style, onToggle = context.state::toggle)
        }
    }
}

@Composable
private fun SidebarPanel(
    variant: SidebarVariant,
    side: Side,
    style: SidebarStyle,
    content: @Composable ColumnScope.() -> Unit,
) {
    val isFloating = variant == SidebarVariant.Floating
    val isPlain = variant == SidebarVariant.Sidebar
    val outer = Modifier.padding(style.floatingPadding).takeUnless { isPlain } ?: Modifier
    val face = floatingFaceOf(isFloating = isFloating, style = style)
    val edge = edgeOf(isShown = isPlain, side = side, color = style.border)
    val panel = Modifier
        .fillMaxSize()
        .then(outer)
        .then(face)
        .then(edge)
    Column(modifier = panel, content = content)
}

private fun floatingFaceOf(isFloating: Boolean, style: SidebarStyle): Modifier {
    if (!isFloating) return Modifier.background(style.container)
    return Modifier
        .boxShadow(shape = style.floatingShape, shadow = style.shadow)
        .surface(
            shape = style.floatingShape,
            color = style.container,
            ring = Border(width = 1.dp, color = style.border),
        )
}

private fun Modifier.edgeLine(side: Side, color: Color): Modifier = drawBehind {
    val stroke = 1.dp.toPx()
    val isLeading = side == Side.Start
    val x = (size.width - stroke / 2).takeIf { isLeading } ?: stroke / 2
    drawLine(color = color, start = Offset(x, 0f), end = Offset(x, size.height), strokeWidth = stroke)
}

@Composable
private fun BoxScope.SidebarRail(side: Side, style: SidebarStyle, onToggle: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val isLeading = side == Side.Start
    val label = style.toggleLabel
    val alignment = Alignment.CenterEnd.takeIf { isLeading } ?: Alignment.CenterStart
    val shift = (style.railWidth / 2).takeIf { isLeading } ?: -(style.railWidth / 2)
    Box(
        modifier = Modifier
            .align(alignment)
            .offset(x = shift)
            .fillMaxHeight()
            .requiredWidth(style.railWidth)
            .then(railLine(color = style.border.takeIf { isHovered } ?: Color.Transparent))
            .hoverable(interactionSource = source)
            .pressableCursor()
            .clickable(interactionSource = source, indication = null, onClick = onToggle)
            .semantics { contentDescription = label },
    )
}

@Composable
public fun SidebarTrigger(modifier: Modifier = Modifier, style: SidebarStyle = sidebarStyle()) {
    val state = LocalSidebar.current.state
    val label = style.toggleLabel
    Button(
        onClick = state::toggle,
        modifier = modifier.semantics { contentDescription = label },
        variant = ButtonVariant.Ghost,
        size = ButtonSize.IconSm,
    ) {
        val inline = this
        Box(
            modifier = Modifier.mirroredInRtl(),
        ) { inline.Icon(icon = style.triggerIcon, placement = IconPlacement.Inline) }
    }
}

@Composable
public fun RowScope.SidebarInset(
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val isInset = LocalSidebar.current.variant == SidebarVariant.Inset
    val face = insetFaceOf(isInset = isInset, style = style)
    Column(modifier = modifier.weight(1f).fillMaxHeight().then(face), content = content)
}

private fun insetFaceOf(isInset: Boolean, style: SidebarStyle): Modifier {
    if (!isInset) return Modifier.background(style.background)
    return Modifier
        .padding(style.floatingPadding)
        .boxShadow(shape = style.insetShape, shadow = style.shadow)
        .surface(shape = style.insetShape, color = style.background)
}

private fun railLine(color: Color): Modifier = Modifier.drawBehind {
    drawLine(
        color = color,
        start = Offset(size.width / 2, 0f),
        end = Offset(size.width / 2, size.height),
        strokeWidth = 2.dp.toPx(),
    )
}

private fun edgeOf(isShown: Boolean, side: Side, color: Color): Modifier {
    if (!isShown) return Modifier
    return Modifier.edgeLine(side = side, color = color)
}

private fun targetWidthOf(isOpen: Boolean, collapsible: SidebarCollapsible, full: Dp, icon: Dp): Dp {
    if (isOpen) return full
    return icon.takeIf { collapsible == SidebarCollapsible.Icon } ?: 0.dp
}

private fun Dp?.orZero(): Dp = this ?: 0.dp
