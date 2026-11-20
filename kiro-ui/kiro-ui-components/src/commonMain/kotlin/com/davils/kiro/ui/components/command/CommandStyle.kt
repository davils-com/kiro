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
     * @since 0.1.0
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
