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

package com.davils.kiro.gallery.playground

import androidx.compose.ui.unit.Dp
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToLong
import kotlin.time.Duration

private const val PRECISION: Long = 1_000L
private const val DIGITS: Int = 3
private const val SECOND: Long = 1_000L
private const val STEPS: Float = 100f
private const val BASE: Float = 10f

internal fun numberOf(value: Float): String {
    val scaled = (value * PRECISION).roundToLong()
    val sign = "-".takeIf { scaled < 0L }.orEmpty()
    val whole = scaled.absoluteValue / PRECISION
    val digits = (scaled.absoluteValue % PRECISION).toString()
    val fraction = digits.padStart(DIGITS, '0').trimEnd('0')
    if (fraction.isEmpty()) return "$sign$whole"
    return "$sign$whole.$fraction"
}

internal fun floatCode(value: Float): String = "${numberOf(value)}f"

internal fun dpCode(value: Dp): String = "${numberOf(value.value)}.dp"

internal fun durationCode(value: Duration): String {
    val millis = value.inWholeMilliseconds
    if (millis in 1L..<SECOND) return "$millis.milliseconds"
    return "${numberOf(millis.toFloat() / SECOND)}.seconds"
}

internal fun stepOf(range: ClosedFloatingPointRange<Float>): Float {
    val span = range.endInclusive - range.start
    return BASE.pow(floor(log10(span / STEPS)))
}
