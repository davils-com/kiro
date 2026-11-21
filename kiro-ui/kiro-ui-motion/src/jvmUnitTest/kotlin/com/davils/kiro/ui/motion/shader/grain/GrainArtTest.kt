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

package com.davils.kiro.ui.motion.shader.grain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.later
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.shader.ShaderBackend
import com.davils.kiro.ui.motion.shader.ShaderSubject
import com.davils.kiro.ui.motion.shader.shaderContract
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration

@OptIn(ExperimentalTestApi::class)
class GrainArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultGrainArtStyle(theme)
    val canvas = style.copy(backend = ShaderBackend.Canvas)

    shaderContract(
        ShaderSubject(
            name = "grain",
            art = GrainArt(),
            fallback = GrainArt(style = canvas),
            changed = GrainArt(style = style.copy(cell = 3.dp, amount = 0.3f)),
            recolored = GrainArt(style = style.copy(shade = Color.Red, tone = Color.Blue)),
            isPointerAware = false,
        ),
    )

    test("stands still without a refresh") {
        val still = GrainArt(style.copy(refresh = null))
        val stopped = GrainArt(style.copy(refresh = Duration.ZERO))

        still.isStatic shouldBe true
        GrainArt().isStatic shouldBe false
        GrainArt(style).isStatic shouldBe false
        still.print(Render(time = later)) shouldBe still.print()
        stopped.print(Render(time = later)) shouldBe stopped.print()
    }

    test("draws fewer specks with the canvas at a lower quality") {
        val art = GrainArt(canvas)

        art.print(Render(quality = QualityLevel.Low)) shouldNotBe art.print()
        GrainArt(canvas.copy(shade = Color.Red, tone = Color.Blue)).print() shouldNotBe art.print()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(GrainArtStyleKey) { _, _ ->
                GrainArtStyles { current -> defaultGrainArtStyle(current).copy(balance = 0f) }
            },
        )

        GrainArt().print(Render(theme = custom)) shouldNotBe GrainArt().print()
    }

    test("resolves its style in a composition") {
        var resolved: GrainArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = grainArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = GrainArt(style = style)

        art shouldBe GrainArt(style = style)
        art.hashCode() shouldBe GrainArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe GrainArt()
        art.toString() shouldStartWith "GrainArt(style=GrainArtStyle(shade=null"
        GrainArt().toString() shouldBe "GrainArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(amount = 1f) shouldNotBe style
        style.copy(refresh = null).toString() shouldStartWith "GrainArtStyle(shade=null, tone=null"
    }

    extremeChecks(name = "GrainArt", style = style) { variant -> GrainArt(style = variant) }
})
