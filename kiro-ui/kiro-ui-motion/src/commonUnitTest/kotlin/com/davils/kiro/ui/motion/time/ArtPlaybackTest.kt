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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val SECOND: Long = 1_000_000_000L

class ArtPlaybackTest : FunSpec({
    test("advances while playing, at its speed") {
        val playback = ArtPlayback(speed = 2f)

        playback.advance(nanos = SECOND)

        playback.elapsed shouldBe 2.seconds
        playback.time shouldBe ArtTime.of(2.seconds)
    }

    test("stands still while paused and plays again") {
        val playback = ArtPlayback(isPlaying = false)

        playback.advance(nanos = SECOND)
        playback.elapsed shouldBe Duration.ZERO

        playback.play()
        playback.advance(nanos = SECOND)
        playback.pause()
        playback.advance(nanos = SECOND)

        playback.elapsed shouldBe 1.seconds
        playback.isPlaying shouldBe false
    }

    test("toggles between playing and paused") {
        val playback = ArtPlayback()

        playback.toggle()
        playback.isPlaying shouldBe false
        playback.toggle()
        playback.isPlaying shouldBe true
    }

    test("seeks and resets to its start") {
        val playback = ArtPlayback(start = 3.seconds)

        playback.seek(to = 10.seconds)
        playback.elapsed shouldBe 10.seconds

        playback.reset()
        playback.elapsed shouldBe 3.seconds
    }

    test("loops and ping-pongs its time") {
        val playback = ArtPlayback(start = 2500.milliseconds, loop = 1.seconds)

        playback.time shouldBe ArtTime.of(500.milliseconds)

        playback.isPingPong = true
        playback.seek(to = 1250.milliseconds)
        playback.time shouldBe ArtTime.of(750.milliseconds)

        playback.loop = null
        playback.time shouldBe ArtTime.of(1250.milliseconds)
    }

    test("runs backwards at a negative speed") {
        val playback = ArtPlayback(start = 2.seconds)

        playback.speed = -1f
        playback.advance(nanos = SECOND)

        playback.elapsed shouldBe 1.seconds
    }

    test("describes itself") {
        ArtPlayback(loop = 1.seconds).toString() shouldContain "loop=1s"
        ArtPlayback().toString() shouldContain "loop=null"
    }
})
