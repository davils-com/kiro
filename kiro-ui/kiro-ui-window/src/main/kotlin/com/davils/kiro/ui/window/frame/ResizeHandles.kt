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

package com.davils.kiro.ui.window.frame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import java.awt.Cursor

internal const val RESIZE_TAG_PREFIX: String = "resize-"

private val handleOrder: List<ResizeEdge> = ResizeEdge.entries.sortedBy { edge -> edge.zone == ResizeZone.Corner }

@Composable
internal fun ResizeHandles(frame: ChromeFrame, thickness: Dp) {
    Box(modifier = Modifier.fillMaxSize()) {
        handleOrder.forEach { edge ->
            Box(
                modifier = Modifier
                    .align(edge.alignment)
                    .zone(edge = edge, thickness = thickness)
                    .pointerHoverIcon(PointerIcon(Cursor(edge.cursor)))
                    .windowResize(frame = frame, edge = edge)
                    .testTag(RESIZE_TAG_PREFIX + edge.name),
            )
        }
    }
}

private fun Modifier.zone(edge: ResizeEdge, thickness: Dp): Modifier = when (edge.zone) {
    ResizeZone.Corner -> size(thickness * 2)
    ResizeZone.Column -> fillMaxHeight().width(thickness)
    ResizeZone.Row -> fillMaxWidth().height(thickness)
}
