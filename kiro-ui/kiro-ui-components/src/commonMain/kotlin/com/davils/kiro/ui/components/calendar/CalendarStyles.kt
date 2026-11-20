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

package com.davils.kiro.ui.components.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.defaultButtonStyle
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.textStyleOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.core.typography.TextSize
import com.davils.kiro.ui.icons.lucide.ChevronLeft
import com.davils.kiro.ui.icons.lucide.ChevronRight
import com.davils.kiro.ui.icons.lucide.Lucide

private const val HALF: Float = 0.5f

public fun interface CalendarStyles {
    public fun resolve(theme: Theme): CalendarStyle

    public companion object {
        public val Default: CalendarStyles = CalendarStyles(::defaultCalendarStyle)
    }
}

@Composable
@ReadOnlyComposable
public fun calendarStyle(): CalendarStyle = CalendarStyleKey.current.resolve(theme)

internal fun defaultCalendarStyle(theme: Theme): CalendarStyle {
    val colors = theme.colors
    val small = TextSize(fontSize = 12.8.sp, lineHeight = 16.sp)
    val muted = colors.muted
    val navigation = defaultButtonStyle(theme = theme, variant = ButtonVariant.Ghost, size = ButtonSize.Icon)
    return CalendarStyle(
        container = colors.background,
        padding = 8.dp,
        cellSize = 28.dp,
        cellRadius = theme.radii.md,
        sectionGap = 16.dp,
        weekGap = 8.dp,
        captionStyle = theme.textStyleOf(size = theme.typography.scale.sm, weight = FontWeight.Medium),
        weekdayStyle = theme.textStyleOf(size = small),
        weekdayColor = colors.mutedForeground,
        day = CalendarDayStyle(
            textStyle = theme.textStyleOf(size = theme.typography.scale.sm, lineHeight = theme.typography.leading.none),
            container = StateValue(
                default = Color.Transparent,
                hovered = muted.copy(alpha = theme.darkAware(light = 1f, dark = HALF)),
            ),
            content = colors.foreground,
            selectedContainer = colors.primary,
            selectedContent = colors.primaryForeground,
            todayContainer = muted,
            rangeContainer = muted,
            rangeContent = colors.foreground,
            outsideContent = colors.mutedForeground.copy(alpha = theme.darkAware(light = HALF, dark = 1f)),
            focusBorder = colors.ring,
            focusRing = colors.ring.copy(alpha = HALF),
            disabledAlpha = HALF,
        ),
        navButton = navigation.copy(height = 28.dp, minWidth = 28.dp),
        previousIcon = Lucide.ChevronLeft,
        nextIcon = Lucide.ChevronRight,
        ringWidth = theme.focusRing.width,
        colorAnimation = theme.colorTransition(),
    )
}

public val CalendarStyleKey: StyleKey<CalendarStyles> = StyleKey(name = "calendar") { _ -> CalendarStyles.Default }
