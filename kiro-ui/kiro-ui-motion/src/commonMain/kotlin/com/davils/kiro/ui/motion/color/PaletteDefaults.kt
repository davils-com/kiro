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

package com.davils.kiro.ui.motion.color

import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.color.mix
import com.davils.kiro.ui.core.color.oklch
import com.davils.kiro.ui.core.color.toOklch

private const val TINT_LIGHT: Float = 0.12f
private const val TINT_DARK: Float = 0.18f
private const val HALF_TURN: Double = 180.0
private const val WHEEL: Int = 6
private const val RAMP: Int = 6
private const val VIVID_LIGHTNESS: Double = 0.68
private const val VIVID_CHROMA: Double = 0.2
private const val PASTEL_LIGHTNESS: Double = 0.88
private const val PASTEL_CHROMA: Double = 0.08
private const val MONO_LIGHT: Double = 0.92
private const val MONO_DARK: Double = 0.36
private const val NIGHT_LIGHTNESS: Double = 0.16
private const val NIGHT_CHROMA: Double = 0.05
private const val GLOW_LIGHTNESS: Double = 0.74
private const val GLOW_CHROMA: Double = 0.18
private const val INK_LIGHTNESS: Double = 0.96
private const val REFINED_GROUND_LIGHT: Float = 0.03f
private const val REFINED_GROUND_DARK: Float = 0.05f
private const val REFINED_INK_LIGHT: Float = 0.6f
private const val REFINED_INK_DARK: Float = 0.45f
private const val REFINED_FIRST_LIGHT: Float = 0.35f
private const val REFINED_FIRST_DARK: Float = 0.5f
private const val REFINED_LIFT_DARK: Float = 0.4f
private const val REFINED_STEPS: Int = 5

internal fun expressivePalette(theme: Theme): ArtPalette {
    val colors = theme.colors
    val chart = colors.chart
    val status = colors.status
    return ArtPalette(
        background = theme.tinted(chart.first),
        ink = colors.foreground,
        accents = listOf(
            chart.first,
            chart.second,
            chart.third,
            chart.fourth,
            chart.fifth,
            status.warning.foreground,
            status.info.foreground,
        ),
        highlight = colors.knob,
        shade = colors.shadow,
    )
}

internal fun refinedPalette(theme: Theme): ArtPalette {
    val colors = theme.colors
    val ground = colors.background.mix(
        other = colors.chart.first,
        amount = theme.darkAware(light = REFINED_GROUND_LIGHT, dark = REFINED_GROUND_DARK),
    )
    val strong = theme.darkAware(
        light = colors.primary,
        dark = colors.primary.mix(other = colors.chart.first, amount = REFINED_LIFT_DARK),
    )
    return ArtPalette(
        background = ground,
        ink = colors.mutedForeground.mix(
            other = ground,
            amount = theme.darkAware(light = REFINED_INK_LIGHT, dark = REFINED_INK_DARK),
        ),
        accents = rampOf(
            start = ground.mix(
                other = colors.chart.second,
                amount = theme.darkAware(light = REFINED_FIRST_LIGHT, dark = REFINED_FIRST_DARK),
            ),
            end = strong,
            steps = REFINED_STEPS,
        ),
        highlight = strong,
        shade = theme.darkAware(light = colors.foreground, dark = colors.shadow),
    )
}

internal fun monochromePalette(theme: Theme): ArtPalette {
    val colors = theme.colors
    val hue = colors.brand.toOklch().hue
    val accents = rampOf(
        start = oklch(lightness = MONO_LIGHT, chroma = VIVID_CHROMA / 2, hue = hue),
        end = oklch(lightness = MONO_DARK, chroma = VIVID_CHROMA, hue = hue),
        steps = RAMP,
    )
    return theme.paletteOf(accents = accents)
}

internal fun vividPalette(theme: Theme): ArtPalette {
    val hue = theme.brandHue()
    val accents = wheelOf(hue = hue, lightness = VIVID_LIGHTNESS, chroma = VIVID_CHROMA, count = WHEEL)
    return theme.paletteOf(accents = accents)
}

internal fun pastelPalette(theme: Theme): ArtPalette {
    val hue = theme.brandHue()
    val accents = wheelOf(hue = hue, lightness = PASTEL_LIGHTNESS, chroma = PASTEL_CHROMA, count = WHEEL)
    return theme.paletteOf(accents = accents)
}

internal fun duotonePalette(theme: Theme): ArtPalette {
    val brand = theme.colors.brand
    val brandHue = brand.toOklch().hue
    val opposite = (brandHue + HALF_TURN) % (HALF_TURN * 2)
    val complement = oklch(lightness = VIVID_LIGHTNESS, chroma = VIVID_CHROMA, hue = opposite)
    return theme.paletteOf(accents = rampOf(start = brand, end = complement, steps = RAMP))
}

internal fun nightPalette(theme: Theme): ArtPalette {
    val colors = theme.colors
    val hue = colors.brand.toOklch().hue
    return ArtPalette(
        background = oklch(lightness = NIGHT_LIGHTNESS, chroma = NIGHT_CHROMA, hue = hue),
        ink = oklch(lightness = INK_LIGHTNESS, chroma = NIGHT_CHROMA, hue = hue),
        accents = wheelOf(hue = hue, lightness = GLOW_LIGHTNESS, chroma = GLOW_CHROMA, count = WHEEL),
        highlight = colors.knob,
        shade = colors.shadow,
    )
}

private fun Theme.paletteOf(accents: List<Color>): ArtPalette = ArtPalette(
    background = tinted(accents.first()),
    ink = colors.foreground,
    accents = accents,
    highlight = colors.knob,
    shade = colors.shadow,
)

private fun Theme.tinted(tint: Color): Color {
    if (isDark) return colors.background.mix(other = tint, amount = TINT_DARK)
    return colors.background.mix(other = tint, amount = TINT_LIGHT)
}

private fun Theme.brandHue(): Double = colors.brand.toOklch().hue
