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

package com.davils.kiro.ui.components.separator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Orientation
import com.davils.kiro.ui.core.Theme

@Composable
public fun Separator(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal,
    style: SeparatorStyle = separatorStyle(),
) {
    val sized = when (orientation) {
        Orientation.Horizontal -> modifier.fillMaxWidth().height(style.thickness)
        Orientation.Vertical -> modifier.fillMaxHeight().width(style.thickness)
    }
    Box(modifier = sized.background(style.color))
}

internal fun defaultSeparatorStyle(theme: Theme): SeparatorStyle =
    SeparatorStyle(thickness = 1.dp, color = theme.colors.border)
