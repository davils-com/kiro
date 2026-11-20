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

package com.davils.kiro.ui.components.menubar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menu.defaultMenuStyle
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

public fun interface MenubarStyles {
    public fun resolve(theme: Theme): MenubarStyle

    public companion object {
        public val Default: MenubarStyles = MenubarStyles(::defaultMenubarStyle)
    }
}

public val MenubarStyleKey: StyleKey<MenubarStyles> = StyleKey(name = "menubar") { _ -> MenubarStyles.Default }

@Composable
@ReadOnlyComposable
public fun menubarStyle(): MenubarStyle = MenubarStyleKey.current.resolve(theme)

internal fun defaultMenubarStyle(theme: Theme): MenubarStyle = MenubarStyle(
    height = 32.dp,
    padding = 3.dp,
    gap = 2.dp,
    shape = RoundedCornerShape(theme.radii.lg),
    border = Border(width = 1.dp, color = theme.colors.border),
    triggerShape = RoundedCornerShape(theme.radii.sm),
    triggerHorizontalPadding = 6.dp,
    triggerVerticalPadding = 2.dp,
    triggerTextStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
    triggerActive = theme.colors.muted,
    menuMinWidth = 144.dp,
    sideOffset = 8.dp,
    alignOffset = (-4).dp,
    menu = defaultMenuStyle(theme),
)
