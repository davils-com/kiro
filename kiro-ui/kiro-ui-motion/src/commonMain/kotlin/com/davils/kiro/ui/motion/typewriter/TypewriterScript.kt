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

/**
 * What a [TypewriterText] types, deletes and waits for, written with [typewriter] or [typewriterCycle].
 *
 * @property steps the steps in the order they play.
 * @property isRepeating whether the script starts over after its last step.
 * @since 1.0.0
 */
@Immutable
public class TypewriterScript(
    public val steps: List<TypewriterStep>,
    public val isRepeating: Boolean,
) {
    /**
     * Returns a copy of this [TypewriterScript] with the given values replaced and every other value kept.
     *
     * @param steps the new value of [steps].
     * @param isRepeating the new value of [isRepeating].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        steps: List<TypewriterStep> = this.steps,
        isRepeating: Boolean = this.isRepeating,
    ): TypewriterScript = TypewriterScript(
        steps = steps,
        isRepeating = isRepeating,
    )

    override fun equals(other: Any?): Boolean = other is TypewriterScript && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TypewriterScript(" +
        "steps=$steps, " +
        "isRepeating=$isRepeating)"

    private fun values(): List<Any?> = listOf(
        steps,
        isRepeating,
    )
}
