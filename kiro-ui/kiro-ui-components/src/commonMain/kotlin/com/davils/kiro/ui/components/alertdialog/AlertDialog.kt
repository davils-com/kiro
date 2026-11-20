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

@Composable
public fun AlertDialogTitle(text: String, modifier: Modifier = Modifier, style: DialogStyle = dialogStyle()) {
    Text(
        text = text,
        modifier = modifier,
        style = style.titleStyle.copy(textAlign = alignOf(LocalAlertDialogSize.current)),
    )
}

@Composable
public fun AlertDialogDescription(text: String, modifier: Modifier = Modifier, style: DialogStyle = dialogStyle()) {
    Text(
        text = text,
        modifier = modifier,
        style = style.descriptionStyle.copy(textAlign = alignOf(LocalAlertDialogSize.current)),
        color = style.descriptionColor,
    )
}

@Composable
public fun AlertDialogFooter(
    modifier: Modifier = Modifier,
    style: DialogStyle = dialogStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    BleedingFooter(style = style, modifier = modifier, content = content)
}

@Composable
public fun RowScope.AlertDialogAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    content: @Composable InlineScope.() -> Unit,
) {
    Button(onClick = onClick, modifier = modifier.then(stretchOf(this)), variant = variant, content = content)
}

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
