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

package com.davils.kiro.ui.components.progress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Resolves the [ProgressStyle] of a [Progress] for a theme.
 *
 * Register your own under [ProgressStyleKey] in the component styles of a theme to change every progress.
 *
 * @since 1.0.0
 */
public fun interface ProgressStyles {
    /**
     * Returns the style of a progress.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a progress.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): ProgressStyle

    /**
     * Holds the default progress styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default progress styles, derived from the colors, typography and motion of the theme, with a round
         * track.
         *
         * @since 1.0.0
         */
        public val Default: ProgressStyles = ProgressStyles(::defaultProgressStyle)
    }
}

/**
 * The key of the progress styles in the component styles of a theme, named `progress`.
 *
 * @since 1.0.0
 */
public val ProgressStyleKey: StyleKey<ProgressStyles> = StyleKey(name = "progress") { _ -> ProgressStyles.Default }

/**
 * Returns the progress style of the current theme.
 *
 * @return the style that the [ProgressStyles] registered under [ProgressStyleKey] resolve.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun progressStyle(): ProgressStyle = ProgressStyleKey.current.resolve(theme)
