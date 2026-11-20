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

package com.davils.kiro.ui.components.questionnaire

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The style of a [QuestionnaireChoice].
 *
 * @property minHeight the smallest height of a choice.
 * @property shape the shape of a choice.
 * @property padding the padding inside a choice.
 * @property gap the space between the indicator, the label and the shortcut of a choice.
 * @property container the color of the container of a choice.
 * @property hover the color of the container of a hovered choice.
 * @property checkedContainer the color of the container of a chosen choice.
 * @property border the color of the border of a choice.
 * @property checkedBorder the color of the border of a chosen choice.
 * @property invalidBorder the color of the border of a choice of a question that is missing its answer.
 * @property focusBorder the color of the border of a choice with a visible focus.
 * @property ring the color of the focus ring of a choice.
 * @property ringWidth the width of the focus ring of a choice.
 * @property disabledAlpha the opacity of a disabled choice.
 * @property labelStyle the text style of the label of a choice.
 * @property labelGap the space between the parts of the label of a choice.
 * @property descriptionColor the color of a [QuestionnaireChoiceDescription].
 * @property indicatorSize the size of the radio or checkbox indicator.
 * @property indicatorShape the shape of the checkbox indicator.
 * @property radioShape the shape of the radio indicator and of its dot.
 * @property indicatorOffset how far the indicator and the shortcut are moved down to line up with the first line of the
 * label.
 * @property indicatorBorder the color of the border of an indicator that is not checked.
 * @property indicatorContainer the color of an indicator that is not checked.
 * @property indicatorChecked the color of the container and the border of a checked indicator.
 * @property indicatorContent the color of the dot and the check mark of a checked indicator.
 * @property dotSize the size of the dot of a checked radio indicator.
 * @property checkIcon the check mark of a checked checkbox indicator.
 * @property checkSize the size of the check mark.
 * @property shortcutSize the size of the box that shows the shortcut.
 * @property shortcutShape the shape of that box.
 * @property shortcutBorder the color of the border of that box.
 * @property shortcutContainer the color of that box.
 * @property shortcutStyle the text style of the shortcut.
 * @property shortcutColor the color of the shortcut.
 * @since 1.0.0
 */
@Immutable
public class QuestionnaireChoiceStyle(
    public val minHeight: Dp,
    public val shape: Shape,
    public val padding: PaddingValues,
    public val gap: Dp,
    public val container: Color,
    public val hover: Color,
    public val checkedContainer: Color,
    public val border: Color,
    public val checkedBorder: Color,
    public val invalidBorder: Color,
    public val focusBorder: Color,
    public val ring: Color,
    public val ringWidth: Dp,
    public val disabledAlpha: Float,
    public val labelStyle: TextStyle,
    public val labelGap: Dp,
    public val descriptionColor: Color,
    public val indicatorSize: Dp,
    public val indicatorShape: Shape,
    public val radioShape: Shape,
    public val indicatorOffset: Dp,
    public val indicatorBorder: Color,
    public val indicatorContainer: Color,
    public val indicatorChecked: Color,
    public val indicatorContent: Color,
    public val dotSize: Dp,
    public val checkIcon: IconGraphic,
    public val checkSize: Dp,
    public val shortcutSize: Dp,
    public val shortcutShape: Shape,
    public val shortcutBorder: Color,
    public val shortcutContainer: Color,
    public val shortcutStyle: TextStyle,
    public val shortcutColor: Color,
) {
    /**
     * Returns a copy of this [QuestionnaireChoiceStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        minHeight: Dp = this.minHeight,
        shape: Shape = this.shape,
        padding: PaddingValues = this.padding,
        gap: Dp = this.gap,
        container: Color = this.container,
        hover: Color = this.hover,
        checkedContainer: Color = this.checkedContainer,
        border: Color = this.border,
        checkedBorder: Color = this.checkedBorder,
        invalidBorder: Color = this.invalidBorder,
        focusBorder: Color = this.focusBorder,
        ring: Color = this.ring,
        ringWidth: Dp = this.ringWidth,
        disabledAlpha: Float = this.disabledAlpha,
        labelStyle: TextStyle = this.labelStyle,
        labelGap: Dp = this.labelGap,
        descriptionColor: Color = this.descriptionColor,
        indicatorSize: Dp = this.indicatorSize,
        indicatorShape: Shape = this.indicatorShape,
        radioShape: Shape = this.radioShape,
        indicatorOffset: Dp = this.indicatorOffset,
        indicatorBorder: Color = this.indicatorBorder,
        indicatorContainer: Color = this.indicatorContainer,
        indicatorChecked: Color = this.indicatorChecked,
        indicatorContent: Color = this.indicatorContent,
        dotSize: Dp = this.dotSize,
        checkIcon: IconGraphic = this.checkIcon,
        checkSize: Dp = this.checkSize,
        shortcutSize: Dp = this.shortcutSize,
        shortcutShape: Shape = this.shortcutShape,
        shortcutBorder: Color = this.shortcutBorder,
        shortcutContainer: Color = this.shortcutContainer,
        shortcutStyle: TextStyle = this.shortcutStyle,
        shortcutColor: Color = this.shortcutColor,
    ): QuestionnaireChoiceStyle = QuestionnaireChoiceStyle(
        minHeight = minHeight,
        shape = shape,
        padding = padding,
        gap = gap,
        container = container,
        hover = hover,
        checkedContainer = checkedContainer,
        border = border,
        checkedBorder = checkedBorder,
        invalidBorder = invalidBorder,
        focusBorder = focusBorder,
        ring = ring,
        ringWidth = ringWidth,
        disabledAlpha = disabledAlpha,
        labelStyle = labelStyle,
        labelGap = labelGap,
        descriptionColor = descriptionColor,
        indicatorSize = indicatorSize,
        indicatorShape = indicatorShape,
        radioShape = radioShape,
        indicatorOffset = indicatorOffset,
        indicatorBorder = indicatorBorder,
        indicatorContainer = indicatorContainer,
        indicatorChecked = indicatorChecked,
        indicatorContent = indicatorContent,
        dotSize = dotSize,
        checkIcon = checkIcon,
        checkSize = checkSize,
        shortcutSize = shortcutSize,
        shortcutShape = shortcutShape,
        shortcutBorder = shortcutBorder,
        shortcutContainer = shortcutContainer,
        shortcutStyle = shortcutStyle,
        shortcutColor = shortcutColor,
    )

    override fun equals(other: Any?): Boolean = other is QuestionnaireChoiceStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "QuestionnaireChoiceStyle(" +
        "minHeight=$minHeight, " +
        "shape=$shape, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "container=$container, " +
        "hover=$hover, " +
        "checkedContainer=$checkedContainer, " +
        "border=$border, " +
        "checkedBorder=$checkedBorder, " +
        "invalidBorder=$invalidBorder, " +
        "focusBorder=$focusBorder, " +
        "ring=$ring, " +
        "ringWidth=$ringWidth, " +
        "disabledAlpha=$disabledAlpha, " +
        "labelStyle=$labelStyle, " +
        "labelGap=$labelGap, " +
        "descriptionColor=$descriptionColor, " +
        "indicatorSize=$indicatorSize, " +
        "indicatorShape=$indicatorShape, " +
        "radioShape=$radioShape, " +
        "indicatorOffset=$indicatorOffset, " +
        "indicatorBorder=$indicatorBorder, " +
        "indicatorContainer=$indicatorContainer, " +
        "indicatorChecked=$indicatorChecked, " +
        "indicatorContent=$indicatorContent, " +
        "dotSize=$dotSize, " +
        "checkIcon=$checkIcon, " +
        "checkSize=$checkSize, " +
        "shortcutSize=$shortcutSize, " +
        "shortcutShape=$shortcutShape, " +
        "shortcutBorder=$shortcutBorder, " +
        "shortcutContainer=$shortcutContainer, " +
        "shortcutStyle=$shortcutStyle, " +
        "shortcutColor=$shortcutColor)"

    private fun values(): List<Any?> = listOf(
        minHeight,
        shape,
        padding,
        gap,
        container,
        hover,
        checkedContainer,
        border,
        checkedBorder,
        invalidBorder,
        focusBorder,
        ring,
        ringWidth,
        disabledAlpha,
        labelStyle,
        labelGap,
        descriptionColor,
        indicatorSize,
        indicatorShape,
        radioShape,
        indicatorOffset,
        indicatorBorder,
        indicatorContainer,
        indicatorChecked,
        indicatorContent,
        dotSize,
        checkIcon,
        checkSize,
        shortcutSize,
        shortcutShape,
        shortcutBorder,
        shortcutContainer,
        shortcutStyle,
        shortcutColor,
    )
}
