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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * The contour lines of a slowly changing terrain, like a topographic map: a height field of noise traced with
 * marching squares, with thicker index lines every few levels and, if wanted, bands of color between the levels.
 *
 * @property style the style of the art, or `null` for the style of the theme under [ContourArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class ContourArt(public val style: ContourArtStyle? = null) : Art {
    /**
     * Returns a copy of this [ContourArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: ContourArtStyle? = this.style): ContourArt = ContourArt(style = style)

    override fun equals(other: Any?): Boolean = other is ContourArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ContourArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        ContourRenderer(style = style ?: context.styles(ContourArtStyleKey).resolve(context.theme))
}
