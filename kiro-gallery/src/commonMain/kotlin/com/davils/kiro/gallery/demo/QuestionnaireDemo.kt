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

package com.davils.kiro.gallery.demo

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.questionnaire.Question
import com.davils.kiro.ui.components.questionnaire.QuestionKey
import com.davils.kiro.ui.components.questionnaire.QuestionKind
import com.davils.kiro.ui.components.questionnaire.Questionnaire
import com.davils.kiro.ui.components.questionnaire.QuestionnaireActions
import com.davils.kiro.ui.components.questionnaire.QuestionnaireChoice
import com.davils.kiro.ui.components.questionnaire.QuestionnaireChoiceDescription
import com.davils.kiro.ui.components.questionnaire.QuestionnaireChoices
import com.davils.kiro.ui.components.questionnaire.QuestionnaireDescription
import com.davils.kiro.ui.components.questionnaire.QuestionnaireError
import com.davils.kiro.ui.components.questionnaire.QuestionnaireInput
import com.davils.kiro.ui.components.questionnaire.QuestionnaireItem
import com.davils.kiro.ui.components.questionnaire.QuestionnaireNext
import com.davils.kiro.ui.components.questionnaire.QuestionnairePrevious
import com.davils.kiro.ui.components.questionnaire.QuestionnaireProgress
import com.davils.kiro.ui.components.questionnaire.QuestionnaireSkip
import com.davils.kiro.ui.components.questionnaire.QuestionnaireSubmit
import com.davils.kiro.ui.components.questionnaire.QuestionnaireTitle
import com.davils.kiro.ui.components.questionnaire.rememberQuestionnaireState
import com.davils.kiro.ui.components.text.Text

private val role = Question(key = QuestionKey("role"), kind = QuestionKind.Single, isRequired = true)
private val protocols = Question(key = QuestionKey("protocols"), kind = QuestionKind.Multiple)
private val note = Question(key = QuestionKey("note"), kind = QuestionKind.Text)

@Composable
internal fun QuestionnaireDemo() {
    val state = rememberQuestionnaireState(role, protocols, note)
    Questionnaire(state = state, onSubmit = {}, modifier = Modifier.width(420.dp)) {
        QuestionnaireProgress()
        QuestionnaireItem(key = role.key) {
            QuestionnaireTitle(text = "What is your role?")
            QuestionnaireChoices {
                QuestionnaireChoice(value = "admin", shortcut = 'a') {
                    Text("Administrator")
                    QuestionnaireChoiceDescription(text = "Runs the network day to day.")
                }
                QuestionnaireChoice(value = "engineer", shortcut = 'e') { Text("Network engineer") }
            }
            QuestionnaireError()
        }
        QuestionnaireItem(key = protocols.key) {
            QuestionnaireTitle(text = "Which protocols do you use?")
            QuestionnaireDescription(text = "Pick as many as you like.")
            QuestionnaireChoices {
                QuestionnaireChoice(value = "ssh") { Text("SSH") }
                QuestionnaireChoice(value = "snmp") { Text("SNMP") }
            }
        }
        QuestionnaireItem(key = note.key) {
            QuestionnaireTitle(text = "Anything else?")
            QuestionnaireInput(placeholder = "Your note")
        }
        QuestionnaireActions {
            QuestionnairePrevious()
            QuestionnaireSkip()
            QuestionnaireNext()
            QuestionnaireSubmit()
        }
    }
}
