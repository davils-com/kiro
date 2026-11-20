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

package com.davils.kiro.ui.components.primitive.layout

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density

/**
 * Gives the content the height of one line of [style] and centers it vertically in that line, so that an icon or a
 * control next to a text lines up with the line of the text.
 *
 * @param style the text style whose line height the content takes.
 * @return this modifier with the line box added.
 * @since 1.0.0
 */
public fun Modifier.lineBox(style: TextStyle): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val height = lineHeightOf(style)
    layout(width = placeable.width, height = height) { placeable.place(x = 0, y = (height - placeable.height) / 2) }
}

private fun Density.lineHeightOf(style: TextStyle): Int {
    val lineHeight = style.lineHeight
    if (lineHeight.isEm) return (style.fontSize.toPx() * lineHeight.value).toInt()
    return lineHeight.roundToPx()
}
