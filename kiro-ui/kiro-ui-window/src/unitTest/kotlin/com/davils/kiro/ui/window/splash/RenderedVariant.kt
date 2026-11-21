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

package com.davils.kiro.ui.window.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.splash.centered.CenteredSplash
import com.davils.kiro.ui.window.splash.centered.CenteredSplashStyles
import com.davils.kiro.ui.window.splash.checklist.ChecklistSplash
import com.davils.kiro.ui.window.splash.checklist.ChecklistSplashStyles
import com.davils.kiro.ui.window.splash.hero.HeroSplash
import com.davils.kiro.ui.window.splash.hero.HeroSplashStyles
import com.davils.kiro.ui.window.splash.minimal.MinimalSplash
import com.davils.kiro.ui.window.splash.minimal.MinimalSplashStyles
import com.davils.kiro.ui.window.splash.split.SplitSplash
import com.davils.kiro.ui.window.splash.split.SplitSplashStyles

internal class RenderedVariant(
    val name: String,
    val size: (Theme) -> DpSize,
    val content: @Composable (Splash, Modifier) -> Unit,
) {
    companion object {
        val all: List<RenderedVariant> = listOf(
            RenderedVariant(
                name = "split",
                size = { theme -> SplitSplashStyles.Default.resolve(theme).size },
                content = { splash, modifier -> SplitSplash(splash = splash, modifier = modifier, onRetry = {}) },
            ),
            RenderedVariant(
                name = "centered",
                size = { theme -> CenteredSplashStyles.Default.resolve(theme).size },
                content = { splash, modifier -> CenteredSplash(splash = splash, modifier = modifier, onRetry = {}) },
            ),
            RenderedVariant(
                name = "checklist",
                size = { theme -> ChecklistSplashStyles.Default.resolve(theme).size },
                content = { splash, modifier -> ChecklistSplash(splash = splash, modifier = modifier, onRetry = {}) },
            ),
            RenderedVariant(
                name = "minimal",
                size = { theme -> MinimalSplashStyles.Default.resolve(theme).size },
                content = { splash, modifier -> MinimalSplash(splash = splash, modifier = modifier, onRetry = {}) },
            ),
            RenderedVariant(
                name = "hero",
                size = { theme -> HeroSplashStyles.Default.resolve(theme).size },
                content = { splash, modifier -> HeroSplash(splash = splash, modifier = modifier, onRetry = {}) },
            ),
        )
    }
}
