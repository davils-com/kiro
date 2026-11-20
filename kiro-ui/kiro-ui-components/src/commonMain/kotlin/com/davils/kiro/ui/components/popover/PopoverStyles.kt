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

package com.davils.kiro.ui.components.popover

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

/**
 * Derives the [PopoverStyle] of a popover from the tokens of a theme.
 *
 * The components of a theme hold one under each key of this type; override a key in the components of a theme to change
 * the style of every popover that reads it.
 *
 * @since 1.0.0
 */
public fun interface PopoverStyles {
    /**
     * Returns the style of a popover for the tokens of a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): PopoverStyle

    /**
     * Holds the [PopoverStyles] that Kiro ships.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a popover, derived only from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: PopoverStyles = PopoverStyles(::defaultPopoverStyle)
    }
}

/**
 * The key of the styles of a popover in the components of a theme, named `popover`; its default is
 * [PopoverStyles.Default].
 *
 * @since 1.0.0
 */
public val PopoverStyleKey: StyleKey<PopoverStyles> = StyleKey(name = "popover") { _ -> PopoverStyles.Default }

/**
 * Returns the style of a popover in the theme of the composition, resolved through the styles that the components of
 * the theme hold under [PopoverStyleKey].
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun popoverStyle(): PopoverStyle = PopoverStyleKey.current.resolve(theme)

internal fun defaultPopoverStyle(theme: Theme): PopoverStyle = PopoverStyle(
    panel = theme.popoverPanel(),
    width = 288.dp,
    gap = 10.dp,
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
    headerGap = 2.dp,
    titleStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
    descriptionColor = theme.colors.mutedForeground,
)
