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

package com.davils.kiro.ui.audio

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

internal fun Path.smoothThrough(points: List<Offset>) {
    val first = points.firstOrNull() ?: return
    moveTo(x = first.x, y = first.y)
    for (index in 1 until points.size) {
        val before = points[index - 1]
        val point = points[index]
        val middle = (before + point) / 2f
        quadraticTo(x1 = before.x, y1 = before.y, x2 = middle.x, y2 = middle.y)
    }
    val last = points.last()
    lineTo(x = last.x, y = last.y)
}

internal fun Path.straightThrough(points: List<Offset>) {
    val first = points.firstOrNull() ?: return
    moveTo(x = first.x, y = first.y)
    for (index in 1 until points.size) {
        lineTo(x = points[index].x, y = points[index].y)
    }
}

internal fun Path.stepsThrough(points: List<Offset>, width: Float) {
    val first = points.firstOrNull() ?: return
    val half = width / 2f
    moveTo(x = first.x - half, y = first.y)
    points.forEach { point ->
        lineTo(x = point.x - half, y = point.y)
        lineTo(x = point.x + half, y = point.y)
    }
}
