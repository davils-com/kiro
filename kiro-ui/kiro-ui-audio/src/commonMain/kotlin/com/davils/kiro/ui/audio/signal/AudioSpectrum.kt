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

package com.davils.kiro.ui.audio.signal

import androidx.compose.runtime.Immutable

private const val NONE: String = "none"

/**
 * The magnitude of each band of a signal at one moment, as an analyzer shows it. Every band is drawn as it is given;
 * the caller chooses the number of bands, their spacing and how they rise and fall.
 *
 * @property magnitudes the level of each band in decibels, from the lowest band to the highest.
 * @property range the frequencies the bands cover.
 * @property holds the peak of each band held for a while in decibels, or `null` to show none.
 * @throws IllegalArgumentException if [holds] has another number of bands than [magnitudes].
 * @since 1.0.0
 */
@Immutable
public class AudioSpectrum(
    public val magnitudes: Samples,
    public val range: FrequencyRange,
    public val holds: Samples? = null,
) {
    init {
        val bands = holds?.size ?: magnitudes.size
        require(bands == magnitudes.size) { "The spectrum has ${magnitudes.size} bands but $bands holds." }
    }

    /**
     * Returns a copy of this [AudioSpectrum] with the given values replaced and every other value kept.
     *
     * @param magnitudes the new value of [magnitudes].
     * @param range the new value of [range].
     * @param holds the new value of [holds].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        magnitudes: Samples = this.magnitudes,
        range: FrequencyRange = this.range,
        holds: Samples? = this.holds,
    ): AudioSpectrum = AudioSpectrum(magnitudes = magnitudes, range = range, holds = holds)

    override fun equals(other: Any?): Boolean = other is AudioSpectrum && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioSpectrum(" +
        "magnitudes=$magnitudes, " +
        "range=$range, " +
        "holds=${holds ?: NONE})"

    private fun values(): List<Any?> = listOf(magnitudes, range, holds)
}
