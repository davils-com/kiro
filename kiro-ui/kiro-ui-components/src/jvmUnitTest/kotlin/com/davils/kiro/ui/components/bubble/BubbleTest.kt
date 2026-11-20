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

package com.davils.kiro.ui.components.bubble

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.message.MessageAlign
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.toColor
import com.davils.kiro.ui.core.color.toOklch
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeLessThanOrEqualTo
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe

private const val LONG: String = "A message that is long enough to wrap, because it keeps going and going and going on."
private const val TOLERANCE: Double = 0.01
private const val MAX_WIDTH: Float = 0.8f
private const val DARK_LIGHTNESS: Double = 0.3

private val variants: List<BubbleVariant> = listOf(
    BubbleVariant.Default,
    BubbleVariant.Secondary,
    BubbleVariant.Muted,
    BubbleVariant.Tinted,
    BubbleVariant.Outline,
    BubbleVariant.Ghost,
    BubbleVariant.Destructive,
)

@OptIn(ExperimentalTestApi::class)
class BubbleTest : FunSpec({
    val light = Theme.light()
    val dark = Theme.dark()

    context("styles") {
        test("fill the default bubble with the primary color and cap it at 80 percent") {
            val style = BubbleStyles.Default.resolve(light, BubbleVariant.Default)

            style.container shouldBe light.colors.primary
            style.content shouldBe light.colors.primaryForeground
            style.padding shouldBe PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            style.maxWidthFraction shouldBe MAX_WIDTH
        }

        test("tint the primary color to a light or dark shade") {
            val bright = BubbleStyles.Default.resolve(light, BubbleVariant.Tinted)
            val dim = BubbleStyles.Default.resolve(dark, BubbleVariant.Tinted)

            val primary = light.colors.primary.toOklch()
            bright.container shouldBe primary.copy(lightness = 0.93, chroma = primary.chroma * 0.4).toColor()
            dim.container.toOklch().lightness shouldBe (DARK_LIGHTNESS plusOrMinus TOLERANCE)
        }

        test("let the ghost bubble span the full width without padding") {
            val style = BubbleStyles.Default.resolve(light, BubbleVariant.Ghost)

            style.maxWidthFraction shouldBe 1f
            style.padding shouldBe PaddingValues(0.dp)
        }

        test("adapt the hover colors to the dark theme") {
            val outline = BubbleStyles.Default.resolve(dark, BubbleVariant.Outline)
            val destructive = BubbleStyles.Default.resolve(dark, BubbleVariant.Destructive)

            outline.hover shouldBe dark.colors.input.copy(alpha = 0.3f)
            outline.border shouldBe dark.colors.border
            destructive.container shouldBe dark.colors.destructive.copy(alpha = 0.2f)
        }

        test("reject an unknown variant") {
            shouldThrow<IllegalArgumentException> { BubbleStyles.Default.resolve(light, Unknown) }
        }
    }

    context("bubble") {
        test("never grows beyond 80 percent and sits at the end when aligned there") {
            runComposeUiTest {
                setContent {
                    Themed {
                        BubbleGroup(modifier = Modifier.width(400.dp)) {
                            Bubble(modifier = Modifier.testTag(TAG)) { BubbleContent { Text(LONG) } }
                            Bubble(align = MessageAlign.End) {
                                BubbleContent(modifier = Modifier.testTag("end")) { Text("Short") }
                            }
                        }
                    }
                }

                onNodeWithText(LONG).getUnclippedBoundsInRoot().right shouldBeLessThanOrEqualTo 320.dp
                onNodeWithTag("end").getUnclippedBoundsInRoot().right shouldBe 400.dp
            }
        }

        test("renders every variant with reactions on both sides and handles clicks") {
            var clicked = 0

            runComposeUiTest {
                setContent {
                    Themed(theme = dark) {
                        BubbleGroup(modifier = Modifier.width(400.dp)) {
                            variants.forEach { variant ->
                                Bubble(variant = variant) {
                                    BubbleContent(onClick = { clicked++ }) { Text("Bubble $variant") }
                                    BubbleReactions { Text("+1") }
                                    BubbleReactions(
                                        side = BubbleReactionsSide.Top,
                                        align = MessageAlign.Start,
                                    ) { Text("!") }
                                }
                            }
                        }
                    }
                }

                variants.forEach { variant -> onNodeWithText("Bubble $variant").assertExists() }
                onNodeWithText("Bubble Default").performMouseInput { moveTo(center) }
                onNodeWithText("Bubble Default").performClick()
            }

            clicked shouldBe 1
        }
    }
})
