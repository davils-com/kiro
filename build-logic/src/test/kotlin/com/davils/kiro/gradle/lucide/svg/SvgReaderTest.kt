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

import com.davils.kiro.gradle.lucide.fixture.ARROW_SVG
import com.davils.kiro.gradle.lucide.fixture.svgOf
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class SvgReaderTest : FunSpec({
    context("shapes") {
        test("reads paths and the fill of a shape") {
            SvgReader.read(ARROW_SVG) shouldBe listOf(
                SvgShape.Path(data = "M7 7h10v10", isFilled = false),
                SvgShape.Circle(cx = 12.0, cy = 12.0, r = 2.0, isFilled = true),
            )
        }

        test("reads ellipses and lines") {
            val svg = svgOf(
                """<ellipse cx="12" cy="5" rx="9" ry="3" />""",
                """<line x1="2" y1="3" x2="4" y2="5" fill="none" />""",
            )

            SvgReader.read(svg) shouldBe listOf(
                SvgShape.Ellipse(cx = 12.0, cy = 5.0, rx = 9.0, ry = 3.0, isFilled = false),
                SvgShape.Line(x1 = 2.0, y1 = 3.0, x2 = 4.0, y2 = 5.0, isFilled = false),
            )
        }

        test("reads rectangles with and without a position and radius") {
            val svg = svgOf(
                """<rect width="18" height="11" x="3" y="11" rx="2" ry="1" />""",
                """<rect width="4" height="4" />""",
            )

            SvgReader.read(svg) shouldBe listOf(
                SvgShape.Rect(x = 3.0, y = 11.0, width = 18.0, height = 11.0, rx = 2.0, ry = 1.0, isFilled = false),
                SvgShape.Rect(x = 0.0, y = 0.0, width = 4.0, height = 4.0, rx = null, ry = null, isFilled = false),
            )
        }

        test("reads the points of polylines and polygons") {
            val svg = svgOf(
                """<polyline points="1,2 3,4" />""",
                """<polygon points=" 5 6 7 8 9 10 " />""",
            )
            val polyline = listOf(1.0, 2.0, 3.0, 4.0)
            val polygon = listOf(5.0, 6.0, 7.0, 8.0, 9.0, 10.0)

            SvgReader.read(svg) shouldBe listOf(
                SvgShape.Polyline(points = polyline, isFilled = false),
                SvgShape.Polygon(points = polygon, isFilled = false),
            )
        }
    }

    context("refuses") {
        test("an icon drawn on another view box") {
            val svg = """<svg viewBox="0 0 16 16"><path d="M1 1" /></svg>"""

            shouldThrow<IllegalArgumentException> { SvgReader.read(svg) }.message shouldContain "0 0 24 24"
        }

        test("an element it cannot draw") {
            shouldThrow<IllegalArgumentException> { SvgReader.read(svgOf("<text>A</text>")) }
                .message shouldContain "<text>"
        }

        test("a shape without a required attribute") {
            shouldThrow<IllegalArgumentException> { SvgReader.read(svgOf("""<circle cx="1" cy="1" />""")) }
                .message shouldContain "r"
        }

        test("points that do not come in pairs") {
            shouldThrow<IllegalArgumentException> { SvgReader.read(svgOf("""<polyline points="1 2 3" />""")) }
                .message shouldContain "pairs"
        }

        test("a document type declaration, which could pull in external entities") {
            val svg = """<!DOCTYPE svg [<!ENTITY x "y">]><svg viewBox="0 0 24 24" />"""

            shouldThrowAny { SvgReader.read(svg) }
        }
    }
})
