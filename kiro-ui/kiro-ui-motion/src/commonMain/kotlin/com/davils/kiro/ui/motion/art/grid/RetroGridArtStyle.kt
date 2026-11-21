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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [RetroGridArt] looks: a floor of hairlines in perspective that flows slowly towards the viewer and fades into
 * a soft glow at the horizon, with a few pulses of light that travel along its lanes.
 *
 * @property horizon the height of the horizon, as a fraction of the height from the top.
 * @property cell the width of a lane at the bottom edge.
 * @property depth the depth of a row, relative to the distance of the bottom edge; smaller values give flatter rows.
 * @property lineWidth the width of a line.
 * @property lineAlpha the alpha of the lines close to the viewer.
 * @property fade how far below the horizon the lines reach their full alpha, as a fraction of the floor.
 * @property maxRows the most rows drawn at the highest quality.
 * @property period how long the floor takes to move by one row.
 * @property glowHeight the height of the glow on each side of the horizon, as a fraction of the height.
 * @property glowAlpha the alpha of the glow at the horizon; `0` hides it.
 * @property pulses how many pulses of light travel at the same time at the highest quality.
 * @property pulseLength the length of a pulse, as a fraction of the floor.
 * @property pulseWidth the width of a pulse.
 * @property pulseAlpha the alpha of a pulse at its brightest.
 * @property pulsePeriod how long a pulse takes from the horizon to the bottom edge.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property line the color of the lines, or `null` for the ink of the palette.
 * @property colors the colors of the pulses, used in turn, or `null` for the accents of the palette of the frame.
 * @property glow the color of the glow, or `null` for the highlight of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class RetroGridArtStyle(
    public val horizon: Float,
    public val cell: Dp,
    public val depth: Float,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val fade: Float,
    public val maxRows: Int,
    public val period: Duration,
    public val glowHeight: Float,
    public val glowAlpha: Float,
    public val pulses: Int,
    public val pulseLength: Float,
    public val pulseWidth: Dp,
    public val pulseAlpha: Float,
    public val pulsePeriod: Duration,
    public val salt: Int,
    public val line: Color?,
    public val colors: List<Color>?,
    public val glow: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [RetroGridArtStyle] with the given values replaced and every other value kept.
     *
     * @param horizon the new value of [horizon].
     * @param cell the new value of [cell].
     * @param depth the new value of [depth].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param fade the new value of [fade].
     * @param maxRows the new value of [maxRows].
     * @param period the new value of [period].
     * @param glowHeight the new value of [glowHeight].
     * @param glowAlpha the new value of [glowAlpha].
     * @param pulses the new value of [pulses].
     * @param pulseLength the new value of [pulseLength].
     * @param pulseWidth the new value of [pulseWidth].
     * @param pulseAlpha the new value of [pulseAlpha].
     * @param pulsePeriod the new value of [pulsePeriod].
     * @param salt the new value of [salt].
     * @param line the new value of [line].
     * @param colors the new value of [colors].
     * @param glow the new value of [glow].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        horizon: Float = this.horizon,
        cell: Dp = this.cell,
        depth: Float = this.depth,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        fade: Float = this.fade,
        maxRows: Int = this.maxRows,
        period: Duration = this.period,
        glowHeight: Float = this.glowHeight,
        glowAlpha: Float = this.glowAlpha,
        pulses: Int = this.pulses,
        pulseLength: Float = this.pulseLength,
        pulseWidth: Dp = this.pulseWidth,
        pulseAlpha: Float = this.pulseAlpha,
        pulsePeriod: Duration = this.pulsePeriod,
        salt: Int = this.salt,
        line: Color? = this.line,
        colors: List<Color>? = this.colors,
        glow: Color? = this.glow,
        background: Color? = this.background,
    ): RetroGridArtStyle = RetroGridArtStyle(
        horizon = horizon,
        cell = cell,
        depth = depth,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        fade = fade,
        maxRows = maxRows,
        period = period,
        glowHeight = glowHeight,
        glowAlpha = glowAlpha,
        pulses = pulses,
        pulseLength = pulseLength,
        pulseWidth = pulseWidth,
        pulseAlpha = pulseAlpha,
        pulsePeriod = pulsePeriod,
        salt = salt,
        line = line,
        colors = colors,
        glow = glow,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is RetroGridArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RetroGridArtStyle(" +
        "horizon=$horizon, " +
        "cell=$cell, " +
        "depth=$depth, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "fade=$fade, " +
        "maxRows=$maxRows, " +
        "period=$period, " +
        "glowHeight=$glowHeight, " +
        "glowAlpha=$glowAlpha, " +
        "pulses=$pulses, " +
        "pulseLength=$pulseLength, " +
        "pulseWidth=$pulseWidth, " +
        "pulseAlpha=$pulseAlpha, " +
        "pulsePeriod=$pulsePeriod, " +
        "salt=$salt, " +
        "line=${line ?: "null"}, " +
        "colors=${colors ?: "null"}, " +
        "glow=${glow ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        horizon,
        cell,
        depth,
        lineWidth,
        lineAlpha,
        fade,
        maxRows,
        period,
        glowHeight,
        glowAlpha,
        pulses,
        pulseLength,
        pulseWidth,
        pulseAlpha,
        pulsePeriod,
        salt,
        line,
        colors,
        glow,
        background,
    )
}
