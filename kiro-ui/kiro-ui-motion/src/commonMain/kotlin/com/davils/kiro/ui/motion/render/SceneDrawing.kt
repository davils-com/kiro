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

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import com.davils.kiro.ui.motion.art.ArtFrame
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.time.ArtTime

internal fun DrawScope.drawScene(scene: PreparedScene, time: ArtTime, inputs: ArtInputs, statics: StaticDrawer) {
    if (size.width <= 0f || size.height <= 0f) return
    val mask = scene.maskBrush(size)
    if (mask == null) return drawContents(scene = scene, time = time, inputs = inputs, statics = statics)
    composited(paint = scene.paint, mask = mask) {
        drawContents(scene = scene, time = time, inputs = inputs, statics = statics)
    }
}

internal fun DrawScope.drawLayer(layer: PreparedLayer, frame: ArtFrame, statics: StaticDrawer) {
    if (!layer.isComposited) return drawMoved(layer = layer, frame = frame, statics = statics)
    composited(paint = layer.paint, mask = layer.maskBrush(size)) {
        drawMoved(layer = layer, frame = frame, statics = statics)
    }
}

private fun DrawScope.drawContents(scene: PreparedScene, time: ArtTime, inputs: ArtInputs, statics: StaticDrawer) {
    drawRect(color = scene.background)
    scene.layers.forEach { layer ->
        drawLayer(layer = layer, frame = layer.frameAt(time = time, inputs = inputs), statics = statics)
    }
}

private fun DrawScope.composited(paint: Paint, mask: Brush?, draw: DrawScope.() -> Unit) {
    val canvas = drawContext.canvas
    canvas.saveLayer(bounds = Rect(offset = Offset.Zero, size = size), paint = paint)
    draw()
    if (mask != null) drawRect(brush = mask, blendMode = BlendMode.DstIn)
    canvas.restore()
}

private fun DrawScope.drawMoved(layer: PreparedLayer, frame: ArtFrame, statics: StaticDrawer) {
    val transform = layer.layer.transform
    val translation = translationOf(transform = transform, size = size, inputs = frame.inputs)
    if (!isMoved(transform = transform, translation = translation)) return drawArt(layer, frame, statics)
    val pivot = anchorOf(transform = transform, size = size)
    translate(left = translation.x, top = translation.y) {
        rotate(degrees = transform.rotation, pivot = pivot) {
            scale(scale = transform.scale, pivot = pivot) { drawArt(layer = layer, frame = frame, statics = statics) }
        }
    }
}

private fun DrawScope.drawArt(layer: PreparedLayer, frame: ArtFrame, statics: StaticDrawer) {
    if (layer.renderer.hasStatic) statics.draw(scope = this, layer = layer)
    with(layer.renderer) { drawFrame(frame) }
}
