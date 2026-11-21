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
 * Types a text after the text so far, one character after the other.
 *
 * @property text the text to type.
 * @since 1.0.0
 */
@Immutable
public class TypeStep(
    public val text: String,
) : TypewriterStep {
    /**
     * Returns a copy of this [TypeStep] with the given values replaced and every other value kept.
     *
     * @param text the new value of [text].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(text: String = this.text): TypeStep = TypeStep(text = text)

    override fun equals(other: Any?): Boolean = other is TypeStep && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "TypeStep(" +
        "text=$text)"

    private fun values(): List<Any?> = listOf(
        text,
    )
}
