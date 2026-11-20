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

package com.davils.kiro.ui.components.pagination

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.InlineScopeImpl
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.buttonStyle
import com.davils.kiro.ui.components.primitive.layout.mirroredInRtl
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun Pagination(
    modifier: Modifier = Modifier,
    style: PaginationStyle = paginationStyle(),
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().semantics { isTraversalGroup = true },
        horizontalArrangement = Arrangement.spacedBy(style.gap, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
public fun PaginationLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    size: ButtonSize = ButtonSize.Icon,
    enabled: Boolean = true,
    content: @Composable InlineScopeImpl.() -> Unit,
) {
    val variant = ButtonVariant.Outline.takeIf { isActive } ?: ButtonVariant.Ghost
    Button(
        onClick = onClick,
        modifier = modifier.semantics { selected = isActive },
        variant = variant,
        size = size,
        enabled = enabled,
        style = buttonStyle(variant, size),
        content = content,
    )
}

@Composable
public fun PaginationPrevious(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = Messages.Pagination.previous,
    enabled: Boolean = true,
    style: PaginationStyle = paginationStyle(),
) {
    EdgeLink(onClick = onClick, modifier = modifier, enabled = enabled, style = style) {
        EdgeIcon(icon = style.previousIcon, placement = IconPlacement.Leading)
        Text(text)
    }
}

@Composable
public fun PaginationNext(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = Messages.Pagination.next,
    enabled: Boolean = true,
    style: PaginationStyle = paginationStyle(),
) {
    EdgeLink(onClick = onClick, modifier = modifier, enabled = enabled, style = style) {
        Text(text)
        EdgeIcon(icon = style.nextIcon, placement = IconPlacement.Trailing)
    }
}

@Composable
public fun PaginationEllipsis(
    contentDescription: String,
    modifier: Modifier = Modifier,
    style: PaginationStyle = paginationStyle(),
) {
    Box(
        modifier = modifier.size(style.ellipsisBox).semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize)) {
            Icon(icon = style.ellipsisIcon, contentDescription = null)
        }
    }
}

@Composable
private fun EdgeLink(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    style: PaginationStyle,
    content: @Composable InlineScopeImpl.() -> Unit,
) {
    val base = buttonStyle(ButtonVariant.Ghost, ButtonSize.Default)
    Button(
        onClick = onClick,
        modifier = modifier,
        variant = ButtonVariant.Ghost,
        enabled = enabled,
        style = base.copy(iconEdgePadding = style.edgePadding),
        content = content,
    )
}

@Composable
private fun InlineScopeImpl.EdgeIcon(icon: IconGraphic, placement: IconPlacement) {
    val scope = this
    Box(modifier = Modifier.mirroredInRtl()) { scope.Icon(icon = icon, placement = placement) }
}
