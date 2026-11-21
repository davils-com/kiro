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

package com.davils.kiro.ui.audio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.Colors
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Disc3
import com.davils.kiro.ui.icons.lucide.ListMusic
import com.davils.kiro.ui.icons.lucide.Lucide
import com.davils.kiro.ui.icons.lucide.Repeat
import com.davils.kiro.ui.icons.lucide.Repeat1
import com.davils.kiro.ui.icons.lucide.Shuffle
import com.davils.kiro.ui.icons.lucide.SkipBack
import com.davils.kiro.ui.icons.lucide.SkipForward
import com.davils.kiro.ui.icons.lucide.Volume
import com.davils.kiro.ui.icons.lucide.Volume1
import com.davils.kiro.ui.icons.lucide.Volume2
import com.davils.kiro.ui.icons.lucide.VolumeX
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private const val SECONDARY_ALPHA: Float = 0.7f
private const val TRACK_ALPHA: Float = 0.2f
private const val BUFFER_ALPHA: Float = 0.35f
private const val HOVER_ALPHA: Float = 0.12f
private const val IDLE_ALPHA: Float = 0.75f
private const val PRESSED_SCALE: Float = 0.9f
private const val PAUSED_SCALE: Float = 0.86f
private const val GLOW_ALPHA: Float = 0.5f
private const val SCRIM: Float = 0.35f
private const val SATURATION: Float = 1.4f
private const val TOP_ALPHA: Float = 0.55f
private const val BOTTOM_ALPHA: Float = 0.9f
private const val BREATH: Float = 1.06f
private const val BREATH_SECONDS: Int = 9
private const val BARS: Int = 4
private const val REST: Float = 0.18f
private const val SPEED: Float = 0.0045f
private const val SPREAD: Float = 1.7f
private const val WAVE: Float = 0.6f
private const val BAR_GAP: Float = 0.5f
private const val WHEEL_STEP: Float = 0.05f
private const val QUIET: Float = 0.34f
private const val MEDIUM: Float = 0.67f
private const val MUTED_DEPTH: Float = 0.45f
private const val VIBRANT: Float = 0.25f
private const val SLIDE: Float = 0.25f
private const val SEEK_SECONDS: Int = 5
private const val TABULAR: String = "tnum"
private const val SECONDS_PER_MINUTE: Long = 60L
private const val MINUTES_PER_HOUR: Long = 60L
private const val PADDED: Int = 2

/**
 * Derives the [AudioControlsStyle] of the audio players from the tokens of a theme.
 *
 * Register an implementation under [AudioControlsStyleKey] in the components of a theme to change the controls of
 * every audio player.
 *
 * @since 1.0.0
 */
public fun interface AudioControlsStyles {
    /**
     * Returns the style of the audio controls for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the audio controls.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AudioControlsStyle

    /**
     * Holds the default [AudioControlsStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of the audio controls.
         *
         * @since 1.0.0
         */
        public val Default: AudioControlsStyles = AudioControlsStyles(::defaultAudioControlsStyle)
    }
}

/**
 * The key of the styles of the audio controls in the components of a theme, with [AudioControlsStyles.Default] as
 * its default.
 *
 * @since 1.0.0
 */
public val AudioControlsStyleKey: StyleKey<AudioControlsStyles> =
    StyleKey(name = "audio-controls") { _ -> AudioControlsStyles.Default }

/**
 * Returns the style of the audio controls in the current theme, with the overrides of its components.
 *
 * @return the style of the audio controls.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun audioControlsStyle(): AudioControlsStyle = AudioControlsStyleKey.current.resolve(theme)

internal fun defaultAudioControlsStyle(theme: Theme): AudioControlsStyle {
    val scale = theme.typography.scale
    val sans = theme.typography.families.sans
    val spacing = theme.spacing
    val colors = theme.colors
    val (light, dark) = contentsOf(colors)
    return AudioControlsStyle(
        gap = spacing(steps = 3f),
        titleStyle = TextStyle(fontFamily = sans, fontSize = scale.sm.fontSize, fontWeight = FontWeight.SemiBold),
        artistStyle = TextStyle(fontFamily = sans, fontSize = scale.xs.fontSize),
        timeStyle = TextStyle(fontFamily = sans, fontSize = scale.xs.fontSize, fontFeatureSettings = TABULAR),
        secondaryAlpha = SECONDARY_ALPHA,
        trackHeight = spacing(steps = 1f),
        trackAlpha = TRACK_ALPHA,
        bufferAlpha = BUFFER_ALPHA,
        thumbSize = spacing(steps = 2.5f),
        thumbActiveSize = spacing(steps = 3.5f),
        seekStep = SEEK_SECONDS.seconds,
        iconSize = spacing(steps = 4.5f),
        actionSize = spacing(steps = 8f),
        hoverAlpha = HOVER_ALPHA,
        idleAlpha = IDLE_ALPHA,
        playSize = spacing(steps = 11f),
        pressedScale = PRESSED_SCALE,
        playContainer = colors.foreground,
        playContent = colors.background,
        coverRadius = theme.radii.xl,
        coverElevation = spacing(steps = 6f),
        pausedScale = PAUSED_SCALE,
        glowAlpha = GLOW_ALPHA,
        placeholderIconSize = spacing(steps = 9f),
        backdropBlur = spacing(steps = 12f),
        backdropScrim = SCRIM,
        backdropSaturation = SATURATION,
        backdropTopAlpha = TOP_ALPHA,
        backdropBottomAlpha = BOTTOM_ALPHA,
        breathScale = BREATH,
        breathDuration = BREATH_SECONDS.seconds,
        visualizerSize = spacing(steps = 4f),
        visualizerBars = BARS,
        visualizerRest = REST,
        visualizerSpeed = SPEED,
        visualizerSpread = SPREAD,
        visualizerWave = WAVE,
        visualizerGap = BAR_GAP,
        volumeWidth = spacing(steps = 28f),
        volumeWheelStep = WHEEL_STEP,
        volumeQuiet = QUIET,
        volumeMedium = MEDIUM,
        lightContent = light,
        darkContent = dark,
        mutedDepth = MUTED_DEPTH,
        vibrantSaturation = VIBRANT,
        slideShare = SLIDE,
        motion = theme.motion,
        icons = defaultAudioIcons(),
        labels = defaultAudioLabels(),
        time = ::clockOf,
    )
}

private fun contentsOf(colors: Colors): Pair<Color, Color> {
    val isForegroundLight = colors.foreground.luminance() > colors.background.luminance()
    if (isForegroundLight) return colors.foreground to colors.background
    return colors.background to colors.foreground
}

private fun defaultAudioIcons(): AudioIcons = AudioIcons(
    previous = Lucide.SkipBack,
    next = Lucide.SkipForward,
    shuffle = Lucide.Shuffle,
    repeat = Lucide.Repeat,
    repeatOne = Lucide.Repeat1,
    muted = Lucide.VolumeX,
    quiet = Lucide.Volume,
    medium = Lucide.Volume1,
    loud = Lucide.Volume2,
    queue = Lucide.ListMusic,
    placeholder = Lucide.Disc3,
)

private fun defaultAudioLabels(): AudioLabels = AudioLabels(
    play = Messages.PLAY,
    pause = Messages.PAUSE,
    previous = Messages.PREVIOUS,
    next = Messages.NEXT,
    shuffle = Messages.SHUFFLE,
    repeat = Messages.REPEAT,
    mute = Messages.MUTE,
    queue = Messages.QUEUE,
    seek = Messages.SEEK,
    nothingPlaying = Messages.NOTHING_PLAYING,
)

internal fun clockOf(duration: Duration): String {
    val total = duration.coerceAtLeast(Duration.ZERO).inWholeSeconds
    val seconds = (total % SECONDS_PER_MINUTE).toString().padStart(PADDED, '0')
    val minutes = total / SECONDS_PER_MINUTE
    val hours = minutes / MINUTES_PER_HOUR
    if (hours == 0L) return "$minutes:$seconds"
    val within = (minutes % MINUTES_PER_HOUR).toString().padStart(PADDED, '0')
    return "$hours:$within:$seconds"
}
