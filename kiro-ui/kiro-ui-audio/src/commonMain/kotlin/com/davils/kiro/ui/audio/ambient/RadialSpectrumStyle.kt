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

package com.davils.kiro.ui.audio.ambient

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.signal.DecibelRange

/**
 * How a [RadialSpectrum] looks: bars that radiate from a ring around the content in its center, colored along a
 * gradient around the circle, with dots on the held peaks, and a ring that pulses with the lowest band.
 *
 * @property size the size of the spectrum, unless the modifier sets another.
 * @property range the levels the bars show from empty to full.
 * @property inner the radius of the ring as a share of half the size.
 * @property reach the length of a full bar as a share of half the size.
 * @property barWidth the share of the space of a band on the ring that its bar takes.
 * @property colors the gradient of the bars around the circle, at least one color.
 * @property symmetry how the bands are laid around the circle.
 * @property startAngle the angle in degrees of the first band, clockwise from the right.
 * @property ringColor the color of the ring.
 * @property ringWidth the width of the ring.
 * @property pulse how far the ring and the content grow with the level of the lowest band, as a share of their size.
 * @property capColor the color of the dots on the held peaks.
 * @property capSize the size of a dot; zero hides the dots.
 * @property rest the share of [reach] a bar keeps at silence.
 * @property description what the spectrum announces to accessibility services.
 * @throws IllegalArgumentException if [colors] is empty.
 * @since 1.0.0
 */
@Immutable
public class RadialSpectrumStyle(
    public val size: Dp,
    public val range: DecibelRange,
    public val inner: Float,
    public val reach: Float,
    public val barWidth: Float,
    public val colors: List<Color>,
    public val symmetry: RadialSymmetry,
    public val startAngle: Float,
    public val ringColor: Color,
    public val ringWidth: Dp,
    public val pulse: Float,
    public val capColor: Color,
    public val capSize: Dp,
    public val rest: Float,
    public val description: String,
) {
    init {
        require(colors.isNotEmpty()) { "A radial spectrum needs at least one color." }
    }

    /**
     * Returns a copy of this [RadialSpectrumStyle] with the given values replaced and every other value kept.
     *
     * @param size the new value of [size].
     * @param range the new value of [range].
     * @param inner the new value of [inner].
     * @param reach the new value of [reach].
     * @param barWidth the new value of [barWidth].
     * @param colors the new value of [colors].
     * @param symmetry the new value of [symmetry].
     * @param startAngle the new value of [startAngle].
     * @param ringColor the new value of [ringColor].
     * @param ringWidth the new value of [ringWidth].
     * @param pulse the new value of [pulse].
     * @param capColor the new value of [capColor].
     * @param capSize the new value of [capSize].
     * @param rest the new value of [rest].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        size: Dp = this.size,
        range: DecibelRange = this.range,
        inner: Float = this.inner,
        reach: Float = this.reach,
        barWidth: Float = this.barWidth,
        colors: List<Color> = this.colors,
        symmetry: RadialSymmetry = this.symmetry,
        startAngle: Float = this.startAngle,
        ringColor: Color = this.ringColor,
        ringWidth: Dp = this.ringWidth,
        pulse: Float = this.pulse,
        capColor: Color = this.capColor,
        capSize: Dp = this.capSize,
        rest: Float = this.rest,
        description: String = this.description,
    ): RadialSpectrumStyle = RadialSpectrumStyle(
        size = size,
        range = range,
        inner = inner,
        reach = reach,
        barWidth = barWidth,
        colors = colors,
        symmetry = symmetry,
        startAngle = startAngle,
        ringColor = ringColor,
        ringWidth = ringWidth,
        pulse = pulse,
        capColor = capColor,
        capSize = capSize,
        rest = rest,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is RadialSpectrumStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RadialSpectrumStyle(" +
        "size=$size, " +
        "range=$range, " +
        "inner=$inner, " +
        "reach=$reach, " +
        "barWidth=$barWidth, " +
        "colors=$colors, " +
        "symmetry=$symmetry, " +
        "startAngle=$startAngle, " +
        "ringColor=$ringColor, " +
        "ringWidth=$ringWidth, " +
        "pulse=$pulse, " +
        "capColor=$capColor, " +
        "capSize=$capSize, " +
        "rest=$rest, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        size,
        range,
        inner,
        reach,
        barWidth,
        colors,
        symmetry,
        startAngle,
        ringColor,
        ringWidth,
        pulse,
        capColor,
        capSize,
        rest,
        description,
    )
}
