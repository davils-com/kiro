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
 * Deletes characters from the end of the text, one after the other.
 *
 * @property count how many characters to delete, or `null` for the whole text.
 * @since 1.0.0
 */
@Immutable
public class DeleteStep(
    public val count: Int? = null,
) : TypewriterStep {
    /**
     * Returns a copy of this [DeleteStep] with the given values replaced and every other value kept.
     *
     * @param count the new value of [count].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(count: Int? = this.count): DeleteStep = DeleteStep(count = count)

    override fun equals(other: Any?): Boolean = other is DeleteStep && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DeleteStep(" +
        "count=${count ?: "null"})"

    private fun values(): List<Any?> = listOf(
        count,
    )
}
