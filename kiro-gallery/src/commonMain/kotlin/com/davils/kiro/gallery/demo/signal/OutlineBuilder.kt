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

import com.davils.kiro.ui.audio.signal.Samples
import com.davils.kiro.ui.audio.signal.WaveformChannel
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

internal class OutlineBuilder(private val buckets: Int, private val total: Int) {
    private val minimums = FloatArray(buckets)
    private val maximums = FloatArray(buckets)
    private val squares = FloatArray(buckets)
    private val counts = IntArray(buckets)

    fun add(index: Int, value: Float) {
        val bucket = (index.toLong() * buckets / total).toInt()
        minimums[bucket] = min(a = minimums[bucket], b = value)
        maximums[bucket] = max(a = maximums[bucket], b = value)
        squares[bucket] += value * value
        counts[bucket] += 1
    }

    fun build(scale: Float): WaveformChannel {
        val rms = FloatArray(buckets) { bucket -> sqrt(squares[bucket] / max(a = counts[bucket], b = 1)) * scale }
        return WaveformChannel(
            minimums = Samples(FloatArray(buckets) { bucket -> minimums[bucket] * scale }),
            maximums = Samples(FloatArray(buckets) { bucket -> maximums[bucket] * scale }),
            rms = Samples(rms),
        )
    }

    companion object {
        fun loudestOf(left: FloatArray, right: FloatArray): Float {
            var loudest = 0f
            for (index in left.indices) {
                loudest = max(a = loudest, b = max(a = abs(left[index]), b = abs(right[index])))
            }
            return loudest
        }
    }
}
