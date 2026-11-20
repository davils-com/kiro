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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
internal fun ModalLayer(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    isScrimDismissing: Boolean,
    scrim: Color,
    transitions: ModalTransitions,
    alignment: Alignment,
    content: @Composable () -> Unit,
) {
    val presence = remember { MutableTransitionState(false) }
    presence.targetState = isOpen
    val isShown = presence.currentState || presence.targetState
    if (!isShown) return
    val transition = rememberTransition(presence)
    val escape = Modifier.onKeyEvent { event ->
        val isEscape = event.type == KeyEventType.KeyDown && event.key == Key.Escape
        if (isEscape) onDismiss()
        isEscape
    }
    Popup(
        popupPositionProvider = WindowPositionProvider,
        onDismissRequest = {},
        properties = PopupProperties(focusable = true, dismissOnClickOutside = false),
    ) {
        Box(modifier = Modifier.fillMaxSize().then(escape)) {
            transition.AnimatedVisibility(
                visible = { isVisible -> isVisible },
                enter = transitions.scrimEnter,
                exit = transitions.scrimExit,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(scrim)
                        .pointerInput(isScrimDismissing) { detectTapGestures { if (isScrimDismissing) onDismiss() } },
                )
            }
            transition.AnimatedVisibility(
                visible = { isVisible -> isVisible },
                modifier = Modifier.align(alignment),
                enter = transitions.panelEnter,
                exit = transitions.panelExit,
            ) {
                Box(modifier = Modifier.pointerInput(Unit) { detectTapGestures {} }) { content() }
            }
        }
    }
}
