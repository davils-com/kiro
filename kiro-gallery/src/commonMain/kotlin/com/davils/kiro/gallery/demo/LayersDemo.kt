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

package com.davils.kiro.gallery.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.motion.art.gradient.GradientArt
import com.davils.kiro.ui.motion.art.grid.GridPatternArt
import com.davils.kiro.ui.motion.art.grid.GridPatternKind
import com.davils.kiro.ui.motion.art.grid.gridPatternArtStyle
import com.davils.kiro.ui.motion.art.particle.ParticlesArt
import com.davils.kiro.ui.motion.host.MotionCanvas
import com.davils.kiro.ui.motion.mask.Mask
import com.davils.kiro.ui.motion.mask.MaskStop
import com.davils.kiro.ui.motion.scene.scene

@Composable
internal fun LayersDemo() {
    val dots = gridPatternArtStyle().copy(kind = GridPatternKind.Dots)
    val spotlight = Mask.Radial(
        center = Offset(x = 0.5f, y = 0.5f),
        radius = 0.7f,
        stops = listOf(MaskStop(position = 0.3f, alpha = 1f), MaskStop(position = 1f, alpha = 0f)),
    )
    val layers = scene {
        layer(GradientArt())
        layer(GridPatternArt(style = dots)) {
            opacity = 0.7f
            mask = spotlight
        }
        layer(ParticlesArt()) {
            opacity = 0.8f
            blend = BlendMode.Plus
        }
    }
    MotionCanvas(scene = layers, modifier = Modifier.artFrame(theme, height = 320.dp))
}
