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

package com.davils.kiro.ui.audio.mini

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val TINT: Float = 0.18f
private const val RING_ALPHA: Float = 0.2f
private const val RING_START: Float = -90f

/**
 * Derives the style of an [AudioPlayerMini] from the tokens of a theme.
 *
 * Register an implementation under [AudioPlayerMiniStyleKey] in the components of a theme
 * to change how every audio player mini looks.
 *
 * @since 1.0.0
 */
public fun interface AudioPlayerMiniStyles {
    /**
     * Returns the style of an audio player mini for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the audio player mini.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AudioPlayerMiniStyle

    /**
     * Holds the default [AudioPlayerMiniStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an audio player mini.
         *
         * @since 1.0.0
         */
        public val Default: AudioPlayerMiniStyles = AudioPlayerMiniStyles(::defaultAudioPlayerMiniStyle)
    }
}

/**
 * The key of the styles of an [AudioPlayerMini] in the components of a theme,
 * with [AudioPlayerMiniStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AudioPlayerMiniStyleKey: StyleKey<AudioPlayerMiniStyles> =
    StyleKey(name = "audio-player-mini") { _ -> AudioPlayerMiniStyles.Default }

/**
 * Returns the style of an [AudioPlayerMini] in the current theme, with the overrides of its components.
 *
 * @return the style of the audio player mini.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun audioPlayerMiniStyle(): AudioPlayerMiniStyle = AudioPlayerMiniStyleKey.current.resolve(Theme.current)

internal fun defaultAudioPlayerMiniStyle(theme: Theme): AudioPlayerMiniStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return AudioPlayerMiniStyle(
        height = spacing(steps = 12f),
        maxWidth = spacing(steps = 70f),
        padding = spacing(steps = 1.5f),
        gap = spacing(steps = 1.5f),
        shape = CircleShape,
        container = colors.card,
        content = colors.cardForeground,
        border = Border(width = theme.borders.thin, color = colors.border),
        tint = TINT,
        coverShape = CircleShape,
        coverElevation = spacing(steps = 0f),
        ringWidth = theme.borders.thick,
        ringGap = spacing(steps = 2f),
        ringAlpha = RING_ALPHA,
        ringStart = RING_START,
        playContainer = colors.foreground,
        playContent = colors.background,
    )
}
