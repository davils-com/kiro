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

package com.davils.kiro.ui.components.primitive.surface

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import com.davils.kiro.ui.core.elevation.BoxShadow

/**
 * Draws a shadow of [shape] behind the content, one drop shadow for each layer of [shadow].
 *
 * @param shape the shape that casts the shadow.
 * @param shadow the layers of the shadow.
 * @return this modifier with the shadow added.
 * @since 1.0.0
 */
public fun Modifier.boxShadow(shape: Shape, shadow: BoxShadow): Modifier = shadow.layers.fold(
    this,
) { modifier, layer ->
    modifier.dropShadow(
        shape = shape,
        shadow = Shadow(
            radius = layer.blur,
            color = layer.color,
            spread = layer.spread,
            offset = DpOffset(x = layer.offsetX, y = layer.offsetY),
        ),
    )
}
