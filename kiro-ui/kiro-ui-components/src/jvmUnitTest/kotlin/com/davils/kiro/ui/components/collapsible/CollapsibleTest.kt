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

package com.davils.kiro.ui.components.collapsible

import androidx.compose.animation.EnterTransition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val DETAILS: String = "Details"

@OptIn(ExperimentalTestApi::class)
class CollapsibleTest : FunSpec({
    context("styles") {
        test("expand vertically unless motion is reduced") {
            CollapsibleStyles.Default.resolve(Theme.light()).enter shouldNotBe EnterTransition.None
            CollapsibleStyles.Default.resolve(reducedMotion).enter shouldBe EnterTransition.None
        }
    }

    context("collapsible") {
        test("shows its content after the trigger was clicked") {
            runComposeUiTest {
                setContent {
                    Themed {
                        var isExpanded by remember { mutableStateOf(false) }
                        Collapsible(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
                            CollapsibleTrigger(modifier = Modifier.testTag(TAG)) { Text("Toggle") }
                            CollapsibleContent { Text(DETAILS) }
                        }
                    }
                }

                onNodeWithText(DETAILS).assertDoesNotExist()
                onNodeWithTag(TAG).performClick()
                waitForIdle()
                onNodeWithText(DETAILS).assertExists()
                onNodeWithTag(TAG).performSemanticsAction(SemanticsActions.Collapse)
                onNodeWithTag(TAG).performSemanticsAction(SemanticsActions.Expand)
            }
        }

        test("ignores toggles when it is disabled") {
            var changes = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Collapsible(expanded = false, onExpandedChange = { changes++ }, enabled = false) {
                            CollapsibleTrigger(modifier = Modifier.testTag(TAG)) { Text("Toggle") }
                            toggle()
                        }
                    }
                }

                onNodeWithTag(TAG).performClick()
            }

            changes shouldBe 0
        }
    }
})
