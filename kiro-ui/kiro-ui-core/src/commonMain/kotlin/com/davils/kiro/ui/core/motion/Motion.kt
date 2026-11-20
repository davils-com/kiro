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

package com.davils.kiro.ui.core.motion

import androidx.compose.runtime.Immutable

/**
 * The motion of a theme: durations, curves, springs, the motion of overlays and of ambient animations.
 *
 * @property durations the durations of transitions.
 * @property easings the curves of transitions.
 * @property springs the springs.
 * @property overlay how overlays appear and disappear.
 * @property ambient the motion that runs on its own.
 * @property isReduced whether motion is reduced, as the user may ask the system; components then change without
 *   transitions.
 * @since 0.1.0
 */
@Immutable
public class Motion(
    public val durations: Durations = Durations(),
    public val easings: Easings = Easings(),
    public val springs: MotionSprings = MotionSprings(),
    public val overlay: OverlayMotion = OverlayMotion(),
    public val ambient: AmbientMotion = AmbientMotion(),
    public val isReduced: Boolean = false,
) {
    /**
     * Returns a copy of this [Motion] with the given values replaced and every other value kept.
     *
     * @param durations the new value of [durations].
     * @param easings the new value of [easings].
     * @param springs the new value of [springs].
     * @param overlay the new value of [overlay].
     * @param ambient the new value of [ambient].
     * @param isReduced the new value of [isReduced].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        durations: Durations = this.durations,
        easings: Easings = this.easings,
        springs: MotionSprings = this.springs,
        overlay: OverlayMotion = this.overlay,
        ambient: AmbientMotion = this.ambient,
        isReduced: Boolean = this.isReduced,
    ): Motion = Motion(
        durations = durations,
        easings = easings,
        springs = springs,
        overlay = overlay,
        ambient = ambient,
        isReduced = isReduced,
    )

    override fun equals(other: Any?): Boolean = other is Motion && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Motion(" +
        "durations=$durations, " +
        "easings=$easings, " +
        "springs=$springs, " +
        "overlay=$overlay, " +
        "ambient=$ambient, " +
        "isReduced=$isReduced)"

    private fun values(): List<Any?> = listOf(
        durations,
        easings,
        springs,
        overlay,
        ambient,
        isReduced,
    )
}
