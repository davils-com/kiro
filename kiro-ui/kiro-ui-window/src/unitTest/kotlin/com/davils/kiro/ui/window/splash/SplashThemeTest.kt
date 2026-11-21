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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.window.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.fixture.Themed
import com.davils.kiro.ui.window.host.LocalWindowHost
import com.davils.kiro.ui.window.splash.centered.CenteredSplash
import com.davils.kiro.ui.window.splash.centered.CenteredSplashStyleKey
import com.davils.kiro.ui.window.splash.centered.CenteredSplashStyles
import com.davils.kiro.ui.window.splash.checklist.ChecklistSplash
import com.davils.kiro.ui.window.splash.checklist.ChecklistSplashStyleKey
import com.davils.kiro.ui.window.splash.checklist.ChecklistSplashStyles
import com.davils.kiro.ui.window.splash.hero.HeroSplash
import com.davils.kiro.ui.window.splash.hero.HeroSplashStyleKey
import com.davils.kiro.ui.window.splash.hero.HeroSplashStyles
import com.davils.kiro.ui.window.splash.minimal.MinimalSplash
import com.davils.kiro.ui.window.splash.minimal.MinimalSplashStyleKey
import com.davils.kiro.ui.window.splash.minimal.MinimalSplashStyles
import com.davils.kiro.ui.window.splash.split.SplitSplash
import com.davils.kiro.ui.window.splash.split.SplitSplashStyleKey
import com.davils.kiro.ui.window.splash.split.SplitSplashStyles
import com.davils.kiro.ui.window.testing.DetachedWindowHost
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val small: DpSize = DpSize(width = 10.dp, height = 10.dp)
private val bounds: Modifier = Modifier.size(width = 880.dp, height = 520.dp)
private val silent: NativeSplash = NativeSplash {}

private fun overridden(): Theme {
    val theme = Theme.light()
    val window = theme.components.with(SplashWindowStyleKey) { _, styles ->
        SplashWindowStyles { current -> styles.resolve(current).copy(size = small) }
    }
    val split = window.with(SplitSplashStyleKey) { _, styles ->
        SplitSplashStyles { current -> styles.resolve(current).copy(size = small) }
    }
    val centered = split.with(CenteredSplashStyleKey) { _, styles ->
        CenteredSplashStyles { current -> styles.resolve(current).copy(size = small) }
    }
    val checklist = centered.with(ChecklistSplashStyleKey) { _, styles ->
        ChecklistSplashStyles { current -> styles.resolve(current).copy(size = small) }
    }
    val minimal = checklist.with(MinimalSplashStyleKey) { _, styles ->
        MinimalSplashStyles { current -> styles.resolve(current).copy(size = small) }
    }
    val hero = minimal.with(HeroSplashStyleKey) { _, styles ->
        HeroSplashStyles { current -> styles.resolve(current).copy(size = small) }
    }
    return theme.copy(components = hero)
}

@Composable
private fun Ticker(tick: Int) {
    Text(text = "tick $tick")
}

private val variants: Map<String, @Composable (Int) -> Unit> = mapOf(
    "split" to { tick ->
        Ticker(tick)
        SplitSplash(splash = sample, modifier = bounds)
    },
    "centered" to { tick ->
        Ticker(tick)
        CenteredSplash(splash = sample, modifier = bounds)
    },
    "checklist" to { tick ->
        Ticker(tick)
        ChecklistSplash(splash = sample, modifier = bounds)
    },
    "minimal" to { tick ->
        Ticker(tick)
        MinimalSplash(splash = sample, modifier = bounds)
    },
    "hero" to { tick ->
        Ticker(tick)
        HeroSplash(splash = sample, modifier = bounds)
    },
    "window" to { tick ->
        Ticker(tick)
        SplashWindow(onCloseRequest = {}, title = NAME) { Text(text = NAME) }
    },
)

@OptIn(ExperimentalTestApi::class)
class SplashThemeTest : FunSpec({
    context("theme") {
        test("follows the overrides of the theme for the window and every splash") {
            val theme = overridden()
            val components = theme.components

            components.resolve(SplashWindowStyleKey, theme).resolve(theme).size shouldBe small
            components.resolve(SplitSplashStyleKey, theme).resolve(theme).size shouldBe small
            components.resolve(CenteredSplashStyleKey, theme).resolve(theme).size shouldBe small
            components.resolve(ChecklistSplashStyleKey, theme).resolve(theme).size shouldBe small
            components.resolve(MinimalSplashStyleKey, theme).resolve(theme).size shouldBe small
            components.resolve(HeroSplashStyleKey, theme).resolve(theme).size shouldBe small
        }
    }

    context("recomposition") {
        variants.forEach { (name, variant) ->
            test("keeps the $name splash while what surrounds it recomposes") {
                var tick by mutableIntStateOf(0)

                runComposeUiTest {
                    setContent {
                        Themed {
                            CompositionLocalProvider(
                                LocalWindowHost provides DetachedWindowHost(),
                                LocalNativeSplash provides silent,
                            ) {
                                Column { variant(tick) }
                            }
                        }
                    }
                    tick = 1
                    waitForIdle()

                    onNodeWithText("tick 1").assertIsDisplayed()
                    onNodeWithText(NAME).assertExists()
                }
            }
        }
    }
})
