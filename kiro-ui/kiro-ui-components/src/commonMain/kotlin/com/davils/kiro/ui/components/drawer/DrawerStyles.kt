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

/**
 * Derives the [DrawerStyle] of a [Drawer] from a theme; the value of [DrawerStyleKey] in the component styles of a
 * theme.
 *
 * @since 1.0.0
 */
public fun interface DrawerStyles {
    /**
     * Returns the style of a [Drawer] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the drawer.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): DrawerStyle

    /**
     * Holds the default styles of a [Drawer].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default styles of a [Drawer], derived only from the tokens of the theme and the default style of a sheet.
         *
         * @since 1.0.0
         */
        public val Default: DrawerStyles = DrawerStyles(::defaultDrawerStyle)
    }
}

/**
 * Returns the style of a [Drawer] in the theme of the composition, with the overrides of its component styles.
 *
 * @return the style of the drawer.
 * @since 1.0.0
 */
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

/**
 * The key of the styles of a [Drawer] in the component styles of a theme.
 *
 * @since 1.0.0
 */
public val DrawerStyleKey: StyleKey<DrawerStyles> = StyleKey(name = "drawer") { _ -> DrawerStyles.Default }
