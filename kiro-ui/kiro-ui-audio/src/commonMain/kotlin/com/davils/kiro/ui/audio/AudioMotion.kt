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

package com.davils.kiro.ui.audio

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import com.davils.kiro.ui.core.motion.Motion

internal fun <T> Motion.resizing(): FiniteAnimationSpec<T> {
    if (isReduced) return snap()
    return tween(durationMillis = durations.medium.inWholeMilliseconds.toInt(), easing = easings.emphasized)
}

internal fun <T> Motion.fading(): FiniteAnimationSpec<T> {
    if (isReduced) return snap()
    return tween(durationMillis = durations.long.inWholeMilliseconds.toInt(), easing = easings.standard)
}

internal fun <T> Motion.springing(): FiniteAnimationSpec<T> {
    if (isReduced) return snap()
    return spring(dampingRatio = springs.spatial.dampingRatio, stiffness = springs.spatial.stiffness)
}
