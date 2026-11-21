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

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val DEGREES: Float = 180f
private const val X: Int = 0
private const val Y: Int = 1
private const val Z: Int = 2
private const val AXES: Int = 3

internal fun lightOf(azimuth: Float, elevation: Float, light: FloatArray) {
    val tilt = elevation / DEGREES * PI.toFloat()
    light[X] = cos(azimuth) * cos(tilt)
    light[Y] = sin(azimuth) * cos(tilt)
    light[Z] = sin(tilt)
}

internal fun shadeOf(points: FloatArray, first: Int, second: Int, third: Int, light: FloatArray): Float {
    val ux = points[second * AXES + X] - points[first * AXES + X]
    val uy = points[second * AXES + Y] - points[first * AXES + Y]
    val uz = points[second * AXES + Z] - points[first * AXES + Z]
    val vx = points[third * AXES + X] - points[first * AXES + X]
    val vy = points[third * AXES + Y] - points[first * AXES + Y]
    val vz = points[third * AXES + Z] - points[first * AXES + Z]
    val nx = uy * vz - uz * vy
    val ny = uz * vx - ux * vz
    val nz = ux * vy - uy * vx
    val length = sqrt(nx * nx + ny * ny + nz * nz)
    if (length <= 0f) return 0f
    val facing = (nx * light[X] + ny * light[Y] + nz * light[Z]) / length
    val upward = facing.takeIf { nz >= 0f } ?: -facing
    return upward - light[Z]
}
