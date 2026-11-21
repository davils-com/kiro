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
import kotlin.time.Duration

/**
 * How a [LissajousArt] looks: lissajous figures with damping, as a harmonograph draws them: curves that spiral
 * slowly inwards, draw themselves with a pen, fade back into the faint curve beneath and slowly change their shape.
 *
 * @property curves the number of curves.
 * @property centerX the horizontal position of the center, as a fraction of the width.
 * @property centerY the vertical position of the center, as a fraction of the height.
 * @property size the vertical reach of a curve, as a fraction of the shorter side of the area.
 * @property stretch how much farther a curve reaches sideways than up and down.
 * @property minFrequency the lowest frequency of the horizontal swing.
 * @property maxFrequency the highest frequency of the horizontal swing; the vertical one runs at the same or the next.
 * @property detune how much faster the vertical swing runs than its frequency, which lets a figure turn as it fades.
 * @property twist how far the paper turns beneath the pen while a curve is drawn, in turns.
 * @property damping how strongly the swings die down: the end of a curve reaches e to the minus this of its start.
 * @property turns how many swings of the frequency 1 a curve lasts.
 * @property spread how far the swing of a curve lags behind the one before it, as a fraction of a swing.
 * @property steps the number of straight pieces a curve is drawn with at the highest quality.
 * @property lineWidth the width of the curves.
 * @property lineAlpha the alpha of the part the pen has drawn.
 * @property faintAlpha the alpha of the faint curve beneath.
 * @property head the diameter of the pen.
 * @property headGlow the diameter of the glow around the pen.
 * @property glowAlpha the alpha of the glow around the pen.
 * @property guideAlpha the alpha of the axes through the center, which never move.
 * @property drawPeriod how long a curve takes to draw itself and fade.
 * @property drawShare how much of [drawPeriod] the pen draws, before the drawing fades.
 * @property driftPeriod how long a curve takes to run through all its shapes.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property pen the color of the pen, or `null` for the color of its curve.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class LissajousArtStyle(
    public val curves: Int,
    public val centerX: Float,
    public val centerY: Float,
    public val size: Float,
    public val stretch: Float,
    public val minFrequency: Int,
    public val maxFrequency: Int,
    public val detune: Float,
    public val twist: Float,
    public val damping: Float,
    public val turns: Float,
    public val spread: Float,
    public val steps: Int,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val faintAlpha: Float,
    public val head: Dp,
    public val headGlow: Dp,
    public val glowAlpha: Float,
    public val guideAlpha: Float,
    public val drawPeriod: Duration,
    public val drawShare: Float,
    public val driftPeriod: Duration,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val pen: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [LissajousArtStyle] with the given values replaced and every other value kept.
     *
     * @param curves the new value of [curves].
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param size the new value of [size].
     * @param stretch the new value of [stretch].
     * @param minFrequency the new value of [minFrequency].
     * @param maxFrequency the new value of [maxFrequency].
     * @param detune the new value of [detune].
     * @param twist the new value of [twist].
     * @param damping the new value of [damping].
     * @param turns the new value of [turns].
     * @param spread the new value of [spread].
     * @param steps the new value of [steps].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param faintAlpha the new value of [faintAlpha].
     * @param head the new value of [head].
     * @param headGlow the new value of [headGlow].
     * @param glowAlpha the new value of [glowAlpha].
     * @param guideAlpha the new value of [guideAlpha].
     * @param drawPeriod the new value of [drawPeriod].
     * @param drawShare the new value of [drawShare].
     * @param driftPeriod the new value of [driftPeriod].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param pen the new value of [pen].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        curves: Int = this.curves,
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        size: Float = this.size,
        stretch: Float = this.stretch,
        minFrequency: Int = this.minFrequency,
        maxFrequency: Int = this.maxFrequency,
        detune: Float = this.detune,
        twist: Float = this.twist,
        damping: Float = this.damping,
        turns: Float = this.turns,
        spread: Float = this.spread,
        steps: Int = this.steps,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        faintAlpha: Float = this.faintAlpha,
        head: Dp = this.head,
        headGlow: Dp = this.headGlow,
        glowAlpha: Float = this.glowAlpha,
        guideAlpha: Float = this.guideAlpha,
        drawPeriod: Duration = this.drawPeriod,
        drawShare: Float = this.drawShare,
        driftPeriod: Duration = this.driftPeriod,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        pen: Color? = this.pen,
        background: Color? = this.background,
    ): LissajousArtStyle = LissajousArtStyle(
        curves = curves,
        centerX = centerX,
        centerY = centerY,
        size = size,
        stretch = stretch,
        minFrequency = minFrequency,
        maxFrequency = maxFrequency,
        detune = detune,
        twist = twist,
        damping = damping,
        turns = turns,
        spread = spread,
        steps = steps,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        faintAlpha = faintAlpha,
        head = head,
        headGlow = headGlow,
        glowAlpha = glowAlpha,
        guideAlpha = guideAlpha,
        drawPeriod = drawPeriod,
        drawShare = drawShare,
        driftPeriod = driftPeriod,
        salt = salt,
        colors = colors,
        line = line,
        pen = pen,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is LissajousArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "LissajousArtStyle(" +
        "curves=$curves, " +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "size=$size, " +
        "stretch=$stretch, " +
        "minFrequency=$minFrequency, " +
        "maxFrequency=$maxFrequency, " +
        "detune=$detune, " +
        "twist=$twist, " +
        "damping=$damping, " +
        "turns=$turns, " +
        "spread=$spread, " +
        "steps=$steps, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "faintAlpha=$faintAlpha, " +
        "head=$head, " +
        "headGlow=$headGlow, " +
        "glowAlpha=$glowAlpha, " +
        "guideAlpha=$guideAlpha, " +
        "drawPeriod=$drawPeriod, " +
        "drawShare=$drawShare, " +
        "driftPeriod=$driftPeriod, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "pen=${pen ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        curves,
        centerX,
        centerY,
        size,
        stretch,
        minFrequency,
        maxFrequency,
        detune,
        twist,
        damping,
        turns,
        spread,
        steps,
        lineWidth,
        lineAlpha,
        faintAlpha,
        head,
        headGlow,
        glowAlpha,
        guideAlpha,
        drawPeriod,
        drawShare,
        driftPeriod,
        salt,
        colors,
        line,
        pen,
        background,
    )
}
