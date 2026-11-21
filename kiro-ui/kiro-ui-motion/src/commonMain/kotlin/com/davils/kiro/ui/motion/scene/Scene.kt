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
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.mask.Mask

/**
 * A picture of layered generative art, drawn by a [com.davils.kiro.ui.motion.host.MotionCanvas].
 *
 * Build one with [scene] or take one of the [ScenePresets].
 *
 * @property layers the layers, from the bottom to the top.
 * @property background the color behind every layer, or `null` for the background of the palette.
 * @property palette the colors of the scene, or `null` for the palette of the theme.
 * @property mask how the whole scene fades out.
 * @since 1.0.0
 */
@Immutable
public class Scene(
    public val layers: List<Layer>,
    public val background: Color? = null,
    public val palette: ArtPalette? = null,
    public val mask: Mask = Mask.None,
) {
    /**
     * Whether no visible layer moves, which lets the host stop its clock.
     *
     * @since 1.0.0
     */
    public val isStatic: Boolean
        get() = layers.all { layer -> !layer.isVisible || layer.art.isStatic || layer.time.isFrozen }

    /**
     * Returns a copy of this [Scene] with the given values replaced and every other value kept.
     *
     * @param layers the new value of [layers].
     * @param background the new value of [background].
     * @param palette the new value of [palette].
     * @param mask the new value of [mask].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        layers: List<Layer> = this.layers,
        background: Color? = this.background,
        palette: ArtPalette? = this.palette,
        mask: Mask = this.mask,
    ): Scene = Scene(
        layers = layers,
        background = background,
        palette = palette,
        mask = mask,
    )

    override fun equals(other: Any?): Boolean = other is Scene && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Scene(" +
        "layers=$layers, " +
        "background=${background ?: "null"}, " +
        "palette=${palette ?: "null"}, " +
        "mask=$mask)"

    private fun values(): List<Any?> = listOf(
        layers,
        background,
        palette,
        mask,
    )
}
