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
 * The styles of the components of a theme, as overrides of the defaults that each component derives from the tokens
 * of the theme.
 *
 * Without an override, a component takes the default of its [StyleKey]. An override receives the theme and the style
 * it replaces and returns the style to use, so it can replace the style as a whole or change single values of it
 * through its `copy` function. Overrides of the same key stack: each one receives the result of the one before.
 *
 * A [ComponentStyles] is immutable; [with] and [without] return new instances. It is compared by identity of its
 * overrides, because functions cannot be compared by their behaviour.
 *
 * @since 0.1.0
 */
@Immutable
public class ComponentStyles private constructor(private val overrides: List<StyleOverride<*>>) {
    /**
     * The keys that have at least one override, in the order in which they were first overridden.
     *
     * @since 0.1.0
     */
    public val keys: List<StyleKey<*>>
        get() = buildList { overrides.forEach { override -> if (override.key !in this) add(override.key) } }

    /**
     * Returns these styles with one more override for a component.
     *
     * @param T the type of the style.
     * @param key the key of the component.
     * @param override returns the style to use from the theme and the style it replaces.
     * @return the styles with the override.
     * @since 0.1.0
     */
    public fun <T : Any> with(key: StyleKey<T>, override: (theme: Theme, style: T) -> T): ComponentStyles {
        val added = buildList {
            addAll(overrides)
            add(StyleOverride(key = key, transform = override))
        }
        return ComponentStyles(added)
    }

    /**
     * Returns these styles without any override for a component, which then takes its default again.
     *
     * @param key the key of the component.
     * @return the styles without the overrides of [key].
     * @since 0.1.0
     */
    public fun without(key: StyleKey<*>): ComponentStyles {
        val kept = buildList { overrides.forEach { override -> if (override.key !== key) add(override) } }
        return ComponentStyles(kept)
    }

    /**
     * Returns the style of a component for a theme: its default, changed by every override of its key in turn.
     *
     * @param T the type of the style.
     * @param key the key of the component.
     * @param theme the theme whose tokens the default follows and which every override receives.
     * @return the style.
     * @since 0.1.0
     */
    public fun <T : Any> resolve(key: StyleKey<T>, theme: Theme): T {
        val default = key.defaultFor(theme)
        return overrides.fold(default) { style, override -> override.applyTo(key = key, theme = theme, style = style) }
    }

    override fun equals(other: Any?): Boolean = other is ComponentStyles && overrides == other.overrides

    override fun hashCode(): Int = overrides.hashCode()

    override fun toString(): String = "ComponentStyles(keys=$keys)"

    /**
     * The styles every theme starts from.
     *
     * @since 0.1.0
     */
    public companion object {
        /**
         * Styles without any override, so that every component takes its default.
         *
         * @since 0.1.0
         */
        public val Default: ComponentStyles = ComponentStyles(overrides = emptyList())
    }
}
