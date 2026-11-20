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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.text.ShimmerText
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private val VERTICAL_ICON = 24.dp

/**
 * Shows a file that is attached or being attached, made of an [AttachmentMedia], an [AttachmentContent] and
 * [AttachmentActions].
 *
 * In a row, the media and the actions take their own width and the content the rest. In a column, the media and the
 * content fill the width one below the other and the actions float over the top end corner. The outline is dashed
 * while the attachment is [AttachmentState.Idle] and takes the destructive color in [AttachmentState.Error].
 *
 * @param modifier the modifier of the attachment.
 * @param state the state of the file, which decides the outline, the colors and the animation of the title.
 * @param size the size of the attachment, which picks its default [style].
 * @param orientation whether the parts are laid out in a row or in a column.
 * @param onClick called when the attachment is clicked, or `null` if it is not clickable.
 * @param style the look of the attachment and its parts.
 * @param content the parts of the attachment.
 * @since 1.0.0
 */
@Composable
public fun Attachment(
    modifier: Modifier = Modifier,
    state: AttachmentState = AttachmentState.Done,
    size: AttachmentSize = AttachmentSize.Default,
    orientation: Orientation = Orientation.Horizontal,
    onClick: (() -> Unit)? = null,
    style: AttachmentStyle = attachmentStyle(size),
    content: @Composable () -> Unit,
) {
    val context = remember(
        state,
        orientation,
        style,
    ) { AttachmentContext(state = state, orientation = orientation, style = style) }
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    var hasFocus by remember { mutableStateOf(false) }
    val transition = theme.colorTransition()
    val container by animateColor(
        style.hover.takeIf { isHovered && onClick != null } ?: style.container,
        transition,
    )
    val ring = Border(width = 1.dp, color = style.ring.takeIf { hasFocus } ?: Color.Transparent)
    val border = style.errorBorder.takeIf { state == AttachmentState.Error } ?: style.border
    val click = onClick?.let { action ->
        Modifier.pressableCursor().clickable(
            interactionSource = source,
            indication = null,
            role = Role.Button,
            onClick = action,
        )
    }
    CompositionLocalProvider(
        LocalAttachment provides context,
        LocalContentColor provides style.content,
        LocalTextStyle provides style.textStyle,
    ) {
        Layout(
            content = content,
            modifier = modifier.then(
                Modifier
                    .then(sizeOf(context))
                    .surface(shape = style.shape, color = container, ring = ring)
                    .then(outlineOf(isDashed = state == AttachmentState.Idle, color = border, style = style))
                    .onFocusChanged { focus -> hasFocus = focus.hasFocus }
                    .hoverable(interactionSource = source)
                    .then(click ?: Modifier)
                    .padding(paddingOf(context)),
            ),
            measurePolicy = AttachmentMeasurePolicy(
                orientation = orientation,
                gap = style.gap,
                inset = style.actionInset - style.mediaPadding,
            ),
        )
    }
}

/**
 * Shows the icon or the image of an [Attachment].
 *
 * In a row it takes the media size of the style, in a column the full width as a square. In
 * [AttachmentState.Error] it takes the destructive colors.
 *
 * @param modifier the modifier of the media.
 * @param variant whether the media shows an icon or an image.
 * @param content the icon or the image.
 * @throws IllegalArgumentException if it is not placed in an [Attachment].
 * @since 1.0.0
 */
@Composable
public fun AttachmentMedia(
    modifier: Modifier = Modifier,
    variant: AttachmentMediaVariant = AttachmentMediaVariant.Icon,
    content: @Composable () -> Unit,
) {
    val context = requireNotNull(LocalAttachment.current) { "An attachment media needs its attachment." }
    val style = context.style
    DisposableEffect(context) {
        context.hasMedia = true
        onDispose { context.hasMedia = false }
    }
    val isError = context.state == AttachmentState.Error
    val isVertical = context.orientation == Orientation.Vertical
    val isDim = variant == AttachmentMediaVariant.Image && context.state !in settled
    val sizing = Modifier.fillMaxWidth().aspectRatio(1f).takeIf { isVertical } ?: Modifier.size(style.mediaSize)
    Box(
        modifier = modifier.then(
            Modifier
                .layoutId(AttachmentSlot.Media)
                .then(sizing)
                .alpha(style.pendingAlpha.takeIf { isDim } ?: 1f)
                .clip(style.mediaShape)
                .surface(shape = style.mediaShape, color = style.errorMedia.takeIf { isError } ?: style.mediaContainer),
        ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides (style.errorContent.takeIf { isError } ?: style.content),
            LocalIconStyle provides IconStyle(size = VERTICAL_ICON.takeIf { isVertical } ?: style.iconSize),
            content = content,
        )
    }
}

/**
 * Holds the [AttachmentTitle] and the [AttachmentDescription] of an [Attachment] in a column.
 *
 * @param modifier the modifier of the column.
 * @param content the title and the description.
 * @throws IllegalArgumentException if it is not placed in an [Attachment].
 * @since 1.0.0
 */
@Composable
public fun AttachmentContent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val context = requireNotNull(LocalAttachment.current) { "An attachment content needs its attachment." }
    DisposableEffect(context) {
        context.hasContent = true
        onDispose { context.hasContent = false }
    }
    val inset = 4.dp.takeIf { context.orientation == Orientation.Vertical } ?: 0.dp
    Column(modifier = modifier.layoutId(AttachmentSlot.Content).padding(horizontal = inset), content = content)
}

/**
 * Shows the name of the file of an [Attachment] on one line.
 *
 * While the attachment is uploading or processing, the title shimmers unless the motion of the theme is reduced.
 *
 * @param text the name of the file.
 * @param modifier the modifier of the title.
 * @throws IllegalArgumentException if it is not placed in an [Attachment].
 * @since 1.0.0
 */
@Composable
public fun AttachmentTitle(text: String, modifier: Modifier = Modifier) {
    val context = requireNotNull(LocalAttachment.current) { "An attachment title needs its attachment." }
    val isBusy = context.state == AttachmentState.Uploading || context.state == AttachmentState.Processing
    val isShimmering = isBusy && !theme.motion.isReduced
    if (isShimmering) {
        ShimmerText(
            text = text,
            highlight = theme.colors.foreground,
            modifier = modifier,
            style = context.style.titleStyle,
        )
        return
    }
    Text(text = text, modifier = modifier, style = context.style.titleStyle, maxLines = 1)
}

/**
 * Shows a line about the file of an [Attachment], such as its size or an error, in the destructive color while the
 * attachment is in [AttachmentState.Error].
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @throws IllegalArgumentException if it is not placed in an [Attachment].
 * @since 1.0.0
 */
@Composable
public fun AttachmentDescription(text: String, modifier: Modifier = Modifier) {
    val context = requireNotNull(LocalAttachment.current) { "An attachment description needs its attachment." }
    val style = context.style
    val color = style.errorDescription().takeIf { context.state == AttachmentState.Error } ?: style.descriptionColor
    Text(
        text = text,
        modifier = modifier.padding(top = 2.dp),
        style = style.descriptionStyle,
        color = color,
        maxLines = 1,
    )
}

/**
 * Holds the [AttachmentAction]s of an [Attachment] in a row.
 *
 * @param modifier the modifier of the row.
 * @param content the actions.
 * @since 1.0.0
 */
@Composable
public fun AttachmentActions(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier.layoutId(AttachmentSlot.Actions),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

/**
 * An icon button for an action on an attachment, such as removing it or retrying the upload.
 *
 * @param onClick called when the action is clicked.
 * @param icon the icon of the action.
 * @param contentDescription the text that describes the action to assistive technologies.
 * @param modifier the modifier of the button.
 * @since 1.0.0
 */
@Composable
public fun AttachmentAction(
    onClick: () -> Unit,
    icon: IconGraphic,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.semantics { this.contentDescription = contentDescription },
        variant = ButtonVariant.Ghost,
        size = ButtonSize.IconXs,
    ) {
        Icon(icon = icon, placement = IconPlacement.Inline)
    }
}

/**
 * Lays out attachments in a row that scrolls horizontally when it does not fit.
 *
 * @param modifier the modifier of the row.
 * @param style the style whose group gap separates the attachments.
 * @param content the attachments.
 * @since 1.0.0
 */
@Composable
public fun AttachmentGroup(
    modifier: Modifier = Modifier,
    style: AttachmentStyle = attachmentStyle(AttachmentSize.Default),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()).padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(style.groupGap),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

private val settled: Set<AttachmentState> = setOf(AttachmentState.Done, AttachmentState.Idle)

private fun sizeOf(context: AttachmentContext): Modifier {
    val style = context.style
    if (context.orientation == Orientation.Horizontal) return Modifier.widthIn(min = style.minWidth)
    return Modifier.width(style.verticalContentWidth.takeIf { context.hasContent } ?: style.verticalWidth)
}

private fun paddingOf(context: AttachmentContext): PaddingValues {
    val style = context.style
    if (context.hasMedia) return PaddingValues(style.mediaPadding)
    return PaddingValues(horizontal = style.contentPadding, vertical = style.contentVerticalPadding)
}

private fun outlineOf(isDashed: Boolean, color: Color, style: AttachmentStyle): Modifier = Modifier.drawBehind {
    val stroke = 1.dp.toPx()
    val dash = style.dash * density
    val effect = PathEffect.dashPathEffect(floatArrayOf(dash, dash)).takeIf { isDashed }
    drawOutline(
        outline = style.shape.createOutline(size, layoutDirection, this),
        color = color,
        style = Stroke(width = stroke, pathEffect = effect),
    )
}
