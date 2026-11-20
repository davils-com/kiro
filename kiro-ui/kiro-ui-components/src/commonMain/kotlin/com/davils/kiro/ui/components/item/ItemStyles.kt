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

package com.davils.kiro.ui.components.item

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

public fun interface ItemStyles {
    public fun resolve(theme: Theme, variant: ItemVariant, size: ItemSize): ItemStyle

    public companion object {
        public val Default: ItemStyles = ItemStyles(::defaultItemStyle)
    }
}

public val ItemStyleKey: StyleKey<ItemStyles> = StyleKey(name = "item") { _ -> ItemStyles.Default }

@Composable
@ReadOnlyComposable
public fun itemStyle(variant: ItemVariant, size: ItemSize): ItemStyle =
    ItemStyleKey.current.resolve(theme, variant, size)
