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

package com.davils.kiro.ui.components.dropdownmenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isFocused
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.direction.Direction
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.menu.MenuItemVariant
import com.davils.kiro.ui.components.menu.MenuStyles
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val OPEN: String = "Open"
private const val PROFILE: String = "Profile"
private const val INVITE: String = "Invite users"
private const val EMAIL: String = "Email"
private const val INTENT: Long = 500L
private const val MORE: String = "More"
private const val TEAMS: String = "Teams"

@OptIn(ExperimentalTestApi::class)
class DropdownMenuTest : FunSpec({
    context("styles") {
        test("are p-1 popover panels with rounded-md items on the accent color") {
            val light = Theme.light()
            val style = MenuStyles.Default.resolve(light)

            style.panel.padding shouldBe 4.dp
            style.highlight shouldBe light.colors.accent
            style.destructive shouldBe light.colors.destructive
            style.subPanel.shadow shouldBe light.shadows.lg
        }
    }

    context("dropdown menu") {
        test("runs an item and closes") {
            var isOpen by mutableStateOf(false)
            var clicked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        DropdownMenu(
                            expanded = isOpen,
                            onExpandedChange = { isOpen = it },
                            trigger = { Button(onClick = { isOpen = true }) { Text(OPEN) } },
                        ) {
                            label(text = "My Account")
                            item(
                                label = PROFILE,
                                onClick = { clicked = PROFILE },
                                icon = Lucide.User,
                                shortcut = "⇧⌘P",
                            )
                            separator()
                            item(
                                label = "Delete",
                                onClick = {},
                                variant = MenuItemVariant.Destructive,
                                isInset = true,
                            )
                        }
                    }
                }

                onNodeWithText(OPEN).performClick()
                waitForIdle()
                onNodeWithText("Delete").performMouseInput { moveTo(center) }
                onNodeWithText(PROFILE).performMouseInput { moveTo(center) }
                onNodeWithText(PROFILE).performClick()
                waitForIdle()
            }

            clicked shouldBe PROFILE
            isOpen shouldBe false
        }

        test("moves with the keyboard, toggles checks and radios and opens sub menus") {
            var isOpen by mutableStateOf(true)
            var isBar by mutableStateOf(false)
            var position by mutableStateOf("top")
            var invited = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Direction(direction = LayoutDirection.Ltr) {
                            DropdownMenu(
                                expanded = isOpen,
                                onExpandedChange = { isOpen = it },
                                trigger = { Text(OPEN) },
                            ) {
                                checkbox(
                                    label = "Status bar",
                                    checked = isBar,
                                    onCheckedChange = { isBar = it },
                                    shortcut = "⌘B",
                                )
                                radio(
                                    label = "Bottom",
                                    value = "bottom",
                                    selected = position,
                                    onSelect = { position = it },
                                )
                                sub(label = INVITE) {
                                    item(label = EMAIL, onClick = { invited = EMAIL })
                                }
                            }
                        }
                    }
                }

                val menu = onNode(isFocused() and !hasClickAction() and hasAnyDescendant(hasText(INVITE)))
                menu.performKeyInput { pressKey(Key.DirectionDown) }
                menu.performKeyInput { pressKey(Key.Enter) }
                menu.performKeyInput { pressKey(Key.DirectionDown) }
                menu.performKeyInput { pressKey(Key.Spacebar) }
                menu.performKeyInput { pressKey(Key.I) }
                menu.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                onNodeWithText(EMAIL).assertExists()
                val sub = onNode(isFocused() and !hasClickAction() and hasAnyDescendant(hasText(EMAIL)))
                sub.performKeyInput { pressKey(Key.DirectionLeft) }
                waitForIdle()
                onNodeWithText(EMAIL).assertDoesNotExist()
                onNodeWithText(INVITE).performMouseInput { moveTo(center) }
                mainClock.advanceTimeBy(INTENT)
                waitForIdle()
                onNodeWithText(EMAIL).performClick()
                waitForIdle()
            }

            isBar shouldBe true
            position shouldBe "bottom"
            invited shouldBe EMAIL
        }

        test("walks three levels deep with the keyboard and closes the whole chain on a pick") {
            var isOpen by mutableStateOf(true)
            var picked = ""

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        DropdownMenu(expanded = isOpen, onExpandedChange = { isOpen = it }, trigger = { Text(OPEN) }) {
                            sub(label = INVITE) {
                                sub(label = MORE) {
                                    item(label = TEAMS, onClick = { picked = TEAMS })
                                }
                            }
                        }
                    }
                }

                val first = onNode(isFocused() and !hasClickAction() and hasAnyDescendant(hasText(INVITE)))
                first.performKeyInput { pressKey(Key.DirectionDown) }
                first.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                val second = onNode(isFocused() and !hasClickAction() and hasAnyDescendant(hasText(MORE)))
                second.performKeyInput { pressKey(Key.DirectionDown) }
                second.performKeyInput { pressKey(Key.DirectionRight) }
                waitForIdle()
                val third = onNode(isFocused() and !hasClickAction() and hasAnyDescendant(hasText(TEAMS)))
                third.performKeyInput { pressKey(Key.DirectionDown) }
                third.performKeyInput { pressKey(Key.Enter) }
                waitForIdle()
            }

            picked shouldBe TEAMS
            isOpen shouldBe false
        }
    }
})
