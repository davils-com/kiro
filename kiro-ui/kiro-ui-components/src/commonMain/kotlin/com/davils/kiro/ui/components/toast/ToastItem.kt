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
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.motion.turnBy
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.color.StatusColor
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle
import kotlinx.coroutines.delay

private const val FULL_TURN: Float = 360f
private const val SPIN_MILLIS: Int = 1000
private const val CLOSE_ICON_SCALE: Float = 0.75f
private const val CLOSE_INSET: Int = 4

@Composable
internal fun ToastItem(
    toast: Toast,
    isPaused: Boolean,
    options: ToastOptions,
    onDismiss: () -> Unit,
    style: ToastStyle,
    modifier: Modifier,
) {
    val remaining = rememberTimer(toast = toast, isPaused = isPaused, style = style, onDone = onDismiss)
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val isFocused by source.collectIsFocusedAsState()
    val status = style.statuses[toast.type]
    val face = faceOf(status = status.takeIf { options.isRich }, style = style)
    CompositionLocalProvider(LocalContentColor provides face.content) {
        Box(
            modifier = modifier.then(
                Modifier
                    .width(style.width)
                    .boxShadow(shape = style.shape, shadow = style.shadow)
                    .then(surfaceOf(face = face, style = style))
                    .hoverable(interactionSource = source)
                    .focusable(interactionSource = source)
                    .semantics { liveRegion = LiveRegionMode.Polite },
            ),
        ) {
            ToastBody(toast = toast, status = status, onDismiss = onDismiss, style = style)
            val isCloseShown = toast.isDismissible && (options.isCloseButtonShown || isHovered || isFocused)
            if (isCloseShown) {
                CloseButton(style = style, modifier = Modifier.align(Alignment.TopEnd), onClick = onDismiss)
            }
            val duration = toast.duration
            if (options.isTimerShown && duration != null) {
                ToastTimerBar(
                    remaining = remaining.value.toFloat() / duration.inWholeMilliseconds,
                    height = style.timerHeight,
                    color = style.timerColor,
                    modifier = Modifier.align(Alignment.BottomStart),
                )
            }
        }
    }
}

@Composable
private fun ToastBody(toast: Toast, status: StatusColor?, onDismiss: () -> Unit, style: ToastStyle) {
    Row(
        modifier = Modifier.padding(style.padding),
        horizontalArrangement = Arrangement.spacedBy(style.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        style.icons[toast.type]?.let { icon ->
            ToastIcon(
                icon = icon,
                color = status?.foreground,
                isSpinning = toast.type == ToastType.Loading,
                style = style,
            )
        }
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(style.gap)) {
            Text(text = toast.title, style = style.titleStyle)
            toast.description?.let { description -> Text(text = description, style = style.descriptionStyle) }
            toast.progress?.let { progress -> ToastProgressBar(progress = progress, style = style) }
        }
        toast.action?.let { action -> ToastActionButton(action = action, style = style.action, onDismiss = onDismiss) }
    }
}

@Composable
private fun rememberTimer(toast: Toast, isPaused: Boolean, style: ToastStyle, onDone: () -> Unit): State<Long> {
    val remaining = remember(toast.id, toast.duration) {
        mutableLongStateOf(toast.duration?.inWholeMilliseconds ?: Long.MAX_VALUE)
    }
    val hasTimer = toast.duration != null
    LaunchedEffect(toast.id, toast.duration, isPaused) {
        val tick = style.tick.inWholeMilliseconds
        while (hasTimer && !isPaused && remaining.longValue > 0) {
            delay(tick)
            remaining.longValue -= tick
        }
        if (hasTimer && remaining.longValue <= 0) onDone()
    }
    return remaining
}

private fun surfaceOf(face: ToastFace, style: ToastStyle): Modifier =
    Modifier.surface(shape = style.shape, color = face.container, border = Border(style.border.width, face.border))

private fun faceOf(status: StatusColor?, style: ToastStyle): ToastFace {
    if (status == null) {
        return ToastFace(
            container = style.container,
            content = style.content,
            border = style.border.color,
        )
    }
    return ToastFace(container = status.surface, content = status.foreground, border = status.border)
}

@Composable
private fun ToastIcon(icon: IconGraphic, color: Color?, isSpinning: Boolean, style: ToastStyle) {
    val isMoving = isSpinning && !theme.motion.isReduced
    val tint = color ?: LocalContentColor.current
    CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize, color = tint)) {
        Icon(icon = icon, contentDescription = null, modifier = spinOf(isMoving))
    }
}

@Composable
private fun CloseButton(style: ToastStyle, modifier: Modifier, onClick: () -> Unit) {
    val label = style.closeLabel
    val press = Modifier
        .pressableCursor()
        .clickable(role = Role.Button, onClick = onClick)
    Box(
        modifier = modifier.then(
            Modifier
                .padding(style.padding / CLOSE_INSET)
                .size(style.closeSize)
                .semantics { contentDescription = label }
                .then(press),
        ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize * CLOSE_ICON_SCALE)) {
            Icon(icon = style.closeIcon, contentDescription = null)
        }
    }
}

@Composable
private fun spinOf(isMoving: Boolean): Modifier {
    if (!isMoving) return Modifier
    return Modifier.turnBy(spinning())
}

@Composable
private fun spinning(): State<Float> {
    val transition = rememberInfiniteTransition()
    return transition.animateFloat(
        initialValue = 0f,
        targetValue = FULL_TURN,
        animationSpec = infiniteRepeatable(
            animation = tween(SPIN_MILLIS, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
    )
}

@Composable
private fun ToastActionButton(action: ToastAction, style: ToastActionStyle, onDismiss: () -> Unit) {
    val click = Modifier
        .pressableCursor()
        .clickable {
            action.onClick()
            onDismiss()
        }
    Box(
        modifier = Modifier
            .height(style.height)
            .background(color = style.container, shape = style.shape)
            .then(click)
            .padding(horizontal = style.horizontalPadding),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = action.label, style = style.textStyle, color = style.content)
    }
}

@Composable
internal fun rememberEntrance(spec: AnimationSpec<Float>): Animatable<Float, *> {
    val entrance = remember { Animatable(1f) }
    LaunchedEffect(entrance) { entrance.animateTo(0f, animationSpec = spec) }
    return entrance
}
