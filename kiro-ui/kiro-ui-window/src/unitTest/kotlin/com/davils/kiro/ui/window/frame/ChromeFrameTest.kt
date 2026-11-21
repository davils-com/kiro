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
import com.davils.kiro.ui.window.ChromeWindowState
import com.davils.kiro.ui.window.testing.WindowFrameMock
import com.davils.kore.core.annotation.DavilsInternal
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

private val minimum = DpSize(width = 400.dp, height = 300.dp)

private fun floating(): WindowState =
    WindowState(position = WindowPosition(x = 100.dp, y = 100.dp), size = DpSize(width = 800.dp, height = 600.dp))

private fun frameOf(
    state: WindowState,
    api: WindowFrameMock = WindowFrameMock(),
    strategy: FrameStrategy = FrameStrategy.Emulated,
    onClose: () -> Unit = {},
    chrome: ChromeWindowState = ChromeWindowState(state),
): ChromeFrame = ChromeFrame(
    chrome = chrome,
    api = api,
    strategy = strategy,
    minimumSize = minimum,
    onClose = onClose,
)

class ChromeFrameTest : FunSpec({
    context("preparing") {
        test("limits the window to the minimum size and keeps its own title bar off macOS") {
            val api = WindowFrameMock()

            frameOf(floating(), api).prepare(titleBarHeight = 40.dp, isDark = false)

            api.limited shouldBe minimum
            api.isExtended shouldBe false
        }

        test("draws into the native title bar on macOS") {
            val api = WindowFrameMock()

            frameOf(
                floating(),
                api,
                strategy = FrameStrategy.FullContent,
            ).prepare(titleBarHeight = 40.dp, isDark = false)

            api.isExtended shouldBe true
        }
    }

    context("buttons") {
        test("minimizes the window") {
            val state = floating()

            frameOf(state).minimize()

            state.isMinimized shouldBe true
        }

        test("closes through the close request") {
            var isClosed = false

            frameOf(floating(), onClose = { isClosed = true }).close()

            isClosed shouldBe true
        }
    }

    context("maximizing a framed window") {
        test("fills the work area and restores the previous bounds") {
            val state = floating()
            val frame = frameOf(state)

            frame.toggleMaximize()

            frame.isMaximized shouldBe true
            frame.isResizable shouldBe false
            boundsOf(state) shouldBe DpRect(left = 0.dp, top = 32.dp, right = 1920.dp, bottom = 1032.dp)

            frame.toggleMaximize()

            frame.isMaximized shouldBe false
            boundsOf(state) shouldBe DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp)
        }

        test("keeps the emulated maximize in the chrome state") {
            val state = floating()
            val chrome = ChromeWindowState(state)
            val frame = frameOf(state, chrome = chrome)

            frame.toggleMaximize()

            state.placement shouldBe WindowPlacement.Floating
            chrome.placement shouldBe WindowPlacement.Maximized
            chrome.floatingBounds shouldBe DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp)
        }

        test("restores the bounds of a window that was opened maximized") {
            val state = WindowState(
                position = WindowPosition(x = 0.dp, y = 32.dp),
                size = DpSize(width = 1920.dp, height = 1000.dp),
            )
            val restored = DpRect(left = 200.dp, top = 150.dp, right = 1000.dp, bottom = 750.dp)
            val frame = frameOf(state, chrome = ChromeWindowState(state, restored))

            frame.isMaximized shouldBe true
            frame.toggleMaximize()

            frame.isMaximized shouldBe false
            boundsOf(state) shouldBe restored
        }
    }

    context("maximizing a native window") {
        test("toggles the placement of the platform") {
            val state = floating()
            val frame = frameOf(state, strategy = FrameStrategy.FullContent)

            frame.toggleMaximize()

            state.placement shouldBe WindowPlacement.Maximized
            frame.isMaximized shouldBe true
            frame.isResizable shouldBe false
        }
    }

    context("a platform that moves windows itself") {
        test("hands a drag to the operating system and moves nothing by hand") {
            val state = floating()
            val api = WindowFrameMock(pointer = DpOffset(x = 200.dp, y = 110.dp), isSystemMoving = true)
            val frame = frameOf(state, api)

            val session = frame.beginDrag()

            session shouldBe null
            api.systemMoves shouldBe 1
            boundsOf(state) shouldBe boundsOf(floating())
        }

        test("hands a drag of a maximized window to the operating system as well") {
            val state = floating()
            state.placement = WindowPlacement.Maximized
            val api = WindowFrameMock(isSystemMoving = true)

            frameOf(state, api).beginDrag()

            api.systemMoves shouldBe 1
            state.placement shouldBe WindowPlacement.Maximized
        }

        test("maximizes through the placement of the platform instead of emulating it") {
            val state = floating()
            val chrome = ChromeWindowState(state)
            val frame = frameOf(state, WindowFrameMock(isSystemMoving = true), chrome = chrome)

            frame.toggleMaximize()
            val maximized = state.placement
            frame.toggleMaximize()

            maximized shouldBe WindowPlacement.Maximized
            chrome.restoreBounds shouldBe null
            state.placement shouldBe WindowPlacement.Floating
        }

        test("restores an emulated maximize by hand before the operating system takes over") {
            val state = floating()
            val restore = DpRect(left = 40.dp, top = 60.dp, right = 640.dp, bottom = 460.dp)
            val chrome = ChromeWindowState(state, restoreBounds = restore)
            val api = WindowFrameMock(pointer = DpOffset(x = 300.dp, y = 110.dp), isSystemMoving = true)
            val frame = frameOf(state, api, chrome = chrome)

            val session = frame.beginDrag()

            session shouldNotBe null
            api.systemMoves shouldBe 0
            chrome.restoreBounds shouldBe null
        }

        test("leaves an emulated maximize by restoring its bounds") {
            val state = floating()
            val restore = DpRect(left = 40.dp, top = 60.dp, right = 640.dp, bottom = 460.dp)
            val chrome = ChromeWindowState(state, restoreBounds = restore)
            val frame = frameOf(state, WindowFrameMock(isSystemMoving = true), chrome = chrome)

            frame.toggleMaximize()

            boundsOf(state) shouldBe restore
            state.placement shouldBe WindowPlacement.Floating
        }
    }

    context("dragging") {
        test("moves the window with the pointer") {
            val state = floating()
            val api = WindowFrameMock(pointer = DpOffset(x = 200.dp, y = 110.dp))
            val frame = frameOf(state, api)

            val session = requireNotNull(frame.beginDrag())
            api.pointer = DpOffset(x = 260.dp, y = 150.dp)
            frame.drag(session)

            boundsOf(state) shouldBe DpRect(left = 160.dp, top = 140.dp, right = 960.dp, bottom = 740.dp)
        }

        test("restores a maximized window under the pointer") {
            val state = floating()
            val api = WindowFrameMock(pointer = DpOffset(x = 960.dp, y = 40.dp))
            val frame = frameOf(state, api)
            frame.toggleMaximize()

            frame.beginDrag()

            frame.isMaximized shouldBe false
            boundsOf(state) shouldBe DpRect(left = 560.dp, top = 32.dp, right = 1360.dp, bottom = 632.dp)
        }

        test("leaves the maximized placement of the platform") {
            val state = floating()
            state.placement = WindowPlacement.Maximized

            frameOf(state, strategy = FrameStrategy.FullContent).beginDrag()

            state.placement shouldBe WindowPlacement.Floating
        }

        test("ignores a drag while the pointer is unknown") {
            val state = floating()
            val api = WindowFrameMock(pointer = null)
            val frame = frameOf(state, api)

            frame.beginDrag() shouldBe null
            frame.drag(FrameDrag(pointer = DpOffset.Zero, bounds = boundsOf(state)))

            boundsOf(state) shouldBe DpRect(left = 100.dp, top = 100.dp, right = 900.dp, bottom = 700.dp)
        }
    }

    context("resizing") {
        test("resizes from the dragged edge") {
            val state = floating()
            val api = WindowFrameMock(pointer = DpOffset(x = 900.dp, y = 400.dp))
            val frame = frameOf(state, api)

            val session = requireNotNull(frame.beginResize())
            api.pointer = DpOffset(x = 1000.dp, y = 400.dp)
            frame.resize(ResizeEdge.East, session)

            boundsOf(state) shouldBe DpRect(left = 100.dp, top = 100.dp, right = 1000.dp, bottom = 700.dp)
        }

        test("ignores a resize while the pointer is unknown") {
            val state = floating()
            val frame = frameOf(state, WindowFrameMock(pointer = null))

            frame.beginResize() shouldBe null
            frame.resize(ResizeEdge.East, FrameDrag(pointer = DpOffset.Zero, bounds = boundsOf(state)))

            state.size shouldBe DpSize(width = 800.dp, height = 600.dp)
        }

        test("stops resizing in full screen") {
            val state = floating()
            state.placement = WindowPlacement.Fullscreen

            val frame = frameOf(state)

            frame.isFullscreen shouldBe true
            frame.isResizable shouldBe false
        }
    }
})
