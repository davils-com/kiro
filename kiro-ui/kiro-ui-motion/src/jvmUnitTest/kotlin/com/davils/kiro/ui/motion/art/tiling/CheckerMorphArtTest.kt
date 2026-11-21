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
import com.davils.kiro.ui.motion.fixture.colors
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val WIDE_BAND: Int = 1

@OptIn(ExperimentalTestApi::class)
class CheckerMorphArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultCheckerMorphArtStyle(theme)
    val base = CheckerMorphArt().print()

    artChecks(
        ArtCase(
            name = "checker-morph",
            art = CheckerMorphArt(),
            changed = CheckerMorphArt(style = style.copy(band = WIDE_BAND, circle = 1f)),
            recolored = CheckerMorphArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    line = Color.Green,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "CheckerMorphArt",
            value = CheckerMorphArt(style),
            copy = CheckerMorphArt(style).copy(),
            changed = CheckerMorphArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "CheckerMorphArtStyle", value = style, copy = style.copy(), changed = style.copy(band = 0)),
    )

    test("keeps its squares square without a morph") {
        CheckerMorphArt(style.copy(morph = 0f)).print() shouldNotBe base
    }

    test("leaves out the grid and the outlines") {
        CheckerMorphArt(style.copy(gridAlpha = 0f)).print() shouldNotBe base
        CheckerMorphArt(style.copy(outlineAlpha = 0f)).print() shouldNotBe base
        val art = CheckerMorphArt(style.copy(outlineQuality = QualityLevel.High))
        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("draws nothing but the background without a square") {
        CheckerMorphArt(style.copy(cell = 0.dp)).colors() shouldBe 1
    }

    test("is static only when its waves never come") {
        CheckerMorphArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        CheckerMorphArt(style).isStatic shouldBe false
        CheckerMorphArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(CheckerMorphArtStyleKey) { _, _ ->
                CheckerMorphArtStyles { current -> defaultCheckerMorphArtStyle(current).copy(morph = 0f) }
            },
        )

        CheckerMorphArt().print(Render(theme = custom)) shouldNotBe base
        CheckerMorphArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: CheckerMorphArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = checkerMorphArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "CheckerMorphArt", style = style) { variant -> CheckerMorphArt(style = variant) }
})
