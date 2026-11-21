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

package com.davils.kiro.gallery.playground

import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.quality.QualityLevel
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldNotContain
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

private const val HALF: Float = 0.5f
private const val THIRD: Float = 0.333f
private const val NEGATIVE: Float = -24f
private const val CELL: Float = 1.5f
private const val SHORT: Int = 350
private const val LONG: Int = 1_500
private const val WHOLE: Int = 14
private const val SEED: Int = 7
private const val SPEED: Float = 1.5f
private const val CELL_SIZE: Float = 32f
private const val RANGE_END: Float = 64f
private const val FINE_STEP: Float = 0.01f
private const val COARSE_STEP: Float = 0.1f
private const val TRUCHET: String = "Truchet"
private const val LINE_ALPHA: String = "lineAlpha"
private const val PLAIN: String = """MotionCanvas(
    scene = scene {
        layer(TruchetArt())
    },
)"""

class PlaygroundCodeTest : FunSpec({
    val theme = Theme.light()

    context("format") {
        test("writes numbers, lengths and durations as Kotlin") {
            floatCode(HALF) shouldBe "0.5f"
            floatCode(THIRD) shouldBe "0.333f"
            floatCode(NEGATIVE) shouldBe "-24f"
            dpCode(CELL.dp) shouldBe "1.5.dp"
            durationCode(SHORT.milliseconds) shouldBe "350.milliseconds"
            durationCode(LONG.milliseconds) shouldBe "1.5.seconds"
            durationCode(WHOLE.seconds) shouldBe "14.seconds"
            numberOf(0f) shouldBe "0"
        }

        test("steps through a range in about a hundred steps") {
            stepOf(0f..1f) shouldBe FINE_STEP
            stepOf(0f..RANGE_END) shouldBe COARSE_STEP
        }
    }

    context("code") {
        test("shows the plain art until something changes") {
            val state = PlaygroundState(spec = artSpecs.first { spec -> spec.name == TRUCHET })

            codeOf(state = state, theme = theme) shouldBe PLAIN
        }

        test("shows only the changed fields, the palette, the playback and the motion style") {
            val state = PlaygroundState(spec = artSpecs.first { spec -> spec.name == TRUCHET })
            state.change(name = "cell", setting = Setting.Amount(value = CELL_SIZE))
            state.change(name = "line", setting = Setting.Pick(index = colorChoices.lastIndex))
            val defaults = state.spec.settingsOf(theme = theme, palette = palette(state), settings = emptyMap())
            state.change(name = LINE_ALPHA, setting = defaults.getValue(LINE_ALPHA))

            val code = codeOf(state = state, theme = theme)

            code shouldContain "val palette = artPalette()"
            code shouldContain "style = truchetArtStyle().copy("
            code shouldContain "cell = 32.dp,"
            code shouldContain "line = palette.accent(3),"
            code shouldNotContain LINE_ALPHA

            state.palette = PalettePreset.Vivid
            state.seed = SEED
            state.quality = QualityLevel.Low
            state.playback.speed = SPEED
            val tuned = codeOf(state = state, theme = theme)

            tuned shouldContain "val palette = ArtPalettes.Vivid.resolve(Theme.current)"
            tuned shouldContain "this.palette = palette"
            tuned shouldContain "playback = rememberArtPlayback(speed = 1.5f),"
            tuned shouldContain "seed = ArtSeed(value = 7),"
            tuned shouldContain "quality = QualityPolicy.Fixed(level = QualityLevel.Low),"

            state.seed = null
            codeOf(state = state, theme = theme) shouldContain
                "style = motionStyle().copy(quality = QualityPolicy.Fixed(level = QualityLevel.Low)),"

            state.reset()
            codeOf(state = state, theme = theme) shouldNotContain "copy("
        }

        test("starts over when another art is chosen") {
            val state = PlaygroundState(spec = artSpecs.first())
            val first = state.spec.parameters.first()
            state.change(name = first.name, setting = Setting.Amount(value = CELL_SIZE))
            val next = artSpecs.last()

            state.select(next)

            state.settings shouldBe emptyMap()
            codeOf(state = state, theme = theme) shouldContain "layer(${next.art}())"
        }
    }
})

private fun palette(state: PlaygroundState): ArtPalette = state.palette.palettes.resolve(Theme.light())
