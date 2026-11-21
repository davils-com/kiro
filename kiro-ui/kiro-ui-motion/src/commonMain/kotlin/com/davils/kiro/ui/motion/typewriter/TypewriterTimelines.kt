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

package com.davils.kiro.ui.motion.typewriter

import androidx.compose.animation.core.Easing

internal fun timelineOf(script: TypewriterScript, style: TypewriterStyle, seed: Int): TypewriterTimeline {
    val firstType = script.steps.firstNotNullOfOrNull { step -> step as? TypeStep }
    val first = firstType?.text.orEmpty()
    val writer = TimelineWriter(style = style, seed = seed, firstTarget = first)
    for (step in script.steps) writer.play(step)
    return TypewriterTimeline(
        marks = writer.marks,
        duration = writer.elapsed,
        isRepeating = script.isRepeating,
        resting = restingOf(isRepeating = script.isRepeating, last = writer.current, first = first),
    )
}

internal fun caretAlphaOf(sinceChange: Long, period: Long, fade: Long, easing: Easing): Float {
    if (period <= 0L) return 1f
    val position = sinceChange.coerceAtLeast(minimumValue = 0L).mod(period)
    val ramp = fade.coerceIn(minimumValue = 0L, maximumValue = period / 2)
    return blinkOf(position = position, period = period, ramp = ramp, easing = easing)
}

private fun blinkOf(position: Long, period: Long, ramp: Long, easing: Easing): Float {
    val half = period / 2
    if (position < half) return 1f - easedOf(progress = position - (half - ramp), length = ramp, easing = easing)
    return easedOf(progress = position - (period - ramp), length = ramp, easing = easing)
}

private fun easedOf(progress: Long, length: Long, easing: Easing): Float {
    if (progress < 0L) return 0f
    val fraction = progress.toFloat() / length.coerceAtLeast(minimumValue = 1L)
    return easing.transform(fraction.coerceIn(minimumValue = 0f, maximumValue = 1f))
}

private fun restingOf(isRepeating: Boolean, last: String, first: String): String {
    if (isRepeating || last.isEmpty()) return first
    return last
}
