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

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Immutable

/**
 * The scope of the content of an [Item]: a row that also hands its parts the style of the item.
 *
 * @since 1.0.0
 */
@LayoutScopeMarker
@Immutable
public interface ItemScope : RowScope {
    /**
     * The style of the item, which its media, content and actions use.
     *
     * @since 1.0.0
     */
    public val style: ItemStyle
}
