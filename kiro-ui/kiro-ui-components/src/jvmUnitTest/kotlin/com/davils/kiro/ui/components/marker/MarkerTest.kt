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

package com.davils.kiro.ui.components.marker

import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.lucide.Info
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalTestApi::class)
class MarkerTest : FunSpec({
    context("styles") {
        test("are muted text-sm rows with border lines") {
            val light = Theme.light()
            val style = MarkerStyles.Default.resolve(light)

            style.color shouldBe light.colors.mutedForeground
            style.gap shouldBe 8.dp
            style.lineColor shouldBe light.colors.border
        }
    }

    context("marker") {
        test("renders every variant") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            MarkerVariant.entries.forEach { variant ->
                                Marker(variant = variant) {
                                    MarkerIcon { Icon(icon = Lucide.Info, contentDescription = null) }
                                    Text("Marker $variant")
                                }
                            }
                        }
                    }
                }

                MarkerVariant.entries.forEach { variant -> onNodeWithText("Marker $variant").assertExists() }
            }
        }
    }
})
