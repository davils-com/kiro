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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

private const val LINES: Int = 40
private const val OTHER: String = "other"

@OptIn(ExperimentalTestApi::class)
class ScrollAreaTest : FunSpec({
    context("styles") {
        test("draw a w-2.5 scrollbar with a rounded border-colored thumb") {
            val light = Theme.light()
            val style = ScrollAreaStyles.Default.resolve(light)

            style.thickness shouldBe 10.dp
            style.inset shouldBe 1.dp
            style.thumbColor shouldBe light.colors.border
        }
    }

    context("scroll area") {
        test("scrolls content that is larger than its viewport") {
            var offset = 0

            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            val state = rememberScrollState()
                            ScrollArea(modifier = Modifier.size(120.dp).testTag(TAG), verticalState = state) {
                                Column { repeat(LINES) { line -> Text("Line $line") } }
                            }
                            LaunchedEffect(state) {
                                state.scrollTo(state.maxValue)
                                offset = state.value
                            }
                            ScrollArea(
                                modifier = Modifier.size(120.dp).testTag(OTHER),
                                direction = ScrollDirection.Both,
                            ) {
                                Row { repeat(LINES) { line -> Text("Column $line") } }
                            }
                            ScrollArea(modifier = Modifier.size(120.dp), direction = ScrollDirection.Horizontal) {
                                Text("Short")
                            }
                        }
                    }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(120.dp)
                onNodeWithTag(OTHER).assertHeightIsEqualTo(120.dp)
            }

            offset shouldBeGreaterThan 0
        }
    }
})
