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

package com.davils.kiro.ui.motion.art.geometry

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val GOLDEN: Float = 1.618034f
private const val INVERSE_GOLDEN: Float = 0.618034f
private const val TOLERANCE: Float = 0.001f
private const val NEAREST_EYE: Float = 1.05f
private const val AXES: Int = 3

private val signs: FloatArray = floatArrayOf(-1f, 1f)

internal fun solidVertices(solid: WireframeSolid): FloatArray {
    val points = when (solid) {
        WireframeSolid.Cube -> cube()
        WireframeSolid.Octahedron -> octahedron()
        WireframeSolid.Icosahedron -> icosahedron()
        WireframeSolid.Dodecahedron -> dodecahedron()
    }
    return normalized(points)
}

internal fun solidEdges(vertices: FloatArray): IntArray {
    val count = vertices.size / AXES
    var shortest = Float.MAX_VALUE
    for (first in 0 until count) {
        for (second in first + 1 until count) {
            shortest = minOf(a = shortest, b = gapOf(vertices = vertices, first = first, second = second))
        }
    }
    val edges = ArrayList<Int>()
    for (first in 0 until count) {
        for (second in first + 1 until count) {
            if (gapOf(vertices = vertices, first = first, second = second) - shortest > TOLERANCE) continue
            edges += first
            edges += second
        }
    }
    return edges.toIntArray()
}

internal fun rotateVertices(vertices: FloatArray, yaw: Double, pitch: Double, into: FloatArray) {
    val yawCos = cos(yaw).toFloat()
    val yawSin = sin(yaw).toFloat()
    val pitchCos = cos(pitch).toFloat()
    val pitchSin = sin(pitch).toFloat()
    for (index in 0 until vertices.size / AXES) {
        val x = vertices[index * AXES]
        val y = vertices[index * AXES + 1]
        val z = vertices[index * AXES + 2]
        val turnedZ = z * yawCos - x * yawSin
        into[index * AXES] = x * yawCos + z * yawSin
        into[index * AXES + 1] = y * pitchCos - turnedZ * pitchSin
        into[index * AXES + 2] = y * pitchSin + turnedZ * pitchCos
    }
}

internal fun perspectiveOf(depth: Float, eye: Float): Float {
    val distance = eye.coerceAtLeast(minimumValue = NEAREST_EYE)
    return distance / (distance - depth)
}

internal fun silhouetteOf(eye: Float): Float {
    val distance = eye.coerceAtLeast(minimumValue = NEAREST_EYE)
    return distance / sqrt(distance * distance - 1f)
}

internal fun nearnessOf(depth: Float): Float = ((depth + 1f) / 2f).coerceIn(minimumValue = 0f, maximumValue = 1f)

private fun cube(): List<Float> {
    val points = ArrayList<Float>()
    for (x in signs) for (y in signs) for (z in signs) points += listOf(x, y, z)
    return points
}

private fun octahedron(): List<Float> {
    val points = ArrayList<Float>()
    for (sign in signs) points += listOf(sign, 0f, 0f, 0f, sign, 0f, 0f, 0f, sign)
    return points
}

private fun icosahedron(): List<Float> {
    val points = ArrayList<Float>()
    for (first in signs) {
        for (second in signs) {
            val far = second * GOLDEN
            points += listOf(0f, first, far, first, far, 0f, far, 0f, first)
        }
    }
    return points
}

private fun dodecahedron(): List<Float> {
    val points = ArrayList(cube())
    for (first in signs) {
        for (second in signs) {
            val near = first * INVERSE_GOLDEN
            val far = second * GOLDEN
            points += listOf(0f, near, far, near, far, 0f, far, 0f, near)
        }
    }
    return points
}

private fun normalized(points: List<Float>): FloatArray {
    val length = sqrt(points[0] * points[0] + points[1] * points[1] + points[2] * points[2])
    return FloatArray(points.size) { index -> points[index] / length }
}

private fun gapOf(vertices: FloatArray, first: Int, second: Int): Float {
    val dx = vertices[first * AXES] - vertices[second * AXES]
    val dy = vertices[first * AXES + 1] - vertices[second * AXES + 1]
    val dz = vertices[first * AXES + 2] - vertices[second * AXES + 2]
    return sqrt(dx * dx + dy * dy + dz * dz)
}
