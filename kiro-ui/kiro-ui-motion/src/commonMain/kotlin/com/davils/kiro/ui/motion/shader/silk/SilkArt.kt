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

package com.davils.kiro.ui.motion.shader.silk

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * A sheet of silk in soft folds: the folds interfere, catch a quiet sheen on their crests and slowly roll across the
 * area. It is drawn by a shader, and by slowly moving linear gradients where the shader cannot run. The light follows
 * the pointer a little.
 *
 * @property style the style of the art, or `null` for the style of the theme under [SilkArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class SilkArt(public val style: SilkArtStyle? = null) : Art {
    /**
     * Returns a copy of this [SilkArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: SilkArtStyle? = this.style): SilkArt = SilkArt(style = style)

    override fun equals(other: Any?): Boolean = other is SilkArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SilkArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        silkRendererOf(style = style ?: context.styles(SilkArtStyleKey).resolve(context.theme))
}
