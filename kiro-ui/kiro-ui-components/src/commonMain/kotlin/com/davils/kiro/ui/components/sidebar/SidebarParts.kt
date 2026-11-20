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

@Composable
public fun SidebarSeparator(modifier: Modifier = Modifier, style: SidebarStyle = sidebarStyle()) {
    val line = Modifier
        .padding(horizontal = style.section)
        .fillMaxWidth()
        .height(1.dp)
        .background(style.border)
    Box(modifier = modifier.then(line))
}

@Composable
public fun ColumnScope.SidebarContent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    ScrollArea(modifier = modifier.weight(1f).fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth(), content = content)
    }
}

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

@Composable
public fun SidebarGroupLabel(text: String, modifier: Modifier = Modifier, style: SidebarStyle = sidebarStyle()) {
    val isHidden = LocalIconCollapsed.current
    val height = animateDpAsState(
        targetValue = 0.dp.takeIf { isHidden } ?: style.labelHeight,
        animationSpec = style.widthAnimation,
    )
    val alpha = animateFloatAsState(targetValue = 0f.takeIf { isHidden } ?: 1f, animationSpec = theme.sizeTransition())
    Box(
        modifier = modifier
            .fillMaxWidth()
            .animatedHeight(height)
            .fadeBy(alpha)
            .clipToBounds()
            .padding(horizontal = style.buttonPadding),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(text = text, style = style.labelStyle, color = style.labelColor, maxLines = 1)
    }
}

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

@Composable
public fun SidebarMenu(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = modifier.fillMaxWidth(), content = content)
}

@Composable
public fun SidebarMenuItem(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.fillMaxWidth(), content = content)
}

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
