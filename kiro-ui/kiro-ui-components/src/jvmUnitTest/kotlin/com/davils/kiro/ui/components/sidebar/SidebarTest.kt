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

package com.davils.kiro.ui.components.sidebar

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.requestFocus
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.test.withKeyDown
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Plus
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val HOME: String = "Home"
private const val INBOX: String = "Inbox"
private const val TOGGLE: String = "Toggle Sidebar"

@OptIn(ExperimentalTestApi::class)
class SidebarTest : FunSpec({
    context("styles") {
        test("are 16rem wide, 3rem as icons and use the sidebar colors") {
            val light = Theme.light()
            val style = SidebarStyles.Default.resolve(light)

            style.width shouldBe 256.dp
            style.iconWidth shouldBe 48.dp
            style.container shouldBe light.colors.sidebar.background
            style.accent shouldBe light.colors.sidebar.accent
        }
    }

    context("state") {
        test("toggles and opens on request") {
            val state = SidebarState(isInitiallyOpen = false)

            state.toggle()
            state.isOpen shouldBe true
            state.show(isOpen = false)
            state.isOpen shouldBe false
        }
    }

    context("sidebar") {
        test("shows its parts and slides away with the trigger") {
            val state = SidebarState()
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        SidebarProvider(state = state) {
                            Sidebar(modifier = Modifier.testTag(TAG), isRailShown = true) {
                                SidebarHeader { SidebarInput(value = "", onValueChange = {}, placeholder = "Search") }
                                SidebarSeparator()
                                SidebarContent {
                                    SidebarGroup(
                                        action = {
                                            SidebarGroupAction(
                                                onClick = {},
                                                icon = Lucide.Plus,
                                                contentDescription = "Add",
                                            )
                                        },
                                    ) {
                                        SidebarGroupLabel(text = "Application")
                                        SidebarGroupContent {
                                            SidebarMenu {
                                                SidebarMenuItem {
                                                    SidebarMenuButton(
                                                        onClick = { clicked = HOME },
                                                        isActive = true,
                                                        tooltip = HOME,
                                                    ) {
                                                        Icon(
                                                            icon = Lucide.User,
                                                            placement = IconPlacement.Inline,
                                                        )
                                                        Text(HOME)
                                                    }
                                                    SidebarMenuBadge(text = "24")
                                                    SidebarMenuAction(
                                                        onClick = {},
                                                        icon = Lucide.Plus,
                                                        contentDescription = "More",
                                                    )
                                                }
                                                SidebarMenuItem {
                                                    SidebarMenuButton(
                                                        onClick = { clicked = INBOX },
                                                        isOutlined = true,
                                                        size = SidebarMenuButtonSize.Sm,
                                                    ) {
                                                        Text(INBOX)
                                                    }
                                                }
                                                SidebarMenuSkeleton(isIconShown = true)
                                            }
                                        }
                                    }
                                }
                                SidebarFooter { Text("Footer") }
                            }
                            SidebarInset { SidebarTrigger() }
                        }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(256.dp)
                onNodeWithText(INBOX).performMouseInput { moveTo(center) }
                onNodeWithText(INBOX).performClick()
                onNodeWithText(HOME).performClick()
                val toggles = onAllNodesWithContentDescription(TOGGLE)
                toggles[1].performClick()
                waitForIdle()
                state.isOpen shouldBe false
                toggles[0].performMouseInput { moveTo(center) }
                toggles[0].performClick()
                waitForIdle()
            }

            clicked shouldBe HOME
            state.isOpen shouldBe true
        }

        test("collapses to icons with the shortcut and shows tooltips") {
            val state = SidebarState()

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        SidebarProvider(state = state, variant = SidebarVariant.Floating) {
                            Sidebar(
                                modifier = Modifier.testTag(TAG),
                                collapsible = SidebarCollapsible.Icon,
                                side = Side.End,
                            ) {
                                SidebarGroup {
                                    SidebarGroupLabel(text = "Group")
                                    SidebarMenu {
                                        SidebarMenuItem {
                                            SidebarMenuButton(onClick = {}, tooltip = HOME) {
                                                Icon(icon = Lucide.User, placement = IconPlacement.Inline)
                                                Text(HOME)
                                            }
                                            SidebarMenuBadge(text = "1")
                                            SidebarMenuAction(
                                                onClick = {},
                                                icon = Lucide.Plus,
                                                contentDescription = "More",
                                            )
                                        }
                                        SidebarBrand(title = "Hidden", logo = Lucide.User)
                                    }
                                }
                            }
                            SidebarInset {
                                Button(
                                    onClick = {},
                                    modifier = Modifier.testTag("focus"),
                                ) { Text("Focus") }
                            }
                        }
                    }
                }

                val focus = onNodeWithTag("focus")
                focus.requestFocus()
                focus.performKeyInput { withKeyDown(Key.CtrlLeft) { pressKey(Key.B) } }
                waitForIdle()
                state.isOpen shouldBe false
                onNodeWithTag(TAG).assertWidthIsEqualTo(64.dp)
                onNodeWithText("Hidden").assertDoesNotExist()
                onNodeWithText(HOME).performMouseInput { moveTo(center) }
                waitForIdle()
            }
        }

        test("lays out the inset variant and a fixed sidebar") {
            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        SidebarProvider(
                            state = SidebarState(isInitiallyOpen = false),
                            variant = SidebarVariant.Inset,
                        ) {
                            Sidebar(
                                modifier = Modifier.testTag(TAG),
                                collapsible = SidebarCollapsible.None,
                            ) { Text("Fixed") }
                            SidebarInset { Text("Main") }
                        }
                    }
                }

                onNodeWithTag(TAG).assertWidthIsEqualTo(256.dp)
                onNodeWithText("Main").assertExists()
            }
        }
    }
})
