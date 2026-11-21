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
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private const val MILLIS: Float = 1_000f

internal class Measure<V>(val amountOf: (V) -> Float, val valueOf: (Float) -> V, val code: (V) -> String)

internal val floats: Measure<Float> = Measure(
    amountOf = { value -> value },
    valueOf = { amount -> amount },
    code = ::floatCode,
)

internal val ints: Measure<Int> = Measure(amountOf = Int::toFloat, valueOf = Float::roundToInt, code = Int::toString)

internal val dps: Measure<Dp> = Measure(amountOf = Dp::value, valueOf = { amount -> amount.dp }, code = ::dpCode)

internal val durations: Measure<Duration> = Measure(
    amountOf = { duration -> duration.inWholeMilliseconds / MILLIS },
    valueOf = { amount -> (amount * MILLIS).roundToLong().milliseconds },
    code = ::durationCode,
)
