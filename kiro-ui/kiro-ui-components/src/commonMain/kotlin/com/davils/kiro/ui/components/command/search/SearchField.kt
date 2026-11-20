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

package com.davils.kiro.ui.components.command.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import com.davils.kiro.ui.components.inputgroup.InputGroupLayout
import com.davils.kiro.ui.components.inputgroup.InputGroupStyle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic

@Composable
internal fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String,
    focus: FocusRequester,
    style: InputGroupStyle,
    icon: IconGraphic,
    iconAlpha: Float,
    leading: (@Composable () -> Unit)? = null,
) {
    InputGroupLayout(modifier = Modifier, enabled = true, isInvalid = false, style = style, focus = focus) {
        addon {
            Icon(icon = icon, contentDescription = null, modifier = Modifier.alpha(iconAlpha))
        }
        leading?.let { path -> addon(content = path) }
        input(value = query, onValueChange = onQueryChange, placeholder = placeholder)
    }
}
