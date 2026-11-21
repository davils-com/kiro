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

import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration.Companion.nanoseconds

private const val NANOS_PER_SECOND: Double = 1_000_000_000.0

internal class FrameMeter(interval: Int) {
    private val interval: Int = interval.coerceAtLeast(minimumValue = 1)
    private var frames: Int = 0
    private var nanos: Long = 0L

    fun record(frameNanos: Long, layers: Int, quality: QualityLevel): ArtDiagnostics? {
        if (frameNanos > 0L) count(frameNanos)
        if (frames < interval) return null
        val diagnostics = ArtDiagnostics(
            framesPerSecond = (frames * NANOS_PER_SECOND / nanos).toFloat(),
            frameTime = (nanos / frames).nanoseconds,
            layers = layers,
            quality = quality,
        )
        frames = 0
        nanos = 0L
        return diagnostics
    }

    private fun count(frameNanos: Long) {
        frames += 1
        nanos += frameNanos
    }
}
