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

package com.davils.kiro.ui.audio.meter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.signal.DecibelRange
import com.davils.kiro.ui.audio.signal.Decibels
import com.davils.kiro.ui.audio.tenthsOf
import com.davils.kiro.ui.audio.wholeDecibelsOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current

private const val FLOOR: Float = -60f
private const val CEILING: Float = 0f
private const val WARNING: Float = -18f
private const val DANGER: Float = -6f
private const val PEAK_ALPHA: Float = 0.45f
private const val SMALL: Float = 0.8f
private const val TABULAR: String = "tnum"

private val marks: List<Float> = listOf(0f, -3f, -6f, -12f, -18f, -24f, -36f, -48f, -60f)
private val stereo: List<String> = listOf("L", "R")

/**
 * Derives the style of a [LevelMeter] from the tokens of a theme.
 *
 * Register an implementation under [LevelMeterStyleKey] in the components of a theme to change how every level meter
 * looks.
 *
 * @since 1.0.0
 */
public fun interface LevelMeterStyles {
    /**
     * Returns the style of a level meter for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the level meter.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): LevelMeterStyle

    /**
     * Holds the default [LevelMeterStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a level meter.
         *
         * @since 1.0.0
         */
        public val Default: LevelMeterStyles = LevelMeterStyles(::defaultLevelMeterStyle)
    }
}

/**
 * The key of the styles of a [LevelMeter] in the components of a theme, with [LevelMeterStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val LevelMeterStyleKey: StyleKey<LevelMeterStyles> =
    StyleKey(name = "level-meter") { _ -> LevelMeterStyles.Default }

/**
 * Returns the style of a [LevelMeter] in the current theme, with the overrides of its components.
 *
 * @return the style of the level meter.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun levelMeterStyle(): LevelMeterStyle = LevelMeterStyleKey.current.resolve(Theme.current)

internal fun defaultLevelMeterStyle(theme: Theme): LevelMeterStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    val status = colors.status
    val small = TextStyle(
        fontFamily = theme.typography.families.mono,
        fontSize = theme.typography.scale.xs.fontSize * SMALL,
        fontFeatureSettings = TABULAR,
    )
    return LevelMeterStyle(
        thickness = spacing(steps = 3f),
        length = spacing(steps = 56f),
        channelGap = spacing(steps = 1f),
        radius = spacing(steps = 0.5f),
        range = DecibelRange(floor = Decibels(FLOOR), ceiling = Decibels(CEILING)),
        marks = marks.map(::Decibels),
        warning = Decibels(WARNING),
        danger = Decibels(DANGER),
        safeColor = status.success.foreground,
        warningColor = status.warning.foreground,
        dangerColor = status.error.foreground,
        track = colors.muted,
        peakAlpha = PEAK_ALPHA,
        holdThickness = theme.borders.thick,
        mode = MeterMode.Continuous,
        segment = spacing(steps = 1f),
        segmentGap = spacing(steps = 0.5f),
        clipSize = spacing(steps = 2f),
        clipGap = spacing(steps = 1f),
        clipColor = colors.destructive,
        clipIdle = colors.muted,
        scaleGap = spacing(steps = 1f),
        tickLength = spacing(steps = 1f),
        tickColor = colors.border,
        scaleStyle = small,
        labelStyle = small,
        labelColor = colors.mutedForeground,
        response = theme.motion.durations.short,
        motion = theme.motion,
        scaleLabel = { level -> wholeDecibelsOf(level.value) },
        readout = { level -> tenthsOf(level.value) },
        channel = ::channelOf,
        description = Messages.LEVEL_METER,
        resetClip = Messages.RESET_CLIP,
    )
}

private fun channelOf(index: Int, count: Int): String =
    stereo.getOrNull(index).takeIf { count == stereo.size } ?: (index + 1).toString()
