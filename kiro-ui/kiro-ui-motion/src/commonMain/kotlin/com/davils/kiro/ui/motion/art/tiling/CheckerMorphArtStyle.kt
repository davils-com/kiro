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
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [CheckerMorphArt] looks: a checkerboard whose squares round into circles and back in a slow diagonal wave,
 * taking on the color of the accents at the crest, over a fine grid.
 *
 * @property cell the size of a square of the board.
 * @property inset the space around a shape, as a fraction of a square.
 * @property circle the size of a circle at the crest, as a fraction of the size of its square.
 * @property fillAlpha the alpha of a square at rest.
 * @property accentAlpha the alpha of the accent at the crest.
 * @property gridWidth the width of the lines of the grid.
 * @property gridAlpha the alpha of the lines of the grid, which never move.
 * @property outlineAlpha the alpha of the outline of a shape at the crest.
 * @property outlineQuality the lowest quality that draws the outlines.
 * @property band how many diagonals share one accent.
 * @property lag how far one diagonal lags behind the one before it, as a fraction of [period].
 * @property jitter how far a square lags behind its diagonal at most, as a fraction of [period].
 * @property morph how much of [period] a square takes to become a circle and back.
 * @property period the time between two waves.
 * @property easing the easing of a morph.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class CheckerMorphArtStyle(
    public val cell: Dp,
    public val inset: Float,
    public val circle: Float,
    public val fillAlpha: Float,
    public val accentAlpha: Float,
    public val gridWidth: Dp,
    public val gridAlpha: Float,
    public val outlineAlpha: Float,
    public val outlineQuality: QualityLevel,
    public val band: Int,
    public val lag: Float,
    public val jitter: Float,
    public val morph: Float,
    public val period: Duration,
    public val easing: Easing,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [CheckerMorphArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param inset the new value of [inset].
     * @param circle the new value of [circle].
     * @param fillAlpha the new value of [fillAlpha].
     * @param accentAlpha the new value of [accentAlpha].
     * @param gridWidth the new value of [gridWidth].
     * @param gridAlpha the new value of [gridAlpha].
     * @param outlineAlpha the new value of [outlineAlpha].
     * @param outlineQuality the new value of [outlineQuality].
     * @param band the new value of [band].
     * @param lag the new value of [lag].
     * @param jitter the new value of [jitter].
     * @param morph the new value of [morph].
     * @param period the new value of [period].
     * @param easing the new value of [easing].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        inset: Float = this.inset,
        circle: Float = this.circle,
        fillAlpha: Float = this.fillAlpha,
        accentAlpha: Float = this.accentAlpha,
        gridWidth: Dp = this.gridWidth,
        gridAlpha: Float = this.gridAlpha,
        outlineAlpha: Float = this.outlineAlpha,
        outlineQuality: QualityLevel = this.outlineQuality,
        band: Int = this.band,
        lag: Float = this.lag,
        jitter: Float = this.jitter,
        morph: Float = this.morph,
        period: Duration = this.period,
        easing: Easing = this.easing,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        background: Color? = this.background,
    ): CheckerMorphArtStyle = CheckerMorphArtStyle(
        cell = cell,
        inset = inset,
        circle = circle,
        fillAlpha = fillAlpha,
        accentAlpha = accentAlpha,
        gridWidth = gridWidth,
        gridAlpha = gridAlpha,
        outlineAlpha = outlineAlpha,
        outlineQuality = outlineQuality,
        band = band,
        lag = lag,
        jitter = jitter,
        morph = morph,
        period = period,
        easing = easing,
        salt = salt,
        colors = colors,
        line = line,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is CheckerMorphArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CheckerMorphArtStyle(" +
        "cell=$cell, " +
        "inset=$inset, " +
        "circle=$circle, " +
        "fillAlpha=$fillAlpha, " +
        "accentAlpha=$accentAlpha, " +
        "gridWidth=$gridWidth, " +
        "gridAlpha=$gridAlpha, " +
        "outlineAlpha=$outlineAlpha, " +
        "outlineQuality=$outlineQuality, " +
        "band=$band, " +
        "lag=$lag, " +
        "jitter=$jitter, " +
        "morph=$morph, " +
        "period=$period, " +
        "easing=$easing, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        inset,
        circle,
        fillAlpha,
        accentAlpha,
        gridWidth,
        gridAlpha,
        outlineAlpha,
        outlineQuality,
        band,
        lag,
        jitter,
        morph,
        period,
        easing,
        salt,
        colors,
        line,
        background,
    )
}
