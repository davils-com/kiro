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
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.accordion.defaultAccordionStyle
import com.davils.kiro.ui.components.alert.AlertVariant
import com.davils.kiro.ui.components.alert.defaultAlertStyle
import com.davils.kiro.ui.components.attachment.AttachmentSize
import com.davils.kiro.ui.components.attachment.defaultAttachmentStyle
import com.davils.kiro.ui.components.avatar.AvatarSize
import com.davils.kiro.ui.components.avatar.defaultAvatarStyle
import com.davils.kiro.ui.components.badge.BadgeVariant
import com.davils.kiro.ui.components.badge.defaultBadgeStyle
import com.davils.kiro.ui.components.brand.defaultBrandMarkStyle
import com.davils.kiro.ui.components.breadcrumb.defaultBreadcrumbStyle
import com.davils.kiro.ui.components.bubble.BubbleVariant
import com.davils.kiro.ui.components.bubble.defaultBubbleStyle
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.button.defaultButtonStyle
import com.davils.kiro.ui.components.buttongroup.defaultButtonGroupStyle
import com.davils.kiro.ui.components.calendar.CalendarOptions
import com.davils.kiro.ui.components.calendar.CalendarSelection
import com.davils.kiro.ui.components.calendar.defaultCalendarStyle
import com.davils.kiro.ui.components.card.CardSize
import com.davils.kiro.ui.components.card.defaultCardStyle
import com.davils.kiro.ui.components.carousel.defaultCarouselStyle
import com.davils.kiro.ui.components.chart.ChartConfig
import com.davils.kiro.ui.components.chart.ChartKey
import com.davils.kiro.ui.components.chart.ChartPoint
import com.davils.kiro.ui.components.chart.ChartSeries
import com.davils.kiro.ui.components.chart.defaultChartStyle
import com.davils.kiro.ui.components.checkbox.defaultCheckboxStyle
import com.davils.kiro.ui.core.Theme
import kotlinx.datetime.LocalDate

private val defaults: Theme = Theme.light()

private val day: LocalDate = LocalDate(year = 2026, month = 11, day = 20)

internal fun accordionToCheckboxSamples(): List<ValueSample> = accordionToBrandSamples() +
    breadcrumbToButtonSamples() +
    calendarSamples() +
    cardToCarouselSamples() +
    chartSamples() +
    checkboxSamples()

private fun accordionToBrandSamples(): List<ValueSample> {
    val accordion = defaultAccordionStyle(defaults)
    val alert = defaultAlertStyle(theme = defaults, variant = AlertVariant.Default)
    val attachment = defaultAttachmentStyle(theme = defaults, size = AttachmentSize.Default)
    val avatar = defaultAvatarStyle(theme = defaults, size = AvatarSize.Default)
    val badge = defaultBadgeStyle(theme = defaults, variant = BadgeVariant.Default)
    val brand = defaultBrandMarkStyle(defaults)
    return listOf(
        ValueSample(
            name = "AccordionStyle",
            value = accordion,
            copy = accordion.copy(),
            changed = accordion.copy(isUnderlinedOnHover = !accordion.isUnderlinedOnHover),
        ),
        ValueSample(
            name = "AlertStyle",
            value = alert,
            copy = alert.copy(),
            changed = alert.copy(gap = alert.gap + 1.dp),
        ),
        ValueSample(
            name = "AttachmentStyle",
            value = attachment,
            copy = attachment.copy(),
            changed = attachment.copy(gap = attachment.gap + 1.dp),
        ),
        ValueSample(
            name = "AvatarStyle",
            value = avatar,
            copy = avatar.copy(),
            changed = avatar.copy(size = avatar.size + 1.dp),
        ),
        ValueSample(
            name = "BadgeStyle",
            value = badge,
            copy = badge.copy(),
            changed = badge.copy(isUnderlinedOnHover = !badge.isUnderlinedOnHover),
        ),
        ValueSample(
            name = "BrandMarkStyle",
            value = brand,
            copy = brand.copy(),
            changed = brand.copy(size = brand.size + 1.dp),
        ),
    )
}

private fun breadcrumbToButtonSamples(): List<ValueSample> {
    val breadcrumb = defaultBreadcrumbStyle(defaults)
    val bubble = defaultBubbleStyle(theme = defaults, variant = BubbleVariant.Default)
    val reactions = bubble.reactions
    val button = defaultButtonStyle(theme = defaults, variant = ButtonVariant.Default, size = ButtonSize.Default)
    val group = defaultButtonGroupStyle(defaults)
    return listOf(
        ValueSample(
            name = "BreadcrumbStyle",
            value = breadcrumb,
            copy = breadcrumb.copy(),
            changed = breadcrumb.copy(gap = breadcrumb.gap + 1.dp),
        ),
        ValueSample(
            name = "BubbleReactionsStyle",
            value = reactions,
            copy = reactions.copy(),
            changed = reactions.copy(gap = reactions.gap + 1.dp),
        ),
        ValueSample(
            name = "BubbleStyle",
            value = bubble,
            copy = bubble.copy(),
            changed = bubble.copy(gap = bubble.gap + 1.dp),
        ),
        ValueSample(
            name = "ButtonStyle",
            value = button,
            copy = button.copy(),
            changed = button.copy(isUnderlinedOnHover = !button.isUnderlinedOnHover),
        ),
        ValueSample(
            name = "ButtonGroupStyle",
            value = group,
            copy = group.copy(),
            changed = group.copy(overlap = group.overlap + 1.dp),
        ),
    )
}

private fun calendarSamples(): List<ValueSample> {
    val calendar = defaultCalendarStyle(defaults)
    val dayStyle = calendar.day
    val options = CalendarOptions()
    val single = CalendarSelection.Single(date = null)
    val multiple = CalendarSelection.Multiple(dates = emptySet())
    val range = CalendarSelection.Range(start = null, end = null)
    return listOf(
        ValueSample(
            name = "CalendarStyle",
            value = calendar,
            copy = calendar.copy(),
            changed = calendar.copy(padding = calendar.padding + 1.dp),
        ),
        ValueSample(
            name = "CalendarDayStyle",
            value = dayStyle,
            copy = dayStyle.copy(),
            changed = dayStyle.copy(content = Color.Red),
        ),
        ValueSample(
            name = "CalendarOptions",
            value = options,
            copy = options.copy(),
            changed = options.copy(isWeekNumberShown = !options.isWeekNumberShown),
        ),
        ValueSample(
            name = "Single",
            value = single,
            copy = single.copy(),
            changed = single.copy(date = day),
        ),
        ValueSample(
            name = "Multiple",
            value = multiple,
            copy = multiple.copy(),
            changed = multiple.copy(dates = setOf(day)),
        ),
        ValueSample(
            name = "Range",
            value = range,
            copy = range.copy(),
            changed = range.copy(start = day),
        ),
    )
}

private fun cardToCarouselSamples(): List<ValueSample> {
    val card = defaultCardStyle(theme = defaults, size = CardSize.Default)
    val carousel = defaultCarouselStyle(defaults)
    return listOf(
        ValueSample(
            name = "CardStyle",
            value = card,
            copy = card.copy(),
            changed = card.copy(spacing = card.spacing + 1.dp),
        ),
        ValueSample(
            name = "CarouselStyle",
            value = carousel,
            copy = carousel.copy(),
            changed = carousel.copy(spacing = carousel.spacing + 1.dp),
        ),
    )
}

private fun chartSamples(): List<ValueSample> {
    val key = ChartKey(value = "desktop")
    val series = ChartSeries(key = key, label = "Desktop")
    val config = ChartConfig(series = listOf(series))
    val point = ChartPoint(label = "January", values = mapOf(key to 186.0))
    val chart = defaultChartStyle(defaults)
    val legend = chart.legend
    val tooltip = chart.tooltip
    return listOf(
        ValueSample(
            name = "ChartConfig",
            value = config,
            copy = config.copy(),
            changed = config.copy(series = emptyList()),
        ),
        ValueSample(
            name = "ChartSeries",
            value = series,
            copy = series.copy(),
            changed = series.copy(label = "Mobile"),
        ),
        ValueSample(
            name = "ChartPoint",
            value = point,
            copy = point.copy(),
            changed = point.copy(label = "February"),
        ),
        ValueSample(
            name = "ChartStyle",
            value = chart,
            copy = chart.copy(),
            changed = chart.copy(animationMillis = chart.animationMillis + 1),
        ),
        ValueSample(
            name = "ChartLegendStyle",
            value = legend,
            copy = legend.copy(),
            changed = legend.copy(gap = legend.gap + 1.dp),
        ),
        ValueSample(
            name = "ChartTooltipStyle",
            value = tooltip,
            copy = tooltip.copy(),
            changed = tooltip.copy(gap = tooltip.gap + 1.dp),
        ),
    )
}

private fun checkboxSamples(): List<ValueSample> {
    val indicator = defaultCheckboxStyle(defaults).indicator
    return listOf(
        ValueSample(
            name = "CheckboxIndicator",
            value = indicator,
            copy = indicator.copy(),
            changed = indicator.copy(size = indicator.size + 1.dp),
        ),
    )
}
