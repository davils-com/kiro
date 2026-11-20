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

package com.davils.kiro.ui.components.combobox

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.inputgroup.InputGroupStyle
import com.davils.kiro.ui.components.menu.MenuStyle
import com.davils.kiro.ui.components.select.SelectStyle
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class ComboboxStyle(
    public val trigger: SelectStyle,
    public val triggerIcon: IconGraphic,
    public val minWidth: Dp,
    public val menu: MenuStyle,
    public val search: InputGroupStyle,
    public val searchIcon: IconGraphic,
    public val searchIconAlpha: Float,
    public val searchPlaceholder: String,
    public val sideOffset: Dp,
    public val listMaxHeight: Dp,
    public val gap: Dp,
    public val emptyTextStyle: TextStyle,
    public val emptyColor: Color,
    public val emptyPadding: Dp,
) {
    /**
     * Returns a copy of this [ComboboxStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        trigger: SelectStyle = this.trigger,
        triggerIcon: IconGraphic = this.triggerIcon,
        minWidth: Dp = this.minWidth,
        menu: MenuStyle = this.menu,
        search: InputGroupStyle = this.search,
        searchIcon: IconGraphic = this.searchIcon,
        searchIconAlpha: Float = this.searchIconAlpha,
        searchPlaceholder: String = this.searchPlaceholder,
        sideOffset: Dp = this.sideOffset,
        listMaxHeight: Dp = this.listMaxHeight,
        gap: Dp = this.gap,
        emptyTextStyle: TextStyle = this.emptyTextStyle,
        emptyColor: Color = this.emptyColor,
        emptyPadding: Dp = this.emptyPadding,
    ): ComboboxStyle = ComboboxStyle(
        trigger = trigger,
        triggerIcon = triggerIcon,
        minWidth = minWidth,
        menu = menu,
        search = search,
        searchIcon = searchIcon,
        searchIconAlpha = searchIconAlpha,
        searchPlaceholder = searchPlaceholder,
        sideOffset = sideOffset,
        listMaxHeight = listMaxHeight,
        gap = gap,
        emptyTextStyle = emptyTextStyle,
        emptyColor = emptyColor,
        emptyPadding = emptyPadding,
    )

    override fun equals(other: Any?): Boolean = other is ComboboxStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ComboboxStyle(" +
        "trigger=$trigger, " +
        "triggerIcon=$triggerIcon, " +
        "minWidth=$minWidth, " +
        "menu=$menu, " +
        "search=$search, " +
        "searchIcon=$searchIcon, " +
        "searchIconAlpha=$searchIconAlpha, " +
        "searchPlaceholder=$searchPlaceholder, " +
        "sideOffset=$sideOffset, " +
        "listMaxHeight=$listMaxHeight, " +
        "gap=$gap, " +
        "emptyTextStyle=$emptyTextStyle, " +
        "emptyColor=$emptyColor, " +
        "emptyPadding=$emptyPadding)"

    private fun values(): List<Any?> = listOf(
        trigger,
        triggerIcon,
        minWidth,
        menu,
        search,
        searchIcon,
        searchIconAlpha,
        searchPlaceholder,
        sideOffset,
        listMaxHeight,
        gap,
        emptyTextStyle,
        emptyColor,
        emptyPadding,
    )
}
