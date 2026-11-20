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

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val ADMIN: String = "admin"

private val role = Question(key = QuestionKey("role"), kind = QuestionKind.Single, isRequired = true)
private val tools = Question(key = QuestionKey("tools"), kind = QuestionKind.Multiple)
private val note = Question(key = QuestionKey("note"), kind = QuestionKind.Text)

class QuestionnaireStateTest : FunSpec({
    context("construction") {
        test("needs at least one question with distinct keys") {
            shouldThrow<IllegalArgumentException> { QuestionnaireState(emptyList()) }
            shouldThrow<IllegalArgumentException> { QuestionnaireState(listOf(role, role)) }
        }
    }

    context("navigation") {
        test("rejects a required question without an answer and moves on once it is answered") {
            val state = QuestionnaireState(listOf(role, tools, note))

            state.next() shouldBe QuestionnaireStep.Invalid(role)
            state.invalid shouldBe role.key
            state.select(ADMIN)
            state.invalid shouldBe null
            state.next() shouldBe QuestionnaireStep.Moved(1)
            state.isFirst shouldBe false
            state.previous() shouldBe QuestionnaireStep.Moved(0)
            state.previous() shouldBe QuestionnaireStep.Moved(0)
        }

        test("skips optional questions and refuses to skip required ones") {
            val state = QuestionnaireState(listOf(role, tools))

            state.skip() shouldBe QuestionnaireStep.Invalid(role)
            state.select(ADMIN)
            state.next()
            val step = state.skip()

            step shouldBe QuestionnaireStep.Submitted(
                mapOf(role.key to Answer.Single(ADMIN), tools.key to Answer.Skipped),
            )
            state.isSubmitted shouldBe true
        }

        test("skips to the next question before the last one") {
            val state = QuestionnaireState(listOf(tools, note))

            state.skip() shouldBe QuestionnaireStep.Moved(1)
            state.isLast shouldBe true
        }

        test("returns to the first missing answer when it is submitted early") {
            val state = QuestionnaireState(listOf(role, note))
            state.select(ADMIN)
            state.next()
            state.answer(role.key, Answer.Single(ADMIN))
            state.answer(note.key, Answer.Text("hi"))

            state.next() shouldBe QuestionnaireStep.Submitted(state.answers)

            val fresh = QuestionnaireState(listOf(note, role))
            fresh.next()
            fresh.submit() shouldBe QuestionnaireStep.Invalid(role)
            fresh.index shouldBe 1
        }
    }

    context("answers") {
        test("toggle multiple choices and ignore selections on text questions") {
            val state = QuestionnaireState(listOf(tools, note))

            state.select("ssh")
            state.select("snmp")
            state.select("ssh")
            state.isSelected("snmp") shouldBe true
            state.isSelected("ssh") shouldBe false
            state.answerOf(tools.key) shouldBe Answer.Multiple(setOf("snmp"))
            state.next()
            state.select("ignored")
            state.answerOf(note.key) shouldBe null
            state.isSelected("ignored") shouldBe false
            state.answer(note.key, Answer.Text("hello"))
            state.isSelected("hello") shouldBe false
        }

        test("treat blank text, empty selections and skips as empty") {
            Answer.Text(" ").isEmpty shouldBe true
            Answer.Multiple(emptySet()).isEmpty shouldBe true
            Answer.Skipped.isEmpty shouldBe true
            Answer.Single("a").isEmpty shouldBe false
        }

        test("keep a question invalid while its answer stays empty") {
            val state = QuestionnaireState(listOf(role.copy(kind = QuestionKind.Text)))

            state.next()
            state.answer(role.key, Answer.Text(""))
            state.invalid shouldBe role.key
            state.answer(QuestionKey("other"), Answer.Text("x"))
            state.invalid shouldBe role.key
        }
    }
})
