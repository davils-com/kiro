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

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.utf16CodePoint
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.roving.rovingMoveOf

private val confirmKeys: Set<Key> = setOf(Key.Enter, Key.NumPadEnter, Key.Spacebar)
private val caretKeys: Set<Key> = setOf(Key.MoveHome, Key.MoveEnd)

internal class MenuKeys(
    private val entries: List<MenuEntry>,
    private val controller: MenuController,
    isReversed: Boolean,
    private val onCloseAll: () -> Unit,
    private val onCloseSelf: (() -> Unit)?,
    private val isTextInput: Boolean = false,
) {
    private val openKey: Key = Key.DirectionLeft.takeIf { isReversed } ?: Key.DirectionRight
    private val closeKey: Key = Key.DirectionRight.takeIf { isReversed } ?: Key.DirectionLeft

    fun handle(event: KeyEvent): Boolean {
        if (event.type != KeyEventType.KeyDown) return false
        return move(event.key) || confirm(event.key) || nest(event.key) || typeahead(event)
    }

    private fun move(key: Key): Boolean {
        val isCaret = isTextInput && key in caretKeys
        val candidate = rovingMoveOf(key = key, orientation = Orientation.Vertical, isReversed = false)
        val move = candidate?.takeUnless { isCaret } ?: return false
        controller.highlighted = highlightAfter(entries = entries, current = controller.highlighted, move = move)
        return true
    }

    private fun confirm(key: Key): Boolean {
        val index =
            controller.highlighted?.takeIf { key in confirmKeys && !(isTextInput && key == Key.Spacebar) }
                ?: return false
        controller.activate(entries = entries, index = index, onCloseAll = onCloseAll)
        return true
    }

    private fun nest(key: Key): Boolean {
        val close = onCloseSelf?.takeIf { key == closeKey }
        close?.invoke()
        val sub = controller.highlighted?.takeIf { key == openKey && entries.getOrNull(it) is MenuEntry.Sub }
        sub?.let(controller::open)
        return close != null || sub != null
    }

    private fun typeahead(event: KeyEvent): Boolean {
        val char =
            event.utf16CodePoint.toChar().takeIf { typed -> !isTextInput && typed.isLetterOrDigit() } ?: return false
        val target = typeaheadTarget(entries = entries, current = controller.highlighted, char = char)
        target?.let { index -> controller.highlighted = index }
        return target != null
    }
}
