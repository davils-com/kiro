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
import kotlin.time.Duration

/**
 * A `uniform float` with the time of the frame as the fraction of a period that has passed, from `0` up to but not
 * including `1`.
 *
 * A source that moves with whole turns of this fraction, such as `sin(6.2831853 * time)`, loops seamlessly and stays
 * precise however long the scene runs.
 *
 * @property name the name of the uniform in the SkSL source.
 * @property period the length of one loop.
 * @since 1.0.0
 */
@Immutable
public class TimeUniform(
    override val name: String,
    public val period: Duration,
) : Uniform {
    /**
     * Returns a copy of this [TimeUniform] with the given values replaced and every other value kept.
     *
     * @param name the new value of [name].
     * @param period the new value of [period].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(name: String = this.name, period: Duration = this.period): TimeUniform =
        TimeUniform(name = name, period = period)

    override fun equals(other: Any?): Boolean = other is TimeUniform && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TimeUniform(" +
        "name=$name, " +
        "period=$period)"

    private fun values(): List<Any?> = listOf(
        name,
        period,
    )
}
