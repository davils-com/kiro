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

/**
 * A `uniform float2` with a fixed value, such as a direction or an offset in pixels.
 *
 * @property name the name of the uniform in the SkSL source.
 * @property x the first component.
 * @property y the second component.
 * @since 1.0.0
 */
@Immutable
public class Vec2Uniform(
    override val name: String,
    public val x: Float,
    public val y: Float,
) : Uniform {
    /**
     * Returns a copy of this [Vec2Uniform] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param x the new value of [x].
     * @param y the new value of [y].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(name: String = this.name, x: Float = this.x, y: Float = this.y): Vec2Uniform =
        Vec2Uniform(name = name, x = x, y = y)

    override fun equals(other: Any?): Boolean = other is Vec2Uniform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Vec2Uniform(" +
        "name=$name, " +
        "x=$x, " +
        "y=$y)"

    private fun values(): List<Any?> = listOf(
        name,
        x,
        y,
    )
}
