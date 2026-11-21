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

package com.davils.kiro.ui.motion.power

import androidx.compose.runtime.Immutable

/**
 * Decides when a [com.davils.kiro.ui.motion.host.MotionCanvas] stops its clock to save power, and how often it draws
 * at most while it runs.
 *
 * A paused canvas keeps showing its last frame and continues where it stopped.
 *
 * @property isPausedWhenUnfocused whether the clock stops while the window is not focused.
 * @property isPausedWhenHidden whether the clock stops while the canvas is outside the window or has no size.
 * @property isPausedWhenReducedMotion whether the canvas shows a still frame while the theme asks for reduced motion.
 * @property maxFrameRate the most frames the canvas draws in a second, or `null` to draw in every frame of the display.
 * @since 1.0.0
 */
@Immutable
public class PowerPolicy(
    public val isPausedWhenUnfocused: Boolean = true,
    public val isPausedWhenHidden: Boolean = true,
    public val isPausedWhenReducedMotion: Boolean = true,
    public val maxFrameRate: Int? = null,
) {
    /**
     * Returns a copy of this [PowerPolicy] with the given values replaced and every other value kept.
     *
     * @param isPausedWhenUnfocused the new value of [isPausedWhenUnfocused].
     * @param isPausedWhenHidden the new value of [isPausedWhenHidden].
     * @param isPausedWhenReducedMotion the new value of [isPausedWhenReducedMotion].
     * @param maxFrameRate the new value of [maxFrameRate].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        isPausedWhenUnfocused: Boolean = this.isPausedWhenUnfocused,
        isPausedWhenHidden: Boolean = this.isPausedWhenHidden,
        isPausedWhenReducedMotion: Boolean = this.isPausedWhenReducedMotion,
        maxFrameRate: Int? = this.maxFrameRate,
    ): PowerPolicy = PowerPolicy(
        isPausedWhenUnfocused = isPausedWhenUnfocused,
        isPausedWhenHidden = isPausedWhenHidden,
        isPausedWhenReducedMotion = isPausedWhenReducedMotion,
        maxFrameRate = maxFrameRate,
    )

    override fun equals(other: Any?): Boolean = other is PowerPolicy && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "PowerPolicy(" +
        "isPausedWhenUnfocused=$isPausedWhenUnfocused, " +
        "isPausedWhenHidden=$isPausedWhenHidden, " +
        "isPausedWhenReducedMotion=$isPausedWhenReducedMotion, " +
        "maxFrameRate=${maxFrameRate ?: "null"})"

    private fun values(): List<Any?> = listOf(
        isPausedWhenUnfocused,
        isPausedWhenHidden,
        isPausedWhenReducedMotion,
        maxFrameRate,
    )
}
