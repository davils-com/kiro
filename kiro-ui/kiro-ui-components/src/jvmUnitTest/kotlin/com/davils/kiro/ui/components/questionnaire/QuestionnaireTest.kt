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

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyInput
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.pressKey
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.components.fixture.TAG
import com.davils.kiro.ui.components.fixture.Themed
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.core.Theme
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val TOTAL: Int = 3
private const val TOOLS: String = "Your tools"
private const val ROLE: String = "Your role"

private val role = Question(key = QuestionKey("role"), kind = QuestionKind.Single, isRequired = true)
private val tools = Question(key = QuestionKey("tools"), kind = QuestionKind.Multiple)
private val note = Question(key = QuestionKey("note"), kind = QuestionKind.Text)

@OptIn(ExperimentalTestApi::class)
class QuestionnaireTest : FunSpec({
    context("styles") {
        test("are min-h-11 rounded-lg choices with a size-4 indicator") {
            val light = Theme.light()
            val style = QuestionnaireStyles.Default.resolve(light)

            style.gap shouldBe 16.dp
            style.choice.minHeight shouldBe 44.dp
            style.choice.border shouldBe light.colors.input
            style.choice.checkedBorder shouldBe light.colors.primary.copy(alpha = 0.4f)
            style.choice.indicatorSize shouldBe 16.dp
            style.labels.progress(1, TOTAL) shouldBe "Question 1 of 3"
        }

        test("tint the choices with the input color in dark mode") {
            val dark = Theme.dark()
            val style = QuestionnaireStyles.Default.resolve(dark)

            style.choice.container shouldBe dark.colors.input.copy(alpha = 0.2f)
            style.choice.indicatorContainer shouldBe dark.colors.input.copy(alpha = 0.3f)
        }
    }

    context("questionnaire") {
        test("walks through single, multiple and text questions and submits the answers") {
            var submitted: Map<QuestionKey, Answer> = emptyMap()

            runComposeUiTest {
                setContent {
                    Themed {
                        val state = rememberQuestionnaireState(role, tools, note)
                        Questionnaire(state = state, onSubmit = { answers -> submitted = answers }) {
                            QuestionnaireProgress()
                            QuestionnaireItem(key = role.key) {
                                QuestionnaireTitle(text = ROLE)
                                QuestionnaireDescription(text = "Pick one")
                                QuestionnaireChoices {
                                    QuestionnaireChoice(
                                        value = "admin",
                                        shortcut = 'a',
                                        modifier = Modifier.testTag(TAG),
                                    ) {
                                        Text("Admin")
                                        QuestionnaireChoiceDescription(text = "Runs the network")
                                    }
                                    QuestionnaireChoice(value = "guest", enabled = false) { Text("Guest") }
                                }
                                QuestionnaireError()
                            }
                            QuestionnaireItem(key = tools.key) {
                                QuestionnaireTitle(text = TOOLS)
                                QuestionnaireChoices {
                                    QuestionnaireChoice(value = "ssh", modifier = Modifier.testTag(TAG)) { Text("SSH") }
                                }
                            }
                            QuestionnaireItem(key = note.key) {
                                QuestionnaireTitle(text = "Anything else")
                                QuestionnaireInput(modifier = Modifier.testTag(TAG), placeholder = "Note")
                            }
                            QuestionnaireActions {
                                QuestionnairePrevious()
                                QuestionnaireSkip()
                                QuestionnaireNext()
                                QuestionnaireSubmit()
                            }
                        }
                    }
                }

                onNodeWithText("Question 1 of 3").assertExists()
                onNodeWithText("Skip").assertDoesNotExist()
                onNodeWithText("Next").performMouseInput { moveTo(center) }
                onNodeWithText("Next").performClick()
                onNodeWithText("Please answer this question.").assertExists()
                onNodeWithTag(TAG).performMouseInput { moveTo(center) }
                onNodeWithTag(TAG).performClick()
                onNodeWithTag(TAG).performKeyInput { pressKey(Key.A) }
                onNodeWithTag(TAG).assertIsSelected()
                onNodeWithText("Next").performClick()

                onNodeWithText(TOOLS).assertExists()
                onNodeWithTag(TAG).performClick()
                onNodeWithTag(TAG).assertIsOn()
                onNodeWithText("Previous").performClick()
                onNodeWithText(ROLE).assertExists()
                onNodeWithText("Next").performClick()
                onNodeWithText("Skip").performClick()

                onNodeWithText("Anything else").assertExists()
                onNodeWithTag(TAG).performClick()
                onNodeWithTag(TAG).performTextInput("Thanks")
                onNodeWithTag(TAG).performKeyInput { pressKey(Key.Enter) }
            }

            submitted shouldBe mapOf(
                role.key to Answer.Single("admin"),
                tools.key to Answer.Skipped,
                note.key to Answer.Text("Thanks"),
            )
        }

        test("submits from the submit button on the last question") {
            var submitted: Map<QuestionKey, Answer>? = null

            runComposeUiTest {
                setContent {
                    Themed(theme = Theme.dark()) {
                        val state = rememberQuestionnaireState(tools)
                        Questionnaire(state = state, onSubmit = { answers -> submitted = answers }) {
                            QuestionnaireItem(key = tools.key) {
                                QuestionnaireTitle(text = TOOLS)
                                QuestionnaireChoices {
                                    QuestionnaireChoice(value = "ssh", shortcut = '1') { Text("SSH") }
                                }
                            }
                            QuestionnaireActions {
                                QuestionnairePrevious()
                                QuestionnaireNext()
                                QuestionnaireSubmit()
                            }
                        }
                    }
                }

                onNodeWithText("Next").assertDoesNotExist()
                onNodeWithText("Previous").assertDoesNotExist()
                onNodeWithText("SSH").performMouseInput { moveTo(center) }
                onNodeWithText("SSH").performClick()
                onNodeWithText("Submit").performClick()
            }

            submitted shouldBe mapOf(tools.key to Answer.Multiple(setOf("ssh")))
        }
    }
})
