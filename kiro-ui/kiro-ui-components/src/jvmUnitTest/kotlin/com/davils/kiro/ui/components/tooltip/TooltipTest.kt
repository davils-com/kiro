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

package com.davils.kiro.ui.components.tooltip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.time.Duration

private const val HINT: String = "Add to library"
private const val FAR: Float = 200f

@OptIn(ExperimentalTestApi::class)
class TooltipTest : FunSpec({
    context("styles") {
        test("are inverted text-xs bubbles without delay") {
            val light = Theme.light()
            val style = TooltipStyles.Default.resolve(light)

            style.container shouldBe light.colors.foreground
            style.content shouldBe light.colors.background
            style.horizontalPadding shouldBe 12.dp
            style.openDelay shouldBe Duration.ZERO
        }
    }

    context("tooltip") {
        test("shows while the trigger is hovered on every side") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Column(modifier = Modifier.padding(80.dp)) {
                            Side.entries.forEach { side ->
                                Tooltip(
                                    tooltip = { Text("$HINT $side") },
                                    modifier = Modifier.testTag("$TAG$side"),
                                    placement = Placement(side = side),
                                ) {
                                    Text("Hover $side")
                                }
                            }
                        }
                    }
                }

                Side.entries.forEach { side ->
                    onNodeWithTag("$TAG$side").performMouseInput { moveTo(center) }
                    waitForIdle()
                    onNodeWithText("$HINT $side").assertExists()
                }
                onNodeWithTag("$TAG${Side.End}").performMouseInput {
                    press()
                    release()
                    moveTo(Offset(x = -FAR, y = -FAR))
                }
                waitForIdle()
                onNodeWithText("$HINT ${Side.End}").assertDoesNotExist()
            }
        }
    }
})
