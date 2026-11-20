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

package com.davils.kiro.ui.components.attachment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.width
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.Unknown
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.X
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.comparables.shouldBeGreaterThanOrEqualTo
import io.kotest.matchers.shouldBe

private const val FILE: String = "report.pdf"
private const val REMOVE: String = "Remove"

@OptIn(ExperimentalTestApi::class)
class AttachmentTest : FunSpec({
    val light = Theme.light()

    context("styles") {
        test("are rounded-xl cards with size-10 media and a minimum width of 160") {
            val style = AttachmentStyles.Default.resolve(light, AttachmentSize.Default)

            style.shape shouldBe RoundedCornerShape(light.radii.xl)
            style.container shouldBe light.colors.card
            style.mediaSize shouldBe 40.dp
            style.minWidth shouldBe 160.dp
        }

        test("shrink the corners and the media for the smaller sizes") {
            val small = AttachmentStyles.Default.resolve(light, AttachmentSize.Sm)
            val tiny = AttachmentStyles.Default.resolve(light, AttachmentSize.Xs)

            small.mediaSize shouldBe 32.dp
            tiny.mediaSize shouldBe 28.dp
            tiny.shape shouldBe RoundedCornerShape(light.radii.lg)
            tiny.errorDescription() shouldBe tiny.errorContent.copy(alpha = 0.8f)
        }

        test("reject an unknown size") {
            shouldThrow<IllegalArgumentException> { AttachmentStyles.Default.resolve(light, Unknown) }
        }
    }

    context("attachment") {
        test("lays out media, content and actions in a row and reacts to clicks") {
            var opened = 0
            var removed = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Attachment(modifier = Modifier.testTag(TAG), onClick = { opened++ }) {
                            AttachmentMedia { Text("PDF") }
                            AttachmentContent {
                                AttachmentTitle(text = FILE)
                                AttachmentDescription(text = "2.4 MB")
                            }
                            AttachmentActions {
                                AttachmentAction(
                                    onClick = { removed++ },
                                    icon = Lucide.X,
                                    contentDescription = REMOVE,
                                )
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).getUnclippedBoundsInRoot().width shouldBeGreaterThanOrEqualTo 160.dp
                onNodeWithText(FILE).performMouseInput { moveTo(center) }
                onNodeWithText(FILE).performClick()
                onNodeWithContentDescription(REMOVE).performClick()
            }

            opened shouldBe 1
            removed shouldBe 1
        }

        test("shows every state, shimmering while it is busy") {
            runComposeUiTest {
                setContent {
                    Themed {
                        AttachmentGroup {
                            AttachmentState.entries.forEach { state ->
                                Attachment(state = state, size = AttachmentSize.Sm) {
                                    AttachmentMedia(variant = AttachmentMediaVariant.Image) { Box(Modifier) }
                                    AttachmentContent {
                                        AttachmentTitle(text = "File $state")
                                        AttachmentDescription(text = "$state")
                                    }
                                }
                            }
                        }
                    }
                }

                AttachmentState.entries.forEach { state -> onNodeWithText("File $state").assertExists() }
            }
        }

        test("stacks vertically with a width of 120 with content and 96 without") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        AttachmentGroup {
                            Attachment(
                                modifier = Modifier.testTag(TAG),
                                state = AttachmentState.Uploading,
                                orientation = Orientation.Vertical,
                            ) {
                                AttachmentMedia { Text("IMG") }
                                AttachmentContent { AttachmentTitle(text = FILE) }
                                AttachmentActions {
                                    AttachmentAction(onClick = {}, icon = Lucide.X, contentDescription = REMOVE)
                                }
                            }
                            Attachment(modifier = Modifier.testTag("bare"), orientation = Orientation.Vertical) {
                                AttachmentMedia { Text("ZIP") }
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(120.dp)
                onNodeWithTag("bare").assertWidthIsEqualTo(96.dp)
                onNodeWithText(FILE).assertExists()
            }
        }
    }
})
