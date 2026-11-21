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

package com.davils.kiro.ui.audio.card

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the style of an [AudioPlayerCard] from the tokens of a theme.
 *
 * Register an implementation under [AudioPlayerCardStyleKey] in the components of a theme
 * to change how every audio player card looks.
 *
 * @since 1.0.0
 */
public fun interface AudioPlayerCardStyles {
    /**
     * Returns the style of an audio player card for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the audio player card.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AudioPlayerCardStyle

    /**
     * Holds the default [AudioPlayerCardStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an audio player card.
         *
         * @since 1.0.0
         */
        public val Default: AudioPlayerCardStyles = AudioPlayerCardStyles(::defaultAudioPlayerCardStyle)
    }
}

/**
 * The key of the styles of an [AudioPlayerCard] in the components of a theme,
 * with [AudioPlayerCardStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AudioPlayerCardStyleKey: StyleKey<AudioPlayerCardStyles> =
    StyleKey(name = "audio-player-card") { _ -> AudioPlayerCardStyles.Default }

/**
 * Returns the style of an [AudioPlayerCard] in the current theme, with the overrides of its components.
 *
 * @return the style of the audio player card.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun audioPlayerCardStyle(): AudioPlayerCardStyle = AudioPlayerCardStyleKey.current.resolve(Theme.current)

internal fun defaultAudioPlayerCardStyle(theme: Theme): AudioPlayerCardStyle {
    val spacing = theme.spacing
    return AudioPlayerCardStyle(
        width = spacing(steps = 80f),
        padding = spacing(steps = 4f),
        gap = spacing(steps = 3f),
        shape = RoundedCornerShape(theme.radii.xl),
    )
}
