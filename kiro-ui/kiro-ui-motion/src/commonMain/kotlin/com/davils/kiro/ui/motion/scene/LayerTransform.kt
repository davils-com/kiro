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

package com.davils.kiro.ui.motion.scene

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

/**
 * How a [Layer] is moved, scaled and turned before it draws, and how far it follows the pointer.
 *
 * @property offset the shift of the layer, as fractions of the width and the height.
 * @property scale the scale of the layer around [anchor].
 * @property rotation the rotation of the layer around [anchor], in degrees.
 * @property anchor the point that scaling and rotation keep in place, as fractions of the size.
 * @property parallax how far the layer follows the pointer, as a fraction of the distance of the pointer from the
 * center.
 * @since 1.0.0
 */
@Immutable
public class LayerTransform(
    public val offset: Offset,
    public val scale: Float,
    public val rotation: Float,
    public val anchor: Offset,
    public val parallax: Float,
) {
    /**
     * Returns a copy of this [LayerTransform] with the given values replaced and every other value kept.
     *
     * @param offset the new value of [offset].
     * @param scale the new value of [scale].
     * @param rotation the new value of [rotation].
     * @param anchor the new value of [anchor].
     * @param parallax the new value of [parallax].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        offset: Offset = this.offset,
        scale: Float = this.scale,
        rotation: Float = this.rotation,
        anchor: Offset = this.anchor,
        parallax: Float = this.parallax,
    ): LayerTransform = LayerTransform(
        offset = offset,
        scale = scale,
        rotation = rotation,
        anchor = anchor,
        parallax = parallax,
    )

    override fun equals(other: Any?): Boolean = other is LayerTransform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "LayerTransform(" +
        "offset=$offset, " +
        "scale=$scale, " +
        "rotation=$rotation, " +
        "anchor=$anchor, " +
        "parallax=$parallax)"

    private fun values(): List<Any?> = listOf(
        offset,
        scale,
        rotation,
        anchor,
        parallax,
    )

    /**
     * Holds the transform that leaves a layer as it is.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The transform that leaves a layer as it is.
         *
         * @since 1.0.0
         */
        public val Identity: LayerTransform = LayerTransform(
            offset = Offset.Zero,
            scale = 1f,
            rotation = 0f,
            anchor = Offset(x = 0.5f, y = 0.5f),
            parallax = 0f,
        )
    }
}
