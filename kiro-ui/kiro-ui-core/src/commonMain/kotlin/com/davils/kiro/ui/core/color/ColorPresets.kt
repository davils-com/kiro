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

package com.davils.kiro.ui.core.color

import androidx.compose.ui.graphics.Color

private const val LINE_ALPHA: Float = 0.14f

internal object ColorPresets {
    private val brand = oklch(lightness = 0.539128, chroma = 0.287217, hue = 294.099)

    private val black = Color.Black

    private val white = Color.White

    private val chart = ChartColors(
        first = oklch(lightness = 0.827, chroma = 0.119, hue = 306.383),
        second = oklch(lightness = 0.627, chroma = 0.265, hue = 303.9),
        third = oklch(lightness = 0.558, chroma = 0.288, hue = 302.321),
        fourth = oklch(lightness = 0.496, chroma = 0.265, hue = 301.924),
        fifth = oklch(lightness = 0.438, chroma = 0.218, hue = 303.724),
    )

    val light: Colors by lazy {
        val foreground = neutral(lightness = 0.145)
        Colors(
            background = neutral(lightness = 1.0),
            foreground = foreground,
            card = neutral(lightness = 1.0),
            cardForeground = foreground,
            popover = neutral(lightness = 1.0),
            popoverForeground = foreground,
            primary = oklch(lightness = 0.496, chroma = 0.265, hue = 301.924),
            primaryForeground = oklch(lightness = 0.977, chroma = 0.014, hue = 308.299),
            secondary = oklch(lightness = 0.955, chroma = 0.001, hue = 286.375),
            secondaryForeground = oklch(lightness = 0.21, chroma = 0.006, hue = 285.885),
            muted = neutral(lightness = 0.955),
            mutedForeground = neutral(lightness = 0.5),
            accent = neutral(lightness = 0.955),
            accentForeground = neutral(lightness = 0.205),
            destructive = oklch(lightness = 0.577, chroma = 0.245, hue = 27.325),
            border = neutral(lightness = 0.885),
            input = neutral(lightness = 0.885),
            ring = neutral(lightness = 0.708),
            brand = brand,
            line = foreground.copy(alpha = LINE_ALPHA),
            scrim = black,
            shadow = black,
            knob = white,
            chart = chart,
            sidebar = SidebarColors(
                background = neutral(lightness = 0.985),
                foreground = foreground,
                primary = oklch(lightness = 0.558, chroma = 0.288, hue = 302.321),
                primaryForeground = oklch(lightness = 0.977, chroma = 0.014, hue = 308.299),
                accent = neutral(lightness = 0.955),
                accentForeground = neutral(lightness = 0.205),
                border = neutral(lightness = 0.885),
                ring = neutral(lightness = 0.708),
            ),
            status = StatusColors(
                info = StatusColor(
                    foreground = oklch(lightness = 0.488, chroma = 0.243, hue = 264.376),
                    surface = oklch(lightness = 0.97, chroma = 0.014, hue = 254.604),
                    border = oklch(lightness = 0.882, chroma = 0.059, hue = 254.128),
                ),
                success = StatusColor(
                    foreground = oklch(lightness = 0.527, chroma = 0.154, hue = 150.069),
                    surface = oklch(lightness = 0.982, chroma = 0.018, hue = 155.826),
                    border = oklch(lightness = 0.925, chroma = 0.084, hue = 155.995),
                ),
                warning = StatusColor(
                    foreground = oklch(lightness = 0.555, chroma = 0.163, hue = 48.998),
                    surface = oklch(lightness = 0.987, chroma = 0.022, hue = 95.277),
                    border = oklch(lightness = 0.924, chroma = 0.12, hue = 95.746),
                ),
                error = StatusColor(
                    foreground = oklch(lightness = 0.577, chroma = 0.245, hue = 27.325),
                    surface = oklch(lightness = 0.971, chroma = 0.013, hue = 17.38),
                    border = oklch(lightness = 0.885, chroma = 0.062, hue = 18.334),
                ),
            ),
        )
    }

    val dark: Colors by lazy {
        val foreground = neutral(lightness = 0.985)
        Colors(
            background = neutral(lightness = 0.145),
            foreground = foreground,
            card = neutral(lightness = 0.205),
            cardForeground = foreground,
            popover = neutral(lightness = 0.205),
            popoverForeground = foreground,
            primary = oklch(lightness = 0.438, chroma = 0.218, hue = 303.724),
            primaryForeground = oklch(lightness = 0.977, chroma = 0.014, hue = 308.299),
            secondary = oklch(lightness = 0.274, chroma = 0.006, hue = 286.033),
            secondaryForeground = foreground,
            muted = neutral(lightness = 0.269),
            mutedForeground = neutral(lightness = 0.708),
            accent = neutral(lightness = 0.269),
            accentForeground = foreground,
            destructive = oklch(lightness = 0.704, chroma = 0.191, hue = 22.216),
            border = neutral(lightness = 1.0, alpha = 0.1),
            input = neutral(lightness = 1.0, alpha = 0.15),
            ring = neutral(lightness = 0.556),
            brand = brand,
            line = foreground.copy(alpha = LINE_ALPHA),
            scrim = black,
            shadow = black,
            knob = white,
            chart = chart,
            sidebar = SidebarColors(
                background = neutral(lightness = 0.205),
                foreground = foreground,
                primary = oklch(lightness = 0.627, chroma = 0.265, hue = 303.9),
                primaryForeground = oklch(lightness = 0.977, chroma = 0.014, hue = 308.299),
                accent = neutral(lightness = 0.269),
                accentForeground = foreground,
                border = neutral(lightness = 1.0, alpha = 0.1),
                ring = neutral(lightness = 0.556),
            ),
            status = StatusColors(
                info = StatusColor(
                    foreground = oklch(lightness = 0.707, chroma = 0.165, hue = 254.624),
                    surface = oklch(lightness = 0.282, chroma = 0.091, hue = 267.935),
                    border = oklch(lightness = 0.379, chroma = 0.146, hue = 265.522),
                ),
                success = StatusColor(
                    foreground = oklch(lightness = 0.792, chroma = 0.209, hue = 151.711),
                    surface = oklch(lightness = 0.266, chroma = 0.065, hue = 152.934),
                    border = oklch(lightness = 0.393, chroma = 0.095, hue = 152.535),
                ),
                warning = StatusColor(
                    foreground = oklch(lightness = 0.828, chroma = 0.189, hue = 84.429),
                    surface = oklch(lightness = 0.279, chroma = 0.077, hue = 45.635),
                    border = oklch(lightness = 0.414, chroma = 0.112, hue = 45.904),
                ),
                error = StatusColor(
                    foreground = oklch(lightness = 0.704, chroma = 0.191, hue = 22.216),
                    surface = oklch(lightness = 0.258, chroma = 0.092, hue = 26.042),
                    border = oklch(lightness = 0.396, chroma = 0.141, hue = 25.723),
                ),
            ),
        )
    }

    private fun neutral(lightness: Double, alpha: Double = 1.0): Color =
        oklch(lightness = lightness, chroma = 0.0, hue = 0.0, alpha = alpha)
}
