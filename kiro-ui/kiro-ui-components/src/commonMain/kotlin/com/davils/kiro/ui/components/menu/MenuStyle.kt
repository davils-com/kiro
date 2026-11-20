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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.icons.IconGraphic
import kotlin.time.Duration

/**
 * How a menu looks and behaves: its panels, its rows, labels, separators, shortcuts and indicators, and how long a
 * submenu waits before it opens or closes.
 *
 * The dropdown menu, the context menu, the menubar and the list of a select draw their menus with this style.
 *
 * @property panel the panel of the menu itself.
 * @property subPanel the panel of a submenu.
 * @property minWidth the smallest width of the menu.
 * @property subMinWidth the smallest width of a submenu.
 * @property itemShape the shape of the highlight behind a row.
 * @property itemHorizontalPadding the padding at the start and the end of a row, and of a label.
 * @property itemVerticalPadding the padding above and below a row, and a label.
 * @property insetPadding the padding at the start of an inset row or label, which lines its text up with rows that show
 * an icon.
 * @property indicatorPadding the padding at the end of a checkbox or radio row, which leaves room for its check mark.
 * @property indicatorEnd the distance between the check mark of a checkbox or radio row and the end of the row.
 * @property gap the space between the icon, the label, the shortcut and the submenu arrow of a row.
 * @property textStyle the text style of the label of a row.
 * @property iconSize the size of the icons of a row and of its check mark.
 * @property highlight the color behind the highlighted row.
 * @property highlightContent the color of the text and icons of the highlighted row.
 * @property destructive the color of the text and icons of a destructive row.
 * @property destructiveHighlight the color behind a destructive row while it is highlighted.
 * @property labelStyle the text style of a label between the rows.
 * @property labelColor the color of a label between the rows.
 * @property separatorColor the color of a separator line.
 * @property separatorSpacing the space above and below a separator line.
 * @property shortcutStyle the text style of the keyboard shortcut of a row.
 * @property shortcutColor the color of the keyboard shortcut of a row that is not highlighted.
 * @property checkIcon the check mark of a checked checkbox row and of the selected radio row.
 * @property subIcon the arrow at the end of a row that opens a submenu; it is mirrored in a right-to-left layout.
 * @property subOpenDelay how long the pointer rests on a row before its submenu opens.
 * @property subCloseDelay how long a submenu stays open after the pointer moves to another row.
 * @property disabledAlpha the alpha of a disabled row.
 * @since 1.0.0
 */
@Immutable
public class MenuStyle(
    public val panel: PanelStyle,
    public val subPanel: PanelStyle,
    public val minWidth: Dp,
    public val subMinWidth: Dp,
    public val itemShape: Shape,
    public val itemHorizontalPadding: Dp,
    public val itemVerticalPadding: Dp,
    public val insetPadding: Dp,
    public val indicatorPadding: Dp,
    public val indicatorEnd: Dp,
    public val gap: Dp,
    public val textStyle: TextStyle,
    public val iconSize: Dp,
    public val highlight: Color,
    public val highlightContent: Color,
    public val destructive: Color,
    public val destructiveHighlight: Color,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val separatorColor: Color,
    public val separatorSpacing: Dp,
    public val shortcutStyle: TextStyle,
    public val shortcutColor: Color,
    public val checkIcon: IconGraphic,
    public val subIcon: IconGraphic,
    public val subOpenDelay: Duration,
    public val subCloseDelay: Duration,
    public val disabledAlpha: Float,
) {
    /**
     * Returns a copy of this [MenuStyle] with the given values replaced and every other value kept.
     *
     * @param panel the new value of [panel].
     * @param subPanel the new value of [subPanel].
     * @param minWidth the new value of [minWidth].
     * @param subMinWidth the new value of [subMinWidth].
     * @param itemShape the new value of [itemShape].
     * @param itemHorizontalPadding the new value of [itemHorizontalPadding].
     * @param itemVerticalPadding the new value of [itemVerticalPadding].
     * @param insetPadding the new value of [insetPadding].
     * @param indicatorPadding the new value of [indicatorPadding].
     * @param indicatorEnd the new value of [indicatorEnd].
     * @param gap the new value of [gap].
     * @param textStyle the new value of [textStyle].
     * @param iconSize the new value of [iconSize].
     * @param highlight the new value of [highlight].
     * @param highlightContent the new value of [highlightContent].
     * @param destructive the new value of [destructive].
     * @param destructiveHighlight the new value of [destructiveHighlight].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param separatorColor the new value of [separatorColor].
     * @param separatorSpacing the new value of [separatorSpacing].
     * @param shortcutStyle the new value of [shortcutStyle].
     * @param shortcutColor the new value of [shortcutColor].
     * @param checkIcon the new value of [checkIcon].
     * @param subIcon the new value of [subIcon].
     * @param subOpenDelay the new value of [subOpenDelay].
     * @param subCloseDelay the new value of [subCloseDelay].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        panel: PanelStyle = this.panel,
        subPanel: PanelStyle = this.subPanel,
        minWidth: Dp = this.minWidth,
        subMinWidth: Dp = this.subMinWidth,
        itemShape: Shape = this.itemShape,
        itemHorizontalPadding: Dp = this.itemHorizontalPadding,
        itemVerticalPadding: Dp = this.itemVerticalPadding,
        insetPadding: Dp = this.insetPadding,
        indicatorPadding: Dp = this.indicatorPadding,
        indicatorEnd: Dp = this.indicatorEnd,
        gap: Dp = this.gap,
        textStyle: TextStyle = this.textStyle,
        iconSize: Dp = this.iconSize,
        highlight: Color = this.highlight,
        highlightContent: Color = this.highlightContent,
        destructive: Color = this.destructive,
        destructiveHighlight: Color = this.destructiveHighlight,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        separatorColor: Color = this.separatorColor,
        separatorSpacing: Dp = this.separatorSpacing,
        shortcutStyle: TextStyle = this.shortcutStyle,
        shortcutColor: Color = this.shortcutColor,
        checkIcon: IconGraphic = this.checkIcon,
        subIcon: IconGraphic = this.subIcon,
        subOpenDelay: Duration = this.subOpenDelay,
        subCloseDelay: Duration = this.subCloseDelay,
        disabledAlpha: Float = this.disabledAlpha,
    ): MenuStyle = MenuStyle(
        panel = panel,
        subPanel = subPanel,
        minWidth = minWidth,
        subMinWidth = subMinWidth,
        itemShape = itemShape,
        itemHorizontalPadding = itemHorizontalPadding,
        itemVerticalPadding = itemVerticalPadding,
        insetPadding = insetPadding,
        indicatorPadding = indicatorPadding,
        indicatorEnd = indicatorEnd,
        gap = gap,
        textStyle = textStyle,
        iconSize = iconSize,
        highlight = highlight,
        highlightContent = highlightContent,
        destructive = destructive,
        destructiveHighlight = destructiveHighlight,
        labelStyle = labelStyle,
        labelColor = labelColor,
        separatorColor = separatorColor,
        separatorSpacing = separatorSpacing,
        shortcutStyle = shortcutStyle,
        shortcutColor = shortcutColor,
        checkIcon = checkIcon,
        subIcon = subIcon,
        subOpenDelay = subOpenDelay,
        subCloseDelay = subCloseDelay,
        disabledAlpha = disabledAlpha,
    )

    override fun equals(other: Any?): Boolean = other is MenuStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MenuStyle(" +
        "panel=$panel, " +
        "subPanel=$subPanel, " +
        "minWidth=$minWidth, " +
        "subMinWidth=$subMinWidth, " +
        "itemShape=$itemShape, " +
        "itemHorizontalPadding=$itemHorizontalPadding, " +
        "itemVerticalPadding=$itemVerticalPadding, " +
        "insetPadding=$insetPadding, " +
        "indicatorPadding=$indicatorPadding, " +
        "indicatorEnd=$indicatorEnd, " +
        "gap=$gap, " +
        "textStyle=$textStyle, " +
        "iconSize=$iconSize, " +
        "highlight=$highlight, " +
        "highlightContent=$highlightContent, " +
        "destructive=$destructive, " +
        "destructiveHighlight=$destructiveHighlight, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "separatorColor=$separatorColor, " +
        "separatorSpacing=$separatorSpacing, " +
        "shortcutStyle=$shortcutStyle, " +
        "shortcutColor=$shortcutColor, " +
        "checkIcon=$checkIcon, " +
        "subIcon=$subIcon, " +
        "subOpenDelay=$subOpenDelay, " +
        "subCloseDelay=$subCloseDelay, " +
        "disabledAlpha=$disabledAlpha)"

    private fun values(): List<Any?> = listOf(
        panel,
        subPanel,
        minWidth,
        subMinWidth,
        itemShape,
        itemHorizontalPadding,
        itemVerticalPadding,
        insetPadding,
        indicatorPadding,
        indicatorEnd,
        gap,
        textStyle,
        iconSize,
        highlight,
        highlightContent,
        destructive,
        destructiveHighlight,
        labelStyle,
        labelColor,
        separatorColor,
        separatorSpacing,
        shortcutStyle,
        shortcutColor,
        checkIcon,
        subIcon,
        subOpenDelay,
        subCloseDelay,
        disabledAlpha,
    )
}
