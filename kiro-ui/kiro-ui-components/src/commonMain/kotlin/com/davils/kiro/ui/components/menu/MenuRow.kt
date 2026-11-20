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

package com.davils.kiro.ui.components.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.layout.mirroredInRtl
import com.davils.kiro.ui.components.primitive.overlay.hoverTracking
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
internal fun MenuRow(
    entry: MenuEntry.Interactive,
    isHighlighted: Boolean,
    style: MenuStyle,
    onHover: () -> Unit,
    onActivate: () -> Unit,
) {
    val isDestructive = (entry as? MenuEntry.Action)?.isDestructive == true
    val content = contentOf(isHighlighted = isHighlighted, isDestructive = isDestructive, style = style)
    val container = containerOf(isHighlighted = isHighlighted, isDestructive = isDestructive, style = style)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(disabledAlphaOf(isDisabled = !entry.isEnabled, alpha = style.disabledAlpha))
            .surface(shape = style.itemShape, color = container)
            .clip(style.itemShape)
            .hoverTracking(onHover = { isOver -> if (isOver && entry.isEnabled) onHover() })
            .then(interactionOf(entry = entry, onActivate = onActivate))
            .padding(start = startOf(entry, style), end = endOf(entry, style))
            .padding(vertical = style.itemVerticalPadding),
        contentAlignment = Alignment.CenterStart,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides content,
            LocalTextStyle provides style.textStyle,
            LocalIconStyle provides IconStyle(size = style.iconSize),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(style.gap),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                iconOf(entry)?.let { icon -> Icon(icon = icon, contentDescription = null) }
                Text(text = entry.label, maxLines = 1)
                Spacer(modifier = Modifier.weight(1f))
                shortcutOf(entry)?.let { shortcut ->
                    Text(
                        text = shortcut,
                        style = style.shortcutStyle,
                        color = style.shortcutColor.takeUnless { isHighlighted } ?: content,
                    )
                }
                if (entry is MenuEntry.Sub) {
                    Icon(
                        icon = style.subIcon,
                        contentDescription = null,
                        modifier = Modifier.mirroredInRtl(),
                    )
                }
            }
        }
        if (isMarked(entry)) {
            CompositionLocalProvider(LocalIconStyle provides IconStyle(size = style.iconSize, color = content)) {
                Icon(
                    icon = style.checkIcon,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd).offset(x = indicatorShiftOf(entry, style)),
                )
            }
        }
    }
}

private fun interactionOf(entry: MenuEntry.Interactive, onActivate: () -> Unit): Modifier = when (entry) {
    is MenuEntry.Check -> {
        Modifier
            .pressableCursor(enabled = entry.isEnabled)
            .toggleable(value = entry.isChecked, enabled = entry.isEnabled, role = Role.Checkbox) { onActivate() }
    }

    is MenuEntry.Radio -> {
        Modifier
            .pressableCursor(enabled = entry.isEnabled)
            .selectable(
                selected = entry.isSelected,
                enabled = entry.isEnabled,
                role = Role.RadioButton,
                onClick = onActivate,
            )
    }

    is MenuEntry.Action -> {
        Modifier
            .pressableCursor(enabled = entry.isEnabled)
            .clickable(enabled = entry.isEnabled, role = Role.Button, onClick = onActivate)
    }

    is MenuEntry.Sub -> {
        Modifier
            .pressableCursor(enabled = entry.isEnabled)
            .clickable(enabled = entry.isEnabled, role = Role.Button, onClick = onActivate)
    }
}

private fun iconOf(entry: MenuEntry.Interactive): IconGraphic? = when (entry) {
    is MenuEntry.Action -> entry.icon
    is MenuEntry.Sub -> entry.icon
    is MenuEntry.Check -> null
    is MenuEntry.Radio -> null
}

private fun shortcutOf(entry: MenuEntry.Interactive): String? = when (entry) {
    is MenuEntry.Action -> entry.shortcut
    is MenuEntry.Check -> entry.shortcut
    is MenuEntry.Radio -> null
    is MenuEntry.Sub -> null
}

private fun isMarked(entry: MenuEntry.Interactive): Boolean = when (entry) {
    is MenuEntry.Check -> entry.isChecked
    is MenuEntry.Radio -> entry.isSelected
    is MenuEntry.Action -> false
    is MenuEntry.Sub -> false
}

private fun isIndicated(entry: MenuEntry.Interactive): Boolean = entry is MenuEntry.Check || entry is MenuEntry.Radio

private fun startOf(entry: MenuEntry.Interactive, style: MenuStyle): Dp =
    style.insetPadding.takeIf { entry.isInset } ?: style.itemHorizontalPadding

private fun endOf(entry: MenuEntry.Interactive, style: MenuStyle): Dp =
    style.indicatorPadding.takeIf { isIndicated(entry) } ?: style.itemHorizontalPadding

private fun contentOf(isHighlighted: Boolean, isDestructive: Boolean, style: MenuStyle): Color {
    if (isDestructive) return style.destructive
    return style.highlightContent.takeIf { isHighlighted } ?: style.panel.content
}

private fun containerOf(isHighlighted: Boolean, isDestructive: Boolean, style: MenuStyle): Color {
    if (!isHighlighted) return Color.Transparent
    return style.destructiveHighlight.takeIf { isDestructive } ?: style.highlight
}

private fun indicatorShiftOf(entry: MenuEntry.Interactive, style: MenuStyle): Dp =
    endOf(entry, style) - style.indicatorEnd
