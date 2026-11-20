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

package com.davils.kiro.ui.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.roving.rovingMoveOf
import com.davils.kiro.ui.components.primitive.roving.rovingTarget
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun <T> Tabs(
    selected: T,
    onSelectedChange: (T) -> Unit,
    modifier: Modifier = Modifier,
    listModifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    variant: TabsVariant = TabsVariant.Default,
    style: TabsStyle = tabsStyle(variant),
    content: TabsScopeImpl<T>.() -> Unit,
) {
    val entries = TabsScope<T>().apply(content).entries
    val isReversed = LocalLayoutDirection.current == LayoutDirection.Rtl
    var requested by remember { mutableStateOf<Int?>(null) }
    val current = entries.indexOfFirst { entry -> entry.value == selected }
    val enabled = entries.map { entry -> entry.isEnabled }
    val keys = Modifier.onKeyEvent { event ->
        val move = rovingMoveOf(key = event.key, orientation = orientation, isReversed = isReversed)
            ?.takeIf { event.type == KeyEventType.KeyDown }
        val target = move?.let { step -> rovingTarget(move = step, current = maxOf(current, 0), isEnabled = enabled) }
        target?.let { index ->
            requested = index
            onSelectedChange(entries[index].value)
        }
        target != null
    }
    val list = Modifier
        .then(listModifier)
        .then(sizeOf(orientation = orientation, style = style))
        .surface(shape = style.listShape, color = style.listContainer)
        .padding(style.listPadding)
        .selectableGroup()
        .then(keys)
    val triggers: @Composable (Modifier) -> Unit = { each ->
        entries.forEachIndexed { index, entry ->
            TabTrigger(
                entry = entry,
                state = TabState(
                    isSelected = index == current,
                    isTabbable = index == current || current < 0,
                    isFocusRequested = index == requested,
                ),
                orientation = orientation,
                modifier = each,
                onSelect = { onSelectedChange(entry.value) },
                style = style,
            )
        }
    }
    when (orientation) {
        Orientation.Horizontal -> Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(style.gap),
        ) {
            Row(
                modifier = list,
                horizontalArrangement = Arrangement.spacedBy(style.listGap),
            ) { triggers(Modifier.weight(1f)) }
            TabPanel(entry = entries.getOrNull(current), style = style)
        }

        Orientation.Vertical -> Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(style.gap)) {
            Column(
                modifier = list,
                verticalArrangement = Arrangement.spacedBy(style.listGap),
            ) { triggers(Modifier.fillMaxWidth()) }
            TabPanel(entry = entries.getOrNull(current), style = style)
        }
    }
}

@Composable
private fun <T> TabPanel(entry: TabEntry<T>?, style: TabsStyle) {
    val content = entry?.content ?: return
    CompositionLocalProvider(LocalTextStyle provides style.contentTextStyle, content = content)
}

private fun sizeOf(orientation: Orientation, style: TabsStyle): Modifier = when (orientation) {
    Orientation.Horizontal -> Modifier.width(IntrinsicSize.Max).height(style.listHeight)
    Orientation.Vertical -> Modifier.width(IntrinsicSize.Max)
}
