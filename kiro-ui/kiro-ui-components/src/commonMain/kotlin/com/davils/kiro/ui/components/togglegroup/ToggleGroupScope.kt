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

package com.davils.kiro.ui.components.togglegroup

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.components.InlineScope

/**
 * The scope in which the content of a [ToggleGroup] declares its toggles.
 *
 * @param T the type of the values that identify the toggles.
 * @since 1.0.0
 */
public interface ToggleGroupScope<T> {
    /**
     * Declares a toggle of the group, in the order of the calls.
     *
     * @param value the value that identifies the toggle in the selected set of the group.
     * @param enabled whether the toggle can be pressed and focused.
     * @param content the content of the toggle.
     * @since 1.0.0
     */
    public fun item(value: T, enabled: Boolean = true, content: @Composable InlineScope.() -> Unit)
}
