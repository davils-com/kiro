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

package com.davils.kiro.ui.motion.art.geometry

import com.davils.kiro.ui.motion.noise.unitOf
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sqrt

internal fun waveHeight(distance: Float, frequency: Float, phase: Double): Float {
    val angle = 2.0 * PI * (distance * frequency - (phase - floor(phase)))
    return ((1.0 + cos(angle)) / 2.0).toFloat()
}

internal fun blockHeight(wave: Float, jitter: Float, column: Int, diagonal: Int, seed: Int): Float {
    val own = unitOf(x = column, y = diagonal, seed = seed)
    return wave * (1f - jitter) + own * jitter
}

internal fun gridDistance(column: Float, diagonal: Float, originColumn: Float, originDiagonal: Float): Float {
    val di = (diagonal + column - originDiagonal - originColumn) / 2f
    val dj = (diagonal - column - originDiagonal + originColumn) / 2f
    return sqrt(di * di + dj * dj)
}
