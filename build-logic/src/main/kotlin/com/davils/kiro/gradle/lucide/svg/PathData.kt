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

package com.davils.kiro.gradle.lucide.svg

import java.math.BigDecimal
import java.math.RoundingMode

private const val DECIMALS: Int = 3

internal fun SvgShape.toPathData(): String = when (this) {
    is SvgShape.Path -> data
    is SvgShape.Circle -> ellipseData(cx = cx, cy = cy, rx = r, ry = r)
    is SvgShape.Ellipse -> ellipseData(cx = cx, cy = cy, rx = rx, ry = ry)
    is SvgShape.Rect -> rectData(this)
    is SvgShape.Line -> "M ${format(x1)} ${format(y1)} L ${format(x2)} ${format(y2)}"
    is SvgShape.Polyline -> pointsData(points)
    is SvgShape.Polygon -> "${pointsData(points)} Z"
}

internal fun format(value: Double): String {
    val rounded = BigDecimal.valueOf(value).setScale(DECIMALS, RoundingMode.HALF_UP)
    return rounded.stripTrailingZeros().toPlainString()
}

private fun ellipseData(cx: Double, cy: Double, rx: Double, ry: Double): String {
    val radii = "${format(rx)} ${format(ry)}"
    val start = "M ${format(cx - rx)} ${format(cy)}"
    val firstHalf = "A $radii 0 1 0 ${format(cx + rx)} ${format(cy)}"
    val secondHalf = "A $radii 0 1 0 ${format(cx - rx)} ${format(cy)}"
    return "$start $firstHalf $secondHalf Z"
}

private fun rectData(rect: SvgShape.Rect): String {
    val rx = (rect.rx ?: rect.ry ?: 0.0).coerceAtMost(rect.width / 2)
    val ry = (rect.ry ?: rect.rx ?: 0.0).coerceAtMost(rect.height / 2)
    val isSquare = rx == 0.0 || ry == 0.0
    if (isSquare) return squareRectData(rect)
    return roundedRectData(rect = rect, rx = rx, ry = ry)
}

private fun squareRectData(rect: SvgShape.Rect): String {
    val start = "M ${format(rect.x)} ${format(rect.y)}"
    return "$start h ${format(rect.width)} v ${format(rect.height)} h ${format(-rect.width)} Z"
}

private fun roundedRectData(rect: SvgShape.Rect, rx: Double, ry: Double): String {
    val right = rect.x + rect.width
    val bottom = rect.y + rect.height
    val corner = "A ${format(rx)} ${format(ry)} 0 0 1"
    return listOf(
        "M ${format(rect.x + rx)} ${format(rect.y)}",
        "H ${format(right - rx)}",
        "$corner ${format(right)} ${format(rect.y + ry)}",
        "V ${format(bottom - ry)}",
        "$corner ${format(right - rx)} ${format(bottom)}",
        "H ${format(rect.x + rx)}",
        "$corner ${format(rect.x)} ${format(bottom - ry)}",
        "V ${format(rect.y + ry)}",
        "$corner ${format(rect.x + rx)} ${format(rect.y)}",
        "Z",
    ).joinToString(" ")
}

private fun pointsData(points: List<Double>): String {
    val pairs = points.chunked(2) { (x, y) -> "${format(x)} ${format(y)}" }
    return "M ${pairs.first()} L ${pairs.drop(1).joinToString(" ")}"
}
