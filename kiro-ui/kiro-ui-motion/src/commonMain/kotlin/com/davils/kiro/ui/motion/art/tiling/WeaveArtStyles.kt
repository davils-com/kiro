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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware
import com.davils.kiro.ui.motion.quality.QualityLevel

private const val FRAME_ALPHA_LIGHT: Float = 0.34f
private const val FRAME_ALPHA_DARK: Float = 0.3f
private const val LINE_ALPHA_LIGHT: Float = 0.2f
private const val LINE_ALPHA_DARK: Float = 0.2f
private const val PANEL_ALPHA_LIGHT: Float = 0.07f
private const val PANEL_ALPHA_DARK: Float = 0.1f
private const val HIGHLIGHT_ALPHA_LIGHT: Float = 0.6f
private const val HIGHLIGHT_ALPHA_DARK: Float = 0.75f
private const val GLOW_ALPHA_LIGHT: Float = 0.1f
private const val GLOW_ALPHA_DARK: Float = 0.16f
private const val PANEL_SHARE: Float = 0.05f
private const val BANDS: Int = 2
private const val ANGLE: Float = 30f
private const val ARC_STEPS: Int = 8
private const val PERIOD: Int = 36

/**
 * Derives the style of a [WeaveArt] from the tokens of a theme.
 *
 * Register an implementation under [WeaveArtStyleKey] in the components of a theme to change how every
 * [WeaveArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface WeaveArtStyles {
    /**
     * Returns the style of a [WeaveArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): WeaveArtStyle

    /**
     * Holds the default [WeaveArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [WeaveArt].
         *
         * @since 1.0.0
         */
        public val Default: WeaveArtStyles = WeaveArtStyles(::defaultWeaveArtStyle)
    }
}

/**
 * The key of the styles of a [WeaveArt] in the components of a theme, with
 * [WeaveArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val WeaveArtStyleKey: StyleKey<WeaveArtStyles> =
    StyleKey(name = "weave-art") { _ -> WeaveArtStyles.Default }

/**
 * Returns the style of a [WeaveArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun weaveArtStyle(): WeaveArtStyle = WeaveArtStyleKey.current.resolve(Theme.current)

internal fun defaultWeaveArtStyle(theme: Theme): WeaveArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    return WeaveArtStyle(
        pattern = WeavePattern.Asanoha,
        cell = spacing(steps = 10f),
        frameWidth = theme.borders.thin,
        frameAlpha = theme.darkAware(light = FRAME_ALPHA_LIGHT, dark = FRAME_ALPHA_DARK),
        lineWidth = theme.borders.thin,
        lineAlpha = theme.darkAware(light = LINE_ALPHA_LIGHT, dark = LINE_ALPHA_DARK),
        panelShare = PANEL_SHARE,
        panelAlpha = theme.darkAware(light = PANEL_ALPHA_LIGHT, dark = PANEL_ALPHA_DARK),
        arcSteps = ARC_STEPS,
        band = spacing(steps = 28f),
        bands = BANDS,
        angle = ANGLE,
        highlightWidth = theme.borders.thin,
        highlightAlpha = theme.darkAware(light = HIGHLIGHT_ALPHA_LIGHT, dark = HIGHLIGHT_ALPHA_DARK),
        glowWidth = spacing(steps = 1f),
        glowAlpha = theme.darkAware(light = GLOW_ALPHA_LIGHT, dark = GLOW_ALPHA_DARK),
        glowQuality = QualityLevel.Balanced,
        period = long * PERIOD,
        easing = theme.motion.easings.ease,
        salt = 0,
        colors = null,
        line = null,
        accent = null,
        background = null,
    )
}
