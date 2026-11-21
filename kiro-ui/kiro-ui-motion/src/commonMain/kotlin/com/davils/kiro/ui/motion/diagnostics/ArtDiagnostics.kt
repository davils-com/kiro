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

package com.davils.kiro.ui.motion.diagnostics

import androidx.compose.runtime.Immutable
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [com.davils.kiro.ui.motion.host.MotionCanvas] performs, measured over the last frames.
 *
 * @property framesPerSecond how many frames the canvas drew in a second.
 * @property frameTime the average time between two frames.
 * @property layers the number of visible layers.
 * @property quality the level of detail the layers draw at.
 * @since 1.0.0
 */
@Immutable
public class ArtDiagnostics(
    public val framesPerSecond: Float,
    public val frameTime: Duration,
    public val layers: Int,
    public val quality: QualityLevel,
) {
    /**
     * Returns a copy of this [ArtDiagnostics] with the given values replaced and every other value kept.
     *
     * @param framesPerSecond the new value of [framesPerSecond].
     * @param frameTime the new value of [frameTime].
     * @param layers the new value of [layers].
     * @param quality the new value of [quality].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        framesPerSecond: Float = this.framesPerSecond,
        frameTime: Duration = this.frameTime,
        layers: Int = this.layers,
        quality: QualityLevel = this.quality,
    ): ArtDiagnostics = ArtDiagnostics(
        framesPerSecond = framesPerSecond,
        frameTime = frameTime,
        layers = layers,
        quality = quality,
    )

    override fun equals(other: Any?): Boolean = other is ArtDiagnostics && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ArtDiagnostics(" +
        "framesPerSecond=$framesPerSecond, " +
        "frameTime=$frameTime, " +
        "layers=$layers, " +
        "quality=$quality)"

    private fun values(): List<Any?> = listOf(
        framesPerSecond,
        frameTime,
        layers,
        quality,
    )
}
