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
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

@OptIn(ExperimentalTestApi::class)
class WireframeArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultWireframeArtStyle(theme)
    val base = WireframeArt().print()

    artChecks(
        ArtCase(
            name = "wireframe",
            art = WireframeArt(),
            changed = WireframeArt(style = style.copy(solid = WireframeSolid.Dodecahedron)),
            recolored = WireframeArt(
                style = style.copy(line = Color.Red, accent = Color.Blue, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "WireframeArt",
            value = WireframeArt(style),
            copy = WireframeArt(style).copy(),
            changed = WireframeArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "WireframeArtStyle", value = style, copy = style.copy(), changed = style.copy(tilt = 0f)),
    )

    test("draws every solid") {
        val prints = WireframeSolid.entries.map { solid -> WireframeArt(style.copy(solid = solid)).print() }

        prints.toSet() shouldHaveSize WireframeSolid.entries.size
    }

    test("leaves out the guide and the dots") {
        WireframeArt(style.copy(guideAlpha = 0f)).print() shouldNotBe base
        WireframeArt(style.copy(dot = 0.dp)).print() shouldNotBe base
        val art = WireframeArt(style.copy(dotQuality = QualityLevel.High))
        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("keeps its eye outside the solid") {
        WireframeArt(style.copy(perspective = 0f)).print() shouldNotBe base
    }

    test("is static only when it never turns nor swings") {
        val still = style.copy(period = Duration.INFINITE, wobblePeriod = Duration.INFINITE)

        WireframeArt(still).isStatic shouldBe true
        WireframeArt(still.copy(wobblePeriod = style.wobblePeriod)).isStatic shouldBe false
        WireframeArt(style).isStatic shouldBe false
        WireframeArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(WireframeArtStyleKey) { _, _ ->
                WireframeArtStyles { current -> defaultWireframeArtStyle(current).copy(solid = WireframeSolid.Cube) }
            },
        )

        WireframeArt().print(Render(theme = custom)) shouldNotBe base
        WireframeArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: WireframeArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = wireframeArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "WireframeArt", style = style) { variant -> WireframeArt(style = variant) }
})
