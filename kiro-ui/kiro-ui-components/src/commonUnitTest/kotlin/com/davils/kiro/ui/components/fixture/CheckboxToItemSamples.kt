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

package com.davils.kiro.ui.components.fixture

import androidx.compose.animation.EnterTransition
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.checkbox.defaultCheckboxStyle
import com.davils.kiro.ui.components.codeblock.CodeBlockSize
import com.davils.kiro.ui.components.codeblock.CodeBlockVariant
import com.davils.kiro.ui.components.codeblock.defaultCodeBlockStyle
import com.davils.kiro.ui.components.collapsible.defaultCollapsibleStyle
import com.davils.kiro.ui.components.combobox.defaultComboboxStyle
import com.davils.kiro.ui.components.command.defaultCommandStyle
import com.davils.kiro.ui.components.datatable.DataTableColumn
import com.davils.kiro.ui.components.datatable.DataTableSort
import com.davils.kiro.ui.components.datatable.DataTableView
import com.davils.kiro.ui.components.datatable.defaultDataTableStyle
import com.davils.kiro.ui.components.datepicker.defaultDatePickerStyle
import com.davils.kiro.ui.components.dialog.defaultDialogStyle
import com.davils.kiro.ui.components.drawer.defaultDrawerStyle
import com.davils.kiro.ui.components.empty.defaultEmptyStyle
import com.davils.kiro.ui.components.field.defaultFieldStyle
import com.davils.kiro.ui.components.hovercard.defaultHoverCardStyle
import com.davils.kiro.ui.components.input.InputOptions
import com.davils.kiro.ui.components.input.defaultInputStyle
import com.davils.kiro.ui.components.inputgroup.defaultInputGroupStyle
import com.davils.kiro.ui.components.inputotp.defaultInputOtpStyle
import com.davils.kiro.ui.components.item.ItemSize
import com.davils.kiro.ui.components.item.ItemVariant
import com.davils.kiro.ui.components.item.defaultItemStyle
import com.davils.kiro.ui.core.Theme

private val light: Theme = Theme.light()

internal fun checkboxToItemSamples(): List<ValueSample> = contentSamples() +
    dataTableSamples() +
    inputSamples() +
    inputGroupSamples() +
    overlaySamples()

private fun contentSamples(): List<ValueSample> {
    val checkbox = defaultCheckboxStyle(light)
    val codeBlock = defaultCodeBlockStyle(
        theme = light,
        variant = CodeBlockVariant.Default,
        size = CodeBlockSize.Default,
    )
    val collapsible = defaultCollapsibleStyle(light)
    val empty = defaultEmptyStyle(light)
    val field = defaultFieldStyle(light)
    val item = defaultItemStyle(theme = light, variant = ItemVariant.Default, size = ItemSize.Default)
    return listOf(
        ValueSample(
            name = "CheckboxStyle",
            value = checkbox,
            copy = checkbox.copy(),
            changed = checkbox.copy(size = 0.dp),
        ),
        ValueSample(
            name = "CodeBlockStyle",
            value = codeBlock,
            copy = codeBlock.copy(),
            changed = codeBlock.copy(container = Color.Red),
        ),
        ValueSample(
            name = "CollapsibleStyle",
            value = collapsible,
            copy = collapsible.copy(),
            changed = collapsible.copy(enter = EnterTransition.None),
        ),
        ValueSample(
            name = "EmptyStyle",
            value = empty,
            copy = empty.copy(),
            changed = empty.copy(iconMediaContainer = Color.Red),
        ),
        ValueSample(
            name = "FieldStyle",
            value = field,
            copy = field.copy(),
            changed = field.copy(separatorColor = Color.Red),
        ),
        ValueSample(
            name = "ItemStyle",
            value = item,
            copy = item.copy(),
            changed = item.copy(descriptionColor = Color.Red),
        ),
    )
}

private fun dataTableSamples(): List<ValueSample> {
    val column = DataTableColumn<String>(header = "Name") {}
    val sort = DataTableSort(column = column, isDescending = false)
    val view = DataTableView(
        rows = listOf(column.header),
        columns = listOf(column),
        rowCount = 1,
        page = 0,
        pageCount = 1,
    )
    val style = defaultDataTableStyle(light)
    return listOf(
        ValueSample(
            name = "DataTableColumn",
            value = column,
            copy = column.copy(),
            changed = column.copy(isHideable = false),
        ),
        ValueSample(
            name = "DataTableSort",
            value = sort,
            copy = sort.copy(),
            changed = sort.copy(isDescending = true),
        ),
        ValueSample(
            name = "DataTableView",
            value = view,
            copy = view.copy(),
            changed = view.copy(rowCount = 0),
        ),
        ValueSample(
            name = "DataTableStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(emptyHeight = 0.dp),
        ),
    )
}

private fun inputSamples(): List<ValueSample> {
    val options = InputOptions()
    val input = defaultInputStyle(light)
    val text = input.text
    val otp = defaultInputOtpStyle(light)
    val caret = otp.caret
    return listOf(
        ValueSample(
            name = "InputOptions",
            value = options,
            copy = options.copy(),
            changed = options.copy(isSecure = true),
        ),
        ValueSample(
            name = "InputStyle",
            value = input,
            copy = input.copy(),
            changed = input.copy(maxHeight = 0.dp),
        ),
        ValueSample(
            name = "InputTextStyle",
            value = text,
            copy = text.copy(),
            changed = text.copy(cursorColor = Color.Red),
        ),
        ValueSample(
            name = "InputOtpStyle",
            value = otp,
            copy = otp.copy(),
            changed = otp.copy(invalidRing = Color.Red),
        ),
        ValueSample(
            name = "InputOtpCaret",
            value = caret,
            copy = caret.copy(),
            changed = caret.copy(color = Color.Red),
        ),
    )
}

private fun inputGroupSamples(): List<ValueSample> {
    val group = defaultInputGroupStyle(light)
    val control = group.control
    val addon = group.addon
    return listOf(
        ValueSample(
            name = "InputGroupStyle",
            value = group,
            copy = group.copy(),
            changed = group.copy(minHeight = 0.dp),
        ),
        ValueSample(
            name = "InputGroupControlStyle",
            value = control,
            copy = control.copy(),
            changed = control.copy(textareaMinHeight = 0.dp),
        ),
        ValueSample(
            name = "InputGroupAddonStyle",
            value = addon,
            copy = addon.copy(),
            changed = addon.copy(color = Color.Red),
        ),
    )
}

private fun overlaySamples(): List<ValueSample> {
    val combobox = defaultComboboxStyle(light)
    val command = defaultCommandStyle(light)
    val datePicker = defaultDatePickerStyle(light)
    val dialog = defaultDialogStyle(light)
    val drawer = defaultDrawerStyle(light)
    val hoverCard = defaultHoverCardStyle(light)
    return listOf(
        ValueSample(
            name = "ComboboxStyle",
            value = combobox,
            copy = combobox.copy(),
            changed = combobox.copy(emptyColor = Color.Red),
        ),
        ValueSample(
            name = "CommandStyle",
            value = command,
            copy = command.copy(),
            changed = command.copy(scrim = Color.Red),
        ),
        ValueSample(
            name = "DatePickerStyle",
            value = datePicker,
            copy = datePicker.copy(),
            changed = datePicker.copy(placeholderColor = Color.Red),
        ),
        ValueSample(
            name = "DialogStyle",
            value = dialog,
            copy = dialog.copy(),
            changed = dialog.copy(scrim = Color.Red),
        ),
        ValueSample(
            name = "DrawerStyle",
            value = drawer,
            copy = drawer.copy(),
            changed = drawer.copy(handleColor = Color.Red),
        ),
        ValueSample(
            name = "HoverCardStyle",
            value = hoverCard,
            copy = hoverCard.copy(),
            changed = hoverCard.copy(width = 0.dp),
        ),
    )
}
