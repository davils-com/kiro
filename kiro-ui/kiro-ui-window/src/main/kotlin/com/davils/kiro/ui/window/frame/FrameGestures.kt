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

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.windowDrag(frame: ChromeFrame): Modifier = pointerInput(frame) {
    var session: FrameDrag? = null
    val stop = { session = null }
    detectDragGestures(
        onDragStart = { session = frame.beginDrag() },
        onDragEnd = stop,
        onDragCancel = stop,
    ) { change, _ ->
        change.consume()
        session?.let(frame::drag)
    }
}

internal fun Modifier.windowResize(frame: ChromeFrame, edge: ResizeEdge): Modifier = pointerInput(frame, edge) {
    var session: FrameDrag? = null
    val stop = { session = null }
    detectDragGestures(
        onDragStart = { session = frame.beginResize() },
        onDragEnd = stop,
        onDragCancel = stop,
    ) { change, _ ->
        change.consume()
        session?.let { active -> frame.resize(edge = edge, session = active) }
    }
}
