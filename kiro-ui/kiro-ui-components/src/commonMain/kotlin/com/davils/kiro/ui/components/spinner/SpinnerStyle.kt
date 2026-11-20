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

package com.davils.kiro.ui.components.spinner

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic
import kotlin.time.Duration

/**
 * The style of a [Spinner].
 *
 * @property icon the icon that turns.
 * @property size the size of the icon.
 * @property turnDuration the duration of one full turn.
 * @property isAnimated whether the icon turns; the default style turns it unless the theme reduces motion.
 * @since 1.0.0
 */
@Immutable
public class SpinnerStyle(
    public val icon: IconGraphic,
    public val size: Dp,
    public val turnDuration: Duration,
    public val isAnimated: Boolean,
) {
    /**
     * Returns a copy of this [SpinnerStyle] with the given values replaced and every other value kept.
     *
     * @param icon the new value of [icon].
     * @param size the new value of [size].
     * @param turnDuration the new value of [turnDuration].
     * @param isAnimated the new value of [isAnimated].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        icon: IconGraphic = this.icon,
        size: Dp = this.size,
        turnDuration: Duration = this.turnDuration,
        isAnimated: Boolean = this.isAnimated,
    ): SpinnerStyle = SpinnerStyle(
        icon = icon,
        size = size,
        turnDuration = turnDuration,
        isAnimated = isAnimated,
    )

    override fun equals(other: Any?): Boolean = other is SpinnerStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SpinnerStyle(" +
        "icon=$icon, " +
        "size=$size, " +
        "turnDuration=$turnDuration, " +
        "isAnimated=$isAnimated)"

    private fun values(): List<Any?> = listOf(
        icon,
        size,
        turnDuration,
        isAnimated,
    )
}
