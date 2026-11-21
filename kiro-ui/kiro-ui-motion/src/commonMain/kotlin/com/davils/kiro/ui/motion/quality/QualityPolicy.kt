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

package com.davils.kiro.ui.motion.quality

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

/**
 * Decides the [QualityLevel] a scene draws at.
 *
 * @since 1.0.0
 */
@Immutable
public sealed interface QualityPolicy {
    /**
     * Always draws at one level.
     *
     * @property level the level.
     * @since 1.0.0
     */
    @Immutable
    public class Fixed(public val level: QualityLevel) : QualityPolicy {
        /**
         * Returns a copy of this policy with the given level.
         *
         * @param level the new value of [level].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(level: QualityLevel = this.level): Fixed = Fixed(level = level)

        override fun equals(other: Any?): Boolean = other is Fixed && level == other.level

        override fun hashCode(): Int = level.hashCode()

        override fun toString(): String = "Fixed(level=$level)"
    }

    /**
     * Starts at [initial] and steps down when frames take longer than [budget], and up again when they stay well
     * below it, between [minimum] and [maximum].
     *
     * @property budget the time a frame may take on average.
     * @property initial the level to start at.
     * @property minimum the lowest level.
     * @property maximum the highest level.
     * @property headroom the share of [budget] below which the level steps up again.
     * @property smoothing how strongly a new frame time moves the average, between `0` and `1`.
     * @property patience how many frames over or under the budget it takes to change the level.
     * @since 1.0.0
     */
    @Immutable
    public class Adaptive(
        public val budget: Duration,
        public val initial: QualityLevel,
        public val minimum: QualityLevel,
        public val maximum: QualityLevel,
        public val headroom: Float,
        public val smoothing: Float,
        public val patience: Int,
    ) : QualityPolicy {
        /**
         * Returns a copy of this policy with the given values replaced and every other value kept.
         *
         * @param budget the new value of [budget].
         * @param initial the new value of [initial].
         * @param minimum the new value of [minimum].
         * @param maximum the new value of [maximum].
         * @param headroom the new value of [headroom].
         * @param smoothing the new value of [smoothing].
         * @param patience the new value of [patience].
         * @return the copy.
         * @since 1.0.0
         */
        public fun copy(
            budget: Duration = this.budget,
            initial: QualityLevel = this.initial,
            minimum: QualityLevel = this.minimum,
            maximum: QualityLevel = this.maximum,
            headroom: Float = this.headroom,
            smoothing: Float = this.smoothing,
            patience: Int = this.patience,
        ): Adaptive = Adaptive(
            budget = budget,
            initial = initial,
            minimum = minimum,
            maximum = maximum,
            headroom = headroom,
            smoothing = smoothing,
            patience = patience,
        )

        override fun equals(other: Any?): Boolean = other is Adaptive && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Adaptive(budget=$budget, initial=$initial, minimum=$minimum, " +
            "maximum=$maximum, headroom=$headroom, smoothing=$smoothing, patience=$patience)"

        private fun values(): List<Any> = listOf(budget, initial, minimum, maximum, headroom, smoothing, patience)
    }
}
