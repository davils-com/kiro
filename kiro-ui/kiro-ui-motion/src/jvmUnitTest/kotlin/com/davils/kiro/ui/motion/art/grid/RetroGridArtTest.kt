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

package com.davils.kiro.ui.motion.art.grid

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
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val LOW_HORIZON: Float = 0.7f
private const val WIDE: Float = 120f

@OptIn(ExperimentalTestApi::class)
class RetroGridArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultRetroGridArtStyle(theme)
    val base = RetroGridArt().print()

    artChecks(
        ArtCase(
            name = "retro-grid",
            art = RetroGridArt(),
            changed = RetroGridArt(style = style.copy(horizon = LOW_HORIZON, cell = WIDE.dp)),
            recolored = RetroGridArt(
                style = style.copy(
                    line = Color.Red,
                    colors = listOf(Color.Blue),
                    glow = Color.Green,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "RetroGridArt",
            value = RetroGridArt(style),
            copy = RetroGridArt(style).copy(),
            changed = RetroGridArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "RetroGridArtStyle", value = style, copy = style.copy(), changed = style.copy(maxRows = 1)),
    )

    test("leaves out the glow, the pulses and the fade") {
        RetroGridArt(style.copy(glowAlpha = 0f)).print() shouldNotBe base
        RetroGridArt(style.copy(glowHeight = 0f)).print() shouldNotBe base
        RetroGridArt(style.copy(pulseAlpha = 0f)).print() shouldNotBe base
        RetroGridArt(style.copy(fade = 0f)).print() shouldNotBe base
    }

    test("draws no floor below a horizon at the bottom edge, nor lanes without a width") {
        val sky = RetroGridArt(style.copy(horizon = 1f, glowAlpha = 0f))

        sky.colors() shouldBe 1
        RetroGridArt(style.copy(cell = 0.dp)).colors() shouldBeGreaterThan 1
    }

    test("draws no rows without depth") {
        RetroGridArt(style.copy(depth = 0f)).print() shouldNotBe base
    }

    test("draws nothing into an empty area") {
        RetroGridArt().drawEmpty()
    }

    test("is static only when neither the floor nor the pulses move") {
        val still = style.copy(period = Duration.INFINITE, pulsePeriod = Duration.INFINITE)

        RetroGridArt(still).isStatic shouldBe true
        RetroGridArt(still.copy(period = style.period)).isStatic shouldBe false
        RetroGridArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(RetroGridArtStyleKey) { _, _ ->
                RetroGridArtStyles { current -> defaultRetroGridArtStyle(current).copy(horizon = LOW_HORIZON) }
            },
        )

        RetroGridArt().print(Render(theme = custom)) shouldNotBe base
        RetroGridArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: RetroGridArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = retroGridArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "RetroGridArt", style = style) { variant -> RetroGridArt(style = variant) }
})
