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

package com.davils.kiro.ui.window.splash.centered

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.art.geometry.OrbitArt
import com.davils.kiro.ui.motion.art.geometry.OrbitArtStyleKey
import com.davils.kiro.ui.motion.scene.Scene
import com.davils.kiro.ui.motion.scene.scene
import com.davils.kiro.ui.window.splash.SplashMessages
import com.davils.kiro.ui.window.splash.defaultSplashFailureStyle
import com.davils.kiro.ui.window.textStyleOf

private const val CENTER: Float = 0.5f
private const val VEIL_ALPHA: Float = 0.92f
private const val VEIL_HOLD: Float = 0.55f
private const val RING_START: Float = -90f
private const val RING_SWEEP: Float = 90f
private const val RING_PERIOD: Int = 4
private const val RING_FOLLOW: Int = 2

/**
 * Derives the style of a [CenteredSplash] from the tokens of a theme.
 *
 * Register an implementation under [CenteredSplashStyleKey] in the components of a theme to change how every
 * centered splash looks.
 *
 * @since 1.0.0
 */
public fun interface CenteredSplashStyles {
    /**
     * Returns the style of a centered splash for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the centered splash.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CenteredSplashStyle

    /**
     * Holds the default [CenteredSplashStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a centered splash.
         *
         * @since 1.0.0
         */
        public val Default: CenteredSplashStyles = CenteredSplashStyles(::defaultCenteredSplashStyle)
    }
}

/**
 * The key of the styles of a [CenteredSplash] in the components of a theme, with [CenteredSplashStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val CenteredSplashStyleKey: StyleKey<CenteredSplashStyles> =
    StyleKey(name = "centered-splash") { _ -> CenteredSplashStyles.Default }

/**
 * Returns the style of a [CenteredSplash] in the current theme, with the overrides of its components.
 *
 * @return the style of the centered splash.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun centeredSplashStyle(): CenteredSplashStyle = CenteredSplashStyleKey.current.resolve(Theme.current)

internal fun defaultCenteredSplashStyle(theme: Theme): CenteredSplashStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val typography = theme.typography
    val scale = typography.scale
    val heading = typography.families.heading
    val durations = theme.motion.durations
    val centered = TextStyle(textAlign = TextAlign.Center)
    val failure = defaultSplashFailureStyle(theme)
    val name = theme.textStyleOf(size = scale.xl2, weight = FontWeight.SemiBold, family = heading)
    return CenteredSplashStyle(
        size = DpSize(width = spacing(steps = 130f), height = spacing(steps = 110f)),
        scene = theme.orbitsAroundTheCenter(),
        content = colors.foreground,
        muted = colors.mutedForeground,
        veil = colors.background.copy(alpha = VEIL_ALPHA),
        veilSpread = spacing(steps = 10f),
        veilHold = VEIL_HOLD,
        padding = spacing(steps = 6f),
        gap = spacing(steps = 5f),
        titleGap = spacing(steps = 1f),
        footerGap = spacing(steps = 3f),
        logoSize = spacing(steps = 12f),
        ringGap = spacing(steps = 2f),
        ringWidth = theme.borders.thick,
        ringTrack = colors.border,
        ringColor = colors.primary,
        ringCap = StrokeCap.Round,
        ringStart = RING_START,
        ringSweep = RING_SWEEP,
        ringPeriod = durations.long * RING_PERIOD,
        ringDuration = durations.long * RING_FOLLOW,
        ringEasing = theme.motion.easings.emphasized,
        nameStyle = name.copy(letterSpacing = typography.tracking.tight),
        subtitleStyle = theme.textStyleOf(size = scale.sm).merge(centered),
        stepStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium),
        footerStyle = theme.textStyleOf(size = scale.xs),
        failure = failure.copy(textStyle = failure.textStyle.merge(centered)),
        labels = SplashMessages.labels,
    )
}

private fun Theme.orbitsAroundTheCenter(): Scene {
    val ground = colors.background
    val orbits = components.resolve(key = OrbitArtStyleKey, theme = this)
    val style = orbits.resolve(this).copy(centerX = CENTER, centerY = CENTER, background = ground)
    return scene {
        background = ground
        layer(OrbitArt(style = style))
    }
}
