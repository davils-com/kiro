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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration

private const val WIDE_BAND: Int = 9
private const val NEAR: Float = 60f
private const val FAR: Float = 5000f

@OptIn(ExperimentalTestApi::class)
class TruchetArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultTruchetArtStyle(theme)
    val base = TruchetArt().print()

    artChecks(
        ArtCase(
            name = "truchet",
            art = TruchetArt(),
            changed = TruchetArt(style = style.copy(band = WIDE_BAND, cap = StrokeCap.Butt)),
            recolored = TruchetArt(style.copy(colors = listOf(Color.Red), line = Color.Blue, background = Color.Black)),
        ),
    )

    valueChecks(
        ValueCase(
            name = "TruchetArt",
            value = TruchetArt(style),
            copy = TruchetArt(style).copy(),
            changed = TruchetArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "TruchetArtStyle", value = style, copy = style.copy(), changed = style.copy(cell = 1.dp)),
    )

    test("draws arcs, angular lines and both") {
        val prints = TruchetVariant.entries.map { variant -> TruchetArt(style.copy(variant = variant)).print() }

        prints.toSet() shouldHaveSize TruchetVariant.entries.size
    }

    test("colors every band, some bands or none") {
        TruchetArt(style.copy(accentEvery = 1)).print() shouldNotBe base
        TruchetArt(style.copy(accentEvery = 0)).print() shouldNotBe base
    }

    test("turns the tiles under the pointer first") {
        val near = ArtInputs.None.copy(pointer = Offset(x = NEAR, y = NEAR))
        val far = ArtInputs.None.copy(pointer = Offset(x = FAR, y = FAR))

        TruchetArt().print(Render(inputs = near)) shouldNotBe base
        TruchetArt().print(Render(inputs = far)) shouldBe base
    }

    test("keeps its tiles still when every turn takes no time or no flip happens") {
        TruchetArt(style.copy(turn = 0f)).print() shouldNotBe base
        TruchetArt(style.copy(flip = 0f, fillAlpha = 0f)).print() shouldNotBe base
    }

    test("leaves out the fill below its quality") {
        val art = TruchetArt(style.copy(fillQuality = QualityLevel.High))

        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("is static only when its waves never come") {
        TruchetArt(style.copy(period = Duration.INFINITE)).isStatic shouldBe true
        TruchetArt(style).isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(TruchetArtStyleKey) { _, _ ->
                TruchetArtStyles { current -> defaultTruchetArtStyle(current).copy(band = WIDE_BAND) }
            },
        )

        TruchetArt().print(Render(theme = custom)) shouldNotBe base
        TruchetArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: TruchetArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = truchetArtStyle() }
            }
            waitForIdle()
        }

        resolved shouldBe style
    }

    extremeChecks(name = "TruchetArt", style = style) { variant -> TruchetArt(style = variant) }
})
