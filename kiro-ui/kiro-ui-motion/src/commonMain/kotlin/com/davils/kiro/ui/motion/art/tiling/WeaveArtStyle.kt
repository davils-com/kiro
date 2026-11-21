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
 * How a [WeaveArt] looks: a lattice of hairlines in the manner of Japanese Kumiko and Sashiko, such as the hemp
 * leaf, the basket weave or the seven treasures, with a few softly tinted panels, along which a band of light
 * slowly travels.
 *
 * @property pattern the pattern of the lattice.
 * @property cell the size of a cell of the grid the pattern is laid on.
 * @property frameWidth the width of the lines of the grid.
 * @property frameAlpha the alpha of the lines of the grid.
 * @property lineWidth the width of the lines inside a cell.
 * @property lineAlpha the alpha of the lines inside a cell.
 * @property panelShare the share of cells that carry a tinted panel.
 * @property panelAlpha the alpha of a tinted panel.
 * @property arcSteps the number of straight pieces a curved line is drawn with.
 * @property band the width of a band of light.
 * @property bands the number of bands of light that travel at the same time, evenly apart.
 * @property angle the direction the bands of light travel in, in degrees.
 * @property highlightWidth the width of a lit line.
 * @property highlightAlpha the alpha of a line in the middle of the band of light.
 * @property glowWidth the width of the soft glow around a lit line.
 * @property glowAlpha the alpha of the soft glow around a lit line.
 * @property glowQuality the lowest quality that draws the glow.
 * @property period how long a band of light takes to cross the area.
 * @property easing the easing of the travel of a band of light.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property accent the color of the accent, or `null` for the last accent of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class WeaveArtStyle(
    public val pattern: WeavePattern,
    public val cell: Dp,
    public val frameWidth: Dp,
    public val frameAlpha: Float,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val panelShare: Float,
    public val panelAlpha: Float,
    public val arcSteps: Int,
    public val band: Dp,
    public val bands: Int,
    public val angle: Float,
    public val highlightWidth: Dp,
    public val highlightAlpha: Float,
    public val glowWidth: Dp,
    public val glowAlpha: Float,
    public val glowQuality: QualityLevel,
    public val period: Duration,
    public val easing: Easing,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val accent: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [WeaveArtStyle] with the given values replaced and every other value kept.
     *
     * @param pattern the new value of [pattern].
     * @param cell the new value of [cell].
     * @param frameWidth the new value of [frameWidth].
     * @param frameAlpha the new value of [frameAlpha].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param panelShare the new value of [panelShare].
     * @param panelAlpha the new value of [panelAlpha].
     * @param arcSteps the new value of [arcSteps].
     * @param band the new value of [band].
     * @param bands the new value of [bands].
     * @param angle the new value of [angle].
     * @param highlightWidth the new value of [highlightWidth].
     * @param highlightAlpha the new value of [highlightAlpha].
     * @param glowWidth the new value of [glowWidth].
     * @param glowAlpha the new value of [glowAlpha].
     * @param glowQuality the new value of [glowQuality].
     * @param period the new value of [period].
     * @param easing the new value of [easing].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param accent the new value of [accent].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        pattern: WeavePattern = this.pattern,
        cell: Dp = this.cell,
        frameWidth: Dp = this.frameWidth,
        frameAlpha: Float = this.frameAlpha,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        panelShare: Float = this.panelShare,
        panelAlpha: Float = this.panelAlpha,
        arcSteps: Int = this.arcSteps,
        band: Dp = this.band,
        bands: Int = this.bands,
        angle: Float = this.angle,
        highlightWidth: Dp = this.highlightWidth,
        highlightAlpha: Float = this.highlightAlpha,
        glowWidth: Dp = this.glowWidth,
        glowAlpha: Float = this.glowAlpha,
        glowQuality: QualityLevel = this.glowQuality,
        period: Duration = this.period,
        easing: Easing = this.easing,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        accent: Color? = this.accent,
        background: Color? = this.background,
    ): WeaveArtStyle = WeaveArtStyle(
        pattern = pattern,
        cell = cell,
        frameWidth = frameWidth,
        frameAlpha = frameAlpha,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        panelShare = panelShare,
        panelAlpha = panelAlpha,
        arcSteps = arcSteps,
        band = band,
        bands = bands,
        angle = angle,
        highlightWidth = highlightWidth,
        highlightAlpha = highlightAlpha,
        glowWidth = glowWidth,
        glowAlpha = glowAlpha,
        glowQuality = glowQuality,
        period = period,
        easing = easing,
        salt = salt,
        colors = colors,
        line = line,
        accent = accent,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is WeaveArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WeaveArtStyle(" +
        "pattern=$pattern, " +
        "cell=$cell, " +
        "frameWidth=$frameWidth, " +
        "frameAlpha=$frameAlpha, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "panelShare=$panelShare, " +
        "panelAlpha=$panelAlpha, " +
        "arcSteps=$arcSteps, " +
        "band=$band, " +
        "bands=$bands, " +
        "angle=$angle, " +
        "highlightWidth=$highlightWidth, " +
        "highlightAlpha=$highlightAlpha, " +
        "glowWidth=$glowWidth, " +
        "glowAlpha=$glowAlpha, " +
        "glowQuality=$glowQuality, " +
        "period=$period, " +
        "easing=$easing, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "accent=${accent ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        pattern,
        cell,
        frameWidth,
        frameAlpha,
        lineWidth,
        lineAlpha,
        panelShare,
        panelAlpha,
        arcSteps,
        band,
        bands,
        angle,
        highlightWidth,
        highlightAlpha,
        glowWidth,
        glowAlpha,
        glowQuality,
        period,
        easing,
        salt,
        colors,
        line,
        accent,
        background,
    )
}
