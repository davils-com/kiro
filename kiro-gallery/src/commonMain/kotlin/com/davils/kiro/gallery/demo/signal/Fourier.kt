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

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.log10
import kotlin.math.sin
import kotlin.math.sqrt

private const val DECIBELS: Float = 20f
private const val FLOOR: Float = 1e-6f
private const val WINDOW_GAIN: Float = 0.5f

internal class Fourier(private val size: Int) {
    private val window = FloatArray(size) { index -> (0.5 - 0.5 * cos(2.0 * PI * index / (size - 1))).toFloat() }
    private val real = FloatArray(size)
    private val imaginary = FloatArray(size)

    val decibels: FloatArray = FloatArray(size / 2)

    fun analyze(samples: FloatArray) {
        for (index in 0 until size) {
            real[index] = samples[index] * window[index]
            imaginary[index] = 0f
        }
        reorder()
        transform()
        val scale = 2f / (size * WINDOW_GAIN)
        for (bin in decibels.indices) {
            val magnitude = sqrt(real[bin] * real[bin] + imaginary[bin] * imaginary[bin]) * scale
            decibels[bin] = DECIBELS * log10(magnitude + FLOOR)
        }
    }

    private fun reorder() {
        var target = 0
        for (index in 0 until size - 1) {
            if (index < target) {
                real[index] = real[target].also { real[target] = real[index] }
            }
            var bit = size shr 1
            while (target and bit != 0) {
                target = target xor bit
                bit = bit shr 1
            }
            target = target or bit
        }
    }

    private fun transform() {
        var length = 2
        while (length <= size) {
            val angle = -2.0 * PI / length
            for (start in 0 until size step length) {
                butterflies(start = start, length = length, angle = angle)
            }
            length = length shl 1
        }
    }

    private fun butterflies(start: Int, length: Int, angle: Double) {
        val half = length / 2
        for (offset in 0 until half) {
            val turn = angle * offset
            val cosine = cos(turn).toFloat()
            val sine = sin(turn).toFloat()
            val even = start + offset
            val odd = even + half
            val oddReal = real[odd] * cosine - imaginary[odd] * sine
            val oddImaginary = real[odd] * sine + imaginary[odd] * cosine
            real[odd] = real[even] - oddReal
            imaginary[odd] = imaginary[even] - oddImaginary
            real[even] += oddReal
            imaginary[even] += oddImaginary
        }
    }
}
