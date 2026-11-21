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
 * How a [SpectrumWave] looks: the bands as smooth waves, layered in front of each other like a mountain range, each
 * layer behind the front a little higher and in a color of its own.
 *
 * @property height the height of the waves, unless the modifier sets another.
 * @property range the levels the waves show from empty to full.
 * @property colors the color of each layer from the front to the back, at least one.
 * @property falloff how much lower each layer is than the one behind it, as a share of the full height.
 * @property fillAlpha the alpha of the color of a layer for its area.
 * @property lineWidth the width of the edge of a layer.
 * @property lineAlpha the alpha of the color of a layer for its edge.
 * @property baseline where the waves grow from.
 * @property rest the share of the height the waves keep at silence.
 * @property description what the waves announce to accessibility services.
 * @throws IllegalArgumentException if [colors] is empty.
 * @since 1.0.0
 */
@Immutable
public class SpectrumWaveStyle(
    public val height: Dp,
    public val range: DecibelRange,
    public val colors: List<Color>,
    public val falloff: Float,
    public val fillAlpha: Float,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val baseline: Baseline,
    public val rest: Float,
    public val description: String,
) {
    init {
        require(colors.isNotEmpty()) { "A spectrum wave needs at least one color." }
    }

    /**
     * Returns a copy of this [SpectrumWaveStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param range the new value of [range].
     * @param colors the new value of [colors].
     * @param falloff the new value of [falloff].
     * @param fillAlpha the new value of [fillAlpha].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param baseline the new value of [baseline].
     * @param rest the new value of [rest].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        range: DecibelRange = this.range,
        colors: List<Color> = this.colors,
        falloff: Float = this.falloff,
        fillAlpha: Float = this.fillAlpha,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        baseline: Baseline = this.baseline,
        rest: Float = this.rest,
        description: String = this.description,
    ): SpectrumWaveStyle = SpectrumWaveStyle(
        height = height,
        range = range,
        colors = colors,
        falloff = falloff,
        fillAlpha = fillAlpha,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        baseline = baseline,
        rest = rest,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is SpectrumWaveStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SpectrumWaveStyle(" +
        "height=$height, " +
        "range=$range, " +
        "colors=$colors, " +
        "falloff=$falloff, " +
        "fillAlpha=$fillAlpha, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "baseline=$baseline, " +
        "rest=$rest, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        range,
        colors,
        falloff,
        fillAlpha,
        lineWidth,
        lineAlpha,
        baseline,
        rest,
        description,
    )
}
