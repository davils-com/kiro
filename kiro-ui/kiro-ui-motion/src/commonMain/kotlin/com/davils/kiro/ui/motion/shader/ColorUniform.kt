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

package com.davils.kiro.ui.motion.shader

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * A `uniform half4` or `float4` with a color: its red, green and blue in sRGB and its alpha, not premultiplied,
 * so that the source decides how the alpha blends.
 *
 * @property name the name of the uniform in the SkSL source.
 * @property color the color, whose alpha is kept.
 * @since 1.0.0
 */
@Immutable
public class ColorUniform(
    override val name: String,
    public val color: Color,
) : Uniform {
    /**
     * Returns a copy of this [ColorUniform] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param color the new value of [color].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(name: String = this.name, color: Color = this.color): ColorUniform =
        ColorUniform(name = name, color = color)

    override fun equals(other: Any?): Boolean = other is ColorUniform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ColorUniform(" +
        "name=$name, " +
        "color=$color)"

    private fun values(): List<Any?> = listOf(
        name,
        color,
    )
}
