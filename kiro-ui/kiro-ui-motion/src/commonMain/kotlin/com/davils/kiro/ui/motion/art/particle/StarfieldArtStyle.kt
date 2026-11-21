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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [StarfieldArt] looks: stars that fly out of a vanishing point toward the viewer, growing, brightening and
 * drawing short streaks as they come closer.
 *
 * @property count the number of stars at the highest quality.
 * @property centerX the horizontal position of the vanishing point, as a fraction of the width.
 * @property centerY the vertical position of the vanishing point, as a fraction of the height.
 * @property spread how far from the vanishing point the stars start, as a fraction of the larger side.
 * @property nearest the depth at which a star passes the viewer, as a fraction of the depth at which it starts.
 * @property period how long a star takes from its start until it passes the viewer.
 * @property streak the length of the streak behind a star, as a fraction of its depth.
 * @property minWidth the width of a star far away.
 * @property maxWidth the width of a star passing the viewer.
 * @property alpha the alpha of a star passing the viewer.
 * @property fadeIn the part of its way over which a star fades in, as a fraction.
 * @property colors the colors of the stars from far to near, or `null` for the accents of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class StarfieldArtStyle(
    public val count: Int,
    public val centerX: Float,
    public val centerY: Float,
    public val spread: Float,
    public val nearest: Float,
    public val period: Duration,
    public val streak: Float,
    public val minWidth: Dp,
    public val maxWidth: Dp,
    public val alpha: Float,
    public val fadeIn: Float,
    public val colors: List<Color>?,
) {
    /**
     * Returns a copy of this [StarfieldArtStyle] with the given values replaced and every other value kept.
     *
     * @param count the new value of [count].
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param spread the new value of [spread].
     * @param nearest the new value of [nearest].
     * @param period the new value of [period].
     * @param streak the new value of [streak].
     * @param minWidth the new value of [minWidth].
     * @param maxWidth the new value of [maxWidth].
     * @param alpha the new value of [alpha].
     * @param fadeIn the new value of [fadeIn].
     * @param colors the new value of [colors].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        count: Int = this.count,
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        spread: Float = this.spread,
        nearest: Float = this.nearest,
        period: Duration = this.period,
        streak: Float = this.streak,
        minWidth: Dp = this.minWidth,
        maxWidth: Dp = this.maxWidth,
        alpha: Float = this.alpha,
        fadeIn: Float = this.fadeIn,
        colors: List<Color>? = this.colors,
    ): StarfieldArtStyle = StarfieldArtStyle(
        count = count,
        centerX = centerX,
        centerY = centerY,
        spread = spread,
        nearest = nearest,
        period = period,
        streak = streak,
        minWidth = minWidth,
        maxWidth = maxWidth,
        alpha = alpha,
        fadeIn = fadeIn,
        colors = colors,
    )

    override fun equals(other: Any?): Boolean = other is StarfieldArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "StarfieldArtStyle(" +
        "count=$count, " +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "spread=$spread, " +
        "nearest=$nearest, " +
        "period=$period, " +
        "streak=$streak, " +
        "minWidth=$minWidth, " +
        "maxWidth=$maxWidth, " +
        "alpha=$alpha, " +
        "fadeIn=$fadeIn, " +
        "colors=${colors ?: "null"})"

    private fun values(): List<Any?> = listOf(
        count,
        centerX,
        centerY,
        spread,
        nearest,
        period,
        streak,
        minWidth,
        maxWidth,
        alpha,
        fadeIn,
        colors,
    )
}
