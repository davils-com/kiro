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

package com.davils.kiro.ui.motion.render

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.color.artPaletteOf
import com.davils.kiro.ui.motion.fixture.Probe
import com.davils.kiro.ui.motion.fixture.ProbeArt
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.scene.scene
import com.davils.kiro.ui.motion.time.ArtTime
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty

private const val SIDE: Int = 8

private fun PreparedScene.drawInto(size: Size) {
    val bitmap = ImageBitmap(width = SIDE, height = SIDE)
    CanvasDrawScope().draw(
        density = Density(density = 1f),
        layoutDirection = LayoutDirection.Ltr,
        canvas = Canvas(bitmap),
        size = size,
    ) {
        drawScene(scene = this@drawInto, time = ArtTime.Zero, inputs = ArtInputs.None, statics = StaticDrawer.Direct)
    }
}

class SceneDrawingTest : FunSpec({
    test("draws nothing without a width or a height") {
        val probe = Probe()
        val theme = Theme.light()
        val setup = SceneSetup(
            theme = theme,
            palette = artPaletteOf(theme),
            quality = QualityLevel.High,
            seed = ArtSeed(value = 1),
            textMeasurer = null,
        )
        val prepared = prepareScene(scene = scene { layer(ProbeArt(probe = probe)) }, setup = setup)

        prepared.drawInto(size = Size(width = 0f, height = SIDE.toFloat()))
        prepared.drawInto(size = Size(width = SIDE.toFloat(), height = 0f))

        probe.frames.shouldBeEmpty()
    }
})
