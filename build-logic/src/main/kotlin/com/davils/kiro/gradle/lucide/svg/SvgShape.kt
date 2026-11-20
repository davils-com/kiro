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

internal sealed interface SvgShape {
    val isFilled: Boolean

    data class Path(val data: String, override val isFilled: Boolean) : SvgShape

    data class Circle(val cx: Double, val cy: Double, val r: Double, override val isFilled: Boolean) : SvgShape

    data class Ellipse(
        val cx: Double,
        val cy: Double,
        val rx: Double,
        val ry: Double,
        override val isFilled: Boolean,
    ) : SvgShape

    data class Rect(
        val x: Double,
        val y: Double,
        val width: Double,
        val height: Double,
        val rx: Double?,
        val ry: Double?,
        override val isFilled: Boolean,
    ) : SvgShape

    data class Line(
        val x1: Double,
        val y1: Double,
        val x2: Double,
        val y2: Double,
        override val isFilled: Boolean,
    ) : SvgShape

    data class Polyline(val points: List<Double>, override val isFilled: Boolean) : SvgShape

    data class Polygon(val points: List<Double>, override val isFilled: Boolean) : SvgShape
}
