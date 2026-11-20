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

package com.davils.kiro.ui.components.accordion

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

/**
 * The scope in which the content of an [Accordion] declares its items.
 *
 * @param T the type of the values that identify the items.
 * @since 1.0.0
 */
public interface AccordionScope<T> {
    /**
     * Declares an item of the accordion, in the order of the calls.
     *
     * @param value the value that identifies the item in the expanded set of the accordion.
     * @param enabled whether the item can be toggled and focused.
     * @param trigger the content of the trigger row, shown left of the expand icon.
     * @param content the content shown below the trigger while the item is expanded.
     * @since 1.0.0
     */
    public fun item(
        value: T,
        enabled: Boolean = true,
        trigger: @Composable RowScope.() -> Unit,
        content: @Composable ColumnScope.() -> Unit,
    )
}
