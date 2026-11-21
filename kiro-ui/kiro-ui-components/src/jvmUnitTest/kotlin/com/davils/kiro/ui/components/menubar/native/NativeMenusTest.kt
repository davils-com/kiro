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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.components.menubar.native

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import com.davils.kiro.ui.components.menubar.MenubarMenu
import com.davils.kiro.ui.components.menubar.MenubarScopeImpl
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf

private const val SELECTED: Int = 2

private const val SKIPPED: Int = 3

private fun treeOf(clicks: MutableList<String>): List<MenubarMenu> {
    val scope = MenubarScopeImpl()
    scope.menu(label = "File") {
        label(text = "Recent")
        item(label = "New", onClick = { clicks += "New" }, icon = Lucide.User, shortcut = "⌘N")
        separator()
        sub(label = "Export") {
            item(label = "PDF", onClick = { clicks += "PDF" }, shortcut = "unknown")
            sub(label = "Image", enabled = false) { item(label = "PNG", onClick = { clicks += "PNG" }) }
        }
    }
    scope.menu(label = "View", enabled = false) {
        checkbox(
            label = "Wrap",
            checked = true,
            onCheckedChange = { checked -> clicks += "Wrap $checked" },
            shortcut = "Alt+Z",
        )
        radio(
            label = "Light",
            value = 1,
            selected = SELECTED,
            onSelect = { value -> clicks += "Light $value" },
            enabled = false,
        )
    }
    return scope.menus
}

class NativeMenusTest : FunSpec({
    context("nativeMenusOf") {
        test("keeps every menu with its label and state") {
            val native = nativeMenusOf(treeOf(mutableListOf()))

            native.map { menu -> menu.label to menu.isEnabled } shouldBe listOf("File" to true, "View" to false)
        }

        test("turns labels into disabled items, keeps separators and parses shortcuts") {
            val file = nativeMenusOf(treeOf(mutableListOf())).first()

            val recent = file.entries[0].shouldBeInstanceOf<NativeMenuEntry.Item>()
            recent.label shouldBe "Recent"
            recent.isEnabled shouldBe false
            recent.shortcut shouldBe null
            val new = file.entries[1].shouldBeInstanceOf<NativeMenuEntry.Item>()
            new.isEnabled shouldBe true
            new.shortcut shouldBe KeyShortcut(key = Key.N, meta = true)
            file.entries[2] shouldBe NativeMenuEntry.Separator
        }

        test("nests submenus down to the third level") {
            val file = nativeMenusOf(treeOf(mutableListOf())).first()

            val export = file.entries[3].shouldBeInstanceOf<NativeMenuEntry.Menu>()
            export.label shouldBe "Export"
            val pdf = export.entries[0].shouldBeInstanceOf<NativeMenuEntry.Item>()
            pdf.shortcut shouldBe null
            val image = export.entries[1].shouldBeInstanceOf<NativeMenuEntry.Menu>()
            image.isEnabled shouldBe false
            val png = image.entries.single()
            png.shouldBeInstanceOf<NativeMenuEntry.Item>().label shouldBe "PNG"
        }

        test("translates checkboxes and radios") {
            val view = nativeMenusOf(treeOf(mutableListOf())).last()

            val wrap = view.entries[0].shouldBeInstanceOf<NativeMenuEntry.Checkbox>()
            wrap.label shouldBe "Wrap"
            wrap.isChecked shouldBe true
            wrap.isEnabled shouldBe true
            wrap.shortcut shouldBe KeyShortcut(key = Key.Z, alt = true)
            val light = view.entries[1].shouldBeInstanceOf<NativeMenuEntry.Radio>()
            light.label shouldBe "Light"
            light.isSelected shouldBe false
            light.isEnabled shouldBe false
        }

        test("forwards every callback of the tree") {
            val clicks = mutableListOf<String>()
            val native = nativeMenusOf(treeOf(clicks))
            val file = native.first().entries
            val export = file[3].shouldBeInstanceOf<NativeMenuEntry.Menu>()
            val image = export.entries[1].shouldBeInstanceOf<NativeMenuEntry.Menu>()
            val view = native.last().entries
            val pdf = export.entries[0]
            val png = image.entries[0]

            file[0].shouldBeInstanceOf<NativeMenuEntry.Item>().onClick()
            file[1].shouldBeInstanceOf<NativeMenuEntry.Item>().onClick()
            pdf.shouldBeInstanceOf<NativeMenuEntry.Item>().onClick()
            png.shouldBeInstanceOf<NativeMenuEntry.Item>().onClick()
            view[0].shouldBeInstanceOf<NativeMenuEntry.Checkbox>().onCheckedChange(false)
            view[1].shouldBeInstanceOf<NativeMenuEntry.Radio>().onSelect()

            clicks shouldBe listOf("New", "PDF", "PNG", "Wrap false", "Light 1")
        }
    }

    context("nativeEntriesOf") {
        test("translates the entries of a menu in their order") {
            val clicks = mutableListOf<String>()

            val entries = nativeEntriesOf {
                label(text = "Account")
                item(label = "Open", onClick = { clicks += "Open" }, shortcut = "Ctrl+O")
                separator()
                checkbox(
                    label = "Pinned",
                    checked = false,
                    onCheckedChange = { checked -> clicks += "Pinned $checked" },
                )
                radio(label = "Dark", value = 1, selected = 1, onSelect = { value -> clicks += "Dark $value" })
                sub(label = "More") { item(label = "Help", onClick = { clicks += "Help" }) }
            }

            val (account, open, separator) = entries
            val (pinned, dark, more) = entries.drop(SKIPPED)
            account.shouldBeInstanceOf<NativeMenuEntry.Item>().isEnabled shouldBe false
            val item = open.shouldBeInstanceOf<NativeMenuEntry.Item>()
            item.shortcut shouldBe KeyShortcut(key = Key.O, ctrl = true)
            separator shouldBe NativeMenuEntry.Separator
            pinned.shouldBeInstanceOf<NativeMenuEntry.Checkbox>().onCheckedChange(true)
            dark.shouldBeInstanceOf<NativeMenuEntry.Radio>().isSelected shouldBe true
            val submenu = more.shouldBeInstanceOf<NativeMenuEntry.Menu>()
            val help = submenu.entries.single().shouldBeInstanceOf<NativeMenuEntry.Item>()
            help.onClick()
            item.onClick()

            clicks shouldBe listOf("Pinned true", "Help", "Open")
        }

        test("returns no entries for an empty menu") {
            nativeEntriesOf {} shouldBe emptyList()
        }
    }

    context("entries") {
        val click: () -> Unit = {}
        val check: (Boolean) -> Unit = {}
        val item = NativeMenuEntry.Item(label = "Open", isEnabled = true, shortcut = null, onClick = click)
        val checkbox = NativeMenuEntry.Checkbox(
            label = "Wrap",
            isChecked = true,
            isEnabled = true,
            shortcut = null,
            onCheckedChange = check,
        )
        val radio = NativeMenuEntry.Radio(label = "Sepia", isSelected = false, isEnabled = true, onSelect = click)
        val menu = NativeMenuEntry.Menu(label = "File", isEnabled = true, entries = listOf(item))

        test("compare by their values") {
            item.copy() shouldBe item
            item.copy().hashCode() shouldBe item.hashCode()
            item.copy(label = "Close") shouldNotBe item
            item shouldNotBe checkbox
            checkbox.copy() shouldBe checkbox
            checkbox.copy().hashCode() shouldBe checkbox.hashCode()
            checkbox.copy(isChecked = false) shouldNotBe checkbox
            checkbox shouldNotBe radio
            radio.copy() shouldBe radio
            radio.copy().hashCode() shouldBe radio.hashCode()
            radio.copy(isSelected = true) shouldNotBe radio
            radio shouldNotBe menu
            menu.copy() shouldBe menu
            menu.copy().hashCode() shouldBe menu.hashCode()
            menu.copy(entries = emptyList()) shouldNotBe menu
            menu shouldNotBe item
        }

        test("name their values") {
            item.toString() shouldContain "Item(label=Open, isEnabled=true, shortcut=null"
            checkbox.toString() shouldContain "Checkbox(label=Wrap, isChecked=true, isEnabled=true"
            radio.toString() shouldContain "Radio(label=Sepia, isSelected=false, isEnabled=true"
            menu.toString() shouldContain "Menu(label=File, isEnabled=true, entries=[Item(label=Open"
        }
    }
})
