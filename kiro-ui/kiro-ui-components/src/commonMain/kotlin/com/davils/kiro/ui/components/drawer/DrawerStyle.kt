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
     * @since 0.1.0
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
