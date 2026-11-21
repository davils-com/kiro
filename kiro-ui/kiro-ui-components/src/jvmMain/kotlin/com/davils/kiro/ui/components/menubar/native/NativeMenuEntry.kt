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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.input.key.KeyShortcut
import com.davils.kore.core.annotation.DavilsInternal

/**
 * One entry of a menu that the operating system draws, such as the menu bar of macOS, the menu of a tray icon or the
 * menu of the dock, translated from the entries of a [com.davils.kiro.ui.components.menu.MenuScope].
 *
 * @since 1.0.0
 */
@DavilsInternal
@Immutable
public sealed interface NativeMenuEntry {
    /**
     * An item that runs an action. A label of a menu becomes an item that is not enabled.
     *
     * @property label the text of the item.
     * @property isEnabled whether the item can be chosen.
     * @property shortcut the keyboard shortcut of the item, or `null` for none.
     * @property onClick called when the item is chosen.
     * @since 1.0.0
     */
    @Immutable
    public class Item(
        public val label: String,
        public val isEnabled: Boolean,
        public val shortcut: KeyShortcut?,
        public val onClick: () -> Unit,
    ) : NativeMenuEntry {
        /**
         * Returns a copy of this [Item] with the given values replaced and every other value kept.
         *
         * @param label the new value of [label].
         * @param isEnabled the new value of [isEnabled].
         * @param shortcut the new value of [shortcut].
         * @param onClick the new value of [onClick].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(
            label: String = this.label,
            isEnabled: Boolean = this.isEnabled,
            shortcut: KeyShortcut? = this.shortcut,
            onClick: () -> Unit = this.onClick,
        ): Item = Item(label = label, isEnabled = isEnabled, shortcut = shortcut, onClick = onClick)

        override fun equals(other: Any?): Boolean = other is Item && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String =
            "Item(label=$label, isEnabled=$isEnabled, shortcut=${shortcut ?: "null"}, onClick=$onClick)"

        private fun values(): List<Any?> = listOf(label, isEnabled, shortcut, onClick)
    }

    /**
     * An item that can be checked and unchecked.
     *
     * @property label the text of the item.
     * @property isChecked whether the item is checked.
     * @property isEnabled whether the item can be chosen.
     * @property shortcut the keyboard shortcut of the item, or `null` for none.
     * @property onCheckedChange called with the new checked state when the item is chosen.
     * @since 1.0.0
     */
    @Immutable
    public class Checkbox(
        public val label: String,
        public val isChecked: Boolean,
        public val isEnabled: Boolean,
        public val shortcut: KeyShortcut?,
        public val onCheckedChange: (Boolean) -> Unit,
    ) : NativeMenuEntry {
        /**
         * Returns a copy of this [Checkbox] with the given values replaced and every other value kept.
         *
         * @param label the new value of [label].
         * @param isChecked the new value of [isChecked].
         * @param isEnabled the new value of [isEnabled].
         * @param shortcut the new value of [shortcut].
         * @param onCheckedChange the new value of [onCheckedChange].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(
            label: String = this.label,
            isChecked: Boolean = this.isChecked,
            isEnabled: Boolean = this.isEnabled,
            shortcut: KeyShortcut? = this.shortcut,
            onCheckedChange: (Boolean) -> Unit = this.onCheckedChange,
        ): Checkbox = Checkbox(
            label = label,
            isChecked = isChecked,
            isEnabled = isEnabled,
            shortcut = shortcut,
            onCheckedChange = onCheckedChange,
        )

        override fun equals(other: Any?): Boolean = other is Checkbox && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Checkbox(" +
            "label=$label, " +
            "isChecked=$isChecked, " +
            "isEnabled=$isEnabled, " +
            "shortcut=${shortcut ?: "null"}, " +
            "onCheckedChange=$onCheckedChange)"

        private fun values(): List<Any?> = listOf(label, isChecked, isEnabled, shortcut, onCheckedChange)
    }

    /**
     * One item of a group of radio items, of which one is selected.
     *
     * @property label the text of the item.
     * @property isSelected whether the item is the selected one of its group.
     * @property isEnabled whether the item can be chosen.
     * @property onSelect called when the item is chosen.
     * @since 1.0.0
     */
    @Immutable
    public class Radio(
        public val label: String,
        public val isSelected: Boolean,
        public val isEnabled: Boolean,
        public val onSelect: () -> Unit,
    ) : NativeMenuEntry {
        /**
         * Returns a copy of this [Radio] with the given values replaced and every other value kept.
         *
         * @param label the new value of [label].
         * @param isSelected the new value of [isSelected].
         * @param isEnabled the new value of [isEnabled].
         * @param onSelect the new value of [onSelect].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(
            label: String = this.label,
            isSelected: Boolean = this.isSelected,
            isEnabled: Boolean = this.isEnabled,
            onSelect: () -> Unit = this.onSelect,
        ): Radio = Radio(label = label, isSelected = isSelected, isEnabled = isEnabled, onSelect = onSelect)

        override fun equals(other: Any?): Boolean = other is Radio && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String =
            "Radio(label=$label, isSelected=$isSelected, isEnabled=$isEnabled, onSelect=$onSelect)"

        private fun values(): List<Any?> = listOf(label, isSelected, isEnabled, onSelect)
    }

    /**
     * A menu, or a submenu, with entries of its own.
     *
     * @property label the text of the menu.
     * @property isEnabled whether the menu can be opened.
     * @property entries the entries of the menu, in order.
     * @since 1.0.0
     */
    @Immutable
    public class Menu(
        public val label: String,
        public val isEnabled: Boolean,
        public val entries: List<NativeMenuEntry>,
    ) : NativeMenuEntry {
        /**
         * Returns a copy of this [Menu] with the given values replaced and every other value kept.
         *
         * @param label the new value of [label].
         * @param isEnabled the new value of [isEnabled].
         * @param entries the new value of [entries].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(
            label: String = this.label,
            isEnabled: Boolean = this.isEnabled,
            entries: List<NativeMenuEntry> = this.entries,
        ): Menu = Menu(label = label, isEnabled = isEnabled, entries = entries)

        override fun equals(other: Any?): Boolean = other is Menu && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Menu(label=$label, isEnabled=$isEnabled, entries=$entries)"

        private fun values(): List<Any?> = listOf(label, isEnabled, entries)
    }

    /**
     * A line that separates the entries above it from the entries below it.
     *
     * @since 1.0.0
     */
    @Immutable
    public data object Separator : NativeMenuEntry
}
