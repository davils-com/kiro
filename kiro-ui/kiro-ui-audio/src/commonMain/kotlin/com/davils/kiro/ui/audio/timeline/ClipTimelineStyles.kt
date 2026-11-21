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
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val ZOOM_STEP: Float = 1.15f

/**
 * Derives the style of a [ClipTimeline] from the tokens of a theme.
 *
 * Register an implementation under [ClipTimelineStyleKey] in the components of a theme to change how every clip
 * timeline looks.
 *
 * @since 1.0.0
 */
public fun interface ClipTimelineStyles {
    /**
     * Returns the style of a clip timeline for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the clip timeline.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ClipTimelineStyle

    /**
     * Holds the default [ClipTimelineStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a clip timeline.
         *
         * @since 1.0.0
         */
        public val Default: ClipTimelineStyles = ClipTimelineStyles(::defaultClipTimelineStyle)
    }
}

/**
 * The key of the styles of a [ClipTimeline] in the components of a theme, with [ClipTimelineStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val ClipTimelineStyleKey: StyleKey<ClipTimelineStyles> =
    StyleKey(name = "clip-timeline") { _ -> ClipTimelineStyles.Default }

/**
 * Returns the style of a [ClipTimeline] in the current theme, with the overrides of its components.
 *
 * @return the style of the clip timeline.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun clipTimelineStyle(): ClipTimelineStyle = ClipTimelineStyleKey.current.resolve(Theme.current)

internal fun defaultClipTimelineStyle(theme: Theme): ClipTimelineStyle {
    val colors = theme.colors
    return ClipTimelineStyle(
        background = colors.background,
        border = colors.border,
        borderWidth = theme.borders.thin,
        radius = theme.radii.md,
        playheadColor = colors.destructive,
        playheadWidth = theme.borders.thick,
        scrollStep = theme.spacing(steps = 10f),
        zoomStep = ZOOM_STEP,
        description = Messages.TIMELINE,
    )
}
