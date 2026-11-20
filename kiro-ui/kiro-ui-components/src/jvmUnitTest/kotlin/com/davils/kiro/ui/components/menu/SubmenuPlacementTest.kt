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

package com.davils.kiro.ui.components.menu

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.text.Text
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe

private const val SHARE: String = "Share"
private const val EXPORT: String = "Export"
private const val SETTLE: Long = 1_000L
private const val TOLERANCE: Float = 0.5f

@OptIn(ExperimentalTestApi::class)
class SubmenuPlacementTest : FunSpec({
    context("submenu") {
        test("starts at the outer edge of its parent panel instead of overlapping it") {
            val style = defaultMenuStyle(reducedMotion)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        DropdownMenu(expanded = true, onExpandedChange = {}, trigger = { Text("Open") }) {
                            sub(label = SHARE) { item(label = EXPORT, onClick = {}) }
                        }
                    }
                }

                onNodeWithText(SHARE).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                val share = onNodeWithText(SHARE).fetchSemanticsNode().boundsInWindow
                val export = onNodeWithText(EXPORT).fetchSemanticsNode().boundsInWindow
                val parentEdge = share.right + with(density) { (style.panel.padding + style.panel.ring.width).toPx() }
                val subStart = export.left - with(density) { style.subPanel.padding.toPx() }

                subStart shouldBe (parentEdge plusOrMinus TOLERANCE)
            }
        }
    }
})
