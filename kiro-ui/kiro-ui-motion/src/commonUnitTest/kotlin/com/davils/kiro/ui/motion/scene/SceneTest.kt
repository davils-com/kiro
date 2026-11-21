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

package com.davils.kiro.ui.motion.scene

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.color.ArtPalettes
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.input.SignalKey
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.mask.MaskStop
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.time.ArtTime
import com.davils.kiro.ui.motion.time.TimeRemap
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith

private const val HALF: Float = 0.5f
private const val QUARTER: Float = 0.25f
private const val THREE_QUARTERS: Float = 0.75f

class SceneTest : FunSpec({
    test("builds layers in order with their settings") {
        val moving = Still(isStatic = false)
        val built = scene {
            background = Color.Red
            mask = Mask.Vignette(strength = 0.5f, extent = 0.6f)
            layer(Still(isStatic = true))
            layer(moving) {
                opacity = 0.5f
                blend = BlendMode.Screen
                transform = LayerTransform.Identity.copy(rotation = 10f)
                mask = Mask.Linear(angle = 90f, stops = listOf(MaskStop(position = 0f, alpha = 1f)))
                time = TimeRemap.Identity.copy(speed = 2f)
                seed = ArtSeed(value = 3)
                isVisible = true
                isInteractive = false
            }
        }

        built.layers.size shouldBe 2
        built.background shouldBe Color.Red
        built.layers[1].opacity shouldBe HALF
        built.layers[1].blend shouldBe BlendMode.Screen
        built.layers[1].seed shouldBe ArtSeed(value = 3)
        built.layers[1].isInteractive shouldBe false
        built.isStatic shouldBe false
    }

    test("is static when no visible layer moves") {
        scene { layer(Still(isStatic = true)) }.isStatic shouldBe true
        scene { layer(Still(isStatic = false)) { isVisible = false } }.isStatic shouldBe true
        val frozen = scene {
            layer(Still(isStatic = false)) { time = TimeRemap.Identity.copy(isFrozen = true) }
        }
        frozen.isStatic shouldBe true
    }

    test("keeps its values") {
        val layer = Layer(art = Still(isStatic = true))
        val palette = ArtPalettes.Refined.resolve(Theme.light())
        val built = Scene(layers = listOf(layer), palette = palette)

        built.copy() shouldBe built
        built.copy().hashCode() shouldBe built.hashCode()
        built.toString() shouldStartWith "Scene("
        layer.copy(opacity = 0.2f) shouldNotBe layer
        layer.toString() shouldStartWith "Layer("
        LayerTransform.Identity.copy(anchor = Offset.Zero) shouldNotBe LayerTransform.Identity
    }

    test("describes its masks and inputs") {
        val stop = MaskStop(position = 0.5f, alpha = 1f)
        val masks = listOf(
            Mask.Linear(angle = 0f, stops = listOf(stop)),
            Mask.Radial(center = Offset.Zero, radius = 1f, stops = listOf(stop)),
            Mask.Vignette(strength = 1f, extent = 0.5f),
        )
        masks.forEach { mask ->
            mask shouldBe mask
            mask.hashCode() shouldBe mask.hashCode()
            mask shouldNotBe Mask.None
            mask.toString().isNotEmpty() shouldBe true
        }
        stop.copy(alpha = 0f) shouldNotBe stop
        stop.toString() shouldBe "MaskStop(position=0.5, alpha=1.0)"
        val key = SignalKey(name = "level", default = 0.25f)
        ArtInputs.None[key] shouldBe QUARTER
        ArtInputs.None.copy(signals = mapOf(key to THREE_QUARTERS))[key] shouldBe THREE_QUARTERS
        ArtInputs.None.copy(progress = 0.5f) shouldNotBe ArtInputs.None
        ArtInputs.None.toString() shouldStartWith "ArtInputs("
        key.toString() shouldBe "SignalKey(name=level)"
        QualityLevel.Low.density shouldBe QUARTER
        ArtTime.Zero shouldNotBe ArtTime(nanos = 1L)
    }
})
