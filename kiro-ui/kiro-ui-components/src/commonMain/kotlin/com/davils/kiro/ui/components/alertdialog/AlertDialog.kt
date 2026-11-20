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

package com.davils.kiro.ui.components.alertdialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.InlineScope
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.dialog.BleedingFooter
import com.davils.kiro.ui.components.dialog.DialogPanel
import com.davils.kiro.ui.components.dialog.DialogStyle
import com.davils.kiro.ui.components.dialog.dialogStyle
import com.davils.kiro.ui.components.primitive.overlay.ModalLayer
import com.davils.kiro.ui.components.primitive.overlay.dialogTransitions
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private val MEDIA_SIZE = 40.dp
private val MEDIA_ICON = 24.dp
private val TEXT_GAP = 6.dp

/**
 * A modal dialog that asks the user to confirm or cancel an action and, unlike a dialog, is not dismissed by a click on
 * its scrim.
 *
 * The dialog shows over a scrim in the middle of the window while [open] is true and asks to be closed when Escape is
 * pressed. Its width is limited by [size], which also decides how [AlertDialogHeader], [AlertDialogTitle],
 * [AlertDialogDescription], [AlertDialogAction] and [AlertDialogCancel] lay out their content.
 *
 * @param open whether the dialog is shown.
 * @param onOpenChange called with `false` when the dialog asks to be closed.
 * @param modifier the modifier of the panel of the dialog.
 * @param size the size of the dialog.
 * @param style the style of the dialog.
 * @param content the content of the dialog, laid out in a column.
 * @throws IllegalArgumentException if [size] is neither [AlertDialogSize.Default] nor [AlertDialogSize.Sm].
 * @since 1.0.0
 */
@Composable
public fun AlertDialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: AlertDialogSize = AlertDialogSize.Default,
    style: DialogStyle = dialogStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val width = maxWidthOf(size)
    ModalLayer(
        isOpen = open,
        onDismiss = { onOpenChange(false) },
        isScrimDismissing = false,
        scrim = style.scrim,
        transitions = dialogTransitions(theme.motion),
        alignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalAlertDialogSize provides size) {
            DialogPanel(modifier = modifier, style = style.copy(maxWidth = width)) {
                Column(
                    modifier = Modifier.padding(style.panel.padding),
                    verticalArrangement = Arrangement.spacedBy(style.gap),
                    content = content,
                )
            }
        }
    }
}

/**
 * The header of an [AlertDialog]: an optional media next to its title and description, or centered above them in a
 * dialog of size [AlertDialogSize.Sm].
 *
 * @param modifier the modifier of the header.
 * @param media the media of the header, such as an [AlertDialogMedia], or `null` for none.
 * @param content the title and description of the header, laid out in a column.
 * @since 1.0.0
 */
@Composable
public fun AlertDialogHeader(
    modifier: Modifier = Modifier,
    media: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val isCompact = LocalAlertDialogSize.current == AlertDialogSize.Sm
    if (isCompact) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(TEXT_GAP),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            media?.invoke()
            content()
        }
        return
    }
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        media?.invoke()
        Column(verticalArrangement = Arrangement.spacedBy(TEXT_GAP), content = content)
    }
}

/**
 * A square box with the muted color of the theme that holds an icon in the header of an [AlertDialog].
 *
 * @param modifier the modifier of the box.
 * @param content the content of the box, usually an icon, centered in it.
 * @since 1.0.0
 */
@Composable
public fun AlertDialogMedia(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val colors = theme.colors
    Box(
        modifier = modifier
            .size(MEDIA_SIZE)
            .surface(shape = RoundedCornerShape(theme.radii.md), color = colors.muted),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalIconStyle provides IconStyle(size = MEDIA_ICON), content = content)
    }
}

/**
 * The title of an [AlertDialog], centered in a dialog of size [AlertDialogSize.Sm] and aligned to the start otherwise.
 *
 * @param text the title.
 * @param modifier the modifier of the title.
 * @param style the style of the dialog whose title style is used.
 * @since 1.0.0
 */
@Composable
public fun AlertDialogTitle(text: String, modifier: Modifier = Modifier, style: DialogStyle = dialogStyle()) {
    Text(
        text = text,
        modifier = modifier,
        style = style.titleStyle.copy(textAlign = alignOf(LocalAlertDialogSize.current)),
    )
}

/**
 * The description of an [AlertDialog], centered in a dialog of size [AlertDialogSize.Sm] and aligned to the start
 * otherwise.
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @param style the style of the dialog whose description style and color are used.
 * @since 1.0.0
 */
@Composable
public fun AlertDialogDescription(text: String, modifier: Modifier = Modifier, style: DialogStyle = dialogStyle()) {
    Text(
        text = text,
        modifier = modifier,
        style = style.descriptionStyle.copy(textAlign = alignOf(LocalAlertDialogSize.current)),
        color = style.descriptionColor,
    )
}

/**
 * The footer of an [AlertDialog]: a row at the bottom edge of the dialog, with the footer container and border of the
 * style, that aligns its actions to the end.
 *
 * @param modifier the modifier of the footer.
 * @param style the style of the dialog.
 * @param content the actions of the footer, such as [AlertDialogAction] and [AlertDialogCancel].
 * @since 1.0.0
 */
@Composable
public fun AlertDialogFooter(
    modifier: Modifier = Modifier,
    style: DialogStyle = dialogStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    BleedingFooter(style = style, modifier = modifier, content = content)
}

/**
 * The button of an [AlertDialog] that performs the action; in a dialog of size [AlertDialogSize.Sm] it shares the width
 * of the footer with the other buttons.
 *
 * @param onClick called when the button is clicked.
 * @param modifier the modifier of the button.
 * @param variant the variant of the button.
 * @param content the content of the button.
 * @since 1.0.0
 */
@Composable
public fun RowScope.AlertDialogAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    content: @Composable InlineScope.() -> Unit,
) {
    Button(onClick = onClick, modifier = modifier.then(stretchOf(this)), variant = variant, content = content)
}

/**
 * The button of an [AlertDialog] that cancels the action, drawn as an outline button by default; in a dialog of size
 * [AlertDialogSize.Sm] it shares the width of the footer with the other buttons.
 *
 * @param onClick called when the button is clicked.
 * @param modifier the modifier of the button.
 * @param variant the variant of the button.
 * @param content the content of the button.
 * @since 1.0.0
 */
@Composable
public fun RowScope.AlertDialogCancel(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Outline,
    content: @Composable InlineScope.() -> Unit,
) {
    Button(onClick = onClick, modifier = modifier.then(stretchOf(this)), variant = variant, content = content)
}

@Composable
private fun stretchOf(scope: RowScope): Modifier {
    if (LocalAlertDialogSize.current != AlertDialogSize.Sm) return Modifier
    return with(scope) { Modifier.weight(1f) }
}

private fun alignOf(size: AlertDialogSize): TextAlign {
    if (size == AlertDialogSize.Sm) return TextAlign.Center
    return TextAlign.Start
}
