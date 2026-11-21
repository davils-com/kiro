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

package com.davils.kiro.ui.motion.art.field

internal class VoronoiShard {
    var points: FloatArray = FloatArray(size = INITIAL * 2)
        private set
    var size: Int = 0
        private set
    private var scratch = FloatArray(size = INITIAL * 2)

    fun reset(left: Float, top: Float, right: Float, bottom: Float) {
        size = 0
        add(x = left, y = top)
        add(x = right, y = top)
        add(x = right, y = bottom)
        add(x = left, y = bottom)
    }

    fun clip(normalX: Float, normalY: Float, limit: Float) {
        val count = size
        if (count == 0) return
        if (scratch.size < points.size + 2) scratch = FloatArray(size = points.size + 2)
        points.copyInto(destination = scratch, endIndex = count * 2)
        size = 0
        for (index in 0 until count) {
            val next = (index + 1) % count
            val ax = scratch[index * 2]
            val ay = scratch[index * 2 + 1]
            val bx = scratch[next * 2]
            val by = scratch[next * 2 + 1]
            val first = normalX * ax + normalY * ay - limit
            val second = normalX * bx + normalY * by - limit
            if (first <= 0f) add(x = ax, y = ay)
            if (first * second < 0f) {
                val share = first / (first - second)
                add(x = ax + (bx - ax) * share, y = ay + (by - ay) * share)
            }
        }
    }

    fun reachSquared(x: Float, y: Float): Float {
        var farthest = 0f
        for (index in 0 until size) {
            val dx = points[index * 2] - x
            val dy = points[index * 2 + 1] - y
            farthest = maxOf(a = farthest, b = dx * dx + dy * dy)
        }
        return farthest
    }

    private fun add(x: Float, y: Float) {
        if (points.size < size * 2 + 2) points = points.copyOf(newSize = points.size * 2)
        points[size * 2] = x
        points[size * 2 + 1] = y
        size++
    }
}

private const val INITIAL: Int = 16
