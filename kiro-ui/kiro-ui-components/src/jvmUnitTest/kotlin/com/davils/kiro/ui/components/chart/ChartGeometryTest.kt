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

package com.davils.kiro.ui.components.chart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.components.chart.cartesian.CartesianStacks
import com.davils.kiro.ui.components.chart.cartesian.ChartSpan
import com.davils.kiro.ui.components.chart.cartesian.at
import com.davils.kiro.ui.components.chart.frame.ChartPalette
import com.davils.kiro.ui.components.chart.polar.PieGeometry
import com.davils.kiro.ui.components.chart.polar.PolarMath
import com.davils.kiro.ui.components.chart.polar.RadarGeometry
import com.davils.kiro.ui.components.chart.polar.RadialGeometry
import com.davils.kiro.ui.components.chart.spec.CartesianSpec
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val TOLERANCE: Float = 0.01f
private const val TABLET: String = "tablet"
private const val SIDE: Float = 200f
private const val CENTER: Float = 100f
private const val GAP_SWEEP: Float = 170f
private const val HALF_TURN: Float = 180f
private const val QUARTER_TURN: Float = 90f
private const val RADAR_BOTTOM: Float = 150f
private const val RING: Float = 20f
private const val THREE: Double = 3.0
private const val FIVE: Double = 5.0

private val desktop = ChartKey("desktop")
private val mobile = ChartKey("mobile")
private val square = Size(width = SIDE, height = SIDE)

class ChartGeometryTest : FunSpec({
    context("stacks") {
        test("start every series at zero unless they are stacked") {
            val data = listOf(ChartPoint(label = "Jan", values = mapOf(desktop to THREE, mobile to 2.0)))
            val keys = listOf(desktop, mobile)

            CartesianStacks.of(data = data, keys = keys, isStacked = false) shouldBe
                listOf(listOf(ChartSpan(start = 0.0, end = THREE)), listOf(ChartSpan(start = 0.0, end = 2.0)))
            CartesianStacks.of(data = data, keys = keys, isStacked = true) shouldBe
                listOf(listOf(ChartSpan(start = 0.0, end = THREE)), listOf(ChartSpan(start = THREE, end = FIVE)))
            ChartSpan(start = 2.0, end = 4.0).at(progress = 0.5f) shouldBe THREE
        }
    }

    context("palette") {
        test("prefers the configured color and falls back to the palette by position") {
            val series = listOf(
                ChartSeries(key = desktop, label = "Desktop"),
                ChartSeries(key = mobile, label = "Mobile", color = Color.Red),
            )
            val config = ChartConfig(series)
            val palette = ChartPalette(config = config, colors = listOf(Color.Blue, Color.Green))

            palette.colorOf(key = desktop, fallback = 5) shouldBe Color.Blue
            palette.colorOf(key = mobile, fallback = 0) shouldBe Color.Red
            palette.colorOf(key = ChartKey(TABLET), fallback = 3) shouldBe Color.Green
            palette.labelOf(ChartKey(TABLET)) shouldBe TABLET
            palette.iconOf(desktop) shouldBe null
            shouldThrow<IllegalArgumentException> { ChartPalette(config = config, colors = emptyList()) }
        }
    }

    context("spec") {
        test("writes the values of an axis and of a tooltip with the numbers of the chart unless told otherwise") {
            val spec = CartesianSpec(numbers = { value -> "<$value>" })
            spec.valueAxis()
            spec.tooltip()
            val regional = listOf(spec.axes.value(1.0), tooltipOf(spec))
            spec.valueAxis(format = Double::toString)
            spec.tooltip(format = Double::toString)

            regional shouldBe listOf("<1.0>", "<1.0>")
            listOf(spec.axes.value(1.0), tooltipOf(spec)) shouldBe listOf("1.0", "1.0")
        }

        test("needs two ticks on a value axis") {
            val spec = CartesianSpec(numbers = Double::toString)

            shouldThrow<IllegalArgumentException> { spec.valueAxis(tickCount = 1) }
        }
    }

    context("pie") {
        test("leaves gaps between slices and finds the slice under the pointer") {
            val pie = PieGeometry.of(
                size = square,
                margin = 0f,
                fraction = 1f,
                inner = 50f,
                padding = 10f,
                values = listOf(1.0, 1.0, 0.0),
            )

            pie.slices.first().sweep shouldBe GAP_SWEEP
            pie.slices[1].start shouldBe HALF_TURN
            pie.indexAt(Offset(x = 170f, y = 90f)) shouldBe 0
            pie.indexAt(Offset(x = 30f, y = 110f)) shouldBe 1
            pie.indexAt(Offset(x = CENTER, y = CENTER)) shouldBe null
            pie.indexAt(Offset(x = 170f, y = CENTER)) shouldBe 0
        }

        test("draws nothing for an empty pie") {
            val empty = PieGeometry.of(
                size = square,
                margin = 0f,
                fraction = 1f,
                inner = 0f,
                padding = 0f,
                values = listOf(0.0),
            )

            empty.slices.first().sweep shouldBe 0f
        }
    }

    context("radar") {
        test("finds the nearest spoke from the top clockwise") {
            val radar = RadarGeometry.of(
                size = square,
                margin = 0f,
                fraction = 1f,
                count = 4,
                ticks = listOf(0.0, 10.0),
            )

            radar.indexAt(Offset(x = CENTER, y = 0f)) shouldBe 0
            radar.indexAt(Offset(x = SIDE, y = CENTER)) shouldBe 1
            radar.pointOf(index = 2, value = FIVE).y shouldBe (RADAR_BOTTOM plusOrMinus TOLERANCE)
        }

        test("handles no spokes and a zero domain") {
            val small = Size(width = 10f, height = 10f)
            val empty = RadarGeometry.of(size = small, margin = 0f, fraction = 1f, count = 0, ticks = listOf(0.0))
            val flat = RadarGeometry.of(size = small, margin = 0f, fraction = 1f, count = 1, ticks = listOf(0.0))

            empty.indexAt(Offset.Zero) shouldBe null
            flat.pointOf(index = 0, value = 1.0).y shouldBe (0f plusOrMinus TOLERANCE)
        }
    }

    context("radial") {
        test("finds the ring under the pointer") {
            val radial = RadialGeometry.of(
                size = square,
                margin = 0f,
                fraction = 1f,
                inner = 20f,
                outer = 80f,
                count = 3,
                padding = 0.1f,
                angles = 0f to HALF_TURN,
            )

            radial.band shouldBe RING
            radial.indexAt(Offset(x = CENTER, y = 30f)) shouldBe 2
            radial.indexAt(Offset(x = CENTER, y = CENTER)) shouldBe null
            radial.degreesOf(fraction = 0.5f) shouldBe QUARTER_TURN
        }

        test("finds nothing without rows") {
            val empty = RadialGeometry.of(
                size = Size.Zero,
                margin = 0f,
                fraction = 1f,
                inner = 0f,
                outer = null,
                count = 0,
                padding = 0f,
                angles = 0f to 0f,
            )

            empty.indexAt(Offset.Zero) shouldBe null
        }
    }

    context("polar math") {
        test("measures angles counterclockwise from the right") {
            PolarMath.degreesOf(center = Offset.Zero, position = Offset(x = 0f, y = -10f)) shouldBe QUARTER_TURN
            PolarMath.contains(start = 350f, sweep = 20f, degrees = 5f) shouldBe true
            PolarMath.contains(start = 0f, sweep = 20f, degrees = 30f) shouldBe false
        }
    }
})

private fun tooltipOf(spec: CartesianSpec): String {
    val tooltip = checkNotNull(spec.tooltip)
    return tooltip.format(1.0)
}
