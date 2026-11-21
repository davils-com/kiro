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

package com.davils.kiro.ui.motion.typewriter

import androidx.compose.animation.core.LinearEasing
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration.Companion.milliseconds

private const val TYPE: Long = 100L
private const val DELETE: Long = 50L
private const val HOLD: Long = 1000L
private const val PUNCTUATION: Long = 300L
private const val PERIOD: Long = 1000L
private const val FADE: Long = 100L
private const val HALF: Float = 0.5f
private const val TOLERANCE: Float = 0.01f
private const val HEY: String = "Hey"
private const val HELP: String = "Help!"
private const val SMILE: String = "a😀"

private val steady: TypewriterStyle = defaultTypewriterStyle(Theme.light()).copy(
    typeDelay = TYPE.milliseconds,
    deleteDelay = DELETE.milliseconds,
    punctuationDelay = PUNCTUATION.milliseconds,
    jitter = 0f,
    hold = HOLD.milliseconds,
)

private fun TypewriterTimeline.texts(): List<String> = marks.map { mark -> mark.text }

private fun TypewriterTimeline.gaps(): List<Long> = marks.zipWithNext { before, after -> after.at - before.at }

private fun TypewriterTimeline.textAt(elapsed: Long): String = markAt(position = positionOf(elapsed = elapsed)).text

private fun alpha(at: Long, period: Long = PERIOD, fade: Long = FADE): Float =
    caretAlphaOf(sinceChange = at, period = period, fade = fade, easing = LinearEasing)

class TypewriterTimelineTest : FunSpec({
    test("writes scripts with the builder") {
        val script = typewriter {
            type("Hi")
            pause()
            pause(duration = FADE.milliseconds)
            delete(count = 1)
            delete()
            repeat()
        }

        script.steps shouldContainExactly listOf(
            TypeStep(text = "Hi"),
            PauseStep(),
            PauseStep(duration = FADE.milliseconds),
            DeleteStep(count = 1),
            DeleteStep(),
        )
        script.isRepeating shouldBe true
        typewriter { type("x") }.isRepeating shouldBe false
    }

    test("cycles through phrases with a hold") {
        val cycle = typewriterCycle(phrases = listOf("A", "B"), hold = FADE.milliseconds)

        cycle.isRepeating shouldBe true
        cycle.steps shouldContainExactly listOf(
            TypeStep(text = "A"),
            PauseStep(duration = FADE.milliseconds),
            DeleteStep(),
            TypeStep(text = "B"),
            PauseStep(duration = FADE.milliseconds),
            DeleteStep(),
        )
        typewriterCycle(phrases = listOf("A")).steps[1] shouldBe PauseStep()
    }

    test("types, holds and deletes character by character") {
        val timeline = timelineOf(script = typewriterCycle(phrases = listOf(HEY)), style = steady, seed = 1)

        timeline.texts() shouldContainExactly listOf("", "H", "He", HEY, "He", "H", "")
        timeline.gaps() shouldContainExactly listOf(TYPE, TYPE, TYPE, HOLD + DELETE, DELETE, DELETE)
        timeline.duration shouldBe timeline.gaps().sum()
        timeline.marks.map { mark -> mark.target }.toSet() shouldBe setOf(HEY)
        timeline.resting shouldBe HEY
    }

    test("aims every mark at the phrase it types") {
        val script = typewriter {
            type("Hello")
            delete(count = 2)
            type("p!")
        }
        val timeline = timelineOf(script = script, style = steady, seed = 1)
        val last = timeline.marks.last()

        timeline.marks.first().target shouldBe "Hello"
        last.text shouldBe HELP
        last.target shouldBe HELP
        timeline.resting shouldBe HELP
    }

    test("pauses after punctuation") {
        val timeline = timelineOf(script = typewriter { type("a,b.") }, style = steady, seed = 1)

        timeline.gaps() shouldContainExactly listOf(TYPE, TYPE, TYPE + PUNCTUATION, TYPE)
    }

    test("varies its rhythm by its seed only") {
        val style = steady.copy(jitter = HALF)
        val script = typewriter { type("Typewriter") }
        val first = timelineOf(script = script, style = style, seed = 1)

        timelineOf(script = script, style = style, seed = 1).marks shouldBe first.marks
        timelineOf(script = script, style = style, seed = 2).marks shouldNotBe first.marks
        first.gaps().forEach { gap -> (gap in TYPE - TYPE / 2..TYPE + TYPE / 2) shouldBe true }
    }

    test("keeps pairs of surrogates together") {
        val script = typewriter {
            type("${SMILE}b")
            delete(count = 2)
        }
        val timeline = timelineOf(script = script, style = steady, seed = 1)

        timeline.texts() shouldContainExactly listOf("", "a", SMILE, "${SMILE}b", SMILE, "a")
        glyphLength(text = "\uD83D", index = 0) shouldBe 1
        glyphLength(text = "\uD83Dx", index = 0) shouldBe 1
        lastGlyphLength(text = "\uDE00") shouldBe 1
        lastGlyphLength(text = "x\uDE00") shouldBe 1
    }

    test("ignores deletions of an empty text and rests on what it shows") {
        val empty = timelineOf(script = typewriter { delete(count = 2) }, style = steady, seed = 1)
        val script = typewriter {
            type("Hi")
            delete()
        }
        val ended = timelineOf(script = script, style = steady, seed = 1)

        empty.marks.size shouldBe 1
        empty.resting shouldBe ""
        ended.resting shouldBe "Hi"
    }

    test("finds the mark at a position and loops when it repeats") {
        val repeating = timelineOf(script = typewriterCycle(phrases = listOf(HEY)), style = steady, seed = 1)
        val once = timelineOf(script = typewriter { type(HEY) }, style = steady, seed = 1)
        val between = TYPE * 2 + TYPE / 2

        repeating.textAt(elapsed = between) shouldBe "He"
        repeating.textAt(elapsed = repeating.duration + between) shouldBe "He"
        repeating.textAt(elapsed = repeating.duration - 1L) shouldBe "H"
        repeating.isFinished(elapsed = HOLD * HOLD) shouldBe false
        once.textAt(elapsed = HOLD * HOLD) shouldBe HEY
        once.textAt(elapsed = -TYPE) shouldBe ""
        once.textAt(elapsed = TYPE) shouldBe "H"
        once.isFinished(elapsed = once.duration - 1L) shouldBe false
        once.isFinished(elapsed = once.duration) shouldBe true
        timelineOf(script = typewriter { repeat() }, style = steady, seed = 1).positionOf(elapsed = TYPE) shouldBe TYPE
    }

    test("blinks the caret only while the text rests") {
        val fading = PERIOD / 2 - FADE / 2

        alpha(at = 0L) shouldBe 1f
        alpha(at = fading) shouldBe (HALF plusOrMinus TOLERANCE)
        alpha(at = PERIOD / 2 + FADE) shouldBe 0f
        alpha(at = PERIOD - FADE / 2) shouldBe (HALF plusOrMinus TOLERANCE)
        alpha(at = PERIOD + FADE) shouldBe 1f
        alpha(at = PERIOD / 2 + FADE, fade = 0L) shouldBe 0f
        alpha(at = PERIOD - 1L, fade = 0L) shouldBe 0f
        alpha(at = FADE, period = 0L) shouldBe 1f
    }
})
