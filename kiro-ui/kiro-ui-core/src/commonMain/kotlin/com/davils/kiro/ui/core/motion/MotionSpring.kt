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
 * The parameters of a spring animation.
 *
 * @property dampingRatio the damping ratio; 1 settles without bouncing, less bounces.
 * @property stiffness the stiffness; the higher, the faster the spring settles.
 * @since 0.1.0
 */
@Immutable
public class MotionSpring(
    public val dampingRatio: Float,
    public val stiffness: Float,
) {
    /**
     * Returns a copy of this [MotionSpring] with the given values replaced and every other value kept.
     *
     * @param dampingRatio the new value of [dampingRatio].
     * @param stiffness the new value of [stiffness].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(dampingRatio: Float = this.dampingRatio, stiffness: Float = this.stiffness): MotionSpring =
        MotionSpring(
            dampingRatio = dampingRatio,
            stiffness = stiffness,
        )

    override fun equals(other: Any?): Boolean = other is MotionSpring && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MotionSpring(" +
        "dampingRatio=$dampingRatio, " +
        "stiffness=$stiffness)"

    private fun values(): List<Any?> = listOf(
        dampingRatio,
        stiffness,
    )
}
