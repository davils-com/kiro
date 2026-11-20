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

package com.davils.kiro.ui.components.buttongroup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation

private val square = CornerSize(0.dp)

internal fun GroupItem?.shapeOr(shape: Shape): Shape {
    val item = this ?: return shape
    val round = CornerSize(item.radius)
    val isStartRound = item.isFirst
    val isEndRound = item.isLast
    return when (item.orientation) {
        Orientation.Horizontal -> RoundedCornerShape(
            topStart = cornerOf(isStartRound, round),
            bottomStart = cornerOf(isStartRound, round),
            topEnd = cornerOf(isEndRound, round),
            bottomEnd = cornerOf(isEndRound, round),
        )

        Orientation.Vertical -> RoundedCornerShape(
            topStart = cornerOf(isStartRound, round),
            topEnd = cornerOf(isStartRound, round),
            bottomStart = cornerOf(isEndRound, round),
            bottomEnd = cornerOf(isEndRound, round),
        )
    }
}

internal fun Modifier.joinedTo(item: GroupItem?, overlap: Dp): Modifier {
    if (item == null) return this
    val stretched = stretch(item)
    return stretched.overlapping(item = item, overlap = overlap)
}

private fun Modifier.overlapping(item: GroupItem, overlap: Dp): Modifier {
    if (item.isFirst) return this
    return layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val pixels = overlap.roundToPx()
        when (item.orientation) {
            Orientation.Horizontal -> layout(
                width = placeable.width - pixels,
                height = placeable.height,
            ) { placeable.placeRelative(x = -pixels, y = 0) }

            Orientation.Vertical -> layout(
                width = placeable.width,
                height = placeable.height - pixels,
            ) { placeable.place(x = 0, y = -pixels) }
        }
    }
}

private fun Modifier.stretch(item: GroupItem): Modifier = when (item.orientation) {
    Orientation.Horizontal -> this
    Orientation.Vertical -> fillMaxWidth()
}

private fun cornerOf(isRound: Boolean, round: CornerSize): CornerSize {
    if (isRound) return round
    return square
}
