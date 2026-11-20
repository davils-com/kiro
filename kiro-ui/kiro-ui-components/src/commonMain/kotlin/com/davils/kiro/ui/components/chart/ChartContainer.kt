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

package com.davils.kiro.ui.components.chart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.chart.frame.ChartContext
import com.davils.kiro.ui.components.chart.frame.LocalChart
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun ChartContainer(
    config: ChartConfig,
    modifier: Modifier = Modifier,
    style: ChartStyle = chartStyle(),
    aspectRatio: Float? = style.aspectRatio,
    content: @Composable () -> Unit,
) {
    val context = remember(config, style) { ChartContext(config = config, style = style) }
    val ratio = aspectRatio?.let { value -> Modifier.aspectRatio(value) } ?: Modifier
    CompositionLocalProvider(
        LocalChart provides context,
        LocalTextStyle provides style.textStyle,
        LocalContentColor provides style.labelColor,
    ) {
        Box(modifier = modifier.then(ratio), contentAlignment = Alignment.Center) { content() }
    }
}
