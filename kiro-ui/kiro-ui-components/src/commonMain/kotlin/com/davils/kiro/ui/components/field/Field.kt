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

package com.davils.kiro.ui.components.field

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * A set of related fields, such as the fields of an address, laid out in a column under an optional [FieldLegend].
 *
 * Assistive technologies traverse the set as one group.
 *
 * @param modifier the modifier of the column.
 * @param style the style of the fields; its [FieldStyle.setGap] spaces the children.
 * @param content the children of the set, such as a [FieldLegend], a [FieldDescription] and a [FieldGroup].
 * @since 1.0.0
 */
@Composable
public fun FieldSet(
    modifier: Modifier = Modifier,
    style: FieldStyle = fieldStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.semantics { isTraversalGroup = true },
        verticalArrangement = Arrangement.spacedBy(style.setGap),
        content = content,
    )
}

/**
 * The legend of a [FieldSet], the text that names the set.
 *
 * @param text the text of the legend.
 * @param modifier the modifier of the text.
 * @param variant the text style of the legend.
 * @param style the style of the fields.
 * @since 1.0.0
 */
@Composable
public fun FieldLegend(
    text: String,
    modifier: Modifier = Modifier,
    variant: FieldLegendVariant = FieldLegendVariant.Legend,
    style: FieldStyle = fieldStyle(),
) {
    Text(
        text = text,
        modifier = modifier.padding(bottom = style.legendSpacing),
        style = legendStyleOf(variant = variant, style = style),
    )
}

/**
 * A group of fields that fills the available width and lays them out in a column.
 *
 * @param modifier the modifier of the column.
 * @param style the style of the fields; its [FieldStyle.groupGap] spaces the children.
 * @param content the children of the group, such as [Field]s and [FieldSeparator]s.
 * @since 1.0.0
 */
@Composable
public fun FieldGroup(
    modifier: Modifier = Modifier,
    style: FieldStyle = fieldStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(style.groupGap),
        content = content,
    )
}

/**
 * One field of a form: a control together with its [FieldLabel], [FieldDescription] and [FieldError].
 *
 * The field fills the available width, and assistive technologies traverse it as one group. While it is invalid, its
 * content takes the [FieldStyle.invalidColor] as the content color; while it is disabled, its label and title are
 * faded. In a horizontal layout, every child that is not a [FieldLabel] or a [FieldContent] counts as a control: it
 * keeps its own width, and the labels and the content share the rest.
 *
 * @param modifier the modifier of the field.
 * @param orientation how the field lays out its children.
 * @param isInvalid whether the value of the field is invalid.
 * @param isDisabled whether the field is disabled; the control itself is disabled through its own parameter.
 * @param style the style of the field.
 * @param content the children of the field: its label or content and its control.
 * @since 1.0.0
 */
@Composable
public fun Field(
    modifier: Modifier = Modifier,
    orientation: FieldOrientation = FieldOrientation.Vertical,
    isInvalid: Boolean = false,
    isDisabled: Boolean = false,
    style: FieldStyle = fieldStyle(),
    content: @Composable () -> Unit,
) {
    val color = contentColorOf(isInvalid = isInvalid, style = style, current = LocalContentColor.current)
    CompositionLocalProvider(
        LocalField provides FieldState(isInvalid = isInvalid, isDisabled = isDisabled),
        LocalContentColor provides color,
    ) {
        val grouped = modifier
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }
        when (orientation) {
            FieldOrientation.Vertical -> VerticalField(modifier = grouped, style = style, content = content)

            FieldOrientation.Horizontal -> HorizontalField(modifier = grouped, style = style, content = content)

            FieldOrientation.Responsive -> BoxWithConstraints(modifier = grouped) {
                val isWide = maxWidth >= style.responsiveBreakpoint
                if (isWide) HorizontalField(modifier = Modifier, style = style, content = content)
                if (!isWide) VerticalField(modifier = Modifier, style = style, content = content)
            }
        }
    }
}

@Composable
private fun VerticalField(modifier: Modifier, style: FieldStyle, content: @Composable () -> Unit) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.gap)) { content() }
}

@Composable
private fun HorizontalField(modifier: Modifier, style: FieldStyle, content: @Composable () -> Unit) {
    Layout(
        content = content,
        modifier = modifier,
        measurePolicy = FieldRowMeasurePolicy(gap = style.gap, nudge = style.controlNudge),
    )
}

private fun legendStyleOf(variant: FieldLegendVariant, style: FieldStyle): TextStyle = when (variant) {
    FieldLegendVariant.Legend -> style.legendStyle
    FieldLegendVariant.Label -> style.legendLabelStyle
}

private fun contentColorOf(isInvalid: Boolean, style: FieldStyle, current: Color): Color {
    if (isInvalid) return style.invalidColor
    return current
}
