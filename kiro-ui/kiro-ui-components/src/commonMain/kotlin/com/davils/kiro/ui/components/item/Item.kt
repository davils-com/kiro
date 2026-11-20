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

package com.davils.kiro.ui.components.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.davils.kiro.ui.components.primitive.interaction.animate
import com.davils.kiro.ui.components.primitive.interaction.collectInteractionState
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.core.style.Border

@Composable
public fun Item(
    modifier: Modifier = Modifier,
    variant: ItemVariant = ItemVariant.Default,
    size: ItemSize = ItemSize.Default,
    onClick: (() -> Unit)? = null,
    style: ItemStyle = itemStyle(variant, size),
    header: (@Composable RowScope.() -> Unit)? = null,
    footer: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ItemScopeImpl.() -> Unit,
) {
    val source = remember { MutableInteractionSource() }
    val state = source.collectInteractionState()
    val colors = style.colors.animate(state = state, animation = style.colorAnimation)
    val surface = Modifier.surface(
        shape = style.shape,
        color = colors.container,
        border = Border(width = style.borderWidth, color = colors.border),
        ring = Border(width = style.ringWidth, color = colors.ring),
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(surface)
            .interactive(source = source, onClick = onClick)
            .padding(horizontal = style.horizontalPadding, vertical = style.verticalPadding),
        verticalArrangement = Arrangement.spacedBy(style.gap),
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.content, LocalTextStyle provides style.textStyle) {
            header?.let { slot -> ItemEdge(style = style, content = slot) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(style.gap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ItemScope(rowScope = this, style = style).content()
            }
            footer?.let { slot -> ItemEdge(style = style, content = slot) }
        }
    }
}

@Composable
private fun ItemEdge(style: ItemStyle, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(style.actionsGap, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

private fun Modifier.interactive(source: MutableInteractionSource, onClick: (() -> Unit)?): Modifier {
    if (onClick == null) return this
    val hovering = hoverable(interactionSource = source)
    return hovering.pressableCursor().clickable(
        interactionSource = source,
        indication = null,
        role = Role.Button,
        onClick = onClick,
    )
}
