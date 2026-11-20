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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
internal data class MenuNesting(
    val onCloseSelf: (() -> Unit)? = null,
    val isSubFocusable: Boolean = true,
    val onHover: ((Boolean) -> Unit)? = null,
    val onBounds: (Rect) -> Unit = {},
    val chain: MenuChain? = null,
    val onOutside: (() -> Unit)? = null,
    val edge: Dp = 0.dp,
    val depth: Int = 0,
)
