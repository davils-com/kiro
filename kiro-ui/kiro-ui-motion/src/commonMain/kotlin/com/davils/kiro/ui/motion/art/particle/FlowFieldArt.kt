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
 * Thin, fading streamlines that follow the currents of a slowly changing field of noise, each with a head that
 * advances along its path before it starts again somewhere else.
 *
 * @property style the style of the art, or `null` for the style of the theme under [FlowFieldArtStyleKey].
 * @since 1.0.0
 */
@Immutable
public class FlowFieldArt(public val style: FlowFieldArtStyle? = null) : Art {
    /**
     * Returns a copy of this [FlowFieldArt] with the given values replaced and every other value kept.
     *
     * @param style the new value of [style].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(style: FlowFieldArtStyle? = this.style): FlowFieldArt = FlowFieldArt(style = style)

    override fun equals(other: Any?): Boolean = other is FlowFieldArt && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FlowFieldArt(style=${style ?: "null"})"

    private fun values(): List<Any?> = listOf(style)

    override fun prepare(context: ArtContext): ArtRenderer =
        FlowFieldRenderer(style = style ?: context.styles(FlowFieldArtStyleKey).resolve(context.theme))
}
