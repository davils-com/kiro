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

package com.davils.kiro.ui.motion.shader.silk

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.shader.ShaderBackend

private const val INTENSITY: Float = 0.32f
private const val INTENSITY_DARK: Float = 0.16f
private const val SHADOW_ALPHA: Float = 0.22f
private const val SHEEN_ALPHA: Float = 0.25f
private const val SCALE: Float = 0.045f
private const val WARP: Float = 1.2f
private const val RELIEF: Float = 0.9f
private const val SHINE: Float = 24f
private const val ANGLE: Float = -24f
private const val PERIOD: Int = 120
private const val POINTER_LIGHT: Float = 0.35f
private const val DITHER: Float = 1f

/**
 * Derives the style of a [SilkArt] from the tokens of a theme.
 *
 * Register an implementation under [SilkArtStyleKey] in the components of a theme to change how every silk art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface SilkArtStyles {
    /**
     * Returns the style of a silk art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the silk art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SilkArtStyle

    /**
     * Holds the default [SilkArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a silk art.
         *
         * @since 1.0.0
         */
        public val Default: SilkArtStyles = SilkArtStyles(::defaultSilkArtStyle)
    }
}

/**
 * The key of the styles of a [SilkArt] in the components of a theme, with [SilkArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val SilkArtStyleKey: StyleKey<SilkArtStyles> =
    StyleKey(name = "silk-art") { _ -> SilkArtStyles.Default }

/**
 * Returns the style of a [SilkArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the silk art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun silkArtStyle(): SilkArtStyle = SilkArtStyleKey.current.resolve(Theme.current)

internal fun defaultSilkArtStyle(theme: Theme): SilkArtStyle = SilkArtStyle(
    background = null,
    color = null,
    shadow = null,
    sheen = null,
    intensity = intensityOf(theme),
    shadowAlpha = SHADOW_ALPHA,
    sheenAlpha = SHEEN_ALPHA,
    scale = SCALE,
    warp = WARP,
    relief = RELIEF,
    shine = SHINE,
    angle = ANGLE,
    period = theme.motion.durations.long * PERIOD,
    pointerLight = POINTER_LIGHT,
    dither = DITHER,
    backend = ShaderBackend.Auto,
)

private fun intensityOf(theme: Theme): Float {
    if (theme.isDark) return INTENSITY_DARK
    return INTENSITY
}
