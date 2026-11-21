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
import com.davils.kiro.ui.motion.time.ArtTime
import kotlin.time.Duration

private const val ANGLE_SALT: Int = 0x2545f491
private const val DISTANCE_SALT: Int = 0x6c8e9cf5

internal fun revolutionOf(time: ArtTime, period: Duration): Int {
    if (!period.isPositive()) return 0
    return (time.nanos / period.inWholeNanoseconds).toInt()
}

internal fun blipAngle(index: Int, revolution: Int, seed: Int): Float =
    unitOf(x = index, y = revolution, seed = seed + ANGLE_SALT)

internal fun blipDistance(index: Int, revolution: Int, seed: Int, inner: Float): Float {
    val low = inner.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return low + (1f - low) * unitOf(x = index, y = revolution, seed = seed + DISTANCE_SALT)
}

internal fun blipRevolution(index: Int, revolution: Int, phase: Double, seed: Int): Int {
    if (phase >= blipAngle(index = index, revolution = revolution, seed = seed)) return revolution
    return revolution - 1
}

internal fun blipAge(index: Int, revolution: Int, phase: Double, seed: Int): Float {
    val found = blipRevolution(index = index, revolution = revolution, phase = phase, seed = seed)
    return (phase + (revolution - found) - blipAngle(index = index, revolution = found, seed = seed)).toFloat()
}

internal fun tickLength(index: Int, majorEvery: Int, tick: Float, majorTick: Float): Float {
    if (majorEvery > 0 && index % majorEvery == 0) return majorTick
    return tick
}
