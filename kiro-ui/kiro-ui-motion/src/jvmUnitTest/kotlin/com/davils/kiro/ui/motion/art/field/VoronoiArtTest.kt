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

package com.davils.kiro.ui.motion.art.field

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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private const val POINTER_X: Float = 120f
private const val POINTER_Y: Float = 80f

@OptIn(ExperimentalTestApi::class)
class VoronoiArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultVoronoiArtStyle(theme)
    val base = VoronoiArt().print()

    artChecks(
        ArtCase(
            name = "voronoi",
            art = VoronoiArt(),
            changed = VoronoiArt(style = style.copy(cell = 6.dp, seedRadius = 0.dp)),
            recolored = VoronoiArt(style = style.copy(colors = listOf(Color.Red, Color.Blue), line = Color.Green)),
        ),
    )

    valueChecks(
        ValueCase(
            name = "VoronoiArt",
            value = VoronoiArt(style),
            copy = VoronoiArt(style).copy(),
            changed = VoronoiArt(),
        ),
    )
    valueChecks(
        ValueCase(
            name = "VoronoiArtStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(jitter = 0f),
        ),
    )

    test("lights the shard under the pointer") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = POINTER_X, y = POINTER_Y))

        VoronoiArt().print(Render(inputs = inputs)) shouldNotBe base
    }

    test("leaves out the dots at the seeds") {
        VoronoiArt(style.copy(seedAlpha = 0f)).print() shouldNotBe base
    }

    test("is never static and draws nothing into an empty area") {
        VoronoiArt().isStatic shouldBe false
        VoronoiArt().toString() shouldBe "VoronoiArt(style=null)"
        VoronoiArt().drawEmpty()
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(VoronoiArtStyleKey) { _, _ ->
                VoronoiArtStyles { current ->
                    defaultVoronoiArtStyle(current).copy(cell = 6.dp, seedRadius = 0.dp)
                }
            },
        )

        VoronoiArt().print(Render(theme = custom)) shouldNotBe base
        VoronoiArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: VoronoiArtStyle? = null
        runComposeUiTest {
            setContent { ThemeProvider(theme = theme) { resolved = voronoiArtStyle() } }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "VoronoiArt", style = style) { variant -> VoronoiArt(style = variant) }
})
