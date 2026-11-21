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
 * A value that moves through [Keyframe]s over time, such as the size of a shape that grows, overshoots and settles.
 *
 * Between two keyframes the value is mixed by [lerp] along the easing of the first of them.
 *
 * ```kotlin
 * val radius = Keyframes(
 *     frames = listOf(Keyframe(ZERO, 0f, Easings.backOut), Keyframe(600.milliseconds, 1f)),
 *     lerp = ::lerp,
 *     loop = KeyframeLoop.PingPong,
 * )
 * val value = radius.valueAt(frame.time)
 * ```
 *
 * @param T the type of the value.
 * @property frames the keyframes, in the order of their time.
 * @property lerp mixes two values: the value at a fraction between `0` (the first) and `1` (the second).
 * @property loop what happens before the first and after the last keyframe.
 * @throws IllegalArgumentException if [frames] is empty or not in the order of their time.
 * @since 1.0.0
 */
@Immutable
public class Keyframes<T>(
    public val frames: List<Keyframe<T>>,
    public val lerp: (start: T, stop: T, fraction: Float) -> T,
    public val loop: KeyframeLoop = KeyframeLoop.Clamp,
) {
    private val times: LongArray = LongArray(frames.size) { index -> frames[index].time.inWholeNanoseconds }

    init {
        require(frames.isNotEmpty()) { "Keyframes need at least one keyframe." }
        val pairs = times.asList().zipWithNext()
        require(pairs.all { (first, second) -> first <= second }) {
            "The keyframes must be in the order of their time, but were at ${frames.map { frame -> frame.time }}."
        }
    }

    /**
     * The time from the first to the last keyframe.
     *
     * @since 1.0.0
     */
    public val duration: Duration
        get() = frames.last().time - frames.first().time

    /**
     * Returns the value at a point in time.
     *
     * @param time the point in time.
     * @return the value.
     * @since 1.0.0
     */
    public fun valueAt(time: ArtTime): T {
        val first = times.first()
        val span = times.last() - first
        if (span <= 0L) return frames.first().value
        return valueWithin(nanos = first + localOf(nanos = time.nanos - first, span = span))
    }

    /**
     * Returns the value after a duration.
     *
     * @param time the duration since the start.
     * @return the value.
     * @since 1.0.0
     */
    public fun valueAt(time: Duration): T = valueAt(ArtTime.of(time))

    /**
     * Returns a copy of these [Keyframes] with the given values replaced and every other value kept.
     *
     * @param frames the new value of [frames].
     * @param lerp the new value of [lerp].
     * @param loop the new value of [loop].
     * @return the copy.
     * @throws IllegalArgumentException if [frames] is empty or not in the order of their time.
     * @since 1.0.0
     */
    public fun copy(
        frames: List<Keyframe<T>> = this.frames,
        lerp: (start: T, stop: T, fraction: Float) -> T = this.lerp,
        loop: KeyframeLoop = this.loop,
    ): Keyframes<T> = Keyframes(frames = frames, lerp = lerp, loop = loop)

    override fun equals(other: Any?): Boolean =
        other is Keyframes<*> && frames == other.frames && lerp == other.lerp && loop == other.loop

    override fun hashCode(): Int = listOf(frames, lerp, loop).hashCode()

    override fun toString(): String = "Keyframes(frames=$frames, lerp=$lerp, loop=$loop)"

    private fun localOf(nanos: Long, span: Long): Long = when (loop) {
        KeyframeLoop.Clamp -> nanos.coerceIn(minimumValue = 0L, maximumValue = span)
        KeyframeLoop.Loop -> loopedNanos(nanos = nanos, length = span, isPingPong = false)
        KeyframeLoop.PingPong -> loopedNanos(nanos = nanos, length = span, isPingPong = true)
    }

    private fun valueWithin(nanos: Long): T {
        val reached = times.indexOfLast { time -> time <= nanos }
        val index = reached.coerceIn(minimumValue = 0, maximumValue = frames.size - 2)
        val from = frames[index]
        val to = frames[index + 1]
        val length = times[index + 1] - times[index]
        if (length <= 0L) return to.value
        val fraction = ((nanos - times[index]).toDouble() / length).toFloat()
        return lerp(from.value, to.value, from.easing.transform(fraction))
    }
}
