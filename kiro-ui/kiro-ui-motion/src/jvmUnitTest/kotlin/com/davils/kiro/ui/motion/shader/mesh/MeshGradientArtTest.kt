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

package com.davils.kiro.ui.motion.shader.mesh

import androidx.compose.ui.geometry.Offset
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
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val TOLERANCE: Float = 1e-4f

@OptIn(ExperimentalTestApi::class)
class MeshGradientArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultMeshGradientArtStyle(theme)
    val canvas = style.copy(backend = ShaderBackend.Canvas)

    shaderContract(
        ShaderSubject(
            name = "mesh-gradient",
            art = MeshGradientArt(),
            fallback = MeshGradientArt(style = canvas),
            changed = MeshGradientArt(style = style.copy(anchors = 2, radius = 0.2f)),
            recolored = MeshGradientArt(
                style = style.copy(colors = listOf(Color.Red, Color.Blue), background = Color.Black),
            ),
        ),
    )

    test("clamps the number of anchors and shows each count") {
        val prints = (1..MAX_ANCHORS).map { count -> MeshGradientArt(style.copy(anchors = count)).print() }

        prints.toSet().size shouldBe MAX_ANCHORS
        MeshGradientArt(style.copy(anchors = MAX_ANCHORS + 2)).print() shouldBe prints.last()
        MeshGradientArt(style.copy(anchors = 0)).print() shouldBe prints.first()
        val crowded = MeshGradientArt(canvas.copy(anchors = MAX_ANCHORS + 2, softness = 0f))
        crowded.print() shouldNotBe MeshGradientArt(canvas).print()
    }

    test("places its anchors on loops that return after a period") {
        val anchors = MeshAnchors()
        anchors.place(count = 3, phase = 0.0, seed = 1, style = style, pointer = null)
        val start = anchors.x.copyOf()
        anchors.place(count = 3, phase = 1.0, seed = 1, style = style, pointer = null)

        anchors.x.zip(start).forEach { (now, then) -> now shouldBe (then plusOrMinus TOLERANCE) }
        anchors.place(count = 3, phase = 1.0, seed = 1, style = style, pointer = Offset(x = 0.5f, y = 0.5f))
        anchors.x.toList() shouldNotBe start.toList()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(MeshGradientArtStyleKey) { _, _ ->
                MeshGradientArtStyles { current -> defaultMeshGradientArtStyle(current).copy(anchors = 2) }
            },
        )

        MeshGradientArt().print(Render(theme = custom)) shouldNotBe MeshGradientArt().print()
    }

    test("resolves its style in a composition") {
        var resolved: MeshGradientArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = meshGradientArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = MeshGradientArt(style = style)

        art shouldBe MeshGradientArt(style = style)
        art.hashCode() shouldBe MeshGradientArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe MeshGradientArt()
        art.isStatic shouldBe false
        art.toString() shouldStartWith "MeshGradientArt(style=MeshGradientArtStyle(colors=null, background=null"
        MeshGradientArt().toString() shouldBe "MeshGradientArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(anchors = 2) shouldNotBe style
        style.copy(colors = listOf(Color.Red)).toString() shouldStartWith "MeshGradientArtStyle(colors=[Color("
    }

    extremeChecks(name = "MeshGradientArt", style = style) { variant -> MeshGradientArt(style = variant) }
})
