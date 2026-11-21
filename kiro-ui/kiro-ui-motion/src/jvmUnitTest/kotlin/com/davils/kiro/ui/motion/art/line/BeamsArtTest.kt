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

package com.davils.kiro.ui.motion.art.line

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
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val FEW_BEAMS: Int = 3
private const val BOWED: Float = 0.5f

@OptIn(ExperimentalTestApi::class)
class BeamsArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultBeamsArtStyle(theme)
    val base = BeamsArt().print()

    artChecks(
        ArtCase(
            name = "beams",
            art = BeamsArt(),
            changed = BeamsArt(style = style.copy(beams = FEW_BEAMS, bend = BOWED)),
            recolored = BeamsArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    guide = Color.Green,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(name = "BeamsArt", value = BeamsArt(style), copy = BeamsArt(style).copy(), changed = BeamsArt()),
    )
    valueChecks(
        ValueCase(name = "BeamsArtStyle", value = style, copy = style.copy(), changed = style.copy(beams = 1)),
    )

    test("leaves out the curves, the glow and the beams") {
        BeamsArt(style.copy(guideAlpha = 0f)).print() shouldNotBe base
        BeamsArt(style.copy(glowAlpha = 0f)).print() shouldNotBe base
        BeamsArt(style.copy(segment = 0f)).print() shouldNotBe base
        BeamsArt(style.copy(beams = 0)).colors() shouldBe 1
    }

    test("draws nothing into an empty area") {
        BeamsArt().drawEmpty()
    }

    test("is static only when the beams never travel") {
        BeamsArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        BeamsArt(style).isStatic shouldBe false
        BeamsArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(BeamsArtStyleKey) { _, _ ->
                BeamsArtStyles { current -> defaultBeamsArtStyle(current).copy(beams = FEW_BEAMS) }
            },
        )

        BeamsArt().print(Render(theme = custom)) shouldNotBe base
        BeamsArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: BeamsArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = beamsArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "BeamsArt", style = style) { variant -> BeamsArt(style = variant) }
})
