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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.input.InputOptions

/**
 * The scope in which an [InputGroup] declares its addons and its one input or textarea.
 *
 * @since 1.0.0
 */
public interface InputGroupScope {
    /**
     * Adds an addon, such as an icon, a text or a button, next to the control. Addons with the same alignment are
     * shown in the order they are declared.
     *
     * @param align where the addon sits relative to the control.
     * @param content the content of the addon, laid out in a row.
     * @since 1.0.0
     */
    public fun addon(align: InputGroupAlign = InputGroupAlign.InlineStart, content: @Composable () -> Unit)

    /**
     * Places a single-line input as the control of the group.
     *
     * @param value the text of the input.
     * @param onValueChange called with the new text when the user edits it.
     * @param placeholder the text shown while [value] is empty, or `null` for none.
     * @param options whether the input is secure or read-only, and its keyboard options.
     * @throws IllegalStateException if the group has an input or a textarea already.
     * @since 1.0.0
     */
    public fun input(
        value: String,
        onValueChange: (String) -> Unit,
        placeholder: String? = null,
        options: InputOptions = InputOptions(),
    )

    /**
     * Places a textarea with several lines as the control of the group.
     *
     * @param value the text of the textarea.
     * @param onValueChange called with the new text when the user edits it.
     * @param placeholder the text shown while [value] is empty, or `null` for none.
     * @param options whether the textarea is secure or read-only, and its keyboard options.
     * @throws IllegalStateException if the group has an input or a textarea already.
     * @since 1.0.0
     */
    public fun textarea(
        value: String,
        onValueChange: (String) -> Unit,
        placeholder: String? = null,
        options: InputOptions = InputOptions(),
    )
}
