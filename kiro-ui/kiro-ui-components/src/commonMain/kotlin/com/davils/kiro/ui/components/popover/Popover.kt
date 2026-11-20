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

/**
 * A panel of free content that opens in a popup next to its trigger.
 *
 * The panel takes the focus when it opens. Pressing escape or clicking outside the panel asks to close it through
 * [onExpandedChange].
 *
 * @param expanded whether the popover is open.
 * @param onExpandedChange called with `false` when the popover asks to close.
 * @param trigger the content the popover opens next to, such as a button that sets [expanded].
 * @param modifier the modifier of the box around the trigger.
 * @param placement where the popover opens relative to the trigger.
 * @param style the style of the popover.
 * @param content the content of the panel, laid out in a column.
 * @since 1.0.0
 */
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

/**
 * The header of a popover: a column that holds its title and description.
 *
 * @param modifier the modifier of the column.
 * @param style the style of the popover; its [PopoverStyle.headerGap] spaces the children.
 * @param content the children of the header, such as a [PopoverTitle] and a [PopoverDescription].
 * @since 1.0.0
 */
@Composable
public fun PopoverHeader(
    modifier: Modifier = Modifier,
    style: PopoverStyle = popoverStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.headerGap), content = content)
}

/**
 * The title of a popover, in the [PopoverStyle.titleStyle].
 *
 * @param text the text of the title.
 * @param modifier the modifier of the text.
 * @param style the style of the popover.
 * @since 1.0.0
 */
@Composable
public fun PopoverTitle(text: String, modifier: Modifier = Modifier, style: PopoverStyle = popoverStyle()) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

/**
 * The description of a popover, in the [PopoverStyle.descriptionColor].
 *
 * @param text the text of the description.
 * @param modifier the modifier of the text.
 * @param style the style of the popover.
 * @since 1.0.0
 */
@Composable
public fun PopoverDescription(text: String, modifier: Modifier = Modifier, style: PopoverStyle = popoverStyle()) {
    Text(text = text, modifier = modifier, color = style.descriptionColor)
}
