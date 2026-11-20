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

package com.davils.kiro.ui.components.inputotp

import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.buttongroup.GroupItem
import com.davils.kiro.ui.components.buttongroup.LocalGroupItem
import com.davils.kiro.ui.components.buttongroup.joinedTo
import com.davils.kiro.ui.components.buttongroup.shapeOr
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.motion.fadeBy
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.state.InteractionState
import com.davils.kiro.ui.core.state.resolve
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

private const val PERCENT: Int = 100
private const val FADED_FROM: Int = 20
private const val FADED_UNTIL: Int = 50
private const val SHOWN_FROM: Int = 70

@Composable
public fun InputOtp(
    value: String,
    onValueChange: (String) -> Unit,
    length: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    style: InputOtpStyle = inputOtpStyle(),
    layout: InputOtpScope.() -> Unit = { group(0 until length) },
) {
    val parts = InputOtpScopeImpl().apply(layout).parts
    val source = remember { MutableInteractionSource() }
    val isFocused by source.collectIsFocusedAsState()
    val active = minOf(value.length, length - 1).takeIf { isFocused }
    val slots = OtpSlots(value = value, active = active, isInvalid = isInvalid)
    BasicTextField(
        value = value,
        onValueChange = { text -> onValueChange(text.take(length)) },
        modifier = modifier.alpha(disabledAlphaOf(isDisabled = !enabled, alpha = style.disabledAlpha)),
        enabled = enabled,
        textStyle = style.textStyle.copy(color = Color.Transparent),
        singleLine = true,
        interactionSource = source,
        cursorBrush = SolidColor(Color.Transparent),
    ) { field ->
        Row(horizontalArrangement = Arrangement.spacedBy(style.gap), verticalAlignment = Alignment.CenterVertically) {
            parts.forEach { part ->
                when (part) {
                    is InputOtpPart.Group -> OtpGroup(slots = part.slots, state = slots, style = style)

                    InputOtpPart.Separator -> CompositionLocalProvider(
                        LocalIconStyle provides IconStyle(size = style.separatorSize),
                    ) {
                        Icon(icon = style.separatorIcon, contentDescription = null)
                    }
                }
            }
            Box(modifier = Modifier.size(0.dp)) { field() }
        }
    }
}

@Composable
private fun OtpGroup(slots: IntRange, state: OtpSlots, style: InputOtpStyle) {
    val shape = RoundedCornerShape(style.radius)
    val ring = Border(width = style.ringWidth, color = invalidRingOf(isInvalid = state.isInvalid, style = style))
    Row(modifier = Modifier.surface(shape = shape, color = Color.Transparent, ring = ring)) {
        slots.forEachIndexed { position, slot ->
            val item = GroupItem(
                orientation = Orientation.Horizontal,
                isFirst = position == 0,
                isLast = slot == slots.last,
                radius = style.radius,
            )
            CompositionLocalProvider(LocalGroupItem provides item) {
                OtpSlot(
                    char = state.value.getOrNull(slot),
                    isActive = slot == state.active,
                    state = state,
                    style = style,
                )
            }
        }
    }
}

@Composable
private fun OtpSlot(char: Char?, isActive: Boolean, state: OtpSlots, style: InputOtpStyle) {
    val item = LocalGroupItem.current
    val borderState = InteractionState(isFocusVisible = isActive, isInvalid = state.isInvalid)
    val ringState = borderState.copy(isInvalid = state.isInvalid && isActive)
    val border = animateColor(style.colors.border.resolve(borderState), style.colorAnimation)
    val ring = animateColor(style.colors.ring.resolve(ringState), style.colorAnimation)
    Box(
        modifier = Modifier
            .joinedTo(item = item, overlap = style.borderWidth)
            .zIndex(layerOf(isActive))
            .size(style.slotSize)
            .surface(
                shape = item.shapeOr(RoundedCornerShape(style.radius)),
                color = style.colors.container.default,
                border = Border(width = style.borderWidth, color = border.value),
                ring = Border(width = style.ringWidth, color = ring.value),
            ),
        contentAlignment = Alignment.Center,
    ) {
        char?.let { typed -> Text(text = typed.toString(), style = style.textStyle) }
        if (isActive && char == null) Caret(style.caret)
    }
}

@Composable
private fun Caret(caret: InputOtpCaret) {
    val blink = caret.period?.let { period ->
        Modifier.fadeBy(
            blinking(period.inWholeMilliseconds.toInt()),
        )
    } ?: Modifier
    Box(modifier = Modifier.size(width = caret.width, height = caret.height).then(blink).background(caret.color))
}

@Composable
private fun blinking(period: Int): State<Float> {
    val transition = rememberInfiniteTransition()
    val spec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = keyframes {
            durationMillis = period
            1f at 0
            0f at period * FADED_FROM / PERCENT
            0f at period * FADED_UNTIL / PERCENT
            1f at period * SHOWN_FROM / PERCENT
            1f at period
        },
        repeatMode = RepeatMode.Restart,
    )
    return transition.animateFloat(initialValue = 1f, targetValue = 1f, animationSpec = spec)
}

private fun invalidRingOf(isInvalid: Boolean, style: InputOtpStyle): Color {
    if (isInvalid) return style.invalidRing
    return Color.Transparent
}

private fun layerOf(isActive: Boolean): Float {
    if (isActive) return 1f
    return 0f
}
