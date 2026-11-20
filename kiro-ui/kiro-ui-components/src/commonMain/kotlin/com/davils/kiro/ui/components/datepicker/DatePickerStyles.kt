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

package com.davils.kiro.ui.components.datepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.defaultButtonStyle
import com.davils.kiro.ui.components.calendar.defaultCalendarStyle
import com.davils.kiro.ui.components.popover.defaultPopoverStyle
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.icons.lucide.Calendar
import com.davils.kiro.ui.icons.lucide.Lucide

/**
 * Resolves the style of a [DatePicker] for a theme.
 *
 * Replace it in the components of a theme under [DatePickerStyleKey] to change the style of every date picker.
 *
 * @since 1.0.0
 */
public fun interface DatePickerStyles {
    /**
     * Returns the style of a [DatePicker] in a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the date picker.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): DatePickerStyle

    /**
     * Holds the default [DatePickerStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [DatePicker], derived from the tokens of the theme.
         *
         * @since 1.0.0
         */
        public val Default: DatePickerStyles = DatePickerStyles(::defaultDatePickerStyle)
    }
}

/**
 * Returns the style of a [DatePicker] in the theme of the composition, resolved by the [DatePickerStyles] under
 * [DatePickerStyleKey].
 *
 * @return the style of the date picker.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun datePickerStyle(): DatePickerStyle = DatePickerStyleKey.current.resolve(theme)

internal fun defaultDatePickerStyle(theme: Theme): DatePickerStyle {
    val button = defaultButtonStyle(theme = theme, variant = ButtonVariant.Outline, size = ButtonSize.Default)
    val popover = defaultPopoverStyle(theme)
    return DatePickerStyle(
        trigger = button.copy(textStyle = button.textStyle.copy(fontWeight = FontWeight.Normal)),
        triggerWidth = 240.dp,
        icon = Lucide.Calendar,
        placeholderColor = theme.colors.mutedForeground,
        popover = popover.copy(width = Dp.Unspecified, panel = popover.panel.copy(padding = 0.dp)),
        calendar = defaultCalendarStyle(theme),
    )
}

/**
 * The key of the [DatePickerStyles] of a [DatePicker] in the components of a theme; its default is
 * [DatePickerStyles.Default].
 *
 * @since 1.0.0
 */
public val DatePickerStyleKey: StyleKey<DatePickerStyles> = StyleKey(
    name = "date-picker",
) { _ -> DatePickerStyles.Default }
