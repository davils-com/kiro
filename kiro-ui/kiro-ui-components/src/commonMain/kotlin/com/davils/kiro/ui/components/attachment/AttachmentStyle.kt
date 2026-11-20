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

/**
 * The look of an [Attachment], its parts and an [AttachmentGroup].
 *
 * @property shape the shape of the attachment, its surface and its outline.
 * @property container the background color of the attachment.
 * @property content the content color of the attachment and of the content of its media.
 * @property border the color of the outline.
 * @property errorBorder the color of the outline in the state [AttachmentState.Error].
 * @property hover the background color of a clickable attachment under the pointer.
 * @property ring the color of the focus ring, drawn while the attachment or one of its parts has the focus.
 * @property gap the space between the media, the content and the actions.
 * @property contentPadding the horizontal padding of an attachment without media.
 * @property contentVerticalPadding the vertical padding of an attachment without media.
 * @property mediaPadding the padding on every side of an attachment with media.
 * @property minWidth the minimum width of a horizontal attachment.
 * @property verticalWidth the width of a vertical attachment without content.
 * @property verticalContentWidth the width of a vertical attachment with content.
 * @property mediaSize the width and height of the media of a horizontal attachment.
 * @property mediaShape the shape that the media is clipped to.
 * @property mediaContainer the background color of the media.
 * @property errorMedia the background color of the media in the state [AttachmentState.Error].
 * @property errorContent the content color of the media in the state [AttachmentState.Error]; the description takes it
 * with less alpha.
 * @property iconSize the size of the icons in the media of a horizontal attachment.
 * @property textStyle the text style handed to the content of the attachment.
 * @property titleStyle the text style of an [AttachmentTitle].
 * @property descriptionStyle the text style of an [AttachmentDescription].
 * @property descriptionColor the color of an [AttachmentDescription] outside the state [AttachmentState.Error].
 * @property pendingAlpha the alpha of an image media while the attachment is uploading, processing or failed.
 * @property actionInset the distance of the actions from the top and the end edge of a vertical attachment.
 * @property groupGap the space between the attachments of an [AttachmentGroup].
 * @property dash the length of the dashes and of the gaps of the dashed outline of an idle attachment, in dp.
 * @since 1.0.0
 */
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
     * @since 1.0.0
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
