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

@Composable
public fun FieldLabel(text: String, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    Text(text = text, modifier = modifier.layoutId(FieldSlot.Label).then(disabledOf(style)), style = style.labelStyle)
}

@Composable
public fun FieldTitle(text: String, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    Text(text = text, modifier = modifier.then(disabledOf(style)), style = style.titleStyle)
}

@Composable
public fun FieldDescription(text: String, modifier: Modifier = Modifier, style: FieldStyle = fieldStyle()) {
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}

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

@Composable
public fun FieldSeparator(
    modifier: Modifier = Modifier,
    style: FieldStyle = fieldStyle(),
    content: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .overlapping(style.separatorOverlap)
            .fillMaxWidth()
            .height(style.separatorHeight),
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
    layout(placeable.width, maxOf(placeable.height - pixels * 2, 0)) { placeable.place(0, -pixels) }
}
