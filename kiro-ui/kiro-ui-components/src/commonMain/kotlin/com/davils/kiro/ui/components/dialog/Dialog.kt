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

package com.davils.kiro.ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.primitive.overlay.ModalLayer
import com.davils.kiro.ui.components.primitive.overlay.dialogTransitions
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun Dialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isCloseButtonShown: Boolean = true,
    style: DialogStyle = dialogStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalLayer(
        isOpen = open,
        onDismiss = { onOpenChange(false) },
        isScrimDismissing = true,
        scrim = style.scrim,
        transitions = dialogTransitions(theme.motion),
        alignment = Alignment.Center,
    ) {
        DialogPanel(modifier = modifier, style = style) {
            Column(
                modifier = Modifier.padding(style.panel.padding),
                verticalArrangement = Arrangement.spacedBy(style.gap),
                content = content,
            )
            if (isCloseButtonShown) {
                CloseButton(
                    icon = style.closeIcon,
                    label = style.closeLabel,
                    offset = style.closeOffset,
                ) { onOpenChange(false) }
            }
        }
    }
}

@Composable
internal fun DialogPanel(modifier: Modifier, style: DialogStyle, content: @Composable BoxScope.() -> Unit) {
    CompositionLocalProvider(LocalContentColor provides style.panel.content, LocalTextStyle provides style.textStyle) {
        Box(
            modifier = modifier
                .padding(style.margin)
                .widthIn(max = style.maxWidth)
                .fillMaxWidth()
                .surface(shape = style.panel.shape, color = style.panel.container, ring = style.panel.ring)
                .initialFocus(),
            content = content,
        )
    }
}

@Composable
public fun DialogHeader(
    modifier: Modifier = Modifier,
    style: DialogStyle = dialogStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.headerGap), content = content)
}

@Composable
public fun DialogTitle(text: String, modifier: Modifier = Modifier, style: DialogStyle = dialogStyle()) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

@Composable
public fun DialogDescription(text: String, modifier: Modifier = Modifier, style: DialogStyle = dialogStyle()) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}

@Composable
public fun DialogFooter(
    modifier: Modifier = Modifier,
    style: DialogStyle = dialogStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    BleedingFooter(style = style, modifier = modifier, content = content)
}
