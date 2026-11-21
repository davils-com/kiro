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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.tenthsOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Lock
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.VolumeX

private const val HEADER_ALPHA: Float = 0.85f
private const val BODY_ALPHA: Float = 0.22f
private const val PEAK_ALPHA: Float = 0.5f
private const val BORDER_ALPHA: Float = 0.9f
private const val HOVER_ALPHA: Float = 0.06f
private const val MUTED_ALPHA: Float = 0.4f
private const val FADE_ALPHA: Float = 0.45f
private const val QUIETEST: Float = -36f
private const val LOUDEST: Float = 12f
private const val SMALL: Float = 0.9f

/**
 * Derives the style of a [AudioClip] from the tokens of a theme.
 *
 * Register an implementation under [AudioClipStyleKey] in the components of a theme to change how every audio clip
 * looks.
 *
 * @since 1.0.0
 */
public fun interface AudioClipStyles {
    /**
     * Returns the style of a audio clip for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the audio clip.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AudioClipStyle

    /**
     * Holds the default [AudioClipStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a audio clip.
         *
         * @since 1.0.0
         */
        public val Default: AudioClipStyles = AudioClipStyles(::defaultAudioClipStyle)
    }
}

/**
 * The key of the styles of a [AudioClip] in the components of a theme, with [AudioClipStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AudioClipStyleKey: StyleKey<AudioClipStyles> =
    StyleKey(name = "audio-clip") { _ -> AudioClipStyles.Default }

/**
 * Returns the style of a [AudioClip] in the current theme, with the overrides of its components.
 *
 * @return the style of the audio clip.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun audioClipStyle(): AudioClipStyle = AudioClipStyleKey.current.resolve(Theme.current)

internal fun defaultAudioClipStyle(theme: Theme): AudioClipStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val chart = colors.chart
    val status = colors.status
    val isForegroundLight = colors.foreground.luminance() > colors.background.luminance()
    val light = colors.foreground.takeIf { isForegroundLight } ?: colors.background
    val dark = colors.background.takeIf { isForegroundLight } ?: colors.foreground
    return AudioClipStyle(
        radius = theme.radii.sm,
        headerHeight = spacing(steps = 5f),
        tints = listOf(
            chart.second,
            status.info.foreground,
            status.success.foreground,
            status.warning.foreground,
            chart.fourth,
        ),
        headerAlpha = HEADER_ALPHA,
        bodyAlpha = BODY_ALPHA,
        peakAlpha = PEAK_ALPHA,
        nameStyle = TextStyle(
            fontFamily = theme.typography.families.sans,
            fontSize = theme.typography.scale.xs.fontSize * SMALL,
            fontWeight = FontWeight.SemiBold,
        ),
        lightName = light,
        darkName = dark,
        padding = spacing(steps = 1.5f),
        iconSize = spacing(steps = 3f),
        border = theme.borders.thin,
        borderAlpha = BORDER_ALPHA,
        selectedColor = colors.foreground,
        selectedWidth = theme.borders.thick,
        hoverAlpha = HOVER_ALPHA,
        mutedAlpha = MUTED_ALPHA,
        fadeColor = colors.scrim,
        fadeAlpha = FADE_ALPHA,
        fadeLineColor = colors.foreground,
        lineWidth = theme.borders.thin,
        handleSize = spacing(steps = 2.5f),
        handleColor = colors.foreground,
        gainColor = colors.foreground,
        gainRange = DecibelRange(floor = Decibels(QUIETEST), ceiling = Decibels(LOUDEST)),
        edgeWidth = spacing(steps = 1.5f),
        muted = Lucide.VolumeX,
        locked = Lucide.Lock,
        gainLabel = { gain -> "${tenthsOf(level = gain.value, signed = true)} dB" },
        selectedLabel = Messages.SELECTED,
        mutedLabel = Messages.MUTED,
        lockedLabel = Messages.LOCKED,
    )
}
