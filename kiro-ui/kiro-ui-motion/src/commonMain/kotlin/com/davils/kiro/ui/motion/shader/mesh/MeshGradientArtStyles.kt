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

package com.davils.kiro.ui.motion.shader.mesh

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.shader.ShaderBackend

private const val ANCHORS: Int = 5
private const val SPREAD: Float = 0.42f
private const val RADIUS: Float = 0.3f
private const val SOFTNESS: Float = 1.8f
private const val INTENSITY: Float = 0.3f
private const val INTENSITY_DARK: Float = 0.16f
private const val DRIFT: Float = 0.14f
private const val PERIOD: Int = 100
private const val POINTER_PULL: Float = 0.18f
private const val DITHER: Float = 1f

/**
 * Derives the style of a [MeshGradientArt] from the tokens of a theme.
 *
 * Register an implementation under [MeshGradientArtStyleKey] in the components of a theme to change how every mesh
 * gradient art looks.
 *
 * @since 1.0.0
 */
public fun interface MeshGradientArtStyles {
    /**
     * Returns the style of a mesh gradient art for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the mesh gradient art.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MeshGradientArtStyle

    /**
     * Holds the default [MeshGradientArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a mesh gradient art.
         *
         * @since 1.0.0
         */
        public val Default: MeshGradientArtStyles = MeshGradientArtStyles(::defaultMeshGradientArtStyle)
    }
}

/**
 * The key of the styles of a [MeshGradientArt] in the components of a theme, with [MeshGradientArtStyles.Default] as
 * its default.
 *
 * @since 1.0.0
 */
public val MeshGradientArtStyleKey: StyleKey<MeshGradientArtStyles> =
    StyleKey(name = "mesh-gradient-art") { _ -> MeshGradientArtStyles.Default }

/**
 * Returns the style of a [MeshGradientArt] in the current theme, with the overrides of its components.
 *
 * @return the style of the mesh gradient art.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun meshGradientArtStyle(): MeshGradientArtStyle = MeshGradientArtStyleKey.current.resolve(Theme.current)

internal fun defaultMeshGradientArtStyle(theme: Theme): MeshGradientArtStyle = MeshGradientArtStyle(
    colors = null,
    background = null,
    anchors = ANCHORS,
    spread = SPREAD,
    radius = RADIUS,
    softness = SOFTNESS,
    intensity = intensityOf(theme),
    drift = DRIFT,
    period = theme.motion.durations.long * PERIOD,
    pointerPull = POINTER_PULL,
    dither = DITHER,
    backend = ShaderBackend.Auto,
)

private fun intensityOf(theme: Theme): Float {
    if (theme.isDark) return INTENSITY_DARK
    return INTENSITY
}
