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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Paint
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.color.ArtPalette
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.mask.brushOf
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.scene.Layer
import com.davils.kiro.ui.motion.time.ArtTime

internal class PreparedLayer(
    val layer: Layer,
    val index: Int,
    val renderer: ArtRenderer,
    val seed: ArtSeed,
    val palette: ArtPalette,
    val quality: QualityLevel,
) {
    private var maskSize: Size = Size.Unspecified
    private var maskBrush: Brush? = null

    val paint: Paint = Paint().apply {
        alpha = layer.opacity.coerceIn(minimumValue = 0f, maximumValue = 1f)
        blendMode = layer.blend
    }

    val isComposited: Boolean = layer.opacity < 1f || layer.blend != BlendMode.SrcOver || layer.mask != Mask.None

    val staticFrame: ArtFrame = ArtFrame(
        time = layer.time.apply(ArtTime.Zero),
        inputs = ArtInputs.None,
        palette = palette,
        quality = quality,
        seed = seed,
    )

    fun frameAt(time: ArtTime, inputs: ArtInputs): ArtFrame = ArtFrame(
        time = layer.time.apply(time),
        inputs = inputsOf(inputs),
        palette = palette,
        quality = quality,
        seed = seed,
    )

    fun maskBrush(size: Size): Brush? {
        if (size == maskSize) return maskBrush
        maskSize = size
        maskBrush = brushOf(mask = layer.mask, size = size)
        return maskBrush
    }

    private fun inputsOf(inputs: ArtInputs): ArtInputs {
        if (!layer.isInteractive) return ArtInputs.None
        return inputs
    }
}
