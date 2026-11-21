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
 * How a [BokehArt] looks: large, soft, out of focus discs of light that slowly wander and breathe.
 *
 * @property count the number of discs at the highest quality.
 * @property minRadius the radius of the smallest disc.
 * @property maxRadius the radius of the largest disc.
 * @property wander how far a disc wanders around its home.
 * @property period how long a disc takes for one loop of its wandering.
 * @property breathe how much a disc grows and shrinks while it breathes, as a fraction of its radius.
 * @property breathePeriod how long one breath of a disc takes.
 * @property alpha the alpha of the strongest disc.
 * @property alphaFloor the alpha of the faintest disc, as a fraction of [alpha].
 * @property softness the part of the radius over which the edge of a disc fades out, between `0` and `1`.
 * @property rimWidth the width of the faint ring at the edge of a disc.
 * @property rimAlpha the alpha of the ring, as a fraction of the alpha of its disc, zero for none.
 * @property colors the colors of the discs, or `null` for the accents of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class BokehArtStyle(
    public val count: Int,
    public val minRadius: Dp,
    public val maxRadius: Dp,
    public val wander: Dp,
    public val period: Duration,
    public val breathe: Float,
    public val breathePeriod: Duration,
    public val alpha: Float,
    public val alphaFloor: Float,
    public val softness: Float,
    public val rimWidth: Dp,
    public val rimAlpha: Float,
    public val colors: List<Color>?,
) {
    /**
     * Returns a copy of this [BokehArtStyle] with the given values replaced and every other value kept.
     *
     * @param count the new value of [count].
     * @param minRadius the new value of [minRadius].
     * @param maxRadius the new value of [maxRadius].
     * @param wander the new value of [wander].
     * @param period the new value of [period].
     * @param breathe the new value of [breathe].
     * @param breathePeriod the new value of [breathePeriod].
     * @param alpha the new value of [alpha].
     * @param alphaFloor the new value of [alphaFloor].
     * @param softness the new value of [softness].
     * @param rimWidth the new value of [rimWidth].
     * @param rimAlpha the new value of [rimAlpha].
     * @param colors the new value of [colors].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        count: Int = this.count,
        minRadius: Dp = this.minRadius,
        maxRadius: Dp = this.maxRadius,
        wander: Dp = this.wander,
        period: Duration = this.period,
        breathe: Float = this.breathe,
        breathePeriod: Duration = this.breathePeriod,
        alpha: Float = this.alpha,
        alphaFloor: Float = this.alphaFloor,
        softness: Float = this.softness,
        rimWidth: Dp = this.rimWidth,
        rimAlpha: Float = this.rimAlpha,
        colors: List<Color>? = this.colors,
    ): BokehArtStyle = BokehArtStyle(
        count = count,
        minRadius = minRadius,
        maxRadius = maxRadius,
        wander = wander,
        period = period,
        breathe = breathe,
        breathePeriod = breathePeriod,
        alpha = alpha,
        alphaFloor = alphaFloor,
        softness = softness,
        rimWidth = rimWidth,
        rimAlpha = rimAlpha,
        colors = colors,
    )

    override fun equals(other: Any?): Boolean = other is BokehArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BokehArtStyle(" +
        "count=$count, " +
        "minRadius=$minRadius, " +
        "maxRadius=$maxRadius, " +
        "wander=$wander, " +
        "period=$period, " +
        "breathe=$breathe, " +
        "breathePeriod=$breathePeriod, " +
        "alpha=$alpha, " +
        "alphaFloor=$alphaFloor, " +
        "softness=$softness, " +
        "rimWidth=$rimWidth, " +
        "rimAlpha=$rimAlpha, " +
        "colors=${colors ?: "null"})"

    private fun values(): List<Any?> = listOf(
        count,
        minRadius,
        maxRadius,
        wander,
        period,
        breathe,
        breathePeriod,
        alpha,
        alphaFloor,
        softness,
        rimWidth,
        rimAlpha,
        colors,
    )
}
