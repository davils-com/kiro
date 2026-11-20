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

package com.davils.kiro.ui.components.menubar.native

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import com.davils.kiro.ui.components.menubar.MenubarMenu
import com.davils.kiro.ui.components.menubar.MenubarScope
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.User
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

private const val SELECTED: Int = 2

private fun treeOf(clicks: MutableList<String>): List<MenubarMenu> {
    val scope = MenubarScope()
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
})
