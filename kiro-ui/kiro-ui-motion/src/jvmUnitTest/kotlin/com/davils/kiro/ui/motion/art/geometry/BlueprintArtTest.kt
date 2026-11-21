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

package com.davils.kiro.ui.motion.art.geometry

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
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.extremeChecks
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.fixture.textMeasurer
import com.davils.kiro.ui.motion.fixture.valueChecks
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300
private const val FEW: Int = 8

private val measurer = textMeasurer()

@OptIn(ExperimentalTestApi::class)
class BlueprintArtTest : FunSpec({
    val theme = Theme.light()
    val style = defaultBlueprintArtStyle(theme)
    val base = BlueprintArt().print()

    artChecks(
        ArtCase(
            name = "blueprint",
            art = BlueprintArt(),
            changed = BlueprintArt(style = style.copy(major = 2, cell = style.cell * 2)),
            recolored = BlueprintArt(
                style = style.copy(line = Color.Red, accent = Color.Blue, background = Color.Black),
            ),
        ),
    )

    valueChecks(
        ValueCase(
            name = "BlueprintArt",
            value = BlueprintArt(style),
            copy = BlueprintArt(style).copy(),
            changed = BlueprintArt(),
        ),
    )
    valueChecks(
        ValueCase(name = "BlueprintArtStyle", value = style, copy = style.copy(), changed = style.copy(major = 1)),
    )

    test("labels its ruler when the context can measure text") {
        val art = BlueprintArt()
        val measured = Render(textMeasurer = measurer)
        val labeled = art.print(measured)

        labeled shouldNotBe base
        art.print(Render(textMeasurer = null)) shouldBe base
        BlueprintArt(style.copy(isRulerShown = false)).print(measured) shouldBe base
        BlueprintArt(style.copy(label = { value -> "$value mm" })).print(measured) shouldNotBe labeled
    }

    test("renders its ruler in light and dark") {
        for ((name, current) in listOf("light" to Theme.light(), "dark" to Theme.dark())) {
            val render = Render(
                time = 3.seconds,
                theme = current,
                width = SHEET_WIDTH,
                height = SHEET_HEIGHT,
                textMeasurer = measurer,
            )
            val image = BlueprintArt().render(render = render)
            image.colorCount() shouldBeGreaterThan FEW
            image.save(name = "blueprint-ruler-$name")
        }
    }

    test("leaves out the marks, the brackets, the band and the grid") {
        BlueprintArt(style.copy(mark = 0.dp)).print() shouldNotBe base
        BlueprintArt(style.copy(major = 0)).print() shouldNotBe base
        BlueprintArt(style.copy(bracket = 0.dp)).print() shouldNotBe base
        BlueprintArt(style.copy(bracketAlpha = 0f)).print() shouldNotBe base
        BlueprintArt(style.copy(scanBand = 0.dp)).print() shouldNotBe base
        BlueprintArt(style.copy(bandAlpha = 0f)).print() shouldNotBe base
        BlueprintArt(style.copy(cell = 0.dp)).print() shouldNotBe base
        val art = BlueprintArt(style.copy(bandQuality = QualityLevel.High))
        art.print(Render(quality = QualityLevel.Balanced)) shouldNotBe art.print()
    }

    test("is static only when its scan line never moves") {
        BlueprintArt(style.copy(scanPeriod = Duration.INFINITE)).isStatic shouldBe true
        BlueprintArt(style).isStatic shouldBe false
        BlueprintArt().isStatic shouldBe false
    }

    test("takes its style from the components of the theme") {
        val custom = theme.copy(
            components = theme.components.with(BlueprintArtStyleKey) { _, _ ->
                BlueprintArtStyles { current -> defaultBlueprintArtStyle(current).copy(major = 2) }
            },
        )

        BlueprintArt().print(Render(theme = custom)) shouldNotBe base
        BlueprintArtStyles.Default.resolve(theme) shouldBe style
    }

    test("resolves its style in a composition") {
        var resolved: BlueprintArtStyle? = null
        runComposeUiTest {
            setContent {
                ThemeProvider(theme = theme) { resolved = blueprintArtStyle() }
            }
            waitForIdle()
        }

        val current = resolved?.copy(labelStyle = style.labelStyle)
        current shouldBe style
        resolved?.run { labelStyle.fontSize } shouldBe style.labelStyle.fontSize
    }

    extremeChecks(name = "BlueprintArt", style = style) { variant -> BlueprintArt(style = variant) }
})
