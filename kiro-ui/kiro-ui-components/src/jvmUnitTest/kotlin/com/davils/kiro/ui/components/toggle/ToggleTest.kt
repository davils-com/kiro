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

package com.davils.kiro.ui.components.toggle

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val HOVER_SETTLE_MILLIS: Long = 500L
private const val PULSE_PEEK_MILLIS: Long = 50L

private fun backgroundOf(pixels: PixelMap): Color = pixels[pixels.width / 2, 2]

@OptIn(ExperimentalTestApi::class)
class ToggleTest : FunSpec({
    val light = Theme.light()

    fun style(variant: ToggleVariant = ToggleVariant.Default, size: ToggleSize = ToggleSize.Default) =
        ToggleStyles.Default.resolve(theme = light, variant = variant, size = size)

    context("styles") {
        test("are h-8 min-w-8 px-2.5 gap-1 rounded-lg and muted when pressed") {
            val default = style()

            default.height shouldBe 32.dp
            default.minWidth shouldBe 32.dp
            default.horizontalPadding shouldBe 10.dp
            default.gap shouldBe 4.dp
            default.shape shouldBe RoundedCornerShape(10.dp)
            default.borderWidth shouldBe 0.dp
            default.colors.container.default shouldBe Color.Transparent
            default.checkedColors.container.default shouldBe light.colors.secondary
            default.checkedColors.content.default shouldBe light.colors.secondaryForeground
            default.colors.container.hovered shouldBe null
        }

        test("tint the background briefly when pressed in light mode and in dark mode") {
            val night = Theme.dark()
            val bright = style().colors.container.pressed
            val dim = ToggleStyles.Default.resolve(night, ToggleVariant.Default, ToggleSize.Default)

            bright shouldBe light.colors.muted.mix(light.colors.foreground, amount = 0.08f)
            dim.colors.container.pressed shouldBe night.colors.muted.mix(night.colors.foreground, amount = 0.15f)
        }

        test("draw a border for the outline variant and resize for sm and lg") {
            style(variant = ToggleVariant.Outline).borderWidth shouldBe 1.dp
            style(variant = ToggleVariant.Outline).colors.border.default shouldBe light.colors.input
            style(size = ToggleSize.Sm).height shouldBe 28.dp
            style(size = ToggleSize.Sm).shape shouldBe RoundedCornerShape(8.dp)
            style(size = ToggleSize.Sm).textStyle.fontSize shouldBe 12.8.sp
            style(size = ToggleSize.Lg).minWidth shouldBe 36.dp
        }

        test("need a style of their own for custom variants and sizes") {
            shouldThrow<IllegalArgumentException> { style(variant = Unknown) }
            shouldThrow<IllegalArgumentException> { style(size = Unknown) }
        }
    }

    context("toggle") {
        test("switches between pressed and released") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var isPressed by remember { mutableStateOf(false) }
                        Toggle(
                            pressed = isPressed,
                            onPressedChange = { isPressed = it },
                            modifier = Modifier.testTag(TAG),
                            variant = ToggleVariant.Outline,
                        ) {
                            Icon(icon = Lucide.Check, placement = IconPlacement.Leading)
                            Text("Bold")
                        }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.assertHeightIsEqualTo(32.dp)
                node.performMouseInput { moveTo(center) }
                node.assertIsOff()
                node.performClick()
                node.assertIsOn()
            }
        }

        test("shows nothing while hovered, flashes after a click and then settles on the secondary color") {
            runComposeUiTest {
                mainClock.autoAdvance = false
                setContent {
                    Themed(theme = reducedMotion) {
                        var isPressed by remember { mutableStateOf(false) }
                        Toggle(
                            pressed = isPressed,
                            onPressedChange = { isPressed = it },
                            modifier = Modifier.testTag(TAG),
                        ) { Text("Bold") }
                    }
                }

                val node = onNodeWithTag(TAG)
                node.performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(HOVER_SETTLE_MILLIS)
                val idle = backgroundOf(node.captureToImage().toPixelMap())
                node.performClick()
                mainClock.advanceTimeBy(PULSE_PEEK_MILLIS)
                val flash = backgroundOf(node.captureToImage().toPixelMap())
                mainClock.advanceTimeBy(HOVER_SETTLE_MILLIS)
                val settled = backgroundOf(node.captureToImage().toPixelMap())

                idle.alpha shouldBe 0f
                flash.alpha shouldBeGreaterThan idle.alpha
                settled.red shouldBeGreaterThan flash.red
            }
        }
    }
})
