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

package com.davils.kiro.ui.components.empty

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * The style of an [Empty] and of its parts.
 *
 * @property shape the shape of the empty state.
 * @property border the border of the empty state, or `null` for none.
 * @property padding the padding inside the empty state.
 * @property gap the space between the parts of the empty state.
 * @property textStyle the text style of the content of the empty state.
 * @property headerGap the space between the parts of an [EmptyHeader].
 * @property maxContentWidth the largest width of an [EmptyHeader] and an [EmptyContent].
 * @property mediaBottomGap the extra space below an [EmptyMedia].
 * @property iconMediaSize the size of the container of an [EmptyMedia] of the variant [EmptyMediaVariant.Icon].
 * @property iconMediaShape the shape of that container.
 * @property iconMediaContainer the color of that container.
 * @property iconMediaContent the color of the icon on that container.
 * @property iconSize the size of the icon on that container.
 * @property titleStyle the text style of an [EmptyTitle].
 * @property descriptionStyle the text style of an [EmptyDescription].
 * @property descriptionColor the color of an [EmptyDescription].
 * @property contentGap the space between the parts of an [EmptyContent].
 * @since 1.0.0
 */
@Immutable
public class EmptyStyle(
    public val shape: Shape,
    public val border: Border?,
    public val padding: Dp,
    public val gap: Dp,
    public val textStyle: TextStyle,
    public val headerGap: Dp,
    public val maxContentWidth: Dp,
    public val mediaBottomGap: Dp,
    public val iconMediaSize: Dp,
    public val iconMediaShape: Shape,
    public val iconMediaContainer: Color,
    public val iconMediaContent: Color,
    public val iconSize: Dp,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val contentGap: Dp,
) {
    /**
     * Returns a copy of this [EmptyStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        shape: Shape = this.shape,
        border: Border? = this.border,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        textStyle: TextStyle = this.textStyle,
        headerGap: Dp = this.headerGap,
        maxContentWidth: Dp = this.maxContentWidth,
        mediaBottomGap: Dp = this.mediaBottomGap,
        iconMediaSize: Dp = this.iconMediaSize,
        iconMediaShape: Shape = this.iconMediaShape,
        iconMediaContainer: Color = this.iconMediaContainer,
        iconMediaContent: Color = this.iconMediaContent,
        iconSize: Dp = this.iconSize,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        contentGap: Dp = this.contentGap,
    ): EmptyStyle = EmptyStyle(
        shape = shape,
        border = border,
        padding = padding,
        gap = gap,
        textStyle = textStyle,
        headerGap = headerGap,
        maxContentWidth = maxContentWidth,
        mediaBottomGap = mediaBottomGap,
        iconMediaSize = iconMediaSize,
        iconMediaShape = iconMediaShape,
        iconMediaContainer = iconMediaContainer,
        iconMediaContent = iconMediaContent,
        iconSize = iconSize,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        contentGap = contentGap,
    )

    override fun equals(other: Any?): Boolean = other is EmptyStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "EmptyStyle(" +
        "shape=$shape, " +
        "border=${border ?: "null"}, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "textStyle=$textStyle, " +
        "headerGap=$headerGap, " +
        "maxContentWidth=$maxContentWidth, " +
        "mediaBottomGap=$mediaBottomGap, " +
        "iconMediaSize=$iconMediaSize, " +
        "iconMediaShape=$iconMediaShape, " +
        "iconMediaContainer=$iconMediaContainer, " +
        "iconMediaContent=$iconMediaContent, " +
        "iconSize=$iconSize, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "contentGap=$contentGap)"

    private fun values(): List<Any?> = listOf(
        shape,
        border,
        padding,
        gap,
        textStyle,
        headerGap,
        maxContentWidth,
        mediaBottomGap,
        iconMediaSize,
        iconMediaShape,
        iconMediaContainer,
        iconMediaContent,
        iconSize,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        contentGap,
    )
}
