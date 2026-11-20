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

package com.davils.kiro.ui.components.slider

import androidx.compose.ui.input.key.Key
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private const val MAX: Float = 100f
private const val BETWEEN: Float = 12.4f
private const val QUARTER: Float = 25f
private const val HALF: Float = 50f
private const val VALUE: Float = 10f
private const val STEP: Float = 5f
private const val LOW: Float = 10f
private const val HIGH: Float = 80f
private const val TRACK: Float = 112f
private const val THUMB: Float = 12f

class SliderMathTest : FunSpec({
    val range = 0f..MAX

    fun key(key: Key, isReversed: Boolean = false, step: Float = 1f) =
        keyTarget(key = key, value = VALUE, range = range, step = step, isReversed = isReversed)

    context("snap") {
        test("rounds to the nearest step inside the range") {
            snap(value = BETWEEN, range = range, step = STEP) shouldBe VALUE
            snap(value = 130f, range = range, step = STEP) shouldBe MAX
            snap(value = BETWEEN, range = range, step = 0f) shouldBe BETWEEN
        }
    }

    context("fractions") {
        test("map values to fractions and back") {
            fractionOf(value = QUARTER, range = range) shouldBe QUARTER / MAX
            fractionOf(value = 1f, range = 1f..1f) shouldBe 0f
            valueAt(fraction = HALF / MAX, range = range) shouldBe HALF
        }

        test("center the thumb on the pointer and keep it inside the track") {
            fractionAt(position = THUMB / 2, length = TRACK, thumb = THUMB) shouldBe 0f
            fractionAt(position = TRACK / 2, length = TRACK, thumb = THUMB) shouldBe HALF / MAX
            fractionAt(position = THUMB / 2, length = THUMB - 2, thumb = THUMB) shouldBe 0f
        }
    }

    context("thumbs") {
        test("pick the nearest thumb and the upper one when they overlap") {
            nearestIndex(values = listOf(LOW, HIGH), value = HIGH - LOW) shouldBe 1
            nearestIndex(values = listOf(HALF, HALF), value = HALF + LOW) shouldBe 1
            nearestIndex(values = listOf(HALF, HALF), value = HALF - LOW) shouldBe 0
        }

        test("keep a thumb between its neighbours") {
            moved(values = listOf(LOW, HIGH), index = 0, value = MAX, range = range) shouldBe listOf(HIGH, HIGH)
            moved(values = listOf(LOW, HIGH), index = 1, value = 0f, range = range) shouldBe listOf(LOW, LOW)
        }
    }

    context("keys") {
        test("step, page and jump to the ends") {
            key(Key.DirectionRight) shouldBe VALUE + 1
            key(Key.DirectionRight, isReversed = true) shouldBe VALUE - 1
            key(Key.DirectionDown, step = 0f) shouldBe VALUE - 1
            key(Key.PageUp) shouldBe VALUE * 2
            key(Key.PageDown) shouldBe 0f
            key(Key.DirectionUp) shouldBe VALUE + 1
            key(Key.DirectionLeft) shouldBe VALUE - 1
            key(Key.MoveHome) shouldBe 0f
            key(Key.MoveEnd) shouldBe MAX
            key(Key.A).shouldBeNull()
        }
    }
})
