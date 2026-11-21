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
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val MANY_BLIPS: Int = 40

@OptIn(ExperimentalTestApi::class)
class RadarArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultRadarArtStyle(theme)
    val base = RadarArt().print()
    val busy = style.copy(blips = MANY_BLIPS)
    val later = Render(time = 5.seconds)

    artChecks(
        ArtCase(
            name = "radar",
            art = RadarArt(),
            changed = RadarArt(style = style.copy(rings = 2, majorEvery = 0)),
            recolored = RadarArt(
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
        ValueCase(name = "RadarArt", value = RadarArt(style), copy = RadarArt(style).copy(), changed = RadarArt()),
    )
    valueChecks(
        ValueCase(name = "RadarArtStyle", value = style, copy = style.copy(), changed = style.copy(rings = 1)),
    )

    test("leaves out the wedge, the blips and their rings") {
        RadarArt(style.copy(wedge = 0f)).print() shouldNotBe base
        RadarArt(style.copy(wedgeAlpha = 0f)).print() shouldNotBe base
        RadarArt(busy.copy(fade = 0f)).print(later) shouldNotBe RadarArt(busy).print(later)
        RadarArt(busy.copy(haloAlpha = 0f)).print(later) shouldNotBe RadarArt(busy).print(later)
        val art = RadarArt(busy.copy(haloQuality = QualityLevel.High))
        art.print(Render(time = 5.seconds, quality = QualityLevel.Balanced)) shouldNotBe art.print(later)
    }

    test("finds blips in the last turn of the sweep and in this one") {
        RadarArt(busy).print(Render(time = 1.seconds)) shouldNotBe RadarArt(busy).print(later)
    }

    test("is static only when its sweep never turns") {
        RadarArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        RadarArt(style).isStatic shouldBe false
        RadarArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(RadarArtStyleKey) { _, _ ->
                RadarArtStyles { current -> defaultRadarArtStyle(current).copy(rings = 1) }
            },
        )

        RadarArt().print(Render(theme = custom)) shouldNotBe base
        RadarArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: RadarArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = radarArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "RadarArt", style = style) { variant -> RadarArt(style = variant) }
})
