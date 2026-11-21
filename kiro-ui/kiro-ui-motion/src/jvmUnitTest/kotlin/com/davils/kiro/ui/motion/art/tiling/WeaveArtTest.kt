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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val STEEP: Float = 90f

@OptIn(ExperimentalTestApi::class)
class WeaveArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultWeaveArtStyle(theme)
    val base = WeaveArt().print()

    artChecks(
        ArtCase(
            name = "weave",
            art = WeaveArt(),
            changed = WeaveArt(style = style.copy(pattern = WeavePattern.Kagome)),
            recolored = WeaveArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    line = Color.Green,
                    accent = Color.Yellow,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(name = "WeaveArt", value = WeaveArt(style), copy = WeaveArt(style).copy(), changed = WeaveArt()),
    )
    valueChecks(
        ValueCase(name = "WeaveArtStyle", value = style, copy = style.copy(), changed = style.copy(arcSteps = 1)),
    )

    test("weaves every pattern") {
        val prints = WeavePattern.entries.map { pattern -> WeaveArt(style.copy(pattern = pattern)).print() }

        prints.toSet() shouldHaveSize WeavePattern.entries.size
    }

    test("tints every cell or none") {
        WeaveArt(style.copy(panelShare = 1f)).print() shouldNotBe base
        WeaveArt(style.copy(panelShare = 0f)).print() shouldNotBe base
        WeaveArt(style.copy(panelAlpha = 0f)).print() shouldNotBe base
    }

    test("leaves out the band of light without a width and its glow below its quality") {
        WeaveArt(style.copy(band = 0.dp)).print() shouldNotBe base
        WeaveArt(style.copy(glowAlpha = 0f)).print() shouldNotBe base
        val art = WeaveArt(style.copy(glowQuality = QualityLevel.High))
        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("sends the band of light another way") {
        WeaveArt(style.copy(angle = STEEP)).print() shouldNotBe base
    }

    test("is static only when its light never travels") {
        WeaveArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        WeaveArt(style).isStatic shouldBe false
        WeaveArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(WeaveArtStyleKey) { _, _ ->
                WeaveArtStyles { current -> defaultWeaveArtStyle(current).copy(pattern = WeavePattern.Shippo) }
            },
        )

        WeaveArt().print(Render(theme = custom)) shouldNotBe base
        WeaveArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: WeaveArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = weaveArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "WeaveArt", style = style) { variant -> WeaveArt(style = variant) }
})
