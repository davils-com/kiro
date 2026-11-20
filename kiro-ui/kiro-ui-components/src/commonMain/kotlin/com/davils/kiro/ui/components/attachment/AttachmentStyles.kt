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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val HALF: Float = 0.5f
private const val ERROR_BORDER: Float = 0.3f
private const val ERROR_MEDIA: Float = 0.1f
private const val ERROR_TEXT: Float = 0.8f
private const val PENDING: Float = 0.6f
private const val DASH: Float = 4f

private val known: Set<AttachmentSize> = setOf(
    AttachmentSize.Default,
    AttachmentSize.Sm,
    AttachmentSize.Xs,
)

/**
 * Resolves the [AttachmentStyle] of an [Attachment] for a theme and a size.
 *
 * Register your own under [AttachmentStyleKey] in the component styles of a theme to change every attachment.
 *
 * @since 1.0.0
 */
public fun interface AttachmentStyles {
    /**
     * Returns the style of an attachment.
     *
     * @param theme the theme whose tokens the style follows.
     * @param size the size of the attachment.
     * @return the style of an attachment of [size].
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, size: AttachmentSize): AttachmentStyle

    /**
     * Holds the default attachment styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default attachment styles, derived from the colors, radii and typography of the theme for the sizes of
         * [AttachmentSize].
         *
         * @since 1.0.0
         */
        public val Default: AttachmentStyles = AttachmentStyles(::defaultAttachmentStyle)
    }
}

/**
 * Returns the attachment style of the current theme for a size.
 *
 * @param size the size of the attachment.
 * @return the style that the [AttachmentStyles] registered under [AttachmentStyleKey] resolve for [size].
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun attachmentStyle(size: AttachmentSize): AttachmentStyle = AttachmentStyleKey.current.resolve(
    theme,
    size,
)

internal fun defaultAttachmentStyle(theme: Theme, size: AttachmentSize): AttachmentStyle {
    require(size in known) { "No default style for the attachment size $size." }
    val colors = theme.colors
    val scale = theme.typography.scale
    val isXs = size == AttachmentSize.Xs
    val text = scale.sm.takeIf { size == AttachmentSize.Default } ?: scale.xs
    return AttachmentStyle(
        shape = RoundedCornerShape(theme.radii.lg.takeIf { isXs } ?: theme.radii.xl),
        container = colors.card,
        content = colors.cardForeground,
        border = colors.border,
        errorBorder = colors.destructive.copy(alpha = ERROR_BORDER),
        hover = colors.muted.copy(alpha = HALF),
        ring = colors.ring.copy(alpha = HALF),
        gap = size.pick(xs = 6.dp, sm = 10.dp, default = 8.dp),
        contentPadding = size.pick(xs = 6.dp, sm = 8.dp, default = 10.dp),
        contentVerticalPadding = size.pick(xs = 4.dp, sm = 6.dp, default = 8.dp),
        mediaPadding = size.pick(xs = 4.dp, sm = 6.dp, default = 8.dp),
        minWidth = 160.dp,
        verticalWidth = 96.dp,
        verticalContentWidth = 120.dp,
        mediaSize = size.pick(xs = 28.dp, sm = 32.dp, default = 40.dp),
        mediaShape = RoundedCornerShape(theme.radii.md.takeIf { isXs } ?: theme.radii.lg),
        mediaContainer = colors.muted,
        errorMedia = colors.destructive.copy(alpha = ERROR_MEDIA),
        errorContent = colors.destructive,
        iconSize = 14.dp.takeIf { isXs } ?: 16.dp,
        textStyle = theme.textStyleOf(size = text, lineHeight = theme.typography.leading.tight),
        titleStyle = theme.textStyleOf(
            size = text,
            weight = FontWeight.Medium,
            lineHeight = theme.typography.leading.tight,
        ),
        descriptionStyle = theme.textStyleOf(size = scale.xs),
        descriptionColor = colors.mutedForeground,
        pendingAlpha = PENDING,
        actionInset = 12.dp,
        groupGap = 12.dp,
        dash = DASH,
    )
}

private fun AttachmentSize.pick(xs: Dp, sm: Dp, default: Dp): Dp {
    if (this == AttachmentSize.Xs) return xs
    return sm.takeIf { this == AttachmentSize.Sm } ?: default
}

internal fun AttachmentStyle.errorDescription(): Color = errorContent.copy(alpha = ERROR_TEXT)

/**
 * The key of the attachment styles in the component styles of a theme, named `attachment`.
 *
 * @since 1.0.0
 */
public val AttachmentStyleKey: StyleKey<AttachmentStyles> = StyleKey(
    name = "attachment",
) { _ -> AttachmentStyles.Default }
