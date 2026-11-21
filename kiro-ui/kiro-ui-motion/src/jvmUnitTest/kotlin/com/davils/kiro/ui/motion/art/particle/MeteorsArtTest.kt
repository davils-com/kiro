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
class MeteorsArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultMeteorsArtStyle(theme)
    val base = MeteorsArt().print()

    artChecks(
        ArtCase(
            name = "meteors",
            art = MeteorsArt(),
            changed = MeteorsArt(style = style.copy(stars = 0, angle = 140f)),
            recolored = MeteorsArt(style = style.copy(colors = listOf(Color.Red, Color.Blue), star = Color.Green)),
        ),
    )

    valueChecks(
        ValueCase(
            name = "MeteorsArt",
            value = MeteorsArt(style),
            copy = MeteorsArt(style).copy(),
            changed = MeteorsArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "MeteorsArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(duty = 1f),
        ),
    )

    test("keeps its meteors in flight all the time or never") {
        MeteorsArt(style.copy(duty = 1f)).print() shouldNotBe base
        MeteorsArt(style.copy(duty = 0f)).print() shouldNotBe base
    }

    test("is never static and draws nothing into an empty area") {
        MeteorsArt().isStatic shouldBe false
        MeteorsArt().toString() shouldBe "MeteorsArt(style=null)"
        MeteorsArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(MeteorsArtStyleKey) { _, _ ->
                MeteorsArtStyles { current ->
                    defaultMeteorsArtStyle(current).copy(stars = 0, angle = 140f)
                }
            },
        )

        MeteorsArt().print(Render(theme = custom)) shouldNotBe base
        MeteorsArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: MeteorsArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = meteorsArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "MeteorsArt", style = style) { variant -> MeteorsArt(style = variant) }
})
