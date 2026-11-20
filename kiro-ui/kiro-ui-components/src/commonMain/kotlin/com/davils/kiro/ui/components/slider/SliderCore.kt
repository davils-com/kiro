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

package com.davils.kiro.ui.components.slider

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf

@Composable
internal fun SliderCore(
    values: List<Float>,
    onValuesChange: (List<Float>) -> Unit,
    modifier: Modifier,
    track: SliderTrack,
    isRange: Boolean,
    style: SliderStyle,
) {
    val current by rememberUpdatedState(values)
    val change by rememberUpdatedState(onValuesChange)
    val requesters = remember(values.size) { List(values.size) { FocusRequester() } }
    var active by remember { mutableStateOf<Int?>(null) }
    val fractions = values.map { value -> fractionOf(value = value, range = track.range) }
    val update = { index: Int, value: Float ->
        change(moved(values = current, index = index, value = value, range = track.range))
    }
    Layout(
        content = {
            values.forEachIndexed { index, value ->
                SliderThumb(
                    value = value,
                    track = track,
                    isActive = active == index,
                    focusRequester = requesters[index],
                    onChange = { next -> update(index, next) },
                    style = style,
                )
            }
        },
        modifier = modifier
            .alpha(disabledAlphaOf(isDisabled = !track.isEnabled, alpha = style.disabledAlpha))
            .track(fractions = fractions, isRange = isRange, geometry = track.geometry, style = style)
            .sliderGestures(
                track = track,
                thumbSize = style.thumbSize,
                current = { current },
                onActive = { index ->
                    active = index
                    index?.let { pressed -> requesters[pressed].requestFocus() }
                },
                onMove = update,
            ),
        measurePolicy = SliderMeasurePolicy(
            fractions = fractions,
            orientation = track.geometry.orientation,
            thumbSize = style.thumbSize,
            minLength = style.minLength,
        ),
    )
}

private fun Modifier.track(
    fractions: List<Float>,
    isRange: Boolean,
    geometry: SliderGeometry,
    style: SliderStyle,
): Modifier = drawBehind { drawTrack(fractions = fractions, isRange = isRange, geometry = geometry, style = style) }

private fun Modifier.sliderGestures(
    track: SliderTrack,
    thumbSize: Dp,
    current: () -> List<Float>,
    onActive: (Int?) -> Unit,
    onMove: (Int, Float) -> Unit,
): Modifier {
    if (!track.isEnabled) return this
    return pointerInput(track.range, track.step, track.geometry) {
        val thumb = thumbSize.toPx()
        awaitEachGesture {
            val down = awaitFirstDown()
            val target = valueAtPointer(track = track, position = down.position, size = size, thumb = thumb)
            val index = nearestIndex(values = current(), value = target)
            onActive(index)
            onMove(index, target)
            drag(down.id) { moving ->
                onMove(index, valueAtPointer(track = track, position = moving.position, size = size, thumb = thumb))
                moving.consume()
            }
            onActive(null)
        }
    }
}

private fun valueAtPointer(track: SliderTrack, position: Offset, size: IntSize, thumb: Float): Float {
    val fraction = fractionAt(
        position = track.geometry.along(position, size),
        length = track.geometry.lengthOf(size).toFloat(),
        thumb = thumb,
    )
    return snap(value = valueAt(fraction = fraction, range = track.range), range = track.range, step = track.step)
}

@Composable
internal fun rememberSliderGeometry(orientation: Orientation): SliderGeometry {
    val direction = LocalLayoutDirection.current
    return remember(orientation, direction) { SliderGeometry(orientation = orientation, direction = direction) }
}

private fun DrawScope.drawTrack(
    fractions: List<Float>,
    isRange: Boolean,
    geometry: SliderGeometry,
    style: SliderStyle,
) {
    val thickness = style.trackThickness.toPx()
    val thumb = style.thumbSize.toPx()
    val isHorizontal = geometry.isHorizontal
    val length = lengthOf(isHorizontal = isHorizontal, size = size)
    val centers = fractions.map { fraction -> thumb / 2 + fraction * (length - thumb) }
    val from = centers.first().takeIf { isRange } ?: 0f
    val track = trackRect(isHorizontal = isHorizontal, thickness = thickness, size = size)
    val path = Path()
    path.addRoundRect(RoundRect(rect = track, cornerRadius = CornerRadius(thickness / 2)))
    drawPath(path = path, color = style.trackColor)
    val range = rangeRect(geometry = geometry, from = from, to = centers.last(), track = track)
    clipPath(path) { drawRect(color = style.rangeColor, topLeft = range.topLeft, size = range.size) }
}

private fun lengthOf(isHorizontal: Boolean, size: Size): Float {
    if (isHorizontal) return size.width
    return size.height
}

private fun trackRect(isHorizontal: Boolean, thickness: Float, size: Size): Rect {
    if (isHorizontal) {
        return Rect(
            left = 0f,
            top = (size.height - thickness) / 2,
            right = size.width,
            bottom = (size.height + thickness) / 2,
        )
    }
    return Rect(
        left = (size.width - thickness) / 2,
        top = 0f,
        right = (size.width + thickness) / 2,
        bottom = size.height,
    )
}

private fun rangeRect(geometry: SliderGeometry, from: Float, to: Float, track: Rect): Rect {
    if (!geometry.isHorizontal) {
        return Rect(left = track.left, top = track.bottom - to, right = track.right, bottom = track.bottom - from)
    }
    return horizontalRange(isReversed = geometry.isReversed, from = from, to = to, track = track)
}

private fun horizontalRange(isReversed: Boolean, from: Float, to: Float, track: Rect): Rect {
    if (isReversed) {
        return Rect(
            left = track.right - to,
            top = track.top,
            right = track.right - from,
            bottom = track.bottom,
        )
    }
    return Rect(left = from, top = track.top, right = to, bottom = track.bottom)
}
