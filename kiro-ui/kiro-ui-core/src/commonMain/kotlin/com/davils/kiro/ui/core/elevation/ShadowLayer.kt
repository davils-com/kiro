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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * One layer of a [BoxShadow], drawn like a layer of a CSS box shadow.
 *
 * @property offsetX the horizontal offset of the layer.
 * @property offsetY the vertical offset of the layer.
 * @property blur the blur radius of the layer.
 * @property spread how far the layer grows beyond the shape, or shrinks into it when negative.
 * @property color the color of the layer.
 * @since 1.0.0
 */
@Immutable
public class ShadowLayer(
    public val offsetX: Dp = 0.dp,
    public val offsetY: Dp,
    public val blur: Dp,
    public val spread: Dp = 0.dp,
    public val color: Color,
) {
    /**
     * Returns a copy of this [ShadowLayer] with the given values replaced and every other value kept.
     *
     * @param offsetX the new value of [offsetX].
     * @param offsetY the new value of [offsetY].
     * @param blur the new value of [blur].
     * @param spread the new value of [spread].
     * @param color the new value of [color].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        offsetX: Dp = this.offsetX,
        offsetY: Dp = this.offsetY,
        blur: Dp = this.blur,
        spread: Dp = this.spread,
        color: Color = this.color,
    ): ShadowLayer = ShadowLayer(
        offsetX = offsetX,
        offsetY = offsetY,
        blur = blur,
        spread = spread,
        color = color,
    )

    override fun equals(other: Any?): Boolean = other is ShadowLayer && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ShadowLayer(" +
        "offsetX=$offsetX, " +
        "offsetY=$offsetY, " +
        "blur=$blur, " +
        "spread=$spread, " +
        "color=$color)"

    private fun values(): List<Any?> = listOf(
        offsetX,
        offsetY,
        blur,
        spread,
        color,
    )
}
