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

/**
 * The look of the addons of an [InputGroup] and of an [InputGroupText].
 *
 * @property gap the space between the parts of an addon and of an [InputGroupText].
 * @property inlinePadding the padding between an addon at the start or the end and the edge of the group.
 * @property verticalPadding the padding above and below an addon at the start or the end, and between an addon above
 * or below the control and the control.
 * @property blockHorizontalPadding the padding at the start and the end of an addon above or below the control.
 * @property blockPadding the padding between an addon above or below the control and the edge of the group.
 * @property textStyle the text style of the content of an addon.
 * @property plainTextStyle the text style of an [InputGroupText].
 * @property color the color of the content of an addon and of an [InputGroupText].
 * @property iconSize the size of the icons in an addon.
 * @property buttonOffset how far an addon at the start or the end that holds an [InputGroupButton] reaches into its
 * padding towards the edge of the group.
 * @since 1.0.0
 */
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
     * @since 1.0.0
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
