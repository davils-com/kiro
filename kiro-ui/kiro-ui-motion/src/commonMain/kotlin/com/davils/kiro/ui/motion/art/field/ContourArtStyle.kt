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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [ContourArt] looks: the resolution of its grid, the number of levels, its lines and bands, and the
 * terrain they trace.
 *
 * @property colors the colors the levels run through from the lowest to the highest, or `null` for the accents of the
 * palette of the frame.
 * @property cell the size of a cell of the grid the terrain is sampled on at the highest quality.
 * @property levels the number of contour lines between the lowest and the highest point.
 * @property indexEvery every how many levels an index line is drawn, or `0` for none.
 * @property lineWidth the width of a contour line.
 * @property indexWidth the width of an index line.
 * @property lineAlpha the alpha of a contour line that is no index line.
 * @property indexAlpha the alpha of an index line.
 * @property ink the neutral color of the lines, or `null` for the ink of the palette of the frame.
 * @property lineTint how far a line takes the color of its level instead of [ink], from `0` to `1`.
 * @property bandAlpha the alpha of the band of color above each level, or `0` for no bands.
 * @property scale the size of a hill of the terrain.
 * @property octaves the number of layers of detail of the terrain.
 * @property warp how strongly the terrain is folded, `0` for not at all.
 * @property contrast how far the terrain spreads over the levels; higher values give more lines.
 * @property drift how far the terrain moves sideways in one [period], as a fraction of [scale].
 * @property period how long the terrain takes to change its shape completely.
 * @since 1.0.0
 */
@Immutable
public class ContourArtStyle(
    public val colors: List<Color>?,
    public val cell: Dp,
    public val levels: Int,
    public val indexEvery: Int,
    public val lineWidth: Dp,
    public val indexWidth: Dp,
    public val lineAlpha: Float,
    public val indexAlpha: Float,
    public val ink: Color?,
    public val lineTint: Float,
    public val bandAlpha: Float,
    public val scale: Dp,
    public val octaves: Int,
    public val warp: Float,
    public val contrast: Float,
    public val drift: Float,
    public val period: Duration,
) {
    /**
     * Returns a copy of this [ContourArtStyle] with the given values replaced and every other value kept.
     *
     * @param colors the new value of [colors].
     * @param cell the new value of [cell].
     * @param levels the new value of [levels].
     * @param indexEvery the new value of [indexEvery].
     * @param lineWidth the new value of [lineWidth].
     * @param indexWidth the new value of [indexWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param indexAlpha the new value of [indexAlpha].
     * @param ink the new value of [ink].
     * @param lineTint the new value of [lineTint].
     * @param bandAlpha the new value of [bandAlpha].
     * @param scale the new value of [scale].
     * @param octaves the new value of [octaves].
     * @param warp the new value of [warp].
     * @param contrast the new value of [contrast].
     * @param drift the new value of [drift].
     * @param period the new value of [period].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        colors: List<Color>? = this.colors,
        cell: Dp = this.cell,
        levels: Int = this.levels,
        indexEvery: Int = this.indexEvery,
        lineWidth: Dp = this.lineWidth,
        indexWidth: Dp = this.indexWidth,
        lineAlpha: Float = this.lineAlpha,
        indexAlpha: Float = this.indexAlpha,
        ink: Color? = this.ink,
        lineTint: Float = this.lineTint,
        bandAlpha: Float = this.bandAlpha,
        scale: Dp = this.scale,
        octaves: Int = this.octaves,
        warp: Float = this.warp,
        contrast: Float = this.contrast,
        drift: Float = this.drift,
        period: Duration = this.period,
    ): ContourArtStyle = ContourArtStyle(
        colors = colors,
        cell = cell,
        levels = levels,
        indexEvery = indexEvery,
        lineWidth = lineWidth,
        indexWidth = indexWidth,
        lineAlpha = lineAlpha,
        indexAlpha = indexAlpha,
        ink = ink,
        lineTint = lineTint,
        bandAlpha = bandAlpha,
        scale = scale,
        octaves = octaves,
        warp = warp,
        contrast = contrast,
        drift = drift,
        period = period,
    )

    override fun equals(other: Any?): Boolean = other is ContourArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ContourArtStyle(" +
        "colors=${colors ?: "null"}, " +
        "cell=$cell, " +
        "levels=$levels, " +
        "indexEvery=$indexEvery, " +
        "lineWidth=$lineWidth, " +
        "indexWidth=$indexWidth, " +
        "lineAlpha=$lineAlpha, " +
        "indexAlpha=$indexAlpha, " +
        "ink=${ink ?: "null"}, " +
        "lineTint=$lineTint, " +
        "bandAlpha=$bandAlpha, " +
        "scale=$scale, " +
        "octaves=$octaves, " +
        "warp=$warp, " +
        "contrast=$contrast, " +
        "drift=$drift, " +
        "period=$period)"

    private fun values(): List<Any?> = listOf(
        colors,
        cell,
        levels,
        indexEvery,
        lineWidth,
        indexWidth,
        lineAlpha,
        indexAlpha,
        ink,
        lineTint,
        bandAlpha,
        scale,
        octaves,
        warp,
        contrast,
        drift,
        period,
    )
}
