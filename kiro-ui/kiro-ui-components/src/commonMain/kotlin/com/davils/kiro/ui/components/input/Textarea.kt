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

/**
 * A field for text of several lines that fills the available width and, with the default style, grows with its text.
 *
 * @param value the text of the field.
 * @param onValueChange called with the new text when the user edits it.
 * @param modifier the modifier of the field.
 * @param placeholder the text shown while the field is empty, or `null` for none.
 * @param enabled whether the field can be focused and edited.
 * @param isInvalid whether the text is invalid, which the field shows in its invalid colors.
 * @param options the behavior of the field: whether it hides its text, is read only, and which keyboard it asks for.
 * @param style the style of the field.
 * @since 1.0.0
 */
@Composable
public fun Textarea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    enabled: Boolean = true,
    isInvalid: Boolean = false,
    options: InputOptions = InputOptions(),
    style: InputStyle = textareaStyle(),
) {
    FieldBox(
        text = FieldText(value = value, onValueChange = onValueChange, placeholder = placeholder),
        modifier = modifier,
        enabled = enabled,
        isInvalid = isInvalid,
        options = options,
        isSingleLine = false,
        style = style,
    )
}
