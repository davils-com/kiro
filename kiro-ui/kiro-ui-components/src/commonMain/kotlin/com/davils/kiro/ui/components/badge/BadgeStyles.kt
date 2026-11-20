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

package com.davils.kiro.ui.components.badge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the style of a [Badge] for a theme and a variant.
 *
 * Replace it in the components of a theme under [BadgeStyleKey] to change the style of every badge.
 *
 * @since 1.0.0
 */
public fun interface BadgeStyles {
    /**
     * Returns the style of a [Badge] of a variant in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the badge.
     * @return the style of the badge.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: BadgeVariant): BadgeStyle

    /**
     * Holds the default [BadgeStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a [Badge], derived from the tokens of the theme for each [BadgeVariant].
         *
         * @since 1.0.0
         */
        public val Default: BadgeStyles = BadgeStyles(::defaultBadgeStyle)
    }
}

/**
 * Returns the style of a [Badge] of a variant in the theme of the composition, resolved by the [BadgeStyles] under
 * [BadgeStyleKey].
 *
 * @param variant the variant of the badge.
 * @return the style of the badge.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun badgeStyle(variant: BadgeVariant): BadgeStyle = BadgeStyleKey.current.resolve(theme, variant)

/**
 * The key of the [BadgeStyles] of a [Badge] in the components of a theme; its default is [BadgeStyles.Default].
 *
 * @since 1.0.0
 */
public val BadgeStyleKey: StyleKey<BadgeStyles> = StyleKey(name = "badge") { _ -> BadgeStyles.Default }
