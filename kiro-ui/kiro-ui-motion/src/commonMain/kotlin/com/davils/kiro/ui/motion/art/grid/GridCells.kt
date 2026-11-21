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

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

internal class GridCells(
    val pitch: Float,
    val left: Float,
    val top: Float,
    val columns: Int,
    val rows: Int,
) {
    fun xOf(column: Int): Float = left + column * pitch

    fun yOf(row: Int): Float = top + row * pitch

    fun columnAt(x: Float): Int = floor((x - left) / pitch).toInt()

    fun rowAt(y: Float): Int = floor((y - top) / pitch).toInt()

    fun lineAt(position: Float, start: Float): Int = ((position - start) / pitch).roundToInt()

    companion object {
        private val Empty = GridCells(pitch = 1f, left = 0f, top = 0f, columns = 0, rows = 0)

        fun of(width: Float, height: Float, pitch: Float): GridCells {
            if (pitch <= 0f || width <= 0f || height <= 0f) return Empty
            val left = (width / 2f).mod(pitch) - pitch
            val top = (height / 2f).mod(pitch) - pitch
            return GridCells(
                pitch = pitch,
                left = left,
                top = top,
                columns = ceil((width - left) / pitch).toInt(),
                rows = ceil((height - top) / pitch).toInt(),
            )
        }
    }
}

internal fun isMajor(index: Int, middle: Int, every: Int): Boolean = every > 0 && (index - middle).mod(every) == 0
