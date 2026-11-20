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

package com.davils.kiro.ui.components.switch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.colorTransition
import com.davils.kiro.ui.components.primitive.controlColors
import com.davils.kiro.ui.components.primitive.darkAware
import com.davils.kiro.ui.components.primitive.transition
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.Border

private const val DARK_TRACK: Float = 0.8f
private const val DISABLED_ALPHA: Float = 0.5f
private const val LIGHT_OUTLINE: Float = 0.25f

private val metricsBySize: Map<SwitchSize, SwitchMetrics> = mapOf(
    SwitchSize.Default to SwitchMetrics(width = 32.dp, height = 18.4.dp, thumb = 16.dp),
    SwitchSize.Sm to SwitchMetrics(width = 24.dp, height = 14.dp, thumb = 12.dp),
)

internal fun defaultSwitchStyle(theme: Theme, size: SwitchSize): SwitchStyle {
    val metrics = requireNotNull(metricsBySize[size]) { "No default style for the switch size $size." }
    val colors = theme.colors
    val track = colors.input.copy(alpha = theme.darkAware(light = 1f, dark = DARK_TRACK))
    val unchecked = theme.controlColors(
        container = StateValue(default = track),
        content = StateValue(default = Color.Transparent),
    )
    return SwitchStyle(
        width = metrics.width,
        height = metrics.height,
        thumb = SwitchThumb(
            size = metrics.thumb,
            color = thumbColorOf(theme = theme, dark = colors.foreground),
            checkedColor = thumbColorOf(theme = theme, dark = colors.primaryForeground),
            border = Border(width = 1.dp, color = outlineOf(theme)),
        ),
        colors = unchecked,
        checkedColors = unchecked.copy(container = StateValue(default = colors.primary)),
        borderWidth = 1.dp,
        ringWidth = theme.focusRing.width,
        disabledAlpha = DISABLED_ALPHA,
        colorAnimation = theme.colorTransition(),
        thumbAnimation = theme.transition(),
    )
}

private fun outlineOf(theme: Theme): Color {
    if (theme.isDark) return theme.colors.background
    return theme.colors.foreground.copy(alpha = LIGHT_OUTLINE)
}

private fun thumbColorOf(theme: Theme, dark: Color): Color {
    if (theme.isDark) return dark
    return theme.colors.background
}
