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

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.art.ArtSeed
import com.davils.kiro.ui.motion.power.PowerPolicy
import com.davils.kiro.ui.motion.quality.QualityPolicy
import kotlin.time.Duration

/**
 * How a [MotionCanvas] plays its scene: whether and how fast it moves, at which quality, when it saves power and how it
 * follows the pointer.
 *
 * @property isAnimated whether the scene moves; a scene that does not shows the frame at [stillTime].
 * @property speed the factor of the speed of the time, on top of the speed of the playback.
 * @property quality decides the level of detail the layers draw at.
 * @property power decides when the clock stops and how often the canvas draws at most.
 * @property pointerSmoothing the share of the way to the pointer covered per sixtieth of a second; `1` jumps.
 * @property stillTime the point in time of the frame shown while the scene does not move.
 * @property seed the seed the seeds of the layers are derived from.
 * @property isInteractive whether the canvas follows the pointer.
 * @property diagnosticsInterval after how many frames the canvas reports its diagnostics again.
 * @since 1.0.0
 */
@Immutable
public class MotionStyle(
    public val isAnimated: Boolean,
    public val speed: Float,
    public val quality: QualityPolicy,
    public val power: PowerPolicy,
    public val pointerSmoothing: Float,
    public val stillTime: Duration,
    public val seed: ArtSeed,
    public val isInteractive: Boolean,
    public val diagnosticsInterval: Int,
) {
    /**
     * Returns a copy of this [MotionStyle] with the given values replaced and every other value kept.
     *
     * @param isAnimated the new value of [isAnimated].
     * @param speed the new value of [speed].
     * @param quality the new value of [quality].
     * @param power the new value of [power].
     * @param pointerSmoothing the new value of [pointerSmoothing].
     * @param stillTime the new value of [stillTime].
     * @param seed the new value of [seed].
     * @param isInteractive the new value of [isInteractive].
     * @param diagnosticsInterval the new value of [diagnosticsInterval].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        isAnimated: Boolean = this.isAnimated,
        speed: Float = this.speed,
        quality: QualityPolicy = this.quality,
        power: PowerPolicy = this.power,
        pointerSmoothing: Float = this.pointerSmoothing,
        stillTime: Duration = this.stillTime,
        seed: ArtSeed = this.seed,
        isInteractive: Boolean = this.isInteractive,
        diagnosticsInterval: Int = this.diagnosticsInterval,
    ): MotionStyle = MotionStyle(
        isAnimated = isAnimated,
        speed = speed,
        quality = quality,
        power = power,
        pointerSmoothing = pointerSmoothing,
        stillTime = stillTime,
        seed = seed,
        isInteractive = isInteractive,
        diagnosticsInterval = diagnosticsInterval,
    )

    override fun equals(other: Any?): Boolean = other is MotionStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MotionStyle(" +
        "isAnimated=$isAnimated, " +
        "speed=$speed, " +
        "quality=$quality, " +
        "power=$power, " +
        "pointerSmoothing=$pointerSmoothing, " +
        "stillTime=$stillTime, " +
        "seed=$seed, " +
        "isInteractive=$isInteractive, " +
        "diagnosticsInterval=$diagnosticsInterval)"

    private fun values(): List<Any?> = listOf(
        isAnimated,
        speed,
        quality,
        power,
        pointerSmoothing,
        stillTime,
        seed,
        isInteractive,
        diagnosticsInterval,
    )
}
