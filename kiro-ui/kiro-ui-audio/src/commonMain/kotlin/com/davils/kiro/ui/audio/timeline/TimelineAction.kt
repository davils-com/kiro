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

import com.davils.kiro.ui.audio.signal.Decibels
import kotlin.time.Duration

private const val NONE: String = "none"

/**
 * What the user asks a timeline to do, from a drag on a clip to a switch of a track. The timeline only asks;
 * [TimelineState.apply] or the caller decides what changes.
 *
 * @since 1.0.0
 */
public sealed interface TimelineAction {
    /**
     * Selects a clip, or clears the selection.
     *
     * @property clip the clip to select, or `null` to select none.
     * @since 1.0.0
     */
    public class Select(public val clip: ClipId?) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Select && clip == other.clip

        override fun hashCode(): Int = clip?.hashCode() ?: 0

        override fun toString(): String = "Select(clip=${clip ?: NONE})"
    }

    /**
     * Moves a clip along its track.
     *
     * @property clip the clip to move.
     * @property start where the clip begins after the move.
     * @since 1.0.0
     */
    public class Move(public val clip: ClipId, public val start: Duration) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Move && clip == other.clip && start == other.start

        override fun hashCode(): Int = listOf(clip, start).hashCode()

        override fun toString(): String = "Move(clip=$clip, start=$start)"
    }

    /**
     * Trims a clip at one of its edges, uncovering or hiding a part of its recording.
     *
     * @property clip the clip to trim.
     * @property edge the edge to trim.
     * @property at where the edge lies on the timeline after the trim.
     * @since 1.0.0
     */
    public class Trim(public val clip: ClipId, public val edge: ClipEdge, public val at: Duration) : TimelineAction {
        override fun equals(other: Any?): Boolean =
            other is Trim && clip == other.clip && edge == other.edge && at == other.at

        override fun hashCode(): Int = listOf(clip, edge, at).hashCode()

        override fun toString(): String = "Trim(clip=$clip, edge=$edge, at=$at)"
    }

    /**
     * Changes how long a clip fades at one of its edges.
     *
     * @property clip the clip to fade.
     * @property edge the edge to fade at.
     * @property length how long the fade lasts.
     * @since 1.0.0
     */
    public class Fade(
        public val clip: ClipId,
        public val edge: ClipEdge,
        public val length: Duration,
    ) : TimelineAction {
        override fun equals(other: Any?): Boolean =
            other is Fade && clip == other.clip && edge == other.edge && length == other.length

        override fun hashCode(): Int = listOf(clip, edge, length).hashCode()

        override fun toString(): String = "Fade(clip=$clip, edge=$edge, length=$length)"
    }

    /**
     * Changes the gain of a clip.
     *
     * @property clip the clip to change.
     * @property gain the new gain.
     * @since 1.0.0
     */
    public class Gain(public val clip: ClipId, public val gain: Decibels) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Gain && clip == other.clip && gain == other.gain

        override fun hashCode(): Int = listOf(clip, gain).hashCode()

        override fun toString(): String = "Gain(clip=$clip, gain=$gain)"
    }

    /**
     * Moves the playhead.
     *
     * @property position the new position of the playhead.
     * @since 1.0.0
     */
    public class Seek(public val position: Duration) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Seek && position == other.position

        override fun hashCode(): Int = position.hashCode()

        override fun toString(): String = "Seek(position=$position)"
    }

    /**
     * Zooms the timeline while the time under an anchor stays in place.
     *
     * @property zoom the new zoom in density-independent pixels per second.
     * @property anchor the time that stays where it is on the screen.
     * @since 1.0.0
     */
    public class Zoom(public val zoom: Float, public val anchor: Duration) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Zoom && zoom == other.zoom && anchor == other.anchor

        override fun hashCode(): Int = listOf(zoom, anchor).hashCode()

        override fun toString(): String = "Zoom(zoom=$zoom, anchor=$anchor)"
    }

    /**
     * Scrolls the timeline.
     *
     * @property offset the time at the left edge of the timeline.
     * @since 1.0.0
     */
    public class Scroll(public val offset: Duration) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Scroll && offset == other.offset

        override fun hashCode(): Int = offset.hashCode()

        override fun toString(): String = "Scroll(offset=$offset)"
    }

    /**
     * Mutes or unmutes a track.
     *
     * @property lane the track to switch.
     * @since 1.0.0
     */
    public class Mute(public val lane: TrackLaneId) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Mute && lane == other.lane

        override fun hashCode(): Int = lane.hashCode()

        override fun toString(): String = "Mute(lane=$lane)"
    }

    /**
     * Switches whether a track plays solo.
     *
     * @property lane the track to switch.
     * @since 1.0.0
     */
    public class Solo(public val lane: TrackLaneId) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Solo && lane == other.lane

        override fun hashCode(): Int = lane.hashCode()

        override fun toString(): String = "Solo(lane=$lane)"
    }

    /**
     * Arms or disarms a track for recording.
     *
     * @property lane the track to switch.
     * @since 1.0.0
     */
    public class Arm(public val lane: TrackLaneId) : TimelineAction {
        override fun equals(other: Any?): Boolean = other is Arm && lane == other.lane

        override fun hashCode(): Int = lane.hashCode()

        override fun toString(): String = "Arm(lane=$lane)"
    }
}
