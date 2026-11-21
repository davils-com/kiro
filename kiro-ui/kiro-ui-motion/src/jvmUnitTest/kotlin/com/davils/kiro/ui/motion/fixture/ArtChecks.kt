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

package com.davils.kiro.ui.motion.fixture

import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val FEW: Int = 8
private const val OTHER_SEED: Int = 99
private const val SHEET_WIDTH: Int = 480
private const val SHEET_HEIGHT: Int = 300
private const val TALL_WIDTH: Int = 180
private const val TALL_HEIGHT: Int = 360
private const val DENSE: Float = 2f

internal val later: Duration = 2500.milliseconds
private val sheetTimes: List<Duration> = listOf(0.seconds, 3.seconds, 8.seconds)
private val presets: List<Pair<String, ArtPalettes>> = listOf(
    "refined" to ArtPalettes.Refined,
    "expressive" to ArtPalettes.Expressive,
    "monochrome" to ArtPalettes.Monochrome,
    "vivid" to ArtPalettes.Vivid,
    "pastel" to ArtPalettes.Pastel,
    "duotone" to ArtPalettes.Duotone,
    "night" to ArtPalettes.Night,
)

internal fun FunSpec.artChecks(case: ArtCase) {
    pictureChecks(case = case)
    variationChecks(case = case)
    sheetChecks(case = case)
}

internal fun FunSpec.valueChecks(case: ValueCase) {
    test("${case.name} is a value") {
        case.copy shouldBe case.value
        case.copy.hashCode() shouldBe case.value.hashCode()
        case.changed shouldNotBe case.value
        case.value.toString() shouldStartWith "${case.name}("
    }
}

private fun FunSpec.pictureChecks(case: ArtCase) {
    val art = case.art
    val base = art.print()

    test("${case.name} paints more than a few colors") {
        art.colors() shouldBeGreaterThan FEW
    }

    test("${case.name} changes over time") {
        art.isStatic shouldBe false
        art.print(Render(time = later)) shouldNotBe base
    }

    test("${case.name} draws the same picture for the same seed and another for another seed") {
        art.print() shouldBe base
        art.print(Render(seed = OTHER_SEED)) shouldNotBe base
    }

    test("${case.name} paints in the dark theme") {
        val dark = art.render(Render(theme = Theme.dark()))
        dark.colorCount() shouldBeGreaterThan FEW
        dark.fingerprint() shouldNotBe base
    }

    test("${case.name} paints at every quality level") {
        val prints = QualityLevel.entries.map { quality ->
            val image = art.render(Render(quality = quality))
            image.colorCount() shouldBeGreaterThan FEW
            image.fingerprint()
        }
        prints.toSet() shouldHaveAtLeastSize 2
    }
}

private fun FunSpec.variationChecks(case: ArtCase) {
    val art = case.art
    val base = art.print()

    test("${case.name} shows a changed style") {
        case.changed.print() shouldNotBe base
    }

    test("${case.name} applies its color overrides") {
        case.recolored.print() shouldNotBe base
    }

    test("${case.name} redraws at another density and size with the same renderer") {
        val images = art.drawSequence(
            Render(),
            Render(density = DENSE),
            Render(width = TALL_WIDTH, height = TALL_HEIGHT, time = later),
            Render(palette = ArtPalettes.Vivid.resolve(Theme.light())),
            Render(),
        )
        images.forEach { image -> image.colorCount() shouldBeGreaterThan FEW }
        images.last().fingerprint() shouldBe base
        images[1].fingerprint() shouldNotBe base
    }

    test("${case.name} draws nothing into an empty area") {
        art.drawEmpty()
    }
}

private fun FunSpec.sheetChecks(case: ArtCase) {
    val art = case.art

    test("${case.name} paints with every palette preset") {
        presets.forEach { (label, preset) ->
            val palette = preset.resolve(theme = Theme.light())
            val render = Render(time = sheetTimes[1], width = SHEET_WIDTH, height = SHEET_HEIGHT, palette = palette)
            val image = art.render(render = render)
            image.save(name = "${case.name}-palette-$label")
            image.colorCount() shouldBeGreaterThan FEW
        }
    }

    test("${case.name} renders its contact sheet in light, dark and monochrome at three points in time") {
        art.saveSheet(name = case.name, times = sheetTimes)
    }
}
