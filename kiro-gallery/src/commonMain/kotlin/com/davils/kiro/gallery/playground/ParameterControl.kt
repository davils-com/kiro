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

package com.davils.kiro.gallery.playground

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.davils.kiro.gallery.theme
import com.davils.kiro.ui.components.label.Label
import com.davils.kiro.ui.components.select.Select
import com.davils.kiro.ui.components.slider.Slider
import com.davils.kiro.ui.components.switch.Switch
import com.davils.kiro.ui.components.text.Text

private const val CUSTOM: String = "Custom"

@Composable
internal fun ParameterControl(
    parameter: Parameter<*>,
    setting: Setting,
    modifier: Modifier = Modifier,
    onChange: (Setting) -> Unit,
) {
    when (val control = parameter.control) {
        is Control.Slider -> LabeledSlider(
            name = parameter.name,
            value = (setting as? Setting.Amount)?.value ?: control.range.start,
            valueText = parameter.valueCode(setting),
            control = control,
            modifier = modifier,
        ) { amount -> onChange(Setting.Amount(value = amount)) }

        Control.Toggle -> ToggleControl(
            name = parameter.name,
            isOn = (setting as? Setting.Flag)?.isOn == true,
            modifier = modifier,
        ) { isOn -> onChange(Setting.Flag(isOn = isOn)) }

        is Control.Menu -> MenuControl(
            name = parameter.name,
            index = (setting as? Setting.Pick)?.index ?: -1,
            labels = control.labels,
            modifier = modifier,
        ) { index -> onChange(Setting.Pick(index = index)) }
    }
}

@Composable
internal fun LabeledSlider(
    name: String,
    value: Float,
    valueText: String,
    control: Control.Slider,
    modifier: Modifier = Modifier,
    onChange: (Float) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Label(text = name, modifier = Modifier.weight(1f))
            Text(text = valueText, color = theme.colors.mutedForeground, maxLines = 1)
        }
        Slider(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = name },
            valueRange = control.range,
            step = control.step,
        )
    }
}

@Composable
private fun ToggleControl(name: String, isOn: Boolean, modifier: Modifier, onChange: (Boolean) -> Unit) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Label(text = name, modifier = Modifier.weight(1f))
        Switch(
            checked = isOn,
            onCheckedChange = onChange,
            modifier = Modifier.semantics { contentDescription = name },
        )
    }
}

@Composable
private fun MenuControl(name: String, index: Int, labels: List<String>, modifier: Modifier, onChange: (Int) -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Label(text = name)
        Select(
            selected = index.takeIf { chosen -> chosen >= 0 },
            onSelectedChange = onChange,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = name },
        ) {
            placeholder(CUSTOM)
            labels.forEachIndexed { position, label -> option(value = position, label = label) }
        }
    }
}
