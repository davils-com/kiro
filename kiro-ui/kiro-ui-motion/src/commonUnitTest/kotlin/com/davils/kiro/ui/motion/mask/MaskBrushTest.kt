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

package com.davils.kiro.ui.motion.mask

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.SolidColor
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

private val size: Size = Size(width = 200f, height = 100f)
private val fade: List<MaskStop> = listOf(MaskStop(position = 1f, alpha = 0f), MaskStop(position = 0f, alpha = 1f))

class MaskBrushTest : FunSpec({
    test("shows everything without a mask or without stops") {
        brushOf(mask = Mask.None, size = size).shouldBeNull()
        brushOf(mask = Mask.Linear(angle = 0f, stops = emptyList()), size = size).shouldBeNull()
    }

    test("fills with one alpha for a single stop") {
        val brush = brushOf(mask = Mask.Radial(center = Offset.Zero, radius = 1f, stops = fade.take(1)), size = size)

        brush shouldBe SolidColor(Color.Black.copy(alpha = 0f))
    }

    test("turns gradients into shader brushes") {
        val linear = Mask.Linear(angle = 90f, stops = fade)
        val radial = Mask.Radial(center = Offset(x = 0.5f, y = 0.5f), radius = 0f, stops = fade)
        val vignette = Mask.Vignette(strength = 0.5f, extent = 0.4f)

        brushOf(mask = linear, size = size).shouldBeInstanceOf<ShaderBrush>()
        brushOf(mask = radial, size = size).shouldBeInstanceOf<ShaderBrush>()
        brushOf(mask = vignette, size = Size.Zero).shouldBeInstanceOf<ShaderBrush>()
    }

    test("masks and stops are values") {
        val linear = Mask.Linear(angle = 90f, stops = fade)
        val radial = Mask.Radial(center = Offset.Zero, radius = 1f, stops = fade)
        val vignette = Mask.Vignette(strength = 0.5f, extent = 0.4f)

        linear shouldBe Mask.Linear(angle = 90f, stops = fade)
        linear.hashCode() shouldBe Mask.Linear(angle = 90f, stops = fade).hashCode()
        linear shouldNotBe Mask.Linear(angle = 0f, stops = fade)
        radial shouldBe Mask.Radial(center = Offset.Zero, radius = 1f, stops = fade)
        radial.hashCode() shouldBe Mask.Radial(center = Offset.Zero, radius = 1f, stops = fade).hashCode()
        radial shouldNotBe Mask.Radial(center = Offset.Zero, radius = 2f, stops = fade)
        vignette shouldBe Mask.Vignette(strength = 0.5f, extent = 0.4f)
        vignette.hashCode() shouldBe Mask.Vignette(strength = 0.5f, extent = 0.4f).hashCode()
        vignette shouldNotBe Mask.Vignette(strength = 0.5f, extent = 0.5f)
        linear.toString() shouldContain "angle=90.0"
        radial.toString() shouldContain "radius=1.0"
        vignette.toString() shouldContain "strength=0.5"
        fade.first().copy() shouldBe fade.first()
        fade.first().copy(alpha = 1f) shouldNotBe fade.first()
        fade.first().hashCode() shouldBe MaskStop(position = 1f, alpha = 0f).hashCode()
        fade.first().toString() shouldBe "MaskStop(position=1.0, alpha=0.0)"
        fade.first() shouldNotBe MaskStop(position = 0f, alpha = 0f)
        fade.first().equals(linear) shouldBe false
        linear.equals(radial) shouldBe false
        radial.equals(vignette) shouldBe false
        vignette.equals(linear) shouldBe false
    }
})
