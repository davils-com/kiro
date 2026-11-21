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

private const val CORNER: Float = 20f
private const val MORE_RINGS: Int = 9

@OptIn(ExperimentalTestApi::class)
class RippleArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultRippleArtStyle(theme)
    val base = RippleArt().print()

    artChecks(
        ArtCase(
            name = "ripple",
            art = RippleArt(),
            changed = RippleArt(style = style.copy(rings = MORE_RINGS, originX = 0f)),
            recolored = RippleArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    guide = Color.Green,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(name = "RippleArt", value = RippleArt(style), copy = RippleArt(style).copy(), changed = RippleArt()),
    )
    valueChecks(
        ValueCase(name = "RippleArtStyle", value = style, copy = style.copy(), changed = style.copy(rings = 1)),
    )

    test("moves the rings to the pointer as far as asked") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = CORNER, y = CORNER))
        val following = RippleArt().print(Render(inputs = inputs))

        following shouldNotBe base
        RippleArt(style.copy(pointerFollow = 0f)).print(Render(inputs = inputs)) shouldBe base
    }

    test("leaves out the discs, the still circles and rings without a radius") {
        RippleArt(style.copy(fillAlpha = 0f)).print() shouldNotBe base
        RippleArt(style.copy(guideAlpha = 0f)).print() shouldNotBe base
        RippleArt(style.copy(guideSpacing = 0.dp)).print() shouldNotBe base
        RippleArt(style.copy(easing = Easing { 0f })).print() shouldBe RippleArt(style.copy(rings = 0)).print()
    }

    test("draws nothing into an empty area") {
        RippleArt().drawEmpty()
    }

    test("is static only when its rings never widen") {
        RippleArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        RippleArt(style).isStatic shouldBe false
        RippleArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(RippleArtStyleKey) { _, _ ->
                RippleArtStyles { current -> defaultRippleArtStyle(current).copy(rings = MORE_RINGS) }
            },
        )

        RippleArt().print(Render(theme = custom)) shouldNotBe base
        RippleArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: RippleArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = rippleArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "RippleArt", style = style) { variant -> RippleArt(style = variant) }
})
