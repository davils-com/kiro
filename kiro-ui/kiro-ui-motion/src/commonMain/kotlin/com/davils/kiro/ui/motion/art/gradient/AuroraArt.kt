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
 * A few translucent bands of light across the sky that sway, ripple and shift their colors like an aurora.
 *
 * @property style the style of the art, or `null` for the style of the theme under [AuroraArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class AuroraArt(public val style: AuroraArtStyle? = null) : Art {
    /**
     * Returns a copy of this [AuroraArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: AuroraArtStyle? = this.style): AuroraArt = AuroraArt(style = style)

    override fun equals(other: Any?): Boolean = other is AuroraArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AuroraArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        AuroraRenderer(style = style ?: context.styles(AuroraArtStyleKey).resolve(context.theme))
}
