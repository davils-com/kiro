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

private const val FAR_AWAY: Float = 20f
private const val SQUARES: Int = 4

@OptIn(ExperimentalTestApi::class)
class PolygonsArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultPolygonsArtStyle(theme)
    val base = PolygonsArt().print()

    artChecks(
        ArtCase(
            name = "polygons",
            art = PolygonsArt(),
            changed = PolygonsArt(style = style.copy(minSides = SQUARES, isAlternating = false)),
            recolored = PolygonsArt(
                style = style.copy(line = Color.Red, accent = Color.Blue, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "PolygonsArt",
            value = PolygonsArt(style),
            copy = PolygonsArt(style).copy(),
            changed = PolygonsArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "PolygonsArtStyle", value = style, copy = style.copy(), changed = style.copy(rings = 1)),
    )

    test("leaves out the guides, the trace and the dots") {
        PolygonsArt(style.copy(guideAlpha = 0f)).print() shouldNotBe base
        PolygonsArt(style.copy(traceAlpha = 0f)).print() shouldNotBe base
        PolygonsArt(style.copy(dot = 0.dp)).print() shouldNotBe base
        val art = PolygonsArt(style.copy(dotQuality = QualityLevel.High))
        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("traces a whole polygon and fades it") {
        val traced = PolygonsArt(style.copy(drawShare = 0f))

        traced.print() shouldNotBe base
        traced.print(Render(time = 1.seconds)) shouldNotBe traced.print()
    }

    test("skips polygons outside the area and polygons without a radius") {
        val outside = PolygonsArt(style.copy(centerX = FAR_AWAY, centerY = FAR_AWAY, traceAlpha = 0f))
        val empty = PolygonsArt(style.copy(rings = 0))

        outside.colors() shouldBe 1
        empty.colors() shouldBe 1
        PolygonsArt(style.copy(firstRadius = 0.dp, ringSpacing = 0.dp)).colors() shouldBe 1
    }

    test("is static only when nothing ever turns") {
        val still = style.copy(period = Duration.INFINITE, tracePeriod = Duration.INFINITE)

        PolygonsArt(still).isStatic shouldBe true
        PolygonsArt(still.copy(tracePeriod = style.tracePeriod)).isStatic shouldBe false
        PolygonsArt(style).isStatic shouldBe false
        PolygonsArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(PolygonsArtStyleKey) { _, _ ->
                PolygonsArtStyles { current -> defaultPolygonsArtStyle(current).copy(rings = 1) }
            },
        )

        PolygonsArt().print(Render(theme = custom)) shouldNotBe base
        PolygonsArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: PolygonsArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = polygonsArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "PolygonsArt", style = style) { variant -> PolygonsArt(style = variant) }
})
