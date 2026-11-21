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

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.fixture.ValueSample
import com.davils.kiro.ui.window.splash.centered.CenteredSplashStyles
import com.davils.kiro.ui.window.splash.checklist.ChecklistSplashStyles
import com.davils.kiro.ui.window.splash.hero.HeroSplashStyles
import com.davils.kiro.ui.window.splash.minimal.MinimalSplashStyles
import com.davils.kiro.ui.window.splash.split.SplitSplashStyles
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private val small: DpSize = DpSize(width = 1.dp, height = 1.dp)

private fun samples(): List<ValueSample> {
    val theme = Theme.light()
    val step = steps.first()
    val labels = SplashMessages.labels
    val window = SplashWindowStyles.Default.resolve(theme)
    val failure = defaultSplashFailureStyle(theme)
    val split = SplitSplashStyles.Default.resolve(theme)
    val centered = CenteredSplashStyles.Default.resolve(theme)
    val checklist = ChecklistSplashStyles.Default.resolve(theme)
    val minimal = MinimalSplashStyles.Default.resolve(theme)
    val hero = HeroSplashStyles.Default.resolve(theme)
    return listOf(
        ValueSample(name = "Splash", value = sample, copy = sample.copy(), changed = sample.copy(progress = 1f)),
        ValueSample(name = "SplashStep", value = step, copy = step.copy(), changed = step.copy(label = "b")),
        ValueSample(name = "SplashLabels", value = labels, copy = labels.copy(), changed = labels.copy(close = "Exit")),
        ValueSample(
            name = "SplashWindowStyle",
            value = window,
            copy = window.copy(),
            changed = window.copy(size = small),
        ),
        ValueSample(
            name = "SplashFailureStyle",
            value = failure,
            copy = failure.copy(),
            changed = failure.copy(maxLines = 1),
        ),
        ValueSample(name = "SplitSplashStyle", value = split, copy = split.copy(), changed = split.copy(size = small)),
        ValueSample(
            name = "CenteredSplashStyle",
            value = centered,
            copy = centered.copy(),
            changed = centered.copy(size = small),
        ),
        ValueSample(
            name = "ChecklistSplashStyle",
            value = checklist,
            copy = checklist.copy(),
            changed = checklist.copy(size = small),
        ),
        ValueSample(
            name = "MinimalSplashStyle",
            value = minimal,
            copy = minimal.copy(),
            changed = minimal.copy(size = small),
        ),
        ValueSample(name = "HeroSplashStyle", value = hero, copy = hero.copy(), changed = hero.copy(size = small)),
    )
}

class SplashValueTypesTest : FunSpec({
    context("every splash value type") {
        samples().forEach { sample ->
            test("${sample.name} equals its copy and differs from a copy with a changed value") {
                sample.copy shouldBe sample.value
                sample.copy.hashCode() shouldBe sample.value.hashCode()
                sample.changed shouldNotBe sample.value
                sample.value shouldNotBe Any()
            }

            test("${sample.name} describes itself by its name and values") {
                sample.value.toString() shouldStartWith "${sample.name}("
            }
        }
    }

    context("default sizes") {
        test("recommends a size for the window of every splash") {
            val theme = Theme.light()

            SplitSplashStyles.Default.resolve(theme).size shouldBe DpSize(width = 880.dp, height = 520.dp)
            SplashWindowStyles.Default.resolve(theme).size shouldBe DpSize(width = 880.dp, height = 520.dp)
            MinimalSplashStyles.Default.resolve(theme).size shouldBe DpSize(width = 320.dp, height = 320.dp)
        }
    }
})
