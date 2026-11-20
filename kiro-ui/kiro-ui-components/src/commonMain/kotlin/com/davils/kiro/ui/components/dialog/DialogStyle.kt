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

package com.davils.kiro.ui.components.dialog

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The style of a [Dialog]: its panel, its layout, its texts, its footer, its close button and its scrim.
 *
 * @property panel the colors, the shape, the ring and the padding of the panel.
 * @property radius the radius of the bottom corners of the footer, which matches the corners of the panel.
 * @property maxWidth the largest width of the panel.
 * @property margin the smallest space between the panel and the edges of the window.
 * @property gap the space between the items of the content.
 * @property textStyle the style of the text of the content.
 * @property headerGap the space between the title and the description in the header.
 * @property titleStyle the style of the title.
 * @property descriptionStyle the style of the description.
 * @property descriptionColor the color of the description.
 * @property footerContainer the color of the container of the footer.
 * @property footerBorder the line above the footer.
 * @property footerGap the space between the actions of the footer.
 * @property closeIcon the icon of the close button.
 * @property closeOffset the space between the close button and the top end corner of the panel.
 * @property closeLabel the text that announces the close button.
 * @property scrim the color of the scrim behind the dialog.
 * @since 1.0.0
 */
@Immutable
public class DialogStyle(
    public val panel: PanelStyle,
    public val radius: Dp,
    public val maxWidth: Dp,
    public val margin: Dp,
    public val gap: Dp,
    public val textStyle: TextStyle,
    public val headerGap: Dp,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val footerContainer: Color,
    public val footerBorder: Border,
    public val footerGap: Dp,
    public val closeIcon: IconGraphic,
    public val closeOffset: Dp,
    public val closeLabel: String,
    public val scrim: Color,
) {
    /**
     * Returns a copy of this [DialogStyle] with the given values replaced and every other value kept.
     *
     * @param panel the new value of [panel].
     * @param radius the new value of [radius].
     * @param maxWidth the new value of [maxWidth].
     * @param margin the new value of [margin].
     * @param gap the new value of [gap].
     * @param textStyle the new value of [textStyle].
     * @param headerGap the new value of [headerGap].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionStyle the new value of [descriptionStyle].
     * @param descriptionColor the new value of [descriptionColor].
     * @param footerContainer the new value of [footerContainer].
     * @param footerBorder the new value of [footerBorder].
     * @param footerGap the new value of [footerGap].
     * @param closeIcon the new value of [closeIcon].
     * @param closeOffset the new value of [closeOffset].
     * @param closeLabel the new value of [closeLabel].
     * @param scrim the new value of [scrim].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        panel: PanelStyle = this.panel,
        radius: Dp = this.radius,
        maxWidth: Dp = this.maxWidth,
        margin: Dp = this.margin,
        gap: Dp = this.gap,
        textStyle: TextStyle = this.textStyle,
        headerGap: Dp = this.headerGap,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        footerContainer: Color = this.footerContainer,
        footerBorder: Border = this.footerBorder,
        footerGap: Dp = this.footerGap,
        closeIcon: IconGraphic = this.closeIcon,
        closeOffset: Dp = this.closeOffset,
        closeLabel: String = this.closeLabel,
        scrim: Color = this.scrim,
    ): DialogStyle = DialogStyle(
        panel = panel,
        radius = radius,
        maxWidth = maxWidth,
        margin = margin,
        gap = gap,
        textStyle = textStyle,
        headerGap = headerGap,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        footerContainer = footerContainer,
        footerBorder = footerBorder,
        footerGap = footerGap,
        closeIcon = closeIcon,
        closeOffset = closeOffset,
        closeLabel = closeLabel,
        scrim = scrim,
    )

    override fun equals(other: Any?): Boolean = other is DialogStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DialogStyle(" +
        "panel=$panel, " +
        "radius=$radius, " +
        "maxWidth=$maxWidth, " +
        "margin=$margin, " +
        "gap=$gap, " +
        "textStyle=$textStyle, " +
        "headerGap=$headerGap, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "footerContainer=$footerContainer, " +
        "footerBorder=$footerBorder, " +
        "footerGap=$footerGap, " +
        "closeIcon=$closeIcon, " +
        "closeOffset=$closeOffset, " +
        "closeLabel=$closeLabel, " +
        "scrim=$scrim)"

    private fun values(): List<Any?> = listOf(
        panel,
        radius,
        maxWidth,
        margin,
        gap,
        textStyle,
        headerGap,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        footerContainer,
        footerBorder,
        footerGap,
        closeIcon,
        closeOffset,
        closeLabel,
        scrim,
    )
}
