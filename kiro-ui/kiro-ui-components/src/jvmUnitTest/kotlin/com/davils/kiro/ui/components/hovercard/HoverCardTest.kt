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

package com.davils.kiro.ui.components.hovercard

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.milliseconds

private const val TRIGGER: String = "@nextjs"
private const val CARD: String = "The React Framework"
private const val WAIT_MILLIS: Long = 700
private const val FAR: Float = 500f

@OptIn(ExperimentalTestApi::class)
class HoverCardTest : FunSpec({
    context("styles") {
        test("are w-64 popover panels that open after 600 ms") {
            val style = HoverCardStyles.Default.resolve(Theme.light())

            style.width shouldBe 256.dp
            style.openDelay shouldBe 600.milliseconds
            style.closeDelay shouldBe 300.milliseconds
        }
    }

    context("hover card") {
        test("opens after hovering its trigger and closes after leaving") {
            runComposeUiTest {
                setContent {
                    Themed {
                        HoverCard(trigger = { Text(TRIGGER) }) { Text(CARD) }
                    }
                }

                onNodeWithText(TRIGGER).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(CARD).assertExists()
                onNodeWithText(CARD).performMouseInput { moveTo(center) }
                onNodeWithText(CARD).performMouseInput { moveTo(Offset(x = FAR, y = FAR)) }
                mainClock.advanceTimeBy(WAIT_MILLIS)
                waitForIdle()
                onNodeWithText(CARD).assertDoesNotExist()
            }
        }
    }
})
