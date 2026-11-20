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

package com.davils.kiro.ui.components.toast

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.davils.kiro.ui.components.primitive.motion.animatedHeight
import com.davils.kiro.ui.components.primitive.motion.spatialSpring
import com.davils.kiro.ui.components.primitive.motion.tweenOf
import com.davils.kiro.ui.components.primitive.overlay.hoverTracking
import com.davils.kiro.ui.components.theme
import kotlin.math.abs

private const val RESISTANCE: Float = 0.15f
private const val EXIT_SHIFT: Float = 0.5f

@Composable
public fun Toaster(
    toaster: ToasterState,
    modifier: Modifier = Modifier,
    position: ToastPosition = ToastPosition.BottomEnd,
    isRich: Boolean = false,
    isCloseButtonShown: Boolean = false,
    isTimerShown: Boolean = false,
    style: ToastStyle = toastStyle(),
) {
    val shown = toaster.toasts.takeLast(style.visibleCount)
    if (shown.isEmpty()) return
    val density = LocalDensity.current
    var isExpanded by remember { mutableStateOf(false) }
    val heights = remember { mutableStateMapOf<ToastId, Int>() }
    val isTopEdge = isTop(position)
    val newestFirst = shown.reversed()
    val layout = stackOf(
        toasts = newestFirst,
        heights = heights,
        isExpanded = isExpanded,
        style = style,
        density = density,
    )
    val height = animateDpAsState(
        targetValue = with(density) { layout.height.toDp() },
        animationSpec = theme.spatialSpring(),
    )
    val options = ToastOptions(
        isRich = isRich,
        isCloseButtonShown = isCloseButtonShown,
        isTimerShown = isTimerShown,
        swipe = swipeOf(position),
    )
    val provider = remember(position, density) {
        CornerPositionProvider(position = position, offset = with(density) { style.edgeOffset.roundToPx() })
    }
    Popup(popupPositionProvider = provider, properties = PopupProperties(focusable = false)) {
        Box(
            modifier = modifier
                .width(style.width)
                .animatedHeight(height)
                .hoverTracking(onHover = { hovered -> isExpanded = hovered }),
            contentAlignment = Alignment.TopCenter.takeIf { isTopEdge } ?: Alignment.BottomCenter,
        ) {
            shown.forEach { toast ->
                key(toast.id) {
                    StackedToast(
                        toast = toast,
                        placement = StackPlacement(
                            offset = layout.offsets.getValue(toast.id).toFloat(),
                            scale = scaleOf(depth = newestFirst.indexOf(toast), isExpanded = isExpanded, style = style),
                            isTopEdge = isTopEdge,
                        ),
                        isPaused = isExpanded,
                        heights = heights,
                        toaster = toaster,
                        options = options,
                        style = style,
                    )
                }
            }
        }
    }
}

@Composable
private fun StackedToast(
    toast: Toast,
    placement: StackPlacement,
    isPaused: Boolean,
    heights: MutableMap<ToastId, Int>,
    toaster: ToasterState,
    options: ToastOptions,
    style: ToastStyle,
) {
    val offset = animateFloatAsState(placement.offset, theme.spatialSpring())
    val scale = animateFloatAsState(placement.scale, theme.spatialSpring())
    val entrance = rememberEntrance(spec = theme.tweenOf(theme.motion.durations.long, theme.motion.easings.emphasized))
    val exit = remember { Animatable(0f) }
    val swiped = remember { mutableFloatStateOf(0f) }
    val isLeaving = toaster.isLeaving(toast.id)
    val leave = theme.tweenOf<Float>(theme.motion.durations.medium, theme.motion.easings.exit)
    val settle = theme.spatialSpring<Float>()
    val width = with(LocalDensity.current) { style.width.toPx() }
    LaunchedEffect(isLeaving) { leaveIf(isLeaving = isLeaving, exit = exit, spec = leave) { toaster.remove(toast.id) } }
    val drag = Modifier.draggable(
        state = rememberDraggableState { delta -> swiped.floatValue += delta },
        orientation = Orientation.Horizontal,
        enabled = toast.isDismissible && !isLeaving,
        onDragStopped = { velocity ->
            val shown = resisted(swiped = swiped.floatValue, swipe = options.swipe)
            val outcome = SwipeRelease(
                swiped = swiped.floatValue,
                shown = shown,
                velocity = velocity,
                width = width,
                threshold = style.swipeThreshold,
            )
            release(
                outcome = outcome,
                spec = settle.takeUnless { outcome.isGone } ?: leave,
            ) { value -> swiped.floatValue = value }
            if (outcome.isGone) toaster.remove(toast.id)
        },
    )
    ToastItem(
        toast = toast,
        isPaused = isPaused,
        options = options,
        onDismiss = { toaster.dismiss(toast.id) },
        style = style,
        modifier = stackedLayer(
            toast = toast,
            placement = placement,
            heights = heights,
            motion = StackMotion(
                offset = offset,
                scale = scale,
                entrance = entrance.asState(),
                exit = exit.asState(),
                swiped = swiped,
            ),
            options = options,
            width = width,
            drag = drag,
        ),
    )
}

private fun stackedLayer(
    toast: Toast,
    placement: StackPlacement,
    heights: MutableMap<ToastId, Int>,
    motion: StackMotion,
    options: ToastOptions,
    width: Float,
    drag: Modifier,
): Modifier {
    val direction = 1f.takeIf { placement.isTopEdge } ?: -1f
    val align = Alignment.Top.takeIf { placement.isTopEdge } ?: Alignment.Bottom
    val measured = Modifier
        .wrapContentHeight(align = align, unbounded = true)
        .onSizeChanged { size -> heights[toast.id] = size.height }
        .then(drag)
    return measured.graphicsLayer {
        val height = heights[toast.id] ?: 0
        val entrance = motion.entrance.value
        val exit = motion.exit.value
        val shift = resisted(swiped = motion.swiped.value, swipe = options.swipe)
        val hidden = entrance + exit * EXIT_SHIFT
        translationX = shift
        translationY = direction * (motion.offset.value - hidden * height)
        alpha = (1f - entrance) * (1f - exit) * (1f - (abs(shift) / width).coerceAtMost(1f))
        scaleX = motion.scale.value
        scaleY = motion.scale.value
        val pivot = 0f.takeIf { placement.isTopEdge } ?: 1f
        transformOrigin = TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = pivot)
    }
}

private suspend fun leaveIf(
    isLeaving: Boolean,
    exit: Animatable<Float, *>,
    spec: FiniteAnimationSpec<Float>,
    onLeft: () -> Unit,
) {
    if (!isLeaving) return
    exit.animateTo(1f, animationSpec = spec)
    onLeft()
}

private suspend fun release(outcome: SwipeRelease, spec: FiniteAnimationSpec<Float>, onFrame: (Float) -> Unit) {
    animate(
        initialValue = outcome.swiped,
        targetValue = outcome.target,
        animationSpec = spec,
    ) { value, _ -> onFrame(value) }
}

private fun resisted(swiped: Float, swipe: Float): Float {
    val isAgainstEdge = swipe != 0f && swiped * swipe < 0f
    return (swiped * RESISTANCE).takeIf { isAgainstEdge } ?: swiped
}

private fun swipeOf(position: ToastPosition): Float = when (position) {
    ToastPosition.TopStart, ToastPosition.BottomStart -> -1f
    ToastPosition.TopCenter, ToastPosition.BottomCenter -> 0f
    ToastPosition.TopEnd, ToastPosition.BottomEnd -> 1f
}

private fun scaleOf(depth: Int, isExpanded: Boolean, style: ToastStyle): Float {
    if (isExpanded) return 1f
    return 1f - depth * style.scaleStep
}
