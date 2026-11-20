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

package com.davils.kiro.ui.components.command

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import com.davils.kiro.ui.components.menu.MenuKeys

private val backKeys: Set<Key> = setOf(Key.Backspace, Key.Escape)

internal class CommandKeys(
    private val state: CommandState,
    private val tabCount: Int,
    private val menu: MenuKeys,
) {
    fun handle(event: KeyEvent): Boolean {
        if (event.type != KeyEventType.KeyDown) return false
        return switchTab(event) || back(event) || menu.handle(event)
    }

    private fun switchTab(event: KeyEvent): Boolean {
        if (event.key != Key.Tab || tabCount < 2) return false
        val step = (-1).takeIf { event.isShiftPressed } ?: 1
        state.select((state.tab + step).mod(tabCount))
        return true
    }

    private fun back(event: KeyEvent): Boolean {
        val isBack = event.key in backKeys && (event.key == Key.Escape || state.query.isEmpty())
        return isBack && state.back()
    }
}
