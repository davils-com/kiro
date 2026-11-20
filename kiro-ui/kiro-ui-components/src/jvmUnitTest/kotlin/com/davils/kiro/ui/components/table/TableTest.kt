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

package com.davils.kiro.ui.components.table

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.getUnclippedBoundsInRoot
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val INVOICE: String = "INV001"
private const val WIDE: Int = 300
private const val THIRDS: Int = 3
private const val SIXTHS: Int = 6
private const val ODD: Int = 101

@OptIn(ExperimentalTestApi::class)
class TableTest : FunSpec({
    context("styles") {
        test("use h-10 heads, p-2 cells and muted hover rows") {
            val light = Theme.light()
            val style = TableStyles.Default.resolve(light)

            style.headHeight shouldBe 40.dp
            style.cellPadding shouldBe 8.dp
            style.hoverColor shouldBe light.colors.muted.copy(alpha = 0.5f)
            style.selectedColor shouldBe light.colors.muted
            style.border.color shouldBe light.colors.border
        }
    }

    context("widths") {
        test("stretch columns in proportion and spread an even share without content") {
            stretched(
                widths = listOf(WIDE / THIRDS, WIDE / SIXTHS),
                minWidth = WIDE,
            ) shouldBe listOf(WIDE * 2 / THIRDS, WIDE / THIRDS)
            stretched(widths = listOf(0, 0), minWidth = ODD) shouldBe listOf(ODD / 2, ODD / 2 + 1)
            stretched(widths = listOf(WIDE), minWidth = WIDE / 2) shouldBe listOf(WIDE)
        }
    }

    context("table") {
        test("aligns its columns across rows and clicks rows") {
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed {
                        Table(modifier = Modifier.width(WIDE.dp)) {
                            header {
                                cell { Text("Invoice", modifier = Modifier.testTag(TAG)) }
                                cell(alignment = Alignment.End) { Text("Amount") }
                            }
                            row(onClick = { clicked = INVOICE }) {
                                cell { Text(INVOICE) }
                                cell(alignment = Alignment.End) { Text("$250.00") }
                            }
                            row(isSelected = true) {
                                cell { Text("INV002") }
                                cell(alignment = Alignment.CenterHorizontally) { Text("$150.00") }
                            }
                            footer {
                                cell(span = 2, alignment = Alignment.End) { Text("Total $400.00") }
                            }
                            caption("A list of your recent invoices.")
                        }
                    }
                }

                val head = onNodeWithText("Invoice").getUnclippedBoundsInRoot()
                val cell = onNodeWithText(INVOICE).getUnclippedBoundsInRoot()
                head.left shouldBe cell.left
                onNodeWithText(INVOICE).performMouseInput { moveTo(center) }
                onNodeWithText(INVOICE).performClick()
                onNodeWithText("A list of your recent invoices.").assertExists()
            }

            clicked shouldBe INVOICE
        }

        test("keeps a row of heads at h-10") {
            runComposeUiTest {
                setContent {
                    Themed {
                        Table(modifier = Modifier.testTag(TAG)) {
                            header { cell { Text("Only") } }
                        }
                    }
                }

                onNodeWithTag(TAG).assertHeightIsEqualTo(40.dp)
            }
        }

        test("rejects cells without a column") {
            shouldThrow<IllegalArgumentException> { TableRowScope().cell(span = 0) {} }
        }
    }
})
