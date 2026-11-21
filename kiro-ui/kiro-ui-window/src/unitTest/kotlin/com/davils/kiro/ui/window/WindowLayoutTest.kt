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

import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.window.testing.ScreenMock
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

private val visible: DpSize = DpSize(width = 100.dp, height = 40.dp)

@OptIn(DavilsInternal::class)
class WindowLayoutTest : FunSpec({
    test("remembers the floating bounds and the placement of a window") {
        val state = ChromeWindowState(
            window = WindowState(
                position = WindowPosition(x = 10.dp, y = 20.dp),
                size = DpSize(width = 800.dp, height = 600.dp),
            ),
        )

        state.layout shouldBe WindowLayout(
            bounds = DpRect(left = 10.dp, top = 20.dp, right = 810.dp, bottom = 620.dp),
            placement = WindowPlacement.Floating,
        )
    }

    test("opens a remembered window where it was") {
        val layout = WindowLayout(
            bounds = DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp),
            placement = WindowPlacement.Maximized,
        )

        val state = chromeWindowStateOf(layout = layout, screens = listOf(ScreenMock.PRIMARY), minimumVisible = visible)

        state.window.placement shouldBe WindowPlacement.Maximized
        state.window.position shouldBe WindowPosition(x = 100.dp, y = 100.dp)
        state.window.size shouldBe DpSize(width = 800.dp, height = 600.dp)
    }

    test("moves a remembered window off a screen that is gone") {
        val layout = WindowLayout(
            bounds = DpRect(left = 3000.dp, top = 100.dp, right = 3800.dp, bottom = 700.dp),
            placement = WindowPlacement.Floating,
        )

        val state = chromeWindowStateOf(layout = layout, screens = listOf(ScreenMock.PRIMARY), minimumVisible = visible)

        state.window.position shouldBe WindowPosition(x = 560.dp, y = 232.dp)
    }

    test("is a value") {
        val layout = WindowLayout(
            bounds = DpRect(left = 0.dp, top = 0.dp, right = 10.dp, bottom = 10.dp),
            placement = WindowPlacement.Floating,
        )

        layout.copy(placement = WindowPlacement.Fullscreen).placement shouldBe WindowPlacement.Fullscreen
        layout.copy() shouldBe layout
        layout.hashCode() shouldBe layout.copy().hashCode()
        layout.toString() shouldContain "placement=Floating"
    }
})
