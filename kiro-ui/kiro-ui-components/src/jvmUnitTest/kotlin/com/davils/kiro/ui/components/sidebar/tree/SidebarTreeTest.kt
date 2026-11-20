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

package com.davils.kiro.ui.components.sidebar.tree

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.sidebar.Sidebar
import com.davils.kiro.ui.components.sidebar.SidebarCollapsible
import com.davils.kiro.ui.components.sidebar.SidebarContent
import com.davils.kiro.ui.components.sidebar.SidebarProvider
import com.davils.kiro.ui.components.sidebar.SidebarState
import com.davils.kiro.ui.icons.lucide.Folder
import com.davils.kiro.ui.icons.lucide.House
import com.davils.kiro.ui.icons.lucide.Lucide
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HOME: String = "Home"
private const val PROJECTS: String = "Projects"
private const val KIDE: String = "kide"
private const val ARCHIVE: String = "Archive"
private const val LEGACY: String = "legacy"
private const val FILLER: Int = 30
private const val SETTLE: Long = 2_000L

private fun SidebarTreeScope<String>.workspace(onOpen: (String) -> Unit) {
    item(value = HOME, label = HOME, icon = Lucide.House, badge = "3") { onOpen(HOME) }
    sub(value = PROJECTS, label = PROJECTS, icon = Lucide.Folder) {
        item(value = KIDE, label = KIDE) { onOpen(KIDE) }
        sub(value = ARCHIVE, label = ARCHIVE) {
            item(value = LEGACY, label = LEGACY) { onOpen(LEGACY) }
        }
    }
    repeat(FILLER) { index -> item(value = "page $index", label = "Page $index") }
}

@OptIn(ExperimentalTestApi::class)
class SidebarTreeTest : FunSpec({
    context("state") {
        test("expands every ancestor of a selection and toggles branches") {
            val state = SidebarTreeState<String>()
            state.parents = mapOf(KIDE to PROJECTS, LEGACY to ARCHIVE, ARCHIVE to PROJECTS)
            state.branches = setOf(PROJECTS, ARCHIVE)

            state.select(LEGACY)
            state.expanded shouldBe setOf(ARCHIVE, PROJECTS)
            state.toggle(ARCHIVE)
            state.isExpanded(ARCHIVE) shouldBe false
            state.collapseAll()
            state.expanded shouldBe emptySet()
            state.expandAll()
            state.expanded shouldBe setOf(PROJECTS, ARCHIVE)
        }
    }

    context("tree") {
        test("opens a branch with the left arrow and closes it with the right arrow from right to left") {
            val state = SidebarTreeState<String>(selected = KIDE)

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Direction(direction = LayoutDirection.Rtl) {
                            SidebarProvider(
                                state = SidebarState(),
                                modifier = Modifier.size(width = 400.dp, height = 300.dp),
                            ) {
                                Sidebar { SidebarContent { SidebarTree(state = state) { workspace { } } } }
                            }
                        }
                    }
                }

                val archive = onNodeWithText(ARCHIVE)
                archive.requestFocus()
                archive.performKeyInput { pressKey(Key.DirectionLeft) }
                waitForIdle()
                val isOpened = state.isExpanded(ARCHIVE)
                archive.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()

                isOpened shouldBe true
                state.isExpanded(ARCHIVE) shouldBe false
            }
        }

        test("opens nested branches, selects leaves and walks the tree with the keyboard") {
            val state = SidebarTreeState<String>(selected = KIDE)
            var opened = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        SidebarProvider(
                            state = SidebarState(),
                            modifier = Modifier.size(width = 400.dp, height = 300.dp),
                        ) {
                            Sidebar { SidebarContent { SidebarTree(state = state) { workspace { opened = it } } } }
                        }
                    }
                }

                onNodeWithText(KIDE).assertExists()
                onNodeWithText(ARCHIVE).performClick()
                waitForIdle()
                onNodeWithText(LEGACY).performClick()
                waitForIdle()
                state.selected shouldBe LEGACY
                val archive = onNodeWithText(ARCHIVE)
                archive.requestFocus()
                archive.performKeyInput { pressKey(Key.DirectionLeft) }
                waitForIdle()
                state.isExpanded(ARCHIVE) shouldBe false
                archive.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                state.isExpanded(ARCHIVE) shouldBe true
                archive.performKeyInput { pressKey(Key.DirectionRight) }
                archive.performKeyInput { pressKey(Key.DirectionUp) }
                archive.performKeyInput { pressKey(Key.DirectionDown) }
                archive.performKeyInput { pressKey(Key.DirectionLeft) }
                waitForIdle()
                onNodeWithText(HOME).performClick()
            }

            opened shouldBe HOME
        }

        test("collapses to icons with flyout menus for branches") {
            val state = SidebarTreeState<String>()
            var opened = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        SidebarProvider(state = SidebarState(isInitiallyOpen = false)) {
                            Sidebar(collapsible = SidebarCollapsible.Icon) {
                                SidebarContent { SidebarTree(state = state) { workspace { opened = it } } }
                            }
                        }
                    }
                }

                onNodeWithText(HOME).assertDoesNotExist()
                onAllNodes(hasClickAction())[1].performClick()
                waitForIdle()
                onNodeWithText(KIDE).performClick()
                waitForIdle()
            }

            opened shouldBe KIDE
            state.selected shouldBe KIDE
        }

        test("scrolls an expanded branch into view") {
            val state = SidebarTreeState<String>()

            runComposeUiTest {
                setContent {
                    Themed {
                        SidebarProvider(
                            state = SidebarState(),
                            modifier = Modifier.size(width = 400.dp, height = 240.dp),
                        ) {
                            Sidebar {
                                SidebarContent {
                                    SidebarTree(state = state) {
                                        repeat(FILLER) { index -> item(value = "page $index", label = "Page $index") }
                                        sub(value = ARCHIVE, label = ARCHIVE) { item(value = LEGACY, label = LEGACY) }
                                    }
                                }
                            }
                        }
                    }
                }

                onNodeWithText(ARCHIVE).assertIsNotDisplayed()
                runOnIdle { state.expand(ARCHIVE) }
                mainClock.advanceTimeBy(SETTLE)
                waitForIdle()
                onNodeWithText(LEGACY).assertIsDisplayed()
            }
        }
    }
})
