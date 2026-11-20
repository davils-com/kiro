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

package com.davils.kiro.ui.components.popover

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.davils.kiro.ui.components.primitive.overlay.OverlayPopup
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.primitive.overlay.panel
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

@Composable
public fun Popover(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    trigger: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    placement: Placement = Placement(),
    style: PopoverStyle = popoverStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(modifier = modifier) {
        trigger()
        OverlayPopup(
            isOpen = expanded,
            placement = placement,
            isFocusable = true,
            onDismiss = { onExpandedChange(false) },
        ) {
            val focus = remember { FocusRequester() }
            LaunchedEffect(focus) { focus.requestFocus() }
            CompositionLocalProvider(
                LocalContentColor provides style.panel.content,
                LocalTextStyle provides style.textStyle,
            ) {
                Column(
                    modifier = Modifier
                        .width(style.width)
                        .panel(style.panel)
                        .focusRequester(focus)
                        .focusable(),
                    verticalArrangement = Arrangement.spacedBy(style.gap),
                    content = content,
                )
            }
        }
    }
}

@Composable
public fun PopoverHeader(
    modifier: Modifier = Modifier,
    style: PopoverStyle = popoverStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.headerGap), content = content)
}

@Composable
public fun PopoverTitle(text: String, modifier: Modifier = Modifier, style: PopoverStyle = popoverStyle()) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

@Composable
public fun PopoverDescription(text: String, modifier: Modifier = Modifier, style: PopoverStyle = popoverStyle()) {
    Text(text = text, modifier = modifier, color = style.descriptionColor)
}
