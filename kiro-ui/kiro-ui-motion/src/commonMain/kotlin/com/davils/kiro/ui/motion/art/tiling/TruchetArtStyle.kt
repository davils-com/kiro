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

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [TruchetArt] looks: a field of square tiles whose quarter arcs join into winding pipes, banded diagonally
 * through the colors, and a wave that sweeps across the field and turns tiles by a quarter, so that the maze keeps
 * rearranging.
 *
 * @property cell the size of a tile.
 * @property strokeWidth the width of a pipe.
 * @property cap the ends of a pipe, which show while a tile turns.
 * @property fillAlpha the alpha of the quarter discs behind the arcs, or of the half behind a diagonal.
 * @property fillQuality the lowest quality at which the quarter discs are drawn.
 * @property band how many diagonals of tiles share a color; at least `1`.
 * @property accentEvery every how many bands one carries an accent color; `1` colors every band, `0` none.
 * @property lineAlpha the alpha of the pipes in the bands without an accent.
 * @property accentAlpha the alpha of the pipes in the accent bands.
 * @property variant whether the tiles carry arcs, diagonal lines or both.
 * @property lineShare the share of tiles with a diagonal line in [TruchetVariant.Mixed].
 * @property flip the share of tiles turned away from their resting orientation in each wave, between `0` and `1`.
 * @property period the time between two waves.
 * @property lag how far the wave lags from one diagonal to the next, as a fraction of [period].
 * @property turn how much of [period] a turn takes, between `0` and `1`.
 * @property easing how a turn speeds up and slows down.
 * @property pointerRadius how far the pointer reaches.
 * @property pointerLead how much earlier the tiles under the pointer turn, as a fraction of [period].
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property line the color of the pipes outside the accent bands, or `null` for the ink of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class TruchetArtStyle(
    public val cell: Dp,
    public val strokeWidth: Dp,
    public val cap: StrokeCap,
    public val fillAlpha: Float,
    public val fillQuality: QualityLevel,
    public val band: Int,
    public val accentEvery: Int,
    public val lineAlpha: Float,
    public val accentAlpha: Float,
    public val variant: TruchetVariant,
    public val lineShare: Float,
    public val flip: Float,
    public val period: Duration,
    public val lag: Float,
    public val turn: Float,
    public val easing: Easing,
    public val pointerRadius: Dp,
    public val pointerLead: Float,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [TruchetArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param strokeWidth the new value of [strokeWidth].
     * @param cap the new value of [cap].
     * @param fillAlpha the new value of [fillAlpha].
     * @param fillQuality the new value of [fillQuality].
     * @param band the new value of [band].
     * @param accentEvery the new value of [accentEvery].
     * @param lineAlpha the new value of [lineAlpha].
     * @param accentAlpha the new value of [accentAlpha].
     * @param variant the new value of [variant].
     * @param lineShare the new value of [lineShare].
     * @param flip the new value of [flip].
     * @param period the new value of [period].
     * @param lag the new value of [lag].
     * @param turn the new value of [turn].
     * @param easing the new value of [easing].
     * @param pointerRadius the new value of [pointerRadius].
     * @param pointerLead the new value of [pointerLead].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        strokeWidth: Dp = this.strokeWidth,
        cap: StrokeCap = this.cap,
        fillAlpha: Float = this.fillAlpha,
        fillQuality: QualityLevel = this.fillQuality,
        band: Int = this.band,
        accentEvery: Int = this.accentEvery,
        lineAlpha: Float = this.lineAlpha,
        accentAlpha: Float = this.accentAlpha,
        variant: TruchetVariant = this.variant,
        lineShare: Float = this.lineShare,
        flip: Float = this.flip,
        period: Duration = this.period,
        lag: Float = this.lag,
        turn: Float = this.turn,
        easing: Easing = this.easing,
        pointerRadius: Dp = this.pointerRadius,
        pointerLead: Float = this.pointerLead,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        background: Color? = this.background,
    ): TruchetArtStyle = TruchetArtStyle(
        cell = cell,
        strokeWidth = strokeWidth,
        cap = cap,
        fillAlpha = fillAlpha,
        fillQuality = fillQuality,
        band = band,
        accentEvery = accentEvery,
        lineAlpha = lineAlpha,
        accentAlpha = accentAlpha,
        variant = variant,
        lineShare = lineShare,
        flip = flip,
        period = period,
        lag = lag,
        turn = turn,
        easing = easing,
        pointerRadius = pointerRadius,
        pointerLead = pointerLead,
        salt = salt,
        colors = colors,
        line = line,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is TruchetArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TruchetArtStyle(" +
        "cell=$cell, " +
        "strokeWidth=$strokeWidth, " +
        "cap=$cap, " +
        "fillAlpha=$fillAlpha, " +
        "fillQuality=$fillQuality, " +
        "band=$band, " +
        "accentEvery=$accentEvery, " +
        "lineAlpha=$lineAlpha, " +
        "accentAlpha=$accentAlpha, " +
        "variant=$variant, " +
        "lineShare=$lineShare, " +
        "flip=$flip, " +
        "period=$period, " +
        "lag=$lag, " +
        "turn=$turn, " +
        "easing=$easing, " +
        "pointerRadius=$pointerRadius, " +
        "pointerLead=$pointerLead, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        strokeWidth,
        cap,
        fillAlpha,
        fillQuality,
        band,
        accentEvery,
        lineAlpha,
        accentAlpha,
        variant,
        lineShare,
        flip,
        period,
        lag,
        turn,
        easing,
        pointerRadius,
        pointerLead,
        salt,
        colors,
        line,
        background,
    )
}
