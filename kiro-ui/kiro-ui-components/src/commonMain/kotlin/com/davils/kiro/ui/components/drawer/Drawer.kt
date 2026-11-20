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

package com.davils.kiro.ui.components.drawer

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.davils.kiro.ui.components.dialog.initialFocus
import com.davils.kiro.ui.components.primitive.motion.spatialSpring
import com.davils.kiro.ui.components.primitive.overlay.ModalLayer
import com.davils.kiro.ui.components.primitive.overlay.Physical
import com.davils.kiro.ui.components.primitive.overlay.Side
import com.davils.kiro.ui.components.primitive.overlay.drawerTransitions
import com.davils.kiro.ui.components.primitive.overlay.latched
import com.davils.kiro.ui.components.primitive.overlay.physicalSideOf
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.sheet.alignmentOf
import com.davils.kiro.ui.components.sheet.edgeSized
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import kotlin.math.roundToInt

@Composable
public fun Drawer(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    side: Side = Side.Bottom,
    style: DrawerStyle = drawerStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val shown = latched(value = side, isOpen = open)
    val physical = physicalSideOf(side = shown, isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl)
    ModalLayer(
        isOpen = open,
        onDismiss = { onOpenChange(false) },
        isScrimDismissing = true,
        scrim = style.scrim,
        transitions = drawerTransitions(motion = theme.motion, physical = physical),
        alignment = alignmentOf(physical),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides style.content,
            LocalTextStyle provides style.textStyle,
            LocalDrawerPhysical provides physical,
        ) {
            DrawerPanel(
                physical = physical,
                modifier = modifier,
                style = style,
                onDismiss = { onOpenChange(false) },
                content = content,
            )
        }
    }
}

@Composable
private fun DrawerPanel(
    physical: Physical,
    modifier: Modifier,
    style: DrawerStyle,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val shape = shapeOf(physical = physical, radius = style.radius)
    val face = Modifier.surface(shape = shape, color = style.container, border = style.border)
    if (isVertical(physical)) {
        EdgeDrawer(
            physical = physical,
            modifier = modifier,
            face = face,
            style = style,
            onDismiss = onDismiss,
            content = content,
        )
        return
    }
    SideDrawer(
        physical = physical,
        modifier = modifier,
        face = face,
        style = style,
        onDismiss = onDismiss,
        content = content,
    )
}

@Composable
private fun EdgeDrawer(
    physical: Physical,
    modifier: Modifier,
    face: Modifier,
    style: DrawerStyle,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    val snaps = remember(style.snapPoints) { DrawerSnaps(points = style.snapPoints) }
    val sign = dismissSignOf(physical)
    val spring = theme.spatialSpring<Float>()
    val drag = Modifier.draggable(
        state = rememberDraggableState { delta -> snaps.drag(-delta * sign) },
        orientation = orientationOf(physical),
        onDragStopped = { settle(snaps = snaps, style = style, spec = spring, onDismiss = onDismiss) },
    )
    val handle: @Composable () -> Unit = { DrawerHandle(isVertical = true, style = style) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .snapped(snaps = snaps, inset = style.edgeInset)
            .then(face)
            .then(drag)
            .initialFocus(),
    ) {
        if (isHandleFirst(physical)) handle()
        Column(modifier = Modifier.weight(1f).clipToBounds(), content = content)
        if (!isHandleFirst(physical)) handle()
    }
}

@Composable
private fun SideDrawer(
    physical: Physical,
    modifier: Modifier,
    face: Modifier,
    style: DrawerStyle,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    var offset by remember { mutableFloatStateOf(0f) }
    var extent by remember { mutableFloatStateOf(1f) }
    val sign = dismissSignOf(physical)
    val spring = theme.spatialSpring<Float>()
    val drag = Modifier.draggable(
        state = rememberDraggableState { delta -> offset = (offset + delta * sign).coerceAtLeast(0f) },
        orientation = orientationOf(physical),
        onDragStopped = {
            val isFar = offset > extent * style.dismissFraction
            if (isFar) onDismiss()
            if (!isFar) {
                animate(
                    initialValue = offset,
                    targetValue = 0f,
                    animationSpec = spring,
                ) { value, _ -> offset = value }
            }
        },
    )
    val handle: @Composable () -> Unit = { DrawerHandle(isVertical = false, style = style) }
    Row(
        modifier = modifier
            .graphicsLayer { translationX = offset * sign }
            .edgeSized(physical = physical, fraction = style.sideFraction, minSize = style.minSize)
            .onSizeChanged { size -> extent = size.width.toFloat() }
            .then(face)
            .then(drag)
            .initialFocus(),
    ) {
        if (isHandleFirst(physical)) handle()
        Column(modifier = Modifier.weight(1f), content = content)
        if (!isHandleFirst(physical)) handle()
    }
}

private suspend fun settle(
    snaps: DrawerSnaps,
    style: DrawerStyle,
    spec: FiniteAnimationSpec<Float>,
    onDismiss: () -> Unit,
) {
    val target = snaps.targetOf(style.dismissFraction)
    if (target == null) {
        onDismiss()
        return
    }
    snaps.settle(target = target, spec = spec)
}

private fun Modifier.snapped(snaps: DrawerSnaps, inset: Dp): Modifier = layout { measurable, constraints ->
    val space = (constraints.maxHeight - inset.roundToPx()).coerceAtLeast(0)
    snaps.measure(space.toFloat())
    val height = snaps.shown.roundToInt().coerceIn(0, space)
    val placeable = measurable.measure(constraints.copy(minHeight = height, maxHeight = height))
    layout(placeable.width, placeable.height) { placeable.place(x = 0, y = 0) }
}

@Composable
private fun DrawerHandle(isVertical: Boolean, style: DrawerStyle) {
    val area = handleAreaOf(isVertical = isVertical, style = style)
    val bar = handleBarOf(isVertical = isVertical, style = style)
    Box(modifier = area, contentAlignment = Alignment.Center) {
        Box(modifier = bar.background(color = style.handleColor, shape = CircleShape))
    }
}

private fun handleAreaOf(isVertical: Boolean, style: DrawerStyle): Modifier {
    if (isVertical) return Modifier.fillMaxWidth().height(style.handleArea)
    return Modifier.fillMaxHeight().width(style.handleArea)
}

private fun handleBarOf(isVertical: Boolean, style: DrawerStyle): Modifier {
    if (isVertical) return Modifier.size(width = style.handleLength, height = style.handleThickness)
    return Modifier.size(width = style.handleThickness, height = style.handleLength)
}

@Composable
public fun DrawerHeader(
    modifier: Modifier = Modifier,
    style: DrawerStyle = drawerStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val isCentered = isVertical(LocalDrawerPhysical.current)
    Column(
        modifier = modifier.fillMaxWidth().padding(
            start = style.sectionPadding,
            top = style.sectionPadding,
            end = style.sectionPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(style.headerGap),
        horizontalAlignment = Alignment.CenterHorizontally.takeIf { isCentered } ?: Alignment.Start,
        content = content,
    )
}

@Composable
public fun ColumnScope.DrawerFooter(
    modifier: Modifier = Modifier,
    style: DrawerStyle = drawerStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Spacer(modifier = Modifier.weight(1f, fill = false))
    Column(
        modifier = modifier.fillMaxWidth().padding(
            start = style.sectionPadding,
            end = style.sectionPadding,
            bottom = style.sectionPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(style.footerGap),
        content = content,
    )
}

@Composable
public fun DrawerTitle(text: String, modifier: Modifier = Modifier, style: DrawerStyle = drawerStyle()) {
    Text(
        text = text,
        modifier = modifier,
        style = style.titleStyle.copy(textAlign = alignOf(LocalDrawerPhysical.current)),
    )
}

@Composable
public fun DrawerDescription(text: String, modifier: Modifier = Modifier, style: DrawerStyle = drawerStyle()) {
    Text(
        text = text,
        modifier = modifier,
        style = style.descriptionStyle.copy(textAlign = alignOf(LocalDrawerPhysical.current)),
        color = style.descriptionColor,
    )
}

private fun alignOf(physical: Physical): TextAlign {
    if (isVertical(physical)) return TextAlign.Center
    return TextAlign.Start
}
