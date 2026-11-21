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
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val FEW_CELLS: Int = 50
private const val TWO_LEVELS: Int = 2

@OptIn(ExperimentalTestApi::class)
class FlickeringGridArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultFlickeringGridArtStyle(theme)
    val base = FlickeringGridArt().print()

    artChecks(
        ArtCase(
            name = "flickering-grid",
            art = FlickeringGridArt(),
            changed = FlickeringGridArt(style = style.copy(levels = TWO_LEVELS, structure = 0f)),
            recolored = FlickeringGridArt(
                style = style.copy(color = Color.Red, accent = Color.Blue, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "FlickeringGridArt",
            value = FlickeringGridArt(style),
            copy = FlickeringGridArt(style).copy(),
            changed = FlickeringGridArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "FlickeringGridArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(levels = 1),
        ),
    )

    test("makes the squares coarser when there are more than it may draw") {
        FlickeringGridArt(style.copy(maxCells = FEW_CELLS)).print() shouldNotBe base
    }

    test("draws a single level, only accents or only clouds") {
        FlickeringGridArt(style.copy(levels = 1)).print() shouldNotBe base
        FlickeringGridArt(style.copy(accentShare = 1f)).print() shouldNotBe base
        FlickeringGridArt(style.copy(structure = 1f)).print() shouldNotBe base
    }

    test("draws nothing into an empty area") {
        FlickeringGridArt().drawEmpty()
    }

    test("is static only when nothing ever flickers or drifts") {
        val still = style.copy(period = Duration.INFINITE, drift = Duration.INFINITE)

        FlickeringGridArt(still).isStatic shouldBe true
        FlickeringGridArt(still.copy(drift = style.drift)).isStatic shouldBe false
        FlickeringGridArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(FlickeringGridArtStyleKey) { _, _ ->
                FlickeringGridArtStyles { current -> defaultFlickeringGridArtStyle(current).copy(levels = TWO_LEVELS) }
            },
        )

        FlickeringGridArt().print(Render(theme = custom)) shouldNotBe base
        FlickeringGridArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: FlickeringGridArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = flickeringGridArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "FlickeringGridArt", style = style) { variant -> FlickeringGridArt(style = variant) }
})
