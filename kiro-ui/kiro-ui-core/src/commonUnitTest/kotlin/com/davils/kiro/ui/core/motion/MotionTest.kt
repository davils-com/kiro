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

package com.davils.kiro.ui.core.motion

import androidx.compose.ui.unit.dp
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

class MotionTest : FunSpec({
    context("defaults") {
        val motion = Motion()

        test("steps the durations from short to long") {
            motion.durations shouldBe Durations(
                short = 150.milliseconds,
                medium = 250.milliseconds,
                long = 350.milliseconds,
            )
        }

        test("damps both springs critically and moves effects faster than space") {
            val springs = motion.springs

            springs.spatial.dampingRatio shouldBe 1f
            springs.effects.dampingRatio shouldBe 1f
            (springs.effects.stiffness > springs.spatial.stiffness) shouldBe true
        }

        test("animates overlays like tw-animate with fade, zoom-in-95 and slide-in-2") {
            motion.overlay shouldBe OverlayMotion(
                enterDuration = 180.milliseconds,
                exitDuration = 40.milliseconds,
                initialAlpha = 0f,
                initialScale = 0.95f,
                slideDistance = 8.dp,
            )
        }

        test("plays ambient motion such as backgrounds at its natural speed") {
            motion.ambient shouldBe AmbientMotion(speed = 1f)
        }

        test("eases the expo curve of the website") {
            val halfway = 0.5f
            val expected = 0.92f

            (motion.easings.outExpo.transform(halfway) > expected) shouldBe true
        }

        test("emphasizes the end of a movement") {
            val halfway = 0.5f
            val expected = 0.8f

            (motion.easings.emphasized.transform(halfway) > expected) shouldBe true
        }
    }
})
