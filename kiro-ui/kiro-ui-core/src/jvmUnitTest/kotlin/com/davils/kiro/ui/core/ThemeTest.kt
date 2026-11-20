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

package com.davils.kiro.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.intl.LocaleList
import com.davils.kiro.ui.core.color.Colors
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.elevation.ShadowStrength
import com.davils.kiro.ui.core.focus.LocalInputModality
import com.davils.kiro.ui.core.typography.FontFamilies
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

@OptIn(ExperimentalTestApi::class)
class ThemeTest : FunSpec({
    context("current") {
        test("is the light theme without a provider") {
            var current: Theme? = null

            runComposeUiTest {
                setContent { current = Theme.current }
            }

            current shouldBe Theme.light()
        }

        test("is the theme of the nearest provider") {
            var current: Theme? = null

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.light()) {
                        ThemeProvider(theme = Theme.dark()) { current = Theme.current }
                    }
                }
            }

            current?.isDark shouldBe true
        }
    }

    context("ThemeProvider") {
        test("provides the given theme with the bundled fonts loaded") {
            var current: Theme? = null

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.dark()) { current = Theme.current }
                }
            }

            val provided = requireNotNull(current)
            provided.colors shouldBe Theme.dark().colors
            provided.typography.families shouldNotBe FontFamilies.Bundled
        }

        test("keeps the font families of a theme that brings its own") {
            val system = Theme.light().copy(typography = Theme.light().typography.copy(families = FontFamilies.System))
            var current: Theme? = null

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = system) { current = Theme.current }
                }
            }

            current shouldBe system
        }

        test("makes the foreground the content color") {
            var color = Color.Unspecified

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.dark()) { color = LocalContentColor.current }
                }
            }

            color shouldBe Theme.dark().colors.foreground
        }

        test("sets body text in the sans family at the base size") {
            var style = TextStyle.Default
            var family: FontFamily? = null

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.light()) {
                        style = LocalTextStyle.current
                        family = Theme.current.typography.families.sans
                    }
                }
            }

            style.fontFamily shouldBe family
            val scale = Theme.light().typography.scale
            style.fontSize shouldBe scale.base.fontSize
        }

        test("shapes text for the languages of the theme") {
            val german = Theme.light().copy(localeList = LocaleList("de-DE"))
            var style = TextStyle.Default

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = german) { style = LocalTextStyle.current }
                }
            }

            style.localeList shouldBe LocaleList("de-DE")
        }

        test("tracks the input modality of its content") {
            var hasModality = false

            runComposeUiTest {
                setContent {
                    ThemeProvider(theme = Theme.light()) { hasModality = LocalInputModality.current != null }
                }
            }

            hasModality shouldBe true
        }

        test("recomposes its content when the theme changes") {
            var isDark by mutableStateOf(false)
            var current: Theme? = null

            runComposeUiTest {
                setContent {
                    val selected = when (isDark) {
                        true -> Theme.dark()
                        false -> Theme.light()
                    }
                    Forwarding(theme = selected) { current = Theme.current }
                }
                isDark = true
                waitForIdle()
            }

            current?.isDark shouldBe true
        }
    }

    context("ApplicationThemeProvider") {
        test("provides the theme without tracking the input modality") {
            var current: Theme? = null
            var hasModality = true

            runComposeUiTest {
                setContent {
                    ApplicationThemeProvider(theme = Theme.dark()) {
                        current = Theme.current
                        hasModality = LocalInputModality.current != null
                    }
                }
            }

            current?.isDark shouldBe true
            hasModality shouldBe false
        }
    }

    context("factories") {
        test("build a light and a dark theme from the color schemes of Kiro") {
            Theme.light().colors shouldBe Colors.light()
            Theme.dark().colors shouldBe Colors.dark()
            Theme.light().isDark shouldBe false
            Theme.dark().isDark shouldBe true
        }

        test("draw the shadows in the shadow color of the theme") {
            val red = Colors.light().copy(shadow = Color.Red)

            val shadows = Theme.light(colors = red).shadows
            val layers = shadows.sm.layers
            layers.first().color shouldBe Color.Red.copy(alpha = ShadowStrength().soft)
        }
    }
})

@Composable
private fun Forwarding(theme: Theme, content: @Composable () -> Unit) {
    ThemeProvider(theme = theme, content = content)
}
