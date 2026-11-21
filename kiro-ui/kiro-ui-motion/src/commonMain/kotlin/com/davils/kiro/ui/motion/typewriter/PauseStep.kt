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

package com.davils.kiro.ui.motion.typewriter

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

/**
 * Waits with the text as it is, with a blinking caret.
 *
 * @property duration how long to wait, or `null` for the [TypewriterStyle.hold] of the style.
 * @since 1.0.0
 */
@Immutable
public class PauseStep(
    public val duration: Duration? = null,
) : TypewriterStep {
    /**
     * Returns a copy of this [PauseStep] with the given values replaced and every other value kept.
     *
     * @param duration the new value of [duration].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(duration: Duration? = this.duration): PauseStep = PauseStep(duration = duration)

    override fun equals(other: Any?): Boolean = other is PauseStep && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "PauseStep(" +
        "duration=${duration ?: "null"})"

    private fun values(): List<Any?> = listOf(
        duration,
    )
}
