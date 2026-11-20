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
public sealed interface QuestionnaireStep {
    public class Moved(
        public val index: Int,
    ) : QuestionnaireStep {
        /**
         * Returns a copy of this [Moved] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(index: Int = this.index): Moved = Moved(
            index = index,
        )

        override fun equals(other: Any?): Boolean = other is Moved && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Moved(" +
            "index=$index)"

        private fun values(): List<Any?> = listOf(
            index,
        )
    }

    public class Invalid(
        public val question: Question,
    ) : QuestionnaireStep {
        /**
         * Returns a copy of this [Invalid] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(question: Question = this.question): Invalid = Invalid(
            question = question,
        )

        override fun equals(other: Any?): Boolean = other is Invalid && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Invalid(" +
            "question=$question)"

        private fun values(): List<Any?> = listOf(
            question,
        )
    }

    public class Submitted(
        public val answers: Map<QuestionKey, Answer>,
    ) : QuestionnaireStep {
        /**
         * Returns a copy of this [Submitted] with the given values replaced and every other value kept.
         *
         * @since 0.1.0
         */
        public fun copy(answers: Map<QuestionKey, Answer> = this.answers): Submitted = Submitted(
            answers = answers,
        )

        override fun equals(other: Any?): Boolean = other is Submitted && values() == other.values()

        override fun hashCode(): Int = values().hashCode()

        override fun toString(): String = "Submitted(" +
            "answers=$answers)"

        private fun values(): List<Any?> = listOf(
            answers,
        )
    }
}
