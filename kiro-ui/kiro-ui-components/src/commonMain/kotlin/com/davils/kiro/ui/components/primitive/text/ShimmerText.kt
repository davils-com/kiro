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

package com.davils.kiro.ui.components.primitive.text

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

private const val SHIMMER_SPAN: Float = 600f
private const val SHIMMER_BAND: Float = 120f
private const val SHIMMER_MILLIS: Int = 2000

@Composable
internal fun ShimmerText(
    text: String,
    highlight: Color,
    modifier: Modifier = Modifier,
    durationMillis: Int = SHIMMER_MILLIS,
    style: TextStyle = LocalTextStyle.current,
) {
    val transition = rememberInfiniteTransition()
    val offset by transition.animateFloat(
        initialValue = -SHIMMER_BAND,
        targetValue = SHIMMER_SPAN,
        animationSpec = infiniteRepeatable(tween(durationMillis, easing = LinearEasing), RepeatMode.Restart),
    )
    val base = LocalContentColor.current
    val brush = Brush.linearGradient(
        colors = listOf(base, highlight, base),
        start = Offset(x = offset, y = 0f),
        end = Offset(x = offset + SHIMMER_BAND, y = 0f),
    )
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(brush = brush),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}
