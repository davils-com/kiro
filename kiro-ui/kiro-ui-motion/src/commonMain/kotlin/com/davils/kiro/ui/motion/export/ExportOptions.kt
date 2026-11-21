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

package com.davils.kiro.ui.motion.export

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Density
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel

/**
 * How [renderFrame] and [renderFrames] draw a scene beyond its size and time.
 *
 * @property density the density the arts measure their lengths with.
 * @property seed the seed the seeds of the layers are derived from, or `null` for the seed of the motion style of the
 * theme, as a [com.davils.kiro.ui.motion.host.MotionCanvas] takes it.
 * @property quality the level of detail the layers draw at.
 * @property inputs the pointer, the progress, the scroll and the signals.
 * @property textMeasurer measures texts for arts that draw them, or `null` where none is available.
 * @since 1.0.0
 */
@Immutable
public class ExportOptions(
    public val density: Density = Density(density = 1f),
    public val seed: ArtSeed? = null,
    public val quality: QualityLevel = QualityLevel.High,
    public val inputs: ArtInputs = ArtInputs.None,
    public val textMeasurer: TextMeasurer? = null,
) {
    /**
     * Returns a copy of these [ExportOptions] with the given values replaced and every other value kept.
     *
     * @param density the new value of [density].
     * @param seed the new value of [seed].
     * @param quality the new value of [quality].
     * @param inputs the new value of [inputs].
     * @param textMeasurer the new value of [textMeasurer].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        density: Density = this.density,
        seed: ArtSeed? = this.seed,
        quality: QualityLevel = this.quality,
        inputs: ArtInputs = this.inputs,
        textMeasurer: TextMeasurer? = this.textMeasurer,
    ): ExportOptions = ExportOptions(
        density = density,
        seed = seed,
        quality = quality,
        inputs = inputs,
        textMeasurer = textMeasurer,
    )

    override fun equals(other: Any?): Boolean = other is ExportOptions && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ExportOptions(" +
        "density=$density, " +
        "seed=${seed ?: "null"}, " +
        "quality=$quality, " +
        "inputs=$inputs, " +
        "textMeasurer=${textMeasurer ?: "null"})"

    private fun values(): List<Any?> = listOf(
        density,
        seed,
        quality,
        inputs,
        textMeasurer,
    )
}
