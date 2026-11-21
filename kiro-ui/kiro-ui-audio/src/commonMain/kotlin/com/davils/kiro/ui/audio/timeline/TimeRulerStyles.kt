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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.audio.Messages
import com.davils.kiro.ui.audio.timecodeOf
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.spacing.invoke
import com.davils.kiro.ui.core.style.StyleKey
import com.davils.kiro.ui.core.style.current
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private const val MINOR_COUNT: Int = 4
private const val FRAMES_PER_SECOND: Int = 30
private const val SMALL: Float = 0.8f
private const val TABULAR: String = "tnum"

private val intervals: List<Duration> = listOf(
    100.milliseconds,
    250.milliseconds,
    500.milliseconds,
    1.seconds,
    2.seconds,
    5.seconds,
    10.seconds,
    15.seconds,
    30.seconds,
    1.minutes,
    2.minutes,
    5.minutes,
    10.minutes,
)

/**
 * Derives the style of a [TimeRuler] from the tokens of a theme.
 *
 * Register an implementation under [TimeRulerStyleKey] in the components of a theme to change how every time ruler
 * looks.
 *
 * @since 1.0.0
 */
public fun interface TimeRulerStyles {
    /**
     * Returns the style of a time ruler for a theme.
     *
     * @param theme the theme whose tokens the style follows.
     * @return the style of the time ruler.
     * @since 1.0.0
     */
    public fun resolve(theme: Theme): TimeRulerStyle

    /**
     * Holds the default [TimeRulerStyles].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * The default style of a time ruler.
         *
         * @since 1.0.0
         */
        public val Default: TimeRulerStyles = TimeRulerStyles(::defaultTimeRulerStyle)
    }
}

/**
 * The key of the styles of a [TimeRuler] in the components of a theme, with [TimeRulerStyles.Default] as its default.
 *
 * @since 1.0.0
 */
public val TimeRulerStyleKey: StyleKey<TimeRulerStyles> =
    StyleKey(name = "time-ruler") { _ -> TimeRulerStyles.Default }

/**
 * Returns the style of a [TimeRuler] in the current theme, with the overrides of its components.
 *
 * @return the style of the time ruler.
 * @since 1.0.0
 */
@Composable
@ReadOnlyComposable
public fun timeRulerStyle(): TimeRulerStyle = TimeRulerStyleKey.current.resolve(Theme.current)

internal fun defaultTimeRulerStyle(theme: Theme): TimeRulerStyle {
    val spacing = theme.spacing
    val colors = theme.colors
    return TimeRulerStyle(
        height = spacing(steps = 7f),
        background = colors.card,
        tickColor = colors.mutedForeground,
        tickWidth = theme.borders.thin,
        majorTick = spacing(steps = 3f),
        minorTick = spacing(steps = 1.5f),
        minorCount = MINOR_COUNT,
        labelStyle = TextStyle(
            fontFamily = theme.typography.families.mono,
            fontSize = theme.typography.scale.xs.fontSize * SMALL,
            fontFeatureSettings = TABULAR,
        ),
        labelColor = colors.mutedForeground,
        labelPadding = spacing(steps = 1f),
        minimumSpacing = spacing(steps = 24f),
        intervals = intervals,
        time = { time -> timecodeOf(time = time, framesPerSecond = FRAMES_PER_SECOND) },
        playheadColor = colors.destructive,
        playheadSize = spacing(steps = 3f),
        description = Messages.TIME_RULER,
    )
}
