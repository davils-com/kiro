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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How an [AuroraArt] looks: a few translucent bands of light across the sky that sway, ripple and shift their colors.
 *
 * @property bands the number of bands.
 * @property segments the number of pieces one [wavelength] of a band is drawn with at the highest quality.
 * @property top the height of the brightest line of the first band, as a fraction of the height.
 * @property gap the distance between the brightest lines of two bands, as a fraction of the height.
 * @property thickness the height of a band.
 * @property lift the part of a band that rises above its brightest line, between `0` and `1`.
 * @property amplitude how far a band sways up and down.
 * @property wavelength the length of one sway along a band.
 * @property period how long one sway takes to pass along a band.
 * @property ripple how much the brightness and height of a band vary along it, between `0` and `1`.
 * @property alpha the alpha of the brightest line of a band.
 * @property blendMode how the bands combine with what lies below them.
 * @property colors the colors along the bands, or `null` for the accents of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class AuroraArtStyle(
    public val bands: Int,
    public val segments: Int,
    public val top: Float,
    public val gap: Float,
    public val thickness: Dp,
    public val lift: Float,
    public val amplitude: Dp,
    public val wavelength: Dp,
    public val period: Duration,
    public val ripple: Float,
    public val alpha: Float,
    public val blendMode: BlendMode,
    public val colors: List<Color>?,
) {
    /**
     * Returns a copy of this [AuroraArtStyle] with the given values replaced and every other value kept.
     *
     * @param bands the new value of [bands].
     * @param segments the new value of [segments].
     * @param top the new value of [top].
     * @param gap the new value of [gap].
     * @param thickness the new value of [thickness].
     * @param lift the new value of [lift].
     * @param amplitude the new value of [amplitude].
     * @param wavelength the new value of [wavelength].
     * @param period the new value of [period].
     * @param ripple the new value of [ripple].
     * @param alpha the new value of [alpha].
     * @param blendMode the new value of [blendMode].
     * @param colors the new value of [colors].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        bands: Int = this.bands,
        segments: Int = this.segments,
        top: Float = this.top,
        gap: Float = this.gap,
        thickness: Dp = this.thickness,
        lift: Float = this.lift,
        amplitude: Dp = this.amplitude,
        wavelength: Dp = this.wavelength,
        period: Duration = this.period,
        ripple: Float = this.ripple,
        alpha: Float = this.alpha,
        blendMode: BlendMode = this.blendMode,
        colors: List<Color>? = this.colors,
    ): AuroraArtStyle = AuroraArtStyle(
        bands = bands,
        segments = segments,
        top = top,
        gap = gap,
        thickness = thickness,
        lift = lift,
        amplitude = amplitude,
        wavelength = wavelength,
        period = period,
        ripple = ripple,
        alpha = alpha,
        blendMode = blendMode,
        colors = colors,
    )

    override fun equals(other: Any?): Boolean = other is AuroraArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AuroraArtStyle(" +
        "bands=$bands, " +
        "segments=$segments, " +
        "top=$top, " +
        "gap=$gap, " +
        "thickness=$thickness, " +
        "lift=$lift, " +
        "amplitude=$amplitude, " +
        "wavelength=$wavelength, " +
        "period=$period, " +
        "ripple=$ripple, " +
        "alpha=$alpha, " +
        "blendMode=$blendMode, " +
        "colors=${colors ?: "null"})"

    private fun values(): List<Any?> = listOf(
        bands,
        segments,
        top,
        gap,
        thickness,
        lift,
        amplitude,
        wavelength,
        period,
        ripple,
        alpha,
        blendMode,
        colors,
    )
}
