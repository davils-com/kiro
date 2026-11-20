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

package com.davils.kiro.ui.components.field

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class FieldStyle(
    public val gap: Dp,
    public val setGap: Dp,
    public val groupGap: Dp,
    public val contentGap: Dp,
    public val legendSpacing: Dp,
    public val legendStyle: TextStyle,
    public val legendLabelStyle: TextStyle,
    public val labelStyle: TextStyle,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val errorStyle: TextStyle,
    public val errorIndent: Dp,
    public val errorGap: Dp,
    public val invalidColor: Color,
    public val disabledAlpha: Float,
    public val controlNudge: Dp,
    public val responsiveBreakpoint: Dp,
    public val separatorHeight: Dp,
    public val separatorOverlap: Dp,
    public val separatorColor: Color,
    public val separatorPadding: Dp,
    public val separatorBackground: Color,
) {
    /**
     * Returns a copy of this [FieldStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        setGap: Dp = this.setGap,
        groupGap: Dp = this.groupGap,
        contentGap: Dp = this.contentGap,
        legendSpacing: Dp = this.legendSpacing,
        legendStyle: TextStyle = this.legendStyle,
        legendLabelStyle: TextStyle = this.legendLabelStyle,
        labelStyle: TextStyle = this.labelStyle,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        errorStyle: TextStyle = this.errorStyle,
        errorIndent: Dp = this.errorIndent,
        errorGap: Dp = this.errorGap,
        invalidColor: Color = this.invalidColor,
        disabledAlpha: Float = this.disabledAlpha,
        controlNudge: Dp = this.controlNudge,
        responsiveBreakpoint: Dp = this.responsiveBreakpoint,
        separatorHeight: Dp = this.separatorHeight,
        separatorOverlap: Dp = this.separatorOverlap,
        separatorColor: Color = this.separatorColor,
        separatorPadding: Dp = this.separatorPadding,
        separatorBackground: Color = this.separatorBackground,
    ): FieldStyle = FieldStyle(
        gap = gap,
        setGap = setGap,
        groupGap = groupGap,
        contentGap = contentGap,
        legendSpacing = legendSpacing,
        legendStyle = legendStyle,
        legendLabelStyle = legendLabelStyle,
        labelStyle = labelStyle,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        errorStyle = errorStyle,
        errorIndent = errorIndent,
        errorGap = errorGap,
        invalidColor = invalidColor,
        disabledAlpha = disabledAlpha,
        controlNudge = controlNudge,
        responsiveBreakpoint = responsiveBreakpoint,
        separatorHeight = separatorHeight,
        separatorOverlap = separatorOverlap,
        separatorColor = separatorColor,
        separatorPadding = separatorPadding,
        separatorBackground = separatorBackground,
    )

    override fun equals(other: Any?): Boolean = other is FieldStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FieldStyle(" +
        "gap=$gap, " +
        "setGap=$setGap, " +
        "groupGap=$groupGap, " +
        "contentGap=$contentGap, " +
        "legendSpacing=$legendSpacing, " +
        "legendStyle=$legendStyle, " +
        "legendLabelStyle=$legendLabelStyle, " +
        "labelStyle=$labelStyle, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "errorStyle=$errorStyle, " +
        "errorIndent=$errorIndent, " +
        "errorGap=$errorGap, " +
        "invalidColor=$invalidColor, " +
        "disabledAlpha=$disabledAlpha, " +
        "controlNudge=$controlNudge, " +
        "responsiveBreakpoint=$responsiveBreakpoint, " +
        "separatorHeight=$separatorHeight, " +
        "separatorOverlap=$separatorOverlap, " +
        "separatorColor=$separatorColor, " +
        "separatorPadding=$separatorPadding, " +
        "separatorBackground=$separatorBackground)"

    private fun values(): List<Any?> = listOf(
        gap,
        setGap,
        groupGap,
        contentGap,
        legendSpacing,
        legendStyle,
        legendLabelStyle,
        labelStyle,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        errorStyle,
        errorIndent,
        errorGap,
        invalidColor,
        disabledAlpha,
        controlNudge,
        responsiveBreakpoint,
        separatorHeight,
        separatorOverlap,
        separatorColor,
        separatorPadding,
        separatorBackground,
    )
}
