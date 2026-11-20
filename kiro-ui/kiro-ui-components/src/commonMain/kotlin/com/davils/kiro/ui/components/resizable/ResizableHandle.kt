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

package com.davils.kiro.ui.components.resizable

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Constraints
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.resizable.junction.HandleActions
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState

@Composable
internal fun ResizableHandle(
    orientation: Orientation,
    isGripShown: Boolean,
    actions: HandleActions,
    onPlaced: (LayoutCoordinates) -> Unit,
    style: ResizableStyle,
) {
    val latest by rememberUpdatedState(actions)
    val source = remember { MutableInteractionSource() }
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    val isHorizontal = orientation == Orientation.Horizontal
    val line = colorOf(isFocusVisible = isFocusVisible, style = style)
    val cursor = resizeCursor(isHorizontal = isHorizontal)
    val divider = Modifier.drawBehind {
        val stroke = (style.thickness + style.focusWidth * focusSteps(isFocusVisible)).toPx()
        val start = Offset(x = size.width / 2, y = 0f).takeIf { isHorizontal } ?: Offset(x = 0f, y = size.height / 2)
        val end = Offset(x = size.width / 2, y = size.height).takeIf { isHorizontal }
            ?: Offset(x = size.width, y = size.height / 2)
        drawLine(color = line, start = start, end = end, strokeWidth = stroke)
    }
    val drag = Modifier.pointerInput(orientation) {
        var total = 0f
        detectDragGestures(
            onDragStart = {
                total = 0f
                latest.begin()
            },
            onDragEnd = { latest.end() },
            onDragCancel = { latest.end() },
        ) { change, amount ->
            change.consume()
            total += amount.x.takeIf { isHorizontal } ?: amount.y
            latest.dragTo(total)
        }
    }
    val keys = Modifier.onKeyEvent { event ->
        val step = keyStepOf(key = event.key, isHorizontal = isHorizontal).takeIf { event.type == KeyEventType.KeyDown }
        step?.let(latest.nudge)
        step != null
    }
    Box(
        modifier = Modifier
            .onGloballyPositioned(onPlaced)
            .expandedHitArea(isHorizontal = isHorizontal, style = style)
            .then(divider)
            .pointerHoverIcon(cursor)
            .then(drag)
            .then(keys)
            .focusable(interactionSource = source),
        contentAlignment = Alignment.Center,
    ) {
        if (isGripShown) {
            Box(
                modifier = gripSizeOf(
                    isHorizontal = isHorizontal,
                    style = style,
                ).background(color = style.gripColor, shape = style.gripShape),
            )
        }
    }
}

private fun Modifier.expandedHitArea(isHorizontal: Boolean, style: ResizableStyle): Modifier = layout {
        measurable,
        constraints,
    ->
    val hit = style.hitArea.roundToPx()
    val grip = style.gripThickness.roundToPx()
    val main = maxOf(a = hit, b = grip)
    val stretched = Constraints.fixed(width = main, height = constraints.maxHeight).takeIf { isHorizontal }
        ?: Constraints.fixed(width = constraints.maxWidth, height = main)
    val placeable = measurable.measure(stretched)
    layout(width = constraints.maxWidth, height = constraints.maxHeight) {
        val shiftX = ((constraints.maxWidth - placeable.width) / 2).takeIf { isHorizontal } ?: 0
        val shiftY = ((constraints.maxHeight - placeable.height) / 2).takeUnless { isHorizontal } ?: 0
        placeable.place(x = shiftX, y = shiftY)
    }
}

private val horizontalKeys: Map<Key, Float> = mapOf(Key.DirectionRight to 1f, Key.DirectionLeft to -1f)
private val verticalKeys: Map<Key, Float> = mapOf(Key.DirectionDown to 1f, Key.DirectionUp to -1f)

private fun keyStepOf(key: Key, isHorizontal: Boolean): Float? {
    if (isHorizontal) return horizontalKeys[key]
    return verticalKeys[key]
}

private fun gripSizeOf(isHorizontal: Boolean, style: ResizableStyle): Modifier {
    if (isHorizontal) return Modifier.size(width = style.gripThickness, height = style.gripLength)
    return Modifier.size(width = style.gripLength, height = style.gripThickness)
}

private fun colorOf(isFocusVisible: Boolean, style: ResizableStyle): Color {
    if (isFocusVisible) return style.focusColor
    return style.color
}

private fun focusSteps(isFocusVisible: Boolean): Int {
    if (isFocusVisible) return 2
    return 0
}
