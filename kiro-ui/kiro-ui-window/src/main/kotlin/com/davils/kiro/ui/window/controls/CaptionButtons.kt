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

package com.davils.kiro.ui.window.controls

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.WindowActions

@Composable
internal fun CaptionButtons(controls: WindowsControls, actions: WindowActions, style: TitleBarStyle) {
    Row(modifier = Modifier.fillMaxHeight()) {
        controls.buttons.forEach { button ->
            ControlButton(
                button = button,
                actions = actions,
                style = style,
                shape = RectangleShape,
                glyphSize = controls.glyphSize,
                strokeWidth = controls.strokeWidth,
                restoreShift = controls.restoreShift,
                modifier = Modifier.fillMaxHeight().width(controls.buttonWidth),
            )
        }
    }
}
