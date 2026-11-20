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
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.IconPlacement

/**
 * Lets an icon at the edge of a row reach into the padding of the row by [inset], so that it sits closer to the edge
 * than the text does.
 *
 * The content takes [inset] less width in the row. A [IconPlacement.Leading] icon moves towards the start by [inset], a
 * [IconPlacement.Trailing] icon reaches past the end of its space, and an [IconPlacement.Inline] icon is left as it is.
 *
 * @param placement where the icon sits in the row.
 * @param inset how far the icon reaches into the padding.
 * @return this modifier with the inset added.
 * @since 1.0.0
 */
public fun Modifier.edgeInset(placement: IconPlacement, inset: Dp): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val pixels = inset.roundToPx()
    val shrink = when (placement) {
        IconPlacement.Inline -> 0
        IconPlacement.Leading -> pixels
        IconPlacement.Trailing -> pixels
    }
    val shift = when (placement) {
        IconPlacement.Inline -> 0
        IconPlacement.Leading -> -pixels
        IconPlacement.Trailing -> 0
    }
    layout(width = placeable.width - shrink, height = placeable.height) { placeable.placeRelative(x = shift, y = 0) }
}
