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

package com.davils.kiro.ui.motion.host

import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import com.davils.kiro.ui.motion.render.PreparedLayer
import com.davils.kiro.ui.motion.render.PreparedScene
import com.davils.kiro.ui.motion.render.StaticDrawer

internal class RecordedStatics(scope: CacheDrawScope, scene: PreparedScene) : StaticDrawer {
    private val recordings: Map<Int, GraphicsLayer> = scene.layers
        .filter { layer -> layer.renderer.hasStatic }
        .associate { layer -> layer.index to scope.recordStatic(layer) }

    override fun draw(scope: DrawScope, layer: PreparedLayer) {
        scope.drawLayer(recordings.getValue(layer.index))
    }
}

private fun CacheDrawScope.recordStatic(layer: PreparedLayer): GraphicsLayer {
    val recording = obtainGraphicsLayer()
    recording.record { StaticDrawer.Direct.draw(scope = this, layer = layer) }
    return recording
}
