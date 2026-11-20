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

package com.davils.kiro.ui.components.primitive.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.davils.kiro.ui.components.input.InputOptions
import com.davils.kiro.ui.components.input.InputTextStyle
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor

@Composable
internal fun TextFieldCore(
    text: FieldText,
    modifier: Modifier,
    enabled: Boolean,
    options: InputOptions,
    isSingleLine: Boolean,
    padding: PaddingValues,
    style: InputTextStyle,
    interactionSource: MutableInteractionSource,
) {
    val textStyle = style.textStyle.copy(color = LocalContentColor.current)
    val alignment = alignmentOf(isSingleLine)
    CompositionLocalProvider(LocalTextSelectionColors provides style.selectionColors) {
        BasicTextField(
            value = text.value,
            onValueChange = text.onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = options.isReadOnly,
            textStyle = textStyle,
            keyboardOptions = options.keyboardOptions,
            singleLine = isSingleLine,
            visualTransformation = transformationOf(options.isSecure),
            interactionSource = interactionSource,
            cursorBrush = SolidColor(style.cursorColor),
        ) { field ->
            Box(modifier = Modifier.padding(padding), contentAlignment = alignment) {
                text.placeholder?.takeIf { text.value.isEmpty() }?.let { placeholder ->
                    Text(text = placeholder, style = textStyle, color = style.placeholderColor)
                }
                field()
            }
        }
    }
}

private fun alignmentOf(isSingleLine: Boolean): Alignment {
    if (isSingleLine) return Alignment.CenterStart
    return Alignment.TopStart
}

private fun transformationOf(isSecure: Boolean): VisualTransformation {
    if (isSecure) return PasswordVisualTransformation()
    return VisualTransformation.None
}
