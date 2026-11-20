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

@Stable
public class QuestionnaireState(public val questions: List<Question>) {
    private val given = mutableStateMapOf<QuestionKey, Answer>()

    public var index: Int by mutableIntStateOf(0)
        private set

    public var invalid: QuestionKey? by mutableStateOf(null)
        private set

    public var isSubmitted: Boolean by mutableStateOf(false)
        private set

    public val current: Question
        get() = questions[index]

    public val isFirst: Boolean
        get() = index == 0

    public val isLast: Boolean
        get() = index == questions.lastIndex

    public val answers: Map<QuestionKey, Answer>
        get() = given.toMap()

    init {
        require(questions.isNotEmpty()) { "A questionnaire needs at least one question." }
        val keys = questions.map { question -> question.key }
        require(keys.toSet().size == keys.size) { "Every question of a questionnaire needs its own key." }
    }

    public fun answerOf(key: QuestionKey): Answer? = given[key]

    public fun answer(key: QuestionKey, answer: Answer) {
        given[key] = answer
        if (invalid == key && !answer.isEmpty) invalid = null
    }

    public fun select(value: String) {
        val question = current
        if (question.kind == QuestionKind.Text) return
        if (question.kind == QuestionKind.Single) return answer(question.key, Answer.Single(value))
        val chosen = (given[question.key] as? Answer.Multiple)?.values.orEmpty()
        answer(question.key, Answer.Multiple(chosen.toggled(value)))
    }

    public fun isSelected(value: String): Boolean = when (val answer = given[current.key]) {
        is Answer.Single -> answer.value == value
        is Answer.Multiple -> value in answer.values
        is Answer.Text, Answer.Skipped, null -> false
    }

    public fun next(): QuestionnaireStep {
        val question = current
        if (isMissing(question)) return reject(question)
        return advance()
    }

    public fun previous(): QuestionnaireStep {
        invalid = null
        index = maxOf(index - 1, 0)
        return QuestionnaireStep.Moved(index)
    }

    public fun skip(): QuestionnaireStep {
        val question = current
        if (question.isRequired) return reject(question)
        given[question.key] = Answer.Skipped
        return advance()
    }

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

@Composable
public fun rememberQuestionnaireState(vararg questions: Question): QuestionnaireState {
    val list = questions.toList()
    return remember(list) { QuestionnaireState(list) }
}
