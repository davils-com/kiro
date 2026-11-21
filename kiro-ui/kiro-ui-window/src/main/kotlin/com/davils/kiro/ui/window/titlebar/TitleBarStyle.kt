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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.window.WindowLabels
import com.davils.kiro.ui.window.controls.WindowControls

/**
 * How a title bar looks: its colors while the window is active and inactive, where the title sits, the window
 * buttons and the badges of its buttons.
 *
 * @property height the height of the bar.
 * @property background the color of the bar while the window is active.
 * @property inactiveBackground the color of the bar while another window is active.
 * @property content the color of the title and the icons while the window is active.
 * @property inactiveContent the color of the title and the icons while another window is active.
 * @property divider the line under the bar, or `null` for none.
 * @property titleStyle the text style of the title and of the texts in the slots.
 * @property horizontalPadding the space between the window buttons and the slots.
 * @property gap the space between the slots and between the parts of a slot.
 * @property titlePlacement where the content of the center slot sits.
 * @property controls the window buttons that Kiro draws.
 * @property controlColors the colors of the minimize and maximize buttons.
 * @property closeColors the colors of the close button.
 * @property buttonVariant the variant of a [com.davils.kiro.ui.window.action.TitleBarButton].
 * @property buttonSize the size of a [com.davils.kiro.ui.window.action.TitleBarButton].
 * @property badgeColor the color of a badge.
 * @property badgeContent the color of the count in a badge.
 * @property badgeTextStyle the text style of the count in a badge.
 * @property badgeDotSize the size of a badge without a count.
 * @property badgeMinSize the smallest width and height of a badge with a count.
 * @property badgePadding the horizontal padding of a badge with a count.
 * @property badgeOffset how far a badge moves from the top end corner of its button.
 * @property badgeLimit the largest count a badge shows before it shows the limit with a plus.
 * @property colorAnimation how the colors change between the active and the inactive window.
 * @property labels the texts the bar announces.
 * @since 1.0.0
 */
@Immutable
public class TitleBarStyle(
    public val height: Dp,
    public val background: Color,
    public val inactiveBackground: Color,
    public val content: Color,
    public val inactiveContent: Color,
    public val divider: Border?,
    public val titleStyle: TextStyle,
    public val horizontalPadding: Dp,
    public val gap: Dp,
    public val titlePlacement: TitlePlacement,
    public val controls: WindowControls,
    public val controlColors: StateColors,
    public val closeColors: StateColors,
    public val buttonVariant: ButtonVariant,
    public val buttonSize: ButtonSize,
    public val badgeColor: Color,
    public val badgeContent: Color,
    public val badgeTextStyle: TextStyle,
    public val badgeDotSize: Dp,
    public val badgeMinSize: Dp,
    public val badgePadding: Dp,
    public val badgeOffset: DpOffset,
    public val badgeLimit: Int,
    public val colorAnimation: AnimationSpec<Color>,
    public val labels: WindowLabels,
) {
    /**
     * Returns a copy of this [TitleBarStyle] with the given values replaced and every other value kept.
     *
     * @param height the new value of [height].
     * @param background the new value of [background].
     * @param inactiveBackground the new value of [inactiveBackground].
     * @param content the new value of [content].
     * @param inactiveContent the new value of [inactiveContent].
     * @param divider the new value of [divider].
     * @param titleStyle the new value of [titleStyle].
     * @param horizontalPadding the new value of [horizontalPadding].
     * @param gap the new value of [gap].
     * @param titlePlacement the new value of [titlePlacement].
     * @param controls the new value of [controls].
     * @param controlColors the new value of [controlColors].
     * @param closeColors the new value of [closeColors].
     * @param buttonVariant the new value of [buttonVariant].
     * @param buttonSize the new value of [buttonSize].
     * @param badgeColor the new value of [badgeColor].
     * @param badgeContent the new value of [badgeContent].
     * @param badgeTextStyle the new value of [badgeTextStyle].
     * @param badgeDotSize the new value of [badgeDotSize].
     * @param badgeMinSize the new value of [badgeMinSize].
     * @param badgePadding the new value of [badgePadding].
     * @param badgeOffset the new value of [badgeOffset].
     * @param badgeLimit the new value of [badgeLimit].
     * @param colorAnimation the new value of [colorAnimation].
     * @param labels the new value of [labels].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        height: Dp = this.height,
        background: Color = this.background,
        inactiveBackground: Color = this.inactiveBackground,
        content: Color = this.content,
        inactiveContent: Color = this.inactiveContent,
        divider: Border? = this.divider,
        titleStyle: TextStyle = this.titleStyle,
        horizontalPadding: Dp = this.horizontalPadding,
        gap: Dp = this.gap,
        titlePlacement: TitlePlacement = this.titlePlacement,
        controls: WindowControls = this.controls,
        controlColors: StateColors = this.controlColors,
        closeColors: StateColors = this.closeColors,
        buttonVariant: ButtonVariant = this.buttonVariant,
        buttonSize: ButtonSize = this.buttonSize,
        badgeColor: Color = this.badgeColor,
        badgeContent: Color = this.badgeContent,
        badgeTextStyle: TextStyle = this.badgeTextStyle,
        badgeDotSize: Dp = this.badgeDotSize,
        badgeMinSize: Dp = this.badgeMinSize,
        badgePadding: Dp = this.badgePadding,
        badgeOffset: DpOffset = this.badgeOffset,
        badgeLimit: Int = this.badgeLimit,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
        labels: WindowLabels = this.labels,
    ): TitleBarStyle = TitleBarStyle(
        height = height,
        background = background,
        inactiveBackground = inactiveBackground,
        content = content,
        inactiveContent = inactiveContent,
        divider = divider,
        titleStyle = titleStyle,
        horizontalPadding = horizontalPadding,
        gap = gap,
        titlePlacement = titlePlacement,
        controls = controls,
        controlColors = controlColors,
        closeColors = closeColors,
        buttonVariant = buttonVariant,
        buttonSize = buttonSize,
        badgeColor = badgeColor,
        badgeContent = badgeContent,
        badgeTextStyle = badgeTextStyle,
        badgeDotSize = badgeDotSize,
        badgeMinSize = badgeMinSize,
        badgePadding = badgePadding,
        badgeOffset = badgeOffset,
        badgeLimit = badgeLimit,
        colorAnimation = colorAnimation,
        labels = labels,
    )

    override fun equals(other: Any?): Boolean = other is TitleBarStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TitleBarStyle(" +
        "height=$height, " +
        "background=$background, " +
        "inactiveBackground=$inactiveBackground, " +
        "content=$content, " +
        "inactiveContent=$inactiveContent, " +
        "divider=${divider ?: "null"}, " +
        "titleStyle=$titleStyle, " +
        "horizontalPadding=$horizontalPadding, " +
        "gap=$gap, " +
        "titlePlacement=$titlePlacement, " +
        "controls=$controls, " +
        "controlColors=$controlColors, " +
        "closeColors=$closeColors, " +
        "buttonVariant=$buttonVariant, " +
        "buttonSize=$buttonSize, " +
        "badgeColor=$badgeColor, " +
        "badgeContent=$badgeContent, " +
        "badgeTextStyle=$badgeTextStyle, " +
        "badgeDotSize=$badgeDotSize, " +
        "badgeMinSize=$badgeMinSize, " +
        "badgePadding=$badgePadding, " +
        "badgeOffset=$badgeOffset, " +
        "badgeLimit=$badgeLimit, " +
        "colorAnimation=$colorAnimation, " +
        "labels=$labels)"

    private fun values(): List<Any?> = listOf(
        height,
        background,
        inactiveBackground,
        content,
        inactiveContent,
        divider,
        titleStyle,
        horizontalPadding,
        gap,
        titlePlacement,
        controls,
        controlColors,
        closeColors,
        buttonVariant,
        buttonSize,
        badgeColor,
        badgeContent,
        badgeTextStyle,
        badgeDotSize,
        badgeMinSize,
        badgePadding,
        badgeOffset,
        badgeLimit,
        colorAnimation,
        labels,
    )
}
