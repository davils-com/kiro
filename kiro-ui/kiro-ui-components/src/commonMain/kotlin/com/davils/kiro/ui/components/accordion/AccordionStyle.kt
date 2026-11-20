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
     * @since 0.1.0
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
