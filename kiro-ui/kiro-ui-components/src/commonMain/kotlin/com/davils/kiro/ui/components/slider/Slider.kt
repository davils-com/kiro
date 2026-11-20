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
