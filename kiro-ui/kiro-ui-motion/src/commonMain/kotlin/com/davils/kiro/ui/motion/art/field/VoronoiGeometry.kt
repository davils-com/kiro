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

internal fun nearestOf(x: Float, y: Float, seeds: FloatArray, count: Int): Int {
    var nearest = -1
    var best = Float.POSITIVE_INFINITY
    for (index in 0 until count) {
        val dx = seeds[index * 2] - x
        val dy = seeds[index * 2 + 1] - y
        val distance = dx * dx + dy * dy
        if (distance < best) {
            best = distance
            nearest = index
        }
    }
    return nearest
}

internal fun shardOf(index: Int, seeds: FloatArray, count: Int, shard: VoronoiShard) {
    val x = seeds[index * 2]
    val y = seeds[index * 2 + 1]
    for (other in 0 until count) {
        val ox = seeds[other * 2]
        val oy = seeds[other * 2 + 1]
        val dx = ox - x
        val dy = oy - y
        val isNear = dx * dx + dy * dy <= shard.reachSquared(x = x, y = y) * 4f
        if (other != index && isNear) {
            shard.clip(normalX = dx, normalY = dy, limit = dx * x + dy * y + (dx * dx + dy * dy) * HALF)
        }
    }
}

private const val HALF: Float = 0.5f
