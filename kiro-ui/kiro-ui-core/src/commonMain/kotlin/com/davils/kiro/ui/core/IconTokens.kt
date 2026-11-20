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

package com.davils.kiro.ui.core

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * How icons are drawn by default: their size and the width of their strokes.
 *
 * @property size the width and height of an icon.
 * @property strokeWidth the width of the strokes of an icon, in units of its 24 by 24 canvas.
 * @property isNonScalingStroke whether the strokes keep [strokeWidth] in dp whatever the size of the icon.
 * @since 1.0.0
 */
@Immutable
public class IconTokens(
    public val size: Dp = 16.dp,
    public val strokeWidth: Float = 2f,
    public val isNonScalingStroke: Boolean = false,
) {
    /**
     * Returns a copy of this [IconTokens] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param strokeWidth the new value of [strokeWidth].
     * @param isNonScalingStroke the new value of [isNonScalingStroke].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: Dp = this.size,
        strokeWidth: Float = this.strokeWidth,
        isNonScalingStroke: Boolean = this.isNonScalingStroke,
    ): IconTokens = IconTokens(
        size = size,
        strokeWidth = strokeWidth,
        isNonScalingStroke = isNonScalingStroke,
    )

    override fun equals(other: Any?): Boolean = other is IconTokens && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "IconTokens(" +
        "size=$size, " +
        "strokeWidth=$strokeWidth, " +
        "isNonScalingStroke=$isNonScalingStroke)"

    private fun values(): List<Any?> = listOf(
        size,
        strokeWidth,
        isNonScalingStroke,
    )
}
