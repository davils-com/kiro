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

package com.davils.kiro.ui.components.primitive.interaction

import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.snap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HALF: Float = 0.5f

private fun halfway(from: Color, to: Color): Color {
    val start = PremultipliedColor.convertToVector(from)
    val end = PremultipliedColor.convertToVector(to)
    val middle = AnimationVector4D(
        v1 = (start.v1 + end.v1) * HALF,
        v2 = (start.v2 + end.v2) * HALF,
        v3 = (start.v3 + end.v3) * HALF,
        v4 = (start.v4 + end.v4) * HALF,
    )
    return PremultipliedColor.convertFromVector(middle)
}

@OptIn(ExperimentalTestApi::class)
class ColorAnimationTest : FunSpec({
    context("premultiplied interpolation") {
        test("fades transparent into a color without passing through gray") {
            halfway(from = Color.Transparent, to = Color.White) shouldBe Color.White.copy(alpha = HALF)
        }

        test("keeps opaque colors and returns transparent for zero alpha") {
            halfway(from = Color.Red, to = Color.Red) shouldBe Color.Red
            halfway(from = Color.Transparent, to = Color.Transparent) shouldBe Color.Transparent
        }
    }

    context("animate color") {
        test("reaches its new target") {
            var target by mutableStateOf(Color.Transparent)
            var shown = Color.Unspecified

            runComposeUiTest {
                setContent { shown = animateColor(targetValue = target, animationSpec = snap()).value }
                target = Color.Blue
                waitForIdle()
            }

            shown shouldBe Color.Blue
        }
    }
})
