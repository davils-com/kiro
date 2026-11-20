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

package com.davils.kiro.ui.components.brand

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.markPixels(theme: Theme, modifier: Modifier): PixelMap {
    setContent {
        Themed(theme = theme) { BrandMark(modifier = modifier.testTag(TAG)) }
    }
    val image = onNodeWithTag(TAG).captureToImage()
    return image.toPixelMap()
}

private fun PixelMap.painted(): List<Color> {
    val colors = (0 until height).flatMap { y -> (0 until width).map { x -> this[x, y] } }
    return colors.filter { color -> color.alpha > 0f }
}

private fun PixelMap.lowestPainted(): Color {
    val rows = (0 until height).reversed()
    val row = rows.first { y -> (0 until width).any { x -> this[x, y].alpha > 0f } }
    val colors = (0 until width).map { x -> this[x, row] }
    return colors.maxBy { color -> color.alpha }
}

@OptIn(ExperimentalTestApi::class)
class BrandMarkTest : FunSpec({
    context("styles") {
        test("fade from the brand colour into the foreground of the theme") {
            listOf(Theme.light(), Theme.dark()).forEach { theme ->
                val style = BrandMarkStyles.Default.resolve(theme)

                style.size shouldBe 28.dp
                style.startColor shouldBe theme.colors.brand
                style.endColor shouldBe theme.colors.foreground
            }
        }
    }

    context("brand mark") {
        test("paints the fox in the light and the dark theme") {
            listOf(Theme.light(), Theme.dark()).forEach { theme ->
                runComposeUiTest {
                    val pixels = markPixels(theme = theme, modifier = Modifier.size(64.dp))

                    pixels.painted().size shouldBeGreaterThan 0
                    pixels[0, 0] shouldBe Color.Transparent
                }
            }
        }

        test("ends its gradient dark in the light theme and bright in the dark theme") {
            val ends = listOf(Theme.light(), Theme.dark()).map { theme ->
                var end = Color.Unspecified
                runComposeUiTest {
                    val pixels = markPixels(theme = theme, modifier = Modifier.size(64.dp))
                    end = pixels.lowestPainted()
                }
                end
            }
            val (light, dark) = ends

            dark.luminance() shouldBeGreaterThan light.luminance()
        }

        test("keeps its aspect ratio and centres itself in a wide area") {
            runComposeUiTest {
                val wide = Modifier.size(width = 96.dp, height = 32.dp)
                val pixels = markPixels(theme = Theme.light(), modifier = wide)

                pixels[0, pixels.height / 2] shouldBe Color.Transparent
                pixels[pixels.width - 1, pixels.height / 2] shouldBe Color.Transparent
                pixels.painted().size shouldBeGreaterThan 0
            }
        }

        test("takes the size of its modifier and falls back to the size of its style") {
            runComposeUiTest {
                setContent {
                    Themed {
                        BrandMark(modifier = Modifier.size(48.dp).testTag(TAG))
                        BrandMark(modifier = Modifier.testTag("default"))
                    }
                }

                val sized = onNodeWithTag(TAG)
                val fallback = onNodeWithTag("default")
                sized.assertWidthIsEqualTo(48.dp)
                sized.assertHeightIsEqualTo(48.dp)
                fallback.assertWidthIsEqualTo(28.dp)
                fallback.assertHeightIsEqualTo(28.dp)
            }
        }

        test("describes itself as kide") {
            runComposeUiTest {
                setContent { Themed { BrandMark() } }

                onNodeWithContentDescription("kide").assertExists()
            }
        }
    }
})
