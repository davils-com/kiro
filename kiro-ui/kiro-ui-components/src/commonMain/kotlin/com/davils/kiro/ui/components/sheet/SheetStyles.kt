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

public fun interface SheetStyles {
    public fun resolve(theme: Theme): SheetStyle

    public companion object {
        public val Default: SheetStyles = SheetStyles(::defaultSheetStyle)
    }
}

public val SheetStyleKey: StyleKey<SheetStyles> = StyleKey(name = "sheet") { _ -> SheetStyles.Default }

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
