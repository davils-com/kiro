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

package com.davils.kiro.ui.components.select

/**
 * The scope in which the content of a [Select] declares its placeholder and the entries of its menu.
 *
 * @param T the type of the values of the options.
 * @since 1.0.0
 */
public interface SelectScope<T> {
    /**
     * Sets the text the field shows while no option is selected; the last call wins, and without a call the text
     * is empty.
     *
     * @param text the placeholder.
     * @since 1.0.0
     */
    public fun placeholder(text: String)

    /**
     * Adds an option to the menu, in the order of the calls.
     *
     * @param value the value of the option.
     * @param label the label of the option, shown in the menu and in the field while it is selected.
     * @param enabled whether the option can be chosen.
     * @since 1.0.0
     */
    public fun option(value: T, label: String, enabled: Boolean = true)

    /**
     * Adds a label to the menu, followed by the entries that [content] declares.
     *
     * @param label the label of the group.
     * @param content declares the entries of the group.
     * @since 1.0.0
     */
    public fun group(label: String, content: SelectScope<T>.() -> Unit)

    /**
     * Adds a separator line to the menu.
     *
     * @since 1.0.0
     */
    public fun separator()
}
