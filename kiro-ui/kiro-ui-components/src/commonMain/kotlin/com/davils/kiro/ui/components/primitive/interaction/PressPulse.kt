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

package com.davils.kiro.ui.components.primitive.interaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

private const val PULSE_MILLIS: Long = 150L

@Composable
internal fun rememberPressPulse(isPressed: Boolean): Boolean {
    var presses by remember { mutableIntStateOf(0) }
    var isPulsing by remember { mutableStateOf(false) }
    LaunchedEffect(isPressed) { if (isPressed) presses++ }
    LaunchedEffect(presses) {
        pulse(isActive = presses > 0) { isPulsing = it }
    }
    return isPulsing
}

private suspend fun pulse(isActive: Boolean, onChange: (Boolean) -> Unit) {
    if (!isActive) return
    onChange(true)
    delay(PULSE_MILLIS)
    onChange(false)
}
