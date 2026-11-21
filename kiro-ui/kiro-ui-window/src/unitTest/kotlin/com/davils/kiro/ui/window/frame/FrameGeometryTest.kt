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

package com.davils.kiro.ui.window.frame

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.window.screen.ScreenInsets
import com.davils.kiro.ui.window.screen.workAreaOf
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private val start = DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp)
private val minimum = DpSize(width = 400.dp, height = 300.dp)

class FrameGeometryTest : FunSpec({
    context("bounds of a window state") {
        test("reads the absolute position and the size") {
            val state = WindowState(
                position = WindowPosition(x = 10.dp, y = 20.dp),
                size = DpSize(width = 300.dp, height = 200.dp),
            )

            boundsOf(state) shouldBe DpRect(left = 10.dp, top = 20.dp, right = 310.dp, bottom = 220.dp)
        }

        test("starts at the origin while the platform still places the window") {
            val state = WindowState(size = DpSize(width = 300.dp, height = 200.dp))

            boundsOf(state) shouldBe DpRect(left = 0.dp, top = 0.dp, right = 300.dp, bottom = 200.dp)
        }
    }

    context("work area") {
        test("leaves the task bars of the screen free") {
            val screen = DpRect(left = 0.dp, top = 0.dp, right = 1920.dp, bottom = 1080.dp)
            val insets = ScreenInsets(left = 10.dp, top = 20.dp, right = 30.dp, bottom = 40.dp)

            workAreaOf(screen, insets) shouldBe DpRect(left = 10.dp, top = 20.dp, right = 1890.dp, bottom = 1040.dp)
        }
    }

    context("moving") {
        test("shifts every edge by the delta") {
            movedBounds(start, DpOffset(x = 50.dp, y = (-20).dp)) shouldBe
                DpRect(left = 150.dp, top = 80.dp, right = 950.dp, bottom = 680.dp)
        }
    }

    context("resizing") {
        test("moves only the edges of the dragged side") {
            val east = resizedBounds(
                edge = ResizeEdge.East,
                start = start,
                delta = DpOffset(x = 100.dp, y = 100.dp),
                minimum = minimum,
            )

            east shouldBe DpRect(left = 100.dp, top = 100.dp, right = 1000.dp, bottom = 700.dp)
        }

        test("moves both edges of a corner") {
            val corner = resizedBounds(
                edge = ResizeEdge.NorthWest,
                start = start,
                delta = DpOffset(x = (-50).dp, y = (-60).dp),
                minimum = minimum,
            )

            corner shouldBe DpRect(left = 50.dp, top = 40.dp, right = 900.dp, bottom = 700.dp)
        }

        test("never shrinks below the minimum size") {
            val west = resizedBounds(
                edge = ResizeEdge.West,
                start = start,
                delta = DpOffset(x = 700.dp, y = 0.dp),
                minimum = minimum,
            )
            val north = resizedBounds(
                edge = ResizeEdge.North,
                start = start,
                delta = DpOffset(x = 0.dp, y = 700.dp),
                minimum = minimum,
            )
            val southEast = resizedBounds(
                edge = ResizeEdge.SouthEast,
                start = start,
                delta = DpOffset(x = (-700).dp, y = (-700).dp),
                minimum = minimum,
            )

            west.left shouldBe 500.dp
            north.top shouldBe 400.dp
            southEast shouldBe DpRect(left = 100.dp, top = 100.dp, right = 500.dp, bottom = 400.dp)
        }
    }

    context("restoring under the pointer") {
        test("keeps the pointer at the same relative position of the title bar") {
            val maximized = DpRect(left = 0.dp, top = 0.dp, right = 1000.dp, bottom = 800.dp)

            val restored = restoredBounds(
                pointer = DpOffset(x = 750.dp, y = 10.dp),
                maximized = maximized,
                restored = DpSize(width = 400.dp, height = 300.dp),
            )

            restored shouldBe DpRect(left = 450.dp, top = 0.dp, right = 850.dp, bottom = 300.dp)
        }
    }

    context("placement") {
        test("toggles between maximized and floating") {
            toggledPlacement(WindowPlacement.Floating) shouldBe WindowPlacement.Maximized
            toggledPlacement(WindowPlacement.Maximized) shouldBe WindowPlacement.Floating
            toggledPlacement(WindowPlacement.Fullscreen) shouldBe WindowPlacement.Maximized
        }
    }
})
