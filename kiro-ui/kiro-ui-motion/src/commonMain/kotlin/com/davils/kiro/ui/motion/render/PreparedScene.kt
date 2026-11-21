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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import com.davils.kiro.ui.motion.art.ArtContext
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.mask.brushOf
import com.davils.kiro.ui.motion.scene.Layer
import com.davils.kiro.ui.motion.scene.Scene

internal class PreparedScene(val scene: Scene, val palette: ArtPalette, val layers: List<PreparedLayer>) {
    private var maskSize: Size = Size.Unspecified
    private var maskBrush: Brush? = null

    val background: Color = scene.background ?: palette.background

    val paint: Paint = Paint()

    fun maskBrush(size: Size): Brush? {
        if (size == maskSize) return maskBrush
        maskSize = size
        maskBrush = brushOf(mask = scene.mask, size = size)
        return maskBrush
    }
}

internal fun prepareScene(scene: Scene, setup: SceneSetup): PreparedScene {
    val layers = scene.layers.mapIndexedNotNull { index, layer ->
        preparedOrNull(layer = layer, index = index, setup = setup)
    }
    return PreparedScene(scene = scene, palette = setup.palette, layers = layers)
}

private fun preparedOrNull(layer: Layer, index: Int, setup: SceneSetup): PreparedLayer? {
    if (!layer.isVisible) return null
    return prepareLayer(layer = layer, index = index, setup = setup)
}

private fun prepareLayer(layer: Layer, index: Int, setup: SceneSetup): PreparedLayer {
    val seed = layer.seed ?: setup.seed.derive(salt = index)
    val context = ArtContext(
        seed = seed,
        palette = setup.palette,
        theme = setup.theme,
        quality = setup.quality,
        textMeasurer = setup.textMeasurer,
    )
    return PreparedLayer(
        layer = layer,
        index = index,
        renderer = layer.art.prepare(context),
        seed = seed,
        palette = setup.palette,
        quality = setup.quality,
    )
}
