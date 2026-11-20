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

package com.davils.kiro.ui.components.select

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.menu.defaultMenuStyle
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.ChevronDown
import com.davils.kiro.ui.icons.lucide.Lucide

private const val DARK_FILL: Float = 0.3f
private const val DARK_HOVER: Float = 0.5f
private const val DISABLED_ALPHA: Float = 0.5f

private val known: Set<SelectSize> = setOf(SelectSize.Default, SelectSize.Sm)

public fun interface SelectStyles {
    public fun resolve(theme: Theme, size: SelectSize): SelectStyle

    public companion object {
        public val Default: SelectStyles = SelectStyles(::defaultSelectStyle)
    }
}

public val SelectStyleKey: StyleKey<SelectStyles> = StyleKey(name = "select") { _ -> SelectStyles.Default }

@Composable
@ReadOnlyComposable
public fun selectStyle(size: SelectSize): SelectStyle = SelectStyleKey.current.resolve(theme, size)

internal fun defaultSelectStyle(theme: Theme, size: SelectSize): SelectStyle {
    require(size in known) { "No default style for the select size $size." }
    val isSmall = size == SelectSize.Sm
    val colors = theme.colors
    val menu = defaultMenuStyle(theme)
    val fill = colors.input.copy(alpha = theme.darkAware(light = 0f, dark = DARK_FILL))
    return SelectStyle(
        height = 28.dp.takeIf { isSmall } ?: 32.dp,
        shape = RoundedCornerShape(minOf(theme.radii.md, 10.dp).takeIf { isSmall } ?: theme.radii.lg),
        startPadding = 10.dp,
        endPadding = 8.dp,
        gap = 6.dp,
        textStyle = theme.textStyleOf(size = theme.typography.scale.sm),
        placeholderColor = colors.mutedForeground,
        colors = theme.controlColors(
            container = StateValue(
                default = fill,
                hovered = colors.input.copy(alpha = DARK_HOVER).takeIf { theme.isDark },
            ),
            content = StateValue(default = colors.foreground),
            border = colors.input,
        ),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        icon = Lucide.ChevronDown,
        iconSize = 16.dp,
        iconColor = colors.mutedForeground,
        menu = menu.copy(labelStyle = theme.textStyleOf(size = theme.typography.scale.xs)),
        minWidth = 144.dp,
        disabledAlpha = DISABLED_ALPHA,
        colorAnimation = theme.colorTransition(),
    )
}
