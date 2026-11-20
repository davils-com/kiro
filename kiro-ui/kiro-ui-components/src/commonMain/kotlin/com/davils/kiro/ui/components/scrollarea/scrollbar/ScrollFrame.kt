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

package com.davils.kiro.ui.components.scrollarea.scrollbar

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.v2.ScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.scrollarea.ScrollAreaStyle

@Composable
internal fun ScrollFrame(
    vertical: ScrollbarAdapter?,
    horizontal: ScrollbarAdapter?,
    modifier: Modifier,
    style: ScrollAreaStyle,
    viewport: @Composable (gutter: Modifier) -> Unit,
) {
    val end = style.thickness.takeIf { vertical != null } ?: 0.dp
    val bottom = style.thickness.takeIf { horizontal != null } ?: 0.dp
    val bar = barStyleOf(style)
    Box(modifier = modifier) {
        viewport(Modifier.padding(end = end, bottom = bottom))
        vertical?.let { adapter ->
            VerticalScrollbar(
                adapter = adapter,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight()
                    .padding(bottom = bottom)
                    .width(style.thickness)
                    .padding(style.inset),
                style = bar,
            )
        }
        horizontal?.let { adapter ->
            HorizontalScrollbar(
                adapter = adapter,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(end = end)
                    .height(style.thickness)
                    .padding(style.inset),
                style = bar,
            )
        }
    }
}

private fun barStyleOf(style: ScrollAreaStyle): ScrollbarStyle = ScrollbarStyle(
    minimalHeight = style.minimalThumbLength,
    thickness = style.thickness - style.inset * 2,
    shape = style.thumbShape,
    hoverDurationMillis = style.hoverDurationMillis,
    unhoverColor = style.thumbColor,
    hoverColor = style.thumbHoverColor,
)
