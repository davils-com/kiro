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

package com.davils.kiro.ui.components.primitive.motion

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.core.motion.MotionSpring
import kotlin.time.Duration

internal fun <T> Theme.sizeTransition(): FiniteAnimationSpec<T> =
    tweenOf(duration = motion.durations.medium, easing = motion.easings.emphasized)

internal fun <T> Theme.fadeTransition(): FiniteAnimationSpec<T> =
    tweenOf(duration = motion.durations.short, easing = motion.easings.standard)

internal fun <T> Theme.spatialSpring(): FiniteAnimationSpec<T> = springOf(motion.springs.spatial)

internal fun <T> Theme.effectsSpring(): FiniteAnimationSpec<T> = springOf(motion.springs.effects)

internal fun <T> Theme.tweenOf(duration: Duration, easing: Easing): FiniteAnimationSpec<T> {
    if (motion.isReduced) return snap()
    return tween(durationMillis = millisOf(duration), easing = easing)
}

internal fun millisOf(duration: Duration): Int = duration.inWholeMilliseconds.toInt()

private fun <T> Theme.springOf(token: MotionSpring): FiniteAnimationSpec<T> {
    if (motion.isReduced) return snap()
    return spring(dampingRatio = token.dampingRatio, stiffness = token.stiffness)
}
