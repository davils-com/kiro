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

package com.davils.kiro.ui.audio.spectrum

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import com.davils.kiro.ui.audio.signal.FrequencyRange
import com.davils.kiro.ui.audio.signal.Samples

private const val DEFAULT_CAPACITY: Int = 256

/**
 * The history a [Spectrogram] shows: the bands of the last spectra pushed into it, up to its capacity, the oldest
 * dropped first. It keeps the spectra as they are and computes nothing.
 *
 * @property capacity how many spectra the history keeps, one column of the spectrogram each.
 * @throws IllegalArgumentException if [capacity] is not positive.
 * @since 1.0.0
 */
@Stable
public class SpectrogramState(public val capacity: Int = DEFAULT_CAPACITY) {
    private val columns: Array<Samples?> = arrayOfNulls(capacity.coerceAtLeast(minimumValue = 1))

    init {
        require(capacity > 0) { "A spectrogram keeps at least one spectrum, not $capacity." }
    }

    /**
     * How many spectra were pushed since the history was created or cleared.
     *
     * @since 1.0.0
     */
    public var count: Long by mutableLongStateOf(0L)
        private set

    /**
     * The frequencies the bands of the newest spectrum cover, or `null` before the first spectrum.
     *
     * @since 1.0.0
     */
    public var range: FrequencyRange? by mutableStateOf(null)
        private set

    /**
     * Adds a spectrum as the newest column and drops the oldest one when the history is full.
     *
     * @param spectrum the spectrum to add.
     * @since 1.0.0
     */
    public fun push(spectrum: AudioSpectrum) {
        columns[slotOf(count)] = spectrum.magnitudes
        range = spectrum.range
        count += 1L
    }

    /**
     * Drops every spectrum.
     *
     * @since 1.0.0
     */
    public fun clear() {
        columns.fill(element = null)
        range = null
        count = 0L
    }

    internal fun columnAt(index: Long): Samples? = columns[slotOf(index)]

    internal fun slotOf(index: Long): Int = (index % capacity).toInt()
}

/**
 * Remembers a [SpectrogramState] across recompositions.
 *
 * @param capacity how many spectra the history keeps.
 * @return the remembered state.
 * @since 1.0.0
 */
@Composable
public fun rememberSpectrogramState(capacity: Int = DEFAULT_CAPACITY): SpectrogramState =
    remember(capacity) { SpectrogramState(capacity = capacity) }
