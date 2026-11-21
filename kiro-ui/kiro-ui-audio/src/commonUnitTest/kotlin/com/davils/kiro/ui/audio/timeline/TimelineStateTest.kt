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

import com.davils.kiro.ui.audio.fixture.ValueSample
import com.davils.kiro.ui.audio.fixture.clipOf
import com.davils.kiro.ui.audio.fixture.lanesOf
import com.davils.kiro.ui.audio.signal.Decibels
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val DOUBLE: Float = 200f

private val a: ClipId = ClipId("a")
private val locked: ClipId = ClipId("locked")
private val one: TrackLaneId = TrackLaneId("one")

private fun TimelineState.clip(id: ClipId): Clip = lanes.flatMap { lane -> lane.clips }.first { clip -> clip.id == id }

private fun actions(): List<Pair<TimelineAction, TimelineAction>> = listOf(
    TimelineAction.Select(a) to TimelineAction.Select(null),
    TimelineAction.Move(a, 1.seconds) to TimelineAction.Move(a, 2.seconds),
    TimelineAction.Trim(a, ClipEdge.Start, 1.seconds) to TimelineAction.Trim(a, ClipEdge.End, 1.seconds),
    TimelineAction.Fade(a, ClipEdge.Start, 1.seconds) to TimelineAction.Fade(a, ClipEdge.Start, 2.seconds),
    TimelineAction.Gain(a, Decibels(value = 1f)) to TimelineAction.Gain(a, Decibels(value = 2f)),
    TimelineAction.Seek(1.seconds) to TimelineAction.Seek(2.seconds),
    TimelineAction.Zoom(1f, 1.seconds) to TimelineAction.Zoom(2f, 1.seconds),
    TimelineAction.Scroll(1.seconds) to TimelineAction.Scroll(2.seconds),
    TimelineAction.Mute(one) to TimelineAction.Mute(TrackLaneId("two")),
    TimelineAction.Solo(one) to TimelineAction.Solo(TrackLaneId("two")),
    TimelineAction.Arm(one) to TimelineAction.Arm(TrackLaneId("two")),
)

class TimelineStateTest : FunSpec({
    test("compares and describes its models and actions") {
        val clip = clipOf()
        val lane = lanesOf().first()
        listOf(
            ValueSample(name = "Clip", value = clip, copy = clip.copy(), changed = clip.copy(tint = 3)),
            ValueSample(name = "TrackLane", value = lane, copy = lane.copy(), changed = lane.copy(isArmed = true)),
        ).forEach { sample ->
            sample.copy shouldBe sample.value
            sample.copy.hashCode() shouldBe sample.value.hashCode()
            sample.changed shouldNotBe sample.value
            sample.value shouldNotBe Any()
            sample.value.toString() shouldStartWith sample.name
        }
        actions().forEach { (action, other) ->
            action shouldBe action
            action.hashCode() shouldBe action.hashCode()
            action shouldNotBe other
            action shouldNotBe Any()
            action.toString() shouldStartWith action::class.simpleName.orEmpty()
        }
        TimelineAction.Select(null).toString() shouldBe "Select(clip=none)"
        clip.end shouldBe 6.seconds
    }

    test("refuses a clip with negative times") {
        shouldThrow<IllegalArgumentException> { clipOf(start = -1) }
    }

    test("selects existing clips only") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Select(a))
        state.selection shouldBe a
        state.apply(TimelineAction.Select(ClipId("missing")))
        state.selection shouldBe null
        state.apply(TimelineAction.Select(a))
        state.load(lanes = lanesOf().drop(1))
        state.selection shouldBe null
    }

    test("moves a clip but never before the start and never a locked clip") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Move(a, 5.seconds))
        state.clip(a).start shouldBe 5.seconds
        state.apply(TimelineAction.Move(a, -clipOf().length))
        state.clip(a).start shouldBe Duration.ZERO
        state.apply(TimelineAction.Move(locked, 9.seconds))
        state.clip(locked).start shouldBe 2.seconds
        state.duration shouldBe 12.seconds
    }

    test("trims the start within the recording and the shortest length") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Trim(a, ClipEdge.Start, 3.seconds))
        state.clip(a).start shouldBe 3.seconds
        state.clip(a).sourceOffset shouldBe 3.seconds
        state.clip(a).length shouldBe 3.seconds
        state.apply(TimelineAction.Trim(a, ClipEdge.Start, Duration.ZERO))
        state.clip(a).start shouldBe Duration.ZERO
        state.clip(a).sourceOffset shouldBe Duration.ZERO
        state.apply(TimelineAction.Trim(a, ClipEdge.Start, 10.seconds))
        state.clip(a).length shouldBe state.shortest
        state.clip(a).fadeIn shouldBe state.shortest
        state.clip(a).fadeOut shouldBe Duration.ZERO
    }

    test("trims the end within the recording") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Trim(a, ClipEdge.End, 40.seconds))
        state.clip(a).length shouldBe 14.seconds
        state.apply(TimelineAction.Trim(a, ClipEdge.End, 1.seconds))
        state.clip(a).length shouldBe 50.milliseconds
    }

    test("keeps fades within their clip") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Fade(a, ClipEdge.Start, 10.seconds))
        state.clip(a).fadeIn shouldBe 3.seconds
        state.apply(TimelineAction.Fade(a, ClipEdge.End, 10.seconds))
        state.clip(a).fadeOut shouldBe 1.seconds
        state.apply(TimelineAction.Fade(a, ClipEdge.End, (-1).seconds))
        state.clip(a).fadeOut shouldBe Duration.ZERO
    }

    test("keeps the gain within its range") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Gain(a, Decibels(value = 30f)))
        state.clip(a).gain shouldBe state.gains.ceiling
        state.apply(TimelineAction.Gain(a, Decibels(value = -3f)))
        state.clip(a).gain shouldBe Decibels(value = -3f)
    }

    test("seeks, scrolls and zooms within the timeline") {
        val state = TimelineState(lanes = lanesOf(), zoom = 100f)
        state.apply(TimelineAction.Seek(30.seconds))
        state.playhead shouldBe state.duration
        state.apply(TimelineAction.Scroll((-1).seconds))
        state.scroll shouldBe Duration.ZERO
        state.apply(TimelineAction.Scroll(2.seconds))
        state.apply(TimelineAction.Zoom(zoom = DOUBLE, anchor = 4.seconds))
        state.zoom shouldBe DOUBLE
        state.scroll shouldBe 3.seconds
        val scroll = state.scroll
        state.apply(TimelineAction.Zoom(zoom = 1_000_000f, anchor = scroll))
        state.zoom shouldBe state.zooms.endInclusive
        state.scroll shouldBe scroll
    }

    test("switches mute, solo and record of a track") {
        val state = TimelineState(lanes = lanesOf())
        state.apply(TimelineAction.Mute(one))
        state.apply(TimelineAction.Solo(one))
        state.apply(TimelineAction.Arm(one))
        val lane = state.lanes.first()
        lane.isMuted shouldBe true
        lane.isSoloed shouldBe true
        lane.isArmed shouldBe true
        state.lanes.last().isMuted shouldBe false
    }
})
