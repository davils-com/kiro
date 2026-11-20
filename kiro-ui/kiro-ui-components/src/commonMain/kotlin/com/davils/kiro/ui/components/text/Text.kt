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

package com.davils.kiro.ui.components.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.davils.kiro.ui.core.content.LocalContentColor
import com.davils.kiro.ui.core.content.LocalTextStyle

/**
 * Shows a text in the current text style and content color.
 *
 * The color is [color] if it is specified, else the color of [style] if that is specified, else the current content
 * color.
 *
 * @param text the text to show.
 * @param modifier the modifier of the text.
 * @param style the style of the text, by default the current text style.
 * @param color the color of the text, or [Color.Unspecified] to take it from [style] or the content color.
 * @param maxLines the largest number of lines the text takes.
 * @param overflow how the text is cut off when it does not fit.
 * @since 1.0.0
 */
@Composable
public fun Text(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val contentColor = LocalContentColor.current
    val resolved = color.takeOrElse { style.color.takeOrElse { contentColor } }
    BasicText(
        text = text,
        modifier = modifier,
        style = style.copy(color = resolved),
        overflow = overflow,
        maxLines = maxLines,
    )
}
