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

package com.davils.kiro.ui.components.buttongroup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border

/**
 * Places buttons and other controls next to each other as one joined group.
 *
 * The items are laid out in a row or in a column, in the order they were added. Buttons, toggles and
 * [ButtonGroupText] in the group square the corners that touch their neighbours and overlap them, so that their borders
 * meet as one line; only the outer corners of the first and the last item stay round.
 *
 * @param modifier the modifier of the group.
 * @param orientation whether the items are placed in a row or in a column.
 * @param style the style of the group.
 * @param content adds the items of the group through [ButtonGroupScope.item].
 * @since 1.0.0
 */
@Composable
public fun ButtonGroup(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    style: ButtonGroupStyle = buttonGroupStyle(),
    content: ButtonGroupScope.() -> Unit,
) {
    val items = ButtonGroupScopeImpl().apply(content).items
    val placed: @Composable () -> Unit = {
        items.forEachIndexed { index, item ->
            val position = GroupItem(
                orientation = orientation,
                isFirst = index == 0,
                isLast = index == items.lastIndex,
                radius = style.radius,
            )
            CompositionLocalProvider(LocalGroupItem provides position, content = item)
        }
    }
    when (orientation) {
        Orientation.Horizontal -> Row(modifier = modifier.height(IntrinsicSize.Min)) { placed() }
        Orientation.Vertical -> Column(modifier = modifier.width(IntrinsicSize.Min)) { placed() }
    }
}

internal fun defaultButtonGroupStyle(theme: Theme): ButtonGroupStyle = ButtonGroupStyle(
    radius = theme.radii.lg,
    overlap = 1.dp,
    separatorColor = theme.colors.input,
    separatorThickness = 1.dp,
    separatorInset = 1.dp,
    textContainer = theme.colors.muted,
    textBorder = Border(width = 1.dp, color = theme.colors.border),
    textStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
    textPadding = 10.dp,
    textGap = 8.dp,
    iconSize = 16.dp,
)
