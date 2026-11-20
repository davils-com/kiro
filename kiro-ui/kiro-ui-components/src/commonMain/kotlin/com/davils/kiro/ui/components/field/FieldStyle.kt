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

/**
 * How the fields of a form and their parts look: their spacing, their texts, their errors and their separators.
 *
 * @property gap the space between the children of a [Field].
 * @property setGap the space between the children of a [FieldSet].
 * @property groupGap the space between the children of a [FieldGroup].
 * @property contentGap the space between the children of a [FieldContent].
 * @property legendSpacing the space below a [FieldLegend].
 * @property legendStyle the text style of a [FieldLegend] of the variant [FieldLegendVariant.Legend].
 * @property legendLabelStyle the text style of a [FieldLegend] of the variant [FieldLegendVariant.Label].
 * @property labelStyle the text style of a [FieldLabel].
 * @property titleStyle the text style of a [FieldTitle].
 * @property descriptionStyle the text style of a [FieldDescription] and of the label of a [FieldSeparator].
 * @property descriptionColor the color of a [FieldDescription] and of the label of a [FieldSeparator].
 * @property errorStyle the text style of a [FieldError].
 * @property errorIndent the indent of the list of a [FieldError] with more than one message.
 * @property errorGap the space between the messages of a [FieldError], and between a bullet and its message.
 * @property invalidColor the color of a [FieldError], and the content color of an invalid [Field].
 * @property disabledAlpha the alpha of the label and the title of a disabled [Field].
 * @property controlNudge how far the control of a horizontal [Field] moves down, so that it lines up with the first
 * line of the [FieldContent] next to it.
 * @property responsiveBreakpoint the smallest width at which a [Field] of the orientation [FieldOrientation.Responsive]
 * lays out its children side by side.
 * @property separatorHeight the height of a [FieldSeparator].
 * @property separatorOverlap how far a [FieldSeparator] reaches into the gaps above and below it.
 * @property separatorColor the color meant for the line of a [FieldSeparator]; the line is drawn by a separator, which
 * takes its color from its own style.
 * @property separatorPadding the space at the start and the end of the label of a [FieldSeparator].
 * @property separatorBackground the color behind the label of a [FieldSeparator], which hides the line under the label.
 * @since 1.0.0
 */
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
     * @param gap the new value of [gap].
     * @param setGap the new value of [setGap].
     * @param groupGap the new value of [groupGap].
     * @param contentGap the new value of [contentGap].
     * @param legendSpacing the new value of [legendSpacing].
     * @param legendStyle the new value of [legendStyle].
     * @param legendLabelStyle the new value of [legendLabelStyle].
     * @param labelStyle the new value of [labelStyle].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionStyle the new value of [descriptionStyle].
     * @param descriptionColor the new value of [descriptionColor].
     * @param errorStyle the new value of [errorStyle].
     * @param errorIndent the new value of [errorIndent].
     * @param errorGap the new value of [errorGap].
     * @param invalidColor the new value of [invalidColor].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param controlNudge the new value of [controlNudge].
     * @param responsiveBreakpoint the new value of [responsiveBreakpoint].
     * @param separatorHeight the new value of [separatorHeight].
     * @param separatorOverlap the new value of [separatorOverlap].
     * @param separatorColor the new value of [separatorColor].
     * @param separatorPadding the new value of [separatorPadding].
     * @param separatorBackground the new value of [separatorBackground].
     * @return the copy.
     * @since 1.0.0
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
