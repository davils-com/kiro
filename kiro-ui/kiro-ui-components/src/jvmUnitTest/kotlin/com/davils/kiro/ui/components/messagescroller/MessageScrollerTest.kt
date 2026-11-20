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

package com.davils.kiro.ui.components.messagescroller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking

private const val LABEL: String = "Scroll to end"
private const val ITEMS: Int = 12

@OptIn(ExperimentalTestApi::class)
class MessageScrollerTest : FunSpec({
    context("styles") {
        test("space the messages with gap-6 and place the button bottom-4") {
            val style = MessageScrollerStyles.Default.resolve(Theme.light())

            style.gap shouldBe 24.dp
            style.buttonInset shouldBe 16.dp
            style.label shouldBe LABEL
        }
    }

    context("message scroller") {
        test("follows new messages and offers a way back to the end") {
            var count by mutableIntStateOf(ITEMS)
            var state: MessageScrollerState? = null

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        val scroller = rememberMessageScrollerState()
                        state = scroller
                        MessageScroller(modifier = Modifier.size(width = 300.dp, height = 200.dp), state = scroller) {
                            repeat(count) { Box(Modifier.fillMaxWidth().height(40.dp)) }
                        }
                    }
                }

                val scroller = requireNotNull(state)
                waitForIdle()
                scroller.isAtEnd shouldBe true
                onNodeWithContentDescription(LABEL).assertDoesNotExist()

                count++
                waitForIdle()
                scroller.isAtEnd shouldBe true

                runBlocking { scroller.scroll.scrollTo(0) }
                waitForIdle()
                scroller.isAtStart shouldBe true
                count++
                waitForIdle()
                scroller.isAtStart shouldBe true

                onNodeWithContentDescription(LABEL).performMouseInput { moveTo(center) }
                onNodeWithContentDescription(LABEL).performClick()
                waitForIdle()
                scroller.isAtEnd shouldBe true
            }
        }

        test("hides the button when it is switched off") {
            runComposeUiTest {
                setContent {
                    Themed {
                        val scroller = rememberMessageScrollerState()
                        MessageScroller(modifier = Modifier.size(200.dp), state = scroller, isButtonShown = false) {
                            repeat(ITEMS) { Box(Modifier.fillMaxWidth().height(40.dp)) }
                        }
                    }
                }

                onNodeWithContentDescription(LABEL).assertDoesNotExist()
            }
        }
    }
})
