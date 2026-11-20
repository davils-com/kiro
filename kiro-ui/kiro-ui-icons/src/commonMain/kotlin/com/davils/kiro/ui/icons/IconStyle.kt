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

package com.davils.kiro.ui.icons

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.takeOrElse
import com.davils.kiro.ui.core.IconTokens

/**
 * How an [Icon] is drawn. Every value that is left unspecified comes from the [IconTokens] of the theme, and an
 * unspecified color from the content color, so a style only names what it changes.
 *
 * @property size the width and height of the icon, or [Dp.Unspecified] for the size of the theme.
 * @property strokeWidth the width of the strokes on the 24 by 24 canvas, or [Float.NaN] for the width of the theme.
 * @property isNonScalingStroke whether strokes keep their width in dp whatever the size, or null for the choice of the
 *   theme.
 * @property color the color of the icon, or [Color.Unspecified] for the content color.
 * @since 1.0.0
 */
@Immutable
public class IconStyle(
    public val size: Dp = Dp.Unspecified,
    public val strokeWidth: Float = Float.NaN,
    public val isNonScalingStroke: Boolean? = null,
    public val color: Color = Color.Unspecified,
) {
    /**
     * Returns a copy of this [IconStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param strokeWidth the new value of [strokeWidth].
     * @param isNonScalingStroke the new value of [isNonScalingStroke].
     * @param color the new value of [color].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: Dp = this.size,
        strokeWidth: Float = this.strokeWidth,
        isNonScalingStroke: Boolean? = this.isNonScalingStroke,
        color: Color = this.color,
    ): IconStyle = IconStyle(
        size = size,
        strokeWidth = strokeWidth,
        isNonScalingStroke = isNonScalingStroke,
        color = color,
    )

    /**
     * Returns this style with every unspecified value taken from the icon tokens of a theme and the color of the
     * content.
     *
     * @param tokens the icon tokens of the theme.
     * @param contentColor the color of the content the icon sits in.
     * @return a style in which every value is specified.
     * @since 1.0.0
     */
    public fun resolve(tokens: IconTokens, contentColor: Color): IconStyle = IconStyle(
        size = size.takeOrElse { tokens.size },
        strokeWidth = strokeWidth.takeUnless { width -> width.isNaN() } ?: tokens.strokeWidth,
        isNonScalingStroke = isNonScalingStroke ?: tokens.isNonScalingStroke,
        color = color.takeOrElse { contentColor },
    )

    override fun equals(other: Any?): Boolean = other is IconStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "IconStyle(" +
        "size=$size, " +
        "strokeWidth=$strokeWidth, " +
        "isNonScalingStroke=${isNonScalingStroke ?: "null"}, " +
        "color=$color)"

    private fun values(): List<Any?> = listOf(
        size,
        strokeWidth,
        isNonScalingStroke,
        color,
    )
}
