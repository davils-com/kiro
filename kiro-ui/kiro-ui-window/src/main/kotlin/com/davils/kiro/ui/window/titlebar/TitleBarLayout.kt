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

package com.davils.kiro.ui.window.titlebar

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.window.controls.ControlPlacement

@Composable
internal fun TitleBarLayout(
    slots: TitleBarSlots,
    actions: WindowActions,
    style: TitleBarStyle,
    modifier: Modifier = Modifier,
    chrome: TitleBarChrome = TitleBarChrome.Drawn,
) {
    val isActive = LocalWindowInfo.current.isWindowFocused
    val background = animateColor(targetValue = backgroundOf(style, isActive), animationSpec = style.colorAnimation)
    val content = animateColor(targetValue = contentOf(style, isActive), animationSpec = style.colorAnimation)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val bar = modifier.then(
        Modifier
            .fillMaxWidth()
            .height(style.height)
            .drawBehind { drawBar(color = background.value, divider = style.divider) }
            .then(maximizeOnDoubleTap(actions = actions, chrome = chrome)),
    )
    CompositionLocalProvider(LocalContentColor provides content.value, LocalTextStyle provides style.titleStyle) {
        Layout(
            contents = listOf(
                { Controls(placement = ControlPlacement.Start, actions = actions, style = style, chrome = chrome) },
                { SlotRow(parts = slots.start, style = style) },
                { SlotRow(parts = slots.center, style = style) },
                { SlotRow(parts = slots.end, style = style) },
                { Controls(placement = ControlPlacement.End, actions = actions, style = style, chrome = chrome) },
            ),
            modifier = bar,
        ) { measurables, constraints ->
            measureBar(
                parts = BarParts(measurables),
                constraints = constraints,
                style = style,
                chrome = chrome,
                isRtl = isRtl,
            )
        }
    }
}

private fun MeasureScope.measureBar(
    parts: BarParts,
    constraints: Constraints,
    style: TitleBarStyle,
    chrome: TitleBarChrome,
    isRtl: Boolean,
): MeasureResult {
    val width = constraints.maxWidth
    val height = constraints.maxHeight
    val padding = style.horizontalPadding.roundToPx()
    val gap = style.gap.roundToPx()
    val insetLeft = chrome.insets.left.roundToPx()
    val insetRight = chrome.insets.right.roundToPx()
    val controlsLeft = parts.startControls.fit(width = width, height = height)
    val controlsRight = parts.endControls.fit(width = width, height = height)
    val leftEdge = insetLeft + controlsLeft.widthOrZero() + padding
    val rightEdge = width - insetRight - controlsRight.widthOrZero() - padding
    val free = maxOf(a = 0, b = rightEdge - leftEdge)
    val start = parts.start.fit(width = free, height = height)
    val startWidth = start.widthOrZero()
    val end = parts.end.fit(width = maxOf(a = 0, b = free - startWidth - gapAfter(startWidth, gap)), height = height)
    val leading = physical(isRtl = isRtl, left = start, right = end)
    val trailing = physical(isRtl = isRtl, left = end, right = start)
    val roomLeft = leftEdge + leading.widthOrZero() + gapAfter(leading.widthOrZero(), gap)
    val roomRight = rightEdge - trailing.widthOrZero() - gapAfter(trailing.widthOrZero(), gap)
    val center = parts.center.fit(width = maxOf(a = 0, b = roomRight - roomLeft), height = height)
    val title = titleOffset(
        placement = style.titlePlacement,
        width = width,
        title = center.widthOrZero(),
        room = roomLeft..roomRight,
        isRtl = isRtl,
    )
    return layout(width = width, height = height) {
        controlsLeft?.place(x = insetLeft, y = (height - controlsLeft.height) / 2)
        controlsRight?.let { placeable ->
            placeable.place(x = width - insetRight - placeable.width, y = (height - placeable.height) / 2)
        }
        leading?.place(x = leftEdge, y = (height - leading.height) / 2)
        trailing?.let { placeable ->
            placeable.place(
                x = rightEdge - placeable.width,
                y = (height - placeable.height) / 2,
            )
        }
        center?.place(x = title, y = (height - center.height) / 2)
    }
}

private fun Measurable?.fit(width: Int, height: Int): Placeable? =
    this?.measure(Constraints(minWidth = 0, maxWidth = width, minHeight = 0, maxHeight = height))

private fun Placeable?.widthOrZero(): Int = this?.width ?: 0

private fun physical(isRtl: Boolean, left: Placeable?, right: Placeable?): Placeable? {
    if (isRtl) return right
    return left
}

@Composable
private fun SlotRow(parts: List<@Composable RowScope.() -> Unit>, style: TitleBarStyle) {
    if (parts.isEmpty()) return
    Row(
        horizontalArrangement = Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) { parts.forEach { part -> part() } }
}

@Composable
private fun Controls(
    placement: ControlPlacement,
    actions: WindowActions,
    style: TitleBarStyle,
    chrome: TitleBarChrome,
) {
    val controls = style.controls
    if (chrome.isSystemControlled || controls.placement != placement) return
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        controls.Content(actions = actions, style = style)
    }
}

private fun maximizeOnDoubleTap(actions: WindowActions, chrome: TitleBarChrome): Modifier {
    if (chrome.isSystemControlled) return Modifier
    return Modifier.pointerInput(actions) { detectTapGestures(onDoubleTap = { actions.toggleMaximize() }) }
}

private fun DrawScope.drawBar(color: Color, divider: Border?) {
    drawRect(color = color)
    val line = divider ?: return
    val width = line.width.toPx()
    drawRect(
        color = line.color,
        topLeft = Offset(x = 0f, y = size.height - width),
        size = Size(width = size.width, height = width),
    )
}

private fun backgroundOf(style: TitleBarStyle, isActive: Boolean): Color {
    if (isActive) return style.background
    return style.inactiveBackground
}

private fun contentOf(style: TitleBarStyle, isActive: Boolean): Color {
    if (isActive) return style.content
    return style.inactiveContent
}
