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

package com.davils.kiro.ui.components.field

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.checkbox.Checkbox
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.input.Input
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val NAME: String = "Name"
private const val TERMS: String = "terms"

@OptIn(ExperimentalTestApi::class)
class FieldTest : FunSpec({
    context("styles") {
        test("space fields by 2, sets by 4 and groups by 5 with muted descriptions") {
            val light = Theme.light()
            val style = FieldStyles.Default.resolve(light)

            style.gap shouldBe 8.dp
            style.setGap shouldBe 16.dp
            style.groupGap shouldBe 20.dp
            style.legendStyle.fontSize shouldBe 16.sp
            style.legendLabelStyle.fontSize shouldBe 14.sp
            style.descriptionColor shouldBe light.colors.mutedForeground
            style.invalidColor shouldBe light.colors.destructive
        }
    }

    context("field") {
        test("stacks label, control, description and errors") {
            runComposeUiTest {
                setContent {
                    Themed {
                        FieldSet {
                            FieldLegend(text = "Profile")
                            FieldLegend(text = "Details", variant = FieldLegendVariant.Label)
                            FieldGroup {
                                Field(isInvalid = true, isDisabled = true) {
                                    FieldLabel(text = NAME)
                                    Input(value = "", onValueChange = {})
                                    FieldDescription(text = "Shown on your profile.")
                                    FieldError(messages = listOf("Required.", "Required.", "Too short."))
                                }
                                FieldSeparator { Text("or") }
                                FieldSeparator()
                                Field {
                                    FieldError(messages = listOf("Taken."), modifier = Modifier.testTag(TAG))
                                    FieldError(messages = emptyList())
                                }
                            }
                        }
                    }
                }

                onNodeWithText(NAME).assertExists()
                onNodeWithText("Too short.").assertExists()
                onNodeWithText("or").assertExists()
                onNodeWithTag(
                    TAG,
                ).assert(SemanticsMatcher.expectValue(SemanticsProperties.LiveRegion, LiveRegionMode.Assertive))
            }
        }

        test("puts a control beside its content and aligns them to the top") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Field(modifier = Modifier.width(300.dp), orientation = FieldOrientation.Horizontal) {
                            Checkbox(checked = true, onCheckedChange = {}, modifier = Modifier.testTag(TERMS))
                            FieldContent {
                                FieldTitle(text = "Accept the terms")
                                FieldDescription(text = "You agree to the rules.")
                            }
                        }
                    }
                }

                val box = onNodeWithTag(TERMS).getUnclippedBoundsInRoot()
                val title = onNodeWithText("Accept the terms").getUnclippedBoundsInRoot()
                title.left shouldBeGreaterThan box.right
                box.top shouldBe 1.dp
            }
        }

        test("centers a label beside its control") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Field(modifier = Modifier.width(300.dp), orientation = FieldOrientation.Horizontal) {
                            Checkbox(checked = false, onCheckedChange = {}, modifier = Modifier.testTag(TERMS))
                            FieldLabel(text = NAME)
                        }
                    }
                }

                onNodeWithText(NAME).getUnclippedBoundsInRoot().left shouldBe 24.dp
            }
        }

        test("switches to a row when it is wide enough") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            Field(modifier = Modifier.width(500.dp), orientation = FieldOrientation.Responsive) {
                                FieldLabel(text = NAME, modifier = Modifier.testTag(TAG))
                                Text("wide")
                            }
                            Field(modifier = Modifier.width(200.dp), orientation = FieldOrientation.Responsive) {
                                FieldLabel(text = "Narrow")
                                Text("narrow")
                            }
                        }
                    }
                }

                val label = onNodeWithTag(TAG).getUnclippedBoundsInRoot()
                val wide = onNodeWithText("wide").getUnclippedBoundsInRoot()
                wide.left shouldBeGreaterThan label.right
                onNodeWithText("narrow").getUnclippedBoundsInRoot().top shouldBeGreaterThan
                    onNodeWithText("Narrow").getUnclippedBoundsInRoot().top
            }
        }
    }
})
