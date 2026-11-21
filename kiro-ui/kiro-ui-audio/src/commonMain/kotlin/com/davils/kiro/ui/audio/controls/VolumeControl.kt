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

package com.davils.kiro.ui.audio.controls

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.AudioAction
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.AudioPlayerState
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.fading
import com.davils.kiro.ui.components.slider.Slider
import com.davils.kiro.ui.components.slider.SliderStyle
import com.davils.kiro.ui.components.slider.sliderStyle
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic

private const val PERCENT: Float = 100f

/**
 * The volume of the player: a button that mutes it, with an icon for the level, and a slider that changes it; the
 * mouse wheel changes it too.
 *
 * @param state the state of the player.
 * @param onAction receives [AudioAction.Mute] and [AudioAction.Volume].
 * @param modifier the modifier of the volume control.
 * @param style the style of the audio controls.
 * @param width the width of the slider.
 * @param accent the color of the filled part and the thumb of the slider.
 * @since 1.0.0
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun VolumeControl(
    state: AudioPlayerState,
    onAction: (AudioAction) -> Unit,
    modifier: Modifier = Modifier,
    style: AudioControlsStyle = audioControlsStyle(),
    width: Dp = style.volumeWidth,
    accent: Color = LocalContentColor.current,
) {
    val level = 0f.takeIf { state.isMuted } ?: state.volume
    val slider = volumeSliderOf(base = sliderStyle(), accent = accent, track = LocalContentColor.current, style = style)
    Row(
        modifier = modifier.onPointerEvent(PointerEventType.Scroll) { event ->
            onAction(AudioAction.Volume(level - wheelOf(event) * style.volumeWheelStep))
        },
        horizontalArrangement = Arrangement.spacedBy(style.gap / 2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Crossfade(targetState = iconOf(level = level, style = style), animationSpec = style.motion.fading()) { icon ->
            IconAction(icon = icon, label = style.labels.mute, tint = null, style = style) {
                onAction(AudioAction.Mute)
            }
        }
        Slider(
            value = level * PERCENT,
            onValueChange = { value -> onAction(AudioAction.Volume(value / PERCENT)) },
            modifier = Modifier.width(width),
            style = slider,
        )
    }
}

private fun volumeSliderOf(base: SliderStyle, accent: Color, track: Color, style: AudioControlsStyle): SliderStyle =
    base.copy(
        trackThickness = style.trackHeight,
        trackColor = track.copy(alpha = style.trackAlpha),
        rangeColor = accent,
        thumbSize = style.thumbSize,
        thumbColor = accent,
        thumbBorder = Border(width = Dp.Hairline, color = Color.Transparent),
    )

private fun wheelOf(event: PointerEvent): Float {
    val change = event.changes.firstOrNull() ?: return 0f
    return change.scrollDelta.y
}

internal fun iconOf(level: Float, style: AudioControlsStyle): IconGraphic {
    val icons = style.icons
    val steps = listOf(0f to icons.muted, style.volumeQuiet to icons.quiet, style.volumeMedium to icons.medium)
    return steps.firstOrNull { (limit, _) -> level <= limit }?.second ?: icons.loud
}
