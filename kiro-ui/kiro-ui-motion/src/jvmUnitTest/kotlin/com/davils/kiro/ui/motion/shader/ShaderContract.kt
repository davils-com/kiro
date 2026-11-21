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

package com.davils.kiro.ui.motion.shader

import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.fixture.Render
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.drawEmpty
import com.davils.kiro.ui.motion.fixture.drawInto
import com.davils.kiro.ui.motion.fixture.fingerprint
import com.davils.kiro.ui.motion.fixture.later
import com.davils.kiro.ui.motion.fixture.print
import com.davils.kiro.ui.motion.fixture.render
import com.davils.kiro.ui.motion.fixture.rendererOf
import com.davils.kiro.ui.motion.fixture.save
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeInstanceOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val FEW: Int = 8
private const val OTHER_SEED: Int = 99
private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300
private const val TALL_WIDTH: Int = 180
private const val TALL_HEIGHT: Int = 360
private const val DENSE: Float = 2f

private val sheetTimes: List<Duration> = listOf(0.seconds, 6.seconds, 14.seconds)
private val presets: List<Pair<String, ArtPalettes>> = listOf(
    "refined" to ArtPalettes.Refined,
    "expressive" to ArtPalettes.Expressive,
    "monochrome" to ArtPalettes.Monochrome,
    "night" to ArtPalettes.Night,
)

internal fun FunSpec.shaderContract(subject: ShaderSubject) {
    pictureContract(subject = subject)
    fallbackContract(subject = subject)
    sheets(subject = subject)
}

private fun FunSpec.pictureContract(subject: ShaderSubject) {
    val art = subject.art
    val base = art.print()

    test("draws with its shader") {
        art.rendererOf().shouldBeInstanceOf<ShaderRenderer>()
        art.render().colorCount() shouldBeGreaterThan FEW
    }

    test("changes over time") {
        art.print(Render(time = later)) shouldNotBe base
    }

    test("draws the same picture for the same seed and another for another seed") {
        art.print() shouldBe base
        art.print(Render(seed = OTHER_SEED)) shouldNotBe base
    }

    test("paints in the dark theme") {
        val dark = art.render(Render(theme = Theme.dark()))
        dark.colorCount() shouldBeGreaterThan FEW
        dark.fingerprint() shouldNotBe base
    }

    test("paints at every quality level") {
        QualityLevel.entries.forEach { quality ->
            art.render(Render(quality = quality)).colorCount() shouldBeGreaterThan FEW
        }
    }

    test("shows a changed style") {
        subject.changed.print() shouldNotBe base
    }

    test("applies its color overrides") {
        subject.recolored.print() shouldNotBe base
    }

    test("follows the pointer only where it reacts to it") {
        val inputs = ArtInputs.None.copy(pointer = Offset(x = 40f, y = 40f))
        (art.print(Render(inputs = inputs)) != base) shouldBe subject.isPointerAware
    }

    test("redraws at another density and size with the same renderer") {
        val renderer = art.rendererOf()
        val first = renderer.drawInto(Render())
        val dense = renderer.drawInto(Render(density = DENSE))
        val tall = renderer.drawInto(Render(width = TALL_WIDTH, height = TALL_HEIGHT, time = later))
        val again = renderer.drawInto(Render())
        listOf(first, dense, tall, again).forEach { image -> image.colorCount() shouldBeGreaterThan FEW }
        again.fingerprint() shouldBe first.fingerprint()
        renderer.drawEmpty()
    }
}

private fun FunSpec.fallbackContract(subject: ShaderSubject) {
    val fallback = subject.fallback
    val base = fallback.print()

    test("draws with the canvas when its backend asks for it") {
        fallback.rendererOf().shouldNotBeInstanceOf<ShaderRenderer>()
        fallback.render().colorCount() shouldBeGreaterThan FEW
        base shouldNotBe subject.art.print()
    }

    test("moves and follows the seed with the canvas") {
        fallback.print(Render(time = later)) shouldNotBe base
        fallback.print() shouldBe base
        fallback.print(Render(seed = OTHER_SEED)) shouldNotBe base
        fallback.render(Render(theme = Theme.dark())).colorCount() shouldBeGreaterThan FEW
        fallback.rendererOf().drawEmpty()
    }
}

private fun FunSpec.sheets(subject: ShaderSubject) {
    test("paints with the palette presets") {
        presets.forEach { (label, preset) ->
            val palette = preset.resolve(theme = Theme.light())
            val render = Render(time = sheetTimes[1], width = SHEET_WIDTH, height = SHEET_HEIGHT, palette = palette)
            val image = subject.art.render(render = render)
            image.save(name = "${subject.name}-palette-$label")
            image.colorCount() shouldBeGreaterThan FEW
        }
    }

    test("renders its contact sheet with the shader and the canvas") {
        listOf("shader" to subject.art, "canvas" to subject.fallback).forEach { (kind, art) ->
            listOf("light" to Theme.light(), "dark" to Theme.dark()).forEach { (label, theme) ->
                sheetTimes.forEach { time ->
                    val render = Render(time = time, theme = theme, width = SHEET_WIDTH, height = SHEET_HEIGHT)
                    val image = art.render(render = render)
                    image.save(name = "${subject.name}-$kind-$label-${time.inWholeSeconds}")
                    image.colorCount() shouldBeGreaterThan FEW
                }
            }
        }
    }
}
