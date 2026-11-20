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

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.components.chart.ChartLegendPosition
import com.davils.kiro.ui.components.chart.ChartStyle
import com.davils.kiro.ui.components.chart.spec.ChartSpec
import com.davils.kiro.ui.components.chart.spec.LegendSpec
import com.davils.kiro.ui.components.chart.spec.TooltipSpec
import com.davils.kiro.ui.components.theme
import kotlin.math.roundToInt

private val steps: Map<Key, Int> = mapOf(
    Key.DirectionRight to 1,
    Key.DirectionDown to 1,
    Key.DirectionLeft to -1,
    Key.DirectionUp to -1,
)

@Composable
internal fun ChartFrame(
    modifier: Modifier,
    spec: ChartSpec,
    target: ChartTarget,
    onSize: (IntSize) -> Unit,
    draw: DrawScope.(active: Int?, progress: Float) -> Unit,
) {
    val style = chart().style
    var hover by remember { mutableStateOf<ChartHover?>(null) }
    val current by rememberUpdatedState(target)
    val progress = rememberProgress(style)
    val tooltip = spec.tooltip
    val items = target.legend()
    val pointer = Modifier.hoverInput { position -> hover = position?.let { point -> hoverAt(current, point) } }
    val keys = Modifier.onKeyEvent { event ->
        val step = steps[event.key].takeIf { event.type == KeyEventType.KeyDown && current.count > 0 }
        step?.let { delta -> hover = moved(hover = hover, delta = delta, target = current) }
        step != null
    }
    Column(modifier = modifier.fillMaxSize()) {
        Legend(items = items, legend = spec.legend, position = ChartLegendPosition.Top, style = style)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .onSizeChanged(onSize)
                .then(pointer)
                .onFocusChanged { focus -> if (!focus.isFocused) hover = null }
                .then(keys)
                .focusable(enabled = tooltip != null),
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) { draw(hover?.index.takeIf { tooltip != null }, progress.value) }
            val active = hover
            if (tooltip != null && active != null) {
                TooltipLayer(
                    target = target,
                    hover = active,
                    spec = tooltip,
                    style = style,
                )
            }
        }
        Legend(items = items, legend = spec.legend, position = ChartLegendPosition.Bottom, style = style)
    }
}

@Composable
private fun Legend(items: List<LegendItem>, legend: LegendSpec?, position: ChartLegendPosition, style: ChartStyle) {
    val shown = legend?.takeIf { spec -> spec.position == position } ?: return
    ChartLegendRow(items = items, spec = shown, style = style.legend)
}

@Composable
private fun TooltipLayer(target: ChartTarget, hover: ChartHover, spec: TooltipSpec, style: ChartStyle) {
    val offset = with(LocalDensity.current) { style.tooltip.offset.roundToPx() }
    val placement = Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(
            Constraints(maxWidth = constraints.maxWidth, maxHeight = constraints.maxHeight),
        )
        val x = placedOf(
            position = hover.position.x,
            size = placeable.width,
            limit = constraints.maxWidth,
            offset = offset,
        )
        val y = placedOf(
            position = hover.position.y,
            size = placeable.height,
            limit = constraints.maxHeight,
            offset = offset,
        )
        layout(constraints.maxWidth, constraints.maxHeight) { placeable.place(x, y) }
    }
    ChartTooltipPanel(model = target.tooltipOf(hover.index), spec = spec, style = style.tooltip, modifier = placement)
}

@Composable
private fun rememberProgress(style: ChartStyle): Animatable<Float, *> {
    val isReduced = theme.motion.isReduced
    val progress = remember { Animatable(1f.takeIf { isReduced } ?: 0f) }
    LaunchedEffect(progress) {
        progress.animateTo(1f, tween(durationMillis = style.animationMillis, easing = style.animationEasing))
    }
    return progress
}

private fun Modifier.hoverInput(onHover: (Offset?) -> Unit): Modifier = pointerInput(Unit) {
    awaitPointerEventScope {
        while (true) {
            val event = awaitPointerEvent()
            val position = event.changes.firstOrNull()?.position
            onHover(position.takeIf { event.type != PointerEventType.Exit })
        }
    }
}

private fun hoverAt(target: ChartTarget, point: Offset): ChartHover? {
    val index = target.indexAt(point) ?: return null
    return ChartHover(index = index, position = point)
}

private fun moved(hover: ChartHover?, delta: Int, target: ChartTarget): ChartHover {
    val first = (target.count - 1).takeIf { delta < 0 } ?: 0
    val index = hover?.let { active -> (active.index + delta).mod(target.count) } ?: first
    return ChartHover(index = index, position = target.anchorOf(index))
}

private fun placedOf(position: Float, size: Int, limit: Int, offset: Int): Int {
    val after = position.roundToInt() + offset
    if (after + size <= limit) return after
    return maxOf(position.roundToInt() - offset - size, 0)
}
