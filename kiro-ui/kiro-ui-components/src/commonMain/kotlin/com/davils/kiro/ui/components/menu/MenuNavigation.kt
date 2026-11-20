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

import com.davils.kiro.ui.components.primitive.roving.RovingMove
import com.davils.kiro.ui.components.primitive.roving.rovingTarget

internal fun enabledFlagsOf(entries: List<MenuEntry>): List<Boolean> =
    entries.map { entry -> (entry as? MenuEntry.Interactive)?.isEnabled == true }

internal fun highlightAfter(entries: List<MenuEntry>, current: Int?, move: RovingMove): Int? {
    val enabled = enabledFlagsOf(entries)
    if (enabled.none { isEnabled -> isEnabled }) return null
    return current?.let { index ->
        stepped(
            enabled = enabled,
            current = index,
            move = move,
        )
    } ?: entering(enabled = enabled, move = move)
}

internal fun typeaheadTarget(entries: List<MenuEntry>, current: Int?, char: Char): Int? {
    val count = entries.size
    val start = (current ?: -1) + 1
    val order = List(count) { step -> (start + step) % count }
    return order.firstOrNull { index -> matches(entry = entries[index], char = char) }
}

private fun stepped(enabled: List<Boolean>, current: Int, move: RovingMove): Int? =
    rovingTarget(move = move, current = current, isEnabled = enabled)

private fun entering(enabled: List<Boolean>, move: RovingMove): Int? {
    val isBackward = move == RovingMove.Previous || move == RovingMove.Last
    if (isBackward) return enabled.indexOfLast { isEnabled -> isEnabled }
    return enabled.indexOfFirst { isEnabled -> isEnabled }
}

private fun matches(entry: MenuEntry, char: Char): Boolean {
    val interactive = entry as? MenuEntry.Interactive ?: return false
    return interactive.isEnabled && interactive.label.startsWith(char, ignoreCase = true)
}
