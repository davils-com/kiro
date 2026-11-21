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
 * How a [SpectrumBars] looks: a bar per band colored along a gradient from bottom to top, a cap that floats on the held
 * peak of each band and a reflection below the bars.
 *
 * @property height the height of the bars, unless the modifier sets another.
 * @property range the levels the bars show from empty to full.
 * @property colors the gradient of the bars from the bottom to the top, at least one color.
 * @property gap the share of the width of a band left free between two bars.
 * @property radius the corner radius of a bar.
 * @property look how the bars are filled.
 * @property segment the height of a block when [look] is [BarLook.Segmented].
 * @property segmentGap the space between two blocks when [look] is [BarLook.Segmented].
 * @property baseline where the bars grow from.
 * @property capColor the color of the caps on the held peaks.
 * @property capHeight the height of a cap; zero hides the caps.
 * @property capGap the space between the top of a bar and its cap.
 * @property reflection the share of the height that the reflection below the bars takes when [baseline] is
 * [Baseline.Bottom]; zero hides it.
 * @property reflectionAlpha the alpha of the reflection where it meets the bars.
 * @property rest the share of the height a bar keeps at silence.
 * @property description what the bars announce to accessibility services.
 * @throws IllegalArgumentException if [colors] is empty.
 * @since 1.0.0
 */
@Immutable
public class SpectrumBarsStyle(
    public val height: Dp,
    public val range: DecibelRange,
    public val colors: List<Color>,
    public val gap: Float,
    public val radius: Dp,
    public val look: BarLook,
    public val segment: Dp,
    public val segmentGap: Dp,
    public val baseline: Baseline,
    public val capColor: Color,
    public val capHeight: Dp,
    public val capGap: Dp,
    public val reflection: Float,
    public val reflectionAlpha: Float,
    public val rest: Float,
    public val description: String,
) {
    init {
        require(colors.isNotEmpty()) { "Spectrum bars need at least one color." }
    }

    /**
     * Returns a copy of this [SpectrumBarsStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param range the new value of [range].
     * @param colors the new value of [colors].
     * @param gap the new value of [gap].
     * @param radius the new value of [radius].
     * @param look the new value of [look].
     * @param segment the new value of [segment].
     * @param segmentGap the new value of [segmentGap].
     * @param baseline the new value of [baseline].
     * @param capColor the new value of [capColor].
     * @param capHeight the new value of [capHeight].
     * @param capGap the new value of [capGap].
     * @param reflection the new value of [reflection].
     * @param reflectionAlpha the new value of [reflectionAlpha].
     * @param rest the new value of [rest].
     * @param description the new value of [description].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        range: DecibelRange = this.range,
        colors: List<Color> = this.colors,
        gap: Float = this.gap,
        radius: Dp = this.radius,
        look: BarLook = this.look,
        segment: Dp = this.segment,
        segmentGap: Dp = this.segmentGap,
        baseline: Baseline = this.baseline,
        capColor: Color = this.capColor,
        capHeight: Dp = this.capHeight,
        capGap: Dp = this.capGap,
        reflection: Float = this.reflection,
        reflectionAlpha: Float = this.reflectionAlpha,
        rest: Float = this.rest,
        description: String = this.description,
    ): SpectrumBarsStyle = SpectrumBarsStyle(
        height = height,
        range = range,
        colors = colors,
        gap = gap,
        radius = radius,
        look = look,
        segment = segment,
        segmentGap = segmentGap,
        baseline = baseline,
        capColor = capColor,
        capHeight = capHeight,
        capGap = capGap,
        reflection = reflection,
        reflectionAlpha = reflectionAlpha,
        rest = rest,
        description = description,
    )

    override fun equals(other: Any?): Boolean = other is SpectrumBarsStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SpectrumBarsStyle(" +
        "height=$height, " +
        "range=$range, " +
        "colors=$colors, " +
        "gap=$gap, " +
        "radius=$radius, " +
        "look=$look, " +
        "segment=$segment, " +
        "segmentGap=$segmentGap, " +
        "baseline=$baseline, " +
        "capColor=$capColor, " +
        "capHeight=$capHeight, " +
        "capGap=$capGap, " +
        "reflection=$reflection, " +
        "reflectionAlpha=$reflectionAlpha, " +
        "rest=$rest, " +
        "description=$description)"

    private fun values(): List<Any?> = listOf(
        height,
        range,
        colors,
        gap,
        radius,
        look,
        segment,
        segmentGap,
        baseline,
        capColor,
        capHeight,
        capGap,
        reflection,
        reflectionAlpha,
        rest,
        description,
    )
}
