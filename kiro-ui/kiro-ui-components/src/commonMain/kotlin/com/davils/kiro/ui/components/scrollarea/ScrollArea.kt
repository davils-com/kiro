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

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.v2.ScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.scrollarea.scrollbar.ScrollFrame
import com.davils.kiro.ui.components.scrollarea.scrollbar.overflows
import com.davils.kiro.ui.components.scrollarea.scrollbar.wheelProxy

/**
 * Shows content that scrolls in one or both directions, with a themed scrollbar for each direction in which the
 * content overflows the area.
 *
 * A scrollbar takes its own space at the end or the bottom of the area. When the area scrolls only horizontally, or
 * only its horizontal direction overflows, the vertical mouse wheel scrolls horizontally.
 *
 * @param modifier the modifier of the area.
 * @param direction the directions in which the content scrolls.
 * @param verticalState the state of the vertical scroll position.
 * @param horizontalState the state of the horizontal scroll position.
 * @param style the style of the scrollbars.
 * @param content the content.
 * @since 1.0.0
 */
@Composable
public fun ScrollArea(
    modifier: Modifier = Modifier,
    direction: ScrollDirection = ScrollDirection.Vertical,
    verticalState: ScrollState = rememberScrollState(),
    horizontalState: ScrollState = rememberScrollState(),
    style: ScrollAreaStyle = scrollAreaStyle(),
    content: @Composable () -> Unit,
) {
    ScrollAreaLayout(
        modifier = modifier,
        direction = direction,
        verticalState = verticalState,
        horizontalState = horizontalState,
        style = style,
        viewport = Modifier,
        content = content,
    )
}

@Composable
internal fun ScrollAreaLayout(
    modifier: Modifier,
    direction: ScrollDirection,
    verticalState: ScrollState,
    horizontalState: ScrollState,
    style: ScrollAreaStyle,
    viewport: Modifier,
    content: @Composable () -> Unit,
) {
    val isVertical = direction != ScrollDirection.Horizontal
    val isHorizontal = direction != ScrollDirection.Vertical
    val isOnlyHorizontalOverflowing = !verticalState.overflows() && horizontalState.overflows()
    val isWheelHorizontal = isHorizontal && (!isVertical || isOnlyHorizontalOverflowing)
    val wheel = wheelProxy(target = horizontalState, isEnabled = isWheelHorizontal)
    ScrollFrame(
        vertical = adapterOf(state = verticalState, isScrollable = isVertical),
        horizontal = adapterOf(state = horizontalState, isScrollable = isHorizontal),
        modifier = modifier,
        style = style,
    ) { gutter ->
        val vertical = Modifier.verticalScroll(verticalState).takeIf { isVertical } ?: Modifier
        val horizontal = Modifier.horizontalScroll(horizontalState).takeIf { isHorizontal } ?: Modifier
        val frame = gutter.then(viewport)
        val scrolling = vertical.then(horizontal).then(wheel)
        Box(modifier = frame.then(scrolling)) { content() }
    }
}

@Composable
private fun adapterOf(state: ScrollState, isScrollable: Boolean): ScrollbarAdapter? {
    val isShown = isScrollable && state.overflows()
    if (!isShown) return null
    return rememberScrollbarAdapter(state)
}
