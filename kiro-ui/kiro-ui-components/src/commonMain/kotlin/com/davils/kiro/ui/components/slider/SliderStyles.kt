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

package com.davils.kiro.ui.components.slider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.theme
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val RING_ALPHA: Float = 0.5f
private const val DISABLED_ALPHA: Float = 0.5f

public fun interface SliderStyles {
    public fun resolve(theme: Theme): SliderStyle

    public companion object {
        public val Default: SliderStyles = SliderStyles(::defaultSliderStyle)
    }
}

public val SliderStyleKey: StyleKey<SliderStyles> = StyleKey(name = "slider") { _ -> SliderStyles.Default }

@Composable
@ReadOnlyComposable
public fun sliderStyle(): SliderStyle = SliderStyleKey.current.resolve(theme)

internal fun defaultSliderStyle(theme: Theme): SliderStyle = SliderStyle(
    trackThickness = 4.dp,
    trackColor = theme.colors.muted,
    rangeColor = theme.colors.primary,
    thumbSize = 12.dp,
    thumbColor = Color.White,
    thumbBorder = Border(width = 1.dp, color = theme.colors.ring),
    ring = Border(width = theme.focusRing.width, color = theme.colors.ring.copy(alpha = RING_ALPHA)),
    minLength = 160.dp,
    disabledAlpha = DISABLED_ALPHA,
    colorAnimation = theme.colorTransition(),
)
