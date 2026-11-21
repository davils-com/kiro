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

import com.davils.kiro.ui.audio.fixture.FIRST
import com.davils.kiro.ui.audio.fixture.SECOND
import com.davils.kiro.ui.audio.fixture.THIRD
import com.davils.kiro.ui.audio.fixture.tracks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private const val HALF: Float = 0.5f
private const val LOUD: Float = 2f

private fun stateOf(): AudioPlayerState = AudioPlayerState(queue = tracks, shuffle = { ids -> ids.reversed() })

class AudioPlayerStateTest : FunSpec({
    context("playback") {
        test("plays, pauses, seeks within the track and reports its progress") {
            val state = stateOf()
            state.apply(AudioAction.Play)
            state.apply(AudioAction.Seek(90.seconds))

            state.isPlaying shouldBe true
            state.progress shouldBe HALF
            state.apply(AudioAction.Seek(10.minutes))
            state.position shouldBe 3.minutes
            state.apply(AudioAction.Pause)
            state.isPlaying shouldBe false
        }

        test("advances while playing, moves on at the end and stops after the last track") {
            val state = stateOf()
            state.advance(1.minutes)
            state.position shouldBe Duration.ZERO
            state.apply(AudioAction.Play)
            state.advance(1.minutes)
            state.position shouldBe 1.minutes
            state.advance(5.minutes)
            state.track?.title shouldBe SECOND
            state.apply(AudioAction.Next)
            state.apply(AudioAction.Next)
            state.isPlaying shouldBe false
            state.track?.title shouldBe THIRD
        }

        test("repeats one track and wraps the queue when repeating all") {
            val state = stateOf()
            state.apply(AudioAction.Play)
            state.apply(AudioAction.Repeat)
            state.repeat shouldBe RepeatMode.All
            state.apply(AudioAction.Previous)
            state.track?.title shouldBe THIRD
            state.apply(AudioAction.Repeat)
            state.advance(5.minutes)
            state.track?.title shouldBe THIRD
            state.position shouldBe Duration.ZERO
            state.apply(AudioAction.Repeat)
            state.repeat shouldBe RepeatMode.Off
        }

        test("restarts a track that already played before going back") {
            val state = stateOf()
            state.apply(AudioAction.Select(TrackId(SECOND)))
            state.apply(AudioAction.Seek(10.seconds))
            state.apply(AudioAction.Previous)
            state.track?.title shouldBe SECOND
            state.position shouldBe Duration.ZERO
            state.apply(AudioAction.Previous)
            state.track?.title shouldBe FIRST
            state.direction shouldBe -1
        }
    }

    context("queue") {
        test("selects known tracks only, shuffles behind the current track and loads a new queue") {
            val state = stateOf()
            state.apply(AudioAction.Select(TrackId("missing")))
            state.track?.title shouldBe FIRST
            state.apply(AudioAction.Shuffle)
            state.isShuffled shouldBe true
            state.apply(AudioAction.Next)
            state.track?.title shouldBe THIRD
            state.load(queue = tracks.reversed())
            state.track?.title shouldBe THIRD
            state.load(queue = emptyList())
            state.track shouldBe null
            state.apply(AudioAction.Play)
            state.isPlaying shouldBe false
            state.apply(AudioAction.Next)
            state.position shouldBe Duration.ZERO
        }
    }

    context("sound") {
        test("clamps the volume, unmutes on a change and buffers within the track") {
            val state = stateOf()
            state.apply(AudioAction.Mute)
            state.isMuted shouldBe true
            state.apply(AudioAction.Volume(LOUD))
            state.volume shouldBe 1f
            state.isMuted shouldBe false
            state.buffer(until = 1.minutes)
            state.buffered shouldBe 1.minutes
            progressOf(position = 1.minutes, duration = null) shouldBe 0f
        }
    }

    context("time") {
        test("is written as a clock with hours only when needed and never runs below zero") {
            clockOf(65.seconds) shouldBe "1:05"
            clockOf(Duration.ZERO) shouldBe "0:00"
            clockOf(-Duration.INFINITE) shouldBe "0:00"
            clockOf(1.hours + 2.minutes + 3.seconds) shouldBe "1:02:03"
        }
    }

    context("shuffle") {
        test("orders every track exactly once by default") {
            val ids = tracks.map { track -> track.id }

            secureShuffled(ids).toSet() shouldBe ids.toSet()
            secureShuffled(ids).size shouldBe ids.size
        }
    }

    context("actions") {
        test("compare by their values and describe themselves") {
            AudioAction.Seek(1.seconds) shouldBe AudioAction.Seek(1.seconds)
            AudioAction.Seek(1.seconds).hashCode() shouldBe AudioAction.Seek(1.seconds).hashCode()
            AudioAction.Volume(HALF) shouldBe AudioAction.Volume(HALF)
            AudioAction.Volume(HALF).hashCode() shouldBe AudioAction.Volume(HALF).hashCode()
            AudioAction.Select(TrackId(FIRST)) shouldBe AudioAction.Select(TrackId(FIRST))
            AudioAction.Select(TrackId(FIRST)).hashCode() shouldBe AudioAction.Select(TrackId(FIRST)).hashCode()
            AudioAction.Seek(1.seconds).toString() shouldBe "Seek(position=1s)"
            AudioAction.Volume(HALF).toString() shouldBe "Volume(level=0.5)"
            AudioAction.Select(TrackId(FIRST)).toString() shouldBe "Select(track=TrackId(value=$FIRST))"
        }
    }
})
