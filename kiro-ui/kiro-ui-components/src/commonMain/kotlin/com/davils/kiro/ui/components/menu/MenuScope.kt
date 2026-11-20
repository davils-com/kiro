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

package com.davils.kiro.ui.components.menu

import com.davils.kiro.ui.icons.IconGraphic

public interface MenuScope {
    public fun item(
        label: String,
        onClick: () -> Unit,
        enabled: Boolean = true,
        icon: IconGraphic? = null,
        shortcut: String? = null,
        variant: MenuItemVariant = MenuItemVariant.Default,
        isInset: Boolean = false,
    )

    public fun checkbox(
        label: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        enabled: Boolean = true,
        shortcut: String? = null,
        isInset: Boolean = false,
    )

    public fun <T> radio(
        label: String,
        value: T,
        selected: T,
        onSelect: (T) -> Unit,
        enabled: Boolean = true,
        isInset: Boolean = false,
    )

    public fun label(text: String, isInset: Boolean = false)

    public fun separator()

    public fun sub(
        label: String,
        icon: IconGraphic? = null,
        enabled: Boolean = true,
        isInset: Boolean = false,
        content: MenuScope.() -> Unit,
    )
}
