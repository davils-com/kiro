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

package com.davils.kiro.ui.core.motion

import androidx.compose.runtime.Immutable

/**
 * The motion that runs on its own, such as a spinner or a shimmer.
 *
 * @property speed the factor of the speed of ambient animations; 2 runs them twice as fast.
 * @since 1.0.0
 */
@Immutable
public class AmbientMotion(
    public val speed: Float = 1f,
) {
    /**
     * Returns a copy of this [AmbientMotion] with the given values replaced and every other value kept.
     *
     * @param speed the new value of [speed].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(speed: Float = this.speed): AmbientMotion = AmbientMotion(
        speed = speed,
    )

    override fun equals(other: Any?): Boolean = other is AmbientMotion && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AmbientMotion(" +
        "speed=$speed)"

    private fun values(): List<Any?> = listOf(
        speed,
    )
}
