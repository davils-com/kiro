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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val BEAMS: Int = 9
private const val SPREAD: Float = 0.7f
private const val BEND: Float = 0.3f
private const val GUIDE_ALPHA_LIGHT: Float = 0.5f
private const val GUIDE_ALPHA_DARK: Float = 0.45f
private const val BEAM_ALPHA_LIGHT: Float = 0.85f
private const val BEAM_ALPHA_DARK: Float = 1f
private const val GLOW_ALPHA_LIGHT: Float = 0.12f
private const val GLOW_ALPHA_DARK: Float = 0.2f
private const val SEGMENT: Float = 0.18f
private const val PERIOD: Int = 24
private const val PERIOD_SPREAD: Float = 0.4f

/**
 * Derives the style of a [BeamsArt] from the tokens of a theme.
 *
 * Register an implementation under [BeamsArtStyleKey] in the components of a theme to change how every
 * [BeamsArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface BeamsArtStyles {
    /**
     * Returns the style of a [BeamsArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): BeamsArtStyle

    /**
     * Holds the default [BeamsArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [BeamsArt].
         *
         * @since 1.0.0
         */
        public val Default: BeamsArtStyles = BeamsArtStyles(::defaultBeamsArtStyle)
    }
}

/**
 * The key of the styles of a [BeamsArt] in the components of a theme, with
 * [BeamsArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val BeamsArtStyleKey: StyleKey<BeamsArtStyles> =
    StyleKey(name = "beams-art") { _ -> BeamsArtStyles.Default }

/**
 * Returns the style of a [BeamsArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun beamsArtStyle(): BeamsArtStyle = BeamsArtStyleKey.current.resolve(Theme.current)

internal fun defaultBeamsArtStyle(theme: Theme): BeamsArtStyle {
    val spacing = theme.spacing
    return BeamsArtStyle(
        beams = BEAMS,
        spread = SPREAD,
        bend = BEND,
        lineWidth = theme.borders.thin,
        guideAlpha = theme.darkAware(light = GUIDE_ALPHA_LIGHT, dark = GUIDE_ALPHA_DARK),
        beamWidth = theme.borders.thick,
        beamAlpha = theme.darkAware(light = BEAM_ALPHA_LIGHT, dark = BEAM_ALPHA_DARK),
        glowWidth = spacing(steps = 2f),
        glowAlpha = theme.darkAware(light = GLOW_ALPHA_LIGHT, dark = GLOW_ALPHA_DARK),
        segment = SEGMENT,
        period = theme.motion.durations.long * PERIOD,
        periodSpread = PERIOD_SPREAD,
        salt = 0,
        colors = null,
        guide = null,
        background = null,
    )
}
