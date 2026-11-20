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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.v2.ScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.scrollarea.scrollbar.ScrollFrame
import com.davils.kiro.ui.components.scrollarea.scrollbar.overflows
import com.davils.kiro.ui.components.scrollarea.scrollbar.wheelProxy

/**
 * Shows a lazy list, a column or a row that composes only its visible items, with a themed scrollbar that appears
 * while the items overflow the area.
 *
 * The scrollbar takes its own space at the end or the bottom of the area. In a row, the vertical mouse wheel
 * scrolls horizontally.
 *
 * @param modifier the modifier of the area.
 * @param state the state of the list, which holds its scroll position.
 * @param orientation [Orientation.Vertical] for a column, [Orientation.Horizontal] for a row.
 * @param contentPadding the padding around the items, inside the scrolled content.
 * @param style the style of the scrollbar.
 * @param content declares the items of the list.
 * @since 1.0.0
 */
@Composable
public fun LazyScrollArea(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    orientation: Orientation = Orientation.Vertical,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    style: ScrollAreaStyle = scrollAreaStyle(),
    content: LazyListScope.() -> Unit,
) {
    val adapter = adapterOf(state)
    val isVertical = orientation == Orientation.Vertical
    val wheel = wheelProxy(target = state, isEnabled = !isVertical)
    ScrollFrame(
        vertical = adapter.takeIf { isVertical },
        horizontal = adapter.takeUnless { isVertical },
        modifier = modifier,
        style = style,
    ) { gutter ->
        LazyList(
            isVertical = isVertical,
            modifier = gutter.then(wheel),
            state = state,
            contentPadding = contentPadding,
            content = content,
        )
    }
}

@Composable
private fun LazyList(
    isVertical: Boolean,
    modifier: Modifier,
    state: LazyListState,
    contentPadding: PaddingValues,
    content: LazyListScope.() -> Unit,
) {
    if (isVertical) {
        LazyColumn(modifier = modifier, state = state, contentPadding = contentPadding, content = content)
        return
    }
    LazyRow(modifier = modifier, state = state, contentPadding = contentPadding, content = content)
}

@Composable
private fun adapterOf(state: LazyListState): ScrollbarAdapter? {
    if (!state.overflows()) return null
    return rememberScrollbarAdapter(state)
}
