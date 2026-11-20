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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun ButtonGroupText(
    modifier: Modifier = Modifier,
    style: ButtonGroupStyle = buttonGroupStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    val item = LocalGroupItem.current
    val shape = item.shapeOr(RoundedCornerShape(style.radius))
    val surface = Modifier.surface(shape = shape, color = style.textContainer, border = style.textBorder)
    Row(
        modifier = modifier
            .joinedTo(item = item, overlap = style.overlap)
            .fillMaxHeight()
            .then(surface)
            .padding(horizontal = style.textPadding),
        horizontalArrangement = Arrangement.spacedBy(style.textGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides style.textStyle,
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            content()
        }
    }
}

@Composable
public fun ButtonGroupSeparator(modifier: Modifier = Modifier, style: ButtonGroupStyle = buttonGroupStyle()) {
    val orientation = LocalGroupItem.current?.orientation ?: Orientation.Horizontal
    val sized = separatorModifierOf(modifier = modifier, orientation = orientation, style = style)
    Box(modifier = sized.background(style.separatorColor))
}

private fun separatorModifierOf(modifier: Modifier, orientation: Orientation, style: ButtonGroupStyle): Modifier {
    if (orientation == Orientation.Vertical) {
        val inset = modifier.padding(horizontal = style.separatorInset)
        return inset.fillMaxWidth().height(style.separatorThickness)
    }
    val inset = modifier.padding(vertical = style.separatorInset)
    return inset.fillMaxHeight().width(style.separatorThickness)
}
