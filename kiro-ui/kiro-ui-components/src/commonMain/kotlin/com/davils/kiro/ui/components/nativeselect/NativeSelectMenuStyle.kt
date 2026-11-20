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
     * @since 0.1.0
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
