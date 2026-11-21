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

package com.davils.kiro.ui.motion.shader.grain

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * A fine film grain, meant as the top layer of a scene with a low alpha, which takes the digital flatness off the
 * layers below. It is drawn by a shader, and by deterministic specks where the shader cannot run. With a
 * [GrainArtStyle.refresh] of `null` the grain never changes and the art is static.
 *
 * @property style the style of the art, or `null` for the style of the theme under [GrainArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class GrainArt(public val style: GrainArtStyle? = null) : Art {
    override val isStatic: Boolean
        get() = style != null && style.refresh == null

    /**
     * Returns a copy of this [GrainArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: GrainArtStyle? = this.style): GrainArt = GrainArt(style = style)

    override fun equals(other: Any?): Boolean = other is GrainArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GrainArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        grainRendererOf(style = style ?: context.styles(GrainArtStyleKey).resolve(context.theme))
}
