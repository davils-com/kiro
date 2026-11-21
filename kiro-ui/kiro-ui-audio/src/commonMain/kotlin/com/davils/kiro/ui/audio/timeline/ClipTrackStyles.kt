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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the style of a [ClipTrack] from the tokens of a theme.
 *
 * Register an implementation under [ClipTrackStyleKey] in the components of a theme to change how every clip track
 * looks.
 *
 * @since 1.0.0
 */
public fun interface ClipTrackStyles {
    /**
     * Returns the style of a clip track for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the clip track.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ClipTrackStyle

    /**
     * Holds the default [ClipTrackStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a clip track.
         *
         * @since 1.0.0
         */
        public val Default: ClipTrackStyles = ClipTrackStyles(::defaultClipTrackStyle)
    }
}

/**
 * The key of the styles of a [ClipTrack] in the components of a theme, with [ClipTrackStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val ClipTrackStyleKey: StyleKey<ClipTrackStyles> =
    StyleKey(name = "clip-track") { _ -> ClipTrackStyles.Default }

/**
 * Returns the style of a [ClipTrack] in the current theme, with the overrides of its components.
 *
 * @return the style of the clip track.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun clipTrackStyle(): ClipTrackStyle = ClipTrackStyleKey.current.resolve(Theme.current)

internal fun defaultClipTrackStyle(theme: Theme): ClipTrackStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val scale = theme.typography.scale
    val sans = theme.typography.families.sans
    return ClipTrackStyle(
        height = spacing(steps = 18f),
        headerWidth = spacing(steps = 36f),
        headerPadding = spacing(steps = 2f),
        headerGap = spacing(steps = 1f),
        headerColor = colors.card,
        laneColor = colors.background,
        separatorColor = colors.border,
        separatorWidth = theme.borders.thin,
        clipInset = spacing(steps = 1f),
        nameStyle = TextStyle(fontFamily = sans, fontSize = scale.sm.fontSize, fontWeight = FontWeight.Medium),
        nameColor = colors.cardForeground,
        toggleSize = spacing(steps = 5f),
        toggleRadius = theme.radii.sm,
        toggleStyle = TextStyle(fontFamily = sans, fontSize = scale.xs.fontSize, fontWeight = FontWeight.Bold),
        toggleIdle = colors.muted,
        toggleIdleContent = colors.mutedForeground,
        toggleActiveContent = colors.background,
        muteColor = colors.status.info.foreground,
        soloColor = colors.status.warning.foreground,
        armColor = colors.destructive,
        mute = Messages.MUTE_LETTER,
        solo = Messages.SOLO_LETTER,
        arm = Messages.ARM_LETTER,
        muteLabel = Messages.MUTE_TRACK,
        soloLabel = Messages.SOLO_TRACK,
        armLabel = Messages.ARM_TRACK,
    )
}
