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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.motion.millisOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val THUMB_HOVER_ALPHA: Float = 0.5f

/**
 * Derives the style of the scrollbars of a [ScrollArea] and a [LazyScrollArea] from the tokens of a theme.
 *
 * Register an implementation under [ScrollAreaStyleKey] in the components of a theme to change how every scroll area
 * looks.
 *
 * @since 1.0.0
 */
public fun interface ScrollAreaStyles {
    /**
     * Returns the style of a scroll area for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the scroll area.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ScrollAreaStyle

    /**
     * Holds the default [ScrollAreaStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a scroll area, with a round thumb in the border color of the theme.
         *
         * @since 1.0.0
         */
        public val Default: ScrollAreaStyles = ScrollAreaStyles(::defaultScrollAreaStyle)
    }
}

/**
 * The key of the styles of a [ScrollArea] and a [LazyScrollArea] in the components of a theme, with
 * [ScrollAreaStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val ScrollAreaStyleKey: StyleKey<ScrollAreaStyles> = StyleKey(
    name = "scroll-area",
) { _ -> ScrollAreaStyles.Default }

/**
 * Returns the style of a [ScrollArea] or a [LazyScrollArea] in the current theme, with the overrides of its
 * components.
 *
 * @return the style of the scroll area.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun scrollAreaStyle(): ScrollAreaStyle = ScrollAreaStyleKey.current.resolve(theme)

internal fun defaultScrollAreaStyle(theme: Theme): ScrollAreaStyle = ScrollAreaStyle(
    thickness = 10.dp,
    inset = 1.dp,
    thumbShape = CircleShape,
    thumbColor = theme.colors.border,
    thumbHoverColor = theme.colors.mutedForeground.copy(alpha = THUMB_HOVER_ALPHA),
    minimalThumbLength = 16.dp,
    hoverDurationMillis = millisOf(theme.motion.durations.short),
)
