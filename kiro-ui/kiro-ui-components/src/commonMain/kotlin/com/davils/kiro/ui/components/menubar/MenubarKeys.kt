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

package com.davils.kiro.ui.components.menubar

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.menu.MenuController
import com.davils.kiro.ui.components.menu.MenuKeys
import com.davils.kiro.ui.components.menu.highlightAfter
import com.davils.kiro.ui.components.primitive.roving.RovingMove
import com.davils.kiro.ui.components.primitive.roving.rovingMoveOf
import com.davils.kiro.ui.components.primitive.roving.rovingTarget

private val openKeys: Set<Key> = setOf(Key.DirectionDown, Key.Enter, Key.NumPadEnter, Key.Spacebar)

internal class MenubarKeys(
    private val menus: List<MenubarMenu>,
    private val controllers: List<MenuController>,
    private val isReversed: Boolean,
    private val state: MenubarState,
) {
    fun handle(event: KeyEvent): Boolean {
        if (event.type != KeyEventType.KeyDown) return false
        return inMenu(event) || across(event.key) || opening(event.key) || closing(event.key)
    }

    private fun inMenu(event: KeyEvent): Boolean {
        val index = state.open() ?: return false
        val keys = MenuKeys(
            entries = menus[index].entries,
            controller = controllers[index],
            isReversed = isReversed,
            onCloseAll = { state.onOpen(null) },
            onCloseSelf = null,
        )
        return keys.handle(event)
    }

    private fun across(key: Key): Boolean {
        val move = rovingMoveOf(
            key = key,
            orientation = Orientation.Horizontal,
            isReversed = isReversed,
        ) ?: return false
        val current = state.open() ?: state.focused() ?: 0
        val target = rovingTarget(move = move, current = current, isEnabled = menus.map { menu -> menu.isEnabled })
        target?.let { index -> moveTo(index) }
        return target != null
    }

    private fun moveTo(index: Int) {
        val isOpen = state.open() != null
        state.onFocus(index)
        if (isOpen) state.onOpen(index)
    }

    private fun opening(key: Key): Boolean {
        val index = state.focused()?.takeIf { key in openKeys && state.open() == null } ?: return false
        state.onOpen(index)
        controllers[index].highlighted = highlightAfter(
            entries = menus[index].entries,
            current = null,
            move = RovingMove.First,
        )
        return true
    }

    private fun closing(key: Key): Boolean {
        val isClosing = key == Key.Escape && state.open() != null
        if (isClosing) state.onOpen(null)
        return isClosing
    }
}
