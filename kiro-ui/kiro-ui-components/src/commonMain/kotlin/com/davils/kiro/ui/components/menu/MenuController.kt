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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import com.davils.kiro.ui.components.menu.intent.isInSafeArea

@Stable
internal class MenuController {
    var highlighted: Int? by mutableStateOf(null)

    var openSub: Int? by mutableStateOf(null)
        private set

    var wantedSub: Int? by mutableStateOf(null)
        private set

    var deferred: Int? by mutableStateOf(null)
        private set

    var subBounds: Rect? = null

    private var pointer: Offset? = null
    private var anchor: Offset? = null

    fun activate(entries: List<MenuEntry>, index: Int, onCloseAll: () -> Unit) {
        val entry = entries.getOrNull(index) as? MenuEntry.Interactive ?: return
        if (!entry.isEnabled) return
        when (entry) {
            is MenuEntry.Action -> {
                entry.onClick()
                onCloseAll()
            }

            is MenuEntry.Check -> {
                entry.onCheckedChange(!entry.isChecked)
            }

            is MenuEntry.Radio -> {
                entry.onSelect()
                if (entry.isClosing) onCloseAll()
            }

            is MenuEntry.Sub -> {
                open(index)
            }
        }
    }

    fun hover(entries: List<MenuEntry>, index: Int) {
        val isHeldBack = openSub != null && index != openSub && isInSafeArea()
        if (isHeldBack) {
            deferred = index
            return
        }
        deferred = null
        highlighted = index
        wantedSub = index.takeIf { entries.getOrNull(it) is MenuEntry.Sub }
    }

    fun track(entries: List<MenuEntry>, point: Offset) {
        pointer = point
        val isOverSubRow = highlighted != null && highlighted == openSub
        if (isOverSubRow && deferred == null) anchor = point
        val waiting = deferred ?: return
        if (!isInSafeArea()) hover(entries = entries, index = waiting)
    }

    fun release(entries: List<MenuEntry>) {
        val waiting = deferred ?: return
        anchor = null
        hover(entries = entries, index = waiting)
    }

    fun settle() {
        openSub = wantedSub
    }

    fun open(index: Int) {
        wantedSub = index
        openSub = index
    }

    fun close() {
        wantedSub = null
        openSub = null
        deferred = null
        subBounds = null
    }

    fun reset() {
        close()
        highlighted = null
        pointer = null
        anchor = null
    }

    private fun isInSafeArea(): Boolean {
        val apex = anchor
        val target = subBounds
        val point = pointer
        if (apex == null || target == null || point == null) return false
        return isInSafeArea(apex = apex, target = target, point = point)
    }
}
