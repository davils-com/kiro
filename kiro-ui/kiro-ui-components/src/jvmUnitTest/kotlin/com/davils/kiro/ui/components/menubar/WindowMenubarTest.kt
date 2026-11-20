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

package com.davils.kiro.ui.components.menubar

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.menubar.fixture.DetachedWindowScope
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class WindowMenubarTest : FunSpec({
    context("window menubar") {
        test("draws the menubar inside the window off macOS") {
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        with(DetachedWindowScope) {
                            WindowMenubar(platform = DesktopPlatform.Linux) {
                                menu(label = "File") { item(label = "Open", onClick = { clicked = "Open" }) }
                            }
                        }
                    }
                }

                onNodeWithText("File").performClick()
                waitForIdle()
                onNodeWithText("Open").performClick()
                waitForIdle()
            }

            clicked shouldBe "Open"
        }
    }
})
