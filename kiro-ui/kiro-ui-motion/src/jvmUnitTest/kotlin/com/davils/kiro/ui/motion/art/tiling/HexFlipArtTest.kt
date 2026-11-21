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
import kotlin.time.Duration.Companion.seconds

private const val LARGE_STEPS: Int = 10

@OptIn(ExperimentalTestApi::class)
class HexFlipArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultHexFlipArtStyle(theme)
    val base = HexFlipArt().print()
    val turning = Render(time = 2.seconds)

    artChecks(
        ArtCase(
            name = "hex-flip",
            art = HexFlipArt(),
            changed = HexFlipArt(style = style.copy(radius = style.radius * LARGE_STEPS, gap = 0.dp)),
            recolored = HexFlipArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    line = Color.Green,
                    shade = Color.Yellow,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "HexFlipArt",
            value = HexFlipArt(style),
            copy = HexFlipArt(style).copy(),
            changed = HexFlipArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "HexFlipArtStyle", value = style, copy = style.copy(), changed = style.copy(shades = 1)),
    )

    test("shows every card on its back or none") {
        HexFlipArt(style.copy(share = 1f)).print() shouldNotBe base
        HexFlipArt(style.copy(share = 0f)).print() shouldNotBe base
    }

    test("leaves out the outlines, the shading and the inner outlines") {
        val bare = style.copy(outlineAlpha = 0f, shading = 0f, detail = 0f)

        HexFlipArt(bare).print(turning) shouldNotBe HexFlipArt(style).print(turning)
    }

    test("leaves out the inner outlines below their quality") {
        val art = HexFlipArt(style.copy(detailQuality = QualityLevel.High))

        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("flips the cards around another origin") {
        HexFlipArt(style.copy(centerX = 0f, centerY = 0f)).print(turning) shouldNotBe HexFlipArt().print(turning)
    }

    test("draws nothing but the background without a radius") {
        HexFlipArt(style.copy(radius = 0.dp)).colors() shouldBe 1
    }

    test("is static only when its ripples never come") {
        HexFlipArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        HexFlipArt(style).isStatic shouldBe false
        HexFlipArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(HexFlipArtStyleKey) { _, _ ->
                HexFlipArtStyles { current -> defaultHexFlipArtStyle(current).copy(share = 1f) }
            },
        )

        HexFlipArt().print(Render(theme = custom)) shouldNotBe base
        HexFlipArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: HexFlipArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = hexFlipArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "HexFlipArt", style = style) { variant -> HexFlipArt(style = variant) }
})
