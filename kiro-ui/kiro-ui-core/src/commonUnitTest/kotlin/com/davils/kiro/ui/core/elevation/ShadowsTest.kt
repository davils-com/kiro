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

package com.davils.kiro.ui.core.elevation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

private const val ALPHA_TOLERANCE: Double = 0.005

class ShadowsTest : FunSpec({
    context("from") {
        val shadows = Shadows.from(color = Color.Black)

        test("draws small shadows in two layers like tailwind") {
            val layers = shadows.sm.layers

            layers shouldHaveSize 2
            layers.last().spread shouldBe (-1).dp
        }

        test("draws the largest shadow as one deep layer") {
            val deep = 0.25
            val layer = shadows.xl2.layers.single()

            layer.color.alpha.toDouble() shouldBe (deep plusOrMinus ALPHA_TOLERANCE)
        }

        test("draws the faintest shadow without blur") {
            val layer = shadows.xs2.layers.single()

            layer.blur shouldBe 0.dp
        }
    }

    context("strength") {
        test("draws every layer in the given color at the given strength") {
            val shadows = Shadows.from(
                color = Color.Red,
                strength = ShadowStrength(faint = 0.2f, soft = 0.4f, deep = 0.6f),
            )

            shadows.xs.layers
                .single()
                .color shouldBe Color.Red.copy(alpha = 0.2f)
            shadows.md.layers
                .map { layer -> layer.color }
                .toSet() shouldBe setOf(Color.Red.copy(alpha = 0.4f))
            shadows.xl2.layers
                .single()
                .color shouldBe Color.Red.copy(alpha = 0.6f)
        }
    }

    context("none") {
        test("has no layers") {
            BoxShadow.None.layers.shouldBeEmpty()
        }
    }

    context("layers") {
        test("are copied, so a change to the list given does not reach the shadow") {
            val layers = mutableListOf(ShadowLayer(offsetY = 1.dp, blur = 1.dp, color = Color.Black))
            val shadow = BoxShadow(layers)

            layers.clear()

            shadow.layers shouldHaveSize 1
        }

        test("compare by value and describe themselves") {
            val layer = ShadowLayer(offsetY = 1.dp, blur = 2.dp, color = Color.Black)

            BoxShadow(listOf(layer)) shouldBe BoxShadow(listOf(layer)).copy()
            BoxShadow(listOf(layer)).hashCode() shouldBe BoxShadow(listOf(layer)).hashCode()
            BoxShadow.None.toString() shouldBe "BoxShadow(layers=[])"
        }
    }
})
