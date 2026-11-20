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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.utf16CodePoint
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.text.Text

/**
 * Asks a list of questions one at a time, as a column of a progress, the current question and its buttons.
 *
 * Each part, such as [QuestionnaireItem], [QuestionnaireChoice] and [QuestionnaireNext], reads the questionnaire it is
 * placed in. When the questionnaire is submitted with every required question answered, [onSubmit] receives the
 * answers.
 *
 * @param state the state of the questionnaire, which holds its questions, answers and current question.
 * @param onSubmit called with the answers by key when the questionnaire is submitted.
 * @param modifier the modifier of the questionnaire, which fills the available width.
 * @param style the style of the questionnaire.
 * @param content the parts of the questionnaire.
 * @since 1.0.0
 */
@Composable
public fun Questionnaire(
    state: QuestionnaireState,
    onSubmit: (Map<QuestionKey, Answer>) -> Unit,
    modifier: Modifier = Modifier,
    style: QuestionnaireStyle = questionnaireStyle(),
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = remember(
        state,
        style,
        onSubmit,
    ) { QuestionnaireContext(state = state, style = style, onSubmit = onSubmit) }
    val shortcuts = Modifier.onKeyEvent { event ->
        val value = context.shortcuts[event.utf16CodePoint.toChar().lowercaseChar()]
        val isShortcut = event.type == KeyEventType.KeyDown && value != null
        value?.takeIf { isShortcut }?.let { chosen -> state.select(chosen) }
        isShortcut
    }
    CompositionLocalProvider(LocalQuestionnaire provides context) {
        Column(
            modifier = modifier.fillMaxWidth().then(shortcuts),
            verticalArrangement = Arrangement.spacedBy(style.gap),
            content = content,
        )
    }
}

/**
 * Shows which question of a [Questionnaire] is the current one, such as `2 of 5`, as given by the progress label of the
 * style.
 *
 * @param modifier the modifier of the text.
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireProgress(modifier: Modifier = Modifier) {
    val context = questionnaire()
    val state = context.state
    val style = context.style
    Text(
        text = style.labels.progress(state.index + 1, state.questions.size),
        modifier = modifier,
        style = style.progressStyle,
        color = style.progressColor,
        maxLines = 1,
    )
}

/**
 * Shows the content of one question of a [Questionnaire] while that question is the current one, as a column with the
 * gap of the style.
 *
 * @param key the key of the question.
 * @param modifier the modifier of the column.
 * @param content the content of the question, such as a [QuestionnaireTitle], a [QuestionnaireDescription] and
 * [QuestionnaireChoices].
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireItem(
    key: QuestionKey,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val context = questionnaire()
    val question = context.state.current
    if (question.key != key) return
    val item = remember(question) { QuestionnaireItemContext(question) }
    CompositionLocalProvider(LocalQuestionnaireItem provides item) {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(context.style.gap), content = content)
    }
}

/**
 * Shows the title of a question of a [Questionnaire]. Inside a [QuestionnaireItem] without a
 * [QuestionnaireDescription], the title spacing of the style follows it.
 *
 * @param text the title.
 * @param modifier the modifier of the title.
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireTitle(text: String, modifier: Modifier = Modifier) {
    val style = questionnaire().style
    val item = LocalQuestionnaireItem.current
    val spacing = style.titleSpacing.takeIf { item?.hasDescription == false } ?: 0.dp
    Text(text = text, modifier = modifier.padding(bottom = spacing), style = style.titleStyle)
}

/**
 * Shows the description of a question of a [Questionnaire], below its [QuestionnaireTitle].
 *
 * @param text the description.
 * @param modifier the modifier of the description.
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireDescription(text: String, modifier: Modifier = Modifier) {
    val style = questionnaire().style
    val item = LocalQuestionnaireItem.current
    DisposableEffect(item) {
        item?.hasDescription = true
        onDispose { item?.hasDescription = false }
    }
    Text(text = text, modifier = modifier, style = style.descriptionStyle, color = style.descriptionColor)
}

/**
 * Shows an error while the current question of a [Questionnaire] is missing its required answer.
 *
 * @param modifier the modifier of the error.
 * @param text the text of the error; its default comes from the labels of the theme's questionnaire style.
 * @throws IllegalArgumentException if it is not placed inside a [Questionnaire].
 * @since 1.0.0
 */
@Composable
public fun QuestionnaireError(modifier: Modifier = Modifier, text: String = requiredText()) {
    val context = questionnaire()
    if (context.state.invalid != context.state.current.key) return
    val style = context.style
    Text(
        text = text,
        modifier = modifier.padding(top = style.errorSpacing),
        style = style.errorStyle,
        color = style.errorColor,
    )
}

@Composable
internal fun questionnaire(): QuestionnaireContext = requireNotNull(LocalQuestionnaire.current) {
    "A questionnaire part needs its questionnaire."
}

@Composable
private fun requiredText(): String {
    val labels = questionnaireStyle().labels
    return labels.required
}
