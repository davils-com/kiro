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

package com.davils.kiro.ui.components.togglegroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.buttongroup.GroupItem
import com.davils.kiro.ui.components.buttongroup.LocalGroupItem
import com.davils.kiro.ui.components.primitive.roving.rovingKeys
import com.davils.kiro.ui.components.primitive.selection.toggledIn
import com.davils.kiro.ui.components.toggle.Toggle
import com.davils.kiro.ui.components.toggle.ToggleSize
import com.davils.kiro.ui.components.toggle.ToggleStyle
import com.davils.kiro.ui.components.toggle.ToggleVariant
import com.davils.kiro.ui.components.toggle.defaultToggleStyle
import com.davils.kiro.ui.core.Theme

@Composable
public fun <T> ToggleGroup(
    selected: Set<T>,
    onSelectedChange: (Set<T>) -> Unit,
    modifier: Modifier = Modifier,
    isMultiple: Boolean = false,
    orientation: Orientation = Orientation.Horizontal,
    style: ToggleGroupStyle = toggleGroupStyle(),
    content: ToggleGroupScope<T>.() -> Unit,
) {
    val entries = ToggleGroupScopeImpl<T>().apply(content).entries
    val isJoined = style.spacing == 0.dp
    val itemStyle = itemStyleOf(style = style, isJoined = isJoined)
    val stretch = stretchOf(orientation)
    val requesters = remember(entries.size) { List(entries.size) { FocusRequester() } }
    var focused by remember { mutableIntStateOf(0) }
    val keys = Modifier.rovingKeys(
        orientation = orientation,
        isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl,
        isEnabled = { entries.map { entry -> entry.isEnabled } },
        current = { focused },
        onTarget = { index -> requesters[index].requestFocus() },
    )
    val items: @Composable () -> Unit = {
        entries.forEachIndexed { index, entry ->
            val position = GroupItem(
                orientation = orientation,
                isFirst = index == 0,
                isLast = index == entries.lastIndex,
                radius = style.radius,
            )
            CompositionLocalProvider(LocalGroupItem provides position.takeIf { isJoined }) {
                Toggle(
                    pressed = entry.value in selected,
                    onPressedChange = {
                        onSelectedChange(
                            toggledIn(selected = selected, value = entry.value, isMultiple = isMultiple),
                        )
                    },
                    modifier = stretch
                        .focusRequester(requesters[index])
                        .onFocusChanged { focus -> if (focus.isFocused) focused = index },
                    enabled = entry.isEnabled,
                    style = itemStyle,
                    content = entry.content,
                )
            }
        }
    }
    val arranged = Arrangement.spacedBy(style.spacing)
    when (orientation) {
        Orientation.Horizontal -> Row(
            modifier = modifier.then(keys),
            horizontalArrangement = arranged,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items()
        }

        Orientation.Vertical -> Column(
            modifier = modifier.width(IntrinsicSize.Max).then(keys),
            verticalArrangement = arranged,
        ) {
            items()
        }
    }
}

internal fun defaultToggleGroupStyle(theme: Theme, variant: ToggleVariant, size: ToggleSize): ToggleGroupStyle =
    ToggleGroupStyle(
        spacing = 8.dp,
        item = defaultToggleStyle(theme = theme, variant = variant, size = size),
        joinedPadding = 8.dp,
        joinedIconEdgePadding = 6.dp,
        radius = theme.radii.lg,
    )

private fun itemStyleOf(style: ToggleGroupStyle, isJoined: Boolean): ToggleStyle {
    if (!isJoined) return style.item
    return style.item.copy(horizontalPadding = style.joinedPadding, iconEdgePadding = style.joinedIconEdgePadding)
}

private fun stretchOf(orientation: Orientation): Modifier = when (orientation) {
    Orientation.Horizontal -> Modifier
    Orientation.Vertical -> Modifier.fillMaxWidth()
}
