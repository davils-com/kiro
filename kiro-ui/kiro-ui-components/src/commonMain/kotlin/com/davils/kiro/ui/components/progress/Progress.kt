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

package com.davils.kiro.ui.components.progress

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import kotlin.math.roundToInt

private const val PERCENT: Float = 100f

@Composable
public fun Progress(
    value: Float?,
    modifier: Modifier = Modifier,
    label: String? = null,
    isValueShown: Boolean = false,
    style: ProgressStyle = progressStyle(),
) {
    val fraction = value?.coerceIn(0f, 1f)
    val animated = animateFloatAsState(targetValue = fraction ?: 0f, animationSpec = style.animation)
    Column(modifier = modifier.semanticsOf(fraction), verticalArrangement = Arrangement.spacedBy(style.gap)) {
        ProgressHeader(fraction = fraction, label = label, isValueShown = isValueShown, style = style)
        val track = Modifier.surface(shape = style.trackShape, color = style.trackColor)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(style.trackHeight)
                .then(track)
                .clip(style.trackShape)
                .drawBehind {
                    drawIndicator(
                        fraction = animated.value,
                        shape = style.trackShape,
                        color = style.indicatorColor,
                    )
                },
        )
    }
}

@Composable
private fun ProgressHeader(fraction: Float?, label: String?, isValueShown: Boolean, style: ProgressStyle) {
    val valueText = percentOf(fraction = fraction, isValueShown = isValueShown)
    if (label == null && valueText == null) return
    Row(modifier = Modifier.fillMaxWidth()) {
        label?.let { text -> Text(text = text, style = style.labelStyle) }
        Spacer(modifier = Modifier.weight(1f))
        valueText?.let { text -> Text(text = text, style = style.valueStyle, color = style.valueColor) }
    }
}

private fun percentOf(fraction: Float?, isValueShown: Boolean): String? {
    if (!isValueShown) return null
    return fraction?.let { known -> "${(known * PERCENT).roundToInt()}%" }
}

private fun Modifier.semanticsOf(fraction: Float?): Modifier {
    if (fraction == null) return progressSemantics()
    return progressSemantics(value = fraction)
}

internal fun defaultProgressStyle(theme: Theme): ProgressStyle {
    val text = theme.typography.scale.sm
    return ProgressStyle(
        gap = 12.dp,
        trackHeight = 4.dp,
        trackShape = CircleShape,
        trackColor = theme.colors.muted,
        indicatorColor = theme.colors.primary,
        labelStyle = theme.textStyleOf(size = text, weight = FontWeight.Medium),
        valueStyle = theme.textStyleOf(size = text).copy(fontFeatureSettings = "tnum"),
        valueColor = theme.colors.mutedForeground,
        animation = animationOf(theme),
    )
}

private fun animationOf(theme: Theme): AnimationSpec<Float> = theme.sizeTransition()

private fun DrawScope.drawIndicator(fraction: Float, shape: Shape, color: Color) {
    val width = size.width * fraction
    if (width <= 0f) return
    val start = (size.width - width).takeIf { layoutDirection == LayoutDirection.Rtl } ?: 0f
    val outline = shape.createOutline(
        size = Size(width = width, height = size.height),
        layoutDirection = layoutDirection,
        density = this,
    )
    translate(left = start) { drawOutline(outline = outline, color = color) }
}
