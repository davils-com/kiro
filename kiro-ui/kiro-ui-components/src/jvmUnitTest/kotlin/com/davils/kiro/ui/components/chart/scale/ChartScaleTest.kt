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

package com.davils.kiro.ui.components.chart.scale

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HUNDRED: Double = 100.0
private const val QUARTER: Double = 2.5
private const val FIFTY: Double = 50.0
private const val TEN: Double = 10.0
private const val THREE: Int = 3
private const val FOUR: Int = 4
private const val BAND: Float = 100f
private const val SPAN: Float = 400f
private const val LAST: Int = 3
private const val MIDDLE: Float = 150f

private fun steps(size: Int, step: Double, from: Double = 0.0): List<Double> = List(
    size,
) { index -> from + index * step }

class ChartScaleTest : FunSpec({
    context("ticks") {
        test("round the domain to nice steps starting at zero") {
            ChartTicks.nice(min = 0.0, max = 305.0, count = 5) shouldBe steps(size = 5, step = HUNDRED)
            ChartTicks.nice(min = 0.0, max = 9.0, count = 5) shouldBe steps(size = 5, step = QUARTER)
        }

        test("include negative values and fall back to a unit domain") {
            ChartTicks.nice(
                min = -40.0,
                max = 60.0,
                count = THREE,
            ) shouldBe steps(size = FOUR, step = FIFTY, from = -FIFTY)
            ChartTicks.nice(min = 0.0, max = 0.0, count = 5) shouldBe listOf(0.0, 1.0)
            ChartTicks.nice(min = 0.0, max = TEN, count = 1) shouldBe listOf(0.0, TEN)
        }
    }

    context("format") {
        test("rounds a value to cents and drops the zeros of the fraction") {
            ChartFormat.number(value = 12.5) shouldBe "12.5"
            ChartFormat.number(value = -0.254) shouldBe "-0.25"
            ChartFormat.number(value = 1.005) shouldBe "1"
            ChartFormat.number(value = -0.001) shouldBe "0"
            ChartFormat.number(value = 3.07) shouldBe "3.07"
        }
    }

    context("linear scale") {
        test("maps the domain onto the range and survives an empty domain") {
            val scale = LinearScale(domainStart = 0.0, domainEnd = HUNDRED, rangeStart = 200f, rangeEnd = 0f)
            val flat = LinearScale(domainStart = 5.0, domainEnd = 5.0, rangeStart = 10f, rangeEnd = 0f)

            scale.map(value = 25.0) shouldBe MIDDLE
            flat.map(value = 5.0) shouldBe TEN.toFloat()
        }
    }

    context("category scale") {
        test("centers bands and finds the nearest band") {
            val scale = CategoryScale(count = FOUR, start = 0f, end = SPAN, isBand = true)

            scale.bandwidth shouldBe BAND
            scale.center(index = 1) shouldBe MIDDLE
            scale.indexAt(position = 330f) shouldBe LAST
            scale.indexAt(position = -50f) shouldBe 0
        }

        test("spreads points from edge to edge and centers a single point") {
            val points = CategoryScale(count = THREE, start = 0f, end = 200f, isBand = false)
            val single = CategoryScale(count = 1, start = 0f, end = 200f, isBand = false)

            points.bandwidth shouldBe 0f
            points.center(index = 2) shouldBe 2 * BAND
            points.indexAt(position = 60f) shouldBe 1
            single.center(index = 0) shouldBe BAND
            CategoryScale(count = 0, start = 0f, end = 200f, isBand = true).indexAt(position = 10f) shouldBe null
            CategoryScale(count = 2, start = 10f, end = 10f, isBand = false).indexAt(position = 10f) shouldBe 0
        }
    }
})
