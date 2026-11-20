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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.button.ButtonStyle
import com.davils.kiro.ui.components.calendar.CalendarStyle
import com.davils.kiro.ui.components.popover.PopoverStyle
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The look of a [DatePicker]: its button, the popover and the calendar inside it.
 *
 * @property trigger the style of the button that opens the calendar.
 * @property triggerWidth the width of the button.
 * @property icon the icon at the start of the button.
 * @property placeholderColor the color of the placeholder while no date is chosen.
 * @property popover the style of the popover around the calendar.
 * @property calendar the style of the calendar.
 * @since 1.0.0
 */
@Immutable
public class DatePickerStyle(
    public val trigger: ButtonStyle,
    public val triggerWidth: Dp,
    public val icon: IconGraphic,
    public val placeholderColor: Color,
    public val popover: PopoverStyle,
    public val calendar: CalendarStyle,
) {
    /**
     * Returns a copy of this [DatePickerStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        trigger: ButtonStyle = this.trigger,
        triggerWidth: Dp = this.triggerWidth,
        icon: IconGraphic = this.icon,
        placeholderColor: Color = this.placeholderColor,
        popover: PopoverStyle = this.popover,
        calendar: CalendarStyle = this.calendar,
    ): DatePickerStyle = DatePickerStyle(
        trigger = trigger,
        triggerWidth = triggerWidth,
        icon = icon,
        placeholderColor = placeholderColor,
        popover = popover,
        calendar = calendar,
    )

    override fun equals(other: Any?): Boolean = other is DatePickerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DatePickerStyle(" +
        "trigger=$trigger, " +
        "triggerWidth=$triggerWidth, " +
        "icon=$icon, " +
        "placeholderColor=$placeholderColor, " +
        "popover=$popover, " +
        "calendar=$calendar)"

    private fun values(): List<Any?> = listOf(
        trigger,
        triggerWidth,
        icon,
        placeholderColor,
        popover,
        calendar,
    )
}
