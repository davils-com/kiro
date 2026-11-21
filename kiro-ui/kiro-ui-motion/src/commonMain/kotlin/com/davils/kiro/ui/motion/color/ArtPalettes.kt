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

package com.davils.kiro.ui.motion.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives an [ArtPalette] from the tokens of a theme.
 *
 * Register an implementation under [ArtPaletteKey] in the components of a theme to change the colors of every scene,
 * such as to one of the presets here.
 *
 * @since 1.0.0
 */
public fun interface ArtPalettes {
    /**
     * Returns the palette for a theme.
     *
     * @param theme the theme whose colors the palette follows.
     * @return the palette.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ArtPalette

    /**
     * Holds the palettes Kiro brings along.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * Calm colors that sit quietly behind an interface: a neutral ground, fine lines in the tone of the borders and
         * a muted ramp towards the primary color. The default palette.
         *
         * @since 1.0.0
         */
        public val Refined: ArtPalettes = ArtPalettes(::refinedPalette)

        /**
         * Bold colors of the brand: the full ramp of the chart colors with warm and cool accents of the status colors,
         * for playful scenes.
         *
         * @since 1.0.0
         */
        public val Expressive: ArtPalettes = ArtPalettes(::expressivePalette)

        /**
         * One hue in many shades, derived from the brand color.
         *
         * @since 1.0.0
         */
        public val Monochrome: ArtPalettes = ArtPalettes(::monochromePalette)

        /**
         * Saturated colors around the whole wheel, starting at the brand color.
         *
         * @since 1.0.0
         */
        public val Vivid: ArtPalettes = ArtPalettes(::vividPalette)

        /**
         * Light, soft colors around the whole wheel.
         *
         * @since 1.0.0
         */
        public val Pastel: ArtPalettes = ArtPalettes(::pastelPalette)

        /**
         * Two colors and the shades between them: the brand color and its complement.
         *
         * @since 1.0.0
         */
        public val Duotone: ArtPalettes = ArtPalettes(::duotonePalette)

        /**
         * Glowing colors on a deep night, whatever the theme.
         *
         * @since 1.0.0
         */
        public val Night: ArtPalettes = ArtPalettes(::nightPalette)
    }
}

/**
 * The key of the palette of the scenes in the components of a theme, with [ArtPalettes.Refined] as its default.
 *
 * @since 1.0.0
 */
public val ArtPaletteKey: StyleKey<ArtPalettes> = StyleKey(name = "art-palette") { _ -> ArtPalettes.Refined }

/**
 * Returns the palette of the scenes in the current theme.
 *
 * @return the palette.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun artPalette(): ArtPalette = ArtPaletteKey.current.resolve(Theme.current)

/**
 * Returns the palette of the scenes of a theme, outside of a composition.
 *
 * @param theme the theme.
 * @return the palette.
 * @since 1.0.0
 */
public fun artPaletteOf(theme: Theme): ArtPalette = theme.components.resolve(ArtPaletteKey, theme).resolve(theme)
