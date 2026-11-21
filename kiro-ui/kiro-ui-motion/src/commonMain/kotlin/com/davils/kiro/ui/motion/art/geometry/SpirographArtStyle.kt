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
 * How a [SpirographArt] looks: hypotrochoids and epitrochoids, the curves of a spirograph, that draw themselves with
 * a bright pen, fade back into the faint curve beneath and slowly turn.
 *
 * @property curves the number of curves.
 * @property centerX the horizontal position of the center, as a fraction of the width.
 * @property centerY the vertical position of the center, as a fraction of the height.
 * @property size the radius of the largest curve, as a fraction of half the shorter side.
 * @property minSize the radius of the smallest curve, as a fraction of [size].
 * @property minRoller the smallest radius of the rolling wheel, as a fraction of the fixed one.
 * @property maxRoller the largest radius of the rolling wheel, as a fraction of the fixed one.
 * @property minPen the smallest distance of the pen from the center of the wheel, as a fraction of its radius.
 * @property maxPen the largest distance of the pen from the center of the wheel, as a fraction of its radius.
 * @property loops the largest number of rounds the wheel rolls before a curve closes, which bounds its detail.
 * @property epicycles the share of curves whose wheel rolls outside the fixed one.
 * @property steps the number of points of one round of the wheel at the highest quality.
 * @property lineWidth the width of a curve.
 * @property lineAlpha the alpha of the part of a curve the pen has just drawn.
 * @property drawPeriod how long a curve takes to draw itself and fade back.
 * @property drawShare how much of [drawPeriod] the drawing takes; the rest fades back.
 * @property rotationPeriod how long a curve takes for one turn; every other curve turns the other way.
 * @property headSize the diameter of the pen.
 * @property headGlow the diameter of the glow around the pen.
 * @property glowAlpha the alpha of the glow around the pen.
 * @property trailAlpha the alpha of the faint curve beneath.
 * @property echoes the number of fainter copies of a curve that trail behind it.
 * @property echoAngle how far each copy trails behind, in degrees.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property head the color of the pen, or `null` for the highlight of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class SpirographArtStyle(
    public val curves: Int,
    public val centerX: Float,
    public val centerY: Float,
    public val size: Float,
    public val minSize: Float,
    public val minRoller: Float,
    public val maxRoller: Float,
    public val minPen: Float,
    public val maxPen: Float,
    public val loops: Int,
    public val epicycles: Float,
    public val steps: Int,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val drawPeriod: Duration,
    public val drawShare: Float,
    public val rotationPeriod: Duration,
    public val headSize: Dp,
    public val headGlow: Dp,
    public val glowAlpha: Float,
    public val trailAlpha: Float,
    public val echoes: Int,
    public val echoAngle: Float,
    public val salt: Int,
    public val colors: List<Color>?,
    public val head: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [SpirographArtStyle] with the given values replaced and every other value kept.
     *
     * @param curves the new value of [curves].
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param size the new value of [size].
     * @param minSize the new value of [minSize].
     * @param minRoller the new value of [minRoller].
     * @param maxRoller the new value of [maxRoller].
     * @param minPen the new value of [minPen].
     * @param maxPen the new value of [maxPen].
     * @param loops the new value of [loops].
     * @param epicycles the new value of [epicycles].
     * @param steps the new value of [steps].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param drawPeriod the new value of [drawPeriod].
     * @param drawShare the new value of [drawShare].
     * @param rotationPeriod the new value of [rotationPeriod].
     * @param headSize the new value of [headSize].
     * @param headGlow the new value of [headGlow].
     * @param glowAlpha the new value of [glowAlpha].
     * @param trailAlpha the new value of [trailAlpha].
     * @param echoes the new value of [echoes].
     * @param echoAngle the new value of [echoAngle].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param head the new value of [head].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        curves: Int = this.curves,
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        size: Float = this.size,
        minSize: Float = this.minSize,
        minRoller: Float = this.minRoller,
        maxRoller: Float = this.maxRoller,
        minPen: Float = this.minPen,
        maxPen: Float = this.maxPen,
        loops: Int = this.loops,
        epicycles: Float = this.epicycles,
        steps: Int = this.steps,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        drawPeriod: Duration = this.drawPeriod,
        drawShare: Float = this.drawShare,
        rotationPeriod: Duration = this.rotationPeriod,
        headSize: Dp = this.headSize,
        headGlow: Dp = this.headGlow,
        glowAlpha: Float = this.glowAlpha,
        trailAlpha: Float = this.trailAlpha,
        echoes: Int = this.echoes,
        echoAngle: Float = this.echoAngle,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        head: Color? = this.head,
        background: Color? = this.background,
    ): SpirographArtStyle = SpirographArtStyle(
        curves = curves,
        centerX = centerX,
        centerY = centerY,
        size = size,
        minSize = minSize,
        minRoller = minRoller,
        maxRoller = maxRoller,
        minPen = minPen,
        maxPen = maxPen,
        loops = loops,
        epicycles = epicycles,
        steps = steps,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        drawPeriod = drawPeriod,
        drawShare = drawShare,
        rotationPeriod = rotationPeriod,
        headSize = headSize,
        headGlow = headGlow,
        glowAlpha = glowAlpha,
        trailAlpha = trailAlpha,
        echoes = echoes,
        echoAngle = echoAngle,
        salt = salt,
        colors = colors,
        head = head,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is SpirographArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SpirographArtStyle(" +
        "curves=$curves, " +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "size=$size, " +
        "minSize=$minSize, " +
        "minRoller=$minRoller, " +
        "maxRoller=$maxRoller, " +
        "minPen=$minPen, " +
        "maxPen=$maxPen, " +
        "loops=$loops, " +
        "epicycles=$epicycles, " +
        "steps=$steps, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "drawPeriod=$drawPeriod, " +
        "drawShare=$drawShare, " +
        "rotationPeriod=$rotationPeriod, " +
        "headSize=$headSize, " +
        "headGlow=$headGlow, " +
        "glowAlpha=$glowAlpha, " +
        "trailAlpha=$trailAlpha, " +
        "echoes=$echoes, " +
        "echoAngle=$echoAngle, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "head=${head ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        curves,
        centerX,
        centerY,
        size,
        minSize,
        minRoller,
        maxRoller,
        minPen,
        maxPen,
        loops,
        epicycles,
        steps,
        lineWidth,
        lineAlpha,
        drawPeriod,
        drawShare,
        rotationPeriod,
        headSize,
        headGlow,
        glowAlpha,
        trailAlpha,
        echoes,
        echoAngle,
        salt,
        colors,
        head,
        background,
    )
}
