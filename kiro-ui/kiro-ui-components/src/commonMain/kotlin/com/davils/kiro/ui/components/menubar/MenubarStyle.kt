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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.menu.MenuStyle
import com.davils.kiro.ui.core.style.Border

@Immutable
public class MenubarStyle(
    public val height: Dp,
    public val padding: Dp,
    public val gap: Dp,
    public val shape: Shape,
    public val border: Border,
    public val triggerShape: Shape,
    public val triggerHorizontalPadding: Dp,
    public val triggerVerticalPadding: Dp,
    public val triggerTextStyle: TextStyle,
    public val triggerActive: Color,
    public val menuMinWidth: Dp,
    public val sideOffset: Dp,
    public val alignOffset: Dp,
    public val menu: MenuStyle,
) {
    /**
     * Returns a copy of this [MenubarStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        height: Dp = this.height,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        shape: Shape = this.shape,
        border: Border = this.border,
        triggerShape: Shape = this.triggerShape,
        triggerHorizontalPadding: Dp = this.triggerHorizontalPadding,
        triggerVerticalPadding: Dp = this.triggerVerticalPadding,
        triggerTextStyle: TextStyle = this.triggerTextStyle,
        triggerActive: Color = this.triggerActive,
        menuMinWidth: Dp = this.menuMinWidth,
        sideOffset: Dp = this.sideOffset,
        alignOffset: Dp = this.alignOffset,
        menu: MenuStyle = this.menu,
    ): MenubarStyle = MenubarStyle(
        height = height,
        padding = padding,
        gap = gap,
        shape = shape,
        border = border,
        triggerShape = triggerShape,
        triggerHorizontalPadding = triggerHorizontalPadding,
        triggerVerticalPadding = triggerVerticalPadding,
        triggerTextStyle = triggerTextStyle,
        triggerActive = triggerActive,
        menuMinWidth = menuMinWidth,
        sideOffset = sideOffset,
        alignOffset = alignOffset,
        menu = menu,
    )

    override fun equals(other: Any?): Boolean = other is MenubarStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MenubarStyle(" +
        "height=$height, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "shape=$shape, " +
        "border=$border, " +
        "triggerShape=$triggerShape, " +
        "triggerHorizontalPadding=$triggerHorizontalPadding, " +
        "triggerVerticalPadding=$triggerVerticalPadding, " +
        "triggerTextStyle=$triggerTextStyle, " +
        "triggerActive=$triggerActive, " +
        "menuMinWidth=$menuMinWidth, " +
        "sideOffset=$sideOffset, " +
        "alignOffset=$alignOffset, " +
        "menu=$menu)"

    private fun values(): List<Any?> = listOf(
        height,
        padding,
        gap,
        shape,
        border,
        triggerShape,
        triggerHorizontalPadding,
        triggerVerticalPadding,
        triggerTextStyle,
        triggerActive,
        menuMinWidth,
        sideOffset,
        alignOffset,
        menu,
    )
}
