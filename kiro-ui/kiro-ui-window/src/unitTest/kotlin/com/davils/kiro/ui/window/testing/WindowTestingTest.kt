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

@file:OptIn(DavilsInternal::class)

package com.davils.kiro.ui.window.testing

import androidx.compose.ui.input.key.KeyEvent
import com.davils.kiro.ui.window.WindowOptions
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.awt.Label
import java.awt.event.KeyEvent as AwtKeyEvent

class WindowTestingTest : FunSpec({
    context("detached window") {
        test("has no operating system window") {
            shouldThrow<IllegalStateException> { DetachedWindowScope.window }
        }
    }

    context("options") {
        test("lets every key pass by default") {
            val options = WindowOptions()
            val event = KeyEvent(
                AwtKeyEvent(
                    Label(),
                    AwtKeyEvent.KEY_PRESSED,
                    0L,
                    0,
                    AwtKeyEvent.VK_A,
                    'a',
                ),
            )

            options.onPreviewKeyEvent(event) shouldBe false
            options.onKeyEvent(event) shouldBe false
        }
    }
})
