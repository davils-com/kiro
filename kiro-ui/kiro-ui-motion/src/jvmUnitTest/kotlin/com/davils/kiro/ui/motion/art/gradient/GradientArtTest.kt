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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration.Companion.seconds

private const val FEW: Int = 8
private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300

@OptIn(ExperimentalTestApi::class)
class GradientArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultGradientArtStyle(theme)
    val base = GradientArt().print()

    artChecks(
        ArtCase(
            name = "gradient",
            art = GradientArt(),
            changed = GradientArt(style = style.copy(kind = GradientKind.Radial, reach = 0.4f)),
            recolored = GradientArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "GradientArt",
            value = GradientArt(style),
            copy = GradientArt(style).copy(),
            changed = GradientArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "GradientArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(steps = 2),
        ),
    )

    test("draws every kind of gradient") {
        val prints = GradientKind.entries.map { kind ->
            val image = GradientArt(style.copy(kind = kind))
                .render(Render(time = 3.seconds, width = SHEET_WIDTH, height = SHEET_HEIGHT))
            image.save(name = "gradient-kind-${kind.name.lowercase()}")
            image.colorCount() shouldBeGreaterThan FEW
            image.fingerprint()
        }

        prints.toSet() shouldHaveSize GradientKind.entries.size
    }

    test("is never static and draws nothing into an empty area") {
        GradientArt().isStatic shouldBe false
        GradientArt().toString() shouldBe "GradientArt(style=null)"
        GradientArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(GradientArtStyleKey) { _, _ ->
                GradientArtStyles { current ->
                    defaultGradientArtStyle(current).copy(kind = GradientKind.Radial, reach = 0.4f)
                }
            },
        )

        GradientArt().print(Render(theme = custom)) shouldNotBe base
        GradientArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: GradientArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = gradientArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "GradientArt", style = style) { variant -> GradientArt(style = variant) }
})
