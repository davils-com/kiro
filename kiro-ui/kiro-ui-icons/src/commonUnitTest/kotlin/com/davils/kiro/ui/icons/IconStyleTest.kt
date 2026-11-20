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

package com.davils.kiro.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.IconTokens
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private val tokens: IconTokens = IconTokens(size = 20.dp, strokeWidth = 1.5f, isNonScalingStroke = true)

class IconStyleTest : FunSpec({
    context("resolve") {
        test("takes every unspecified value from the tokens of the theme and the content color") {
            val resolved = IconStyle().resolve(tokens = tokens, contentColor = Color.Red)

            resolved shouldBe IconStyle(size = 20.dp, strokeWidth = 1.5f, isNonScalingStroke = true, color = Color.Red)
        }

        test("keeps every value the style names") {
            val style = IconStyle(size = 8.dp, strokeWidth = 3f, isNonScalingStroke = false, color = Color.Blue)

            style.resolve(tokens = tokens, contentColor = Color.Red) shouldBe style
        }
    }

    context("value") {
        test("equals its copy, differs from a changed copy and describes itself") {
            val style = IconStyle(size = 8.dp)

            style.copy() shouldBe style
            style.copy().hashCode() shouldBe style.hashCode()
            style.copy(color = Color.Red) shouldNotBe style
            style.toString() shouldStartWith "IconStyle("
        }
    }
})
