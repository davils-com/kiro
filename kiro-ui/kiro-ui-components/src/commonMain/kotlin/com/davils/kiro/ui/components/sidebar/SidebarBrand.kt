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

package com.davils.kiro.ui.components.sidebar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.components.primitive.interaction.pressableCursor
import com.davils.kiro.ui.components.primitive.motion.fadeTransition
import com.davils.kiro.ui.components.primitive.motion.sizeTransition
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconGraphic
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

@Composable
public fun SidebarBrand(
    title: String,
    logo: IconGraphic,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    style: SidebarStyle = sidebarStyle(),
    trailing: (@Composable () -> Unit)? = null,
) {
    val isCollapsed = LocalIconCollapsed.current
    val source = remember { MutableInteractionSource() }
    val isHovered by source.collectIsHoveredAsState()
    val container = animateColor(
        targetValue = style.accent.takeIf { isHovered && onClick != null } ?: Color.Transparent,
        animationSpec = theme.colorTransition(),
    )
    val press = onClick?.let { click ->
        Modifier
            .pressableCursor()
            .clickable(interactionSource = source, indication = null, role = Role.Button, onClick = click)
    } ?: Modifier
    Row(
        modifier = modifier
            .fillMaxWidth()
            .surface(shape = style.buttonShape, color = container.value)
            .hoverable(interactionSource = source)
            .then(press)
            .padding(style.buttonPadding / 2),
        horizontalArrangement = Arrangement.spacedBy(style.buttonGap),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BrandTile(logo = logo, style = style)
        AnimatedVisibility(
            visible = !isCollapsed,
            enter = expandHorizontally(
                animationSpec = theme.sizeTransition(),
            ) +
                fadeIn(animationSpec = theme.fadeTransition()),
            exit = shrinkHorizontally(
                animationSpec = theme.sizeTransition(),
            ) +
                fadeOut(animationSpec = theme.fadeTransition()),
        ) {
            BrandText(title = title, subtitle = subtitle, style = style, trailing = trailing)
        }
    }
}

@Composable
private fun BrandTile(logo: IconGraphic, style: SidebarStyle) {
    Box(
        modifier = Modifier.size(style.brandSize).surface(shape = style.brandShape, color = style.brandContainer),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalIconStyle provides IconStyle(size = style.iconSize, color = style.brandContent),
        ) {
            Icon(icon = logo, contentDescription = null)
        }
    }
}

@Composable
private fun BrandText(title: String, subtitle: String?, style: SidebarStyle, trailing: (@Composable () -> Unit)?) {
    Row(horizontalArrangement = Arrangement.spacedBy(style.buttonGap), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = style.brandTitleStyle, maxLines = 1, overflow = TextOverflow.Ellipsis)
            subtitle?.let { text ->
                Text(
                    text = text,
                    style = style.brandSubtitleStyle,
                    color = style.brandSubtitleColor,
                    maxLines = 1,
                )
            }
        }
        trailing?.invoke()
    }
}
