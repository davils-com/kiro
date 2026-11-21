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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val FAR_AWAY: Float = 20f
private const val MORE_RINGS: Int = 2

@OptIn(ExperimentalTestApi::class)
class OrbitArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultOrbitArtStyle(theme)
    val base = OrbitArt().print()

    artChecks(
        ArtCase(
            name = "orbit",
            art = OrbitArt(),
            changed = OrbitArt(style = style.copy(rings = MORE_RINGS, isAlternating = false)),
            recolored = OrbitArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    glow = Color.Green,
                    ring = Color.Yellow,
                    highlight = Color.Cyan,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(name = "OrbitArt", value = OrbitArt(style), copy = OrbitArt(style).copy(), changed = OrbitArt()),
    )
    valueChecks(
        ValueCase(name = "OrbitArtStyle", value = style, copy = style.copy(), changed = style.copy(rings = 1)),
    )

    test("draws moons on every planet or none") {
        OrbitArt(style.copy(moonChance = 1f)).print() shouldNotBe base
        OrbitArt(style.copy(moonChance = 0f)).print() shouldNotBe base
    }

    test("leaves out the glow, the trails, the shine and the planets") {
        val bare = style.copy(glowRadius = 0.dp, trail = 0f, shine = 0f, minPlanets = 0, maxPlanets = 0)

        OrbitArt(bare).print() shouldNotBe base
        OrbitArt(style.copy(glowAlpha = 0f, trailAlpha = 0f, shineSize = 0f)).print() shouldNotBe base
    }

    test("skips orbits outside the area and orbits without a radius") {
        val outside = OrbitArt(style.copy(centerX = FAR_AWAY, centerY = FAR_AWAY, glowRadius = 0.dp))
        val collapsed = OrbitArt(style.copy(firstRadius = 0.dp, ringSpacing = 0.dp))

        outside.colors() shouldBe 1
        collapsed.colors() shouldBeGreaterThan 1
    }

    test("is static only when nothing ever turns") {
        val still = style.copy(period = Duration.INFINITE, drift = Duration.INFINITE, moonPeriod = Duration.INFINITE)

        OrbitArt(still).isStatic shouldBe true
        OrbitArt(still.copy(drift = style.drift)).isStatic shouldBe false
        OrbitArt(style).isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(OrbitArtStyleKey) { _, _ ->
                OrbitArtStyles { current -> defaultOrbitArtStyle(current).copy(rings = MORE_RINGS) }
            },
        )

        OrbitArt().print(Render(theme = custom)) shouldNotBe base
        OrbitArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: OrbitArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = orbitArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "OrbitArt", style = style) { variant -> OrbitArt(style = variant) }
})
