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

package com.davils.kiro.ui.components.navigationmenu

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.overlay.popoverPanel
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronDown
import com.davils.kiro.ui.icons.lucide.Lucide
import kotlin.time.Duration.Companion.milliseconds

private const val HALF: Float = 0.5f
private const val DELAY_MILLIS: Int = 50

/**
 * Resolves the style of a [NavigationMenu] for a theme.
 *
 * Replace it in the components of a theme under [NavigationMenuStyleKey] to change the style of every navigation menu.
 *
 * @since 1.0.0
 */
public fun interface NavigationMenuStyles {
    /**
     * Returns the style of a [NavigationMenu] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the navigation menu.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): NavigationMenuStyle

    /**
     * Holds the default [NavigationMenuStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [NavigationMenu], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: NavigationMenuStyles = NavigationMenuStyles(::defaultNavigationMenuStyle)
    }
}

/**
 * The key of the [NavigationMenuStyles] of a [NavigationMenu] in the components of a theme; its default is
 * [NavigationMenuStyles.Default].
 *
 * @since 1.0.0
 */
public val NavigationMenuStyleKey: StyleKey<NavigationMenuStyles> = StyleKey(
    name = "navigation-menu",
) { _ -> NavigationMenuStyles.Default }

/**
 * Returns the style of a [NavigationMenu] in the theme of the composition, resolved by the [NavigationMenuStyles] under
 * [NavigationMenuStyleKey].
 *
 * @return the style of the navigation menu.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun navigationMenuStyle(): NavigationMenuStyle = NavigationMenuStyleKey.current.resolve(theme)

internal fun defaultNavigationMenuStyle(theme: Theme): NavigationMenuStyle {
    val sm = theme.typography.scale.sm
    return NavigationMenuStyle(
        triggerHeight = 36.dp,
        triggerShape = RoundedCornerShape(theme.radii.lg),
        triggerHorizontalPadding = 10.dp,
        triggerTextStyle = theme.textStyleOf(size = sm, weight = FontWeight.Medium),
        hover = theme.colors.muted,
        open = theme.colors.muted.copy(alpha = HALF),
        chevron = Lucide.ChevronDown,
        chevronSize = 12.dp,
        chevronGap = 4.dp,
        panel = theme.popoverPanel(padding = 4.dp).copy(shadow = theme.shadows.sm),
        sideOffset = 4.dp,
        linkShape = RoundedCornerShape(theme.radii.md),
        linkPadding = 8.dp,
        linkGap = 8.dp,
        linkTextStyle = theme.textStyleOf(size = sm),
        openDelay = DELAY_MILLIS.milliseconds,
        closeDelay = DELAY_MILLIS.milliseconds,
    )
}
