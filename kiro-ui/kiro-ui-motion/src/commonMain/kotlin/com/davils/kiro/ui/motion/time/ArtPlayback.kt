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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

/**
 * The clock of a [com.davils.kiro.ui.motion.host.MotionCanvas]: whether its scene plays, how fast and where in time it
 * is. The canvas advances it in every frame while it plays; an application pauses, seeks or loops it.
 *
 * Every value is snapshot state, so that a change shows in the next frame without recomposing the canvas.
 *
 * @param start the time the playback starts at and goes back to on [reset].
 * @param isPlaying whether the playback starts playing.
 * @param speed the factor of the speed the time passes at; `0.5` plays at half the speed, a negative speed backwards.
 * @param loop the length after which the time starts over, or `null` to run on.
 * @param isPingPong whether a loop runs backwards every second time instead of jumping back.
 * @since 1.0.0
 */
@Stable
public class ArtPlayback(
    private val start: Duration = Duration.ZERO,
    isPlaying: Boolean = true,
    speed: Float = 1f,
    loop: Duration? = null,
    isPingPong: Boolean = false,
) {
    private var elapsedNanos: Long by mutableLongStateOf(start.inWholeNanoseconds)

    /**
     * Whether the time passes.
     *
     * @since 1.0.0
     */
    public var isPlaying: Boolean by mutableStateOf(isPlaying)
        private set

    /**
     * The factor of the speed the time passes at; `0.5` plays at half the speed, a negative speed backwards.
     *
     * @since 1.0.0
     */
    public var speed: Float by mutableFloatStateOf(speed)

    /**
     * The length after which the time starts over, or `null` to run on.
     *
     * @since 1.0.0
     */
    public var loop: Duration? by mutableStateOf(loop)

    /**
     * Whether a [loop] runs backwards every second time instead of jumping back.
     *
     * @since 1.0.0
     */
    public var isPingPong: Boolean by mutableStateOf(isPingPong)

    /**
     * The time that has passed while playing, before the [loop] is applied.
     *
     * @since 1.0.0
     */
    public val elapsed: Duration
        get() = elapsedNanos.nanoseconds

    /**
     * The current time of the scene, after the [loop] is applied.
     *
     * @since 1.0.0
     */
    public val time: ArtTime
        get() {
            val length = loop?.inWholeNanoseconds ?: 0L
            return ArtTime(nanos = loopedNanos(nanos = elapsedNanos, length = length, isPingPong = isPingPong))
        }

    /**
     * Lets the time pass.
     *
     * @since 1.0.0
     */
    public fun play() {
        isPlaying = true
    }

    /**
     * Stops the time where it is.
     *
     * @since 1.0.0
     */
    public fun pause() {
        isPlaying = false
    }

    /**
     * Pauses a playing playback and plays a paused one.
     *
     * @since 1.0.0
     */
    public fun toggle() {
        isPlaying = !isPlaying
    }

    /**
     * Jumps to a point in time, keeping whether the playback plays.
     *
     * @param to the time that has passed after the jump.
     * @since 1.0.0
     */
    public fun seek(to: Duration) {
        elapsedNanos = to.inWholeNanoseconds
    }

    /**
     * Goes back to the start time, keeping whether the playback plays.
     *
     * @since 1.0.0
     */
    public fun reset() {
        elapsedNanos = start.inWholeNanoseconds
    }

    internal fun advance(nanos: Long) {
        if (!isPlaying) return
        elapsedNanos += (nanos * speed.toDouble()).toLong()
    }

    override fun toString(): String =
        "ArtPlayback(elapsed=$elapsed, isPlaying=$isPlaying, speed=$speed, loop=${loop ?: "null"}, isPingPong=$isPingPong)"
}

/**
 * Remembers an [ArtPlayback]. The values only set where it starts; change them on the playback afterwards.
 *
 * @param start the time the playback starts at and goes back to on [ArtPlayback.reset].
 * @param isPlaying whether the playback starts playing.
 * @param speed the factor of the speed the time passes at.
 * @param loop the length after which the time starts over, or `null` to run on.
 * @param isPingPong whether a loop runs backwards every second time instead of jumping back.
 * @return the playback.
 * @since 1.0.0
 */
@Composable
public fun rememberArtPlayback(
    start: Duration = Duration.ZERO,
    isPlaying: Boolean = true,
    speed: Float = 1f,
    loop: Duration? = null,
    isPingPong: Boolean = false,
): ArtPlayback = remember {
    ArtPlayback(start = start, isPlaying = isPlaying, speed = speed, loop = loop, isPingPong = isPingPong)
}
