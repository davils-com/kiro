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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * A quiet grid of hairlines, dots or small crosses in which a few cells softly light up and fade again, while the
 * cell under the pointer stays lit.
 *
 * @property style the style of the art, or `null` for the style of the theme under [GridPatternArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class GridPatternArt(public val style: GridPatternArtStyle? = null) : Art {
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
     * Returns a copy of this [GridPatternArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: GridPatternArtStyle? = this.style): GridPatternArt = GridPatternArt(style = style)

    /**
     * Prepares the art with its style, or with the style of the theme of the context under [GridPatternArtStyleKey].
     *
     * @param context the seed, the palette, the theme, the quality and the text measurer the art draws with.
     * @return the renderer that draws the art.
     * @since 1.0.0
     */
    override fun prepare(context: ArtContext): ArtRenderer =
        GridPatternRenderer(style = style ?: context.styles(GridPatternArtStyleKey).resolve(context.theme))

    override fun equals(other: Any?): Boolean = other is GridPatternArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GridPatternArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)
}
