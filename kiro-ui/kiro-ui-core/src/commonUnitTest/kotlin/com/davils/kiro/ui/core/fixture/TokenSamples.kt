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

package com.davils.kiro.ui.core.fixture

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.core.IconTokens
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.Colors
import com.davils.kiro.ui.core.color.Oklch
import com.davils.kiro.ui.core.elevation.ShadowLayer
import com.davils.kiro.ui.core.elevation.ShadowStrength
import com.davils.kiro.ui.core.elevation.Shadows
import com.davils.kiro.ui.core.focus.FocusRing
import com.davils.kiro.ui.core.motion.AmbientMotion
import com.davils.kiro.ui.core.motion.Durations
import com.davils.kiro.ui.core.motion.Easings
import com.davils.kiro.ui.core.motion.Motion
import com.davils.kiro.ui.core.motion.MotionSpring
import com.davils.kiro.ui.core.motion.MotionSprings
import com.davils.kiro.ui.core.motion.OverlayMotion
import com.davils.kiro.ui.core.shape.Radii
import com.davils.kiro.ui.core.shape.RadiusFactors
import com.davils.kiro.ui.core.spacing.Spacing
import com.davils.kiro.ui.core.state.InteractionState
import com.davils.kiro.ui.core.state.StateValue
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.core.style.BorderWidths
import com.davils.kiro.ui.core.style.StateColors
import com.davils.kiro.ui.core.typography.FontFamilies
import com.davils.kiro.ui.core.typography.Leading
import com.davils.kiro.ui.core.typography.TextScale
import com.davils.kiro.ui.core.typography.Tracking
import com.davils.kiro.ui.core.typography.Typography

private val defaults: Theme = Theme.light()

private val red: StateValue<Color> = StateValue(default = Color.Red)

internal fun tokenSamples(): List<TokenSample> = colorSamples() +
    elevationSamples() +
    motionSamples() +
    shapeSamples() +
    stateSamples() +
    typographySamples()

private fun colorSamples(): List<TokenSample> {
    val colors = Colors.light()
    val chart = colors.chart
    val status = colors.status
    val oklch = Oklch(lightness = 1.0, chroma = 0.0, hue = 0.0)
    return listOf(
        TokenSample(
            name = "Colors",
            value = colors,
            copy = colors.copy(),
            changed = colors.copy(primary = Color.Red),
        ),
        TokenSample(
            name = "ChartColors",
            value = chart,
            copy = chart.copy(),
            changed = chart.copy(first = Color.Red),
        ),
        TokenSample(
            name = "SidebarColors",
            value = colors.sidebar,
            copy = colors.sidebar.copy(),
            changed = colors.sidebar.copy(primary = Color.Red),
        ),
        TokenSample(
            name = "StatusColors",
            value = status,
            copy = status.copy(),
            changed = status.copy(info = status.error),
        ),
        TokenSample(
            name = "StatusColor",
            value = status.info,
            copy = status.info.copy(),
            changed = status.info.copy(border = Color.Red),
        ),
        TokenSample(
            name = "Oklch",
            value = oklch,
            copy = oklch.copy(),
            changed = oklch.copy(alpha = 0.0),
        ),
    )
}

private fun elevationSamples(): List<TokenSample> {
    val layer = ShadowLayer(offsetY = defaults.borders.thin, blur = defaults.borders.thick, color = Color.Black)
    val shadows = Shadows.from(Color.Black)
    val strength = ShadowStrength()
    return listOf(
        TokenSample(
            name = "ShadowLayer",
            value = layer,
            copy = layer.copy(),
            changed = layer.copy(spread = defaults.borders.thick),
        ),
        TokenSample(
            name = "ShadowStrength",
            value = strength,
            copy = strength.copy(),
            changed = strength.copy(deep = strength.faint),
        ),
        TokenSample(
            name = "Shadows",
            value = shadows,
            copy = shadows.copy(),
            changed = shadows.copy(xs = shadows.xl),
        ),
        TokenSample(
            name = "FocusRing",
            value = FocusRing(),
            copy = FocusRing().copy(),
            changed = FocusRing().copy(width = defaults.borders.thin),
        ),
    )
}

private fun motionSamples(): List<TokenSample> {
    val spring = MotionSpring(dampingRatio = 1f, stiffness = 1f)
    val durations = Durations()
    return listOf(
        TokenSample(
            name = "AmbientMotion",
            value = AmbientMotion(),
            copy = AmbientMotion().copy(),
            changed = AmbientMotion().copy(speed = 0f),
        ),
        TokenSample(
            name = "Durations",
            value = durations,
            copy = durations.copy(),
            changed = durations.copy(long = durations.short),
        ),
        TokenSample(
            name = "Easings",
            value = Easings(),
            copy = Easings().copy(),
            changed = Easings().copy(enter = Easings().exit),
        ),
        TokenSample(
            name = "MotionSpring",
            value = spring,
            copy = spring.copy(),
            changed = spring.copy(stiffness = 0f),
        ),
        TokenSample(
            name = "MotionSprings",
            value = MotionSprings(),
            copy = MotionSprings().copy(),
            changed = MotionSprings().copy(effects = spring),
        ),
        TokenSample(
            name = "OverlayMotion",
            value = OverlayMotion(),
            copy = OverlayMotion().copy(),
            changed = OverlayMotion().copy(initialScale = 1f),
        ),
        TokenSample(
            name = "Motion",
            value = Motion(),
            copy = Motion().copy(),
            changed = Motion().copy(isReduced = true),
        ),
    )
}

private fun shapeSamples(): List<TokenSample> {
    val radii = Radii.from()
    val factors = RadiusFactors()
    val borders = BorderWidths()
    val border = Border(width = borders.thin, color = Color.Red)
    return listOf(
        TokenSample(
            name = "Radii",
            value = radii,
            copy = radii.copy(),
            changed = radii.copy(sm = radii.xl4),
        ),
        TokenSample(
            name = "RadiusFactors",
            value = factors,
            copy = factors.copy(),
            changed = factors.copy(xl4 = factors.sm),
        ),
        TokenSample(
            name = "Spacing",
            value = Spacing(),
            copy = Spacing().copy(),
            changed = Spacing().copy(unit = borders.thin),
        ),
        TokenSample(
            name = "BorderWidths",
            value = borders,
            copy = borders.copy(),
            changed = borders.copy(thick = borders.thin),
        ),
        TokenSample(
            name = "Border",
            value = border,
            copy = border.copy(),
            changed = border.copy(width = borders.thick),
        ),
        TokenSample(
            name = "IconTokens",
            value = IconTokens(),
            copy = IconTokens().copy(),
            changed = IconTokens().copy(isNonScalingStroke = true),
        ),
    )
}

private fun stateSamples(): List<TokenSample> {
    val colors = StateColors(container = red, content = red, border = red, ring = red)
    val state = InteractionState()
    val value = StateValue(default = true)
    return listOf(
        TokenSample(
            name = "StateColors",
            value = colors,
            copy = colors.copy(),
            changed = colors.copy(ring = StateValue(default = Color.Blue)),
        ),
        TokenSample(
            name = "InteractionState",
            value = state,
            copy = state.copy(),
            changed = state.copy(isHovered = true),
        ),
        TokenSample(
            name = "StateValue",
            value = value,
            copy = value.copy(),
            changed = value.copy(hovered = false),
        ),
        TokenSample(
            name = "Theme",
            value = defaults,
            copy = defaults.copy(),
            changed = defaults.copy(isDark = true),
        ),
    )
}

private fun typographySamples(): List<TokenSample> {
    val scale = TextScale()
    val families = FontFamilies.System
    return listOf(
        TokenSample(
            name = "Leading",
            value = Leading(),
            copy = Leading().copy(),
            changed = Leading().copy(none = Leading().loose),
        ),
        TokenSample(
            name = "TextSize",
            value = scale.xs,
            copy = scale.xs.copy(),
            changed = scale.xs.copy(lineHeight = scale.xl9.lineHeight),
        ),
        TokenSample(
            name = "TextScale",
            value = scale,
            copy = scale.copy(),
            changed = scale.copy(xs = scale.xl9),
        ),
        TokenSample(
            name = "Tracking",
            value = Tracking(),
            copy = Tracking().copy(),
            changed = Tracking().copy(normal = Tracking().wide),
        ),
        TokenSample(
            name = "Typography",
            value = Typography(),
            copy = Typography().copy(),
            changed = Typography().copy(families = families),
        ),
        TokenSample(
            name = "FontFamilies",
            value = families,
            copy = families.copy(),
            changed = FontFamilies.Bundled,
        ),
    )
}
