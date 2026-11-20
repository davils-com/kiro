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

package com.davils.kiro.ui.components.breadcrumb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.layout.mirroredInRtl
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun Breadcrumb(
    modifier: Modifier = Modifier,
    style: BreadcrumbStyle = breadcrumbStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    CompositionLocalProvider(LocalTextStyle provides style.textStyle, LocalContentColor provides style.color) {
        FlowRow(
            modifier = modifier.semantics { isTraversalGroup = true },
            horizontalArrangement = Arrangement.spacedBy(style.gap),
            verticalArrangement = Arrangement.Center,
            itemVerticalAlignment = Alignment.CenterVertically,
            content = { content() },
        )
    }
}

@Composable
public fun BreadcrumbItem(
    modifier: Modifier = Modifier,
    style: BreadcrumbStyle = breadcrumbStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(style.itemGap),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
public fun BreadcrumbLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: BreadcrumbStyle = breadcrumbStyle(),
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val color = animateColor(
        targetValue = linkColorOf(isHovered = isHovered, style = style),
        animationSpec = theme.colorTransition(),
    )
    Text(
        text = text,
        modifier = modifier
            .pressableCursor()
            .hoverable(interactionSource = source)
            .clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onClick),
        color = color.value,
    )
}

@Composable
public fun BreadcrumbPage(text: String, modifier: Modifier = Modifier, style: BreadcrumbStyle = breadcrumbStyle()) {
    Text(text = text, modifier = modifier.semantics { disabled() }, color = style.pageColor)
}

@Composable
public fun BreadcrumbSeparator(modifier: Modifier = Modifier, style: BreadcrumbStyle = breadcrumbStyle()) {
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.separatorSize)) {
        Icon(
            icon = style.separatorIcon,
            contentDescription = null,
            modifier = modifier.mirroredInRtl().semantics { hideFromAccessibility() },
        )
    }
}

@Composable
public fun BreadcrumbEllipsis(
    contentDescription: String,
    modifier: Modifier = Modifier,
    style: BreadcrumbStyle = breadcrumbStyle(),
    menu: (MenuScope.() -> Unit)? = null,
) {
    val described = modifier.semantics { this.contentDescription = contentDescription }
    if (menu == null) {
        EllipsisIcon(modifier = described, style = style)
        return
    }
    var isOpen by remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isOpen,
        onExpandedChange = { open -> isOpen = open },
        trigger = { EllipsisButton(modifier = described, style = style) { isOpen = !isOpen } },
        placement = Placement(side = Side.Bottom, align = Align.Start),
        content = menu,
    )
}

@Composable
private fun EllipsisButton(modifier: Modifier, style: BreadcrumbStyle, onClick: () -> Unit) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val container = animateColor(
        targetValue = style.ellipsisHover.takeIf { isHovered } ?: Color.Transparent,
        animationSpec = theme.colorTransition(),
    )
    val press = Modifier
        .pressableCursor()
        .clickable(interactionSource = source, indication = null, role = Role.Button, onClick = onClick)
    EllipsisIcon(
        modifier = modifier
            .surface(shape = style.ellipsisShape, color = container.value)
            .hoverable(interactionSource = source)
            .then(press),
        style = style,
    )
}

@Composable
private fun EllipsisIcon(modifier: Modifier, style: BreadcrumbStyle) {
    Box(modifier = modifier.size(style.ellipsisBox), contentAlignment = Alignment.Center) {
        CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.ellipsisSize)) {
            Icon(icon = style.ellipsisIcon, contentDescription = null)
        }
    }
}

private fun linkColorOf(isHovered: Boolean, style: BreadcrumbStyle): Color {
    if (isHovered) return style.hoverColor
    return style.color
}
