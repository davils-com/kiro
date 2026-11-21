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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.art.ArtRenderer

internal const val PIXELS_PER_SECOND: Float = 40f
internal const val PARTS: Float = 4f

internal class ProbeRenderer(private val art: ProbeArt) : ArtRenderer {
    override val hasStatic: Boolean
        get() = art.hasStatic

    override fun DrawScope.drawStatic(frame: ArtFrame) {
        art.probe.statics += frame
        drawRect(color = Color.Blue, size = Size(width = size.width, height = size.height / PARTS))
    }

    override fun DrawScope.drawFrame(frame: ArtFrame) {
        art.probe.frames += frame
        val x = (frame.time.seconds.toFloat() * PIXELS_PER_SECOND).mod(size.width)
        drawRect(
            color = art.color,
            topLeft = Offset(x = x, y = 0f),
            size = Size(width = size.width / PARTS, height = size.height),
        )
    }
}
