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

package com.davils.kiro.ui.components.scrollarea

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.motion.millisOf
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val THUMB_HOVER_ALPHA: Float = 0.5f

public fun interface ScrollAreaStyles {
    public fun resolve(theme: Theme): ScrollAreaStyle

    public companion object {
        public val Default: ScrollAreaStyles = ScrollAreaStyles(::defaultScrollAreaStyle)
    }
}

public val ScrollAreaStyleKey: StyleKey<ScrollAreaStyles> = StyleKey(
    name = "scroll-area",
) { _ -> ScrollAreaStyles.Default }

@Composable
@ReadOnlyComposable
public fun scrollAreaStyle(): ScrollAreaStyle = ScrollAreaStyleKey.current.resolve(theme)

internal fun defaultScrollAreaStyle(theme: Theme): ScrollAreaStyle = ScrollAreaStyle(
    thickness = 10.dp,
    inset = 1.dp,
    thumbShape = CircleShape,
    thumbColor = theme.colors.border,
    thumbHoverColor = theme.colors.mutedForeground.copy(alpha = THUMB_HOVER_ALPHA),
    minimalThumbLength = 16.dp,
    hoverDurationMillis = millisOf(theme.motion.durations.short),
)
