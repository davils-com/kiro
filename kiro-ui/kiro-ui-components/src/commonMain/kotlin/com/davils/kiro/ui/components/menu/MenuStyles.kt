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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.overlay.popoverPanel
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Check
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide
import kotlin.time.Duration.Companion.milliseconds

private const val DESTRUCTIVE_LIGHT: Float = 0.1f
private const val DESTRUCTIVE_DARK: Float = 0.2f
private const val WIDEST_TRACKING: Float = 0.1f
private const val DISABLED_ALPHA: Float = 0.5f

private const val SUB_OPEN_MILLIS: Int = 100
private const val SUB_CLOSE_MILLIS: Int = 300

public fun interface MenuStyles {
    public fun resolve(theme: Theme): MenuStyle

    public companion object {
        public val Default: MenuStyles = MenuStyles(::defaultMenuStyle)
    }
}

public val MenuStyleKey: StyleKey<MenuStyles> = StyleKey(name = "menu") { _ -> MenuStyles.Default }

@Composable
@ReadOnlyComposable
public fun menuStyle(): MenuStyle = MenuStyleKey.current.resolve(theme)

internal fun defaultMenuStyle(theme: Theme): MenuStyle {
    val colors = theme.colors
    val scale = theme.typography.scale
    val panel = theme.popoverPanel(padding = 4.dp)
    return MenuStyle(
        panel = panel,
        subPanel = panel.copy(shadow = theme.shadows.lg),
        minWidth = 128.dp,
        subMinWidth = 96.dp,
        itemShape = RoundedCornerShape(theme.radii.md),
        itemHorizontalPadding = 6.dp,
        itemVerticalPadding = 4.dp,
        insetPadding = 28.dp,
        indicatorPadding = 32.dp,
        indicatorEnd = 8.dp,
        gap = 6.dp,
        textStyle = theme.textStyleOf(size = scale.sm),
        iconSize = 16.dp,
        highlight = colors.accent,
        highlightContent = colors.accentForeground,
        destructive = colors.destructive,
        destructiveHighlight = colors.destructive.copy(
            alpha = theme.darkAware(light = DESTRUCTIVE_LIGHT, dark = DESTRUCTIVE_DARK),
        ),
        labelStyle = theme.textStyleOf(size = scale.xs, weight = FontWeight.Medium),
        labelColor = colors.mutedForeground,
        separatorColor = colors.border,
        separatorSpacing = 4.dp,
        shortcutStyle = theme.textStyleOf(size = scale.xs).copy(letterSpacing = WIDEST_TRACKING.em),
        shortcutColor = colors.mutedForeground,
        checkIcon = Lucide.Check,
        subIcon = Lucide.ChevronRight,
        subOpenDelay = SUB_OPEN_MILLIS.milliseconds,
        subCloseDelay = SUB_CLOSE_MILLIS.milliseconds,
        disabledAlpha = DISABLED_ALPHA,
    )
}
