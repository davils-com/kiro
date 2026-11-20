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

package com.davils.kiro.ui.components.menu.intent

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.pointerTracking(coordinates: PanelCoordinates, onMove: (Offset) -> Unit): Modifier =
    pointerInput(coordinates, onMove) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                windowPointOf(event = event, coordinates = coordinates)?.let(onMove)
            }
        }
    }

private fun windowPointOf(event: PointerEvent, coordinates: PanelCoordinates): Offset? {
    val position = event.changes.firstOrNull()?.position
    val layout = coordinates.layout?.takeIf { it.isAttached }
    if (position == null || layout == null) return null
    return layout.localToWindow(position)
}
