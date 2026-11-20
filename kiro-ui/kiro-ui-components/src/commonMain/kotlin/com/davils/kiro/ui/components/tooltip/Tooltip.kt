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

package com.davils.kiro.ui.components.tooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Physical
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.overlay.delayedVisibility
import com.davils.kiro.ui.components.primitive.overlay.hoverTracking
import com.davils.kiro.ui.components.primitive.overlay.physicalSideOf
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.focus.LocalInputModality

private const val HALF_TURN_QUARTER: Float = 45f
private const val ARROW_INSET_DP: Float = 2f

/**
 * Shows a short label in a popup beside its trigger while the pointer is over the trigger or the trigger has the
 * keyboard focus, with an arrow that points at the trigger.
 *
 * The tooltip opens after the open delay of its style and closes after its close delay. A press on the trigger closes
 * it. The popup does not take the focus.
 *
 * @param tooltip the content of the tooltip, in a row with the content color and text style of the style.
 * @param modifier the modifier of the box around the trigger.
 * @param placement the side of the trigger that the tooltip opens on and its alignment along that side.
 * @param style the style of the tooltip.
 * @param trigger the content that the tooltip belongs to.
 * @since 1.0.0
 */
@Composable
public fun Tooltip(
    tooltip: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    placement: Placement = Placement(side = Side.Top),
    style: TooltipStyle = tooltipStyle(),
    trigger: @Composable () -> Unit,
) {
    var isHovered by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val isKeyboard = LocalInputModality.current?.isKeyboard != false
    val isKeyboardFocused = isFocused && isKeyboard
    val isWanted = isHovered || isKeyboardFocused
    val isVisible = delayedVisibility(isWanted = isWanted, openDelay = style.openDelay, closeDelay = style.closeDelay)
    val physical = physicalSideOf(side = placement.side, isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl)
    Box(
        modifier = modifier
            .hoverTracking(onHover = { hovered -> isHovered = hovered }, onPress = { isHovered = false })
            .onFocusChanged { focus -> isFocused = focus.hasFocus },
    ) {
        trigger()
        OverlayPopup(
            isOpen = isVisible && isWanted,
            placement = placement,
            isFocusable = false,
            onDismiss = { isHovered = false },
        ) {
            CompositionLocalProvider(
                LocalContentColor provides style.content,
                LocalTextStyle provides style.textStyle,
            ) {
                Row(
                    modifier = Modifier
                        .widthIn(max = style.maxWidth)
                        .drawBehind { drawArrow(physical = physical, style = style) }
                        .background(color = style.container, shape = style.shape)
                        .padding(horizontal = style.horizontalPadding, vertical = style.verticalPadding),
                    horizontalArrangement = Arrangement.spacedBy(style.gap),
                    verticalAlignment = Alignment.CenterVertically,
                    content = tooltip,
                )
            }
        }
    }
}

private fun DrawScope.drawArrow(physical: Physical, style: TooltipStyle) {
    val side = style.arrowSize.toPx()
    val inset = ARROW_INSET_DP * density
    val center = when (physical) {
        Physical.Top -> Offset(x = size.width / 2, y = size.height - inset)
        Physical.Bottom -> Offset(x = size.width / 2, y = inset)
        Physical.Left -> Offset(x = size.width - inset, y = size.height / 2)
        Physical.Right -> Offset(x = inset, y = size.height / 2)
    }
    rotate(degrees = HALF_TURN_QUARTER, pivot = center) {
        drawRoundRect(
            color = style.container,
            topLeft = Offset(x = center.x - side / 2, y = center.y - side / 2),
            size = Size(width = side, height = side),
            cornerRadius = CornerRadius(style.arrowRadius.toPx()),
        )
    }
}
