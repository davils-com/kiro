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

package com.davils.kiro.ui.components.skeleton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

/**
 * Derives the [SkeletonStyle] of a [Skeleton] from a theme; the value of [SkeletonStyleKey] in the component styles of
 * a theme.
 *
 * @since 1.0.0
 */
public fun interface SkeletonStyles {
    /**
     * Returns the style of a [Skeleton] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the skeleton.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SkeletonStyle

    /**
     * Holds the default styles of a [Skeleton].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a [Skeleton], derived only from the tokens of the theme; they turn animation off when
         * the theme reduces motion.
         *
         * @since 1.0.0
         */
        public val Default: SkeletonStyles = SkeletonStyles(::defaultSkeletonStyle)
    }
}

/**
 * The key of the styles of a [Skeleton] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public val SkeletonStyleKey: StyleKey<SkeletonStyles> = StyleKey(name = "skeleton") { _ -> SkeletonStyles.Default }

/**
 * Returns the style of a [Skeleton] in the theme of the composition, with the overrides of its component styles.
 *
 * @return the style of the skeleton.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun skeletonStyle(): SkeletonStyle = SkeletonStyleKey.current.resolve(theme)
