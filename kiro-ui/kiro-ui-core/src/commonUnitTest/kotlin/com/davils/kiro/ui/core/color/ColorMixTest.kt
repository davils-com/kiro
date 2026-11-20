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

package com.davils.kiro.ui.core.color

import androidx.compose.ui.graphics.Color
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class ColorMixTest : FunSpec({
    context("mix") {
        val foreground = Oklch(lightness = 0.145, chroma = 0.0, hue = 0.0)
        val purple = Oklch(lightness = 0.5, chroma = 0.2, hue = 300.0)
        val red = Oklch(lightness = 0.5, chroma = 0.2, hue = 20.0)
        val half = 0.5f

        test("keeps the color and lowers the alpha when mixed with transparent") {
            val amount = 0.14f

            val line = Color.Transparent.mix(foreground.toColor(), amount = amount)

            line.alpha.toDouble() should nearCoordinate(amount.toDouble())
            line.copy(alpha = 1f).toOklch().lightness should nearCoordinate(foreground.lightness)
        }

        test("is transparent when both colors are transparent") {
            Color.Transparent.mix(Color.Transparent, amount = half) shouldBe Color.Transparent
        }

        test("takes the hue of the chromatic color when the other is gray") {
            val grayFirst = foreground.toColor().mix(purple.toColor(), amount = half)
            val grayLast = purple.toColor().mix(foreground.toColor(), amount = half)

            grayFirst.toOklch().hue should nearHue(purple.hue)
            grayLast.toOklch().hue should nearHue(purple.hue)
        }

        test("interpolates the hue along the shorter arc") {
            val magenta = 340.0

            val upward = purple.toColor().mix(red.toColor(), amount = half)
            val downward = red.toColor().mix(purple.toColor(), amount = half)

            upward.toOklch().hue should nearHue(magenta)
            downward.toOklch().hue should nearHue(magenta)
        }

        test("interpolates the hue directly when the arc is short") {
            val blue = Oklch(lightness = 0.5, chroma = 0.2, hue = 260.0)
            val violet = 280.0

            val mixed = blue.toColor().mix(purple.toColor(), amount = half)

            mixed.toOklch().hue should nearHue(violet)
        }
    }
})
