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

package com.davils.kiro.ui.motion.art.tiling

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val FIRST_ANGLE: Double = PI / 4.0
private const val FULL_TURN: Double = PI * 2.0

internal class LatticeBuilder {
    private val frames = ArrayList<Float>()
    private val lines = ArrayList<Float>()
    private val panels = ArrayList<FloatArray>()

    fun frame(x1: Float, y1: Float, x2: Float, y2: Float) {
        frames += x1
        frames += y1
        frames += x2
        frames += y2
    }

    fun line(x1: Float, y1: Float, x2: Float, y2: Float) {
        lines += x1
        lines += y1
        lines += x2
        lines += y2
    }

    fun panel(corners: FloatArray) {
        panels += corners
    }

    fun build(): WeaveLattice {
        val segments = FloatArray(frames.size + lines.size)
        for (index in frames.indices) segments[index] = frames[index]
        for (index in lines.indices) segments[frames.size + index] = lines[index]
        return WeaveLattice(segments = segments, frames = frames.size / 4, panels = panels.toList())
    }
}

internal fun circleOf(x: Float, y: Float, radius: Float, pieces: Int): FloatArray {
    val points = FloatArray(pieces * 2)
    for (piece in 0 until pieces) {
        val angle = FIRST_ANGLE + FULL_TURN * piece / pieces
        points[piece * 2] = x + (radius * cos(angle)).toFloat()
        points[piece * 2 + 1] = y + (radius * sin(angle)).toFloat()
    }
    return points
}
