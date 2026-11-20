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

package com.davils.kiro.ui.components.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [SwitchStyle] of a [Switch] for a theme and a size.
 *
 * Register an implementation under [SwitchStyleKey] in the components of a theme to change the style of every
 * switch.
 *
 * @since 1.0.0
 */
public fun interface SwitchStyles {
    /**
     * Returns the style of a switch with the given size.
     *
     * @param theme the theme whose tokens the style follows.
     * @param size the size of the switch.
     * @return the style of the switch.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, size: SwitchSize): SwitchStyle

    /**
     * Holds the default styles of a switch.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a switch, derived from the tokens of the theme for every [SwitchSize] that Kiro
         * declares.
         *
         * @since 1.0.0
         */
        public val Default: SwitchStyles = SwitchStyles(::defaultSwitchStyle)
    }
}

/**
 * The key of the [SwitchStyles] of a [Switch] in the components of a theme; its default is [SwitchStyles.Default].
 *
 * @since 1.0.0
 */
public val SwitchStyleKey: StyleKey<SwitchStyles> = StyleKey(name = "switch") { _ -> SwitchStyles.Default }

/**
 * Returns the style of a [Switch] with the given size, as the [SwitchStyles] of the current theme resolve it.
 *
 * @param size the size of the switch.
 * @return the style of the switch.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun switchStyle(size: SwitchSize): SwitchStyle = SwitchStyleKey.current.resolve(theme, size)
