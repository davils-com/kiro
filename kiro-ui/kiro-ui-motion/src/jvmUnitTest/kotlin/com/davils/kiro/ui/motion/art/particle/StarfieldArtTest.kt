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

package com.davils.kiro.ui.motion.art.particle

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

@OptIn(ExperimentalTestApi::class)
class StarfieldArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultStarfieldArtStyle(theme)
    val base = StarfieldArt().print()

    artChecks(
        ArtCase(
            name = "starfield",
            art = StarfieldArt(),
            changed = StarfieldArt(style = style.copy(count = 40, centerX = 0.2f)),
            recolored = StarfieldArt(style = style.copy(colors = listOf(Color.Red, Color.Blue))),
        ),
    )

    valueChecks(
        ValueCase(
            name = "StarfieldArt",
            value = StarfieldArt(style),
            copy = StarfieldArt(style).copy(),
            changed = StarfieldArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "StarfieldArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(streak = 0f),
        ),
    )

    test("fades its stars in at once without a fade") {
        StarfieldArt(style.copy(fadeIn = 0f, nearest = 0f)).print() shouldNotBe base
    }

    test("is never static and draws nothing into an empty area") {
        StarfieldArt().isStatic shouldBe false
        StarfieldArt().toString() shouldBe "StarfieldArt(style=null)"
        StarfieldArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(StarfieldArtStyleKey) { _, _ ->
                StarfieldArtStyles { current ->
                    defaultStarfieldArtStyle(current).copy(count = 40, centerX = 0.2f)
                }
            },
        )

        StarfieldArt().print(Render(theme = custom)) shouldNotBe base
        StarfieldArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: StarfieldArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = starfieldArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "StarfieldArt", style = style) { variant -> StarfieldArt(style = variant) }
})
