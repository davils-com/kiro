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

package com.davils.kiro.ui.core.style

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.core.Theme

/**
 * Names the style of one component in the [ComponentStyles] of a theme and knows how to derive its default from the
 * tokens of a theme.
 *
 * Every component of Kiro declares one key for its styles. A key is compared by identity: two keys with the same
 * name are different keys, so the name only serves messages and debugging.
 *
 * @param T the type of the style.
 * @property name the name of the component, such as `button`.
 * @param default derives the default style of the component from the tokens of a theme; it must not read anything
 * but the theme, so that equal themes give equal styles.
 * @since 1.0.0
 */
@Immutable
public class StyleKey<T : Any>(
    public val name: String,
    private val default: (theme: Theme) -> T,
) {
    /**
     * Returns the default style of the component for a theme, without the overrides of its [ComponentStyles].
     *
     * @param theme the theme whose tokens the style follows.
     * @return the default style.
     * @since 1.0.0
     */
    public fun defaultFor(theme: Theme): T = default(theme)

    override fun toString(): String = "StyleKey($name)"
}
