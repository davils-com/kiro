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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * A fan of faint curves that sweep across the area, along which short beams of light travel.
 *
 * @property style the style of the art, or `null` for the style of the theme under [BeamsArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class BeamsArt(public val style: BeamsArtStyle? = null) : Art {
    /**
     * Whether the art never moves, which holds when its style is set and its period never ends.
     *
     * @since 1.0.0
     */
    override val isStatic: Boolean
        get() {
            val current = style ?: return false
            return current.period.isInfinite()
        }

    /**
     * Returns a copy of this [BeamsArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: BeamsArtStyle? = this.style): BeamsArt = BeamsArt(style = style)

    /**
     * Prepares the art with its style, or with the style of the theme of the context under [BeamsArtStyleKey].
     *
     * @param context the seed, the palette, the theme, the quality and the text measurer the art draws with.
     * @return the renderer that draws the art.
     * @since 1.0.0
     */
    override fun prepare(context: ArtContext): ArtRenderer =
        BeamsRenderer(style = style ?: context.styles(BeamsArtStyleKey).resolve(context.theme))

    override fun equals(other: Any?): Boolean = other is BeamsArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BeamsArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)
}
