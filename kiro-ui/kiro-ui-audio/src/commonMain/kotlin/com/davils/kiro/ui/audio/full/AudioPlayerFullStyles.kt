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

package com.davils.kiro.ui.audio.full

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val ACTIVE_ALPHA: Float = 0.16f

/**
 * Derives the style of an [AudioPlayerFull] from the tokens of a theme.
 *
 * Register an implementation under [AudioPlayerFullStyleKey] in the components of a theme
 * to change how every audio player full view looks.
 *
 * @since 1.0.0
 */
public fun interface AudioPlayerFullStyles {
    /**
     * Returns the style of an audio player full view for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the audio player full view.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): AudioPlayerFullStyle

    /**
     * Holds the default [AudioPlayerFullStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of an audio player full view.
         *
         * @since 1.0.0
         */
        public val Default: AudioPlayerFullStyles = AudioPlayerFullStyles(::defaultAudioPlayerFullStyle)
    }
}

/**
 * The key of the styles of an [AudioPlayerFull] in the components of a theme,
 * with [AudioPlayerFullStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AudioPlayerFullStyleKey: StyleKey<AudioPlayerFullStyles> =
    StyleKey(name = "audio-player-full") { _ -> AudioPlayerFullStyles.Default }

/**
 * Returns the style of an [AudioPlayerFull] in the current theme, with the overrides of its components.
 *
 * @return the style of the audio player full view.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun audioPlayerFullStyle(): AudioPlayerFullStyle = AudioPlayerFullStyleKey.current.resolve(Theme.current)

internal fun defaultAudioPlayerFullStyle(theme: Theme): AudioPlayerFullStyle {
    val spacing = theme.spacing
    return AudioPlayerFullStyle(
        padding = spacing(steps = 8f),
        gap = spacing(steps = 6f),
        controlsGap = spacing(steps = 3f),
        coverSize = spacing(steps = 80f),
        queueWidth = spacing(steps = 70f),
        queueGap = spacing(steps = 0.75f),
        queueRowPadding = spacing(steps = 1.5f),
        queueRowGap = spacing(steps = 3f),
        queueRowShape = RoundedCornerShape(theme.radii.md),
        queueCoverSize = spacing(steps = 10f),
        queueCoverElevation = spacing(steps = 1.5f),
        activeAlpha = ACTIVE_ALPHA,
    )
}
