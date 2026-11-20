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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.inputgroup.InputGroupStyle
import com.davils.kiro.ui.components.menu.MenuStyle
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.components.tabs.TabsStyle
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The look of a [Command] menu and of a [CommandDialog]: the panel, the search field, the list, the groups, the tabs
 * and the dialog.
 *
 * @property panel the panel of an inline menu: its colors, shape, ring, shadow and padding.
 * @property input the style of the search field.
 * @property searchIcon the icon at the start of the search field.
 * @property searchIconAlpha the opacity of [searchIcon], from 0 to 1.
 * @property listMaxHeight the height above which the list of commands scrolls.
 * @property groupPadding the padding around the items of a group.
 * @property headingStyle the text style of the heading of a group.
 * @property headingColor the color of the heading of a group and of the text shown when nothing matches.
 * @property headingHorizontalPadding the padding at the start and the end of the heading of a group.
 * @property headingVerticalPadding the padding above and below the heading of a group.
 * @property item the style of the items, as rows of a menu.
 * @property emptyStyle the text style of the text shown when nothing matches.
 * @property emptyPadding the padding around the text shown when nothing matches.
 * @property separatorColor the color of the line between two groups.
 * @property gap the space between the search field, the tabs and the list.
 * @property tabs the style of the tabs.
 * @property pathStyle the text style of the path of the open pages at the start of the search field.
 * @property pathColor the color of the path of the open pages.
 * @property scrim the color drawn over the window behind a [CommandDialog].
 * @property dialogPanel the panel of the menu in a [CommandDialog].
 * @property dialogWidth the width of the menu in a [CommandDialog].
 * @property dialogOffset the distance between the top of the window and the menu in a [CommandDialog].
 * @since 1.0.0
 */
@Immutable
public class CommandStyle(
    public val panel: PanelStyle,
    public val input: InputGroupStyle,
    public val searchIcon: IconGraphic,
    public val searchIconAlpha: Float,
    public val listMaxHeight: Dp,
    public val groupPadding: Dp,
    public val headingStyle: TextStyle,
    public val headingColor: Color,
    public val headingHorizontalPadding: Dp,
    public val headingVerticalPadding: Dp,
    public val item: MenuStyle,
    public val emptyStyle: TextStyle,
    public val emptyPadding: Dp,
    public val separatorColor: Color,
    public val gap: Dp,
    public val tabs: TabsStyle,
    public val pathStyle: TextStyle,
    public val pathColor: Color,
    public val scrim: Color,
    public val dialogPanel: PanelStyle,
    public val dialogWidth: Dp,
    public val dialogOffset: Dp,
) {
    /**
     * Returns a copy of this [CommandStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        panel: PanelStyle = this.panel,
        input: InputGroupStyle = this.input,
        searchIcon: IconGraphic = this.searchIcon,
        searchIconAlpha: Float = this.searchIconAlpha,
        listMaxHeight: Dp = this.listMaxHeight,
        groupPadding: Dp = this.groupPadding,
        headingStyle: TextStyle = this.headingStyle,
        headingColor: Color = this.headingColor,
        headingHorizontalPadding: Dp = this.headingHorizontalPadding,
        headingVerticalPadding: Dp = this.headingVerticalPadding,
        item: MenuStyle = this.item,
        emptyStyle: TextStyle = this.emptyStyle,
        emptyPadding: Dp = this.emptyPadding,
        separatorColor: Color = this.separatorColor,
        gap: Dp = this.gap,
        tabs: TabsStyle = this.tabs,
        pathStyle: TextStyle = this.pathStyle,
        pathColor: Color = this.pathColor,
        scrim: Color = this.scrim,
        dialogPanel: PanelStyle = this.dialogPanel,
        dialogWidth: Dp = this.dialogWidth,
        dialogOffset: Dp = this.dialogOffset,
    ): CommandStyle = CommandStyle(
        panel = panel,
        input = input,
        searchIcon = searchIcon,
        searchIconAlpha = searchIconAlpha,
        listMaxHeight = listMaxHeight,
        groupPadding = groupPadding,
        headingStyle = headingStyle,
        headingColor = headingColor,
        headingHorizontalPadding = headingHorizontalPadding,
        headingVerticalPadding = headingVerticalPadding,
        item = item,
        emptyStyle = emptyStyle,
        emptyPadding = emptyPadding,
        separatorColor = separatorColor,
        gap = gap,
        tabs = tabs,
        pathStyle = pathStyle,
        pathColor = pathColor,
        scrim = scrim,
        dialogPanel = dialogPanel,
        dialogWidth = dialogWidth,
        dialogOffset = dialogOffset,
    )

    override fun equals(other: Any?): Boolean = other is CommandStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CommandStyle(" +
        "panel=$panel, " +
        "input=$input, " +
        "searchIcon=$searchIcon, " +
        "searchIconAlpha=$searchIconAlpha, " +
        "listMaxHeight=$listMaxHeight, " +
        "groupPadding=$groupPadding, " +
        "headingStyle=$headingStyle, " +
        "headingColor=$headingColor, " +
        "headingHorizontalPadding=$headingHorizontalPadding, " +
        "headingVerticalPadding=$headingVerticalPadding, " +
        "item=$item, " +
        "emptyStyle=$emptyStyle, " +
        "emptyPadding=$emptyPadding, " +
        "separatorColor=$separatorColor, " +
        "gap=$gap, " +
        "tabs=$tabs, " +
        "pathStyle=$pathStyle, " +
        "pathColor=$pathColor, " +
        "scrim=$scrim, " +
        "dialogPanel=$dialogPanel, " +
        "dialogWidth=$dialogWidth, " +
        "dialogOffset=$dialogOffset)"

    private fun values(): List<Any?> = listOf(
        panel,
        input,
        searchIcon,
        searchIconAlpha,
        listMaxHeight,
        groupPadding,
        headingStyle,
        headingColor,
        headingHorizontalPadding,
        headingVerticalPadding,
        item,
        emptyStyle,
        emptyPadding,
        separatorColor,
        gap,
        tabs,
        pathStyle,
        pathColor,
        scrim,
        dialogPanel,
        dialogWidth,
        dialogOffset,
    )
}
