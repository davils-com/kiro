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

/**
 * The latest samples of a stereo signal and how alike its channels are, as an oscilloscope and a goniometer show them.
 *
 * @property left the samples of the left channel between `-1` and `1`.
 * @property right the samples of the right channel between `-1` and `1`, as many as [left].
 * @property correlation the correlation of the channels between `-1` for opposite phase and `1` for mono.
 * @throws IllegalArgumentException if [right] has another number of samples than [left] or [correlation] lies outside
 * of `-1` to `1`.
 * @since 1.0.0
 */
@Immutable
public class AudioScope(
    public val left: Samples,
    public val right: Samples,
    public val correlation: Float = 0f,
) {
    init {
        require(left.size == right.size) { "The left channel has ${left.size} samples but the right ${right.size}." }
        require(correlation in -1f..1f) { "The correlation must lie between -1 and 1, not $correlation." }
    }

    /**
     * Returns a copy of this [AudioScope] with the given values replaced and every other value kept.
     *
     * @param left the new value of [left].
     * @param right the new value of [right].
     * @param correlation the new value of [correlation].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        left: Samples = this.left,
        right: Samples = this.right,
        correlation: Float = this.correlation,
    ): AudioScope = AudioScope(left = left, right = right, correlation = correlation)

    override fun equals(other: Any?): Boolean = other is AudioScope && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AudioScope(" +
        "left=$left, " +
        "right=$right, " +
        "correlation=$correlation)"

    private fun values(): List<Any?> = listOf(left, right, correlation)
}
