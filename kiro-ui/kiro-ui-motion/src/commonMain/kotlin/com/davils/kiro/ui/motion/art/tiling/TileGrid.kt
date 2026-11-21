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

import kotlin.math.ceil

internal class TileGrid(
    val columns: Int,
    val rows: Int,
    val left: Float,
    val top: Float,
    val pitch: Float,
) {
    fun leftOf(column: Int): Float = left + column * pitch

    fun topOf(row: Int): Float = top + row * pitch

    companion object {
        val Empty: TileGrid = TileGrid(columns = 0, rows = 0, left = 0f, top = 0f, pitch = 0f)

        fun of(width: Float, height: Float, pitch: Float): TileGrid {
            if (pitch <= 0f || width <= 0f || height <= 0f) return Empty
            val columns = ceil(width / pitch).toInt()
            val rows = ceil(height / pitch).toInt()
            return TileGrid(
                columns = columns,
                rows = rows,
                left = (width - columns * pitch) / 2f,
                top = (height - rows * pitch) / 2f,
                pitch = pitch,
            )
        }
    }
}
