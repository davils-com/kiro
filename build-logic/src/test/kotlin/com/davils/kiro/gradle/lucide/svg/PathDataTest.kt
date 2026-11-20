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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PathDataTest : FunSpec({
    context("toPathData") {
        test("keeps the data of a path") {
            SvgShape.Path(data = "M7 7h10v10", isFilled = false).toPathData() shouldBe "M7 7h10v10"
        }

        test("draws a circle as two arcs") {
            val circle = SvgShape.Circle(cx = 12.0, cy = 12.0, r = 2.0, isFilled = false)

            circle.toPathData() shouldBe "M 10 12 A 2 2 0 1 0 14 12 A 2 2 0 1 0 10 12 Z"
        }

        test("draws an ellipse as two arcs") {
            val ellipse = SvgShape.Ellipse(cx = 12.0, cy = 5.0, rx = 9.0, ry = 3.0, isFilled = false)

            ellipse.toPathData() shouldBe "M 3 5 A 9 3 0 1 0 21 5 A 9 3 0 1 0 3 5 Z"
        }

        test("draws a line") {
            SvgShape.Line(x1 = 2.0, y1 = 3.0, x2 = 4.0, y2 = 5.0, isFilled = false).toPathData() shouldBe
                "M 2 3 L 4 5"
        }

        test("draws a polyline open and a polygon closed") {
            val points = listOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0)

            SvgShape.Polyline(points = points, isFilled = false).toPathData() shouldBe "M 1 2 L 3 4 5 6"
            SvgShape.Polygon(points = points, isFilled = false).toPathData() shouldBe "M 1 2 L 3 4 5 6 Z"
        }
    }

    context("rectangles") {
        test("draw sharp corners without a radius") {
            val rect = SvgShape.Rect(
                x = 1.0,
                y = 2.0,
                width = 3.0,
                height = 4.0,
                rx = null,
                ry = null,
                isFilled = false,
            )

            rect.toPathData() shouldBe "M 1 2 h 3 v 4 h -3 Z"
        }

        test("draw round corners and take the missing radius from the other") {
            val rect = SvgShape.Rect(
                x = 0.0,
                y = 0.0,
                width = 10.0,
                height = 6.0,
                rx = 2.0,
                ry = null,
                isFilled = false,
            )

            rect.toPathData() shouldBe
                "M 2 0 H 8 A 2 2 0 0 1 10 2 V 4 A 2 2 0 0 1 8 6 H 2 A 2 2 0 0 1 0 4 V 2 A 2 2 0 0 1 2 0 Z"
        }

        test("take the horizontal radius from the vertical one") {
            val rect = SvgShape.Rect(
                x = 0.0,
                y = 0.0,
                width = 10.0,
                height = 6.0,
                rx = null,
                ry = 1.0,
                isFilled = false,
            )

            rect.toPathData() shouldBe
                "M 1 0 H 9 A 1 1 0 0 1 10 1 V 5 A 1 1 0 0 1 9 6 H 1 A 1 1 0 0 1 0 5 V 1 A 1 1 0 0 1 1 0 Z"
        }

        test("limit the radius to half of each side") {
            val rect = SvgShape.Rect(
                x = 0.0,
                y = 0.0,
                width = 4.0,
                height = 2.0,
                rx = 9.0,
                ry = 9.0,
                isFilled = false,
            )

            rect.toPathData() shouldBe
                "M 2 0 H 2 A 2 1 0 0 1 4 1 V 1 A 2 1 0 0 1 2 2 H 2 A 2 1 0 0 1 0 1 V 1 A 2 1 0 0 1 2 0 Z"
        }

        test("draw sharp corners when one radius is zero") {
            val rect = SvgShape.Rect(
                x = 0.0,
                y = 0.0,
                width = 4.0,
                height = 2.0,
                rx = 1.0,
                ry = 0.0,
                isFilled = false,
            )

            rect.toPathData() shouldBe "M 0 0 h 4 v 2 h -4 Z"
        }
    }

    context("format") {
        test("writes whole numbers without a fraction") {
            format(value = 24.0) shouldBe "24"
        }

        test("rounds to three decimals and drops trailing zeros") {
            format(value = 1.23456) shouldBe "1.235"
            format(value = 0.5) shouldBe "0.5"
        }

        test("writes negative zero as zero") {
            format(value = -0.0) shouldBe "0"
        }
    }
})
