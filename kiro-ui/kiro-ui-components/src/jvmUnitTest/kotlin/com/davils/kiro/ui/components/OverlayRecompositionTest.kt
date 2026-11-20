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

package com.davils.kiro.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.alertdialog.AlertDialog
import com.davils.kiro.ui.components.alertdialog.AlertDialogAction
import com.davils.kiro.ui.components.alertdialog.AlertDialogCancel
import com.davils.kiro.ui.components.alertdialog.AlertDialogDescription
import com.davils.kiro.ui.components.alertdialog.AlertDialogFooter
import com.davils.kiro.ui.components.alertdialog.AlertDialogHeader
import com.davils.kiro.ui.components.alertdialog.AlertDialogSize
import com.davils.kiro.ui.components.alertdialog.AlertDialogTitle
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.calendar.CalendarOptions
import com.davils.kiro.ui.components.combobox.Combobox
import com.davils.kiro.ui.components.combobox.comboboxStyle
import com.davils.kiro.ui.components.command.Command
import com.davils.kiro.ui.components.command.commandStyle
import com.davils.kiro.ui.components.contextmenu.ContextMenu
import com.davils.kiro.ui.components.datepicker.DatePicker
import com.davils.kiro.ui.components.datepicker.datePickerStyle
import com.davils.kiro.ui.components.dialog.Dialog
import com.davils.kiro.ui.components.dialog.DialogDescription
import com.davils.kiro.ui.components.dialog.DialogFooter
import com.davils.kiro.ui.components.dialog.DialogHeader
import com.davils.kiro.ui.components.dialog.DialogTitle
import com.davils.kiro.ui.components.dialog.dialogStyle
import com.davils.kiro.ui.components.drawer.Drawer
import com.davils.kiro.ui.components.drawer.DrawerDescription
import com.davils.kiro.ui.components.drawer.DrawerFooter
import com.davils.kiro.ui.components.drawer.DrawerHeader
import com.davils.kiro.ui.components.drawer.DrawerTitle
import com.davils.kiro.ui.components.drawer.drawerStyle
import com.davils.kiro.ui.components.dropdownmenu.DropdownMenu
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.fixture.reducedMotion
import com.davils.kiro.ui.components.hovercard.HoverCard
import com.davils.kiro.ui.components.hovercard.hoverCardStyle
import com.davils.kiro.ui.components.menu.menuStyle
import com.davils.kiro.ui.components.menubar.Menubar
import com.davils.kiro.ui.components.menubar.menubarStyle
import com.davils.kiro.ui.components.navigationmenu.NavigationMenu
import com.davils.kiro.ui.components.navigationmenu.NavigationMenuLink
import com.davils.kiro.ui.components.navigationmenu.navigationMenuStyle
import com.davils.kiro.ui.components.popover.Popover
import com.davils.kiro.ui.components.popover.PopoverDescription
import com.davils.kiro.ui.components.popover.PopoverHeader
import com.davils.kiro.ui.components.popover.PopoverTitle
import com.davils.kiro.ui.components.popover.popoverStyle
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.select.Select
import com.davils.kiro.ui.components.select.SelectSize
import com.davils.kiro.ui.components.select.selectStyle
import com.davils.kiro.ui.components.sheet.Sheet
import com.davils.kiro.ui.components.sheet.SheetDescription
import com.davils.kiro.ui.components.sheet.SheetFooter
import com.davils.kiro.ui.components.sheet.SheetHeader
import com.davils.kiro.ui.components.sheet.SheetTitle
import com.davils.kiro.ui.components.sheet.sheetStyle
import com.davils.kiro.ui.components.sidebar.Sidebar
import com.davils.kiro.ui.components.sidebar.SidebarCollapsible
import com.davils.kiro.ui.components.sidebar.SidebarInset
import com.davils.kiro.ui.components.sidebar.SidebarMenuButton
import com.davils.kiro.ui.components.sidebar.SidebarMenuButtonSize
import com.davils.kiro.ui.components.sidebar.SidebarProvider
import com.davils.kiro.ui.components.sidebar.SidebarState
import com.davils.kiro.ui.components.sidebar.SidebarVariant
import com.davils.kiro.ui.components.sidebar.sidebarStyle
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.toast.ToastPosition
import com.davils.kiro.ui.components.toast.Toaster
import com.davils.kiro.ui.components.toast.ToasterState
import com.davils.kiro.ui.components.toast.toastStyle
import com.davils.kiro.ui.components.tooltip.Tooltip
import com.davils.kiro.ui.components.tooltip.tooltipStyle
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ROUNDS: Int = 3
private const val FIXED: String = "fixed"

@Composable
private fun Floating(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Popover(
        expanded = isOdd,
        onExpandedChange = {},
        trigger = { Text(label) },
        modifier = Modifier,
        placement = Placement(),
        style = popoverStyle(),
    ) {
        PopoverHeader(modifier = Modifier, style = popoverStyle()) {
            PopoverTitle(text = label, modifier = Modifier, style = popoverStyle())
            PopoverDescription(text = label, modifier = Modifier, style = popoverStyle())
        }
    }
    Tooltip(
        tooltip = { Text(label) },
        modifier = Modifier,
        placement = Placement(),
        style = tooltipStyle(),
    ) { Text(label) }
    HoverCard(
        trigger = { Text(label) },
        modifier = Modifier,
        placement = Placement(),
        style = hoverCardStyle(),
    ) { Text(label) }
    DropdownMenu(
        expanded = isOdd,
        onExpandedChange = {},
        trigger = { Text(label) },
        modifier = Modifier,
        placement = Placement(),
        style = menuStyle(),
    ) {
        item(label = label, onClick = {})
    }
    ContextMenu(menu = { item(label = label, onClick = {}) }, modifier = Modifier, style = menuStyle()) { Text(label) }
    Menubar(modifier = Modifier, style = menubarStyle()) { menu(label = label) { item(label = label, onClick = {}) } }
    NavigationMenu(modifier = Modifier, style = navigationMenuStyle()) {
        item(label = label) {
            NavigationMenuLink(
                onClick = {},
                modifier = Modifier,
                isActive = isOdd,
                style = navigationMenuStyle(),
            ) { Text(label) }
        }
    }
}

@Composable
private fun Pickers(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Select(
        selected = label,
        onSelectedChange = {},
        modifier = Modifier,
        size = SelectSize.Sm,
        enabled = !isOdd,
        isInvalid = isOdd,
        style = selectStyle(SelectSize.Sm),
    ) {
        option(value = label, label = label)
    }
    Combobox(
        items = listOf(label),
        selected = label,
        onSelectedChange = {},
        label = { item -> item },
        modifier = Modifier,
        placeholder = label,
        emptyText = label,
        style = comboboxStyle(),
    )
    Command(modifier = Modifier, placeholder = label, emptyText = label, style = commandStyle()) {
        group(heading = label) { item(label = label, onSelect = {}) }
    }
    DatePicker(
        date = null,
        onDateChange = {},
        modifier = Modifier,
        placeholder = label,
        options = CalendarOptions(),
        style = datePickerStyle(),
    )
    Toaster(
        toaster = remember { ToasterState() },
        modifier = Modifier,
        position = ToastPosition.TopEnd,
        style = toastStyle(),
    )
}

@Composable
private fun Modals(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Dialog(open = isOdd, onOpenChange = {}, modifier = Modifier, isCloseButtonShown = !isOdd, style = dialogStyle()) {
        DialogHeader(modifier = Modifier, style = dialogStyle()) {
            DialogTitle(text = label, modifier = Modifier, style = dialogStyle())
            DialogDescription(text = label, modifier = Modifier, style = dialogStyle())
        }
        DialogFooter(modifier = Modifier, style = dialogStyle()) { Text(label) }
    }
    AlertDialog(
        open = isOdd,
        onOpenChange = {},
        modifier = Modifier,
        size = AlertDialogSize.Sm,
        style = dialogStyle(),
    ) {
        AlertDialogHeader(modifier = Modifier, media = null) {
            AlertDialogTitle(text = label, modifier = Modifier, style = dialogStyle())
            AlertDialogDescription(text = label, modifier = Modifier, style = dialogStyle())
        }
        AlertDialogFooter(modifier = Modifier, style = dialogStyle()) {
            AlertDialogCancel(onClick = {}, modifier = Modifier, variant = ButtonVariant.Outline) { Text(label) }
            AlertDialogAction(onClick = {}, modifier = Modifier, variant = ButtonVariant.Default) { Text(label) }
        }
    }
    Sheet(open = isOdd, onOpenChange = {}, modifier = Modifier, isCloseButtonShown = !isOdd, style = sheetStyle()) {
        SheetHeader(modifier = Modifier, style = sheetStyle()) {
            SheetTitle(text = label, modifier = Modifier, style = sheetStyle())
            SheetDescription(text = label, modifier = Modifier, style = sheetStyle())
        }
        SheetFooter(modifier = Modifier, style = sheetStyle()) { Text(label) }
    }
    Drawer(open = isOdd, onOpenChange = {}, modifier = Modifier, style = drawerStyle()) {
        DrawerHeader(modifier = Modifier, style = drawerStyle()) {
            DrawerTitle(text = label, modifier = Modifier, style = drawerStyle())
            DrawerDescription(text = label, modifier = Modifier, style = drawerStyle())
        }
        DrawerFooter(modifier = Modifier, style = drawerStyle()) { Text(label) }
    }
}

@Composable
private fun Shells(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    SidebarProvider(
        state = remember { SidebarState() },
        modifier = Modifier,
        variant = SidebarVariant.Floating,
        isShortcutEnabled = isOdd,
        style = sidebarStyle(),
    ) {
        Sidebar(
            modifier = Modifier,
            collapsible = SidebarCollapsible.Icon,
            isRailShown = isOdd,
            style = sidebarStyle(),
        ) {
            SidebarMenuButton(
                onClick = {},
                modifier = Modifier,
                isActive = isOdd,
                isOutlined = isOdd,
                size = SidebarMenuButtonSize.Lg,
                tooltip = label,
                style = sidebarStyle(),
            ) {
                Text(label)
            }
        }
        SidebarInset(modifier = Modifier, style = sidebarStyle()) { Text(label) }
    }
}

@OptIn(ExperimentalTestApi::class)
class OverlayRecompositionTest : FunSpec({
    context("overlay components") {
        test("take every argument explicitly and recompose with changed and unchanged arguments") {
            var round by mutableIntStateOf(0)
            var rendered = -1

            runComposeUiTest {
                setContent {
                    Themed(theme = reducedMotion) {
                        Column {
                            val isOdd = round % 2 == 1
                            val label = "Round $round"
                            Floating(label = label, isOdd = isOdd, tick = round)
                            Floating(label = FIXED, isOdd = false, tick = round)
                            Pickers(label = label, isOdd = isOdd, tick = round)
                            Pickers(label = FIXED, isOdd = false, tick = round)
                            Modals(label = label, isOdd = isOdd, tick = round)
                            Modals(label = FIXED, isOdd = false, tick = round)
                            Shells(label = label, isOdd = isOdd, tick = round)
                            Shells(label = FIXED, isOdd = false, tick = round)
                            val stable = remember { FIXED.uppercase() }
                            val isNever = round < 0
                            Floating(label = stable, isOdd = isNever, tick = round)
                            Pickers(label = stable, isOdd = isNever, tick = round)
                            Modals(label = stable, isOdd = isNever, tick = round)
                            Shells(label = stable, isOdd = isNever, tick = round)
                            rendered = round
                        }
                    }
                }
                repeat(ROUNDS) {
                    round++
                    waitForIdle()
                }
            }

            rendered shouldBe ROUNDS
        }
    }
})
