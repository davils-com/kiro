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

import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.export.renderFrame
import com.davils.kiro.ui.motion.fixture.colorCount
import com.davils.kiro.ui.motion.fixture.fingerprint
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeUnique
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlin.time.Duration.Companion.seconds

private const val FEW: Int = 8
private const val WIDTH: Int = 240
private const val HEIGHT: Int = 150

class ScenePresetsTest : FunSpec({
    val size = IntSize(width = WIDTH, height = HEIGHT)
    val presets = ScenePresets.all

    test("lists every preset once") {
        presets.shouldNotBeEmpty()
        presets.shouldBeUnique()
        presets shouldBe ScenePresets.all
    }

    test("gives every preset at least one visible layer") {
        for (preset in presets) {
            preset.layers.shouldNotBeEmpty()
            preset.layers.any { layer -> layer.isVisible } shouldBe true
        }
    }

    test("renders every preset in the light and the dark theme into a picture of its own") {
        for (theme in listOf(Theme.light(), Theme.dark())) {
            val prints = presets.map { preset ->
                val image = renderFrame(scene = preset, size = size, time = 1.seconds, theme = theme)
                image.colorCount() shouldBeGreaterThan FEW
                image.fingerprint()
            }
            prints.shouldBeUnique()
        }
    }

    test("keeps every preset moving") {
        for (preset in presets) {
            preset.isStatic shouldBe false
        }
    }
})
