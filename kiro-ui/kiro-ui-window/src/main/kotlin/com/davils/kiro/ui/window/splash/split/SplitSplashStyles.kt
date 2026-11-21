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

package com.davils.kiro.ui.window.splash.split

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.scene.ScenePresets
import com.davils.kiro.ui.window.splash.SplashMessages
import com.davils.kiro.ui.window.splash.defaultSplashFailureStyle
import com.davils.kiro.ui.window.splash.spinnerOf
import com.davils.kiro.ui.window.textStyleOf

private const val VENDOR_ALPHA: Float = 0.5f
private const val VENDOR_SATURATION: Float = 0f
private const val VENDOR_ROTATION: Float = 90f

/**
 * Derives the style of a [SplitSplash] from the tokens of a theme.
 *
 * Register an implementation under [SplitSplashStyleKey] in the components of a theme to change how every split
 * splash looks.
 *
 * @since 1.0.0
 */
public fun interface SplitSplashStyles {
    /**
     * Returns the style of a split splash for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the split splash.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SplitSplashStyle

    /**
     * Holds the default [SplitSplashStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a split splash.
         *
         * @since 1.0.0
         */
        public val Default: SplitSplashStyles = SplitSplashStyles(::defaultSplitSplashStyle)
    }
}

/**
 * The key of the styles of a [SplitSplash] in the components of a theme, with [SplitSplashStyles.Default] as its
 * default.
 *
 * @since 1.0.0
 */
public val SplitSplashStyleKey: StyleKey<SplitSplashStyles> =
    StyleKey(name = "split-splash") { _ -> SplitSplashStyles.Default }

/**
 * Returns the style of a [SplitSplash] in the current theme, with the overrides of its components.
 *
 * @return the style of the split splash.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun splitSplashStyle(): SplitSplashStyle = SplitSplashStyleKey.current.resolve(Theme.current)

internal fun defaultSplitSplashStyle(theme: Theme): SplitSplashStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val typography = theme.typography
    val scale = typography.scale
    val families = typography.families
    val heading = theme.textStyleOf(size = scale.xl3, weight = FontWeight.SemiBold, family = families.heading)
    val vendor = theme.textStyleOf(size = scale.sm, weight = FontWeight.SemiBold, family = families.heading)
    return SplitSplashStyle(
        size = DpSize(width = spacing(steps = 220f), height = spacing(steps = 130f)),
        panel = colors.background,
        divider = Border(width = theme.borders.thin, color = colors.border),
        scene = ScenePresets.Skyline,
        content = colors.foreground,
        muted = colors.mutedForeground,
        padding = spacing(steps = 10f),
        gap = spacing(steps = 6f),
        headerGap = spacing(steps = 4f),
        titleGap = spacing(steps = 2f),
        stepGap = spacing(steps = 1.5f),
        indicatorGap = spacing(steps = 2f),
        logoSize = spacing(steps = 14f),
        nameStyle = heading.copy(letterSpacing = typography.tracking.tight),
        editionStyle = heading.copy(letterSpacing = typography.tracking.tight),
        subtitleStyle = theme.textStyleOf(size = scale.base),
        stepStyle = theme.textStyleOf(size = scale.sm, weight = FontWeight.Medium),
        versionStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium, family = families.mono),
        footerStyle = theme.textStyleOf(size = scale.xs),
        vendorStyle = vendor.copy(letterSpacing = typography.tracking.widest),
        vendorColor = colors.mutedForeground,
        vendorAlpha = VENDOR_ALPHA,
        vendorSaturation = VENDOR_SATURATION,
        vendorRotation = VENDOR_ROTATION,
        vendorGap = spacing(steps = 3f),
        vendorLogoSize = spacing(steps = 7f),
        spinner = theme.spinnerOf(steps = 4f),
        failure = defaultSplashFailureStyle(theme),
        labels = SplashMessages.labels,
    )
}
