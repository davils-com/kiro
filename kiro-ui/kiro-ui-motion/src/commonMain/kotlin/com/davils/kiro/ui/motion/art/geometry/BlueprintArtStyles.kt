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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import com.davils.kiro.ui.motion.color.darkAware
import com.davils.kiro.ui.motion.quality.QualityLevel

private const val MAJOR: Int = 5
private const val MINOR_ALPHA_LIGHT: Float = 0.07f
private const val MINOR_ALPHA_DARK: Float = 0.08f
private const val MAJOR_ALPHA_LIGHT: Float = 0.15f
private const val MAJOR_ALPHA_DARK: Float = 0.17f
private const val MARK_ALPHA_LIGHT: Float = 0.4f
private const val MARK_ALPHA_DARK: Float = 0.45f
private const val BRACKET_ALPHA_LIGHT: Float = 0.45f
private const val BRACKET_ALPHA_DARK: Float = 0.6f
private const val LABEL_ALPHA_LIGHT: Float = 0.45f
private const val LABEL_ALPHA_DARK: Float = 0.55f
private const val LABEL_STEP: Int = 100
private const val SCAN_ALPHA_LIGHT: Float = 0.5f
private const val SCAN_ALPHA_DARK: Float = 0.65f
private const val BAND_ALPHA_LIGHT: Float = 0.07f
private const val BAND_ALPHA_DARK: Float = 0.1f
private const val LIT_ALPHA_LIGHT: Float = 0.8f
private const val LIT_ALPHA_DARK: Float = 0.9f
private const val SCAN_PERIOD: Int = 40

/**
 * Derives the style of a [BlueprintArt] from the tokens of a theme.
 *
 * Register an implementation under [BlueprintArtStyleKey] in the components of a theme to change how every
 * [BlueprintArt] without a style of its own looks.
 *
 * @since 1.0.0
 */
public fun interface BlueprintArtStyles {
    /**
     * Returns the style of a [BlueprintArt] for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): BlueprintArtStyle

    /**
     * Holds the default [BlueprintArtStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a [BlueprintArt].
         *
         * @since 1.0.0
         */
        public val Default: BlueprintArtStyles = BlueprintArtStyles(::defaultBlueprintArtStyle)
    }
}

/**
 * The key of the styles of a [BlueprintArt] in the components of a theme, with
 * [BlueprintArtStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val BlueprintArtStyleKey: StyleKey<BlueprintArtStyles> =
    StyleKey(name = "blueprint-art") { _ -> BlueprintArtStyles.Default }

/**
 * Returns the style of a [BlueprintArt] in the current theme, with the overrides of its components.
 *
 * @return the style.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun blueprintArtStyle(): BlueprintArtStyle = BlueprintArtStyleKey.current.resolve(Theme.current)

private fun rulerLabel(value: Int): String = value.toString()

internal fun defaultBlueprintArtStyle(theme: Theme): BlueprintArtStyle {
    val spacing = theme.spacing
    val long = theme.motion.durations.long
    val size = theme.typography.scale.xs
    val labelStyle = TextStyle(
        fontFamily = theme.typography.families.mono,
        fontSize = size.fontSize,
        lineHeight = size.lineHeight,
    )
    return BlueprintArtStyle(
        cell = spacing(steps = 4f),
        major = MAJOR,
        lineWidth = theme.borders.thin,
        minorAlpha = theme.darkAware(light = MINOR_ALPHA_LIGHT, dark = MINOR_ALPHA_DARK),
        majorAlpha = theme.darkAware(light = MAJOR_ALPHA_LIGHT, dark = MAJOR_ALPHA_DARK),
        mark = spacing(steps = 1.5f),
        markAlpha = theme.darkAware(light = MARK_ALPHA_LIGHT, dark = MARK_ALPHA_DARK),
        margin = spacing(steps = 6f),
        bracket = spacing(steps = 5f),
        bracketWidth = theme.borders.thin,
        bracketAlpha = theme.darkAware(light = BRACKET_ALPHA_LIGHT, dark = BRACKET_ALPHA_DARK),
        isRulerShown = true,
        labelStyle = labelStyle,
        labelAlpha = theme.darkAware(light = LABEL_ALPHA_LIGHT, dark = LABEL_ALPHA_DARK),
        labelPadding = spacing(steps = 1f),
        labelStep = LABEL_STEP,
        label = ::rulerLabel,
        scanWidth = theme.borders.thin,
        scanAlpha = theme.darkAware(light = SCAN_ALPHA_LIGHT, dark = SCAN_ALPHA_DARK),
        scanBand = spacing(steps = 12f),
        bandAlpha = theme.darkAware(light = BAND_ALPHA_LIGHT, dark = BAND_ALPHA_DARK),
        bandQuality = QualityLevel.Medium,
        litAlpha = theme.darkAware(light = LIT_ALPHA_LIGHT, dark = LIT_ALPHA_DARK),
        scanPeriod = long * SCAN_PERIOD,
        easing = theme.motion.easings.ease,
        salt = 0,
        line = null,
        accent = null,
        background = null,
    )
}
