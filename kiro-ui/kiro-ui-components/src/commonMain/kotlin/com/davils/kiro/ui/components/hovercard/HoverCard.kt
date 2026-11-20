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

package com.davils.kiro.ui.components.hovercard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.delayedVisibility
import com.davils.kiro.ui.components.primitive.overlay.hoverTracking
import com.davils.kiro.ui.components.primitive.overlay.panel
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

/**
 * Shows a card next to a trigger while the pointer hovers over the trigger or the card.
 *
 * The card appears after the open delay of [style] and disappears after its close delay, so that the pointer can
 * move from the trigger to the card. It does not take the focus.
 *
 * @param trigger the content that shows the card while hovered.
 * @param modifier the modifier of the box around the trigger.
 * @param placement where the card sits relative to the trigger.
 * @param style the style of the hover card.
 * @param content the content of the card.
 * @since 1.0.0
 */
@Composable
public fun HoverCard(
    trigger: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placement: Placement = Placement(alignOffset = 4.dp),
    style: HoverCardStyle = hoverCardStyle(),
    content: @Composable BoxScope.() -> Unit,
) {
    var isTriggerHovered by remember { mutableStateOf(false) }
    var isContentHovered by remember { mutableStateOf(false) }
    val isVisible = delayedVisibility(
        isWanted = isTriggerHovered || isContentHovered,
        openDelay = style.openDelay,
        closeDelay = style.closeDelay,
    )
    Box(modifier = modifier.hoverTracking(onHover = { hovered -> isTriggerHovered = hovered })) {
        trigger()
        OverlayPopup(
            isOpen = isVisible,
            placement = placement,
            isFocusable = false,
            onDismiss = {
                isTriggerHovered = false
                isContentHovered = false
            },
        ) {
            CompositionLocalProvider(
                LocalContentColor provides style.panel.content,
                LocalTextStyle provides style.textStyle,
            ) {
                Box(
                    modifier = Modifier
                        .hoverTracking(onHover = { hovered -> isContentHovered = hovered })
                        .width(style.width)
                        .panel(style.panel),
                    content = content,
                )
            }
        }
    }
}
