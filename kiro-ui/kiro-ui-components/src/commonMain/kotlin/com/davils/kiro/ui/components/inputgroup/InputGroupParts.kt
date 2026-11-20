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

package com.davils.kiro.ui.components.inputgroup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import com.davils.kiro.ui.components.InlineScope
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonStyle
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun InputGroupText(
    modifier: Modifier = Modifier,
    style: InputGroupStyle = inputGroupStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(style.addon.gap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides style.addon.color,
            LocalTextStyle provides style.addon.plainTextStyle,
        ) {
            content()
        }
    }
}

@Composable
public fun InputGroupButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Ghost,
    size: InputGroupButtonSize = InputGroupButtonSize.Xs,
    enabled: Boolean = true,
    style: ButtonStyle = inputGroupButtonStyle(variant, size),
    content: @Composable InlineScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.layoutId(AddonButton),
        variant = variant,
        enabled = enabled,
        style = style,
        content = content,
    )
}
