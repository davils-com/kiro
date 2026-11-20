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

package com.davils.kiro.ui.components.breadcrumb

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HOME: String = "Home"

private const val MORE: String = "More"
private const val PAGE: String = "Breadcrumb"
private const val DOCUMENTATION: String = "Documentation"
private const val THEMES: String = "Themes"

@OptIn(ExperimentalTestApi::class)
class BreadcrumbTest : FunSpec({
    context("styles") {
        test("are text-sm muted items with gap-1.5 and size-3.5 separators") {
            val light = Theme.light()
            val style = BreadcrumbStyles.Default.resolve(light)

            style.gap shouldBe 6.dp
            style.color shouldBe light.colors.mutedForeground.copy(alpha = 0.65f)
            style.pageColor shouldBe light.colors.foreground
            style.separatorSize shouldBe 14.dp
        }
    }

    context("dark mode") {
        test("keeps the muted items at full strength") {
            val dark = Theme.dark()
            val style = BreadcrumbStyles.Default.resolve(dark)

            style.color shouldBe dark.colors.mutedForeground
        }
    }

    context("breadcrumb") {
        test("navigates through its links") {
            var visited = ""

            runComposeUiTest {
                setContent {
                    Themed {
                        Direction(direction = LayoutDirection.Rtl) {
                            Breadcrumb {
                                BreadcrumbItem { BreadcrumbLink(text = HOME, onClick = { visited = HOME }) }
                                BreadcrumbSeparator()
                                BreadcrumbItem { BreadcrumbEllipsis(contentDescription = MORE) }
                                BreadcrumbSeparator()
                                BreadcrumbItem { BreadcrumbPage(text = PAGE) }
                            }
                        }
                    }
                }

                onNodeWithText(HOME).performMouseInput { moveTo(center) }
                onNodeWithText(HOME).performClick()
                onNodeWithContentDescription(MORE).assertExists()
                onNodeWithText(PAGE).assertIsNotEnabled()
            }

            visited shouldBe HOME
        }

        test("opens the skipped levels from the ellipsis and goes back to one of them") {
            var visited = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Breadcrumb {
                            BreadcrumbItem { BreadcrumbLink(text = HOME, onClick = { visited = HOME }) }
                            BreadcrumbSeparator()
                            BreadcrumbItem {
                                BreadcrumbEllipsis(contentDescription = MORE) {
                                    item(label = DOCUMENTATION, onClick = { visited = DOCUMENTATION })
                                    item(label = THEMES, onClick = { visited = THEMES })
                                }
                            }
                            BreadcrumbSeparator()
                            BreadcrumbItem { BreadcrumbPage(text = PAGE) }
                        }
                    }
                }

                onNodeWithContentDescription(MORE).performMouseInput { moveTo(center) }
                onNodeWithContentDescription(MORE).performClick()
                waitForIdle()
                onNodeWithText(THEMES).performClick()
                waitForIdle()
                onNodeWithText(THEMES).assertDoesNotExist()
            }

            visited shouldBe THEMES
        }
    }
})
