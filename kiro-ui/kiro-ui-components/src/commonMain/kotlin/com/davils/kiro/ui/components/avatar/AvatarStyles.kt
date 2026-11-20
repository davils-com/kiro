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

package com.davils.kiro.ui.components.avatar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [AvatarStyle] of each [AvatarSize] from the tokens of a theme.
 *
 * Register an implementation for [AvatarStyleKey] in the components of a theme to change the style of every avatar.
 *
 * @since 1.0.0
 */
public fun interface AvatarStyles {
    /**
     * Returns the style of an avatar of the given size under a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @param size the size of the avatar.
     * @return the style of the avatar.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme, size: AvatarSize): AvatarStyle

    /**
     * Holds the default avatar styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default avatar styles, derived from the colors, the typography and the borders of a theme.
         *
         * @since 1.0.0
         */
        public val Default: AvatarStyles = AvatarStyles(::defaultAvatarStyle)
    }
}

/**
 * Returns the avatar style of the current theme for a size, with the overrides of the theme for [AvatarStyleKey].
 *
 * @param size the size of the avatar.
 * @return the style of the avatar.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun avatarStyle(size: AvatarSize): AvatarStyle = AvatarStyleKey.current.resolve(theme, size)

/**
 * The key of the avatar styles in the components of a theme; its default is [AvatarStyles.Default].
 *
 * @since 1.0.0
 */
public val AvatarStyleKey: StyleKey<AvatarStyles> = StyleKey(name = "avatar") { _ -> AvatarStyles.Default }
