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
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val POINTER_X: Float = 120f
private const val POINTER_Y: Float = 80f

@OptIn(ExperimentalTestApi::class)
class ParticlesArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultParticlesArtStyle(theme)
    val base = ParticlesArt().print()

    artChecks(
        ArtCase(
            name = "particles",
            art = ParticlesArt(),
            changed = ParticlesArt(style = style.copy(distance = 24.dp, linkDistance = 0.dp)),
            recolored = ParticlesArt(style = style.copy(colors = listOf(Color.Red, Color.Blue), link = Color.Green)),
        ),
    )

    valueChecks(
        ValueCase(
            name = "ParticlesArt",
            value = ParticlesArt(style),
            copy = ParticlesArt(style).copy(),
            changed = ParticlesArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "ParticlesArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(maxCount = 1),
        ),
    )

    test("shifts with the pointer") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = POINTER_X, y = POINTER_Y))

        ParticlesArt().print(Render(inputs = inputs)) shouldNotBe base
    }

    test("draws its hairlines only from their quality on and caps the particles") {
        val art = ParticlesArt(style.copy(linkQuality = QualityLevel.High))

        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
        ParticlesArt(style.copy(maxCount = 0)).print() shouldNotBe base
    }

    test("is never static and draws nothing into an empty area") {
        ParticlesArt().isStatic shouldBe false
        ParticlesArt().toString() shouldBe "ParticlesArt(style=null)"
        ParticlesArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(ParticlesArtStyleKey) { _, _ ->
                ParticlesArtStyles { current ->
                    defaultParticlesArtStyle(current).copy(distance = 24.dp, linkDistance = 0.dp)
                }
            },
        )

        ParticlesArt().print(Render(theme = custom)) shouldNotBe base
        ParticlesArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: ParticlesArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = particlesArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "ParticlesArt", style = style) { variant -> ParticlesArt(style = variant) }
})
