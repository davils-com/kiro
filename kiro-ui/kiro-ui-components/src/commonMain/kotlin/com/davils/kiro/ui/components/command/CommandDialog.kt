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

package com.davils.kiro.ui.components.command

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.primitive.overlay.ModalLayer
import com.davils.kiro.ui.components.primitive.overlay.dialogTransitions
import com.davils.kiro.ui.components.theme

@Composable
public fun CommandDialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    state: CommandState = rememberCommandState(),
    placeholder: String = Messages.Command.placeholder,
    emptyText: String = Messages.Command.empty,
    style: CommandStyle = commandStyle(),
    content: CommandScopeImpl.() -> Unit,
) {
    ModalLayer(
        isOpen = open,
        onDismiss = { onOpenChange(false) },
        isScrimDismissing = true,
        scrim = style.scrim,
        transitions = dialogTransitions(theme.motion),
        alignment = Alignment.TopCenter,
    ) {
        val focus = remember { FocusRequester() }
        LaunchedEffect(Unit) { focus.requestFocus() }
        DisposableEffect(state) { onDispose { state.reset() } }
        CommandLayout(
            modifier = modifier.padding(top = style.dialogOffset).width(style.dialogWidth),
            state = state,
            texts = CommandTexts(placeholder = placeholder, empty = emptyText),
            panel = style.dialogPanel,
            style = style,
            focus = focus,
            onClose = { onOpenChange(false) },
            content = content,
        )
    }
}
