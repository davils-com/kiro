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

package com.davils.kiro.ui.components.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the style of an [Alert] for each [AlertVariant] from the tokens of a theme.
 *
 * Register an implementation under [AlertStyleKey] in the components of a theme to change how every alert looks.
 *
 * @since 1.0.0
 */
public fun interface AlertStyles {
    /**
     * Returns the style of an [Alert] of a variant for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param variant the variant of the alert.
     * @return the style of the alert.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, variant: AlertVariant): AlertStyle

    /**
     * Holds the default [AlertStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of an [Alert], derived from the colors, radii and typography of the theme. They
         * throw an [IllegalArgumentException] for a variant other than the five of [AlertVariant].
         *
         * @since 1.0.0
         */
        public val Default: AlertStyles = AlertStyles(::defaultAlertStyle)
    }
}

/**
 * Returns the style of an [Alert] of a variant in the current theme, with the overrides of its components.
 *
 * @param variant the variant of the alert.
 * @return the style of the alert.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun alertStyle(variant: AlertVariant): AlertStyle = AlertStyleKey.current.resolve(theme, variant)

/**
 * The key of the styles of an [Alert] in the components of a theme, with [AlertStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val AlertStyleKey: StyleKey<AlertStyles> = StyleKey(name = "alert") { _ -> AlertStyles.Default }
