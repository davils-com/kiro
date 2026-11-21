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
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin

private const val TWO_PI: Float = (2.0 * PI).toFloat()
private const val NOISE_BITS: Int = 24
private const val NOISE_SHIFT: Int = 40
private const val SEED: Long = -0x61C8864680B583EBL
private const val LEFT_SHIFT: Int = 13
private const val RIGHT_SHIFT: Int = 7
private const val WIDE_SHIFT: Int = 17
private const val A4: Float = 440f
private const val A4_NOTE: Int = 69
private const val SEMITONES: Float = 12f

private const val KICK_LOW: Float = 45f
private const val KICK_SWEEP: Float = 105f
private const val KICK_DROP: Float = 40f
private const val KICK_DECAY: Float = 7f
private const val KICK_LEVEL: Float = 0.9f
private const val SNARE_DECAY: Float = 18f
private const val SNARE_TONE: Float = 185f
private const val SNARE_TONE_DECAY: Float = 25f
private const val SNARE_NOISE: Float = 0.45f
private const val SNARE_BODY: Float = 0.3f
private const val HAT_CLOSED: Float = 60f
private const val HAT_OPEN: Float = 11f
private const val HAT_LEVEL: Float = 0.16f
private const val BASS_HARMONICS: Int = 6
private const val BASS_DECAY: Float = 3f
private const val BASS_LEVEL: Float = 0.32f
private const val ATTACK: Float = 0.005f
private const val PAD_ATTACK: Float = 0.35f
private const val PAD_LEVEL: Float = 0.07f
private const val PAD_OCTAVE: Float = 0.25f
private const val DETUNE: Float = 0.004f
private const val LEAD_DECAY: Float = 5f
private const val LEAD_LEVEL: Float = 0.11f
private const val THIRD: Float = 3f
private const val FIFTH: Float = 5f

internal fun noise(index: Long): Float {
    var x = (index + 1L) * SEED
    x = x xor (x shl LEFT_SHIFT)
    x = x xor (x ushr RIGHT_SHIFT)
    x = x xor (x shl WIDE_SHIFT)
    return (x ushr NOISE_SHIFT).toFloat() / (1 shl NOISE_BITS) * 2f - 1f
}

internal fun frequencyOf(note: Int): Float = A4 * 2f.pow((note - A4_NOTE) / SEMITONES)

internal fun kick(time: Float): Float {
    val phase = TWO_PI * (KICK_LOW * time + KICK_SWEEP * (1f - exp(-KICK_DROP * time)) / KICK_DROP)
    return sin(phase) * exp(-KICK_DECAY * time) * KICK_LEVEL
}

internal fun snare(time: Float, index: Long): Float {
    val body = sin(TWO_PI * SNARE_TONE * time) * exp(-SNARE_TONE_DECAY * time) * SNARE_BODY
    return noise(index) * exp(-SNARE_DECAY * time) * SNARE_NOISE + body
}

internal fun hat(time: Float, index: Long, isOpen: Boolean): Float {
    val decay = HAT_OPEN.takeIf { isOpen } ?: HAT_CLOSED
    val bright = (noise(index) - noise(index - 1L)) / 2f
    return bright * exp(-decay * time) * HAT_LEVEL
}

internal fun bass(time: Float, frequency: Float, length: Float): Float {
    if (time > length) return 0f
    var saw = 0f
    for (harmonic in 1..BASS_HARMONICS) {
        saw += sin(TWO_PI * frequency * harmonic * time) / harmonic
    }
    val attack = (time / ATTACK).coerceAtMost(maximumValue = 1f)
    return saw * attack * exp(-BASS_DECAY * time) * BASS_LEVEL
}

internal fun pad(time: Float, notes: List<Int>, side: Float, length: Float): Float {
    val attack = (time / PAD_ATTACK).coerceAtMost(maximumValue = 1f)
    val release = ((length - time) / PAD_ATTACK).coerceIn(minimumValue = 0f, maximumValue = 1f)
    val detune = 1f + DETUNE * side
    val sum = notes.sumOf { note ->
        val frequency = frequencyOf(note) * detune
        (sin(TWO_PI * frequency * time) + sin(TWO_PI * frequency * 2f * time) * PAD_OCTAVE).toDouble()
    }
    return sum.toFloat() * attack * release * PAD_LEVEL
}

internal fun lead(time: Float, frequency: Float): Float {
    val phase = TWO_PI * frequency * time
    val square = sin(phase) + sin(phase * THIRD) / THIRD + sin(phase * FIFTH) / FIFTH
    val attack = (time / ATTACK).coerceAtMost(maximumValue = 1f)
    return square * attack * exp(-LEAD_DECAY * time) * LEAD_LEVEL
}
