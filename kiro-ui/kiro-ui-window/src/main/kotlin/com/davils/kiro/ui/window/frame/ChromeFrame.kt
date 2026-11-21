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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.size
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import com.davils.kiro.ui.window.ChromeWindowState
import com.davils.kiro.ui.window.screen.workAreaOf
import com.davils.kiro.ui.window.titlebar.TitleBarChrome
import com.davils.kiro.ui.window.titlebar.WindowActions
import com.davils.kore.core.annotation.DavilsInternal

internal class ChromeFrame(
    private val chrome: ChromeWindowState,
    private val api: WindowFrameApi,
    val strategy: FrameStrategy,
    private val minimumSize: DpSize,
    private val onClose: () -> Unit,
) : WindowActions {
    private val state: WindowState
        get() = chrome.window

    var captionInsets: CaptionInsets by mutableStateOf(CaptionInsets.None)
        private set

    override val isMaximized: Boolean
        get() = chrome.placement == WindowPlacement.Maximized

    val isFullscreen: Boolean
        get() = state.placement == WindowPlacement.Fullscreen

    val isResizable: Boolean
        get() = !strategy.hasSystemFrame && !isMaximized && !isFullscreen

    val titleBarChrome: TitleBarChrome
        get() = TitleBarChrome(insets = captionInsets, isSystemControlled = strategy == FrameStrategy.Decorated)

    private val isSystemManaged: Boolean
        get() = strategy.hasSystemFrame || api.isSystemMoveSupported()

    fun prepare(titleBarHeight: Dp, isDark: Boolean) {
        api.limit(minimumSize)
        when (strategy) {
            FrameStrategy.Decorated -> captionInsets = api.decorate(height = titleBarHeight, isDark = isDark)
            FrameStrategy.FullContent -> api.extendIntoTitleBar()
            FrameStrategy.Emulated -> emulateMaximized()
        }
    }

    override fun minimize() {
        state.isMinimized = true
    }

    override fun close() {
        onClose()
    }

    override fun toggleMaximize() {
        val restore = chrome.restoreBounds
        if (restore != null) {
            chrome.restoreBounds = null
            place(restore)
            return
        }
        if (isSystemManaged) {
            state.placement = toggledPlacement(state.placement)
            return
        }
        maximizeOnWorkArea()
    }

    fun hitTest(isClient: Boolean) {
        api.hitTest(isClient)
    }

    fun beginDrag(): FrameDrag? {
        if (isHandedToSystem()) return null
        return beginManualDrag()
    }

    fun drag(session: FrameDrag) {
        val pointer = api.pointer() ?: return
        place(movedBounds(start = session.bounds, delta = pointer - session.pointer))
    }

    fun beginResize(): FrameDrag? {
        val pointer = api.pointer() ?: return null
        return FrameDrag(pointer = pointer, bounds = boundsOf(state))
    }

    fun resize(edge: ResizeEdge, session: FrameDrag) {
        val pointer = api.pointer() ?: return
        val delta = pointer - session.pointer
        place(resizedBounds(edge = edge, start = session.bounds, delta = delta, minimum = minimumSize))
    }

    private fun emulateMaximized() {
        if (isSystemManaged || state.placement != WindowPlacement.Maximized) return
        state.placement = WindowPlacement.Floating
        maximizeOnWorkArea()
    }

    private fun maximizeOnWorkArea() {
        chrome.restoreBounds = boundsOf(state)
        place(workAreaOf(screen = api.screenBounds(), insets = api.screenInsets()))
    }

    private fun beginManualDrag(): FrameDrag? {
        val pointer = api.pointer() ?: return null
        detach(pointer)
        return FrameDrag(pointer = pointer, bounds = boundsOf(state))
    }

    private fun isHandedToSystem(): Boolean {
        val isHandedOver = chrome.restoreBounds == null && api.isSystemMoveSupported()
        if (isHandedOver) api.startSystemMove()
        return isHandedOver
    }

    private fun detach(pointer: DpOffset) {
        if (state.placement == WindowPlacement.Maximized) {
            state.placement = WindowPlacement.Floating
            return
        }
        val restore = chrome.restoreBounds ?: return
        chrome.restoreBounds = null
        place(restoredBounds(pointer = pointer, maximized = boundsOf(state), restored = restore.size))
    }

    private fun place(bounds: DpRect) {
        state.position = WindowPosition(x = bounds.left, y = bounds.top)
        state.size = bounds.size
    }
}
