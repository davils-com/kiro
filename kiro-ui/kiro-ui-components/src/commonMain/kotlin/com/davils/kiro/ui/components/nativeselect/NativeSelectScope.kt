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

package com.davils.kiro.ui.components.nativeselect

/**
 * The scope in which the options and the groups of a [NativeSelect] are declared, in the order they are shown.
 *
 * @param T the type of the values.
 * @since 1.0.0
 */
public interface NativeSelectScope<T> {
    /**
     * Adds an option.
     *
     * @param value the value that the option picks.
     * @param label the text of the option.
     * @param enabled whether the option can be picked.
     * @since 1.0.0
     */
    public fun option(value: T, label: String, enabled: Boolean = true)

    /**
     * Adds a group: a heading followed by the options that [content] declares.
     *
     * @param label the text of the heading.
     * @param content declares the options of the group.
     * @since 1.0.0
     */
    public fun group(label: String, content: NativeSelectScope<T>.() -> Unit)
}
