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

package com.davils.kiro.ui.motion.art

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.VertexMode
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

private const val MAX_VERTICES: Int = 30_000

internal class TriangleBatch {
    private val paint = Paint()
    private val layer = Paint()
    private val positions = ArrayList<Offset>()
    private val colors = ArrayList<Color>()
    private val indices = ArrayList<Int>()

    val size: Int
        get() = positions.size

    fun vertex(position: Offset, color: Color): Int {
        positions.add(position)
        colors.add(color)
        return positions.size - 1
    }

    fun triangle(first: Int, second: Int, third: Int) {
        indices.add(first)
        indices.add(second)
        indices.add(third)
    }

    fun DrawScope.reserve(count: Int, blendMode: BlendMode) {
        if (positions.size + count > MAX_VERTICES) flush(blendMode = blendMode)
    }

    fun DrawScope.flush(blendMode: BlendMode) {
        if (indices.isNotEmpty()) {
            paint.blendMode = blendMode
            val vertices = vertices()
            drawIntoCanvas { canvas ->
                canvas.drawVertices(vertices = vertices, blendMode = BlendMode.Dst, paint = paint)
            }
        }
        clear()
    }

    fun DrawScope.flushLayer(blendMode: BlendMode) {
        if (indices.isNotEmpty()) {
            layer.blendMode = blendMode
            paint.blendMode = BlendMode.Src
            val vertices = vertices()
            val bounds = Rect(offset = Offset.Zero, size = size)
            drawIntoCanvas { canvas ->
                canvas.saveLayer(bounds = bounds, paint = layer)
                canvas.drawVertices(vertices = vertices, blendMode = BlendMode.Dst, paint = paint)
                canvas.restore()
            }
        }
        clear()
    }

    private fun vertices(): Vertices = Vertices(
        vertexMode = VertexMode.Triangles,
        positions = positions,
        textureCoordinates = positions,
        colors = colors,
        indices = indices,
    )

    private fun clear() {
        positions.clear()
        colors.clear()
        indices.clear()
    }
}
