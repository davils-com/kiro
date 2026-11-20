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

package com.davils.kiro.ui.components.drawer

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

/**
 * The look and the drag behavior of a [Drawer].
 *
 * @property container the color of the panel.
 * @property content the content color of the panel.
 * @property border the border of the panel.
 * @property radius the corner radius of the corners of the panel away from its edge.
 * @property handleArea the thickness of the strip that holds the handle.
 * @property handleLength the length of the handle.
 * @property handleThickness the thickness of the handle.
 * @property handleColor the color of the handle.
 * @property textStyle the text style of the content.
 * @property sideFraction the width of a drawer at the start or end edge, as a fraction of the width of the window.
 * @property minSize the smallest width of a drawer at the start or end edge.
 * @property snapPoints the heights a drawer at the top or bottom edge settles at, as fractions of the available height;
 * it opens at the first one, and the list must not be empty.
 * @property edgeInset the part of the height of the window that a drawer at the top or bottom edge never covers.
 * @property sectionPadding the padding of the header and the footer.
 * @property headerGap the space between the parts of the header.
 * @property footerGap the space between the parts of the footer.
 * @property titleStyle the text style of the title.
 * @property descriptionStyle the text style of the description.
 * @property descriptionColor the color of the description.
 * @property scrim the color of the scrim behind the drawer.
 * @property dismissFraction how far a drawer has to be dragged towards its edge to close, as a fraction of its first
 * snap point or of its width.
 * @since 1.0.0
 */
@Immutable
public class DrawerStyle(
    public val container: Color,
    public val content: Color,
    public val border: Border,
    public val radius: Dp,
    public val handleArea: Dp,
    public val handleLength: Dp,
    public val handleThickness: Dp,
    public val handleColor: Color,
    public val textStyle: TextStyle,
    public val sideFraction: Float,
    public val minSize: Dp,
    public val snapPoints: List<Float>,
    public val edgeInset: Dp,
    public val sectionPadding: Dp,
    public val headerGap: Dp,
    public val footerGap: Dp,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val scrim: Color,
    public val dismissFraction: Float,
) {
    /**
     * Returns a copy of this [DrawerStyle] with the given values replaced and every other value kept.
     *
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param radius the new value of [radius].
     * @param handleArea the new value of [handleArea].
     * @param handleLength the new value of [handleLength].
     * @param handleThickness the new value of [handleThickness].
     * @param handleColor the new value of [handleColor].
     * @param textStyle the new value of [textStyle].
     * @param sideFraction the new value of [sideFraction].
     * @param minSize the new value of [minSize].
     * @param snapPoints the new value of [snapPoints].
     * @param edgeInset the new value of [edgeInset].
     * @param sectionPadding the new value of [sectionPadding].
     * @param headerGap the new value of [headerGap].
     * @param footerGap the new value of [footerGap].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionStyle the new value of [descriptionStyle].
     * @param descriptionColor the new value of [descriptionColor].
     * @param scrim the new value of [scrim].
     * @param dismissFraction the new value of [dismissFraction].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        container: Color = this.container,
        content: Color = this.content,
        border: Border = this.border,
        radius: Dp = this.radius,
        handleArea: Dp = this.handleArea,
        handleLength: Dp = this.handleLength,
        handleThickness: Dp = this.handleThickness,
        handleColor: Color = this.handleColor,
        textStyle: TextStyle = this.textStyle,
        sideFraction: Float = this.sideFraction,
        minSize: Dp = this.minSize,
        snapPoints: List<Float> = this.snapPoints,
        edgeInset: Dp = this.edgeInset,
        sectionPadding: Dp = this.sectionPadding,
        headerGap: Dp = this.headerGap,
        footerGap: Dp = this.footerGap,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        scrim: Color = this.scrim,
        dismissFraction: Float = this.dismissFraction,
    ): DrawerStyle = DrawerStyle(
        container = container,
        content = content,
        border = border,
        radius = radius,
        handleArea = handleArea,
        handleLength = handleLength,
        handleThickness = handleThickness,
        handleColor = handleColor,
        textStyle = textStyle,
        sideFraction = sideFraction,
        minSize = minSize,
        snapPoints = snapPoints,
        edgeInset = edgeInset,
        sectionPadding = sectionPadding,
        headerGap = headerGap,
        footerGap = footerGap,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        scrim = scrim,
        dismissFraction = dismissFraction,
    )

    override fun equals(other: Any?): Boolean = other is DrawerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DrawerStyle(" +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "radius=$radius, " +
        "handleArea=$handleArea, " +
        "handleLength=$handleLength, " +
        "handleThickness=$handleThickness, " +
        "handleColor=$handleColor, " +
        "textStyle=$textStyle, " +
        "sideFraction=$sideFraction, " +
        "minSize=$minSize, " +
        "snapPoints=$snapPoints, " +
        "edgeInset=$edgeInset, " +
        "sectionPadding=$sectionPadding, " +
        "headerGap=$headerGap, " +
        "footerGap=$footerGap, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "scrim=$scrim, " +
        "dismissFraction=$dismissFraction)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        border,
        radius,
        handleArea,
        handleLength,
        handleThickness,
        handleColor,
        textStyle,
        sideFraction,
        minSize,
        snapPoints,
        edgeInset,
        sectionPadding,
        headerGap,
        footerGap,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        scrim,
        dismissFraction,
    )
}
