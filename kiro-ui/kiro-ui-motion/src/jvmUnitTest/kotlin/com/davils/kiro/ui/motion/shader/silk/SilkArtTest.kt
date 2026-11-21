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

package com.davils.kiro.ui.motion.shader.silk

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.shader.ShaderBackend
import com.davils.kiro.ui.motion.shader.ShaderSubject
import com.davils.kiro.ui.motion.shader.shaderContract
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private fun SilkArtStyle.recolored(): SilkArtStyle =
    copy(background = Color.Black, color = Color.Red, shadow = Color.Blue, sheen = Color.Green)

@OptIn(ExperimentalTestApi::class)
class SilkArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultSilkArtStyle(theme)
    val canvas = style.copy(backend = ShaderBackend.Canvas)

    shaderContract(
        ShaderSubject(
            name = "silk",
            art = SilkArt(),
            fallback = SilkArt(style = canvas),
            changed = SilkArt(style = style.copy(scale = 0.05f, warp = 0f)),
            recolored = SilkArt(style = style.recolored()),
        ),
    )

    test("colors its canvas fallback") {
        SilkArt(canvas.recolored()).print() shouldNotBe SilkArt(canvas).print()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(SilkArtStyleKey) { _, _ ->
                SilkArtStyles { current -> defaultSilkArtStyle(current).copy(angle = 60f) }
            },
        )

        SilkArt().print(Render(theme = custom)) shouldNotBe SilkArt().print()
    }

    test("resolves its style in a composition") {
        var resolved: SilkArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = silkArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = SilkArt(style = style)

        art shouldBe SilkArt(style = style)
        art.hashCode() shouldBe SilkArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe SilkArt()
        art.isStatic shouldBe false
        art.toString() shouldStartWith "SilkArt(style=SilkArtStyle(background=null"
        SilkArt().toString() shouldBe "SilkArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(warp = 0f) shouldNotBe style
        style.copy(color = Color.Red).toString() shouldStartWith "SilkArtStyle(background=null, color=Color("
    }

    extremeChecks(name = "SilkArt", style = style) { variant -> SilkArt(style = variant) }
})
