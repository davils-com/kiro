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
 * Columns of glyphs that rain down slowly, measured with the text measurer of the context, or small bars that stand in
 * for the glyphs without one.
 *
 * @property style the style of the art, or `null` for the style of the theme under [MatrixArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class MatrixArt(public val style: MatrixArtStyle? = null) : Art {
    /**
     * Whether the art never moves, which holds when its style is set, the drops never fall and the glyphs never
     * change.
     *
     * @since 1.0.0
     */
    override val isStatic: Boolean
        get() {
            val current = style ?: return false
            return current.period.isInfinite() && current.changePeriod.isInfinite()
        }

    /**
     * Returns a copy of this [MatrixArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: MatrixArtStyle? = this.style): MatrixArt = MatrixArt(style = style)

    /**
     * Prepares the art with its style, or with the style of the theme of the context under [MatrixArtStyleKey].
     *
     * @param context the seed, the palette, the theme, the quality and the text measurer the art draws with.
     * @return the renderer that draws the art.
     * @since 1.0.0
     */
    override fun prepare(context: ArtContext): ArtRenderer = MatrixRenderer(
        style = style ?: context.styles(MatrixArtStyleKey).resolve(context.theme),
        measurer = context.textMeasurer,
    )

    override fun equals(other: Any?): Boolean = other is MatrixArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MatrixArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)
}
