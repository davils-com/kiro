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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.chart.ChartLegendPosition
import com.davils.kiro.ui.components.chart.ChartLegendStyle
import com.davils.kiro.ui.components.chart.spec.LegendSpec
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
internal fun ChartLegendRow(items: List<LegendItem>, spec: LegendSpec, style: ChartLegendStyle) {
    val isTop = spec.position == ChartLegendPosition.Top
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 0.dp.takeIf { isTop } ?: style.spacing, bottom = style.spacing.takeIf { isTop } ?: 0.dp),
        horizontalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(style.itemGap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                LegendMark(item = item, isIconHidden = spec.isIconHidden, style = style)
                Text(text = item.label, style = style.textStyle)
            }
        }
    }
}

@Composable
private fun LegendMark(item: LegendItem, isIconHidden: Boolean, style: ChartLegendStyle) {
    val icon = item.icon?.takeIf { !isIconHidden }
    if (icon == null) {
        Box(modifier = Modifier.size(style.swatchSize).surface(shape = style.swatchShape, color = item.color))
        return
    }
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize, color = style.iconColor)) {
        Icon(icon = icon, contentDescription = null)
    }
}
