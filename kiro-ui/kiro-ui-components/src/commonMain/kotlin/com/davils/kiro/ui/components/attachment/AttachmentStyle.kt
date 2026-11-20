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

package com.davils.kiro.ui.components.attachment

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class AttachmentStyle(
    public val shape: Shape,
    public val container: Color,
    public val content: Color,
    public val border: Color,
    public val errorBorder: Color,
    public val hover: Color,
    public val ring: Color,
    public val gap: Dp,
    public val contentPadding: Dp,
    public val contentVerticalPadding: Dp,
    public val mediaPadding: Dp,
    public val minWidth: Dp,
    public val verticalWidth: Dp,
    public val verticalContentWidth: Dp,
    public val mediaSize: Dp,
    public val mediaShape: Shape,
    public val mediaContainer: Color,
    public val errorMedia: Color,
    public val errorContent: Color,
    public val iconSize: Dp,
    public val textStyle: TextStyle,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val pendingAlpha: Float,
    public val actionInset: Dp,
    public val groupGap: Dp,
    public val dash: Float,
) {
    /**
     * Returns a copy of this [AttachmentStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        shape: Shape = this.shape,
        container: Color = this.container,
        content: Color = this.content,
        border: Color = this.border,
        errorBorder: Color = this.errorBorder,
        hover: Color = this.hover,
        ring: Color = this.ring,
        gap: Dp = this.gap,
        contentPadding: Dp = this.contentPadding,
        contentVerticalPadding: Dp = this.contentVerticalPadding,
        mediaPadding: Dp = this.mediaPadding,
        minWidth: Dp = this.minWidth,
        verticalWidth: Dp = this.verticalWidth,
        verticalContentWidth: Dp = this.verticalContentWidth,
        mediaSize: Dp = this.mediaSize,
        mediaShape: Shape = this.mediaShape,
        mediaContainer: Color = this.mediaContainer,
        errorMedia: Color = this.errorMedia,
        errorContent: Color = this.errorContent,
        iconSize: Dp = this.iconSize,
        textStyle: TextStyle = this.textStyle,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        pendingAlpha: Float = this.pendingAlpha,
        actionInset: Dp = this.actionInset,
        groupGap: Dp = this.groupGap,
        dash: Float = this.dash,
    ): AttachmentStyle = AttachmentStyle(
        shape = shape,
        container = container,
        content = content,
        border = border,
        errorBorder = errorBorder,
        hover = hover,
        ring = ring,
        gap = gap,
        contentPadding = contentPadding,
        contentVerticalPadding = contentVerticalPadding,
        mediaPadding = mediaPadding,
        minWidth = minWidth,
        verticalWidth = verticalWidth,
        verticalContentWidth = verticalContentWidth,
        mediaSize = mediaSize,
        mediaShape = mediaShape,
        mediaContainer = mediaContainer,
        errorMedia = errorMedia,
        errorContent = errorContent,
        iconSize = iconSize,
        textStyle = textStyle,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        pendingAlpha = pendingAlpha,
        actionInset = actionInset,
        groupGap = groupGap,
        dash = dash,
    )

    override fun equals(other: Any?): Boolean = other is AttachmentStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AttachmentStyle(" +
        "shape=$shape, " +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "errorBorder=$errorBorder, " +
        "hover=$hover, " +
        "ring=$ring, " +
        "gap=$gap, " +
        "contentPadding=$contentPadding, " +
        "contentVerticalPadding=$contentVerticalPadding, " +
        "mediaPadding=$mediaPadding, " +
        "minWidth=$minWidth, " +
        "verticalWidth=$verticalWidth, " +
        "verticalContentWidth=$verticalContentWidth, " +
        "mediaSize=$mediaSize, " +
        "mediaShape=$mediaShape, " +
        "mediaContainer=$mediaContainer, " +
        "errorMedia=$errorMedia, " +
        "errorContent=$errorContent, " +
        "iconSize=$iconSize, " +
        "textStyle=$textStyle, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "pendingAlpha=$pendingAlpha, " +
        "actionInset=$actionInset, " +
        "groupGap=$groupGap, " +
        "dash=$dash)"

    private fun values(): List<Any?> = listOf(
        shape,
        container,
        content,
        border,
        errorBorder,
        hover,
        ring,
        gap,
        contentPadding,
        contentVerticalPadding,
        mediaPadding,
        minWidth,
        verticalWidth,
        verticalContentWidth,
        mediaSize,
        mediaShape,
        mediaContainer,
        errorMedia,
        errorContent,
        iconSize,
        textStyle,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        pendingAlpha,
        actionInset,
        groupGap,
        dash,
    )
}
