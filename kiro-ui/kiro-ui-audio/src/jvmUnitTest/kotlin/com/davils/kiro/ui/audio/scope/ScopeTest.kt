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

package com.davils.kiro.ui.audio.scope

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.audio.fixture.TAG
import com.davils.kiro.ui.audio.fixture.Themed
import com.davils.kiro.ui.audio.fixture.scopeOf
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec

@OptIn(ExperimentalTestApi::class)
class ScopeTest : FunSpec({
    test("draws the goniometer and the oscilloscope in every variant") {
        listOf(Theme.light(), Theme.dark()).forEach { theme ->
            runComposeUiTest {
                setContent {
                    Themed(theme = theme) {
                        Column {
                            Goniometer(scope = scopeOf())
                            Goniometer(
                                scope = scopeOf(),
                                style = goniometerStyle().copy(grid = ScopeGrid.Square, trace = ScopeTrace.Lines),
                            )
                            Oscilloscope(scope = scopeOf())
                            Oscilloscope(
                                scope = scopeOf(),
                                style = oscilloscopeStyle().copy(layout = ScopeLayout.Split),
                            )
                            Oscilloscope(scope = scopeOf(size = 1))
                        }
                    }
                }
                onAllNodesWithContentDescription("Goniometer").assertCountEquals(expectedSize = 2)
                onAllNodesWithContentDescription("Oscilloscope").assertCountEquals(expectedSize = 3)
                onRoot().captureToImage()
            }
        }
    }

    test("shows a changed style") {
        runComposeUiTest {
            setContent {
                Themed {
                    Column {
                        Goniometer(
                            scope = scopeOf(),
                            modifier = Modifier.testTag(TAG),
                            style = goniometerStyle().copy(size = 150.dp),
                        )
                        Oscilloscope(
                            scope = scopeOf(),
                            modifier = Modifier.testTag("trace"),
                            style = oscilloscopeStyle().copy(height = 90.dp),
                        )
                    }
                }
            }
            onNodeWithTag(TAG).assertWidthIsEqualTo(150.dp)
            onNodeWithTag("trace").assertHeightIsEqualTo(90.dp)
        }
    }
})
