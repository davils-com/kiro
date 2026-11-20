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

package com.davils.kiro.ui.components.hovercard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.overlay.popoverPanel
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import kotlin.time.Duration.Companion.milliseconds

private const val OPEN_DELAY_MILLIS: Int = 600
private const val CLOSE_DELAY_MILLIS: Int = 300

/**
 * Resolves the style of a [HoverCard] for a theme.
 *
 * Replace it in the components of a theme under [HoverCardStyleKey] to change the style of every hover card.
 *
 * @since 1.0.0
 */
public fun interface HoverCardStyles {
    /**
     * Returns the style of a [HoverCard] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the hover card.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): HoverCardStyle

    /**
     * Holds the default [HoverCardStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [HoverCard], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: HoverCardStyles = HoverCardStyles(::defaultHoverCardStyle)
    }
}

/**
 * Returns the style of a [HoverCard] in the theme of the composition, resolved by the [HoverCardStyles] under
 * [HoverCardStyleKey].
 *
 * @return the style of the hover card.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun hoverCardStyle(): HoverCardStyle = HoverCardStyleKey.current.resolve(theme)

internal fun defaultHoverCardStyle(theme: Theme): HoverCardStyle = HoverCardStyle(
    panel = theme.popoverPanel(),
    width = 256.dp,
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    openDelay = OPEN_DELAY_MILLIS.milliseconds,
    closeDelay = CLOSE_DELAY_MILLIS.milliseconds,
)

/**
 * The key of the [HoverCardStyles] of a [HoverCard] in the components of a theme; its default is
 * [HoverCardStyles.Default].
 *
 * @since 1.0.0
 */
public val HoverCardStyleKey: StyleKey<HoverCardStyles> = StyleKey(name = "hover-card") { _ -> HoverCardStyles.Default }
