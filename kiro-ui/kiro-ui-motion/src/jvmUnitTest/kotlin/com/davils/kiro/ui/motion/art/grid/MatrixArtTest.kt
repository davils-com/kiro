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

package com.davils.kiro.ui.motion.art.grid

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.motion.export.ExportOptions
import com.davils.kiro.ui.motion.export.renderFrame
import com.davils.kiro.ui.motion.fixture.ArtCase
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.ValueCase
import com.davils.kiro.ui.motion.fixture.artChecks
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.fixture.textMeasurer
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.scene.scene
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val FEW: Int = 8
private const val WIDE: Float = 32f
private const val LONG_TRAIL: Int = 30
private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300
private const val DENSE: Float = 2f

private val sheetTimes = listOf(0.seconds, 2.seconds, 5.seconds)

private fun MatrixArt.glyphs(
    theme: Theme = Theme.light(),
    time: Duration = 2.seconds,
    density: Float = 1f,
): ImageBitmap = renderFrame(
    scene = scene { layer(this@glyphs) },
    size = IntSize(width = SHEET_WIDTH, height = SHEET_HEIGHT),
    time = time,
    theme = theme,
    options = ExportOptions(density = Density(density), textMeasurer = textMeasurer()),
)

@OptIn(ExperimentalTestApi::class)
class MatrixArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultMatrixArtStyle(theme)
    val base = MatrixArt().print()

    artChecks(
        ArtCase(
            name = "matrix",
            art = MatrixArt(),
            changed = MatrixArt(style = style.copy(cell = WIDE.dp, trail = LONG_TRAIL)),
            recolored = MatrixArt(
                style = style.copy(
                    colors = listOf(Color.Red, Color.Blue),
                    head = Color.Green,
                    background = Color.Black,
                ),
            ),
        ),
    )

    valueChecks(
        ValueCase(name = "MatrixArt", value = MatrixArt(style), copy = MatrixArt(style).copy(), changed = MatrixArt()),
    )
    valueChecks(
        ValueCase(name = "MatrixArtStyle", value = style, copy = style.copy(), changed = style.copy(trail = 1)),
    )

    test("draws glyphs with a text measurer, and bars without glyphs") {
        val glyphs = MatrixArt().glyphs()
        val bars = MatrixArt(style.copy(glyphs = "")).glyphs()

        glyphs.colorCount() shouldBeGreaterThan FEW
        glyphs.fingerprint() shouldNotBe bars.fingerprint()
        val dense = MatrixArt().glyphs(density = DENSE)
        dense.colorCount() shouldBeGreaterThan FEW
    }

    test("renders its glyphs in light and dark at three points in time") {
        for ((label, current) in listOf("light" to Theme.light(), "dark" to Theme.dark())) {
            for (time in sheetTimes) {
                val image = MatrixArt().glyphs(theme = current, time = time)
                image.colorCount() shouldBeGreaterThan FEW
                image.save(name = "matrix-glyphs-$label-${time.inWholeSeconds}")
            }
        }
    }

    test("leaves the area empty without columns or cells") {
        MatrixArt(style.copy(columnShare = 0f)).print() shouldNotBe base
        MatrixArt(style.copy(cell = 0.dp)).print() shouldNotBe base
    }

    test("draws nothing into an empty area") {
        MatrixArt().drawEmpty()
    }

    test("is static only when the drops never fall and the glyphs never change") {
        val still = style.copy(period = Duration.INFINITE, changePeriod = Duration.INFINITE)

        MatrixArt(still).isStatic shouldBe true
        MatrixArt(still.copy(changePeriod = style.changePeriod)).isStatic shouldBe false
        MatrixArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(MatrixArtStyleKey) { _, _ ->
                MatrixArtStyles { current -> defaultMatrixArtStyle(current).copy(cell = WIDE.dp) }
            },
        )

        MatrixArt().print(Render(theme = custom)) shouldNotBe base
        MatrixArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: MatrixArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = matrixArtStyle() }
            }
            waitForIdle()
        }

        resolved?.copy(textStyle = style.textStyle) shouldBe style
    }

    extremeChecks(name = "MatrixArt", style = style) { variant -> MatrixArt(style = variant) }
})
