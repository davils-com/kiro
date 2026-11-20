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

package com.davils.kiro.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.calendar.Calendar
import com.davils.kiro.ui.components.calendar.CalendarOptions
import com.davils.kiro.ui.components.calendar.CalendarSelection
import com.davils.kiro.ui.components.calendar.calendarStyle
import com.davils.kiro.ui.components.calendar.rememberCalendarState
import com.davils.kiro.ui.components.checkbox.Checkbox
import com.davils.kiro.ui.components.checkbox.checkboxStyle
import com.davils.kiro.ui.components.field.Field
import com.davils.kiro.ui.components.field.FieldContent
import com.davils.kiro.ui.components.field.FieldDescription
import com.davils.kiro.ui.components.field.FieldError
import com.davils.kiro.ui.components.field.FieldGroup
import com.davils.kiro.ui.components.field.FieldLabel
import com.davils.kiro.ui.components.field.FieldLegend
import com.davils.kiro.ui.components.field.FieldLegendVariant
import com.davils.kiro.ui.components.field.FieldOrientation
import com.davils.kiro.ui.components.field.FieldSeparator
import com.davils.kiro.ui.components.field.FieldSet
import com.davils.kiro.ui.components.field.FieldTitle
import com.davils.kiro.ui.components.field.fieldStyle
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.input.Input
import com.davils.kiro.ui.components.input.InputOptions
import com.davils.kiro.ui.components.input.Textarea
import com.davils.kiro.ui.components.input.inputStyle
import com.davils.kiro.ui.components.input.textareaStyle
import com.davils.kiro.ui.components.inputgroup.InputGroup
import com.davils.kiro.ui.components.inputgroup.InputGroupAlign
import com.davils.kiro.ui.components.inputgroup.InputGroupButton
import com.davils.kiro.ui.components.inputgroup.InputGroupButtonSize
import com.davils.kiro.ui.components.inputgroup.InputGroupText
import com.davils.kiro.ui.components.inputgroup.inputGroupButtonStyle
import com.davils.kiro.ui.components.inputgroup.inputGroupStyle
import com.davils.kiro.ui.components.inputotp.InputOtp
import com.davils.kiro.ui.components.inputotp.inputOtpStyle
import com.davils.kiro.ui.components.nativeselect.NativeSelect
import com.davils.kiro.ui.components.nativeselect.NativeSelectSize
import com.davils.kiro.ui.components.nativeselect.nativeSelectStyle
import com.davils.kiro.ui.components.radiogroup.RadioGroup
import com.davils.kiro.ui.components.radiogroup.RadioGroupItem
import com.davils.kiro.ui.components.radiogroup.radioStyle
import com.davils.kiro.ui.components.slider.RangeSlider
import com.davils.kiro.ui.components.slider.Slider
import com.davils.kiro.ui.components.slider.sliderStyle
import com.davils.kiro.ui.components.switch.Switch
import com.davils.kiro.ui.components.switch.SwitchSize
import com.davils.kiro.ui.components.switch.switchStyle
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.components.toggle.Toggle
import com.davils.kiro.ui.components.toggle.ToggleSize
import com.davils.kiro.ui.components.toggle.ToggleVariant
import com.davils.kiro.ui.components.toggle.toggleStyle
import com.davils.kiro.ui.components.togglegroup.ToggleGroup
import com.davils.kiro.ui.components.togglegroup.toggleGroupStyle
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth

private const val ROUNDS: Int = 3
private const val FIXED: String = "fixed"
private const val SLIDER_MAX: Float = 10f
private const val OTP_LENGTH: Int = 4

@Composable
private fun Texts(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Input(
        value = label,
        onValueChange = {},
        modifier = Modifier,
        placeholder = label,
        enabled = !isOdd,
        isInvalid = isOdd,
        options = InputOptions(isSecure = isOdd),
        style = inputStyle(),
    )
    Textarea(
        value = label,
        onValueChange = {},
        modifier = Modifier,
        placeholder = label,
        enabled = !isOdd,
        isInvalid = isOdd,
        options = InputOptions(),
        style = textareaStyle(),
    )
    InputGroup(modifier = Modifier, enabled = !isOdd, isInvalid = isOdd, style = inputGroupStyle()) {
        addon(align = InputGroupAlign.InlineStart) {
            InputGroupText(modifier = Modifier, style = inputGroupStyle()) { Text(label) }
        }
        input(value = label, onValueChange = {}, placeholder = label, options = InputOptions())
        addon(align = InputGroupAlign.InlineEnd) {
            InputGroupButton(
                onClick = {},
                modifier = Modifier,
                variant = ButtonVariant.Outline,
                size = InputGroupButtonSize.Sm,
                enabled = !isOdd,
                style = inputGroupButtonStyle(ButtonVariant.Outline, InputGroupButtonSize.Sm),
            ) {
                Text(label)
            }
        }
    }
    InputOtp(
        value = label.take(2),
        onValueChange = {},
        length = OTP_LENGTH,
        modifier = Modifier,
        enabled = !isOdd,
        isInvalid = isOdd,
        style = inputOtpStyle(),
        layout = { group(0 until OTP_LENGTH) },
    )
}

@Composable
private fun Choices(label: String, isOdd: Boolean, tick: Int) {
    check(tick >= 0)
    Checkbox(
        checked = isOdd,
        onCheckedChange = {},
        modifier = Modifier,
        enabled = !isOdd,
        isInvalid = isOdd,
        isIndeterminate = isOdd,
        style = checkboxStyle(),
        interactionSource = null,
    )
    RadioGroup(
        selected = label,
        onSelectedChange = {},
        modifier = Modifier,
        enabled = !isOdd,
        isInvalid = isOdd,
        orientation = Orientation.Horizontal,
        style = radioStyle(),
    ) {
        RadioGroupItem(value = label, modifier = Modifier, enabled = !isOdd, style = radioStyle())
    }
    Switch(
        checked = isOdd,
        onCheckedChange = {},
        modifier = Modifier,
        size = SwitchSize.Sm,
        enabled = !isOdd,
        isInvalid = isOdd,
        style = switchStyle(SwitchSize.Sm),
        interactionSource = null,
    )
    Toggle(
        pressed = isOdd,
        onPressedChange = {},
        modifier = Modifier,
        variant = ToggleVariant.Outline,
        size = ToggleSize.Lg,
        enabled = !isOdd,
        style = toggleStyle(ToggleVariant.Outline, ToggleSize.Lg),
    ) {
        Text(label)
    }
    ToggleGroup(
        selected = setOf(label),
        onSelectedChange = {},
        modifier = Modifier,
        isMultiple = isOdd,
        orientation = Orientation.Vertical,
        style = toggleGroupStyle(ToggleVariant.Outline, ToggleSize.Sm),
    ) {
        item(value = label, enabled = !isOdd) { Text(label) }
    }
}

@Composable
private fun Ranges(label: String, isOdd: Boolean, value: Float, tick: Int) {
    check(tick >= 0)
    Slider(
        value = value,
        onValueChange = {},
        modifier = Modifier,
        valueRange = 0f..SLIDER_MAX,
        step = 0f,
        enabled = !isOdd,
        orientation = Orientation.Horizontal,
        style = sliderStyle(),
    )
    RangeSlider(
        value = 0f..value,
        onValueChange = {},
        modifier = Modifier,
        valueRange = 0f..SLIDER_MAX,
        step = 1f,
        enabled = !isOdd,
        orientation = Orientation.Vertical,
        style = sliderStyle(),
    )
    NativeSelect(
        selected = label,
        onSelectedChange = {},
        modifier = Modifier,
        size = NativeSelectSize.Sm,
        enabled = !isOdd,
        isInvalid = isOdd,
        style = nativeSelectStyle(NativeSelectSize.Sm),
    ) {
        option(value = label, label = label, enabled = true)
    }
}

@Composable
private fun Fields(label: String, isOdd: Boolean, day: Int, tick: Int) {
    check(tick >= 0)
    FieldSet(modifier = Modifier, style = fieldStyle()) {
        FieldLegend(text = label, modifier = Modifier, variant = FieldLegendVariant.Label, style = fieldStyle())
        FieldGroup(modifier = Modifier, style = fieldStyle()) {
            Field(
                modifier = Modifier,
                orientation = FieldOrientation.Horizontal,
                isInvalid = isOdd,
                isDisabled = isOdd,
                style = fieldStyle(),
            ) {
                FieldLabel(text = label, modifier = Modifier, style = fieldStyle())
                FieldContent(modifier = Modifier, style = fieldStyle()) {
                    FieldTitle(text = label, modifier = Modifier, style = fieldStyle())
                    FieldDescription(text = label, modifier = Modifier, style = fieldStyle())
                    FieldError(messages = listOf(label), modifier = Modifier, style = fieldStyle())
                }
            }
            FieldSeparator(modifier = Modifier, style = fieldStyle()) { Text(label) }
        }
    }
    Calendar(
        selection = CalendarSelection.Single(LocalDate(year = 2026, month = 11, day = day)),
        onSelectionChange = {},
        modifier = Modifier,
        state = rememberCalendarState(initialMonth = YearMonth(year = 2026, month = 11)),
        options = CalendarOptions(isWeekNumberShown = isOdd),
        style = calendarStyle(),
    )
}

@OptIn(ExperimentalTestApi::class)
class FormRecompositionTest : FunSpec({
    context("form components") {
        test("take every argument explicitly and recompose with changed and unchanged arguments") {
            var round by mutableIntStateOf(0)
            var rendered = -1

            runComposeUiTest {
                setContent {
                    Themed {
                        Column {
                            val isOdd = round % 2 == 1
                            val label = "Round $round"
                            Texts(label = label, isOdd = isOdd, tick = round)
                            Texts(label = FIXED, isOdd = false, tick = round)
                            Choices(label = label, isOdd = isOdd, tick = round)
                            Choices(label = FIXED, isOdd = false, tick = round)
                            Ranges(label = label, isOdd = isOdd, value = round.toFloat(), tick = round)
                            Ranges(label = FIXED, isOdd = false, value = 1f, tick = round)
                            Fields(label = label, isOdd = isOdd, day = round + 1, tick = round)
                            Fields(label = FIXED, isOdd = false, day = 1, tick = round)
                            val stable = remember { FIXED.uppercase() }
                            val isNever = round < 0
                            Texts(label = stable, isOdd = isNever, tick = round)
                            Choices(label = stable, isOdd = isNever, tick = round)
                            Ranges(label = stable, isOdd = isNever, value = stable.length.toFloat(), tick = round)
                            Fields(label = stable, isOdd = isNever, day = stable.length, tick = round)
                            rendered = round
                        }
                    }
                }
                repeat(ROUNDS) {
                    round++
                    waitForIdle()
                }
            }

            rendered shouldBe ROUNDS
        }
    }
})
