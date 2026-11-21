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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

private const val ZOOM: Float = 48f
private const val MINIMUM_ZOOM: Float = 2f
private const val MAXIMUM_ZOOM: Float = 2_000f
private const val SHORTEST_MILLISECONDS: Int = 50
private const val QUIETEST: Float = -60f
private const val LOUDEST: Float = 12f

private val defaultShortest: Duration = SHORTEST_MILLISECONDS.milliseconds
private val defaultGains: DecibelRange = DecibelRange(floor = Decibels(QUIETEST), ceiling = Decibels(LOUDEST))

/**
 * The state of a timeline as an editing program keeps it: the tracks and their clips, the selected clip, the
 * playhead, the zoom and the scroll position. [apply] carries out a [TimelineAction] within the limits of a timeline:
 * nothing moves before the start, no clip is trimmed beyond its recording or below [shortest], no fade is longer than
 * its clip allows, and locked clips stay as they are. It changes only what the timeline shows; it edits no audio.
 *
 * @param lanes the tracks of the timeline.
 * @param zoom the zoom to start with, in density-independent pixels per second.
 * @property zooms the zooms the timeline allows, in density-independent pixels per second.
 * @property gains the gains a clip may take.
 * @property shortest the shortest length a clip may be trimmed to.
 * @since 1.0.0
 */
@Stable
public class TimelineState(
    lanes: List<TrackLane>,
    zoom: Float = ZOOM,
    public val zooms: ClosedFloatingPointRange<Float> = MINIMUM_ZOOM..MAXIMUM_ZOOM,
    public val gains: DecibelRange = defaultGains,
    public val shortest: Duration = defaultShortest,
) {
    /**
     * The tracks of the timeline.
     *
     * @since 1.0.0
     */
    public var lanes: List<TrackLane> by mutableStateOf(lanes)
        private set

    /**
     * The selected clip, or `null` when none is selected.
     *
     * @since 1.0.0
     */
    public var selection: ClipId? by mutableStateOf(null)
        private set

    /**
     * The position of the playhead.
     *
     * @since 1.0.0
     */
    public var playhead: Duration by mutableStateOf(Duration.ZERO)
        private set

    /**
     * The zoom in density-independent pixels per second.
     *
     * @since 1.0.0
     */
    public var zoom: Float by mutableFloatStateOf(zoom.coerceIn(zooms))
        private set

    /**
     * The time at the left edge of the timeline.
     *
     * @since 1.0.0
     */
    public var scroll: Duration by mutableStateOf(Duration.ZERO)
        private set

    /**
     * Where the last clip of the timeline ends.
     *
     * @since 1.0.0
     */
    public val duration: Duration
        get() = clips().maxOfOrNull { clip -> clip.end } ?: Duration.ZERO

    /**
     * Changes the state as an action asks.
     *
     * @param action the action to handle.
     * @since 1.0.0
     */
    public fun apply(action: TimelineAction) {
        when (action) {
            is TimelineAction.Select -> selection = action.clip?.takeIf { id -> clips().any { clip -> clip.id == id } }

            is TimelineAction.Move -> change(
                action.clip,
            ) { clip -> clip.copy(start = action.start.coerceAtLeast(Duration.ZERO)) }

            is TimelineAction.Trim -> change(
                action.clip,
            ) { clip -> trimmed(clip = clip, edge = action.edge, at = action.at) }

            is TimelineAction.Fade -> change(
                action.clip,
            ) { clip -> faded(clip = clip, edge = action.edge, length = action.length) }

            is TimelineAction.Gain -> change(action.clip) { clip -> clip.copy(gain = gained(action.gain)) }

            is TimelineAction.Seek -> playhead = action.position.coerceIn(
                minimumValue = Duration.ZERO,
                maximumValue = duration,
            )

            is TimelineAction.Zoom -> zoomTo(zoom = action.zoom, anchor = action.anchor)

            is TimelineAction.Scroll -> scroll = action.offset.coerceAtLeast(Duration.ZERO)

            is TimelineAction.Mute -> switch(action.lane) { lane -> lane.copy(isMuted = !lane.isMuted) }

            is TimelineAction.Solo -> switch(action.lane) { lane -> lane.copy(isSoloed = !lane.isSoloed) }

            is TimelineAction.Arm -> switch(action.lane) { lane -> lane.copy(isArmed = !lane.isArmed) }
        }
    }

    /**
     * Replaces the tracks and clears the selection when its clip is gone.
     *
     * @param lanes the new tracks.
     * @since 1.0.0
     */
    public fun load(lanes: List<TrackLane>) {
        this.lanes = lanes
        selection = selection?.takeIf { id -> clips().any { clip -> clip.id == id } }
    }

    private fun clips(): List<Clip> = lanes.flatMap { lane -> lane.clips }

    private fun change(id: ClipId, change: (Clip) -> Clip) {
        lanes = lanes.map { lane ->
            lane.copy(
                clips = lane.clips.map { clip -> change(clip).takeIf { clip.id == id && !clip.isLocked } ?: clip },
            )
        }
    }

    private fun switch(id: TrackLaneId, change: (TrackLane) -> TrackLane) {
        lanes = lanes.map { lane -> change(lane).takeIf { lane.id == id } ?: lane }
    }

    private fun trimmed(clip: Clip, edge: ClipEdge, at: Duration): Clip {
        val trimmed = when (edge) {
            ClipEdge.Start -> {
                val earliest = (clip.start - clip.sourceOffset).coerceAtLeast(Duration.ZERO)
                val start = at.coerceIn(
                    minimumValue = earliest,
                    maximumValue = (clip.end - shortest).coerceAtLeast(earliest),
                )
                clip.copy(
                    start = start,
                    length = clip.end - start,
                    sourceOffset = clip.sourceOffset + (start - clip.start),
                )
            }

            ClipEdge.End -> {
                val latest = clip.start + (clip.peaks.duration - clip.sourceOffset)
                val end = at.coerceIn(
                    minimumValue = (clip.start + shortest).coerceAtMost(latest),
                    maximumValue = latest,
                )
                clip.copy(length = end - clip.start)
            }
        }
        val fadeIn = trimmed.fadeIn.coerceAtMost(trimmed.length)
        return trimmed.copy(fadeIn = fadeIn, fadeOut = trimmed.fadeOut.coerceAtMost(trimmed.length - fadeIn))
    }

    private fun faded(clip: Clip, edge: ClipEdge, length: Duration): Clip = when (edge) {
        ClipEdge.Start -> clip.copy(
            fadeIn = length.coerceIn(minimumValue = Duration.ZERO, maximumValue = clip.length - clip.fadeOut),
        )

        ClipEdge.End -> clip.copy(
            fadeOut = length.coerceIn(minimumValue = Duration.ZERO, maximumValue = clip.length - clip.fadeIn),
        )
    }

    private fun gained(gain: Decibels): Decibels =
        Decibels(gain.value.coerceIn(minimumValue = gains.floor.value, maximumValue = gains.ceiling.value))

    private fun zoomTo(zoom: Float, anchor: Duration) {
        val next = zoom.coerceIn(zooms)
        val offset = (anchor - scroll) * (this.zoom / next).toDouble()
        this.zoom = next
        scroll = (anchor - offset).coerceAtLeast(Duration.ZERO)
    }
}

/**
 * Remembers a [TimelineState] across recompositions.
 *
 * @param lanes the tracks to start with.
 * @param zoom the zoom to start with, in density-independent pixels per second.
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberTimelineState(lanes: List<TrackLane>, zoom: Float = ZOOM): TimelineState =
    remember { TimelineState(lanes = lanes, zoom = zoom) }
