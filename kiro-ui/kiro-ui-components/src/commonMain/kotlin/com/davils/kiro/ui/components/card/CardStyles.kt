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

package com.davils.kiro.ui.components.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [CardStyle] of a theme and a size.
 *
 * The styles of a theme are found under [CardStyleKey]. Override them in [Theme.components] to change every card of the
 * theme.
 *
 * @since 1.0.0
 */
public fun interface CardStyles {
    /**
     * Returns the style of a card for a theme and a size.
     *
     * @param theme the theme whose tokens the style follows.
     * @param size the size of the card.
     * @return the style of a card of that size.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, size: CardSize): CardStyle

    /**
     * Holds the default [CardStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default card styles, which derive the style of each size of Kiro from the tokens of the theme alone.
         *
         * @since 1.0.0
         */
        public val Default: CardStyles = CardStyles(::defaultCardStyle)
    }
}

/**
 * Returns the style of a card in the current theme: the [CardStyles] under [CardStyleKey], changed by the overrides of
 * [Theme.components]. It is the default style of [Card].
 *
 * @param size the size of the card.
 * @return the style of a card of that size.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun cardStyle(size: CardSize): CardStyle = CardStyleKey.current.resolve(theme, size)

/**
 * The key of the card styles in [Theme.components]. Its default is [CardStyles.Default].
 *
 * @since 1.0.0
 */
public val CardStyleKey: StyleKey<CardStyles> = StyleKey(name = "card") { _ -> CardStyles.Default }
