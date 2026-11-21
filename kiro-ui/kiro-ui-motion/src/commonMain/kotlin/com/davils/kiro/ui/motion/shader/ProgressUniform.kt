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
 * A `uniform float` with the progress the application feeds into the scene, from `0` to `1`, or [missing] while it
 * feeds none.
 *
 * @property name the name of the uniform in the SkSL source.
 * @property missing the value while the scene has no progress.
 * @since 1.0.0
 */
@Immutable
public class ProgressUniform(
    override val name: String,
    public val missing: Float,
) : Uniform {
    /**
     * Returns a copy of this [ProgressUniform] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param missing the new value of [missing].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(name: String = this.name, missing: Float = this.missing): ProgressUniform =
        ProgressUniform(name = name, missing = missing)

    override fun equals(other: Any?): Boolean = other is ProgressUniform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ProgressUniform(" +
        "name=$name, " +
        "missing=$missing)"

    private fun values(): List<Any?> = listOf(
        name,
        missing,
    )
}
