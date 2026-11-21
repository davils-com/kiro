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

package com.davils.kiro.ui.audio.bar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.RectangleShape
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val TINT: Float = 0.12f
private const val CENTER_WEIGHT: Float = 1.6f

/**
 * Derives the style of an [AudioPlayerBar] from the tokens of a theme.
 *
 * Register an implementation under [AudioPlayerBarStyleKey] in the components of a theme
 * to change how every audio player bar looks.
 *
 * @since 1.0.0
 */
public fun interface AudioPlayerBarStyles {
    /**
     * Returns the style of an audio player bar for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the audio player bar.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AudioPlayerBarStyle

    /**
     * Holds the default [AudioPlayerBarStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an audio player bar.
         *
         * @since 1.0.0
         */
        public val Default: AudioPlayerBarStyles = AudioPlayerBarStyles(::defaultAudioPlayerBarStyle)
    }
}

/**
 * The key of the styles of an [AudioPlayerBar] in the components of a theme,
 * with [AudioPlayerBarStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AudioPlayerBarStyleKey: StyleKey<AudioPlayerBarStyles> =
    StyleKey(name = "audio-player-bar") { _ -> AudioPlayerBarStyles.Default }

/**
 * Returns the style of an [AudioPlayerBar] in the current theme, with the overrides of its components.
 *
 * @return the style of the audio player bar.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun audioPlayerBarStyle(): AudioPlayerBarStyle = AudioPlayerBarStyleKey.current.resolve(Theme.current)

internal fun defaultAudioPlayerBarStyle(theme: Theme): AudioPlayerBarStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return AudioPlayerBarStyle(
        height = spacing(steps = 21f),
        padding = spacing(steps = 4f),
        gap = spacing(steps = 3f),
        shape = RectangleShape,
        container = colors.card,
        content = colors.cardForeground,
        border = Border(width = theme.borders.thin, color = colors.border),
        tint = TINT,
        centerWeight = CENTER_WEIGHT,
        coverSize = spacing(steps = 13f),
        coverRadius = theme.radii.md,
        coverElevation = spacing(steps = 2f),
    )
}
