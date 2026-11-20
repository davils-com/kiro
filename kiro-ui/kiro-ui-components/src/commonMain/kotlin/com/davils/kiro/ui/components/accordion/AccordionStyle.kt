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

package com.davils.kiro.ui.components.accordion

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.collapsible.CollapsibleStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The look of an [Accordion]: its trigger rows, icons, content, dividers, focus indication and motion.
 *
 * @property triggerPadding the vertical padding of a trigger row.
 * @property triggerShape the shape of the focus border and ring around a trigger row.
 * @property triggerTextStyle the text style of the content of a trigger row.
 * @property isUnderlinedOnHover whether the text of a trigger is underlined while the pointer hovers over it.
 * @property iconSize the size of the expand and collapse icons.
 * @property iconColor the color of the expand and collapse icons.
 * @property expandIcon the icon of a collapsed item.
 * @property collapseIcon the icon of an expanded item.
 * @property contentTextStyle the text style of the content of an expanded item.
 * @property contentPadding the padding below the content of an expanded item.
 * @property divider the line drawn below every item but the last.
 * @property focusBorder the border of a trigger row while its focus is visible.
 * @property focusRing the ring around a trigger row while its focus is visible.
 * @property disabledAlpha the opacity of the trigger row of a disabled item.
 * @property colorAnimation the animation of the colors of the focus border and ring.
 * @property motion the transitions with which the content of an item appears and disappears.
 * @since 1.0.0
 */
@Immutable
public class AccordionStyle(
    public val triggerPadding: Dp,
    public val triggerShape: Shape,
    public val triggerTextStyle: TextStyle,
    public val isUnderlinedOnHover: Boolean,
    public val iconSize: Dp,
    public val iconColor: Color,
    public val expandIcon: IconGraphic,
    public val collapseIcon: IconGraphic,
    public val contentTextStyle: TextStyle,
    public val contentPadding: Dp,
    public val divider: Border,
    public val focusBorder: Border,
    public val focusRing: Border,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
    public val motion: CollapsibleStyle,
) {
    /**
     * Returns a copy of this [AccordionStyle] with the given values replaced and every other value kept.
     *
     * @param triggerPadding the new value of [triggerPadding].
     * @param triggerShape the new value of [triggerShape].
     * @param triggerTextStyle the new value of [triggerTextStyle].
     * @param isUnderlinedOnHover the new value of [isUnderlinedOnHover].
     * @param iconSize the new value of [iconSize].
     * @param iconColor the new value of [iconColor].
     * @param expandIcon the new value of [expandIcon].
     * @param collapseIcon the new value of [collapseIcon].
     * @param contentTextStyle the new value of [contentTextStyle].
     * @param contentPadding the new value of [contentPadding].
     * @param divider the new value of [divider].
     * @param focusBorder the new value of [focusBorder].
     * @param focusRing the new value of [focusRing].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @param colorAnimation the new value of [colorAnimation].
     * @param motion the new value of [motion].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        triggerPadding: Dp = this.triggerPadding,
        triggerShape: Shape = this.triggerShape,
        triggerTextStyle: TextStyle = this.triggerTextStyle,
        isUnderlinedOnHover: Boolean = this.isUnderlinedOnHover,
        iconSize: Dp = this.iconSize,
        iconColor: Color = this.iconColor,
        expandIcon: IconGraphic = this.expandIcon,
        collapseIcon: IconGraphic = this.collapseIcon,
        contentTextStyle: TextStyle = this.contentTextStyle,
        contentPadding: Dp = this.contentPadding,
        divider: Border = this.divider,
        focusBorder: Border = this.focusBorder,
        focusRing: Border = this.focusRing,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
        motion: CollapsibleStyle = this.motion,
    ): AccordionStyle = AccordionStyle(
        triggerPadding = triggerPadding,
        triggerShape = triggerShape,
        triggerTextStyle = triggerTextStyle,
        isUnderlinedOnHover = isUnderlinedOnHover,
        iconSize = iconSize,
        iconColor = iconColor,
        expandIcon = expandIcon,
        collapseIcon = collapseIcon,
        contentTextStyle = contentTextStyle,
        contentPadding = contentPadding,
        divider = divider,
        focusBorder = focusBorder,
        focusRing = focusRing,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
        motion = motion,
    )

    override fun equals(other: Any?): Boolean = other is AccordionStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AccordionStyle(" +
        "triggerPadding=$triggerPadding, " +
        "triggerShape=$triggerShape, " +
        "triggerTextStyle=$triggerTextStyle, " +
        "isUnderlinedOnHover=$isUnderlinedOnHover, " +
        "iconSize=$iconSize, " +
        "iconColor=$iconColor, " +
        "expandIcon=$expandIcon, " +
        "collapseIcon=$collapseIcon, " +
        "contentTextStyle=$contentTextStyle, " +
        "contentPadding=$contentPadding, " +
        "divider=$divider, " +
        "focusBorder=$focusBorder, " +
        "focusRing=$focusRing, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation, " +
        "motion=$motion)"

    private fun values(): List<Any?> = listOf(
        triggerPadding,
        triggerShape,
        triggerTextStyle,
        isUnderlinedOnHover,
        iconSize,
        iconColor,
        expandIcon,
        collapseIcon,
        contentTextStyle,
        contentPadding,
        divider,
        focusBorder,
        focusRing,
        disabledAlpha,
        colorAnimation,
        motion,
    )
}
