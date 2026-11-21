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

package com.davils.kiro.ui.motion.time

import androidx.compose.runtime.Immutable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

private const val NANOS_PER_SECOND: Double = 1_000_000_000.0
private const val WRAP: Long = 1_000_000L

/**
 * A point in the time of a scene, counted in nanoseconds from its start so that it keeps its precision over days.
 *
 * @property nanos the nanoseconds since the start of the scene.
 * @since 1.0.0
 */
@Immutable
public class ArtTime(public val nanos: Long) {
    /**
     * The time in seconds.
     *
     * @since 1.0.0
     */
    public val seconds: Double
        get() = nanos / NANOS_PER_SECOND

    /**
     * The time as a duration.
     *
     * @since 1.0.0
     */
    public val duration: Duration
        get() = nanos.nanoseconds

    /**
     * Returns how many periods have passed, such as `2.25` after two and a quarter rounds of an orbit. The phase is
     * taken modulo a large number of periods first, so trigonometric functions of it stay precise. A period that is
     * not positive stands still, so its phase is always `0`.
     *
     * @param period the length of one period.
     * @return the number of periods since the start, or `0` if [period] is not positive.
     * @since 1.0.0
     */
    public fun phase(period: Duration): Double {
        if (!period.isPositive()) return 0.0
        val length = period.inWholeNanoseconds
        val wrapped = nanos.mod(wrapOf(length))
        return wrapped.toDouble() / length
    }

    override fun equals(other: Any?): Boolean = other is ArtTime && nanos == other.nanos

    override fun hashCode(): Int = nanos.hashCode()

    override fun toString(): String = "ArtTime(nanos=$nanos)"

    /**
     * Holds the start of a scene.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The start of a scene.
         *
         * @since 1.0.0
         */
        public val Zero: ArtTime = ArtTime(nanos = 0L)

        /**
         * Returns the time after a duration.
         *
         * @param duration the duration since the start.
         * @return the time.
         * @since 1.0.0
         */
        public fun of(duration: Duration): ArtTime = ArtTime(nanos = duration.inWholeNanoseconds)
    }
}

private fun wrapOf(length: Long): Long {
    if (length > Long.MAX_VALUE / WRAP) return length
    return length * WRAP
}
