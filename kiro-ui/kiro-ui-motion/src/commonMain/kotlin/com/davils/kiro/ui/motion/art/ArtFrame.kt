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

package com.davils.kiro.ui.motion.art

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.time.ArtTime

/**
 * Everything an [ArtRenderer] needs to draw one frame.
 *
 * @property time the time of the layer, after its [com.davils.kiro.ui.motion.time.TimeRemap].
 * @property inputs the pointer, the progress and the signals, or [ArtInputs.None] for a layer that does not react.
 * @property palette the colors of the scene.
 * @property quality the level of detail to draw at.
 * @property seed the seed of the layer.
 * @since 1.0.0
 */
@Immutable
public class ArtFrame(
    public val time: ArtTime,
    public val inputs: ArtInputs,
    public val palette: ArtPalette,
    public val quality: QualityLevel,
    public val seed: ArtSeed,
) {
    /**
     * Returns a copy of this [ArtFrame] with the given values replaced and every other value kept.
     *
     * @param time the new value of [time].
     * @param inputs the new value of [inputs].
     * @param palette the new value of [palette].
     * @param quality the new value of [quality].
     * @param seed the new value of [seed].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        time: ArtTime = this.time,
        inputs: ArtInputs = this.inputs,
        palette: ArtPalette = this.palette,
        quality: QualityLevel = this.quality,
        seed: ArtSeed = this.seed,
    ): ArtFrame = ArtFrame(
        time = time,
        inputs = inputs,
        palette = palette,
        quality = quality,
        seed = seed,
    )

    override fun equals(other: Any?): Boolean = other is ArtFrame && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ArtFrame(" +
        "time=$time, " +
        "inputs=$inputs, " +
        "palette=$palette, " +
        "quality=$quality, " +
        "seed=$seed)"

    private fun values(): List<Any?> = listOf(
        time,
        inputs,
        palette,
        quality,
        seed,
    )
}
