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
 * A floor of hairlines in perspective that flows slowly towards the viewer and fades into a soft glow at the
 * horizon, with a few pulses of light travelling along its lanes.
 *
 * @property style the style of the art, or `null` for the style of the theme under [RetroGridArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class RetroGridArt(public val style: RetroGridArtStyle? = null) : Art {
    /**
     * Whether the art never moves, which holds when its style is set and neither the floor nor the pulses ever move.
     *
     * @since 1.0.0
     */
    override val isStatic: Boolean
        get() {
            val current = style ?: return false
            return current.period.isInfinite() && current.pulsePeriod.isInfinite()
        }

    /**
     * Returns a copy of this [RetroGridArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: RetroGridArtStyle? = this.style): RetroGridArt = RetroGridArt(style = style)

    /**
     * Prepares the art with its style, or with the style of the theme of the context under [RetroGridArtStyleKey].
     *
     * @param context the seed, the palette, the theme, the quality and the text measurer the art draws with.
     * @return the renderer that draws the art.
     * @since 1.0.0
     */
    override fun prepare(context: ArtContext): ArtRenderer =
        RetroGridRenderer(style = style ?: context.styles(RetroGridArtStyleKey).resolve(context.theme))

    override fun equals(other: Any?): Boolean = other is RetroGridArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RetroGridArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)
}
