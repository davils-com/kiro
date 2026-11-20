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

package com.davils.kiro.ui.components.resizable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.Messages
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val KEYBOARD_STEP: Float = 10f

public fun interface ResizableStyles {
    public fun resolve(theme: Theme): ResizableStyle

    public companion object {
        public val Default: ResizableStyles = ResizableStyles(::defaultResizableStyle)
    }
}

public val ResizableStyleKey: StyleKey<ResizableStyles> = StyleKey(name = "resizable") { _ -> ResizableStyles.Default }

@Composable
@ReadOnlyComposable
public fun resizableStyle(): ResizableStyle = ResizableStyleKey.current.resolve(theme)

internal fun defaultResizableStyle(theme: Theme): ResizableStyle = ResizableStyle(
    thickness = 1.dp,
    hitArea = 4.dp,
    color = theme.colors.border,
    focusColor = theme.colors.ring,
    focusWidth = 1.dp,
    gripLength = 24.dp,
    gripThickness = 4.dp,
    gripShape = RoundedCornerShape(theme.radii.lg),
    gripColor = theme.colors.border,
    keyboardStep = KEYBOARD_STEP,
    junctionLabel = Messages.Resizable.junction,
)
