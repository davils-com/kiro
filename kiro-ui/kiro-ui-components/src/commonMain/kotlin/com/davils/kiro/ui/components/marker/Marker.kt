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

package com.davils.kiro.ui.components.marker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * A row of small, muted text and icons that marks a place in a list or a feed, such as a date or a note between
 * messages.
 *
 * [MarkerVariant.Separator] draws a line on both sides of the content, and [MarkerVariant.Border] a line below it.
 *
 * @param modifier the modifier of the row.
 * @param variant the variant of the marker.
 * @param style the style of the marker.
 * @param content the content of the marker, such as a text and a [MarkerIcon].
 * @since 1.0.0
 */
@Composable
public fun Marker(
    modifier: Modifier = Modifier,
    variant: MarkerVariant = MarkerVariant.Default,
    style: MarkerStyle = markerStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    val isSeparated = variant == MarkerVariant.Separator
    CompositionLocalProvider(
        LocalContentColor provides style.color,
        LocalTextStyle provides style.textStyle,
        LocalIconStyle provides IconStyle(size = style.iconSize),
    ) {
        Row(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .heightIn(min = style.minHeight)
                    .then(borderOf(variant = variant, style = style)),
            ),
            horizontalArrangement = Arrangement.spacedBy(style.gap),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isSeparated) Line(style = style, modifier = Modifier.weight(1f).padding(end = style.lineGap))
            content()
            if (isSeparated) Line(style = style, modifier = Modifier.weight(1f).padding(start = style.lineGap))
        }
    }
}

/**
 * A box in the icon size of the style for an icon in a [Marker], hidden from accessibility services.
 *
 * @param modifier the modifier of the box.
 * @param style the style of the marker.
 * @param content the icon, centered in the box.
 * @since 1.0.0
 */
@Composable
public fun MarkerIcon(
    modifier: Modifier = Modifier,
    style: MarkerStyle = markerStyle(),
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.size(style.iconSize).semantics { hideFromAccessibility() },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
private fun Line(style: MarkerStyle, modifier: Modifier) {
    Box(modifier = modifier.height(1.dp).background(style.lineColor))
}

private fun borderOf(variant: MarkerVariant, style: MarkerStyle): Modifier {
    if (variant != MarkerVariant.Border) return Modifier
    val line = Modifier.drawBehind {
        val stroke = 1.dp.toPx()
        drawLine(
            color = style.lineColor,
            start = Offset(x = 0f, y = size.height - stroke / 2),
            end = Offset(x = size.width, y = size.height - stroke / 2),
            strokeWidth = stroke,
        )
    }
    return line.padding(bottom = style.borderPadding)
}
