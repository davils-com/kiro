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

package com.davils.kiro.ui.audio

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private val restartThreshold: Duration = 3.seconds

/**
 * The state of an audio player: its queue, the current track, the position in it and the settings of the sound.
 *
 * Every player of Kiro reads this state and reports [AudioAction]s; pass [apply] as `onAction` to handle them in
 * memory, and call [advance] and [buffer] from the playback engine.
 *
 * @param queue the tracks to play, in order.
 * @param shuffle orders the tracks after the current one while the queue is shuffled; by default a cryptographically
 * secure random order.
 * @since 1.0.0
 */
@Stable
public class AudioPlayerState(
    queue: List<Track>,
    private val shuffle: (List<TrackId>) -> List<TrackId> = ::secureShuffled,
) {
    /**
     * The tracks to play, in their original order.
     *
     * @since 1.0.0
     */
    public var queue: List<Track> by mutableStateOf(queue)
        private set

    /**
     * The identifier of the current track, or `null` when the queue is empty.
     *
     * @since 1.0.0
     */
    public var current: TrackId? by mutableStateOf(queue.firstOrNull()?.id)
        private set

    /**
     * The position in the current track.
     *
     * @since 1.0.0
     */
    public var position: Duration by mutableStateOf(Duration.ZERO)
        private set

    /**
     * How far the current track is loaded.
     *
     * @since 1.0.0
     */
    public var buffered: Duration by mutableStateOf(Duration.ZERO)
        private set

    /**
     * Whether the current track is playing.
     *
     * @since 1.0.0
     */
    public var isPlaying: Boolean by mutableStateOf(false)
        private set

    /**
     * The volume between `0` and `1`.
     *
     * @since 1.0.0
     */
    public var volume: Float by mutableFloatStateOf(1f)
        private set

    /**
     * Whether the sound is muted.
     *
     * @since 1.0.0
     */
    public var isMuted: Boolean by mutableStateOf(false)
        private set

    /**
     * Whether the queue plays in a shuffled order.
     *
     * @since 1.0.0
     */
    public var isShuffled: Boolean by mutableStateOf(false)
        private set

    /**
     * How the playback continues at the end of a track.
     *
     * @since 1.0.0
     */
    public var repeat: RepeatMode by mutableStateOf(RepeatMode.Off)
        private set

    internal var direction: Int by mutableIntStateOf(1)
        private set

    private var order: List<TrackId> = queue.map { track -> track.id }

    /**
     * The current track, or `null` when the queue is empty.
     *
     * @since 1.0.0
     */
    public val track: Track?
        get() = queue.firstOrNull { track -> track.id == current }

    /**
     * The position in the current track as a fraction between `0` and `1`.
     *
     * @since 1.0.0
     */
    public val progress: Float
        get() = progressOf(position = position, duration = track?.duration)

    /**
     * Changes the state as an action asks.
     *
     * @param action the action to handle.
     * @since 1.0.0
     */
    public fun apply(action: AudioAction) {
        when (action) {
            AudioAction.Play -> isPlaying = track != null
            AudioAction.Pause -> isPlaying = false
            AudioAction.Next -> skip(step = 1)
            AudioAction.Previous -> back()
            is AudioAction.Seek -> position = clamped(action.position)
            is AudioAction.Volume -> changeVolume(action.level)
            AudioAction.Mute -> isMuted = !isMuted
            AudioAction.Shuffle -> toggleShuffle()
            AudioAction.Repeat -> cycleRepeat()
            is AudioAction.Select -> select(id = action.track, step = 1)
        }
    }

    /**
     * Moves the position on while the track is playing and continues with the next track at its end.
     *
     * @param by how far the playback moved on.
     * @since 1.0.0
     */
    public fun advance(by: Duration) {
        val playing = track?.takeIf { isPlaying } ?: return
        val next = position + by
        if (next < playing.duration) {
            position = next
            return
        }
        finish()
    }

    /**
     * Records how far the current track is loaded.
     *
     * @param until the loaded position, kept within the track.
     * @since 1.0.0
     */
    public fun buffer(until: Duration) {
        buffered = clamped(until)
    }

    /**
     * Replaces the queue and starts at one of its tracks.
     *
     * @param queue the new tracks.
     * @param current the track to start at, the first one by default.
     * @since 1.0.0
     */
    public fun load(queue: List<Track>, current: TrackId? = queue.firstOrNull()?.id) {
        this.queue = queue
        order = orderOf(queue = queue, current = current)
        this.current = current
        position = Duration.ZERO
        buffered = Duration.ZERO
    }

    private fun cycleRepeat() {
        val modes = RepeatMode.entries
        repeat = modes[(repeat.ordinal + 1) % modes.size]
    }

    private fun finish() {
        if (repeat == RepeatMode.One) {
            position = Duration.ZERO
            return
        }
        skip(step = 1)
    }

    private fun back() {
        if (position > restartThreshold) {
            position = Duration.ZERO
            return
        }
        skip(step = -1)
    }

    private fun skip(step: Int) {
        val index = order.indexOf(current) + step
        val isWrapping = repeat == RepeatMode.All && order.isNotEmpty()
        val target = order.getOrNull(index.mod(order.size.coerceAtLeast(1)).takeIf { isWrapping } ?: index)
        if (target == null) {
            isPlaying = false
            position = track?.duration ?: Duration.ZERO
            return
        }
        select(id = target, step = step)
    }

    private fun select(id: TrackId, step: Int) {
        if (queue.none { track -> track.id == id }) return
        direction = step
        current = id
        position = Duration.ZERO
        buffered = Duration.ZERO
        isPlaying = true
    }

    private fun changeVolume(level: Float) {
        volume = level.coerceIn(minimumValue = 0f, maximumValue = 1f)
        isMuted = false
    }

    private fun toggleShuffle() {
        isShuffled = !isShuffled
        order = orderOf(queue = queue, current = current)
    }

    private fun orderOf(queue: List<Track>, current: TrackId?): List<TrackId> {
        val ids = queue.map { track -> track.id }
        if (!isShuffled) return ids
        val rest = shuffle(ids - setOfNotNull(current))
        return listOfNotNull(current) + rest
    }

    private fun clamped(value: Duration): Duration =
        value.coerceIn(minimumValue = Duration.ZERO, maximumValue = track?.duration ?: Duration.ZERO)
}
