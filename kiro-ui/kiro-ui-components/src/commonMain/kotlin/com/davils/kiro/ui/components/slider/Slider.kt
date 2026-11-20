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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.Orientation

private const val DEFAULT_MAX: Float = 100f

/**
 * Shows a slider with one thumb for choosing a value from a range, by dragging, by clicking on the track or with the
 * keyboard.
 *
 * @param value the value of the slider.
 * @param onValueChange called with the new value when the user moves the thumb.
 * @param modifier the modifier of the slider.
 * @param valueRange the range of the values.
 * @param step the distance between two values the slider can take, or zero for any value in [valueRange]. The arrow
 * keys move by one step, or by a hundredth of [valueRange] without steps, and the page keys by ten times as much.
 * @param enabled whether the slider accepts input; a disabled slider is drawn with the disabled alpha of its style.
 * @param orientation [Orientation.Horizontal] for a slider whose values grow in the reading direction,
 * [Orientation.Vertical] for one whose values grow upwards.
 * @param style the style of the slider.
 * @since 1.0.0
 */
@Composable
public fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..DEFAULT_MAX,
    step: Float = 1f,
    enabled: Boolean = true,
    orientation: Orientation = Orientation.Horizontal,
    style: SliderStyle = sliderStyle(),
) {
    SliderCore(
        values = listOf(value),
        onValuesChange = { values -> onValueChange(values.first()) },
        modifier = modifier,
        track = SliderTrack(
            range = valueRange,
            step = step,
            isEnabled = enabled,
            geometry = rememberSliderGeometry(orientation),
        ),
        isRange = false,
        style = style,
    )
}

/**
 * Shows a slider with two thumbs for choosing a part of a range, by dragging, by clicking on the track or with the
 * keyboard. A click on the track moves the nearer thumb, and neither thumb can pass the other.
 *
 * @param value the chosen range.
 * @param onValueChange called with the new range when the user moves a thumb.
 * @param modifier the modifier of the slider.
 * @param valueRange the range of the values.
 * @param step the distance between two values the slider can take, or zero for any value in [valueRange]. The arrow
 * keys move by one step, or by a hundredth of [valueRange] without steps, and the page keys by ten times as much.
 * @param enabled whether the slider accepts input; a disabled slider is drawn with the disabled alpha of its style.
 * @param orientation [Orientation.Horizontal] for a slider whose values grow in the reading direction,
 * [Orientation.Vertical] for one whose values grow upwards.
 * @param style the style of the slider.
 * @since 1.0.0
 */
@Composable
public fun RangeSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..DEFAULT_MAX,
    step: Float = 1f,
    enabled: Boolean = true,
    orientation: Orientation = Orientation.Horizontal,
    style: SliderStyle = sliderStyle(),
) {
    SliderCore(
        values = listOf(value.start, value.endInclusive),
        onValuesChange = { values -> onValueChange(values.first()..values.last()) },
        modifier = modifier,
        track = SliderTrack(
            range = valueRange,
            step = step,
            isEnabled = enabled,
            geometry = rememberSliderGeometry(orientation),
        ),
        isRange = true,
        style = style,
    )
}
