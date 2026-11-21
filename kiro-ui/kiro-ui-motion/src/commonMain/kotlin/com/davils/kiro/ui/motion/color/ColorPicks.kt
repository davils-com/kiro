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

package com.davils.kiro.ui.motion.color

import androidx.compose.ui.graphics.Color

internal fun List<Color>.cyclic(index: Int, fallback: Color): Color {
    if (isEmpty()) return fallback
    return get(index.mod(size))
}

internal fun List<Color>.band(fraction: Float, fallback: Color): Color {
    if (isEmpty()) return fallback
    val index = (fraction.coerceIn(minimumValue = 0f, maximumValue = 1f) * size).toInt().coerceAtMost(size - 1)
    return get(index)
}

internal fun ArtPalette.colorsOr(colors: List<Color>?): List<Color> = (colors ?: accents).ifEmpty { listOf(ink) }
