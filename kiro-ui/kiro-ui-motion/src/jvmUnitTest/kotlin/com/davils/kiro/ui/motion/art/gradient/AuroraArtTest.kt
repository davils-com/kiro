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

import androidx.compose.ui.graphics.BlendMode
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
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.fixture.valueChecks
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val FEW: Int = 8
private const val WIDE_WIDTH: Int = 1200
private const val WIDE_HEIGHT: Int = 600

@OptIn(ExperimentalTestApi::class)
class AuroraArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultAuroraArtStyle(theme)
    val base = AuroraArt().print()

    artChecks(
        ArtCase(
            name = "aurora",
            art = AuroraArt(),
            changed = AuroraArt(style = style.copy(bands = 1, lift = 0.3f)),
            recolored = AuroraArt(style = style.copy(colors = listOf(Color.Red, Color.Blue))),
        ),
    )

    valueChecks(
        ValueCase(
            name = "AuroraArt",
            value = AuroraArt(style),
            copy = AuroraArt(style).copy(),
            changed = AuroraArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "AuroraArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(ripple = 0f),
        ),
    )

    test("draws nothing without bands and blends its bands") {
        val empty = AuroraArt(style.copy(bands = 0)).render()
        val multiplied = AuroraArt(style.copy(blendMode = BlendMode.Multiply)).render()

        empty.colorCount() shouldBe 1
        multiplied.colorCount() shouldBeGreaterThan FEW
    }

    test("draws smooth bands in a wide area") {
        val wide = AuroraArt().render(Render(width = WIDE_WIDTH, height = WIDE_HEIGHT, theme = Theme.dark()))
        wide.save(name = "aurora-wide")

        wide.colorCount() shouldBeGreaterThan FEW
    }

    test("is never static and draws nothing into an empty area") {
        AuroraArt().isStatic shouldBe false
        AuroraArt().toString() shouldBe "AuroraArt(style=null)"
        AuroraArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(AuroraArtStyleKey) { _, _ ->
                AuroraArtStyles { current ->
                    defaultAuroraArtStyle(current).copy(bands = 1, lift = 0.3f)
                }
            },
        )

        AuroraArt().print(Render(theme = custom)) shouldNotBe base
        AuroraArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: AuroraArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = auroraArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "AuroraArt", style = style) { variant -> AuroraArt(style = variant) }
})
