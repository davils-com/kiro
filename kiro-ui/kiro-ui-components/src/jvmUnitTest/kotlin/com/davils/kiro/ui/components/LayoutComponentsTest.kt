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

package com.davils.kiro.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.aspectratio.AspectRatio
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.empty.Empty
import com.davils.kiro.ui.components.empty.EmptyContent
import com.davils.kiro.ui.components.empty.EmptyDescription
import com.davils.kiro.ui.components.empty.EmptyHeader
import com.davils.kiro.ui.components.empty.EmptyMedia
import com.davils.kiro.ui.components.empty.EmptyMediaVariant
import com.davils.kiro.ui.components.empty.EmptyStyles
import com.davils.kiro.ui.components.empty.EmptyTitle
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.progress.Progress
import com.davils.kiro.ui.components.progress.ProgressStyles
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Inbox
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HALF: Float = 0.5f

@OptIn(ExperimentalTestApi::class)
class LayoutComponentsTest : FunSpec({
    val light = Theme.light()

    context("empty") {
        test("is p-6 gap-4 without a border and with an icon media of size-8") {
            val style = EmptyStyles.Default.resolve(light)

            style.padding shouldBe 24.dp
            style.gap shouldBe 16.dp
            style.border shouldBe null
            style.iconMediaSize shouldBe 32.dp
            style.titleStyle.fontFamily shouldBe light.typography.families.heading
        }

        test("renders media, title, description and content") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Empty {
                            EmptyHeader {
                                EmptyMedia(variant = EmptyMediaVariant.Icon) {
                                    Icon(icon = Lucide.Inbox, contentDescription = null)
                                }
                                EmptyMedia { Text("media") }
                                EmptyTitle(text = "No projects")
                                EmptyDescription(text = "Create your first project.")
                            }
                            EmptyContent { Text("Create") }
                        }
                    }
                }

                onNodeWithText("No projects").assertExists()
                onNodeWithText("Create").assertExists()
            }
        }
    }

    context("aspect ratio") {
        test("derives the height from the width") {
            runComposeUiTest {
                setContent { AspectRatio(ratio = 2f, modifier = Modifier.width(40.dp).testTag(TAG)) {} }

                onNodeWithTag(TAG).assertHeightIsEqualTo(20.dp)
            }
        }
    }

    context("direction") {
        test("sets the layout direction of its content") {
            var direction = LayoutDirection.Ltr

            runComposeUiTest {
                setContent { Direction(direction = LayoutDirection.Rtl) { direction = LocalLayoutDirection.current } }
            }

            direction shouldBe LayoutDirection.Rtl
        }
    }

    context("progress") {
        test("is an h-1 track in the muted color with a primary indicator") {
            val style = ProgressStyles.Default.resolve(light)

            style.trackHeight shouldBe 4.dp
            style.trackColor shouldBe light.colors.muted
            style.indicatorColor shouldBe light.colors.primary
            style.gap shouldBe 12.dp
        }

        test("reports its value, shows label and percentage and clamps the value") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Progress(value = HALF, modifier = Modifier.testTag(TAG), label = "Upload", isValueShown = true)
                        Progress(value = 2f, isValueShown = true)
                    }
                }

                val range = ProgressBarRangeInfo(current = HALF, range = 0f..1f)
                onNodeWithTag(TAG).assert(SemanticsMatcher.expectValue(SemanticsProperties.ProgressBarRangeInfo, range))
                onNodeWithText("50%").assertExists()
                onNodeWithText("100%").assertExists()
            }
        }

        test("shows only the label when there is no value to show") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Progress(value = null, label = "Pending", isValueShown = true)
                        Progress(value = HALF, isValueShown = false)
                    }
                }

                onNodeWithText("Pending").assertExists()
                onNodeWithText("50%").assertDoesNotExist()
            }
        }

        test("stays indeterminate without a value and without motion") {
            runComposeUiTest {
                setContent {
                    Themed(
                        theme = reducedMotion,
                    ) { Progress(value = null, modifier = Modifier.testTag(TAG), label = "Wait") }
                }

                val node = onNodeWithTag(TAG)
                node.assert(
                    SemanticsMatcher.expectValue(
                        SemanticsProperties.ProgressBarRangeInfo,
                        ProgressBarRangeInfo.Indeterminate,
                    ),
                )
            }
        }
    }
})
