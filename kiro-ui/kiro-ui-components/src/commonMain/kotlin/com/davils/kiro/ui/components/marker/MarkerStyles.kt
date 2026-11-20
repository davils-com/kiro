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

package com.davils.kiro.ui.components.marker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [MarkerStyle] of a [Marker] from a theme; the value of [MarkerStyleKey] in the component styles of a
 * theme.
 *
 * @since 1.0.0
 */
public fun interface MarkerStyles {
    /**
     * Returns the style of a [Marker] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the marker.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): MarkerStyle

    /**
     * Holds the default styles of a [Marker].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a [Marker], derived only from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: MarkerStyles = MarkerStyles(::defaultMarkerStyle)
    }
}

/**
 * The key of the styles of a [Marker] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public val MarkerStyleKey: StyleKey<MarkerStyles> = StyleKey(name = "marker") { _ -> MarkerStyles.Default }

/**
 * Returns the style of a [Marker] in the theme of the composition, with the overrides of its component styles.
 *
 * @return the style of the marker.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun markerStyle(): MarkerStyle = MarkerStyleKey.current.resolve(theme)

internal fun defaultMarkerStyle(theme: Theme): MarkerStyle = MarkerStyle(
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    color = theme.colors.mutedForeground,
    gap = 8.dp,
    iconSize = 16.dp,
    lineColor = theme.colors.border,
    lineGap = 4.dp,
    borderPadding = 8.dp,
    minHeight = 16.dp,
)
