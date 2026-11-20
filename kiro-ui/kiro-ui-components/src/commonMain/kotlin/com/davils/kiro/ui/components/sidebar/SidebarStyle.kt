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

/**
 * The style of a [Sidebar] and of its parts: its colors, its panels, its sections, its menu buttons, its trees and
 * its brand.
 *
 * @property width the width of the open sidebar.
 * @property iconWidth the width of a sidebar collapsed to its icons, without the padding of a floating or inset
 * sidebar.
 * @property container the color of the sidebar.
 * @property content the color of the text and the icons of the sidebar.
 * @property border the color of the lines of the sidebar: its edge, its separators, the ring of a floating panel, the
 * line of a tree, the rail and the outline of an outlined menu button.
 * @property accent the color behind a menu button that is hovered, pressed or active.
 * @property accentContent the color of the content of a menu button that is hovered, pressed or active.
 * @property ring the color of the focus ring of a menu button.
 * @property background the color of the content next to the sidebar, which also fills an outlined menu button and a
 * [SidebarInput].
 * @property floatingPadding the space around a floating sidebar and around the panel of an inset sidebar.
 * @property floatingShape the shape of a floating sidebar.
 * @property insetShape the shape of the panel of a [SidebarInset] next to an inset sidebar.
 * @property shadow the shadow of a floating sidebar and of the panel of a [SidebarInset].
 * @property section the padding of the header, the footer and the groups, and the space between their items.
 * @property labelHeight the height of a [SidebarGroupLabel].
 * @property labelStyle the style of the text of a [SidebarGroupLabel].
 * @property labelColor the color of the text of a [SidebarGroupLabel].
 * @property buttonShape the shape of a menu button, of an action and of the brand.
 * @property buttonPadding the horizontal padding of a menu button and of a group label; the brand uses half of it.
 * @property buttonGap the space between the items of a menu button and of the brand.
 * @property textStyle the style of the text of a menu button and of the content of a group.
 * @property smallTextStyle the style of the text of a [SidebarMenuButtonSize.Sm] menu button.
 * @property iconSize the size of the icons of the menu buttons, the actions and the brand.
 * @property actionSize the size of an action and the smallest width and the height of a badge.
 * @property badgeStyle the style of the text of a badge.
 * @property subIndent the space between the start of a tree branch and the line in front of its children.
 * @property subPadding the space between the line in front of the children of a tree branch and the children.
 * @property subGap the space between the rows of a tree.
 * @property subHeight the height of a nested row of a tree.
 * @property triggerIcon the icon of a [SidebarTrigger].
 * @property toggleLabel the text that announces a [SidebarTrigger] and the rail.
 * @property railWidth the width of the rail at the edge of a sidebar, which toggles the sidebar when it is clicked.
 * @property widthAnimation the animation of the width of the sidebar and of the height of its group labels.
 * @property chevron the icon that shows whether a branch of a tree is expanded.
 * @property brandSize the size of the tile that holds the logo of a [SidebarBrand].
 * @property brandShape the shape of the tile of a [SidebarBrand].
 * @property brandContainer the color of the tile of a [SidebarBrand].
 * @property brandContent the color of the logo of a [SidebarBrand].
 * @property brandTitleStyle the style of the title of a [SidebarBrand].
 * @property brandSubtitleStyle the style of the subtitle of a [SidebarBrand].
 * @property brandSubtitleColor the color of the subtitle of a [SidebarBrand].
 * @since 1.0.0
 */
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
     * @param width the new value of [width].
     * @param iconWidth the new value of [iconWidth].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param accent the new value of [accent].
     * @param accentContent the new value of [accentContent].
     * @param ring the new value of [ring].
     * @param background the new value of [background].
     * @param floatingPadding the new value of [floatingPadding].
     * @param floatingShape the new value of [floatingShape].
     * @param insetShape the new value of [insetShape].
     * @param shadow the new value of [shadow].
     * @param section the new value of [section].
     * @param labelHeight the new value of [labelHeight].
     * @param labelStyle the new value of [labelStyle].
     * @param labelColor the new value of [labelColor].
     * @param buttonShape the new value of [buttonShape].
     * @param buttonPadding the new value of [buttonPadding].
     * @param buttonGap the new value of [buttonGap].
     * @param textStyle the new value of [textStyle].
     * @param smallTextStyle the new value of [smallTextStyle].
     * @param iconSize the new value of [iconSize].
     * @param actionSize the new value of [actionSize].
     * @param badgeStyle the new value of [badgeStyle].
     * @param subIndent the new value of [subIndent].
     * @param subPadding the new value of [subPadding].
     * @param subGap the new value of [subGap].
     * @param subHeight the new value of [subHeight].
     * @param triggerIcon the new value of [triggerIcon].
     * @param toggleLabel the new value of [toggleLabel].
     * @param railWidth the new value of [railWidth].
     * @param widthAnimation the new value of [widthAnimation].
     * @param chevron the new value of [chevron].
     * @param brandSize the new value of [brandSize].
     * @param brandShape the new value of [brandShape].
     * @param brandContainer the new value of [brandContainer].
     * @param brandContent the new value of [brandContent].
     * @param brandTitleStyle the new value of [brandTitleStyle].
     * @param brandSubtitleStyle the new value of [brandSubtitleStyle].
     * @param brandSubtitleColor the new value of [brandSubtitleColor].
     * @return the copy.
     * @since 1.0.0
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
