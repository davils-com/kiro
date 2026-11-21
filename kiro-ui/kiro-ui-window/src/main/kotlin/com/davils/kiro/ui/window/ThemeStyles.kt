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

package com.davils.kiro.ui.window

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.motion.Motion
import com.davils.kiro.ui.core.typography.TextSize
import kotlin.time.Duration

internal val theme: Theme
    @Composable
    @ReadOnlyComposable
    get() = Theme.current

internal fun Theme.textStyleOf(
    size: TextSize,
    weight: FontWeight = FontWeight.Normal,
    family: FontFamily = typography.families.sans,
): TextStyle = TextStyle(
    fontFamily = family,
    fontSize = size.fontSize,
    lineHeight = size.lineHeight,
    fontWeight = weight,
    lineHeightStyle = LineHeightStyle(alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.None),
)

internal fun <T> Theme.darkAware(light: T, dark: T): T {
    if (isDark) return dark
    return light
}

internal fun Theme.colorTransition(): AnimationSpec<Color> =
    motion.tweenOf(duration = motion.durations.short, easing = motion.easings.standard)

internal fun <T> Motion.fading(): FiniteAnimationSpec<T> = tweenOf(duration = durations.long, easing = easings.standard)

internal fun <T> Motion.sliding(): FiniteAnimationSpec<T> = tweenOf(
    duration = durations.long,
    easing = easings.emphasized,
)

private fun <T> Motion.tweenOf(duration: Duration, easing: Easing): FiniteAnimationSpec<T> {
    if (isReduced) return snap()
    return tween(durationMillis = duration.inWholeMilliseconds.toInt(), easing = easing)
}
