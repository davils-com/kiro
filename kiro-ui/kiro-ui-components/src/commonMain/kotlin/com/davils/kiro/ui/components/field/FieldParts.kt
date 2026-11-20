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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.textfield.disabledAlphaOf
import com.davils.kiro.ui.components.separator.Separator
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

private const val BULLET_POINT: Int = 0x2022

private val bullet: String = Char(BULLET_POINT).toString()

/**
 * The text of a [Field] next to its control, such as a title and a description, laid out in a column.
 *
 * In a horizontal field, the content shares the width that the control leaves, and the control lines up with the top of
 * the content.
 *
 * @param modifier the modifier of the column.
 * @param style the style of the field; its [FieldStyle.contentGap] spaces the children.
 * @param content the children of the content, such as a [FieldTitle] and a [FieldDescription].
 * @since 1.0.0
 */
@Composable
public fun FieldContent(
    modifier: Modifier = Modifier,
    style: FieldStyle = fieldStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.layoutId(FieldSlot.Content),
        verticalArrangement = Arrangement.spacedBy(style.contentGap),
        content = content,
    )
}

/**
 * The label of a [Field], faded while the field is disabled.
 *
 * In a horizontal field, the label shares the width that the control leaves.
 *
 * @param text the text of the label.
 * @param modifier the modifier of the text.
 * @param style the style of the field.
 * @since 1.0.0
 */
@Composable
public fun FieldLabel(text: String, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    Text(text = text, modifier = modifier.layoutId(FieldSlot.Label).then(disabledOf(style)), style = style.labelStyle)
}

/**
 * The title of a [FieldContent], faded while the field is disabled.
 *
 * @param text the text of the title.
 * @param modifier the modifier of the text.
 * @param style the style of the field.
 * @since 1.0.0
 */
@Composable
public fun FieldTitle(text: String, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    Text(text = text, modifier = modifier.then(disabledOf(style)), style = style.titleStyle)
}

/**
 * A description that explains a field or a set of fields.
 *
 * @param text the text of the description.
 * @param modifier the modifier of the text.
 * @param style the style of the field.
 * @since 1.0.0
 */
@Composable
public fun FieldDescription(text: String, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}

/**
 * The error messages of a field, announced to assistive technologies as soon as they change.
 *
 * Repeated messages are shown once. A single message is shown as text, more than one as a list with bullets, and
 * nothing is shown while there is none.
 *
 * @param messages the error messages.
 * @param modifier the modifier of the text or the list.
 * @param style the style of the field.
 * @since 1.0.0
 */
@Composable
public fun FieldError(messages: List<String>, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    val unique = messages.distinct()
    if (unique.isEmpty()) return
    val alert = modifier.semantics { liveRegion = LiveRegionMode.Assertive }
    if (unique.size == 1) {
        Text(text = unique.single(), modifier = alert, style = style.errorStyle, color = style.invalidColor)
        return
    }
    Column(
        modifier = alert.padding(start = style.errorIndent),
        verticalArrangement = Arrangement.spacedBy(style.errorGap),
    ) {
        unique.forEach { message ->
            Row(horizontalArrangement = Arrangement.spacedBy(style.errorGap)) {
                Text(text = bullet, style = style.errorStyle, color = style.invalidColor)
                Text(text = message, style = style.errorStyle, color = style.invalidColor)
            }
        }
    }
}

/**
 * A line between the fields of a [FieldGroup], with an optional label in its middle.
 *
 * The separator reaches into the gaps above and below it by [FieldStyle.separatorOverlap].
 *
 * @param modifier the modifier of the separator.
 * @param style the style of the field.
 * @param content the label in the middle of the line, drawn on the [FieldStyle.separatorBackground], or `null` for
 * none.
 * @since 1.0.0
 */
@Composable
public fun FieldSeparator(
    modifier: Modifier = Modifier,
    style: FieldStyle = fieldStyle(),
    content: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier.then(
            Modifier
                .overlapping(style.separatorOverlap)
                .fillMaxWidth()
                .height(style.separatorHeight),
        ),
        contentAlignment = Alignment.Center,
    ) {
        Separator(modifier = Modifier.fillMaxWidth())
        content?.let { label ->
            Box(
                modifier = Modifier.background(style.separatorBackground).padding(horizontal = style.separatorPadding),
            ) {
                CompositionLocalProvider(
                    LocalTextStyle provides style.descriptionStyle,
                    LocalContentColor provides style.descriptionColor,
                    content = label,
                )
            }
        }
    }
}

@Composable
private fun disabledOf(style: FieldStyle): Modifier {
    val isDisabled = LocalField.current.isDisabled
    return Modifier.alpha(disabledAlphaOf(isDisabled = isDisabled, alpha = style.disabledAlpha))
}

private fun Modifier.overlapping(overlap: Dp): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val pixels = overlap.roundToPx()
    layout(width = placeable.width, height = maxOf(a = placeable.height - pixels * 2, b = 0)) {
        placeable.place(x = 0, y = -pixels)
    }
}
