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

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.captionHitTest(frame: ChromeFrame): Modifier = pointerInput(frame) {
    awaitPointerEventScope {
        var isInControl = false
        while (true) {
            val event = awaitPointerEvent(PointerEventPass.Main)
            val isConsumed = event.changes.any { change -> change.isConsumed }
            if (isConsumed && event.type == PointerEventType.Press) isInControl = true
            frame.hitTest(isClient = isConsumed || isInControl)
            if (event.type == PointerEventType.Release) isInControl = false
        }
    }
}
