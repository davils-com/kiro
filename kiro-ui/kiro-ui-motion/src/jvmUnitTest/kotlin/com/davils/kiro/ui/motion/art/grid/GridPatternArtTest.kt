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

import androidx.compose.animation.core.Easing
import androidx.compose.ui.geometry.Offset
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
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.input.ArtInputs
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val NEAR: Float = 60f
private const val WIDE: Float = 40f
private const val MORE: Int = 20

@OptIn(ExperimentalTestApi::class)
class GridPatternArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultGridPatternArtStyle(theme)
    val base = GridPatternArt().print()

    artChecks(
        ArtCase(
            name = "grid-pattern",
            art = GridPatternArt(),
            changed = GridPatternArt(style = style.copy(cell = WIDE.dp, highlights = MORE)),
            recolored = GridPatternArt(
                style = style.copy(colors = listOf(Color.Red), line = Color.Blue, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "GridPatternArt",
            value = GridPatternArt(style),
            copy = GridPatternArt(style).copy(),
            changed = GridPatternArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "GridPatternArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(highlights = 1),
        ),
    )

    test("draws dots and crosses where the cells meet") {
        val dots = GridPatternArt(style.copy(kind = GridPatternKind.Dots)).print()
        val crosses = GridPatternArt(style.copy(kind = GridPatternKind.Crosses)).print()

        dots shouldNotBe base
        crosses shouldNotBe base
        crosses shouldNotBe dots
        GridPatternArt(style.copy(kind = GridPatternKind.Dots, majorEvery = 0)).print() shouldNotBe dots
        GridPatternArt(style.copy(majorEvery = 0)).print() shouldNotBe base
    }

    test("lights the cell under the pointer, with or without an outline") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = NEAR, y = NEAR))
        val lit = GridPatternArt().print(Render(inputs = inputs))
        val plain = GridPatternArt(style.copy(pointerOutlineAlpha = 0f)).print(Render(inputs = inputs))

        lit shouldNotBe base
        plain shouldNotBe lit
    }

    test("leaves out cells whose inset leaves no room") {
        val still = Easing { 0f }
        val empty = style.copy(inset = WIDE.dp, pointerOutlineAlpha = 0f)
        val inputs = ArtInputs.None.copy(pointer = Offset(x = NEAR, y = NEAR))

        val bare = GridPatternArt(style.copy(highlights = 0, inset = WIDE.dp))

        GridPatternArt(empty).print(Render(inputs = inputs)) shouldBe bare.print()
        GridPatternArt(style.copy(easing = still)).print() shouldBe GridPatternArt(style.copy(highlights = 0)).print()
    }

    test("draws nothing into an empty area") {
        GridPatternArt().drawEmpty()
    }

    test("is static only when its cells never light up") {
        GridPatternArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        GridPatternArt(style).isStatic shouldBe false
        GridPatternArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(GridPatternArtStyleKey) { _, _ ->
                GridPatternArtStyles { current ->
                    defaultGridPatternArtStyle(current).copy(kind = GridPatternKind.Dots)
                }
            },
        )

        GridPatternArt().print(Render(theme = custom)) shouldNotBe base
        GridPatternArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: GridPatternArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = gridPatternArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "GridPatternArt", style = style) { variant -> GridPatternArt(style = variant) }
})
