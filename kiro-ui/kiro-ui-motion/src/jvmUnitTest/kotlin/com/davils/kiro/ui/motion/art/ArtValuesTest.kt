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

package com.davils.kiro.ui.motion.art

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.time.ArtTime
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val SIDE: Int = 4

private val palette = ArtPalettes.Refined.resolve(Theme.light())

private val context = ArtContext(
    seed = ArtSeed(value = 1),
    palette = palette,
    theme = Theme.light(),
    quality = QualityLevel.High,
    textMeasurer = null,
)

private val frame = ArtFrame(
    time = ArtTime.Zero,
    inputs = ArtInputs.None,
    palette = palette,
    quality = QualityLevel.High,
    seed = ArtSeed(value = 1),
)

class ArtValuesTest : FunSpec({
    test("the context is a value") {
        context.copy() shouldBe context
        context.copy().hashCode() shouldBe context.hashCode()
        context.copy(seed = ArtSeed(value = 2)) shouldNotBe context
        context.copy(quality = QualityLevel.Low) shouldNotBe context
        context.copy(theme = Theme.dark()) shouldNotBe context
        context.copy(palette = ArtPalettes.Night.resolve(Theme.light())) shouldNotBe context
        context shouldNotBe Any()
        context.toString() shouldStartWith "ArtContext("
    }

    test("the frame is a value") {
        frame.copy() shouldBe frame
        frame.copy().hashCode() shouldBe frame.hashCode()
        frame.copy(time = ArtTime(nanos = 1L)) shouldNotBe frame
        frame.copy(inputs = ArtInputs.None.copy(progress = 1f)) shouldNotBe frame
        frame.copy(palette = ArtPalettes.Night.resolve(Theme.light())) shouldNotBe frame
        frame.copy(quality = QualityLevel.Low) shouldNotBe frame
        frame.copy(seed = ArtSeed(value = 2)) shouldNotBe frame
        frame shouldNotBe Any()
        frame.toString() shouldStartWith "ArtFrame("
    }

    test("a renderer has nothing static unless it says so") {
        val renderer = object : ArtRenderer {
            override fun DrawScope.drawFrame(frame: ArtFrame) = Unit
        }
        val bitmap = ImageBitmap(width = SIDE, height = SIDE)
        val size = Size(width = SIDE.toFloat(), height = SIDE.toFloat())
        CanvasDrawScope().draw(
            density = Density(density = 1f),
            layoutDirection = LayoutDirection.Ltr,
            canvas = Canvas(bitmap),
            size = size,
        ) {
            with(renderer) { drawStatic(frame) }
        }

        renderer.hasStatic shouldBe false
    }
})
