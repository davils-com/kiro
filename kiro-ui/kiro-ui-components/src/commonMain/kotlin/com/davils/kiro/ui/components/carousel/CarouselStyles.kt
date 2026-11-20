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

package com.davils.kiro.ui.components.carousel

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronLeft
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide

private const val SPACING_STEPS: Int = 4
private const val OFFSET_STEPS: Int = 12

/**
 * Resolves the [CarouselStyle] of a theme.
 *
 * The styles of a theme are found under [CarouselStyleKey]. Override them in [Theme.components] to change every
 * carousel of the theme.
 *
 * @since 1.0.0
 */
public fun interface CarouselStyles {
    /**
     * Returns the style of a carousel for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a carousel.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CarouselStyle

    /**
     * Holds the default [CarouselStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default carousel styles, which derive the style from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: CarouselStyles = CarouselStyles(::defaultCarouselStyle)
    }
}

/**
 * Returns the style of a carousel in the current theme: the [CarouselStyles] under [CarouselStyleKey], changed by the
 * overrides of [Theme.components]. It is the default style of [Carousel].
 *
 * @return the style of a carousel in the current theme.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun carouselStyle(): CarouselStyle = CarouselStyleKey.current.resolve(theme)

internal fun defaultCarouselStyle(theme: Theme): CarouselStyle = CarouselStyle(
    spacing = theme.spacing.unit * SPACING_STEPS,
    buttonOffset = theme.spacing.unit * OFFSET_STEPS,
    buttonShape = CircleShape,
    previousIcon = Lucide.ChevronLeft,
    nextIcon = Lucide.ChevronRight,
    previousLabel = Messages.Carousel.PREVIOUS,
    nextLabel = Messages.Carousel.NEXT,
)

/**
 * The key of the carousel styles in [Theme.components]. Its default is [CarouselStyles.Default].
 *
 * @since 1.0.0
 */
public val CarouselStyleKey: StyleKey<CarouselStyles> = StyleKey(name = "carousel") { _ -> CarouselStyles.Default }
