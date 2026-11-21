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
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * A smooth linear, radial or sweep gradient through the colors of the palette, blended perceptually, that slowly turns
 * and drifts.
 *
 * @property style the style of the art, or `null` for the style of the theme under [GradientArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class GradientArt(public val style: GradientArtStyle? = null) : Art {
    /**
     * Returns a copy of this [GradientArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: GradientArtStyle? = this.style): GradientArt = GradientArt(style = style)

    override fun equals(other: Any?): Boolean = other is GradientArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GradientArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        GradientRenderer(style = style ?: context.styles(GradientArtStyleKey).resolve(context.theme))
}
