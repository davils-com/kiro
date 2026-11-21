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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How an [IsometricCityArt] looks: a city of isometric blocks whose heights rise and fall in a wave that runs out
 * from an origin, colored by their height.
 *
 * @property tile the width of the footprint of a block.
 * @property inset the share of [tile] left free as a street around a block.
 * @property vacancy the share of lots left empty, as squares between the blocks.
 * @property minHeight the height of a block in the trough of the wave.
 * @property maxHeight the height of a block on the crest of the wave.
 * @property jitter how much of the height comes from the block itself rather than the wave, between `0` and `1`.
 * @property frequency how many crests of the wave fit into one tile, so that smaller values give wider waves.
 * @property period how long one crest takes to pass a block.
 * @property originX the horizontal position the wave runs out from, as a fraction of the width.
 * @property originY the vertical position the wave runs out from, as a fraction of the height.
 * @property tint how far the top of the highest block leans from [base] to its color; lower blocks lean less.
 * @property colorBias the power the share of its height is raised to before a block picks a color.
 * @property leftShade how far the shade darkens the left side of a block.
 * @property rightShade how far the shade darkens the right side of a block.
 * @property outlineWidth the width of the edges of a block.
 * @property outlineAlpha the alpha of the edges of a block; `0` hides them.
 * @property floor the height of a storey, whose row of windows the sides of a block show.
 * @property window the width of a window.
 * @property windowHeight the height of a window.
 * @property windowGap the space between two windows.
 * @property windowAlpha the alpha of the windows; `0` hides them.
 * @property detailQuality the lowest quality at which the windows and edges are drawn.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property shade the color that darkens the sides, or `null` for the shade of the palette.
 * @property base the color of the body of a block, or `null` for the background of the palette.
 * @property detail the color of the edges and windows, or `null` for the ink of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class IsometricCityArtStyle(
    public val tile: Dp,
    public val inset: Float,
    public val vacancy: Float,
    public val minHeight: Dp,
    public val maxHeight: Dp,
    public val jitter: Float,
    public val frequency: Float,
    public val period: Duration,
    public val originX: Float,
    public val originY: Float,
    public val tint: Float,
    public val colorBias: Float,
    public val leftShade: Float,
    public val rightShade: Float,
    public val outlineWidth: Dp,
    public val outlineAlpha: Float,
    public val floor: Dp,
    public val window: Dp,
    public val windowHeight: Dp,
    public val windowGap: Dp,
    public val windowAlpha: Float,
    public val detailQuality: QualityLevel,
    public val salt: Int,
    public val colors: List<Color>?,
    public val shade: Color?,
    public val base: Color?,
    public val detail: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [IsometricCityArtStyle] with the given values replaced and every other value kept.
     *
     * @param tile the new value of [tile].
     * @param inset the new value of [inset].
     * @param vacancy the new value of [vacancy].
     * @param minHeight the new value of [minHeight].
     * @param maxHeight the new value of [maxHeight].
     * @param jitter the new value of [jitter].
     * @param frequency the new value of [frequency].
     * @param period the new value of [period].
     * @param originX the new value of [originX].
     * @param originY the new value of [originY].
     * @param tint the new value of [tint].
     * @param colorBias the new value of [colorBias].
     * @param leftShade the new value of [leftShade].
     * @param rightShade the new value of [rightShade].
     * @param outlineWidth the new value of [outlineWidth].
     * @param outlineAlpha the new value of [outlineAlpha].
     * @param floor the new value of [floor].
     * @param window the new value of [window].
     * @param windowHeight the new value of [windowHeight].
     * @param windowGap the new value of [windowGap].
     * @param windowAlpha the new value of [windowAlpha].
     * @param detailQuality the new value of [detailQuality].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param shade the new value of [shade].
     * @param base the new value of [base].
     * @param detail the new value of [detail].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        tile: Dp = this.tile,
        inset: Float = this.inset,
        vacancy: Float = this.vacancy,
        minHeight: Dp = this.minHeight,
        maxHeight: Dp = this.maxHeight,
        jitter: Float = this.jitter,
        frequency: Float = this.frequency,
        period: Duration = this.period,
        originX: Float = this.originX,
        originY: Float = this.originY,
        tint: Float = this.tint,
        colorBias: Float = this.colorBias,
        leftShade: Float = this.leftShade,
        rightShade: Float = this.rightShade,
        outlineWidth: Dp = this.outlineWidth,
        outlineAlpha: Float = this.outlineAlpha,
        floor: Dp = this.floor,
        window: Dp = this.window,
        windowHeight: Dp = this.windowHeight,
        windowGap: Dp = this.windowGap,
        windowAlpha: Float = this.windowAlpha,
        detailQuality: QualityLevel = this.detailQuality,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        shade: Color? = this.shade,
        base: Color? = this.base,
        detail: Color? = this.detail,
        background: Color? = this.background,
    ): IsometricCityArtStyle = IsometricCityArtStyle(
        tile = tile,
        inset = inset,
        vacancy = vacancy,
        minHeight = minHeight,
        maxHeight = maxHeight,
        jitter = jitter,
        frequency = frequency,
        period = period,
        originX = originX,
        originY = originY,
        tint = tint,
        colorBias = colorBias,
        leftShade = leftShade,
        rightShade = rightShade,
        outlineWidth = outlineWidth,
        outlineAlpha = outlineAlpha,
        floor = floor,
        window = window,
        windowHeight = windowHeight,
        windowGap = windowGap,
        windowAlpha = windowAlpha,
        detailQuality = detailQuality,
        salt = salt,
        colors = colors,
        shade = shade,
        base = base,
        detail = detail,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is IsometricCityArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "IsometricCityArtStyle(" +
        "tile=$tile, " +
        "inset=$inset, " +
        "vacancy=$vacancy, " +
        "minHeight=$minHeight, " +
        "maxHeight=$maxHeight, " +
        "jitter=$jitter, " +
        "frequency=$frequency, " +
        "period=$period, " +
        "originX=$originX, " +
        "originY=$originY, " +
        "tint=$tint, " +
        "colorBias=$colorBias, " +
        "leftShade=$leftShade, " +
        "rightShade=$rightShade, " +
        "outlineWidth=$outlineWidth, " +
        "outlineAlpha=$outlineAlpha, " +
        "floor=$floor, " +
        "window=$window, " +
        "windowHeight=$windowHeight, " +
        "windowGap=$windowGap, " +
        "windowAlpha=$windowAlpha, " +
        "detailQuality=$detailQuality, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "shade=${shade ?: "null"}, " +
        "base=${base ?: "null"}, " +
        "detail=${detail ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        tile,
        inset,
        vacancy,
        minHeight,
        maxHeight,
        jitter,
        frequency,
        period,
        originX,
        originY,
        tint,
        colorBias,
        leftShade,
        rightShade,
        outlineWidth,
        outlineAlpha,
        floor,
        window,
        windowHeight,
        windowGap,
        windowAlpha,
        detailQuality,
        salt,
        colors,
        shade,
        base,
        detail,
        background,
    )
}
