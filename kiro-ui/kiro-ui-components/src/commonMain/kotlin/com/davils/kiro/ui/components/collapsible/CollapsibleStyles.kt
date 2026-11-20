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

package com.davils.kiro.ui.components.collapsible

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.primitive.motion.fadeTransition
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [CollapsibleStyle] of a [Collapsible] for a theme.
 *
 * Register an implementation under [CollapsibleStyleKey] in the components of a theme to change the style of every
 * collapsible.
 *
 * @since 1.0.0
 */
public fun interface CollapsibleStyles {
    /**
     * Returns the style of a collapsible.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the collapsible.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): CollapsibleStyle

    /**
     * Holds the default styles of a collapsible.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a collapsible: the content expands and fades in, and shrinks and fades out, with the
         * motion of the theme, or appears and disappears at once when the theme reduces motion.
         *
         * @since 1.0.0
         */
        public val Default: CollapsibleStyles = CollapsibleStyles(::defaultCollapsibleStyle)
    }
}

/**
 * Returns the style of a [Collapsible], as the [CollapsibleStyles] of the current theme resolve it.
 *
 * @return the style of the collapsible.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun collapsibleStyle(): CollapsibleStyle = CollapsibleStyleKey.current.resolve(theme)

internal fun defaultCollapsibleStyle(theme: Theme): CollapsibleStyle {
    if (theme.motion.isReduced) return CollapsibleStyle(enter = EnterTransition.None, exit = ExitTransition.None)
    return CollapsibleStyle(
        enter = expandVertically(
            animationSpec = theme.sizeTransition(),
        ) +
            fadeIn(animationSpec = theme.fadeTransition()),
        exit = shrinkVertically(
            animationSpec = theme.sizeTransition(),
        ) +
            fadeOut(animationSpec = theme.fadeTransition()),
    )
}

/**
 * The key of the [CollapsibleStyles] of a [Collapsible] in the components of a theme; its default is
 * [CollapsibleStyles.Default].
 *
 * @since 1.0.0
 */
public val CollapsibleStyleKey: StyleKey<CollapsibleStyles> = StyleKey(
    name = "collapsible",
) { _ -> CollapsibleStyles.Default }
