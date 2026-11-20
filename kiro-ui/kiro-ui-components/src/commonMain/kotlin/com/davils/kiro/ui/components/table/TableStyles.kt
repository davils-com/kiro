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

/**
 * Resolves the style of a [Table] for a theme.
 *
 * Replace it in the components of a theme under [TableStyleKey] to change the style of every table.
 *
 * @since 1.0.0
 */
public fun interface TableStyles {
    /**
     * Returns the style of a [Table] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the table.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): TableStyle

    /**
     * Holds the default [TableStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [Table], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: TableStyles = TableStyles(::defaultTableStyle)
    }
}

/**
 * The key of the [TableStyles] of a [Table] in the components of a theme; its default is [TableStyles.Default].
 *
 * @since 1.0.0
 */
public val TableStyleKey: StyleKey<TableStyles> = StyleKey(name = "table") { _ -> TableStyles.Default }

/**
 * Returns the style of a [Table] in the theme of the composition, resolved by the [TableStyles] under [TableStyleKey].
 *
 * @return the style of the table.
 * @since 1.0.0
 */
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
