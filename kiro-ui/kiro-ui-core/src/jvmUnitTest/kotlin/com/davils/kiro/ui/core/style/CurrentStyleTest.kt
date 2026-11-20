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

package com.davils.kiro.ui.core.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val accentKey: StyleKey<Color> = StyleKey(name = "accent") { theme -> theme.colors.accent }

@OptIn(ExperimentalTestApi::class)
class CurrentStyleTest : FunSpec({
    context("current") {
        test("is the default of the key for the theme of the composition") {
            var style = Color.Unspecified

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.dark()) { style = accentKey.current }
                }
            }

            style shouldBe Theme.dark().colors.accent
        }

        test("follows the overrides of the theme") {
            val theme = Theme.light().copy(components = ComponentStyles.Default.with(accentKey) { _, _ -> Color.Red })
            var style = Color.Unspecified

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = theme) { style = accentKey.current }
                }
            }

            style shouldBe Color.Red
        }
    }
})
