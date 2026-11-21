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
 * A sequence of values of a signal that cannot change, such as the samples of a block or the magnitudes of the bands
 * of a spectrum. It keeps its own copy of the values it is created from, so that the caller may reuse its array for
 * the next block.
 *
 * @param values the values, copied.
 * @since 1.0.0
 */
@Immutable
public class Samples(values: FloatArray) {
    internal val values: FloatArray = values.copyOf()

    /**
     * The number of values.
     *
     * @since 1.0.0
     */
    public val size: Int
        get() = values.size

    /**
     * Returns a value.
     *
     * @param index the position of the value, from `0` to [size] minus one.
     * @return the value.
     * @throws IndexOutOfBoundsException if [index] lies outside of the values.
     * @since 1.0.0
     */
    public operator fun get(index: Int): Float = values[index]

    /**
     * Returns whether there are no values.
     *
     * @return `true` when [size] is `0`.
     * @since 1.0.0
     */
    public fun isEmpty(): Boolean = values.isEmpty()

    /**
     * Returns a copy of the values that the caller may change.
     *
     * @return the copy.
     * @since 1.0.0
     */
    public fun toFloatArray(): FloatArray = values.copyOf()

    override fun equals(other: Any?): Boolean = other is Samples && values.contentEquals(other.values)

    override fun hashCode(): Int = values.contentHashCode()

    override fun toString(): String = "Samples(size=$size)"

    /**
     * Creates [Samples].
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * Samples without a value.
         *
         * @since 1.0.0
         */
        public val Empty: Samples = Samples(FloatArray(size = 0))

        /**
         * Returns samples of the given values.
         *
         * @param values the values.
         * @return the samples.
         * @since 1.0.0
         */
        public fun of(vararg values: Float): Samples = Samples(values)
    }
}
