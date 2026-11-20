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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.input.Input
import com.davils.kiro.ui.components.input.inputStyle
import com.davils.kiro.ui.components.primitive.motion.animatedHeight
import com.davils.kiro.ui.components.primitive.motion.fadeBy
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.scrollarea.ScrollArea
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.state.StateValue

private val ACTION_TOP = 14.dp
private val ACTION_END = 12.dp

/**
 * The header of a [Sidebar], a column at its top for content such as a [SidebarBrand] or a [SidebarInput].
 *
 * @param modifier the modifier of the header.
 * @param style the style of the sidebar, by default the style of the theme.
 * @param content the content of the header.
 * @since 1.0.0
 */
@Composable
public fun SidebarHeader(
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(style.section),
        verticalArrangement = Arrangement.spacedBy(style.section),
        content = content,
    )
}

/**
 * The footer of a [Sidebar], a column at its bottom for content such as the account of the user.
 *
 * @param modifier the modifier of the footer.
 * @param style the style of the sidebar, by default the style of the theme.
 * @param content the content of the footer.
 * @since 1.0.0
 */
@Composable
public fun SidebarFooter(
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(style.section),
        verticalArrangement = Arrangement.spacedBy(style.section),
        content = content,
    )
}

/**
 * A horizontal line between the sections of a [Sidebar], in the border color of the sidebar.
 *
 * @param modifier the modifier of the line.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun SidebarSeparator(modifier: Modifier = Modifier, style: SidebarStyle = sidebarStyle()) {
    val line = Modifier
        .padding(horizontal = style.section)
        .fillMaxWidth()
        .height(1.dp)
        .background(style.border)
    Box(modifier = modifier.then(line))
}

/**
 * The scrolling middle of a [Sidebar], which takes the height that its header and footer leave and holds its
 * groups.
 *
 * @param modifier the modifier of the scroll area.
 * @param content the groups of the sidebar.
 * @since 1.0.0
 */
@Composable
public fun ColumnScope.SidebarContent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    ScrollArea(modifier = modifier.weight(1f).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

/**
 * A group of a [Sidebar], usually a [SidebarGroupLabel] above a [SidebarGroupContent].
 *
 * @param modifier the modifier of the group.
 * @param action the action in the top end corner of the group, such as a [SidebarGroupAction], or `null` for none;
 * it is hidden while the sidebar is collapsed to its icons.
 * @param style the style of the sidebar, by default the style of the theme.
 * @param content the content of the group.
 * @since 1.0.0
 */
@Composable
public fun SidebarGroup(
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(style.section), content = content)
        if (!LocalIconCollapsed.current) {
            action?.let { slot ->
                Box(
                    modifier = Modifier.align(Alignment.TopEnd).padding(top = ACTION_TOP, end = ACTION_END),
                ) { slot() }
            }
        }
    }
}

/**
 * The label of a [SidebarGroup], which shrinks and fades out while the sidebar is collapsed to its icons.
 *
 * @param text the label.
 * @param modifier the modifier of the label.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun SidebarGroupLabel(text: String, modifier: Modifier = Modifier, style: SidebarStyle = sidebarStyle()) {
    val isHidden = LocalIconCollapsed.current
    val height = animateDpAsState(
        targetValue = 0.dp.takeIf { isHidden } ?: style.labelHeight,
        animationSpec = style.widthAnimation,
    )
    val alpha = animateFloatAsState(targetValue = 0f.takeIf { isHidden } ?: 1f, animationSpec = theme.sizeTransition())
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .animatedHeight(height)
                .fadeBy(alpha)
                .clipToBounds()
                .padding(horizontal = style.buttonPadding),
        ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(text = text, style = style.labelStyle, color = style.labelColor, maxLines = 1)
    }
}

/**
 * The content of a [SidebarGroup], such as a [SidebarMenu], with the text style of the sidebar.
 *
 * @param modifier the modifier of the content.
 * @param style the style of the sidebar, by default the style of the theme.
 * @param content the content.
 * @since 1.0.0
 */
@Composable
public fun SidebarGroupContent(
    modifier: Modifier = Modifier,
    style: SidebarStyle = sidebarStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    CompositionLocalProvider(LocalTextStyle provides style.textStyle) {
        Column(modifier = modifier.fillMaxWidth(), content = content)
    }
}

/**
 * A menu of a [Sidebar], a column of [SidebarMenuItem]s as wide as the sidebar.
 *
 * @param modifier the modifier of the menu.
 * @param content the items of the menu.
 * @since 1.0.0
 */
@Composable
public fun SidebarMenu(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = modifier.fillMaxWidth(), content = content)
}

/**
 * An item of a [SidebarMenu], a box that holds a [SidebarMenuButton] and optionally a [SidebarMenuAction] or a
 * [SidebarMenuBadge] in its top end corner.
 *
 * @param modifier the modifier of the item.
 * @param content the content of the item.
 * @since 1.0.0
 */
@Composable
public fun SidebarMenuItem(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.fillMaxWidth(), content = content)
}

/**
 * A text field for a [Sidebar], such as a search field in its header, filled with the background color of the
 * sidebar style.
 *
 * @param value the text of the field.
 * @param onValueChange called with the new text when the user edits it.
 * @param modifier the modifier of the field.
 * @param placeholder the text shown while the field is empty, or `null` for none.
 * @param style the style of the sidebar, by default the style of the theme.
 * @since 1.0.0
 */
@Composable
public fun SidebarInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    style: SidebarStyle = sidebarStyle(),
) {
    val base = inputStyle()
    Input(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        style = base.copy(colors = base.colors.copy(container = StateValue(default = style.background))),
    )
}
