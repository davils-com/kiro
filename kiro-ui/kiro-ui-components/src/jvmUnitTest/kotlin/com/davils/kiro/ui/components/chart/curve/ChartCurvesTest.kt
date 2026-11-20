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

package com.davils.kiro.ui.components.chart.curve

import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.components.chart.ChartCurve
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe

private val points: List<Offset> = listOf(
    Offset(x = 0f, y = 50f),
    Offset(x = 50f, y = 10f),
    Offset(x = 100f, y = 30f),
    Offset(x = 150f, y = 30f),
    Offset(x = 150f, y = 60f),
)

private const val END: Float = 150f
private const val RISE: Float = 40f

class ChartCurvesTest : FunSpec({
    context("curves") {
        test("pass through the first and the last point for every curve") {
            ChartCurve.entries.forEach { curve ->
                val bounds = ChartCurves.path(points, curve).getBounds()

                bounds.left shouldBe 0f
                bounds.right shouldBeGreaterThanOrEqual END
            }
        }

        test("draw nothing for no points and a straight segment for two") {
            ChartCurves.path(emptyList(), ChartCurve.Natural).isEmpty shouldBe true
            ChartCurves.path(listOf(Offset(0f, 0f)), ChartCurve.Linear).getBounds().width shouldBe 0f
            ChartCurves.path(points.take(2), ChartCurve.Natural).getBounds().height shouldBe RISE
        }
    }
})
