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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * Small dots at different depths that drift, wrap around the edges and twinkle, joined by hairlines where they come
 * close and shifted by the pointer so that the near ones move more than the far ones.
 *
 * @property style the style of the art, or `null` for the style of the theme under [ParticlesArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class ParticlesArt(public val style: ParticlesArtStyle? = null) : Art {
    /**
     * Returns a copy of this [ParticlesArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: ParticlesArtStyle? = this.style): ParticlesArt = ParticlesArt(style = style)

    override fun equals(other: Any?): Boolean = other is ParticlesArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ParticlesArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        ParticlesRenderer(style = style ?: context.styles(ParticlesArtStyleKey).resolve(context.theme))
}
