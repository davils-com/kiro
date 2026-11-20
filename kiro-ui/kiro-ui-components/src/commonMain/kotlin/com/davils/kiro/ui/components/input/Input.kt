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

package com.davils.kiro.ui.components.input

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.primitive.textfield.FieldBox
import com.davils.kiro.ui.components.primitive.textfield.FieldText

@Composable
public fun Input(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    options: InputOptions = InputOptions(),
    style: InputStyle = inputStyle(),
) {
    FieldBox(
        text = FieldText(value = value, onValueChange = onValueChange, placeholder = placeholder),
        modifier = modifier,
        enabled = enabled,
        isInvalid = isInvalid,
        options = options,
        isSingleLine = true,
        style = style,
    )
}
