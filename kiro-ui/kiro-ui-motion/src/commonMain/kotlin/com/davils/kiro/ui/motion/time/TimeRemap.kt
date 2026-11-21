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

/**
 * How a layer sees the time of its scene: faster or slower, shifted, frozen or looping.
 *
 * @property speed the factor of the speed of the layer; `0.5` runs at half the speed of the scene.
 * @property offset the time the layer is ahead of the scene.
 * @property isFrozen whether the layer stays at [offset] and does not move.
 * @property loop the length after which the time of the layer starts over, or `null` to run on.
 * @property isPingPong whether a loop runs backwards every second time instead of jumping back.
 * @since 1.0.0
 */
@Immutable
public class TimeRemap(
    public val speed: Float,
    public val offset: Duration,
    public val isFrozen: Boolean,
    public val loop: Duration?,
    public val isPingPong: Boolean,
) {
    /**
     * Returns a copy of this [TimeRemap] with the given values replaced and every other value kept.
     *
     * @param speed the new value of [speed].
     * @param offset the new value of [offset].
     * @param isFrozen the new value of [isFrozen].
     * @param loop the new value of [loop].
     * @param isPingPong the new value of [isPingPong].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        speed: Float = this.speed,
        offset: Duration = this.offset,
        isFrozen: Boolean = this.isFrozen,
        loop: Duration? = this.loop,
        isPingPong: Boolean = this.isPingPong,
    ): TimeRemap = TimeRemap(
        speed = speed,
        offset = offset,
        isFrozen = isFrozen,
        loop = loop,
        isPingPong = isPingPong,
    )

    override fun equals(other: Any?): Boolean = other is TimeRemap && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TimeRemap(" +
        "speed=$speed, " +
        "offset=$offset, " +
        "isFrozen=$isFrozen, " +
        "loop=${loop ?: "null"}, " +
        "isPingPong=$isPingPong)"

    private fun values(): List<Any?> = listOf(
        speed,
        offset,
        isFrozen,
        loop,
        isPingPong,
    )

    /**
     * Returns the time the layer sees at a time of the scene.
     *
     * @param time the time of the scene.
     * @return the time of the layer.
     * @since 1.0.0
     */
    public fun apply(time: ArtTime): ArtTime {
        val start = ArtTime.of(offset)
        if (isFrozen) return start
        val scaled = (time.nanos * speed.toDouble()).toLong() + start.nanos
        val length = loop?.inWholeNanoseconds ?: 0L
        return ArtTime(nanos = loopedNanos(nanos = scaled, length = length, isPingPong = isPingPong))
    }

    /**
     * Holds the remap that leaves the time of a layer as the time of its scene.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The remap that leaves the time as it is.
         *
         * @since 1.0.0
         */
        public val Identity: TimeRemap = TimeRemap(
            speed = 1f,
            offset = Duration.ZERO,
            isFrozen = false,
            loop = null,
            isPingPong = false,
        )
    }
}
