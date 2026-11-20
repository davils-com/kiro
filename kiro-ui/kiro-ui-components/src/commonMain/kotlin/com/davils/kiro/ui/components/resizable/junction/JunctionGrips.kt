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

package com.davils.kiro.ui.components.resizable.junction

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntOffset
import com.davils.kiro.ui.components.resizable.ResizableStyle
import com.davils.kiro.ui.components.resizable.moveCursor
import kotlin.math.roundToInt

private val columnKeys: Map<Key, Float> = mapOf(Key.DirectionRight to 1f, Key.DirectionLeft to -1f)
private val rowKeys: Map<Key, Float> = mapOf(Key.DirectionDown to 1f, Key.DirectionUp to -1f)

@Composable
internal fun JunctionGrips(coordinator: ResizableCoordinator, isGripShown: Boolean, style: ResizableStyle) {
    val tolerance = with(LocalDensity.current) { style.hitArea.toPx() }
    val junctions = junctionsOf(spots = coordinator.spots, tolerance = tolerance)
    junctions.forEach { junction ->
        key(
            junction.columns,
            junction.rows,
        ) { JunctionGrip(junction = junction, isGripShown = isGripShown, style = style) }
    }
}

@Composable
private fun JunctionGrip(junction: Junction, isGripShown: Boolean, style: ResizableStyle) {
    val latest by rememberUpdatedState(junction)
    val hit = maxOf(style.hitArea, style.gripThickness) * 2
    val drag = Modifier.pointerInput(Unit) {
        var total = Offset.Zero
        detectDragGestures(
            onDragStart = {
                total = Offset.Zero
                latest.columns.forEach { actions -> actions.begin() }
                latest.rows.forEach { actions -> actions.begin() }
            },
            onDragEnd = { endAll(latest) },
            onDragCancel = { endAll(latest) },
        ) { change, amount ->
            change.consume()
            total += amount
            latest.columns.forEach { actions -> actions.dragTo(total.x) }
            latest.rows.forEach { actions -> actions.dragTo(total.y) }
        }
    }
    val keys = Modifier.onKeyEvent { event ->
        val isDown = event.type == KeyEventType.KeyDown
        val column = columnKeys[event.key]?.takeIf { isDown }
        val row = rowKeys[event.key]?.takeIf { isDown }
        column?.let { step -> latest.columns.forEach { actions -> actions.nudge(step) } }
        row?.let { step -> latest.rows.forEach { actions -> actions.nudge(step) } }
        column != null || row != null
    }
    val label = style.junctionLabel
    Box(
        modifier = Modifier
            .offset { centered(point = latest.point, size = hit.roundToPx()) }
            .size(hit)
            .semantics { contentDescription = label }
            .pointerHoverIcon(moveCursor())
            .then(drag)
            .then(keys)
            .focusable(),
        contentAlignment = Alignment.Center,
    ) {
        if (isGripShown) {
            val dot = Modifier.size(style.gripThickness * 2)
            Box(modifier = dot.background(color = style.gripColor, shape = style.gripShape))
        }
    }
}

private fun endAll(junction: Junction) {
    junction.columns.forEach { actions -> actions.end() }
    junction.rows.forEach { actions -> actions.end() }
}

private fun centered(point: Offset, size: Int): IntOffset =
    IntOffset(x = point.x.roundToInt() - size / 2, y = point.y.roundToInt() - size / 2)
