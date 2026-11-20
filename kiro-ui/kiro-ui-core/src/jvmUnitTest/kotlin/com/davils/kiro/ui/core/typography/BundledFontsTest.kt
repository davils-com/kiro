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

package com.davils.kiro.ui.core.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe

private const val SAMPLE: String = "Kiro network engineering"

@OptIn(ExperimentalTestApi::class)
private fun widthOf(family: @Composable (FontFamilies) -> FontFamily, weight: FontWeight): Int {
    var width = 0
    runComposeUiTest {
        setContent {
            val measurer = rememberTextMeasurer()
            val style = TextStyle(fontFamily = family(bundledFontFamilies()), fontWeight = weight, fontSize = 16.sp)
            width = measurer.measure(text = SAMPLE, style = style).size.width
        }
        waitForIdle()
    }
    return width
}

class BundledFontsTest : FunSpec({
    context("families") {
        test("are loaded from the fonts that ship with Kiro, not the generic families") {
            var families: FontFamilies? = null

            @OptIn(ExperimentalTestApi::class)
            runComposeUiTest {
                setContent { families = bundledFontFamilies() }
            }

            families?.sans shouldNotBe FontFamily.SansSerif
            families?.mono shouldNotBe FontFamily.Monospace
        }
    }

    context("weight axis") {
        test("draws heavier weights of the sans family wider") {
            val black = widthOf(family = { families -> families.sans }, weight = FontWeight.Black)
            val thin = widthOf(family = { families -> families.sans }, weight = FontWeight.Thin)

            black shouldBeGreaterThan thin
        }
    }

    context("width axis") {
        test("draws headings wider than body text") {
            val heading = widthOf(family = { families -> families.heading }, weight = FontWeight.Normal)
            val body = widthOf(family = { families -> families.sans }, weight = FontWeight.Normal)

            heading shouldBeGreaterThan body
        }
    }
})
