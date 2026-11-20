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
     * @since 0.1.0
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
