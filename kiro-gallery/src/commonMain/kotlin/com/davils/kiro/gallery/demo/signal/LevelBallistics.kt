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

package com.davils.kiro.gallery.demo.signal

import com.davils.kiro.ui.audio.signal.ChannelLevel
import com.davils.kiro.ui.audio.signal.Decibels
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.sqrt

private const val DECIBELS: Float = 20f
private const val SILENCE: Float = -120f
private const val FALL_PER_SECOND: Float = 20f
private const val HOLD_SECONDS: Float = 1.5f
private const val HOLD_FALL_PER_SECOND: Float = 12f
private const val CLIP: Float = -1f

internal class LevelBallistics {
    private var peak: Float = SILENCE
    private var hold: Float = SILENCE
    private var holding: Float = 0f
    private var isClipping: Boolean = false

    fun update(latest: FloatArray, block: FloatArray, seconds: Float): ChannelLevel {
        var loudest = 0f
        latest.forEach { sample -> loudest = max(a = loudest, b = abs(sample)) }
        var squares = 0f
        block.forEach { sample -> squares += sample * sample }
        val measured = decibelsOf(loudest)
        peak = max(a = measured, b = peak - FALL_PER_SECOND * seconds)
        holding -= seconds
        if (peak >= hold) {
            hold = peak
            holding = HOLD_SECONDS
        }
        if (holding < 0f) hold = max(a = peak, b = hold - HOLD_FALL_PER_SECOND * seconds)
        isClipping = isClipping || measured >= CLIP
        val rms = decibelsOf(sqrt(squares / block.size.coerceAtLeast(minimumValue = 1)))
        return ChannelLevel(peak = Decibels(peak), rms = Decibels(rms), hold = Decibels(hold), isClipping = isClipping)
    }

    fun resetClip() {
        isClipping = false
    }

    private fun decibelsOf(amplitude: Float): Float {
        if (amplitude <= 0f) return SILENCE
        return DECIBELS * log10(amplitude)
    }
}
