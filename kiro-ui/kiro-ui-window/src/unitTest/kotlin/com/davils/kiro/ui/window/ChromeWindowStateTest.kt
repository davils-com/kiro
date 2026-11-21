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

package com.davils.kiro.ui.window

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val restored: DpRect = DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp)

private fun window(): WindowState =
    WindowState(position = WindowPosition(x = 10.dp, y = 20.dp), size = DpSize(width = 300.dp, height = 200.dp))

@OptIn(ExperimentalTestApi::class)
class ChromeWindowStateTest : FunSpec({
    context("a floating window") {
        test("reports the placement and the bounds of the window") {
            val window = window()
            val state = ChromeWindowState(window)
            window.placement = WindowPlacement.Fullscreen

            state.isMaximized shouldBe false
            state.placement shouldBe WindowPlacement.Fullscreen
            state.floatingBounds shouldBe DpRect(left = 10.dp, top = 20.dp, right = 310.dp, bottom = 220.dp)
        }
    }

    context("an emulated maximized window") {
        test("reports the maximized placement and the bounds it restores to") {
            val state = ChromeWindowState(window(), restored)

            state.isMaximized shouldBe true
            state.placement shouldBe WindowPlacement.Maximized
            state.floatingBounds shouldBe restored
        }
    }

    context("remembering") {
        test("starts from the given window and restore bounds") {
            runComposeUiTest {
                val window = window()
                var remembered: ChromeWindowState? = null
                setContent { remembered = rememberChromeWindowState(window, restored) }
                waitForIdle()

                remembered?.window shouldBe window
                remembered?.restoreBounds shouldBe restored
            }
        }
    }
})
