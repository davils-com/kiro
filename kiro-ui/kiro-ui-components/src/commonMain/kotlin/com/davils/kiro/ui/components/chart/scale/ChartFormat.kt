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

package com.davils.kiro.ui.components.chart.scale

import kotlin.math.absoluteValue
import kotlin.math.roundToLong

private const val HUNDRED: Double = 100.0

private const val CENTS: Long = 100L

internal object ChartFormat {
    fun number(value: Double): String {
        val cents = (value * HUNDRED).roundToLong()
        val sign = "-".takeIf { cents < 0 }.orEmpty()
        val whole = cents.absoluteValue / CENTS
        val fraction = cents.absoluteValue % CENTS
        val padded = fraction.toString().padStart(length = 2, padChar = '0')
        val decimals = padded.trimEnd { char -> char == '0' }
        if (decimals.isEmpty()) return "$sign$whole"
        return "$sign$whole.$decimals"
    }
}

internal fun chartNumbers(): (Double) -> String = ChartFormat::number
