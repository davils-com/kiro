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

package com.davils.kiro.ui.window.splash

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.core.style.Border

internal fun Modifier.endDivider(border: Border): Modifier = drawWithContent {
    drawContent()
    val stroke = border.width.toPx()
    val edge = size.width - stroke / 2
    drawLine(
        color = border.color,
        start = Offset(x = edge, y = 0f),
        end = Offset(x = edge, y = size.height),
        strokeWidth = stroke,
    )
}
