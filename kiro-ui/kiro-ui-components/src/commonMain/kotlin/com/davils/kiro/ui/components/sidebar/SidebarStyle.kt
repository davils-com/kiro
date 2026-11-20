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

package com.davils.kiro.ui.components.sidebar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class SidebarStyle(
    public val width: Dp,
    public val iconWidth: Dp,
    public val container: Color,
    public val content: Color,
    public val border: Color,
    public val accent: Color,
    public val accentContent: Color,
    public val ring: Color,
    public val background: Color,
    public val floatingPadding: Dp,
    public val floatingShape: Shape,
    public val insetShape: Shape,
    public val shadow: BoxShadow,
    public val section: Dp,
    public val labelHeight: Dp,
    public val labelStyle: TextStyle,
    public val labelColor: Color,
    public val buttonShape: Shape,
    public val buttonPadding: Dp,
    public val buttonGap: Dp,
    public val textStyle: TextStyle,
    public val smallTextStyle: TextStyle,
    public val iconSize: Dp,
    public val actionSize: Dp,
    public val badgeStyle: TextStyle,
    public val subIndent: Dp,
    public val subPadding: Dp,
    public val subGap: Dp,
    public val subHeight: Dp,
    public val triggerIcon: IconGraphic,
    public val toggleLabel: String,
    public val railWidth: Dp,
    public val widthAnimation: AnimationSpec<Dp>,
    public val chevron: IconGraphic,
    public val brandSize: Dp,
    public val brandShape: Shape,
    public val brandContainer: Color,
    public val brandContent: Color,
    public val brandTitleStyle: TextStyle,
    public val brandSubtitleStyle: TextStyle,
    public val brandSubtitleColor: Color,
) {
    /**
     * Returns a copy of this [SidebarStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        width: Dp = this.width,
        iconWidth: Dp = this.iconWidth,
        container: Color = this.container,
        content: Color = this.content,
        border: Color = this.border,
        accent: Color = this.accent,
        accentContent: Color = this.accentContent,
        ring: Color = this.ring,
        background: Color = this.background,
        floatingPadding: Dp = this.floatingPadding,
        floatingShape: Shape = this.floatingShape,
        insetShape: Shape = this.insetShape,
        shadow: BoxShadow = this.shadow,
        section: Dp = this.section,
        labelHeight: Dp = this.labelHeight,
        labelStyle: TextStyle = this.labelStyle,
        labelColor: Color = this.labelColor,
        buttonShape: Shape = this.buttonShape,
        buttonPadding: Dp = this.buttonPadding,
        buttonGap: Dp = this.buttonGap,
        textStyle: TextStyle = this.textStyle,
        smallTextStyle: TextStyle = this.smallTextStyle,
        iconSize: Dp = this.iconSize,
        actionSize: Dp = this.actionSize,
        badgeStyle: TextStyle = this.badgeStyle,
        subIndent: Dp = this.subIndent,
        subPadding: Dp = this.subPadding,
        subGap: Dp = this.subGap,
        subHeight: Dp = this.subHeight,
        triggerIcon: IconGraphic = this.triggerIcon,
        toggleLabel: String = this.toggleLabel,
        railWidth: Dp = this.railWidth,
        widthAnimation: AnimationSpec<Dp> = this.widthAnimation,
        chevron: IconGraphic = this.chevron,
        brandSize: Dp = this.brandSize,
        brandShape: Shape = this.brandShape,
        brandContainer: Color = this.brandContainer,
        brandContent: Color = this.brandContent,
        brandTitleStyle: TextStyle = this.brandTitleStyle,
        brandSubtitleStyle: TextStyle = this.brandSubtitleStyle,
        brandSubtitleColor: Color = this.brandSubtitleColor,
    ): SidebarStyle = SidebarStyle(
        width = width,
        iconWidth = iconWidth,
        container = container,
        content = content,
        border = border,
        accent = accent,
        accentContent = accentContent,
        ring = ring,
        background = background,
        floatingPadding = floatingPadding,
        floatingShape = floatingShape,
        insetShape = insetShape,
        shadow = shadow,
        section = section,
        labelHeight = labelHeight,
        labelStyle = labelStyle,
        labelColor = labelColor,
        buttonShape = buttonShape,
        buttonPadding = buttonPadding,
        buttonGap = buttonGap,
        textStyle = textStyle,
        smallTextStyle = smallTextStyle,
        iconSize = iconSize,
        actionSize = actionSize,
        badgeStyle = badgeStyle,
        subIndent = subIndent,
        subPadding = subPadding,
        subGap = subGap,
        subHeight = subHeight,
        triggerIcon = triggerIcon,
        toggleLabel = toggleLabel,
        railWidth = railWidth,
        widthAnimation = widthAnimation,
        chevron = chevron,
        brandSize = brandSize,
        brandShape = brandShape,
        brandContainer = brandContainer,
        brandContent = brandContent,
        brandTitleStyle = brandTitleStyle,
        brandSubtitleStyle = brandSubtitleStyle,
        brandSubtitleColor = brandSubtitleColor,
    )

    override fun equals(other: Any?): Boolean = other is SidebarStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SidebarStyle(" +
        "width=$width, " +
        "iconWidth=$iconWidth, " +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "accent=$accent, " +
        "accentContent=$accentContent, " +
        "ring=$ring, " +
        "background=$background, " +
        "floatingPadding=$floatingPadding, " +
        "floatingShape=$floatingShape, " +
        "insetShape=$insetShape, " +
        "shadow=$shadow, " +
        "section=$section, " +
        "labelHeight=$labelHeight, " +
        "labelStyle=$labelStyle, " +
        "labelColor=$labelColor, " +
        "buttonShape=$buttonShape, " +
        "buttonPadding=$buttonPadding, " +
        "buttonGap=$buttonGap, " +
        "textStyle=$textStyle, " +
        "smallTextStyle=$smallTextStyle, " +
        "iconSize=$iconSize, " +
        "actionSize=$actionSize, " +
        "badgeStyle=$badgeStyle, " +
        "subIndent=$subIndent, " +
        "subPadding=$subPadding, " +
        "subGap=$subGap, " +
        "subHeight=$subHeight, " +
        "triggerIcon=$triggerIcon, " +
        "toggleLabel=$toggleLabel, " +
        "railWidth=$railWidth, " +
        "widthAnimation=$widthAnimation, " +
        "chevron=$chevron, " +
        "brandSize=$brandSize, " +
        "brandShape=$brandShape, " +
        "brandContainer=$brandContainer, " +
        "brandContent=$brandContent, " +
        "brandTitleStyle=$brandTitleStyle, " +
        "brandSubtitleStyle=$brandSubtitleStyle, " +
        "brandSubtitleColor=$brandSubtitleColor)"

    private fun values(): List<Any?> = listOf(
        width,
        iconWidth,
        container,
        content,
        border,
        accent,
        accentContent,
        ring,
        background,
        floatingPadding,
        floatingShape,
        insetShape,
        shadow,
        section,
        labelHeight,
        labelStyle,
        labelColor,
        buttonShape,
        buttonPadding,
        buttonGap,
        textStyle,
        smallTextStyle,
        iconSize,
        actionSize,
        badgeStyle,
        subIndent,
        subPadding,
        subGap,
        subHeight,
        triggerIcon,
        toggleLabel,
        railWidth,
        widthAnimation,
        chevron,
        brandSize,
        brandShape,
        brandContainer,
        brandContent,
        brandTitleStyle,
        brandSubtitleStyle,
        brandSubtitleColor,
    )
}
