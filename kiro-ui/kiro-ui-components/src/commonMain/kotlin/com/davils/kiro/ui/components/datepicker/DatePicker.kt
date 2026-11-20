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

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.davils.kiro.ui.components.IconPlacement
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.calendar.Calendar
import com.davils.kiro.ui.components.calendar.CalendarOptions
import com.davils.kiro.ui.components.calendar.CalendarSelection
import com.davils.kiro.ui.components.calendar.settled
import com.davils.kiro.ui.components.popover.Popover
import com.davils.kiro.ui.components.primitive.overlay.Align
import com.davils.kiro.ui.components.primitive.overlay.Placement
import com.davils.kiro.ui.components.text.Text
import kotlinx.datetime.LocalDate

@Composable
public fun DatePicker(
    date: LocalDate?,
    onDateChange: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = Messages.DatePicker.placeholder,
    options: CalendarOptions = CalendarOptions(),
    style: DatePickerStyle = datePickerStyle(),
) {
    val formatter = options.settled().formatter
    var isOpen by remember { mutableStateOf(false) }
    Popover(
        expanded = isOpen,
        onExpandedChange = { expanded -> isOpen = expanded },
        trigger = {
            Button(
                onClick = { isOpen = !isOpen },
                modifier = modifier.width(style.triggerWidth),
                variant = ButtonVariant.Outline,
                style = style.trigger,
            ) {
                Icon(icon = style.icon, placement = IconPlacement.Leading)
                Text(
                    text = date?.let(formatter::date) ?: placeholder,
                    color = style.placeholderColor.takeIf { date == null } ?: style.trigger.colors.content.default,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        },
        placement = Placement(align = Align.Center),
        style = style.popover,
    ) {
        Calendar(
            selection = CalendarSelection.Single(date),
            onSelectionChange = { selection ->
                onDateChange(selection.date)
                isOpen = false
            },
            options = options,
            style = style.calendar,
        )
    }
}
