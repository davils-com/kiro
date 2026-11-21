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
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val TWO_CURVES: Int = 2

@OptIn(ExperimentalTestApi::class)
class SpirographArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultSpirographArtStyle(theme)
    val base = SpirographArt().print()

    artChecks(
        ArtCase(
            name = "spirograph",
            art = SpirographArt(),
            changed = SpirographArt(style = style.copy(curves = TWO_CURVES, echoes = 0)),
            recolored = SpirographArt(
                style.copy(colors = listOf(Color.Red, Color.Blue), head = Color.Green, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "SpirographArt",
            value = SpirographArt(style),
            copy = SpirographArt(style).copy(),
            changed = SpirographArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "SpirographArtStyle", value = style, copy = style.copy(), changed = style.copy(curves = 1)),
    )

    test("draws one curve, no curve and curves that roll outside") {
        SpirographArt(style.copy(curves = 1)).colors() shouldBeGreaterThan 2
        SpirographArt(style.copy(curves = 0)).colors() shouldBe 1
        SpirographArt(style.copy(epicycles = 1f)).print() shouldNotBe base
    }

    test("fades a finished curve back and draws at once without a drawing share") {
        val single = SpirographArt(style.copy(curves = 1))
        val late = Render(time = style.drawPeriod * style.drawShare.toDouble() + 1.seconds)

        single.print(late) shouldNotBe single.print()
        SpirographArt(style.copy(drawShare = 0f)).print() shouldNotBe base
    }

    test("is static only when it neither draws nor turns") {
        val still = style.copy(drawPeriod = Duration.INFINITE, rotationPeriod = Duration.INFINITE)

        SpirographArt(still).isStatic shouldBe true
        SpirographArt(still.copy(drawPeriod = style.drawPeriod)).isStatic shouldBe false
        SpirographArt(style).isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(SpirographArtStyleKey) { _, _ ->
                SpirographArtStyles { current -> defaultSpirographArtStyle(current).copy(curves = TWO_CURVES) }
            },
        )

        SpirographArt().print(Render(theme = custom)) shouldNotBe base
        SpirographArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: SpirographArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = spirographArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "SpirographArt", style = style) { variant -> SpirographArt(style = variant) }
})
