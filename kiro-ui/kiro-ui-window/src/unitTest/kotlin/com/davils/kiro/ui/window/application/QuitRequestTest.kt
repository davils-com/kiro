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

package com.davils.kiro.ui.window.application

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val CONFIRM: String = "confirm"

private const val CANCEL: String = "cancel"

class QuitRequestTest : FunSpec({
    context("quit request") {
        test("confirms once and ignores every later answer") {
            val answers = mutableListOf<String>()
            val request = QuitRequest(onConfirm = { answers += CONFIRM }, onCancel = { answers += CANCEL })

            request.isAnswered shouldBe false
            request.confirm()
            request.confirm()
            request.cancel()

            answers shouldBe listOf(CONFIRM)
            request.isAnswered shouldBe true
            request.toString() shouldBe "QuitRequest(isAnswered=true)"
        }

        test("cancels once and ignores every later answer") {
            val answers = mutableListOf<String>()
            val request = QuitRequest(onConfirm = { answers += CONFIRM }, onCancel = { answers += CANCEL })

            request.cancel()
            request.confirm()
            request.cancel()

            answers shouldBe listOf(CANCEL)
        }
    }
})
