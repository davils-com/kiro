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
 * A `uniform float3` with the smoothed pointer: its position in pixels and `1` while it is over the art, or three
 * zeros while it is not, so that the source can fade its reaction with the third component.
 *
 * @property name the name of the uniform in the SkSL source.
 * @since 1.0.0
 */
@Immutable
public class PointerUniform(
    override val name: String,
) : Uniform {
    /**
     * Returns a copy of this [PointerUniform] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(name: String = this.name): PointerUniform = PointerUniform(name = name)

    override fun equals(other: Any?): Boolean = other is PointerUniform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "PointerUniform(" +
        "name=$name)"

    private fun values(): List<Any?> = listOf(
        name,
    )
}
