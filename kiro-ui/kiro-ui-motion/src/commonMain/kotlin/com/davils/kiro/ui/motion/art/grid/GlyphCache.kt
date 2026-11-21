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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.Density

internal class GlyphCache(capacity: Int) {
    private val layouts = arrayOfNulls<TextLayoutResult>(capacity.coerceAtLeast(minimumValue = 0))
    private var scale = 0f
    private var fontScale = 0f

    val capacity: Int
        get() = layouts.size

    var measured: Int = 0
        private set

    fun glyph(index: Int, density: Density, measure: (Int) -> TextLayoutResult): TextLayoutResult {
        if (density.density != scale || density.fontScale != fontScale) {
            scale = density.density
            fontScale = density.fontScale
            layouts.fill(element = null)
        }
        val cached = layouts[index]
        if (cached != null) return cached
        val layout = measure(index)
        layouts[index] = layout
        measured++
        return layout
    }
}
