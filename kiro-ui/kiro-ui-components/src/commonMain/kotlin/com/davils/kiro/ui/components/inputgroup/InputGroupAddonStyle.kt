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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class InputGroupAddonStyle(
    public val gap: Dp,
    public val inlinePadding: Dp,
    public val verticalPadding: Dp,
    public val blockHorizontalPadding: Dp,
    public val blockPadding: Dp,
    public val textStyle: TextStyle,
    public val plainTextStyle: TextStyle,
    public val color: Color,
    public val iconSize: Dp,
    public val buttonOffset: Dp,
) {
    /**
     * Returns a copy of this [InputGroupAddonStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        inlinePadding: Dp = this.inlinePadding,
        verticalPadding: Dp = this.verticalPadding,
        blockHorizontalPadding: Dp = this.blockHorizontalPadding,
        blockPadding: Dp = this.blockPadding,
        textStyle: TextStyle = this.textStyle,
        plainTextStyle: TextStyle = this.plainTextStyle,
        color: Color = this.color,
        iconSize: Dp = this.iconSize,
        buttonOffset: Dp = this.buttonOffset,
    ): InputGroupAddonStyle = InputGroupAddonStyle(
        gap = gap,
        inlinePadding = inlinePadding,
        verticalPadding = verticalPadding,
        blockHorizontalPadding = blockHorizontalPadding,
        blockPadding = blockPadding,
        textStyle = textStyle,
        plainTextStyle = plainTextStyle,
        color = color,
        iconSize = iconSize,
        buttonOffset = buttonOffset,
    )

    override fun equals(other: Any?): Boolean = other is InputGroupAddonStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputGroupAddonStyle(" +
        "gap=$gap, " +
        "inlinePadding=$inlinePadding, " +
        "verticalPadding=$verticalPadding, " +
        "blockHorizontalPadding=$blockHorizontalPadding, " +
        "blockPadding=$blockPadding, " +
        "textStyle=$textStyle, " +
        "plainTextStyle=$plainTextStyle, " +
        "color=$color, " +
        "iconSize=$iconSize, " +
        "buttonOffset=$buttonOffset)"

    private fun values(): List<Any?> = listOf(
        gap,
        inlinePadding,
        verticalPadding,
        blockHorizontalPadding,
        blockPadding,
        textStyle,
        plainTextStyle,
        color,
        iconSize,
        buttonOffset,
    )
}
