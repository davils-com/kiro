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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.ui.util.lerp
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val TOLERANCE: Float = 0.0001f
private const val HALF: Float = 5f
private const val MIDDLE: Float = 10f
private const val THREE_QUARTERS: Float = 15f
private const val END: Float = 20f
private const val SINGLE: Float = 3f
private const val BETWEEN: Float = 1.5f

private val mix: (Float, Float, Float) -> Float = { start, stop, fraction ->
    lerp(start = start, stop = stop, fraction = fraction)
}

private val ramp: Keyframes<Float> = Keyframes(
    frames = listOf(
        Keyframe(time = ZERO, value = 0f),
        Keyframe(time = 1.seconds, value = MIDDLE, easing = Easings.steps(count = 2)),
        Keyframe(time = 2.seconds, value = END),
    ),
    lerp = mix,
)

class KeyframesTest : FunSpec({
    test("mixes linearly between two keyframes") {
        ramp.valueAt(500.milliseconds) shouldBe (HALF plusOrMinus TOLERANCE)
        ramp.valueAt(ArtTime.of(1.seconds)) shouldBe (MIDDLE plusOrMinus TOLERANCE)
    }

    test("eases a segment by the easing of its first keyframe") {
        ramp.valueAt(1400.milliseconds) shouldBe (MIDDLE plusOrMinus TOLERANCE)
        ramp.valueAt(1600.milliseconds) shouldBe (THREE_QUARTERS plusOrMinus TOLERANCE)
    }

    test("holds the first and the last value when clamped") {
        ramp.valueAt((-1).seconds) shouldBe (0f plusOrMinus TOLERANCE)
        ramp.valueAt(5.seconds) shouldBe (END plusOrMinus TOLERANCE)
        ramp.duration shouldBe 2.seconds
    }

    test("loops and ping-pongs") {
        val looped = ramp.copy(loop = KeyframeLoop.Loop)
        val pingPong = ramp.copy(loop = KeyframeLoop.PingPong)

        looped.valueAt(2500.milliseconds) shouldBe (HALF plusOrMinus TOLERANCE)
        pingPong.valueAt(2500.milliseconds) shouldBe (THREE_QUARTERS plusOrMinus TOLERANCE)
    }

    test("keeps a single keyframe and jumps over keyframes at the same time") {
        val single = Keyframes(frames = listOf(Keyframe(time = 1.seconds, value = SINGLE)), lerp = mix)
        val jump = Keyframes(
            frames = listOf(
                Keyframe(time = ZERO, value = 0f),
                Keyframe(time = ZERO, value = 1f),
                Keyframe(time = 1.seconds, value = 2f),
            ),
            lerp = mix,
        )

        val held = Keyframes(
            frames = listOf(
                Keyframe(time = ZERO, value = 0f),
                Keyframe(time = 1.seconds, value = 1f),
                Keyframe(time = 1.seconds, value = 2f),
            ),
            lerp = mix,
        )

        held.valueAt(5.seconds) shouldBe 2f
        single.valueAt(5.seconds) shouldBe SINGLE
        jump.valueAt(ZERO) shouldBe (1f plusOrMinus TOLERANCE)
        jump.valueAt(500.milliseconds) shouldBe (BETWEEN plusOrMinus TOLERANCE)
    }

    test("rejects no keyframes and keyframes out of order") {
        val backwards = listOf(Keyframe(time = 1.seconds, value = 0f), Keyframe(time = ZERO, value = 1f))

        shouldThrow<IllegalArgumentException> { Keyframes(frames = emptyList(), lerp = mix) }
        shouldThrow<IllegalArgumentException> { Keyframes(frames = backwards, lerp = mix) }
    }

    test("a keyframe is a value") {
        val frame = Keyframe(time = 1.seconds, value = 2f)

        frame.copy() shouldBe frame
        frame.copy().hashCode() shouldBe frame.hashCode()
        frame.copy(value = SINGLE) shouldNotBe frame
        frame.copy(easing = Easings.bounceOut) shouldNotBe frame
        frame.copy(time = 2.seconds) shouldNotBe frame
        frame.toString() shouldContain "value=2.0"
        Keyframe<Float?>(time = ZERO, value = null).toString() shouldContain "value=null"
        frame.easing shouldBe LinearEasing
        frame.equals("frame") shouldBe false
    }

    test("keyframes are values") {
        val shorter = ramp.frames.take(2)

        ramp.copy() shouldBe ramp
        ramp.copy().hashCode() shouldBe ramp.hashCode()
        ramp.copy(loop = KeyframeLoop.Loop) shouldNotBe ramp
        ramp.copy(frames = shorter) shouldNotBe ramp
        ramp.copy(lerp = { start, _, _ -> start }) shouldNotBe ramp
        ramp.toString() shouldContain "loop=Clamp"
        ramp.equals("ramp") shouldBe false
    }
})
