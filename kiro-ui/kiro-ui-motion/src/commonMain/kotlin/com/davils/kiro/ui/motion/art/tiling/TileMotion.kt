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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.animation.core.Easing
import kotlin.math.floor

internal fun wholeTurns(phase: Double): Int = floor(phase).toInt()

internal fun turnProgress(phase: Double, turn: Float, easing: Easing): Float {
    if (turn <= 0f) return 1f
    val fraction = (phase - floor(phase)) / turn
    return easing.transform(fraction.toFloat().coerceIn(minimumValue = 0f, maximumValue = 1f))
}

internal fun Boolean.toBit(): Int = compareTo(false)
