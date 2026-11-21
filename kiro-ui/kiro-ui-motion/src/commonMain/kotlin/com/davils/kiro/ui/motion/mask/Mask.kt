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

package com.davils.kiro.ui.motion.mask

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset

/**
 * Fades a layer or a whole scene out towards some of its parts, such as towards the bottom behind a text.
 *
 * Every mask goes from fully shown to hidden through its [MaskStop]s: a stop at `0.4` with alpha `1` and one at `1`
 * with alpha `0` show the first 40 % and fade the rest.
 *
 * @since 1.0.0
 */
@Immutable
public sealed interface Mask {
    /**
     * Shows everything.
     *
     * @since 1.0.0
     */
    public data object None : Mask

    /**
     * Fades along a straight line.
     *
     * @property angle the direction of the line in degrees, `90` from top to bottom.
     * @property stops where the line shows how much.
     * @since 1.0.0
     */
    @Immutable
    public class Linear(public val angle: Float, public val stops: List<MaskStop>) : Mask {
        override fun equals(other: Any?): Boolean = other is Linear && angle == other.angle && stops == other.stops

        override fun hashCode(): Int = listOf(angle, stops).hashCode()

        override fun toString(): String = "Linear(angle=$angle, stops=$stops)"
    }

    /**
     * Fades from a center outwards.
     *
     * @property center the center, as fractions of the width and the height.
     * @property radius the radius at the last stop, as a fraction of the larger side.
     * @property stops where the circle shows how much, from the center outwards.
     * @since 1.0.0
     */
    @Immutable
    public class Radial(public val center: Offset, public val radius: Float, public val stops: List<MaskStop>) : Mask {
        override fun equals(other: Any?): Boolean =
            other is Radial && center == other.center && radius == other.radius && stops == other.stops

        override fun hashCode(): Int = listOf(center, radius, stops).hashCode()

        override fun toString(): String = "Radial(center=$center, radius=$radius, stops=$stops)"
    }

    /**
     * Darkens the corners like an old lens.
     *
     * @property strength how far the corners fade, between `0` and `1`.
     * @property extent where the fade starts, as a fraction of the distance from the center to a corner.
     * @since 1.0.0
     */
    @Immutable
    public class Vignette(public val strength: Float, public val extent: Float) : Mask {
        override fun equals(other: Any?): Boolean =
            other is Vignette && strength == other.strength && extent == other.extent

        override fun hashCode(): Int = listOf(strength, extent).hashCode()

        override fun toString(): String = "Vignette(strength=$strength, extent=$extent)"
    }
}
