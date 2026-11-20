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

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.primitive.motion.millisOf
import com.davils.kiro.ui.core.motion.Motion

@Immutable
internal data class OverlayTransitions(val enter: EnterTransition, val exit: ExitTransition)

internal fun overlayTransitionsOf(motion: Motion, physical: Physical, slide: Int): OverlayTransitions {
    if (motion.isReduced) return OverlayTransitions(enter = EnterTransition.None, exit = ExitTransition.None)
    val overlay = motion.overlay
    val origin = originOf(physical)
    val entering = millisOf(overlay.enterDuration)
    val leaving = millisOf(overlay.exitDuration)
    val easings = motion.easings
    val enter = fadeIn(tween(entering, easing = easings.outExpo), initialAlpha = overlay.initialAlpha) +
        scaleIn(
            tween(entering, easing = easings.outExpo),
            initialScale = overlay.initialScale,
            transformOrigin = origin,
        ) +
        slideIn(tween(entering, easing = easings.outExpo)) { offsetOf(physical = physical, slide = slide) }
    val exit = fadeOut(tween(leaving, easing = easings.exit), targetAlpha = overlay.initialAlpha) +
        scaleOut(tween(leaving, easing = easings.exit), targetScale = overlay.initialScale, transformOrigin = origin) +
        slideOut(tween(leaving, easing = easings.exit)) { offsetOf(physical = physical, slide = slide) }
    return OverlayTransitions(enter = enter, exit = exit)
}

internal fun slideOf(distance: Dp, gap: Dp): Dp = distance.coerceIn(0.dp, gap.coerceAtLeast(0.dp))

private fun originOf(physical: Physical): TransformOrigin = when (physical) {
    Physical.Top -> TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 1f)
    Physical.Bottom -> TransformOrigin(pivotFractionX = 0.5f, pivotFractionY = 0f)
    Physical.Left -> TransformOrigin(pivotFractionX = 1f, pivotFractionY = 0.5f)
    Physical.Right -> TransformOrigin(pivotFractionX = 0f, pivotFractionY = 0.5f)
}

private fun offsetOf(physical: Physical, slide: Int): IntOffset = when (physical) {
    Physical.Top -> IntOffset(x = 0, y = slide)
    Physical.Bottom -> IntOffset(x = 0, y = -slide)
    Physical.Left -> IntOffset(x = slide, y = 0)
    Physical.Right -> IntOffset(x = -slide, y = 0)
}
