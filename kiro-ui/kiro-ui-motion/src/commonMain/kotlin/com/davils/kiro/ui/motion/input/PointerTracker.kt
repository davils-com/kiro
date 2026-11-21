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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

@Stable
internal class PointerTracker {
    private var target: Offset? by mutableStateOf(null)

    var pointer: Offset? by mutableStateOf(null)
        private set

    var velocity: Offset by mutableStateOf(Offset.Zero)
        private set

    var isPressed: Boolean by mutableStateOf(false)
        private set

    val isSettling: Boolean
        get() = target != null && (target != pointer || velocity != Offset.Zero)

    fun move(position: Offset, isPressed: Boolean) {
        target = position
        this.isPressed = isPressed
    }

    fun exit() {
        target = null
        pointer = null
        velocity = Offset.Zero
        isPressed = false
    }

    fun step(deltaNanos: Long, smoothing: Float) {
        val goal = target ?: return
        val current = pointer ?: goal
        val next = approach(current = current, target = goal, share = smoothingShare(smoothing, deltaNanos))
        velocity = velocityOf(from = current, to = next, deltaNanos = deltaNanos)
        pointer = next
    }

    fun inputsOf(external: ArtInputs): ArtInputs {
        val smoothed = pointer ?: return external
        return external.copy(pointer = smoothed, velocity = velocity, isPressed = isPressed)
    }
}
