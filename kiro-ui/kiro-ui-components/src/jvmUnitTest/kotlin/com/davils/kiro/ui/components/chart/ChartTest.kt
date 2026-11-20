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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Monitor
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val JANUARY: String = "January"
private const val FEBRUARY: String = "February"
private const val MARCH: String = "March"
private const val SAFARI: String = "safari"
private const val STACKED: String = "stacked"
private const val ASPECT: Float = 16f / 9f

private val desktop = ChartKey("desktop")
private val mobile = ChartKey("mobile")
private val chrome = ChartKey("chrome")

private val config = ChartConfig(
    listOf(
        ChartSeries(desktop, "Desktop", icon = Lucide.Monitor),
        ChartSeries(mobile, "Mobile"),
        ChartSeries(chrome, "Chrome"),
    ),
)

private val months: List<ChartPoint> = listOf(
    ChartPoint(JANUARY, mapOf(desktop to 186.0, mobile to 80.0)),
    ChartPoint(FEBRUARY, mapOf(desktop to 305.0, mobile to 200.0)),
    ChartPoint(MARCH, mapOf(desktop to 237.0, mobile to 120.0)),
)

private val browsers: List<ChartPoint> = listOf(
    ChartPoint("chrome", mapOf(chrome to 275.0)),
    ChartPoint(SAFARI, mapOf(chrome to 200.0)),
)

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.chart(theme: Theme = reducedMotion, content: @Composable () -> Unit) {
    setContent {
        Themed(theme = theme) {
            Box(modifier = Modifier.size(width = 480.dp, height = 320.dp)) {
                ChartContainer(config = config, modifier = Modifier.testTag(TAG)) { content() }
            }
        }
    }
}

@OptIn(ExperimentalTestApi::class)
private fun ComposeUiTest.hover(fractionX: Float, fractionY: Float) {
    onNodeWithTag(TAG).performMouseInput {
        moveTo(Offset(x = width * fractionX, y = height * fractionY))
    }
    waitForIdle()
}

@OptIn(ExperimentalTestApi::class)
class ChartTest : FunSpec({
    context("styles") {
        test("use the chart colors, text-xs and a video aspect ratio") {
            val light = Theme.light()
            val style = ChartStyles.Default.resolve(light)

            style.palette.first() shouldBe light.colors.chart.first
            style.gridColor shouldBe light.colors.border.copy(alpha = 0.5f)
            style.aspectRatio shouldBe ASPECT
            style.tooltip.minWidth shouldBe 128.dp
        }
    }

    context("container") {
        test("is required by every chart") {
            shouldThrow<IllegalArgumentException> {
                runComposeUiTest {
                    setContent { Themed { LineChart(data = months) { line(desktop) } } }
                }
            }
        }
    }

    context("bar chart") {
        test("shows a tooltip with every series and a legend") {
            runComposeUiTest {
                chart {
                    BarChart(data = months) {
                        grid(isVertical = true, isDashed = true)
                        categoryAxis(format = { label -> label.take(n = 3) })
                        valueAxis()
                        tooltip()
                        legend()
                        bar(desktop, radius = 4.dp)
                        bar(mobile, radius = 4.dp)
                    }
                }

                onNodeWithText("Mobile").assertExists()
                hover(fractionX = 0.5f, fractionY = 0.5f)
                onNodeWithText(FEBRUARY).assertExists()
                onNodeWithText("305").assertExists()
            }
        }

        test("stacks horizontal bars and hides the tooltip when the pointer leaves") {
            runComposeUiTest {
                chart(theme = Theme.dark()) {
                    BarChart(data = months, orientation = Orientation.Horizontal, isStacked = true) {
                        grid()
                        categoryAxis()
                        valueAxis(tickCount = 3)
                        tooltip(indicator = ChartTooltipIndicator.Line, isIndicatorHidden = true)
                        legend(position = ChartLegendPosition.Top, isIconHidden = true)
                        bar(desktop)
                        bar(mobile)
                    }
                }

                onNodeWithText("Desktop").assertExists()
                hover(fractionX = 0.9f, fractionY = 0.9f)
                onNodeWithText(MARCH).assertExists()
                onNodeWithText("237").assertExists()
                onNodeWithTag(TAG).performMouseInput { exit() }
                waitForIdle()
                onNodeWithText(MARCH).assertDoesNotExist()
            }
        }
    }

    context("line and area charts") {
        test("draw every curve with dots and an active dot") {
            runComposeUiTest {
                chart {
                    LineChart(data = months) {
                        categoryAxis()
                        tooltip(indicator = ChartTooltipIndicator.Dashed)
                        ChartCurve.entries.forEach { curve -> line(desktop, curve = curve, isDotShown = true) }
                    }
                }

                hover(fractionX = 0.05f, fractionY = 0.5f)
                onNodeWithText(JANUARY).assertExists()
            }
        }

        test("nest the label for a single dashed series") {
            runComposeUiTest {
                chart {
                    AreaChart(data = months) {
                        tooltip(indicator = ChartTooltipIndicator.Dashed)
                        area(mobile, isGradient = true)
                    }
                }

                hover(fractionX = 0.95f, fractionY = 0.5f)
                onNodeWithText(MARCH).assertExists()
                onNodeWithText("120").assertExists()
            }
        }

        test("stack areas and animate them in") {
            runComposeUiTest {
                chart(theme = Theme.light()) {
                    AreaChart(data = months, isStacked = true) {
                        grid()
                        valueAxis()
                        tooltip(isLabelHidden = true)
                        legend()
                        area(desktop, curve = ChartCurve.Step)
                        area(mobile, curve = ChartCurve.Linear)
                    }
                }

                hover(fractionX = 0.5f, fractionY = 0.5f)
                onNodeWithText(FEBRUARY).assertDoesNotExist()
                onNodeWithText("200").assertExists()
            }
        }
    }

    context("polar charts") {
        test("show the slice under the pointer of a donut") {
            runComposeUiTest {
                chart {
                    PieChart(
                        data = browsers,
                        key = chrome,
                        innerRadius = 40.dp,
                        paddingAngle = 2f,
                        isLabelShown = true,
                    ) {
                        tooltip()
                        legend()
                    }
                }

                onNodeWithText("Chrome").assertExists()
                onNodeWithText(SAFARI).assertExists()
                onNode(SemanticsMatcher.keyIsDefined(SemanticsActions.RequestFocus)).requestFocus()
                onNode(
                    SemanticsMatcher.keyIsDefined(SemanticsActions.RequestFocus),
                ).performKeyInput { pressKey(Key.DirectionLeft) }
                waitForIdle()
                onNodeWithText("200").assertExists()
            }
        }

        test("walk around a radar with the arrow keys") {
            runComposeUiTest {
                chart {
                    RadarChart(data = months) {
                        grid()
                        angleAxis()
                        tooltip()
                        radar(desktop, strokeWidth = 1.dp, isDotShown = true)
                        radar(mobile)
                    }
                }

                hover(fractionX = 0.5f, fractionY = 0.1f)
                onNodeWithText(JANUARY).assertExists()
                onNode(SemanticsMatcher.keyIsDefined(SemanticsActions.RequestFocus)).requestFocus()
                onNode(
                    SemanticsMatcher.keyIsDefined(SemanticsActions.RequestFocus),
                ).performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                onNodeWithText(FEBRUARY).assertExists()
            }
        }

        test("draw circular radar grids without spokes") {
            runComposeUiTest {
                chart {
                    RadarChart(data = months) {
                        grid(isCircle = true, isSpokeShown = false)
                        radar(desktop)
                    }
                }

                onNodeWithTag(TAG).assertExists()
            }
        }

        test("color radial rings by row or stack them by series") {
            runComposeUiTest {
                chart {
                    Box {
                        RadialBarChart(data = browsers, modifier = Modifier.size(200.dp), endAngle = 250f) {
                            tooltip()
                            legend()
                            radialBar(chrome, isBackgroundShown = true, cornerRadius = 10.dp)
                        }
                        RadialBarChart(
                            data = months.take(1),
                            modifier = Modifier.size(200.dp).testTag(STACKED),
                            outerRadius = 90.dp,
                        ) {
                            tooltip()
                            radialBar(desktop)
                            radialBar(mobile)
                        }
                    }
                }

                onNodeWithText(SAFARI).assertExists()
                onNodeWithTag(STACKED).performMouseInput { moveTo(center + Offset(x = 0f, y = 70f)) }
                waitForIdle()
                onNodeWithText(JANUARY).assertExists()
            }
        }
    }
})
