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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.input.ArtInputs
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration.Companion.seconds

private const val FEW_LINES: Int = 40
private const val POINTER_X: Float = 160f
private const val POINTER_Y: Float = 100f
private const val FEW: Int = 8
private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300

@OptIn(ExperimentalTestApi::class)
class FlowFieldArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultFlowFieldArtStyle(theme)
    val base = FlowFieldArt().print()

    artChecks(
        ArtCase(
            name = "flowfield",
            art = FlowFieldArt(),
            changed = FlowFieldArt(style = style.copy(lines = FEW_LINES)),
            recolored = FlowFieldArt(style = style.copy(colors = listOf(Color.Red, Color.Blue))),
        ),
    )

    test("follows every kind of field and colors by every mode") {
        val prints = FlowFieldKind.entries.flatMap { kind ->
            FlowColorMode.entries.map { mode ->
                val image = FlowFieldArt(style.copy(kind = kind, colorMode = mode))
                    .render(Render(time = 3.seconds, width = SHEET_WIDTH, height = SHEET_HEIGHT))
                image.save(name = "flowfield-${kind.name.lowercase()}-${mode.name.lowercase()}")
                image.colorCount() shouldBeGreaterThan FEW
                image.fingerprint()
            }
        }

        prints.toSet() shouldHaveSize FlowFieldKind.entries.size * FlowColorMode.entries.size
    }

    test("swirls around the pointer") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = POINTER_X, y = POINTER_Y))

        FlowFieldArt().print(Render(inputs = inputs)) shouldNotBe base
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(FlowFieldArtStyleKey) { _, _ ->
                FlowFieldArtStyles { current -> defaultFlowFieldArtStyle(current).copy(lines = FEW_LINES) }
            },
        )

        FlowFieldArt().print(Render(theme = custom)) shouldNotBe base
    }

    test("resolves its style in a composition") {
        var resolved: FlowFieldArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = flowFieldArtStyle() } }
        }

        resolved shouldBe style
    }

    test("is a value") {
        val art = FlowFieldArt(style = style)

        art shouldBe FlowFieldArt(style = style)
        art.hashCode() shouldBe FlowFieldArt(style = style).hashCode()
        art.copy() shouldBe art
        art.copy(style = null) shouldBe FlowFieldArt()
        art.isStatic shouldBe false
        art.toString() shouldStartWith "FlowFieldArt(style=FlowFieldArtStyle(colors=null"
        FlowFieldArt().toString() shouldBe "FlowFieldArt(style=null)"
        style.copy() shouldBe style
        style.copy().hashCode() shouldBe style.hashCode()
        style.copy(lines = FEW_LINES) shouldNotBe style
    }

    extremeChecks(name = "FlowFieldArt", style = style) { variant -> FlowFieldArt(style = variant) }
})
