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

package com.davils.kiro.ui.components.alertdialog

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TITLE: String = "Are you absolutely sure?"
private const val CONTINUE: String = "Continue"

@OptIn(ExperimentalTestApi::class)
class AlertDialogTest : FunSpec({
    context("sizes") {
        test("are max-w-sm by default and max-w-xs when small") {
            maxWidthOf(AlertDialogSize.Default) shouldBe 384.dp
            maxWidthOf(AlertDialogSize.Sm) shouldBe 320.dp
            shouldThrow<IllegalArgumentException> { maxWidthOf(Unknown) }
        }
    }

    context("alert dialog") {
        test("confirms with its action and cancels with its cancel button") {
            var isOpen by mutableStateOf(true)
            var isConfirmed = false

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Column {
                            AlertDialog(open = isOpen, onOpenChange = { isOpen = it }) {
                                AlertDialogHeader(
                                    media = {
                                        AlertDialogMedia {
                                            Icon(
                                                icon = Lucide.User,
                                                contentDescription = null,
                                            )
                                        }
                                    },
                                ) {
                                    AlertDialogTitle(text = TITLE)
                                    AlertDialogDescription(text = "This action cannot be undone.")
                                }
                                AlertDialogFooter {
                                    AlertDialogCancel(onClick = { isOpen = false }) { Text("Cancel") }
                                    AlertDialogAction(onClick = {
                                        isConfirmed = true
                                        isOpen = false
                                    }) { Text(CONTINUE) }
                                }
                            }
                        }
                    }
                }

                onNodeWithText(CONTINUE).performClick()
                waitForIdle()
            }

            isConfirmed shouldBe true
            isOpen shouldBe false
        }

        test("centers a small dialog and stretches its buttons") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        AlertDialog(open = true, onOpenChange = {}, size = AlertDialogSize.Sm) {
                            AlertDialogHeader(media = { AlertDialogMedia { Text("!") } }) {
                                AlertDialogTitle(text = TITLE)
                                AlertDialogDescription(text = "Small")
                            }
                            AlertDialogFooter {
                                AlertDialogCancel(onClick = {}) { Text("Cancel") }
                                AlertDialogAction(onClick = {}) { Text(CONTINUE) }
                            }
                        }
                    }
                }

                onNodeWithText("Small").assertExists()
            }
        }
    }
})
