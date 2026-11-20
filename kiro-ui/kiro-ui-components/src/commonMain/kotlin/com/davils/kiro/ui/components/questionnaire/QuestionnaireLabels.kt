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
import com.davils.kiro.ui.components.Messages

/**
 * The texts that a [Questionnaire] shows, with English defaults.
 *
 * @property previous the text of [QuestionnairePrevious].
 * @property skip the text of [QuestionnaireSkip].
 * @property next the text of [QuestionnaireNext].
 * @property submit the text of [QuestionnaireSubmit].
 * @property required the text of [QuestionnaireError].
 * @property progress the text of [QuestionnaireProgress] for the number of the current question, counted from one, and
 * the number of questions.
 * @since 1.0.0
 */
@Immutable
public class QuestionnaireLabels(
    public val previous: String = Messages.Questionnaire.PREVIOUS,
    public val skip: String = Messages.Questionnaire.SKIP,
    public val next: String = Messages.Questionnaire.NEXT,
    public val submit: String = Messages.Questionnaire.SUBMIT,
    public val required: String = Messages.Questionnaire.REQUIRED,
    public val progress: (current: Int, total: Int) -> String = { current, total ->
        Messages.Questionnaire.progress(current = current, total = total)
    },
) {
    /**
     * Returns a copy of this [QuestionnaireLabels] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        previous: String = this.previous,
        skip: String = this.skip,
        next: String = this.next,
        submit: String = this.submit,
        required: String = this.required,
        progress: (current: Int, total: Int) -> String = this.progress,
    ): QuestionnaireLabels = QuestionnaireLabels(
        previous = previous,
        skip = skip,
        next = next,
        submit = submit,
        required = required,
        progress = progress,
    )

    override fun equals(other: Any?): Boolean = other is QuestionnaireLabels && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "QuestionnaireLabels(" +
        "previous=$previous, " +
        "skip=$skip, " +
        "next=$next, " +
        "submit=$submit, " +
        "required=$required, " +
        "progress=$progress)"

    private fun values(): List<Any?> = listOf(
        previous,
        skip,
        next,
        submit,
        required,
        progress,
    )
}
