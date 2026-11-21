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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val MORE_CURVES: Int = 4

@OptIn(ExperimentalTestApi::class)
class LissajousArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultLissajousArtStyle(theme)
    val base = LissajousArt().print()

    artChecks(
        ArtCase(
            name = "lissajous",
            art = LissajousArt(),
            changed = LissajousArt(style = style.copy(curves = MORE_CURVES, damping = 0f)),
            recolored = LissajousArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    line = Color.Green,
                    pen = Color.Yellow,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "LissajousArt",
            value = LissajousArt(style),
            copy = LissajousArt(style).copy(),
            changed = LissajousArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "LissajousArtStyle", value = style, copy = style.copy(), changed = style.copy(curves = 1)),
    )

    test("draws one frequency for both swings and whole curves") {
        LissajousArt(style.copy(minFrequency = 1, maxFrequency = 1)).print() shouldNotBe base
        LissajousArt(style.copy(drawShare = 0f)).print() shouldNotBe base
    }

    test("leaves out the guides and the curves") {
        LissajousArt(style.copy(guideAlpha = 0f)).print() shouldNotBe base
        LissajousArt(style.copy(curves = 0, guideAlpha = 0f)).colors() shouldBe 1
    }

    test("is static only when it never draws nor drifts") {
        val still = style.copy(drawPeriod = Duration.INFINITE, driftPeriod = Duration.INFINITE)

        LissajousArt(still).isStatic shouldBe true
        LissajousArt(still.copy(driftPeriod = style.driftPeriod)).isStatic shouldBe false
        LissajousArt(style).isStatic shouldBe false
        LissajousArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(LissajousArtStyleKey) { _, _ ->
                LissajousArtStyles { current -> defaultLissajousArtStyle(current).copy(curves = 1) }
            },
        )

        LissajousArt().print(Render(theme = custom)) shouldNotBe base
        LissajousArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: LissajousArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = lissajousArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "LissajousArt", style = style) { variant -> LissajousArt(style = variant) }
})
