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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

@OptIn(ExperimentalTestApi::class)
class BokehArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultBokehArtStyle(theme)
    val base = BokehArt().print()

    artChecks(
        ArtCase(
            name = "bokeh",
            art = BokehArt(),
            changed = BokehArt(style = style.copy(count = 3, rimAlpha = 0f)),
            recolored = BokehArt(style = style.copy(colors = listOf(Color.Red, Color.Blue))),
        ),
    )

    valueChecks(
        ValueCase(
            name = "BokehArt",
            value = BokehArt(style),
            copy = BokehArt(style).copy(),
            changed = BokehArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "BokehArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(softness = 1f),
        ),
    )

    test("leaves out discs without a size") {
        BokehArt(style.copy(minRadius = 0.dp, maxRadius = 0.dp)).print() shouldNotBe base
    }

    test("is never static and draws nothing into an empty area") {
        BokehArt().isStatic shouldBe false
        BokehArt().toString() shouldBe "BokehArt(style=null)"
        BokehArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(BokehArtStyleKey) { _, _ ->
                BokehArtStyles { current ->
                    defaultBokehArtStyle(current).copy(count = 3, rimAlpha = 0f)
                }
            },
        )

        BokehArt().print(Render(theme = custom)) shouldNotBe base
        BokehArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: BokehArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = bokehArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "BokehArt", style = style) { variant -> BokehArt(style = variant) }
})
