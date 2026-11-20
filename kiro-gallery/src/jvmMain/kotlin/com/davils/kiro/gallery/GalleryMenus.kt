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

package com.davils.kiro.gallery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.menubar.MenubarScope

private const val DEFAULT_ZOOM: Int = 100

private val zooms: List<Int> = listOf(90, DEFAULT_ZOOM, 125)

private val idle: () -> Unit = {}

internal class GalleryMenus(private val onQuit: () -> Unit) {
    var isStatusBarVisible: Boolean by mutableStateOf(true)
        private set

    var zoom: Int by mutableIntStateOf(DEFAULT_ZOOM)
        private set

    fun describe(scope: MenubarScope) {
        scope.menu(label = "File") { file() }
        scope.menu(label = "Edit") { edit() }
        scope.menu(label = "View") { view() }
    }

    private fun MenuScope.file() {
        item(label = "New Window", onClick = idle, shortcut = "⌘N")
        item(label = "Open…", onClick = idle, shortcut = "⌘O")
        sub(label = "Open Recent") {
            item(label = "inventory.kide", onClick = idle)
            item(label = "rack-a.kide", onClick = idle)
        }
        separator()
        item(label = "Quit", onClick = onQuit, shortcut = "⌘Q")
    }

    private fun MenuScope.edit() {
        item(label = "Undo", onClick = idle, shortcut = "⌘Z")
        item(label = "Redo", onClick = idle, shortcut = "⇧⌘Z")
        separator()
        item(label = "Cut", onClick = idle, shortcut = "⌘X")
        item(label = "Copy", onClick = idle, shortcut = "⌘C")
        item(label = "Paste", onClick = idle, shortcut = "⌘V")
    }

    private fun MenuScope.view() {
        checkbox(
            label = "Status Bar",
            checked = isStatusBarVisible,
            onCheckedChange = { visible -> isStatusBarVisible = visible },
            shortcut = "⇧⌘B",
        )
        separator()
        label(text = "Zoom")
        zooms.forEach { percent ->
            radio(label = "$percent %", value = percent, selected = zoom, onSelect = { value -> zoom = value })
        }
    }
}
