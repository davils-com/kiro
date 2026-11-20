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

package com.davils.kiro.ui.components.drawer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.sheet.defaultSheetStyle
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val DISMISS_FRACTION: Float = 0.25f

private const val SIDE_FRACTION: Float = 0.4f
private const val HALF: Float = 0.5f

public fun interface DrawerStyles {
    public fun resolve(theme: Theme): DrawerStyle

    public companion object {
        public val Default: DrawerStyles = DrawerStyles(::defaultDrawerStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun drawerStyle(): DrawerStyle = DrawerStyleKey.current.resolve(theme)

internal fun defaultDrawerStyle(theme: Theme): DrawerStyle {
    val sheet = defaultSheetStyle(theme)
    return DrawerStyle(
        container = sheet.container,
        content = sheet.content,
        border = sheet.border,
        radius = theme.radii.xl,
        handleArea = 12.dp,
        handleLength = 96.dp,
        handleThickness = 4.dp,
        handleColor = theme.colors.muted,
        textStyle = sheet.textStyle,
        sideFraction = SIDE_FRACTION,
        minSize = sheet.minSize,
        snapPoints = listOf(HALF, 1f),
        edgeInset = 96.dp,
        sectionPadding = sheet.sectionPadding,
        headerGap = sheet.headerGap,
        footerGap = sheet.footerGap,
        titleStyle = sheet.titleStyle,
        descriptionStyle = sheet.descriptionStyle.copy(),
        descriptionColor = sheet.descriptionColor,
        scrim = sheet.scrim,
        dismissFraction = DISMISS_FRACTION,
    )
}

public val DrawerStyleKey: StyleKey<DrawerStyles> = StyleKey(name = "drawer") { _ -> DrawerStyles.Default }
