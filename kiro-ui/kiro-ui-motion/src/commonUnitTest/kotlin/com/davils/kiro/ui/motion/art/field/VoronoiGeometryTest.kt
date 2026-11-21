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

import com.davils.kiro.ui.motion.noise.unitOf
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private const val TOLERANCE: Float = 0.001f
private const val SIDE: Float = 100f
private const val LEFT_SEED: Float = 20f
private const val RIGHT_SEED: Float = 60f
private const val MIDDLE: Float = 50f
private const val BISECTOR: Float = 40f
private const val CORNERS: Int = 4
private const val SIDES: Int = 40
private const val RADIUS: Float = 30f
private const val FAR: Float = 1000f
private const val SLACK: Float = 10f
private const val MANY: Int = 60
private const val WIDE: Float = 480f
private const val HIGH: Float = 300f
private const val AREA_SLACK: Float = 0.01f

private fun areaOf(shard: VoronoiShard): Float {
    var twice = 0f
    val points = shard.points
    for (index in 0 until shard.size) {
        val next = (index + 1) % shard.size
        twice += points[index * 2] * points[next * 2 + 1] - points[next * 2] * points[index * 2 + 1]
    }
    return twice / 2f
}

class VoronoiGeometryTest : FunSpec({
    val seeds = floatArrayOf(LEFT_SEED, MIDDLE, RIGHT_SEED, MIDDLE)

    test("finds the nearest seed or none") {
        nearestOf(x = 0f, y = 0f, seeds = seeds, count = 2) shouldBe 0
        nearestOf(x = SIDE, y = SIDE, seeds = seeds, count = 2) shouldBe 1
        nearestOf(x = BISECTOR - 1f, y = MIDDLE, seeds = seeds, count = 2) shouldBe 0
        nearestOf(x = BISECTOR + 1f, y = MIDDLE, seeds = seeds, count = 2) shouldBe 1
        nearestOf(x = 0f, y = 0f, seeds = seeds, count = 0) shouldBe -1
    }

    test("gives a lone seed the whole area") {
        val shard = VoronoiShard()
        shard.reset(left = 0f, top = 0f, right = SIDE, bottom = SIDE)
        shardOf(index = 0, seeds = seeds, count = 1, shard = shard)

        shard.size shouldBe CORNERS
        areaOf(shard) shouldBe (SIDE * SIDE plusOrMinus TOLERANCE)
    }

    test("splits the area between two seeds along their bisector") {
        val left = VoronoiShard()
        left.reset(left = 0f, top = 0f, right = SIDE, bottom = SIDE)
        shardOf(index = 0, seeds = seeds, count = 2, shard = left)
        val right = VoronoiShard()
        right.reset(left = 0f, top = 0f, right = SIDE, bottom = SIDE)
        shardOf(index = 1, seeds = seeds, count = 2, shard = right)

        areaOf(left) shouldBe (BISECTOR * SIDE plusOrMinus TOLERANCE)
        areaOf(right) shouldBe ((SIDE - BISECTOR) * SIDE plusOrMinus TOLERANCE)
        nearestOf(x = left.points[0], y = left.points[1], seeds = seeds, count = 2) shouldBe 0
    }

    test("clips a shard away completely and keeps it empty") {
        val shard = VoronoiShard()
        shard.reset(left = 0f, top = 0f, right = SIDE, bottom = SIDE)
        shard.clip(normalX = 1f, normalY = 0f, limit = -FAR)
        shard.size shouldBe 0
        shard.clip(normalX = 1f, normalY = 0f, limit = FAR)
        shard.size shouldBe 0
        shard.reachSquared(x = 0f, y = 0f) shouldBe 0f
    }

    test("grows to hold a shard with many corners") {
        val shard = VoronoiShard()
        shard.reset(left = 0f, top = 0f, right = SIDE, bottom = SIDE)
        repeat(SIDES) { side ->
            val angle = side * 2 * PI / SIDES
            val normalX = cos(angle).toFloat()
            val normalY = sin(angle).toFloat()
            shard.clip(normalX = normalX, normalY = normalY, limit = normalX * MIDDLE + normalY * MIDDLE + RADIUS)
        }

        shard.size shouldBeGreaterThan SIDES / 2
        shard.reachSquared(x = MIDDLE, y = MIDDLE) shouldBe (RADIUS * RADIUS plusOrMinus SLACK)
    }

    test("tiles the area with shards that each hold the points nearest to their seed") {
        val many = FloatArray(size = MANY * 2) { index ->
            val side = WIDE.takeIf { index % 2 == 0 } ?: HIGH
            unitOf(x = index, seed = 1) * side
        }
        val shard = VoronoiShard()
        var total = 0f
        for (index in 0 until MANY) {
            shard.reset(left = 0f, top = 0f, right = WIDE, bottom = HIGH)
            shardOf(index = index, seeds = many, count = MANY, shard = shard)
            total += areaOf(shard)
            var x = 0f
            var y = 0f
            for (corner in 0 until shard.size) {
                x += shard.points[corner * 2] / shard.size
                y += shard.points[corner * 2 + 1] / shard.size
            }
            nearestOf(x = x, y = y, seeds = many, count = MANY) shouldBe index
        }
        total shouldBe (WIDE * HIGH plusOrMinus WIDE * HIGH * AREA_SLACK)
    }
})
