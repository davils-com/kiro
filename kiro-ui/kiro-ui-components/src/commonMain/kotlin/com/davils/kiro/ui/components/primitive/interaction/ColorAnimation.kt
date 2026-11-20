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

package com.davils.kiro.ui.components.primitive.interaction

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces

internal val PremultipliedColor: TwoWayConverter<Color, AnimationVector4D> = TwoWayConverter(
    convertToVector = { color ->
        val srgb = color.convert(ColorSpaces.Srgb)
        AnimationVector4D(
            v1 = srgb.red * srgb.alpha,
            v2 = srgb.green * srgb.alpha,
            v3 = srgb.blue * srgb.alpha,
            v4 = srgb.alpha,
        )
    },
    convertFromVector = { vector -> colorOf(vector) },
)

/**
 * Animates a color towards [targetValue] in sRGB with premultiplied alpha, so that a fade to or from a transparent
 * color does not pass through the color channels of the transparent one.
 *
 * @param targetValue the color to animate to.
 * @param animationSpec the animation that runs whenever [targetValue] changes.
 * @return the state that holds the current color of the animation.
 * @since 1.0.0
 */
@Composable
public fun animateColor(targetValue: Color, animationSpec: AnimationSpec<Color>): State<Color> =
    animateValueAsState(targetValue = targetValue, typeConverter = PremultipliedColor, animationSpec = animationSpec)

private fun colorOf(vector: AnimationVector4D): Color {
    val alpha = vector.v4.coerceIn(minimumValue = 0f, maximumValue = 1f)
    if (alpha == 0f) return Color.Transparent
    return Color(
        red = (vector.v1 / alpha).coerceIn(minimumValue = 0f, maximumValue = 1f),
        green = (vector.v2 / alpha).coerceIn(minimumValue = 0f, maximumValue = 1f),
        blue = (vector.v3 / alpha).coerceIn(minimumValue = 0f, maximumValue = 1f),
        alpha = alpha,
    )
}
