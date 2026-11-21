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

package com.davils.kiro.ui.motion.shader.grain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.shader.ShaderBackend

private const val CELL: Float = 0.25f
private const val AMOUNT: Float = 0.08f
private const val BALANCE: Float = 0.5f
private const val REFRESH: Double = 0.55
private const val DOTS: Int = 6000

/**
 * Derives the style of a [GrainArt] from the tokens of a theme.
 *
 * Register an implementation under [GrainArtStyleKey] in the components of a theme to change how every grain art
 * looks.
 *
 * @since 1.0.0
 */
public fun interface GrainArtStyles {
    /**
     * Returns the style of a grain art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the grain art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): GrainArtStyle

    /**
     * Holds the default [GrainArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a grain art.
         *
         * @since 1.0.0
         */
        public val Default: GrainArtStyles = GrainArtStyles(::defaultGrainArtStyle)
    }
}

/**
 * The key of the styles of a [GrainArt] in the components of a theme, with [GrainArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val GrainArtStyleKey: StyleKey<GrainArtStyles> =
    StyleKey(name = "grain-art") { _ -> GrainArtStyles.Default }

/**
 * Returns the style of a [GrainArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the grain art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun grainArtStyle(): GrainArtStyle = GrainArtStyleKey.current.resolve(Theme.current)

internal fun defaultGrainArtStyle(theme: Theme): GrainArtStyle = GrainArtStyle(
    shade = null,
    tone = null,
    cell = theme.spacing(steps = CELL),
    amount = AMOUNT,
    balance = BALANCE,
    refresh = theme.motion.durations.short * REFRESH,
    dots = DOTS,
    backend = ShaderBackend.Auto,
)
