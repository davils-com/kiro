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

package com.davils.kiro.ui.components.chart.frame

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.chart.ChartTooltipIndicator
import com.davils.kiro.ui.components.chart.ChartTooltipStyle
import com.davils.kiro.ui.components.chart.spec.TooltipSpec
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private const val DASH: Float = 3f

@Composable
internal fun ChartTooltipPanel(
    model: TooltipModel,
    spec: TooltipSpec,
    style: ChartTooltipStyle,
    modifier: Modifier = Modifier,
) {
    val isNested = model.rows.size == 1 && spec.indicator != ChartTooltipIndicator.Dot
    val label = model.label?.takeIf { !spec.isLabelHidden }
    val edge = Border(width = 1.dp, color = style.border)
    Column(
        modifier = modifier.then(
            Modifier
                .widthIn(min = style.minWidth)
                .boxShadow(shape = style.shape, shadow = style.shadow)
                .surface(shape = style.shape, color = style.container, border = edge)
                .padding(style.padding)
                .width(IntrinsicSize.Max),
        ),
        verticalArrangement = Arrangement.spacedBy(style.gap),
    ) {
        if (!isNested) label?.let { text -> Text(text = text, style = style.labelStyle) }
        model.rows.forEach { row ->
            TooltipLine(row = row, label = label.takeIf { isNested }, spec = spec, style = style)
        }
    }
}

@Composable
private fun TooltipLine(row: TooltipRow, label: String?, spec: TooltipSpec, style: ChartTooltipStyle) {
    val isDot = spec.indicator == ChartTooltipIndicator.Dot
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically.takeIf { isDot } ?: Alignment.Top,
    ) {
        TooltipIndicator(row = row, isNested = label != null, spec = spec, style = style)
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom.takeIf { label != null } ?: Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(style.gap)) {
                label?.let { text -> Text(text = text, style = style.labelStyle) }
                Text(text = row.name, style = style.nameStyle, color = style.nameColor)
            }
            Text(
                text = spec.format(row.value),
                modifier = Modifier.padding(start = 8.dp),
                style = style.valueStyle,
                color = style.valueColor,
            )
        }
    }
}

@Composable
private fun TooltipIndicator(row: TooltipRow, isNested: Boolean, spec: TooltipSpec, style: ChartTooltipStyle) {
    val icon = row.icon
    if (icon != null) {
        CompositionLocalProvider(
            LocalIconStyle provides IconStyle(size = style.iconSize, color = style.nameColor),
        ) {
            Icon(icon = icon, contentDescription = null)
        }
        return
    }
    if (spec.isIndicatorHidden) return
    when (spec.indicator) {
        ChartTooltipIndicator.Dot -> {
            Box(modifier = Modifier.size(style.indicatorSize).surface(shape = style.indicatorShape, color = row.color))
        }

        ChartTooltipIndicator.Line -> {
            Box(
                modifier = Modifier
                    .width(style.lineWidth)
                    .fillMaxHeight()
                    .surface(shape = style.indicatorShape, color = row.color),
            )
        }

        ChartTooltipIndicator.Dashed -> {
            DashedIndicator(row = row, isNested = isNested, style = style)
        }
    }
}

@Composable
private fun DashedIndicator(row: TooltipRow, isNested: Boolean, style: ChartTooltipStyle) {
    val inset = 2.dp.takeIf { isNested } ?: 0.dp
    Canvas(modifier = Modifier.padding(vertical = inset).width(style.dashWidth).fillMaxHeight()) {
        val dash = DASH * density
        drawLine(
            color = row.color,
            start = Offset(x = size.width / 2, y = 0f),
            end = Offset(x = size.width / 2, y = size.height),
            strokeWidth = size.width,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dash, dash)),
        )
    }
}
