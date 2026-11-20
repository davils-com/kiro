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

package com.davils.kiro.ui.components.primitive.motion

import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.TweenSpec
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.time.Duration.Companion.milliseconds

private const val SHORT_MILLIS: Int = 150
private const val MEDIUM_MILLIS: Int = 250
private const val ANSWER: Int = 42

class MotionSpecsTest : FunSpec({
    context("tweens") {
        val theme = Theme.light()

        test("resize in the medium duration with the emphasized easing") {
            val spec = theme.sizeTransition<Float>().shouldBeInstanceOf<TweenSpec<Float>>()

            spec.durationMillis shouldBe MEDIUM_MILLIS
            spec.easing shouldBe theme.motion.easings.emphasized
        }

        test("fade in the short duration with the standard easing") {
            val spec = theme.fadeTransition<Float>().shouldBeInstanceOf<TweenSpec<Float>>()

            spec.durationMillis shouldBe SHORT_MILLIS
            spec.easing shouldBe theme.motion.easings.standard
        }

        test("convert durations to whole milliseconds") {
            millisOf(ANSWER.milliseconds) shouldBe ANSWER
        }
    }

    context("springs") {
        test("move space and effects with the springs of the theme") {
            val theme = Theme.light()
            val spatial = theme.spatialSpring<Float>().shouldBeInstanceOf<SpringSpec<Float>>()
            val effects = theme.effectsSpring<Float>().shouldBeInstanceOf<SpringSpec<Float>>()

            spatial.stiffness shouldBe theme.motion.springs.spatial.stiffness
            effects.stiffness shouldBe theme.motion.springs.effects.stiffness
        }
    }

    context("reduced motion") {
        test("snaps every spec") {
            reducedMotion.sizeTransition<Float>().shouldBeInstanceOf<SnapSpec<Float>>()
            reducedMotion.fadeTransition<Float>().shouldBeInstanceOf<SnapSpec<Float>>()
            reducedMotion.spatialSpring<Float>().shouldBeInstanceOf<SnapSpec<Float>>()
            reducedMotion.effectsSpring<Float>().shouldBeInstanceOf<SnapSpec<Float>>()
        }
    }
})
