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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import com.davils.kiro.ui.components.button.Button
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.components.input.Input
import com.davils.kiro.ui.components.text.Text

@Composable
public fun QuestionnaireInput(modifier: Modifier = Modifier, placeholder: String? = null, enabled: Boolean = true) {
    val context = questionnaire()
    val state = context.state
    val key = state.current.key
    val text = (state.answerOf(key) as? Answer.Text)?.text.orEmpty()
    val submit = Modifier.onPreviewKeyEvent { event ->
        val isEnter = event.type == KeyEventType.KeyDown && event.key == Key.Enter
        if (isEnter) context.advance(state.next())
        isEnter
    }
    Input(
        value = text,
        onValueChange = { typed -> state.answer(key, Answer.Text(typed)) },
        modifier = modifier.fillMaxWidth().then(submit),
        placeholder = placeholder,
        enabled = enabled,
        isInvalid = state.invalid == key,
    )
}

@Composable
public fun QuestionnaireActions(modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    val style = questionnaire().style
    Row(
        modifier = modifier.fillMaxWidth().heightIn(min = style.actionsMinHeight),
        horizontalArrangement = Arrangement.spacedBy(style.actionsGap),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
    )
}

@Composable
public fun RowScope.QuestionnairePrevious(
    modifier: Modifier = Modifier,
    text: String = questionnaireStyle().labels.previous,
    variant: ButtonVariant = ButtonVariant.Outline,
    size: ButtonSize = ButtonSize.Default,
) {
    val context = questionnaire()
    Box(modifier = Modifier.weight(1f)) {
        if (!context.state.isFirst) {
            Button(
                onClick = { context.state.previous() },
                modifier = modifier,
                variant = variant,
                size = size,
            ) { Text(text) }
        }
    }
}

@Composable
public fun QuestionnaireSkip(
    modifier: Modifier = Modifier,
    text: String = questionnaireStyle().labels.skip,
    variant: ButtonVariant = ButtonVariant.Outline,
    size: ButtonSize = ButtonSize.Default,
) {
    val context = questionnaire()
    if (context.state.current.isRequired) return
    Button(
        onClick = { context.advance(context.state.skip()) },
        modifier = modifier,
        variant = variant,
        size = size,
    ) { Text(text) }
}

@Composable
public fun QuestionnaireNext(
    modifier: Modifier = Modifier,
    text: String = questionnaireStyle().labels.next,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
) {
    val context = questionnaire()
    if (context.state.isLast) return
    Button(
        onClick = { context.advance(context.state.next()) },
        modifier = modifier,
        variant = variant,
        size = size,
    ) { Text(text) }
}

@Composable
public fun QuestionnaireSubmit(
    modifier: Modifier = Modifier,
    text: String = questionnaireStyle().labels.submit,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
) {
    val context = questionnaire()
    if (!context.state.isLast) return
    Button(
        onClick = { context.advance(context.state.submit()) },
        modifier = modifier,
        variant = variant,
        size = size,
    ) { Text(text) }
}
