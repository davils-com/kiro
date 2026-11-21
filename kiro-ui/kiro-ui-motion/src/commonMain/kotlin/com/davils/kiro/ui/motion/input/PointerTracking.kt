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

package com.davils.kiro.ui.motion.input

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.trackPointer(tracker: PointerTracker, isEnabled: Boolean): Modifier {
    if (!isEnabled) return this
    return pointerInput(tracker) {
        awaitPointerEventScope {
            while (true) tracker.follow(awaitPointerEvent())
        }
    }
}

private fun PointerTracker.follow(event: PointerEvent) {
    if (event.type == PointerEventType.Exit) return exit()
    event.changes.forEach { change -> move(position = change.position, isPressed = change.pressed) }
}
