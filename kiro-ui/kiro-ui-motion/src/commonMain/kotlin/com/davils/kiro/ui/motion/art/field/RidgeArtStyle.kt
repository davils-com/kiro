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
 * How a [RidgeArt] looks: the number and the width of its lines, the shape of its ridges, how they move and how
 * they are colored.
 *
 * @property colors the colors the lines run through, or `null` for the accents of the palette of the frame.
 * @property background the color that fills the space below a line and hides the lines behind it, or `null` for the
 * background of the palette of the frame.
 * @property lines the number of lines.
 * @property lineWidth the width of a line.
 * @property lineAlpha the alpha of a line.
 * @property step the horizontal distance between two points of a line at the highest quality, which the line joins with
 *   a smooth curve.
 * @property amplitude the height of the highest ridge.
 * @property spread the width of the bell that raises the ridges toward the middle, as a fraction of the width.
 * @property wander how far the middle of the bell of a line strays from the middle, as a fraction of the width.
 * @property flat the height of the flat parts outside the bell, as a fraction of [amplitude].
 * @property frequency how many bumps a line has across its width.
 * @property octaves the number of layers of detail of the bumps.
 * @property sharpness how pointed the bumps are; `1` keeps them round, higher values make sharper peaks.
 * @property top where the line at the back rests, as a fraction of the height.
 * @property bottom where the line at the front rests, as a fraction of the height.
 * @property period how long the signal takes to move on by one bump.
 * @property evolution how long the bumps take to change their shape completely.
 * @property gradient the direction in which the colors run over the lines.
 * @property hasFill whether a line fills the space below it, so that it hides the lines behind it.
 * @since 1.0.0
 */
@Immutable
public class RidgeArtStyle(
    public val colors: List<Color>?,
    public val background: Color?,
    public val lines: Int,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val step: Dp,
    public val amplitude: Dp,
    public val spread: Float,
    public val wander: Float,
    public val flat: Float,
    public val frequency: Float,
    public val octaves: Int,
    public val sharpness: Float,
    public val top: Float,
    public val bottom: Float,
    public val period: Duration,
    public val evolution: Duration,
    public val gradient: RidgeGradient,
    public val hasFill: Boolean,
) {
    /**
     * Returns a copy of this [RidgeArtStyle] with the given values replaced and every other value kept.
     *
     * @param colors the new value of [colors].
     * @param background the new value of [background].
     * @param lines the new value of [lines].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param step the new value of [step].
     * @param amplitude the new value of [amplitude].
     * @param spread the new value of [spread].
     * @param wander the new value of [wander].
     * @param flat the new value of [flat].
     * @param frequency the new value of [frequency].
     * @param octaves the new value of [octaves].
     * @param sharpness the new value of [sharpness].
     * @param top the new value of [top].
     * @param bottom the new value of [bottom].
     * @param period the new value of [period].
     * @param evolution the new value of [evolution].
     * @param gradient the new value of [gradient].
     * @param hasFill the new value of [hasFill].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        colors: List<Color>? = this.colors,
        background: Color? = this.background,
        lines: Int = this.lines,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        step: Dp = this.step,
        amplitude: Dp = this.amplitude,
        spread: Float = this.spread,
        wander: Float = this.wander,
        flat: Float = this.flat,
        frequency: Float = this.frequency,
        octaves: Int = this.octaves,
        sharpness: Float = this.sharpness,
        top: Float = this.top,
        bottom: Float = this.bottom,
        period: Duration = this.period,
        evolution: Duration = this.evolution,
        gradient: RidgeGradient = this.gradient,
        hasFill: Boolean = this.hasFill,
    ): RidgeArtStyle = RidgeArtStyle(
        colors = colors,
        background = background,
        lines = lines,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        step = step,
        amplitude = amplitude,
        spread = spread,
        wander = wander,
        flat = flat,
        frequency = frequency,
        octaves = octaves,
        sharpness = sharpness,
        top = top,
        bottom = bottom,
        period = period,
        evolution = evolution,
        gradient = gradient,
        hasFill = hasFill,
    )

    override fun equals(other: Any?): Boolean = other is RidgeArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RidgeArtStyle(" +
        "colors=${colors ?: "null"}, " +
        "background=${background ?: "null"}, " +
        "lines=$lines, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "step=$step, " +
        "amplitude=$amplitude, " +
        "spread=$spread, " +
        "wander=$wander, " +
        "flat=$flat, " +
        "frequency=$frequency, " +
        "octaves=$octaves, " +
        "sharpness=$sharpness, " +
        "top=$top, " +
        "bottom=$bottom, " +
        "period=$period, " +
        "evolution=$evolution, " +
        "gradient=$gradient, " +
        "hasFill=$hasFill)"

    private fun values(): List<Any?> = listOf(
        colors,
        background,
        lines,
        lineWidth,
        lineAlpha,
        step,
        amplitude,
        spread,
        wander,
        flat,
        frequency,
        octaves,
        sharpness,
        top,
        bottom,
        period,
        evolution,
        gradient,
        hasFill,
    )
}
