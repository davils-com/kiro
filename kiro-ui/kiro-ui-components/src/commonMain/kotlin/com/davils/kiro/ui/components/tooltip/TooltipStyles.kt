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

package com.davils.kiro.ui.components.tooltip

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import kotlin.time.Duration

/**
 * Derives the [TooltipStyle] from the tokens of a theme.
 *
 * Register an implementation for [TooltipStyleKey] in the components of a theme to change the style of every tooltip.
 *
 * @since 1.0.0
 */
public fun interface TooltipStyles {
    /**
     * Returns the style of a tooltip under a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a tooltip.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): TooltipStyle

    /**
     * Holds the default tooltip styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default tooltip styles, derived from the colors, the radii and the typography of a theme, without delays.
         *
         * @since 1.0.0
         */
        public val Default: TooltipStyles = TooltipStyles(::defaultTooltipStyle)
    }
}

/**
 * The key of the tooltip styles in the components of a theme; its default is [TooltipStyles.Default].
 *
 * @since 1.0.0
 */
public val TooltipStyleKey: StyleKey<TooltipStyles> = StyleKey(name = "tooltip") { _ -> TooltipStyles.Default }

/**
 * Returns the tooltip style of the current theme, with the overrides of the theme for [TooltipStyleKey].
 *
 * @return the style of a tooltip.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun tooltipStyle(): TooltipStyle = TooltipStyleKey.current.resolve(theme)

internal fun defaultTooltipStyle(theme: Theme): TooltipStyle = TooltipStyle(
    container = theme.colors.foreground,
    content = theme.colors.background,
    shape = RoundedCornerShape(theme.radii.md),
    horizontalPadding = 12.dp,
    verticalPadding = 6.dp,
    textStyle = theme.textStyleOf(size = theme.typography.scale.xs),
    maxWidth = 320.dp,
    gap = 6.dp,
    arrowSize = 10.dp,
    arrowRadius = 2.dp,
    openDelay = Duration.ZERO,
    closeDelay = Duration.ZERO,
)
