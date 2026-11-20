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

package com.davils.kiro.ui.core.motion

import androidx.compose.runtime.Immutable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * The durations of the transitions of a theme.
 *
 * @property short the duration of small changes, such as a color on hover.
 * @property medium the duration of medium changes, such as an expanding panel.
 * @property long the duration of large changes, such as a sheet sliding in.
 * @since 1.0.0
 */
@Immutable
public class Durations(
    public val short: Duration = 150.milliseconds,
    public val medium: Duration = 250.milliseconds,
    public val long: Duration = 350.milliseconds,
) {
    /**
     * Returns a copy of this [Durations] with the given values replaced and every other value kept.
     *
     * @param short the new value of [short].
     * @param medium the new value of [medium].
     * @param long the new value of [long].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        short: Duration = this.short,
        medium: Duration = this.medium,
        long: Duration = this.long,
    ): Durations = Durations(
        short = short,
        medium = medium,
        long = long,
    )

    override fun equals(other: Any?): Boolean = other is Durations && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Durations(" +
        "short=$short, " +
        "medium=$medium, " +
        "long=$long)"

    private fun values(): List<Any?> = listOf(
        short,
        medium,
        long,
    )
}
