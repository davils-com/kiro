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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.motion.Motion
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val SLIDE: Int = 8

class OverlayTransitionsTest : FunSpec({
    context("transitions") {
        test("fade, zoom and slide in from every side") {
            Physical.entries.forEach { physical ->
                val transitions = overlayTransitionsOf(motion = Motion(), physical = physical, slide = SLIDE)

                transitions.enter shouldNotBe EnterTransition.None
                transitions.exit shouldNotBe ExitTransition.None
            }
        }

        test("stay still with reduced motion") {
            val still = overlayTransitionsOf(
                motion = Motion(isReduced = true),
                physical = Physical.Top,
                slide = SLIDE,
            )

            still.enter shouldBe EnterTransition.None
        }
    }

    context("slide") {
        test("never travels further than the gap to the anchor") {
            slideOf(distance = 8.dp, gap = 4.dp) shouldBe 4.dp
        }

        test("keeps the full distance when the gap is wider") {
            slideOf(distance = 8.dp, gap = 12.dp) shouldBe 8.dp
        }

        test("stays still when the overlay touches or overlaps its anchor") {
            slideOf(distance = 8.dp, gap = 0.dp) shouldBe 0.dp
            slideOf(distance = 8.dp, gap = (-2).dp) shouldBe 0.dp
        }
    }

    context("sides") {
        test("mirror logical sides and alignments from right to left") {
            physicalSideOf(side = Side.Start, isRtl = true) shouldBe Physical.Right
            physicalSideOf(side = Side.End, isRtl = false) shouldBe Physical.Right
            mirroredAlignOf(align = Align.Center, isReversed = true) shouldBe Align.Center
            mirroredAlignOf(align = Align.End, isReversed = true) shouldBe Align.Start
        }
    }
})
