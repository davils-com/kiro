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

package com.davils.kiro.ui.components.codeblock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.codeblock.fixture.RecordingClipboard
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.File
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import kotlin.time.Duration.Companion.seconds

private const val CODE: String = "val kide = true\nprintln(kide)\n"
private const val WAIT_MILLIS: Long = 2500

@OptIn(ExperimentalTestApi::class, ExperimentalComposeUiApi::class)
class CodeBlockTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are rounded-lg input-bordered blocks with mono text") {
            val style = CodeBlockStyles.Default.resolve(
                light,
                CodeBlockVariant.Default,
                CodeBlockSize.Default,
            )

            style.border.color shouldBe light.colors.input
            style.padding shouldBe 12.dp
            style.codeStyle.fontFamily shouldBe light.typography.families.mono
            style.copiedDuration shouldBe 2.seconds
        }

        test("tint destructive blocks and shrink small ones") {
            val destructive = CodeBlockStyles.Default.resolve(
                light,
                CodeBlockVariant.Destructive,
                CodeBlockSize.Sm,
            )

            destructive.content shouldBe light.colors.destructive
            destructive.padding shouldBe 10.dp
            shouldThrow<IllegalArgumentException> {
                CodeBlockStyles.Default.resolve(
                    light,
                    Unknown,
                    CodeBlockSize.Default,
                )
            }
            shouldThrow<IllegalArgumentException> {
                CodeBlockStyles.Default.resolve(
                    light,
                    CodeBlockVariant.Default,
                    Unknown,
                )
            }
        }
    }

    context("highlighter") {
        test("keeps plain text by default") {
            CodeHighlighter.Plain.highlight("x") shouldBe AnnotatedString("x")
        }
    }

    context("code block") {
        test("shows its header, numbered lines and footer and copies its code") {
            val clipboard = RecordingClipboard()

            runComposeUiTest {
                setContent {
                    Themed {
                        CompositionLocalProvider(LocalClipboard provides clipboard) {
                            CodeBlock(modifier = Modifier.width(240.dp).testTag(TAG)) {
                                CodeBlockHeader {
                                    CodeBlockTitle(text = "Main.kt", icon = Lucide.File)
                                    CodeBlockActions { CodeBlockCopyButton(value = CODE) }
                                }
                                CodeBlockContent(
                                    code = CODE,
                                    isLineNumbered = true,
                                    lineVariants = mapOf(
                                        1 to CodeLineVariant.Added,
                                        2 to CodeLineVariant.Removed,
                                    ),
                                )
                                CodeBlockFooter { Text("Kotlin") }
                            }
                        }
                    }
                }

                onNodeWithText("Main.kt").assertExists()
                onNodeWithText("Kotlin").assertExists()
                onNodeWithContentDescription("Copy").performClick()
                waitForIdle()
                onNodeWithContentDescription("Copied").assertExists()
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithContentDescription("Copy").assertExists()
            }

            val entry = requireNotNull(clipboard.entry)
            val copied = entry.nativeClipEntry as Transferable
            copied.getTransferData(DataFlavor.stringFlavor) shouldBe CODE
        }

        test("wraps long lines, floats its actions and shows focus") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            CodeBlock(
                                modifier = Modifier.width(200.dp).testTag(TAG),
                                variant = CodeBlockVariant.Destructive,
                                size = CodeBlockSize.Sm,
                                actions = { Text("Act") },
                            ) {
                                CodeBlockContent(
                                    code = "error: something went wrong while compiling the module",
                                    isWrapped = true,
                                    lineVariants = mapOf(1 to CodeLineVariant.Highlighted),
                                )
                            }
                            CodeBlock { CodeBlockContent(code = "plain") }
                        }
                    }
                }

                onNodeWithTag(TAG).performMouseInput { moveTo(center) }
                waitForIdle()
                onNodeWithText("Act").assertExists()
                onNodeWithText("plain").assertExists()
            }
        }
    }
})
