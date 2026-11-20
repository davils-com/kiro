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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.IconTokens
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.icons.lucide.Activity
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TAG: String = "icon"
private const val ICON_DP: Int = 48
private const val QUARTER: Int = 4

private val filledSquare = IconGraphic(
    name = "square",
    paths = listOf(IconPath(data = "M0 0h24v24H0Z", isFilled = true)),
)

private val horizontalLine = IconGraphic(name = "line", paths = listOf(IconPath(data = "M0 12H24")))

@Composable
private fun TaggedIcon(icon: IconGraphic, style: IconStyle = LocalIconStyle.current) {
    Icon(icon = icon, contentDescription = null, modifier = Modifier.testTag(TAG), style = style)
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.pixels(): PixelMap {
    val image = onNodeWithTag(TAG).captureToImage()
    return image.toPixelMap()
}

@OptIn(ExperimentalTestApi::class)
class IconTest : FunSpec({
    context("semantics") {
        test("describes the icon as an image when it has a description") {
            runComposeUiTest {
                setContent { Icon(icon = Lucide.Activity, contentDescription = "Activity") }

                val node = onNodeWithContentDescription("Activity")
                node.assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Image))
            }
        }

        test("stays out of the semantics without a description") {
            runComposeUiTest {
                setContent { TaggedIcon(icon = Lucide.Activity) }

                val node = onNodeWithTag(TAG).fetchSemanticsNode()
                node.config.contains(SemanticsProperties.Role) shouldBe false
            }
        }
    }

    context("theme") {
        test("takes its size from the icon tokens of the theme") {
            val theme = Theme.light().copy(icons = IconTokens(size = 32.dp))

            runComposeUiTest {
                setContent { ThemeProvider(theme = theme) { TaggedIcon(icon = Lucide.Activity) } }

                onNodeWithTag(TAG).assertWidthIsEqualTo(32.dp)
            }
        }
    }

    context("size") {
        test("is 16 dp like size-4 of the website") {
            runComposeUiTest {
                setContent { TaggedIcon(icon = Lucide.Activity) }

                val node = onNodeWithTag(TAG).assertWidthIsEqualTo(16.dp)
                node.assertHeightIsEqualTo(16.dp)
            }
        }

        test("follows the style of the composition") {
            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = 24.dp)) {
                        TaggedIcon(icon = Lucide.Activity)
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(24.dp)
            }
        }
    }

    context("drawing") {
        test("fills filled paths in the content color") {
            runComposeUiTest {
                setContent {
                    CompositionLocalProvider(LocalContentColor provides Color.Red) { TaggedIcon(icon = filledSquare) }
                }

                val pixels = pixels()
                pixels[pixels.width / 2, pixels.height / QUARTER] shouldBe Color.Red
            }
        }

        test("prefers the color of the style over the content color") {
            runComposeUiTest {
                setContent { TaggedIcon(icon = filledSquare, style = IconStyle(color = Color.Blue)) }

                val pixels = pixels()
                pixels[pixels.width / 2, pixels.height / QUARTER] shouldBe Color.Blue
            }
        }

        test("strokes open paths with a stroke that scales with the icon") {
            runComposeUiTest {
                setContent {
                    TaggedIcon(icon = horizontalLine, style = IconStyle(size = ICON_DP.dp, color = Color.Green))
                }

                val pixels = pixels()
                val onePoint = pixels.height / ICON_DP
                pixels[pixels.width / 2, pixels.height / 2 + onePoint] shouldBe Color.Green
                pixels[pixels.width / 2, pixels.height / QUARTER] shouldBe Color.Transparent
            }
        }

        test("keeps a non scaling stroke at its width whatever the size") {
            runComposeUiTest {
                setContent {
                    val style = IconStyle(size = ICON_DP.dp, isNonScalingStroke = true, color = Color.Green)
                    TaggedIcon(icon = horizontalLine, style = style)
                }

                val pixels = pixels()
                val onePoint = pixels.height / ICON_DP
                pixels[pixels.width / 2, pixels.height / 2] shouldBe Color.Green
                pixels[pixels.width / 2, pixels.height / 2 + onePoint] shouldBe Color.Transparent
            }
        }
    }
})
