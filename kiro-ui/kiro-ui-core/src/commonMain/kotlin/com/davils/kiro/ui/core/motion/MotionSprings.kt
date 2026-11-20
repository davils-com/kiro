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
 * The springs of a theme.
 *
 * @property spatial the spring of moving things, such as a thumb.
 * @property effects the spring of effects, such as a fade.
 * @since 0.1.0
 */
@Immutable
public class MotionSprings(
    public val spatial: MotionSpring = MotionSpring(dampingRatio = 1f, stiffness = 500f),
    public val effects: MotionSpring = MotionSpring(dampingRatio = 1f, stiffness = 1600f),
) {
    /**
     * Returns a copy of this [MotionSprings] with the given values replaced and every other value kept.
     *
     * @param spatial the new value of [spatial].
     * @param effects the new value of [effects].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(spatial: MotionSpring = this.spatial, effects: MotionSpring = this.effects): MotionSprings =
        MotionSprings(
            spatial = spatial,
            effects = effects,
        )

    override fun equals(other: Any?): Boolean = other is MotionSprings && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MotionSprings(" +
        "spatial=$spatial, " +
        "effects=$effects)"

    private fun values(): List<Any?> = listOf(
        spatial,
        effects,
    )
}
