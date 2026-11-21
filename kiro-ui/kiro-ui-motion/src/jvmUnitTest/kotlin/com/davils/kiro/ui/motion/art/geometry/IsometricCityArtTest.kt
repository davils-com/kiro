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

private const val CORNER: Float = 0f

@OptIn(ExperimentalTestApi::class)
class IsometricCityArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultIsometricCityArtStyle(theme)
    val base = IsometricCityArt().print()

    artChecks(
        ArtCase(
            name = "isometric-city",
            art = IsometricCityArt(),
            changed = IsometricCityArt(style = style.copy(originX = CORNER, originY = CORNER)),
            recolored = IsometricCityArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    shade = Color.Green,
                    base = Color.Gray,
                    detail = Color.Yellow,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "IsometricCityArt",
            value = IsometricCityArt(style),
            copy = IsometricCityArt(style).copy(),
            changed = IsometricCityArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "IsometricCityArtStyle", value = style, copy = style.copy(), changed = style.copy(inset = 0f)),
    )

    test("leaves every lot empty at full vacancy") {
        IsometricCityArt(style.copy(vacancy = 1f)).colors() shouldBe 1
    }

    test("hides its edges and windows") {
        val balanced = Render(quality = QualityLevel.Balanced)

        IsometricCityArt(style.copy(outlineAlpha = 0f, windowAlpha = 0f)).print() shouldNotBe base
        IsometricCityArt(style.copy(floor = 0.dp)).print() shouldNotBe base
        IsometricCityArt(style.copy(detailQuality = QualityLevel.High)).print(balanced) shouldNotBe
            IsometricCityArt(style).print(balanced)
    }

    test("falls back to the ink without colors and keeps one color") {
        IsometricCityArt(style.copy(colors = emptyList())).print() shouldNotBe base
        IsometricCityArt(style.copy(colors = listOf(Color.Red))).print() shouldNotBe base
    }

    test("is static only when its wave never moves") {
        IsometricCityArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        IsometricCityArt(style).isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(IsometricCityArtStyleKey) { _, _ ->
                IsometricCityArtStyles { current -> defaultIsometricCityArtStyle(current).copy(originX = CORNER) }
            },
        )

        IsometricCityArt().print(Render(theme = custom)) shouldNotBe base
        IsometricCityArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: IsometricCityArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = isometricCityArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "IsometricCityArt", style = style) { variant -> IsometricCityArt(style = variant) }
})
