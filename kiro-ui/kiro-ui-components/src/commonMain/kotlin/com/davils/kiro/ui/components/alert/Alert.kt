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

package com.davils.kiro.ui.components.alert

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle

/**
 * Shows a callout that draws attention to a message, with an optional icon at its start and an optional action in
 * its top end corner.
 *
 * The alert fills the width of its parent and announces its content assertively to accessibility services. The
 * content is laid out in a column, usually an [AlertTitle] and an [AlertDescription].
 *
 * @param modifier the modifier of the alert.
 * @param variant the variant of the alert, which picks its colors.
 * @param icon the icon at the start of the alert, or `null` for none.
 * @param action the action in the top end corner, such as a close button, or `null` for none.
 * @param style the style of the alert.
 * @param content the content of the alert.
 * @since 1.0.0
 */
@Composable
public fun Alert(
    modifier: Modifier = Modifier,
    variant: AlertVariant = AlertVariant.Default,
    icon: IconGraphic? = null,
    action: (@Composable () -> Unit)? = null,
    style: AlertStyle = alertStyle(variant),
    content: @Composable AlertScope.() -> Unit,
) {
    val surface = Modifier.surface(shape = style.shape, color = style.container, border = style.border)
    Box(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .then(surface)
                .semantics { liveRegion = LiveRegionMode.Assertive },
        ),
    ) {
        CompositionLocalProvider(LocalContentColor provides style.content, LocalTextStyle provides style.textStyle) {
            Row(
                modifier = Modifier
                    .padding(horizontal = style.horizontalPadding, vertical = style.verticalPadding)
                    .padding(end = reserveOf(style, action)),
                horizontalArrangement = Arrangement.spacedBy(style.iconGap),
            ) {
                icon?.let { glyph ->
                    Icon(
                        icon = glyph,
                        contentDescription = null,
                        modifier = Modifier.offset(y = style.iconOffset),
                        style = IconStyle(size = style.iconSize),
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(style.gap)) {
                    AlertScopeImpl(columnScope = this, style = style).content()
                }
            }
            action?.let { slot ->
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = style.actionInset, end = style.actionInset),
                ) { slot() }
            }
        }
    }
}

private fun reserveOf(style: AlertStyle, action: (@Composable () -> Unit)?): Dp {
    if (action == null) return 0.dp
    return style.actionReserve - style.horizontalPadding
}
