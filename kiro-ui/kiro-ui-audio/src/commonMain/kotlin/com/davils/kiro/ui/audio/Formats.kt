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

package com.davils.kiro.ui.audio

import kotlin.math.abs
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val TENTHS: Float = 10f
private const val KILO: Float = 1000f
private const val SILENCE: String = "-∞"
private const val PADDED: Int = 2
private const val SECONDS_PER_MINUTE: Long = 60L
private const val MINUTES_PER_HOUR: Long = 60L

internal fun tenthsOf(level: Float, signed: Boolean = false): String {
    if (level.isNaN() || level == Float.NEGATIVE_INFINITY) return SILENCE
    val tenths = (level * TENTHS).roundToLong()
    val sign = "-".takeIf { tenths < 0L } ?: "+".takeIf { signed && tenths > 0L }.orEmpty()
    val magnitude = abs(tenths)
    return "$sign${magnitude / TENTHS.toLong()}.${magnitude % TENTHS.toLong()}"
}

internal fun wholeDecibelsOf(level: Float): String {
    if (level.isNaN() || level == Float.NEGATIVE_INFINITY) return SILENCE
    return level.roundToLong().toString()
}

internal fun hertzOf(frequency: Float): String {
    if (frequency < KILO) return frequency.roundToLong().toString()
    val tenths = (frequency / KILO * TENTHS).roundToLong()
    val fraction = ".${tenths % TENTHS.toLong()}".takeIf { tenths % TENTHS.toLong() != 0L }.orEmpty()
    return "${tenths / TENTHS.toLong()}${fraction}k"
}

internal fun timecodeOf(time: Duration, framesPerSecond: Int): String {
    val clamped = time.coerceAtLeast(Duration.ZERO)
    val total = clamped.inWholeSeconds
    val frames = ((clamped - total.seconds) * framesPerSecond.toDouble()).inWholeSeconds
    val seconds = total % SECONDS_PER_MINUTE
    val minutes = total / SECONDS_PER_MINUTE % MINUTES_PER_HOUR
    val hours = total / SECONDS_PER_MINUTE / MINUTES_PER_HOUR
    return listOf(hours, minutes, seconds, frames).joinToString(separator = ":") { part -> padded(part) }
}

private fun padded(value: Long): String = value.toString().padStart(PADDED, '0')
