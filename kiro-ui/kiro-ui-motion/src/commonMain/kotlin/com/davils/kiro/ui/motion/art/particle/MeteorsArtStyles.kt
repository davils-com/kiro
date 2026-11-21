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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware

private const val COUNT: Int = 6
private const val ANGLE: Float = 32f
private const val PERIOD: Int = 30
private const val DUTY: Float = 0.4f
private const val TRAVEL: Float = 0.7f
private const val ALPHA_LIGHT: Float = 0.75f
private const val ALPHA_DARK: Float = 0.9f
private const val TONE_FROM: Float = 0.6f
private const val STARS: Int = 90
private const val STAR_ALPHA_LIGHT: Float = 0.6f
private const val STAR_ALPHA_DARK: Float = 0.7f

/**
 * Derives the style of a [MeteorsArt] from the tokens of a theme.
 *
 * Register an implementation under [MeteorsArtStyleKey] in the components of a theme to change how every meteors art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface MeteorsArtStyles {
    /**
     * Returns the style of a meteors art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the meteors art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MeteorsArtStyle

    /**
     * Holds the default [MeteorsArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a meteors art.
         *
         * @since 1.0.0
         */
        public val Default: MeteorsArtStyles = MeteorsArtStyles(::defaultMeteorsArtStyle)
    }
}

/**
 * The key of the styles of a [MeteorsArt] in the components of a theme, with [MeteorsArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val MeteorsArtStyleKey: StyleKey<MeteorsArtStyles> =
    StyleKey(name = "meteors-art") { _ -> MeteorsArtStyles.Default }

/**
 * Returns the style of a [MeteorsArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the meteors art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun meteorsArtStyle(): MeteorsArtStyle = MeteorsArtStyleKey.current.resolve(Theme.current)

internal fun defaultMeteorsArtStyle(theme: Theme): MeteorsArtStyle {
    val spacing = theme.spacing
    return MeteorsArtStyle(
        count = COUNT,
        angle = ANGLE,
        period = theme.motion.durations.long * PERIOD,
        duty = DUTY,
        travel = TRAVEL,
        tail = spacing(steps = 36f),
        width = spacing(steps = 0.375f),
        headRadius = spacing(steps = 0.5f),
        alpha = theme.darkAware(light = ALPHA_LIGHT, dark = ALPHA_DARK),
        toneFrom = TONE_FROM,
        stars = STARS,
        starRadius = spacing(steps = 0.25f),
        starAlpha = theme.darkAware(light = STAR_ALPHA_LIGHT, dark = STAR_ALPHA_DARK),
        colors = null,
        star = null,
    )
}
