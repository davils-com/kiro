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

package com.davils.kiro.ui.components.slider

import androidx.compose.foundation.focusable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.focus.collectIsFocusVisibleAsState
import com.davils.kiro.ui.core.style.Border

@Composable
internal fun SliderThumb(
    value: Float,
    track: SliderTrack,
    isActive: Boolean,
    focusRequester: FocusRequester,
    onChange: (Float) -> Unit,
    style: SliderStyle,
) {
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val isFocusVisible by source.collectIsFocusVisibleAsState()
    val isRinged = isHovered || isFocusVisible || isActive
    val ring = animateColor(
        targetValue = ringColorOf(isRinged = isRinged, style = style),
        animationSpec = style.colorAnimation,
    )
    val keys = Modifier.onKeyEvent { event ->
        val target = keyTarget(
            key = event.key,
            value = value,
            range = track.range,
            step = track.step,
            isReversed = track.geometry.isReversed,
        )?.takeIf { event.type == KeyEventType.KeyDown }
        target?.let { next -> onChange(snap(value = next, range = track.range, step = track.step)) }
        target != null
    }
    val semantics = Modifier.semantics {
        progressBarRangeInfo = ProgressBarRangeInfo(current = value, range = track.range)
        setProgress { target ->
            onChange(snap(value = target, range = track.range, step = track.step))
            true
        }
        if (!track.isEnabled) disabled()
    }
    Box(
        modifier = Modifier
            .then(semantics)
            .focusRequester(focusRequester)
            .then(keys)
            .hoverable(interactionSource = source, enabled = track.isEnabled)
            .focusable(enabled = track.isEnabled, interactionSource = source)
            .surface(
                shape = CircleShape,
                color = style.thumbColor,
                border = style.thumbBorder,
                ring = Border(width = style.ring.width, color = ring.value),
            ),
    )
}

private fun ringColorOf(isRinged: Boolean, style: SliderStyle): Color {
    if (isRinged) return style.ring.color
    return Color.Transparent
}
