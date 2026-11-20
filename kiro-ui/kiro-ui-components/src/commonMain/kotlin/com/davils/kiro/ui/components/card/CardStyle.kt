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

package com.davils.kiro.ui.components.card

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * The style of a [Card] and of its parts.
 *
 * @property shape the shape of the card.
 * @property container the color of the container of the card.
 * @property content the color of the content of the card.
 * @property ring the ring around the card.
 * @property spacing the space between the parts of the card, above the first part, below the last one and at the sides
 * of the header, body and footer.
 * @property textStyle the text style of the content of the card.
 * @property headerGap the space between the parts of a [CardHeader].
 * @property titleStyle the text style of a [CardTitle].
 * @property descriptionStyle the text style of a [CardDescription].
 * @property descriptionColor the color of a [CardDescription].
 * @property footerContainer the color of the container of a [CardFooter].
 * @property footerBorder the border on the top edge of a [CardFooter].
 * @since 1.0.0
 */
@Immutable
public class CardStyle(
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val ring: Border,
    public val spacing: Dp,
    public val textStyle: TextStyle,
    public val headerGap: Dp,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val footerContainer: Color,
    public val footerBorder: Border,
) {
    /**
     * Returns a copy of this [CardStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        ring: Border = this.ring,
        spacing: Dp = this.spacing,
        textStyle: TextStyle = this.textStyle,
        headerGap: Dp = this.headerGap,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        footerContainer: Color = this.footerContainer,
        footerBorder: Border = this.footerBorder,
    ): CardStyle = CardStyle(
        shape = shape,
        container = container,
        content = content,
        ring = ring,
        spacing = spacing,
        textStyle = textStyle,
        headerGap = headerGap,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        footerContainer = footerContainer,
        footerBorder = footerBorder,
    )

    override fun equals(other: Any?): Boolean = other is CardStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CardStyle(" +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "ring=$ring, " +
        "spacing=$spacing, " +
        "textStyle=$textStyle, " +
        "headerGap=$headerGap, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "footerContainer=$footerContainer, " +
        "footerBorder=$footerBorder)"

    private fun values(): List<Any?> = listOf(
        shape,
        container,
        content,
        ring,
        spacing,
        textStyle,
        headerGap,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        footerContainer,
        footerBorder,
    )
}
