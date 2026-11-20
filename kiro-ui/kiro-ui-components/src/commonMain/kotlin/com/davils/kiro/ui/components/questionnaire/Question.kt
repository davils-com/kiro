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

package com.davils.kiro.ui.components.questionnaire

import androidx.compose.runtime.Immutable

@Immutable
public class Question(
    public val key: QuestionKey,
    public val kind: QuestionKind,
    public val isRequired: Boolean = false,
) {
    /**
     * Returns a copy of this [Question] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        key: QuestionKey = this.key,
        kind: QuestionKind = this.kind,
        isRequired: Boolean = this.isRequired,
    ): Question = Question(
        key = key,
        kind = kind,
        isRequired = isRequired,
    )

    override fun equals(other: Any?): Boolean = other is Question && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Question(" +
        "key=$key, " +
        "kind=$kind, " +
        "isRequired=$isRequired)"

    private fun values(): List<Any?> = listOf(
        key,
        kind,
        isRequired,
    )
}
