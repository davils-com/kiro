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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.should

class OklchConversionTest : FunSpec({
    context("oklch to srgb") {
        val tailwind = mapOf(
            Oklch(lightness = 0.145, chroma = 0.0, hue = 0.0) to "#0a0a0a",
            Oklch(lightness = 0.985, chroma = 0.0, hue = 0.0) to "#fafafa",
            Oklch(lightness = 0.922, chroma = 0.0, hue = 0.0) to "#e5e5e5",
            Oklch(lightness = 0.556, chroma = 0.0, hue = 0.0) to "#737373",
            Oklch(lightness = 0.577, chroma = 0.245, hue = 27.325) to "#e7000b",
            Oklch(lightness = 0.704, chroma = 0.191, hue = 22.216) to "#ff6467",
            Oklch(lightness = 0.496, chroma = 0.265, hue = 301.924) to "#8200db",
            Oklch(lightness = 0.438, chroma = 0.218, hue = 303.724) to "#6e11b0",
            Oklch(lightness = 0.558, chroma = 0.288, hue = 302.321) to "#9810fa",
            Oklch(lightness = 0.627, chroma = 0.265, hue = 303.9) to "#ad46ff",
            Oklch(lightness = 0.827, chroma = 0.119, hue = 306.383) to "#dab2ff",
        )

        tailwind.forEach { (color, hex) ->
            test("renders $color like the tailwind palette value $hex") {
                hexDistance(actual = color.toColor(), expected = hex) shouldBeLessThanOrEqual 1
            }
        }

        test("keeps the alpha") {
            val alpha = 0.1

            val color = oklch(lightness = 1.0, chroma = 0.0, hue = 0.0, alpha = alpha)

            color.alpha.toDouble() should nearCoordinate(alpha)
        }
    }

    context("srgb to oklch") {
        test("returns the coordinates a color was created from") {
            val original = Oklch(lightness = 0.496, chroma = 0.2, hue = 301.924)

            val roundTrip = original.toColor().toOklch()

            roundTrip.lightness should nearCoordinate(original.lightness)
            roundTrip.chroma should nearCoordinate(original.chroma)
            roundTrip.hue should nearHue(original.hue)
        }

        test("keeps hues of the lower half turn positive") {
            val blue = Oklch(lightness = 0.6, chroma = 0.1, hue = 250.0)

            val color = blue.toColor()

            color.toOklch().hue should nearHue(blue.hue)
        }
    }
})
