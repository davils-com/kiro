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

package com.davils.kiro.ui.motion.host

import androidx.compose.ui.geometry.Offset
import com.davils.kiro.ui.motion.input.ArtInputs
import com.davils.kiro.ui.motion.input.PointerTracker
import com.davils.kiro.ui.motion.power.isStill
import com.davils.kiro.ui.motion.quality.FrameBudget
import com.davils.kiro.ui.motion.quality.QualityLevel
import com.davils.kiro.ui.motion.time.ArtPlayback
import com.davils.kiro.ui.motion.time.ArtTime

internal data class HostConfig(
    val style: MotionStyle,
    val isReducedMotion: Boolean,
    val isStatic: Boolean,
    val external: ArtInputs,
    val layers: Int,
    val quality: QualityLevel,
    val budget: FrameBudget,
) {
    val isStill: Boolean = style.power.isStill(isAnimated = style.isAnimated, isReducedMotion = isReducedMotion)

    val isPointerEnabled: Boolean = style.isInteractive && !isReducedMotion && !isStill
}

internal fun HostConfig.timeOf(playback: ArtPlayback): ArtTime {
    if (isStill) return ArtTime.of(style.stillTime)
    return playback.time
}

internal fun HostConfig.inputsOf(tracker: PointerTracker): ArtInputs {
    if (!isPointerEnabled) return external.copy(pointer = null, velocity = Offset.Zero, isPressed = false)
    return tracker.inputsOf(external)
}
