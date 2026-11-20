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

package com.davils.kiro.ui.components.trace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val FAILED: String = "Failed"
private const val SEARCH: String = "Searching the web"
private const val PENDING: Float = 0.6f

@OptIn(ExperimentalTestApi::class)
class TraceTest : FunSpec({
    context("styles") {
        test("are muted text-sm lines that dim pending steps") {
            val light = Theme.light()
            val style = TraceStyles.Default.resolve(light)

            style.color shouldBe light.colors.mutedForeground
            style.errorColor shouldBe light.colors.destructive
            style.pendingAlpha shouldBe PENDING
        }
    }

    context("trace") {
        test("shows lines in every state and expands details") {
            var isExpanded by mutableStateOf(false)
            var opened = ""

            runComposeUiTest {
                setContent {
                    Themed {
                        Trace {
                            TraceLine(status = TraceStatus.Running) {
                                TraceTitle(text = SEARCH)
                                TraceDescription(text = "2s")
                            }
                            TraceLine(
                                status = TraceStatus.Error,
                                onClick = { opened = "error" },
                            ) { TraceTitle(text = FAILED) }
                            TraceLine(status = TraceStatus.Pending) { TraceIcon { Text("•") } }
                            TraceCollapsible(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
                                TraceCollapsibleTrigger { TraceTitle(text = "Thought for 3s") }
                                TraceCollapsibleContent { Text("Details") }
                            }
                        }
                    }
                }

                onNodeWithText(SEARCH).assertExists()
                onNodeWithText(FAILED).performMouseInput { moveTo(center) }
                onNodeWithText(FAILED).performClick()
                onNodeWithText("Thought for 3s").performClick()
                waitForIdle()
                onNodeWithText("Details").assertExists()
            }

            opened shouldBe "error"
            isExpanded shouldBe true
        }
    }
})
