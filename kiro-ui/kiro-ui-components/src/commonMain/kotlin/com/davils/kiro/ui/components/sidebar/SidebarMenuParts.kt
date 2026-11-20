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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.InlineScope
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.layout.InlineScopeImpl
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.skeleton.Skeleton
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.tooltip.Tooltip
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.state.InteractionState
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private const val SKELETON_FRACTION: Float = 0.7f
private val ACTION_TOP = 6.dp
private val ACTION_END = 4.dp
private val RING_WIDTH = 2.dp

private val heights: Map<SidebarMenuButtonSize, Dp> = mapOf(
    SidebarMenuButtonSize.Sm to 28.dp,
    SidebarMenuButtonSize.Default to 32.dp,
    SidebarMenuButtonSize.Lg to 48.dp,
)

/**
 * A button of a [SidebarMenuItem] with icons and a label, which shows the accent color while it is hovered, pressed
 * or active.
 *
 * While the sidebar is collapsed to its icons the button shrinks to a square and shows [tooltip] at its end when it
 * is hovered.
 *
 * @param onClick called when the button is clicked.
 * @param modifier the modifier of the button.
 * @param isActive whether the button stands for the current page; an active button is accented and its label is
 * medium weight.
 * @param isOutlined whether the button has a border and the background color of the style.
 * @param size the size of the button.
 * @param tooltip the text shown next to the button while the sidebar is collapsed to its icons, or `null` for none.
 * @param style the style of the sidebar, by default the style of the theme.
 * @param content the content of the button, such as an icon and a label.
 * @since 1.0.0
 */
@Composable
public fun SidebarMenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    isOutlined: Boolean = false,
    size: SidebarMenuButtonSize = SidebarMenuButtonSize.Default,
    tooltip: String? = null,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable InlineScope.() -> Unit,
) {
    val isCollapsed = LocalIconCollapsed.current
    val height = heights.getValue(size)
    val text = style.smallTextStyle.takeIf { size == SidebarMenuButtonSize.Sm } ?: style.textStyle
    val button: @Composable () -> Unit = {
        SidebarButtonRow(
            onClick = onClick,
            modifier = modifier.then(collapsedSize(isCollapsed = isCollapsed, height = height)),
            isActive = isActive,
            isOutlined = isOutlined,
            height = height,
            textStyle = text,
            style = style,
            content = content,
        )
    }
    val hint = tooltip?.takeIf { isCollapsed }
    if (hint == null) {
        button()
        return
    }
    Tooltip(tooltip = { Text(hint) }, placement = Placement(side = Side.End), trigger = button)
}

/**
 * An icon button in the top end corner of a [SidebarMenuItem], hidden while the sidebar is collapsed to its icons.
 *
 * @param onClick called when the action is clicked.
 * @param icon the icon of the action.
 * @param contentDescription the text that announces the action.
 * @param modifier the modifier of the action.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun BoxScope.SidebarMenuAction(
    onClick: () -> Unit,
    icon: IconGraphic,
    contentDescription: String,
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
) {
    if (LocalIconCollapsed.current) return
    Box(modifier = Modifier.align(Alignment.TopEnd).padding(top = ACTION_TOP, end = ACTION_END)) {
        SidebarGroupAction(
            onClick = onClick,
            icon = icon,
            contentDescription = contentDescription,
            modifier = modifier,
            style = style,
        )
    }
}

/**
 * A small icon button, such as the action of a [SidebarGroup], which shows the accent color while it is hovered.
 *
 * @param onClick called when the action is clicked.
 * @param icon the icon of the action.
 * @param contentDescription the text that announces the action.
 * @param modifier the modifier of the action.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun SidebarGroupAction(
    onClick: () -> Unit,
    icon: IconGraphic,
    contentDescription: String,
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
) {
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState()
    val container = style.accent.takeIf { state.isHovered } ?: Color.Transparent
    Box(
        modifier = modifier.then(
            Modifier
                .size(style.actionSize)
                .surface(shape = style.buttonShape, color = container)
                .hoverable(interactionSource = source)
                .then(pressable(source = source, onClick = onClick))
                .semantics { this.contentDescription = contentDescription },
        ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize)) {
            Icon(icon = icon, contentDescription = null)
        }
    }
}

/**
 * A short text, such as a count, in the top end corner of a [SidebarMenuItem], hidden while the sidebar is collapsed
 * to its icons.
 *
 * @param text the text of the badge.
 * @param modifier the modifier of the badge.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun BoxScope.SidebarMenuBadge(
    text: String,
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
) {
    if (LocalIconCollapsed.current) return
    Box(
        modifier = modifier.then(
            Modifier
                .align(Alignment.TopEnd)
                .padding(top = ACTION_TOP, end = ACTION_END)
                .height(style.actionSize)
                .widthIn(min = style.actionSize)
                .padding(horizontal = ACTION_END),
        ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = style.badgeStyle)
    }
}

/**
 * A placeholder in the shape of a [SidebarMenuButton], shown while the items of a menu load.
 *
 * @param modifier the modifier of the placeholder.
 * @param isIconShown whether the placeholder has a square for an icon in front of its label.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun SidebarMenuSkeleton(
    modifier: Modifier = Modifier,
    isIconShown: Boolean = false,
    style: SidebarStyle = sidebarStyle(),
) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(skeletonHeight)
                .padding(horizontal = style.buttonPadding),
        ),
        horizontalArrangement = Arrangement.spacedBy(style.buttonGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isIconShown) Skeleton(modifier = Modifier.size(style.iconSize))
        Skeleton(modifier = Modifier.height(style.iconSize).fillMaxWidth(SKELETON_FRACTION))
    }
}

@Composable
internal fun SidebarButtonRow(
    onClick: () -> Unit,
    modifier: Modifier,
    isActive: Boolean,
    isOutlined: Boolean,
    height: Dp,
    textStyle: TextStyle,
    style: SidebarStyle,
    content: @Composable InlineScope.() -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState(isSelected = isActive)
    val isLit = state.isHovered || state.isPressed || isActive
    val ring = ringOf(state = state, isOutlined = isOutlined, isLit = isLit, style = style)
    val container = containerOf(isLit = isLit, isOutlined = isOutlined, style = style)
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(height)
                .surface(shape = style.buttonShape, color = container, ring = ring)
                .clipToBounds()
                .semantics { selected = isActive }
                .hoverable(interactionSource = source)
                .then(pressable(source = source, onClick = onClick))
                .padding(horizontal = style.buttonPadding),
        ),
        horizontalArrangement = Arrangement.spacedBy(style.buttonGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides (style.accentContent.takeIf { isLit } ?: style.content),
            LocalTextStyle provides textStyle.copy(
                fontWeight = FontWeight.Medium.takeIf { isActive } ?: textStyle.fontWeight,
            ),
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            InlineScopeImpl(rowScope = this, edgeInset = 0.dp).content()
        }
    }
}

private val skeletonHeight: Dp = 32.dp

internal fun startLine(color: Color): Modifier = Modifier.drawBehind {
    val stroke = 1.dp.toPx()
    drawLine(
        color = color,
        start = Offset(x = stroke / 2, y = 0f),
        end = Offset(x = stroke / 2, y = size.height),
        strokeWidth = stroke,
    )
}

private fun collapsedSize(isCollapsed: Boolean, height: Dp): Modifier {
    if (!isCollapsed) return Modifier
    return Modifier.size(height)
}

private fun containerOf(isLit: Boolean, isOutlined: Boolean, style: SidebarStyle): Color {
    if (isLit) return style.accent
    return style.background.takeIf { isOutlined } ?: Color.Transparent
}

private fun ringOf(state: InteractionState, isOutlined: Boolean, isLit: Boolean, style: SidebarStyle): Border? {
    if (state.isFocusVisible) return Border(width = RING_WIDTH, color = style.ring)
    return Border(width = 1.dp, color = style.accent.takeIf { isLit } ?: style.border).takeIf { isOutlined }
}

private fun pressable(source: MutableInteractionSource, onClick: () -> Unit): Modifier = Modifier
    .pressableCursor()
    .clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onClick)
