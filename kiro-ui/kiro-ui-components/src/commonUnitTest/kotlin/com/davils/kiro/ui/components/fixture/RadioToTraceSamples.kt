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

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.components.radiogroup.RadioStyles
import com.davils.kiro.ui.components.resizable.ResizableStyles
import com.davils.kiro.ui.components.scrollarea.ScrollAreaStyles
import com.davils.kiro.ui.components.select.SelectSize
import com.davils.kiro.ui.components.select.SelectStyles
import com.davils.kiro.ui.components.separator.SeparatorStyles
import com.davils.kiro.ui.components.sheet.SheetStyles
import com.davils.kiro.ui.components.sidebar.SidebarStyles
import com.davils.kiro.ui.components.skeleton.SkeletonStyles
import com.davils.kiro.ui.components.slider.SliderStyles
import com.davils.kiro.ui.components.spinner.SpinnerStyles
import com.davils.kiro.ui.components.switch.SwitchSize
import com.davils.kiro.ui.components.switch.SwitchStyles
import com.davils.kiro.ui.components.table.TableStyles
import com.davils.kiro.ui.components.tabs.TabsStyles
import com.davils.kiro.ui.components.tabs.TabsVariant
import com.davils.kiro.ui.components.toast.Toast
import com.davils.kiro.ui.components.toast.ToastAction
import com.davils.kiro.ui.components.toast.ToastId
import com.davils.kiro.ui.components.toast.ToastProgress
import com.davils.kiro.ui.components.toast.ToastStyles
import com.davils.kiro.ui.components.toast.ToastType
import com.davils.kiro.ui.components.toggle.ToggleSize
import com.davils.kiro.ui.components.toggle.ToggleStyles
import com.davils.kiro.ui.components.toggle.ToggleVariant
import com.davils.kiro.ui.components.togglegroup.ToggleGroupStyles
import com.davils.kiro.ui.components.tooltip.TooltipStyles
import com.davils.kiro.ui.components.trace.TraceStyles
import com.davils.kiro.ui.core.Theme

private val defaults: Theme = Theme.light()

internal fun radioToTraceSamples(): List<ValueSample> = controlSamples() +
    toggleSamples() +
    layoutSamples() +
    toastSamples() +
    feedbackSamples()

private fun controlSamples(): List<ValueSample> {
    val radio = RadioStyles.Default.resolve(theme = defaults)
    val select = SelectStyles.Default.resolve(theme = defaults, size = SelectSize.Default)
    val slider = SliderStyles.Default.resolve(theme = defaults)
    val switch = SwitchStyles.Default.resolve(theme = defaults, size = SwitchSize.Default)
    val thumb = switch.thumb
    return listOf(
        ValueSample(
            name = "RadioStyle",
            value = radio,
            copy = radio.copy(),
            changed = radio.copy(dotSize = radio.size),
        ),
        ValueSample(
            name = "SelectStyle",
            value = select,
            copy = select.copy(),
            changed = select.copy(placeholderColor = Color.Red),
        ),
        ValueSample(
            name = "SliderStyle",
            value = slider,
            copy = slider.copy(),
            changed = slider.copy(trackColor = Color.Red),
        ),
        ValueSample(
            name = "SwitchStyle",
            value = switch,
            copy = switch.copy(),
            changed = switch.copy(width = switch.height),
        ),
        ValueSample(
            name = "SwitchThumb",
            value = thumb,
            copy = thumb.copy(),
            changed = thumb.copy(color = Color.Red),
        ),
    )
}

private fun toggleSamples(): List<ValueSample> {
    val toggle = ToggleStyles.Default.resolve(
        theme = defaults,
        variant = ToggleVariant.Default,
        size = ToggleSize.Default,
    )
    val group = ToggleGroupStyles.Default.resolve(
        theme = defaults,
        variant = ToggleVariant.Outline,
        size = ToggleSize.Default,
    )
    return listOf(
        ValueSample(
            name = "ToggleStyle",
            value = toggle,
            copy = toggle.copy(),
            changed = toggle.copy(horizontalPadding = toggle.gap),
        ),
        ValueSample(
            name = "ToggleGroupStyle",
            value = group,
            copy = group.copy(),
            changed = group.copy(joinedIconEdgePadding = group.spacing),
        ),
    )
}

private fun layoutSamples(): List<ValueSample> {
    val resizable = ResizableStyles.Default.resolve(theme = defaults)
    val scrollArea = ScrollAreaStyles.Default.resolve(theme = defaults)
    val separator = SeparatorStyles.Default.resolve(theme = defaults)
    val sheet = SheetStyles.Default.resolve(theme = defaults)
    val sidebar = SidebarStyles.Default.resolve(theme = defaults)
    val table = TableStyles.Default.resolve(theme = defaults)
    val tabs = TabsStyles.Default.resolve(theme = defaults, variant = TabsVariant.Line)
    val indicator = requireNotNull(tabs.indicator)
    return listOf(
        ValueSample(
            name = "ResizableStyle",
            value = resizable,
            copy = resizable.copy(),
            changed = resizable.copy(color = Color.Red),
        ),
        ValueSample(
            name = "ScrollAreaStyle",
            value = scrollArea,
            copy = scrollArea.copy(),
            changed = scrollArea.copy(thumbColor = Color.Red),
        ),
        ValueSample(
            name = "SeparatorStyle",
            value = separator,
            copy = separator.copy(),
            changed = separator.copy(color = Color.Red),
        ),
        ValueSample(
            name = "SheetStyle",
            value = sheet,
            copy = sheet.copy(),
            changed = sheet.copy(container = Color.Red),
        ),
        ValueSample(
            name = "SidebarStyle",
            value = sidebar,
            copy = sidebar.copy(),
            changed = sidebar.copy(container = Color.Red),
        ),
        ValueSample(
            name = "TableStyle",
            value = table,
            copy = table.copy(),
            changed = table.copy(headColor = Color.Red),
        ),
        ValueSample(
            name = "TabsStyle",
            value = tabs,
            copy = tabs.copy(),
            changed = tabs.copy(listContainer = Color.Red),
        ),
        ValueSample(
            name = "TabsIndicator",
            value = indicator,
            copy = indicator.copy(),
            changed = indicator.copy(color = Color.Red),
        ),
    )
}

private fun toastSamples(): List<ValueSample> {
    val onClick: () -> Unit = {}
    val action = ToastAction(label = "Undo", onClick = onClick)
    val toast = Toast(
        id = ToastId(value = 1L),
        title = "Saved",
        description = null,
        type = ToastType.Default,
        action = action,
        duration = null,
    )
    val progress = ToastProgress.Determinate(value = 0.5f)
    val style = ToastStyles.Default.resolve(theme = defaults)
    val actionStyle = style.action
    return listOf(
        ValueSample(
            name = "ToastAction",
            value = action,
            copy = action.copy(),
            changed = action.copy(label = "Redo"),
        ),
        ValueSample(
            name = "Toast",
            value = toast,
            copy = toast.copy(),
            changed = toast.copy(title = "Failed"),
        ),
        ValueSample(
            name = "Determinate",
            value = progress,
            copy = progress.copy(),
            changed = progress.copy(value = 1f),
        ),
        ValueSample(
            name = "ToastStyle",
            value = style,
            copy = style.copy(),
            changed = style.copy(container = Color.Red),
        ),
        ValueSample(
            name = "ToastActionStyle",
            value = actionStyle,
            copy = actionStyle.copy(),
            changed = actionStyle.copy(container = Color.Red),
        ),
    )
}

private fun feedbackSamples(): List<ValueSample> {
    val skeleton = SkeletonStyles.Default.resolve(theme = defaults)
    val spinner = SpinnerStyles.Default.resolve(theme = defaults)
    val tooltip = TooltipStyles.Default.resolve(theme = defaults)
    val trace = TraceStyles.Default.resolve(theme = defaults)
    return listOf(
        ValueSample(
            name = "SkeletonStyle",
            value = skeleton,
            copy = skeleton.copy(),
            changed = skeleton.copy(isAnimated = !skeleton.isAnimated),
        ),
        ValueSample(
            name = "SpinnerStyle",
            value = spinner,
            copy = spinner.copy(),
            changed = spinner.copy(isAnimated = !spinner.isAnimated),
        ),
        ValueSample(
            name = "TooltipStyle",
            value = tooltip,
            copy = tooltip.copy(),
            changed = tooltip.copy(container = Color.Red),
        ),
        ValueSample(
            name = "TraceStyle",
            value = trace,
            copy = trace.copy(),
            changed = trace.copy(color = Color.Red),
        ),
    )
}
