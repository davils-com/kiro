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

import kotlin.math.ceil

private const val QUARTER: Float = 4f

internal class IsometricGrid(
    val tile: Float,
    val centerX: Float,
    val firstDiagonal: Int,
    val lastDiagonal: Int,
    val firstColumn: Int,
    val lastColumn: Int,
) {
    val quarter: Float
        get() = tile / QUARTER

    fun xOf(column: Int): Float = centerX + column * tile / 2f

    fun yOf(diagonal: Int): Float = diagonal * quarter

    fun diagonalAt(y: Float): Float = y / quarter

    fun columnAt(x: Float): Float = (x - centerX) * 2f / tile

    fun firstColumnOf(diagonal: Int): Int = firstColumn + (firstColumn + diagonal).mod(2)

    companion object {
        fun of(width: Float, height: Float, tile: Float, reach: Float): IsometricGrid? {
            if (tile <= 0f || width <= 0f || height <= 0f) return null
            val quarter = tile / QUARTER
            val columns = ceil(width / tile).toInt() + 1
            return IsometricGrid(
                tile = tile,
                centerX = width / 2f,
                firstDiagonal = -1,
                lastDiagonal = ceil((height + reach + quarter) / quarter).toInt() + 1,
                firstColumn = -columns,
                lastColumn = columns,
            )
        }
    }
}
