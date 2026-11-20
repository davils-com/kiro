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

package com.davils.kiro.ui.components.tabs

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.StateColors

@Immutable
public class TabsStyle(
    public val gap: Dp,
    public val listContainer: Color,
    public val listShape: Shape,
    public val listPadding: Dp,
    public val listHeight: Dp,
    public val listGap: Dp,
    public val triggerHeight: Dp,
    public val triggerShape: Shape,
    public val triggerHorizontalPadding: Dp,
    public val triggerIconEdgePadding: Dp,
    public val triggerGap: Dp,
    public val textStyle: TextStyle,
    public val iconSize: Dp,
    public val colors: StateColors,
    public val borderWidth: Dp,
    public val ringWidth: Dp,
    public val activeShadow: BoxShadow,
    public val indicator: TabsIndicator?,
    public val contentTextStyle: TextStyle,
    public val disabledAlpha: Float,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [TabsStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        listContainer: Color = this.listContainer,
        listShape: Shape = this.listShape,
        listPadding: Dp = this.listPadding,
        listHeight: Dp = this.listHeight,
        listGap: Dp = this.listGap,
        triggerHeight: Dp = this.triggerHeight,
        triggerShape: Shape = this.triggerShape,
        triggerHorizontalPadding: Dp = this.triggerHorizontalPadding,
        triggerIconEdgePadding: Dp = this.triggerIconEdgePadding,
        triggerGap: Dp = this.triggerGap,
        textStyle: TextStyle = this.textStyle,
        iconSize: Dp = this.iconSize,
        colors: StateColors = this.colors,
        borderWidth: Dp = this.borderWidth,
        ringWidth: Dp = this.ringWidth,
        activeShadow: BoxShadow = this.activeShadow,
        indicator: TabsIndicator? = this.indicator,
        contentTextStyle: TextStyle = this.contentTextStyle,
        disabledAlpha: Float = this.disabledAlpha,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): TabsStyle = TabsStyle(
        gap = gap,
        listContainer = listContainer,
        listShape = listShape,
        listPadding = listPadding,
        listHeight = listHeight,
        listGap = listGap,
        triggerHeight = triggerHeight,
        triggerShape = triggerShape,
        triggerHorizontalPadding = triggerHorizontalPadding,
        triggerIconEdgePadding = triggerIconEdgePadding,
        triggerGap = triggerGap,
        textStyle = textStyle,
        iconSize = iconSize,
        colors = colors,
        borderWidth = borderWidth,
        ringWidth = ringWidth,
        activeShadow = activeShadow,
        indicator = indicator,
        contentTextStyle = contentTextStyle,
        disabledAlpha = disabledAlpha,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is TabsStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TabsStyle(" +
        "gap=$gap, " +
        "listContainer=$listContainer, " +
        "listShape=$listShape, " +
        "listPadding=$listPadding, " +
        "listHeight=$listHeight, " +
        "listGap=$listGap, " +
        "triggerHeight=$triggerHeight, " +
        "triggerShape=$triggerShape, " +
        "triggerHorizontalPadding=$triggerHorizontalPadding, " +
        "triggerIconEdgePadding=$triggerIconEdgePadding, " +
        "triggerGap=$triggerGap, " +
        "textStyle=$textStyle, " +
        "iconSize=$iconSize, " +
        "colors=$colors, " +
        "borderWidth=$borderWidth, " +
        "ringWidth=$ringWidth, " +
        "activeShadow=$activeShadow, " +
        "indicator=$indicator, " +
        "contentTextStyle=$contentTextStyle, " +
        "disabledAlpha=$disabledAlpha, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        gap,
        listContainer,
        listShape,
        listPadding,
        listHeight,
        listGap,
        triggerHeight,
        triggerShape,
        triggerHorizontalPadding,
        triggerIconEdgePadding,
        triggerGap,
        textStyle,
        iconSize,
        colors,
        borderWidth,
        ringWidth,
        activeShadow,
        indicator,
        contentTextStyle,
        disabledAlpha,
        colorAnimation,
    )
}
