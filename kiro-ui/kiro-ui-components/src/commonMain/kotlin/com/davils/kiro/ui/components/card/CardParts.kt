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

package com.davils.kiro.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.style.Border

@Composable
public fun CardScope.CardHeader(
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
    content: @Composable CardScope.() -> Unit,
) {
    val cardStyle = style
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = style.spacing),
        verticalAlignment = Alignment.Top,
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(cardStyle.headerGap)) {
            CardScopeImpl(style = cardStyle).content()
        }
        action?.invoke()
    }
}

@Composable
public fun CardScope.CardTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.titleStyle)
}

@Composable
public fun CardScope.CardDescription(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}

@Composable
public fun CardScope.CardContent(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = style.spacing),
        content = content,
    )
}

@Composable
public fun CardScope.CardFooter(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val surface = Modifier.surface(shape = RectangleShape, color = style.footerContainer)
    Row(
        modifier = modifier
            .layoutId(CARD_FOOTER)
            .fillMaxWidth()
            .then(surface)
            .topBorder(style.footerBorder)
            .padding(style.spacing),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

private fun Modifier.topBorder(border: Border): Modifier = drawBehind {
    val width = border.width.toPx()
    drawLine(
        color = border.color,
        start = Offset(0f, width / 2),
        end = Offset(size.width, width / 2),
        strokeWidth = width,
    )
}
