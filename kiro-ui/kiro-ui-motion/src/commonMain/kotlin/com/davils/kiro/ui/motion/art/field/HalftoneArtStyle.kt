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
 * How a [HalftoneArt] looks: its grid and dots, the waves that size them and how they react to a pointer.
 *
 * @property colors the colors of the gradient across the dots, or `null` for the accents of the palette of the frame.
 * @property pitch the distance between two dots.
 * @property maxDot the size of the largest dot, as a fraction of [pitch].
 * @property minDot the size below which a dot is not drawn, as a fraction of [pitch].
 * @property alpha the alpha of the dots.
 * @property pattern how the dots are arranged.
 * @property shape the shape of a dot.
 * @property angle the angle of the grid, in degrees.
 * @property gradientAngle the direction of the gradient of colors, in degrees.
 * @property waves the number of waves that interfere.
 * @property wavelengths the distance between two crests of each wave, used in turn.
 * @property speeds how many crests of each wave pass a dot in one [period], used in turn.
 * @property orbit how far the centers of the waves wander, as a fraction of the size.
 * @property period the time the speeds of the waves refer to.
 * @property wanderPeriod how long the center of a wave takes for one loop of its wandering.
 * @property pointerStrength how strongly the ripple of a pointer swells the dots, `0` for not at all.
 * @property pointerRadius how far the ripple of a pointer reaches.
 * @property pointerWavelength the distance between two crests of the ripple of a pointer.
 * @since 1.0.0
 */
@Immutable
public class HalftoneArtStyle(
    public val colors: List<Color>?,
    public val pitch: Dp,
    public val maxDot: Float,
    public val minDot: Float,
    public val alpha: Float,
    public val pattern: HalftonePattern,
    public val shape: HalftoneShape,
    public val angle: Float,
    public val gradientAngle: Float,
    public val waves: Int,
    public val wavelengths: List<Dp>,
    public val speeds: List<Float>,
    public val orbit: Float,
    public val period: Duration,
    public val wanderPeriod: Duration,
    public val pointerStrength: Float,
    public val pointerRadius: Dp,
    public val pointerWavelength: Dp,
) {
    /**
     * Returns a copy of this [HalftoneArtStyle] with the given values replaced and every other value kept.
     *
     * @param colors the new value of [colors].
     * @param pitch the new value of [pitch].
     * @param maxDot the new value of [maxDot].
     * @param minDot the new value of [minDot].
     * @param alpha the new value of [alpha].
     * @param pattern the new value of [pattern].
     * @param shape the new value of [shape].
     * @param angle the new value of [angle].
     * @param gradientAngle the new value of [gradientAngle].
     * @param waves the new value of [waves].
     * @param wavelengths the new value of [wavelengths].
     * @param speeds the new value of [speeds].
     * @param orbit the new value of [orbit].
     * @param period the new value of [period].
     * @param wanderPeriod the new value of [wanderPeriod].
     * @param pointerStrength the new value of [pointerStrength].
     * @param pointerRadius the new value of [pointerRadius].
     * @param pointerWavelength the new value of [pointerWavelength].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        colors: List<Color>? = this.colors,
        pitch: Dp = this.pitch,
        maxDot: Float = this.maxDot,
        minDot: Float = this.minDot,
        alpha: Float = this.alpha,
        pattern: HalftonePattern = this.pattern,
        shape: HalftoneShape = this.shape,
        angle: Float = this.angle,
        gradientAngle: Float = this.gradientAngle,
        waves: Int = this.waves,
        wavelengths: List<Dp> = this.wavelengths,
        speeds: List<Float> = this.speeds,
        orbit: Float = this.orbit,
        period: Duration = this.period,
        wanderPeriod: Duration = this.wanderPeriod,
        pointerStrength: Float = this.pointerStrength,
        pointerRadius: Dp = this.pointerRadius,
        pointerWavelength: Dp = this.pointerWavelength,
    ): HalftoneArtStyle = HalftoneArtStyle(
        colors = colors,
        pitch = pitch,
        maxDot = maxDot,
        minDot = minDot,
        alpha = alpha,
        pattern = pattern,
        shape = shape,
        angle = angle,
        gradientAngle = gradientAngle,
        waves = waves,
        wavelengths = wavelengths,
        speeds = speeds,
        orbit = orbit,
        period = period,
        wanderPeriod = wanderPeriod,
        pointerStrength = pointerStrength,
        pointerRadius = pointerRadius,
        pointerWavelength = pointerWavelength,
    )

    override fun equals(other: Any?): Boolean = other is HalftoneArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "HalftoneArtStyle(" +
        "colors=${colors ?: "null"}, " +
        "pitch=$pitch, " +
        "maxDot=$maxDot, " +
        "minDot=$minDot, " +
        "alpha=$alpha, " +
        "pattern=$pattern, " +
        "shape=$shape, " +
        "angle=$angle, " +
        "gradientAngle=$gradientAngle, " +
        "waves=$waves, " +
        "wavelengths=$wavelengths, " +
        "speeds=$speeds, " +
        "orbit=$orbit, " +
        "period=$period, " +
        "wanderPeriod=$wanderPeriod, " +
        "pointerStrength=$pointerStrength, " +
        "pointerRadius=$pointerRadius, " +
        "pointerWavelength=$pointerWavelength)"

    private fun values(): List<Any?> = listOf(
        colors,
        pitch,
        maxDot,
        minDot,
        alpha,
        pattern,
        shape,
        angle,
        gradientAngle,
        waves,
        wavelengths,
        speeds,
        orbit,
        period,
        wanderPeriod,
        pointerStrength,
        pointerRadius,
        pointerWavelength,
    )
}
