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
 * A `uniform float` with a fixed value, such as a strength or a scale.
 *
 * @property name the name of the uniform in the SkSL source.
 * @property value the value.
 * @since 1.0.0
 */
@Immutable
public class FloatUniform(
    override val name: String,
    public val value: Float,
) : Uniform {
    /**
     * Returns a copy of this [FloatUniform] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param value the new value of [value].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(name: String = this.name, value: Float = this.value): FloatUniform =
        FloatUniform(name = name, value = value)

    override fun equals(other: Any?): Boolean = other is FloatUniform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FloatUniform(" +
        "name=$name, " +
        "value=$value)"

    private fun values(): List<Any?> = listOf(
        name,
        value,
    )
}
