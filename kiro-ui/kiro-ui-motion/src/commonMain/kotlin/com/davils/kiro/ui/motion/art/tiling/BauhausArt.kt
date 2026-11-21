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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.Art
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.art.ArtRenderer

/**
 * A grid of colored tiles in the manner of the Bauhaus, each with one bold shape in a contrasting color, such as a
 * quarter circle, a half circle, a ring, a triangle or stripes, whose tiles turn by a quarter now and then.
 *
 * @property style the style of the art, or `null` for the style of the theme under [BauhausArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class BauhausArt(public val style: BauhausArtStyle? = null) : Art {
    /**
     * Whether the art never moves, which holds when its style is set and none of its periods ever ends.
     *
     * @since 1.0.0
     */
    override val isStatic: Boolean
        get() {
            val current = style ?: return false
            return current.period.isInfinite()
        }

    /**
     * Returns a copy of this [BauhausArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: BauhausArtStyle? = this.style): BauhausArt = BauhausArt(style = style)

    /**
     * Prepares the art with its style, or with the style of the theme of the context under [BauhausArtStyleKey].
     *
     * @param context the seed, the palette, the theme and the quality the art draws with.
     * @return the renderer that draws the art.
     * @since 1.0.0
     */
    override fun prepare(context: ArtContext): ArtRenderer =
        BauhausRenderer(style = style ?: context.styles(BauhausArtStyleKey).resolve(context.theme))

    override fun equals(other: Any?): Boolean = other is BauhausArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BauhausArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)
}
