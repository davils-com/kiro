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
import io.kotest.matchers.shouldBe

class ColorsTest : FunSpec({
    context("light") {
        val colors = Colors.light()

        test("uses the purple primary of the website") {
            hexDistance(actual = colors.primary, expected = "#8200db") shouldBeLessThanOrEqual 1
        }

        test("uses the neutral foreground of the website") {
            hexDistance(actual = colors.foreground, expected = "#0a0a0a") shouldBeLessThanOrEqual 1
        }

        test("draws borders, inputs and muted surfaces in a visible gray") {
            val border = hexDistance(actual = colors.border, expected = "#d9d9d9")
            val muted = hexDistance(actual = colors.muted, expected = "#f0f0f0")

            border shouldBeLessThanOrEqual 2
            muted shouldBeLessThanOrEqual 2
            colors.input shouldBe colors.border
            colors.sidebar.border shouldBe colors.border
        }

        test("reports errors in the destructive color") {
            colors.status.error.foreground shouldBe colors.destructive
        }

        test("tells the statuses apart by their foreground") {
            val status = colors.status
            val foregrounds = listOf(status.info.foreground, status.success.foreground, status.warning.foreground)

            foregrounds.toSet().size shouldBe foregrounds.size
        }

        test("draws lines in the foreground at fourteen percent") {
            val fourteenPercent = 0.14f

            colors.line shouldBe colors.foreground.copy(alpha = fourteenPercent)
        }
    }

    context("dark") {
        val colors = Colors.dark()

        test("uses the darker purple primary of the website") {
            hexDistance(actual = colors.primary, expected = "#6e11b0") shouldBeLessThanOrEqual 1
        }

        test("draws borders in white at ten percent") {
            val tenPercent = 0.1

            colors.border.alpha.toDouble() should nearCoordinate(tenPercent)
        }

        test("shares the brand color with the light colors") {
            colors.brand shouldBe Colors.light().brand
        }
    }
})
