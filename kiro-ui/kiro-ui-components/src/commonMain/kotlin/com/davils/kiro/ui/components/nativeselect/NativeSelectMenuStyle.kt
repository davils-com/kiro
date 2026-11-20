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

package com.davils.kiro.ui.components.nativeselect

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.Border

/**
 * The style of the menu of a [NativeSelect], which lists its options and groups below the field.
 *
 * @property container the color of the panel.
 * @property content the color of the text of the options.
 * @property shape the shape of the panel, its ring and its shadow.
 * @property ring the line around the panel.
 * @property shadow the shadow of the panel.
 * @property padding the padding inside the panel.
 * @property offset the space between the field and the panel.
 * @property itemPadding the padding around the text of an option, which leaves room for the check mark at the end.
 * @property itemShape the shape of the highlight of an option.
 * @property itemTextStyle the style of the text of an option.
 * @property highlight the color behind the highlighted option.
 * @property highlightContent the color of the text of the highlighted option.
 * @property groupPadding the padding around the heading of a group.
 * @property groupTextStyle the style of the heading of a group.
 * @property groupColor the color of the heading of a group.
 * @property check the check mark at the end of the picked option.
 * @property disabledAlpha the opacity of a disabled option.
 * @since 1.0.0
 */
@Immutable
public class NativeSelectMenuStyle(
    public val container: Color,
    public val content: Color,
    public val shape: Shape,
    public val ring: Border,
    public val shadow: BoxShadow,
    public val padding: Dp,
    public val offset: Dp,
    public val itemPadding: PaddingValues,
    public val itemShape: Shape,
    public val itemTextStyle: TextStyle,
    public val highlight: Color,
    public val highlightContent: Color,
    public val groupPadding: PaddingValues,
    public val groupTextStyle: TextStyle,
    public val groupColor: Color,
    public val check: NativeSelectIcon,
    public val disabledAlpha: Float,
) {
    /**
     * Returns a copy of this [NativeSelectMenuStyle] with the given values replaced and every other value kept.
     *
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param shape the new value of [shape].
     * @param ring the new value of [ring].
     * @param shadow the new value of [shadow].
     * @param padding the new value of [padding].
     * @param offset the new value of [offset].
     * @param itemPadding the new value of [itemPadding].
     * @param itemShape the new value of [itemShape].
     * @param itemTextStyle the new value of [itemTextStyle].
     * @param highlight the new value of [highlight].
     * @param highlightContent the new value of [highlightContent].
     * @param groupPadding the new value of [groupPadding].
     * @param groupTextStyle the new value of [groupTextStyle].
     * @param groupColor the new value of [groupColor].
     * @param check the new value of [check].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        container: Color = this.container,
        content: Color = this.content,
        shape: Shape = this.shape,
        ring: Border = this.ring,
        shadow: BoxShadow = this.shadow,
        padding: Dp = this.padding,
        offset: Dp = this.offset,
        itemPadding: PaddingValues = this.itemPadding,
        itemShape: Shape = this.itemShape,
        itemTextStyle: TextStyle = this.itemTextStyle,
        highlight: Color = this.highlight,
        highlightContent: Color = this.highlightContent,
        groupPadding: PaddingValues = this.groupPadding,
        groupTextStyle: TextStyle = this.groupTextStyle,
        groupColor: Color = this.groupColor,
        check: NativeSelectIcon = this.check,
        disabledAlpha: Float = this.disabledAlpha,
    ): NativeSelectMenuStyle = NativeSelectMenuStyle(
        container = container,
        content = content,
        shape = shape,
        ring = ring,
        shadow = shadow,
        padding = padding,
        offset = offset,
        itemPadding = itemPadding,
        itemShape = itemShape,
        itemTextStyle = itemTextStyle,
        highlight = highlight,
        highlightContent = highlightContent,
        groupPadding = groupPadding,
        groupTextStyle = groupTextStyle,
        groupColor = groupColor,
        check = check,
        disabledAlpha = disabledAlpha,
    )

    override fun equals(other: Any?): Boolean = other is NativeSelectMenuStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "NativeSelectMenuStyle(" +
        "container=$container, " +
        "content=$content, " +
        "shape=$shape, " +
        "ring=$ring, " +
        "shadow=$shadow, " +
        "padding=$padding, " +
        "offset=$offset, " +
        "itemPadding=$itemPadding, " +
        "itemShape=$itemShape, " +
        "itemTextStyle=$itemTextStyle, " +
        "highlight=$highlight, " +
        "highlightContent=$highlightContent, " +
        "groupPadding=$groupPadding, " +
        "groupTextStyle=$groupTextStyle, " +
        "groupColor=$groupColor, " +
        "check=$check, " +
        "disabledAlpha=$disabledAlpha)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        shape,
        ring,
        shadow,
        padding,
        offset,
        itemPadding,
        itemShape,
        itemTextStyle,
        highlight,
        highlightContent,
        groupPadding,
        groupTextStyle,
        groupColor,
        check,
        disabledAlpha,
    )
}
