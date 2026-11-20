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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.davils.kiro.ui.components.primitive.motion.millisOf
import com.davils.kiro.ui.core.motion.Motion
import kotlin.time.Duration

internal fun dialogTransitions(motion: Motion): ModalTransitions {
    if (motion.isReduced) return ModalTransitions.None
    val enter = motion.durations.medium
    val exit = motion.durations.short
    val scale = motion.overlay.initialScale
    return ModalTransitions(
        scrimEnter = fadeIn(specOf(enter, motion.easings.standard)),
        scrimExit = fadeOut(specOf(exit, motion.easings.exit)),
        panelEnter = fadeIn(specOf(enter, motion.easings.emphasized)) +
            scaleIn(specOf(enter, motion.easings.emphasized), initialScale = scale),
        panelExit = fadeOut(
            specOf(exit, motion.easings.exit),
        ) +
            scaleOut(specOf(exit, motion.easings.exit), targetScale = scale),
    )
}

internal fun sheetTransitions(motion: Motion, physical: Physical): ModalTransitions = edgeTransitions(
    motion,
    physical,
)

internal fun drawerTransitions(motion: Motion, physical: Physical): ModalTransitions = edgeTransitions(
    motion,
    physical,
)

internal fun outwards(physical: Physical, distance: Int): IntOffset = when (physical) {
    Physical.Top -> IntOffset(x = 0, y = -distance)
    Physical.Bottom -> IntOffset(x = 0, y = distance)
    Physical.Left -> IntOffset(x = -distance, y = 0)
    Physical.Right -> IntOffset(x = distance, y = 0)
}

private fun edgeTransitions(motion: Motion, physical: Physical): ModalTransitions {
    if (motion.isReduced) return ModalTransitions.None
    val offset = { size: IntSize -> outwards(physical = physical, distance = maxOf(a = size.width, b = size.height)) }
    val enter = motion.durations.long
    val exit = motion.durations.medium
    return ModalTransitions(
        scrimEnter = fadeIn(specOf(enter, motion.easings.standard)),
        scrimExit = fadeOut(specOf(exit, motion.easings.standard)),
        panelEnter = slideIn(specOf(enter, motion.easings.emphasized), offset),
        panelExit = slideOut(specOf(exit, motion.easings.exit), offset),
    )
}

private fun <T> specOf(duration: Duration, easing: Easing): FiniteAnimationSpec<T> =
    tween(durationMillis = millisOf(duration), easing = easing)
