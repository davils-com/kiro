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

package com.davils.kiro.ui.components.sheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.dialog.defaultDialogStyle
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val FRACTION: Float = 0.25f

/**
 * Resolves the [SheetStyle] of a [Sheet] for a theme.
 *
 * Register your own under [SheetStyleKey] in the component styles of a theme to change every sheet.
 *
 * @since 1.0.0
 */
public fun interface SheetStyles {
    /**
     * Returns the style of a sheet.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of a sheet.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): SheetStyle

    /**
     * Holds the default sheet styles.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default sheet styles, derived from the colors and shadows of the theme and from the default dialog
         * style, with a panel that takes a quarter of the window.
         *
         * @since 1.0.0
         */
        public val Default: SheetStyles = SheetStyles(::defaultSheetStyle)
    }
}

/**
 * The key of the sheet styles in the component styles of a theme, named `sheet`.
 *
 * @since 1.0.0
 */
public val SheetStyleKey: StyleKey<SheetStyles> = StyleKey(name = "sheet") { _ -> SheetStyles.Default }

/**
 * Returns the sheet style of the current theme.
 *
 * @return the style that the [SheetStyles] registered under [SheetStyleKey] resolve.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun sheetStyle(): SheetStyle = SheetStyleKey.current.resolve(theme)

internal fun defaultSheetStyle(theme: Theme): SheetStyle {
    val dialog = defaultDialogStyle(theme)
    return SheetStyle(
        container = theme.colors.popover,
        content = theme.colors.popoverForeground,
        border = Border(width = 1.dp, color = theme.colors.border),
        shadow = theme.shadows.lg,
        gap = 16.dp,
        textStyle = dialog.textStyle,
        fraction = FRACTION,
        minSize = 280.dp,
        sectionPadding = 16.dp,
        headerGap = 2.dp,
        footerGap = 8.dp,
        titleStyle = dialog.titleStyle.copy(lineHeight = theme.typography.scale.base.lineHeight),
        descriptionStyle = dialog.descriptionStyle,
        descriptionColor = dialog.descriptionColor,
        closeIcon = dialog.closeIcon,
        closeOffset = 12.dp,
        closeLabel = dialog.closeLabel,
        scrim = dialog.scrim,
    )
}
