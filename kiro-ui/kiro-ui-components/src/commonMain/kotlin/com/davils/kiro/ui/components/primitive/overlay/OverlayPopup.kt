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
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.davils.kiro.ui.components.theme

@Composable
internal fun OverlayPopup(
    isOpen: Boolean,
    placement: Placement,
    isFocusable: Boolean,
    onDismiss: () -> Unit,
    onEscape: () -> Unit = onDismiss,
    content: @Composable () -> Unit,
) {
    val presence = remember { MutableTransitionState(false) }
    val openings = remember { mutableIntStateOf(0) }
    if (isOpen && !presence.targetState) openings.intValue += 1
    presence.targetState = isOpen
    val isShown = presence.currentState || presence.targetState
    if (!isShown) return
    val density = LocalDensity.current
    val physical = physicalSideOf(side = placement.side, isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl)
    val provider = with(density) {
        AnchorPositionProvider(
            side = placement.side,
            align = placement.align,
            sideOffset = placement.sideOffset.roundToPx(),
            alignOffset = placement.alignOffset.roundToPx(),
            padding = placement.collisionPadding.roundToPx(),
        )
    }
    val motion = theme.motion
    val slide = slideOf(distance = motion.overlay.slideDistance, gap = placement.sideOffset)
    val transitions = overlayTransitionsOf(
        motion = motion,
        physical = physical,
        slide = with(density) { slide.roundToPx() },
    )
    Popup(
        popupPositionProvider = provider,
        onDismissRequest = onDismiss,
        properties = PopupProperties(focusable = isFocusable),
    ) {
        val escape = Modifier.onKeyEvent { event ->
            val isEscape = event.type == KeyEventType.KeyDown && event.key == Key.Escape
            if (isEscape) onEscape()
            isEscape
        }
        Box(modifier = escape) {
            AnimatedVisibility(
                visibleState = presence,
                enter = transitions.enter,
                exit = transitions.exit,
            ) { key(openings.intValue) { content() } }
        }
    }
}
