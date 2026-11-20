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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * The state of a [Questionnaire]: its questions, the given answers, the current question and whether it was submitted.
 *
 * The state is observable, so the questionnaire updates when it changes. The constructor throws an
 * [IllegalArgumentException] if there are no questions or two questions share a key.
 *
 * @property questions the questions, in the order they are asked.
 * @since 1.0.0
 */
@Stable
public class QuestionnaireState(public val questions: List<Question>) {
    private val given = mutableStateMapOf<QuestionKey, Answer>()

    /**
     * The index of the current question in [questions].
     *
     * @since 1.0.0
     */
    public var index: Int by mutableIntStateOf(0)
        private set

    /**
     * The key of the required question that was found missing its answer, or `null`. It is cleared when the question
     * gets an answer that is not empty or the questionnaire moves back.
     *
     * @since 1.0.0
     */
    public var invalid: QuestionKey? by mutableStateOf(null)
        private set

    /**
     * Whether the questionnaire was submitted with every required question answered.
     *
     * @since 1.0.0
     */
    public var isSubmitted: Boolean by mutableStateOf(false)
        private set

    /**
     * The current question.
     *
     * @since 1.0.0
     */
    public val current: Question
        get() = questions[index]

    /**
     * Whether the current question is the first one.
     *
     * @since 1.0.0
     */
    public val isFirst: Boolean
        get() = index == 0

    /**
     * Whether the current question is the last one.
     *
     * @since 1.0.0
     */
    public val isLast: Boolean
        get() = index == questions.lastIndex

    /**
     * A copy of the answers given so far, by the key of their question.
     *
     * @since 1.0.0
     */
    public val answers: Map<QuestionKey, Answer>
        get() = given.toMap()

    init {
        require(questions.isNotEmpty()) { "A questionnaire needs at least one question." }
        val keys = questions.map { question -> question.key }
        require(keys.toSet().size == keys.size) { "Every question of a questionnaire needs its own key." }
    }

    /**
     * Returns the answer given to a question.
     *
     * @param key the key of the question.
     * @return the answer, or `null` if none was given.
     * @since 1.0.0
     */
    public fun answerOf(key: QuestionKey): Answer? = given[key]

    /**
     * Gives the answer to a question, replacing an earlier one. An answer that is not empty clears [invalid] for that
     * question.
     *
     * @param key the key of the question.
     * @param answer the answer.
     * @since 1.0.0
     */
    public fun answer(key: QuestionKey, answer: Answer) {
        given[key] = answer
        if (invalid == key && !answer.isEmpty) invalid = null
    }

    /**
     * Chooses a value for the current question: replaces the answer of a question of the kind [QuestionKind.Single],
     * toggles the value in the answer of a question of the kind [QuestionKind.Multiple] and does nothing for a question
     * of the kind [QuestionKind.Text].
     *
     * @param value the value to choose.
     * @since 1.0.0
     */
    public fun select(value: String) {
        val question = current
        if (question.kind == QuestionKind.Text) return
        if (question.kind == QuestionKind.Single) return answer(question.key, Answer.Single(value))
        val chosen = (given[question.key] as? Answer.Multiple)?.values.orEmpty()
        answer(question.key, Answer.Multiple(chosen.toggled(value)))
    }

    /**
     * Returns whether a value is chosen in the answer to the current question.
     *
     * @param value the value.
     * @return `true` if the value is chosen.
     * @since 1.0.0
     */
    public fun isSelected(value: String): Boolean = when (val answer = given[current.key]) {
        is Answer.Single -> answer.value == value
        is Answer.Multiple -> value in answer.values
        is Answer.Text, Answer.Skipped, null -> false
    }

    /**
     * Moves to the next question, or submits the questionnaire on the last one, unless the current question is required
     * and missing its answer; then that question becomes [invalid].
     *
     * @return the step that was taken.
     * @since 1.0.0
     */
    public fun next(): QuestionnaireStep {
        val question = current
        if (isMissing(question)) return reject(question)
        return advance()
    }

    /**
     * Moves back to the previous question and clears [invalid]. On the first question it stays there.
     *
     * @return the step to the question that is now the current one.
     * @since 1.0.0
     */
    public fun previous(): QuestionnaireStep {
        invalid = null
        index = maxOf(a = index - 1, b = 0)
        return QuestionnaireStep.Moved(index)
    }

    /**
     * Answers the current question with [Answer.Skipped] and moves to the next question, or submits the questionnaire
     * on the last one. A required question cannot be skipped and becomes [invalid] instead.
     *
     * @return the step that was taken.
     * @since 1.0.0
     */
    public fun skip(): QuestionnaireStep {
        val question = current
        if (question.isRequired) return reject(question)
        given[question.key] = Answer.Skipped
        return advance()
    }

    /**
     * Submits the questionnaire if every required question has an answer that is not empty. Otherwise the first missing
     * question becomes the current one and [invalid].
     *
     * @return [QuestionnaireStep.Submitted] with the answers, or [QuestionnaireStep.Invalid] with the missing question.
     * @since 1.0.0
     */
    public fun submit(): QuestionnaireStep {
        val missing = questions.firstOrNull { question -> isMissing(question) }
        if (missing != null) {
            index = questions.indexOf(missing)
            return reject(missing)
        }
        isSubmitted = true
        return QuestionnaireStep.Submitted(answers)
    }

    private fun isMissing(question: Question): Boolean {
        if (!question.isRequired) return false
        return given[question.key]?.isEmpty != false
    }

    private fun advance(): QuestionnaireStep {
        if (isLast) return submit()
        index += 1
        return QuestionnaireStep.Moved(index)
    }

    private fun reject(question: Question): QuestionnaireStep {
        invalid = question.key
        return QuestionnaireStep.Invalid(question)
    }

    private fun Set<String>.toggled(value: String): Set<String> {
        if (value in this) return this - value
        return this + value
    }
}

/**
 * Creates and remembers the state of a [Questionnaire]. The state is created anew when the questions change.
 *
 * @param questions the questions, in the order they are asked.
 * @return the remembered state.
 * @throws IllegalArgumentException if there are no questions or two questions share a key.
 * @since 1.0.0
 */
@Composable
public fun rememberQuestionnaireState(vararg questions: Question): QuestionnaireState {
    val list = questions.toList()
    return remember(list) { QuestionnaireState(list) }
}
