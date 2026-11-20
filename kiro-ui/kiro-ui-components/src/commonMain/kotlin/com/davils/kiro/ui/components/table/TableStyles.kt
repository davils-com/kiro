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

package com.davils.kiro.ui.components.table

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val HALF: Float = 0.5f

public fun interface TableStyles {
    public fun resolve(theme: Theme): TableStyle

    public companion object {
        public val Default: TableStyles = TableStyles(::defaultTableStyle)
    }
}

public val TableStyleKey: StyleKey<TableStyles> = StyleKey(name = "table") { _ -> TableStyles.Default }

@Composable
@ReadOnlyComposable
public fun tableStyle(): TableStyle = TableStyleKey.current.resolve(theme)

internal fun defaultTableStyle(theme: Theme): TableStyle {
    val sm = theme.typography.scale.sm
    val muted = theme.colors.muted
    return TableStyle(
        textStyle = theme.textStyleOf(size = sm),
        headTextStyle = theme.textStyleOf(size = sm, weight = FontWeight.Medium),
        headColor = theme.colors.foreground,
        headHeight = 40.dp,
        headPadding = 8.dp,
        cellPadding = 8.dp,
        hoverColor = muted.copy(alpha = HALF),
        selectedColor = muted,
        footerColor = muted.copy(alpha = HALF),
        footerTextStyle = theme.textStyleOf(size = sm, weight = FontWeight.Medium),
        border = Border(width = 1.dp, color = theme.colors.border),
        captionStyle = theme.textStyleOf(size = sm),
        captionColor = theme.colors.mutedForeground,
        captionSpacing = 16.dp,
        colorAnimation = theme.colorTransition(),
    )
}
