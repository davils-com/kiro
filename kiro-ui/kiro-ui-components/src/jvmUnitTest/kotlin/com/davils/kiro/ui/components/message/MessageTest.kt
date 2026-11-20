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

package com.davils.kiro.ui.components.message

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.bubble.Bubble
import com.davils.kiro.ui.components.bubble.BubbleContent
import com.davils.kiro.ui.components.bubble.BubbleVariant
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val AVATAR: String = "avatar"
private const val MESSAGE: String = "message"

@OptIn(ExperimentalTestApi::class)
class MessageTest : FunSpec({
    context("styles") {
        test("are text-sm rows with gap-2 and muted text-xs labels") {
            val light = Theme.light()
            val style = MessageStyles.Default.resolve(light)

            style.gap shouldBe 8.dp
            style.contentGap shouldBe 10.dp
            style.avatarContainer shouldBe light.colors.muted
            style.labelColor shouldBe light.colors.mutedForeground
            style.avatarLift shouldBe 32.dp
        }
    }

    context("message") {
        test("puts the avatar at the end and lifts it above the footer") {
            var hasFooter by mutableStateOf(true)

            runComposeUiTest {
                setContent {
                    Themed {
                        MessageGroup(modifier = Modifier.width(400.dp)) {
                            Message(
                                modifier = Modifier.testTag(MESSAGE),
                                align = MessageAlign.End,
                                avatar = { Box(Modifier.size(32.dp).testTag(AVATAR)) },
                            ) {
                                MessageHeader { Text("You") }
                                Bubble { BubbleContent { Text("Hello") } }
                                if (hasFooter) MessageFooter { Text("Read") }
                            }
                            Message(avatar = null) {
                                Bubble(variant = BubbleVariant.Ghost) { BubbleContent { Text("Reply") } }
                                MessageFooter { Text("Now") }
                            }
                        }
                    }
                }

                val avatar = onNodeWithTag(AVATAR).getUnclippedBoundsInRoot()
                val message = onNodeWithTag(MESSAGE).getUnclippedBoundsInRoot()
                avatar.right shouldBe 400.dp
                avatar.bottom shouldBe message.bottom - 32.dp
                onNodeWithText("Reply").assertExists()

                hasFooter = false
                waitForIdle()

                onNodeWithTag(
                    AVATAR,
                ).getUnclippedBoundsInRoot().bottom shouldBe onNodeWithTag(MESSAGE).getUnclippedBoundsInRoot().bottom
            }
        }
    }
})
