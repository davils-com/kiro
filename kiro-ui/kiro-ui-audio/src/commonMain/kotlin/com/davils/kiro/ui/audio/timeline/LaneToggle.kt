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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState

@Composable
internal fun LaneToggle(
    letter: String,
    label: String,
    isOn: Boolean,
    color: Color,
    onClick: () -> Unit,
    style: ClipTrackStyle,
) {
    val container = color.takeIf { isOn } ?: style.toggleIdle
    val content = style.toggleActiveContent.takeIf { isOn } ?: style.toggleIdleContent
    Box(
        modifier = Modifier
            .size(style.toggleSize)
            .background(color = container, shape = RoundedCornerShape(style.toggleRadius))
            .pointerHoverIcon(PointerIcon.Hand)
            .clickable(role = Role.Switch, onClick = onClick)
            .semantics {
                contentDescription = label
                toggleableState = ToggleableState(isOn)
            },
        contentAlignment = Alignment.Center,
    ) {
        BasicText(text = letter, style = style.toggleStyle.copy(color = content))
    }
}
