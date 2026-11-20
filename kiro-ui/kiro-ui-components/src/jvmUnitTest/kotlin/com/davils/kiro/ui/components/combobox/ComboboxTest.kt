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

package com.davils.kiro.ui.components.combobox

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.PixelMap
import androidx.compose.ui.graphics.toPixelMap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.ChevronsUpDown
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val REMIX: String = "Remix"
private const val ASTRO: String = "Astro"
private const val NUXT: String = "Nuxt.js"
private const val NOTHING: String = "Nothing"
private const val PLACEHOLDER: String = "Select framework..."
private const val CHECK_NEAR: Float = 8f
private const val CHECK_FAR: Float = 24f
private const val CLEAR_NEAR: Float = 30f
private const val CLEAR_FAR: Float = 50f

private val frameworks = listOf("Next.js", "SvelteKit", NUXT, REMIX, ASTRO)

private fun hasInk(pixels: PixelMap, from: Float, to: Float): Boolean {
    val background = pixels[pixels.width - 1, pixels.height / 2]
    val rows = pixels.height / 4..pixels.height * 3 / 4
    val columns = from.toInt()..to.toInt()
    return rows.any { row -> columns.any { column -> pixels[pixels.width - column, row] != background } }
}

@OptIn(ExperimentalTestApi::class)
class ComboboxTest : FunSpec({
    context("styles") {
        test("open 6 below a select-like trigger with an up and down chevron") {
            val style = ComboboxStyles.Default.resolve(Theme.light())

            style.sideOffset shouldBe 6.dp
            style.triggerIcon shouldBe Lucide.ChevronsUpDown
            style.minWidth shouldBe 200.dp
        }
    }

    context("combobox") {
        test("searches inside the popup and picks with the keyboard") {
            var selected by mutableStateOf<String?>(null)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Combobox(
                            items = frameworks,
                            selected = selected,
                            onSelectedChange = { selected = it },
                            label = { framework -> framework },
                            placeholder = PLACEHOLDER,
                        )
                    }
                }

                onNodeWithText(PLACEHOLDER).performClick()
                waitForIdle()
                val search = onNode(hasSetTextAction() and isFocused())
                search.performTextInput("js")
                waitForIdle()
                onNodeWithText(REMIX).assertDoesNotExist()
                search.performKeyInput { pressKey(Key.DirectionDown) }
                search.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
                onNode(hasSetTextAction()).assertDoesNotExist()
            }

            selected shouldBe NUXT
        }

        test("draws the check of the selected option at the end of the row") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Combobox(
                            items = frameworks,
                            selected = REMIX,
                            onSelectedChange = {},
                            label = { framework -> framework },
                            placeholder = PLACEHOLDER,
                        )
                    }
                }

                val trigger = onAllNodesWithText(REMIX).onFirst()
                trigger.performClick()
                waitForIdle()
                val image = onNode(isSelectable() and hasText(REMIX)).captureToImage()
                val option = image.toPixelMap()
                val scale = density.density

                hasInk(option, from = CHECK_NEAR * scale, to = CHECK_FAR * scale) shouldBe true
                hasInk(option, from = CLEAR_NEAR * scale, to = CLEAR_FAR * scale) shouldBe false
            }
        }

        test("ranks prefix matches before matches inside a word") {
            var selected by mutableStateOf<String?>(null)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Combobox(
                            items = listOf("Hostname", "Host"),
                            selected = selected,
                            onSelectedChange = { selected = it },
                            label = { item -> item },
                            placeholder = PLACEHOLDER,
                        )
                    }
                }

                onNodeWithText(PLACEHOLDER).performClick()
                waitForIdle()
                val search = onNode(hasSetTextAction() and isFocused())
                search.performTextInput("host")
                waitForIdle()
                search.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
            }

            selected shouldBe "Host"
        }

        test("shows a message without matches and opens from the keyboard") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Combobox(
                            items = frameworks,
                            selected = ASTRO,
                            onSelectedChange = {},
                            label = { framework -> framework },
                            emptyText = NOTHING,
                        )
                    }
                }

                val trigger = onNodeWithText(ASTRO)
                trigger.requestFocus()
                trigger.performKeyInput { pressKey(Key.DirectionDown) }
                waitForIdle()
                onNodeWithText(REMIX).assertExists()
                val search = onNode(hasSetTextAction() and isFocused())
                search.performTextInput("zz")
                waitForIdle()
                onNodeWithText(NOTHING).assertExists()
                search.performKeyInput { pressKey(Key.Escape) }
                waitForIdle()
                onNodeWithText(NOTHING).assertDoesNotExist()
            }
        }
    }
})
