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

package com.davils.kiro.ui.motion.scene

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.BlendMode
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.time.TimeRemap

/**
 * One [Art] in a [Scene], with how it is blended, moved, masked and timed.
 *
 * @property art the art the layer draws.
 * @property opacity the opacity of the layer, between `0` and `1`.
 * @property blend how the layer mixes with the layers below it.
 * @property transform how the layer is moved, scaled and turned.
 * @property mask how the layer fades out.
 * @property time how the layer sees the time of the scene.
 * @property seed the seed of the layer, or `null` to derive it from the seed of the scene and the index of the layer.
 * @property isVisible whether the layer draws.
 * @property isInteractive whether the layer sees the pointer, the progress and the signals.
 * @since 1.0.0
 */
@Immutable
public class Layer(
    public val art: Art,
    public val opacity: Float = 1f,
    public val blend: BlendMode = BlendMode.SrcOver,
    public val transform: LayerTransform = LayerTransform.Identity,
    public val mask: Mask = Mask.None,
    public val time: TimeRemap = TimeRemap.Identity,
    public val seed: ArtSeed? = null,
    public val isVisible: Boolean = true,
    public val isInteractive: Boolean = true,
) {
    /**
     * Returns a copy of this [Layer] with the given values replaced and every other value kept.
     *
     * @param art the new value of [art].
     * @param opacity the new value of [opacity].
     * @param blend the new value of [blend].
     * @param transform the new value of [transform].
     * @param mask the new value of [mask].
     * @param time the new value of [time].
     * @param seed the new value of [seed].
     * @param isVisible the new value of [isVisible].
     * @param isInteractive the new value of [isInteractive].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        art: Art = this.art,
        opacity: Float = this.opacity,
        blend: BlendMode = this.blend,
        transform: LayerTransform = this.transform,
        mask: Mask = this.mask,
        time: TimeRemap = this.time,
        seed: ArtSeed? = this.seed,
        isVisible: Boolean = this.isVisible,
        isInteractive: Boolean = this.isInteractive,
    ): Layer = Layer(
        art = art,
        opacity = opacity,
        blend = blend,
        transform = transform,
        mask = mask,
        time = time,
        seed = seed,
        isVisible = isVisible,
        isInteractive = isInteractive,
    )

    override fun equals(other: Any?): Boolean = other is Layer && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Layer(" +
        "art=$art, " +
        "opacity=$opacity, " +
        "blend=$blend, " +
        "transform=$transform, " +
        "mask=$mask, " +
        "time=$time, " +
        "seed=${seed ?: "null"}, " +
        "isVisible=$isVisible, " +
        "isInteractive=$isInteractive)"

    private fun values(): List<Any?> = listOf(
        art,
        opacity,
        blend,
        transform,
        mask,
        time,
        seed,
        isVisible,
        isInteractive,
    )
}
