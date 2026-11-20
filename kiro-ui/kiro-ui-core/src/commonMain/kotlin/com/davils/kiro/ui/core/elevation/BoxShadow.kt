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

package com.davils.kiro.ui.core.elevation

import androidx.compose.runtime.Immutable

/**
 * A shadow made of layers, drawn like a CSS box shadow: the first layer lies on top.
 *
 * @param layers the layers of the shadow; the list is copied, so later changes to it do not reach the shadow.
 * @since 0.1.0
 */
@Immutable
public class BoxShadow(layers: List<ShadowLayer>) {
    /**
     * The layers of the shadow, from top to bottom. The list cannot be changed.
     *
     * @since 0.1.0
     */
    public val layers: List<ShadowLayer> = buildList { addAll(layers) }

    /**
     * Returns a copy of this [BoxShadow] with the given layers.
     *
     * @param layers the new layers.
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(layers: List<ShadowLayer> = this.layers): BoxShadow = BoxShadow(layers = layers)

    override fun equals(other: Any?): Boolean = other is BoxShadow && layers == other.layers

    override fun hashCode(): Int = layers.hashCode()

    override fun toString(): String = "BoxShadow(layers=$layers)"

    /**
     * The shadows that every theme knows.
     *
     * @since 0.1.0
     */
    public companion object {
        /**
         * A shadow without any layer, which draws nothing.
         *
         * @since 0.1.0
         */
        public val None: BoxShadow = BoxShadow(layers = emptyList())
    }
}
