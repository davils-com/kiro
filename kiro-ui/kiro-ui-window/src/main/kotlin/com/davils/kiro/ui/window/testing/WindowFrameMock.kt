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

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.window.frame.CaptionInsets
import com.davils.kiro.ui.window.frame.WindowFrameApi
import com.davils.kiro.ui.window.screen.ScreenInsets
import com.davils.kore.core.annotation.DavilsInternal

/**
 * A [WindowFrameApi] for tests: a scripted pointer, one screen with reserved edges and counters of what the chrome
 * asked of the frame.
 *
 * @property pointer the position the next call of [pointer] returns, or `null` for an unknown pointer.
 * @param step how far the pointer moves after each call of [pointer].
 * @param screen the bounds of the screen.
 * @param insets the reserved edges of the screen.
 * @param captionInsets the widths the system draws into once the window is decorated.
 * @param isSystemMoving whether the system can move the window together with the pointer.
 * @since 1.0.0
 */
@DavilsInternal
public class WindowFrameMock(
    public var pointer: DpOffset? = DpOffset.Zero,
    private val step: DpOffset = DpOffset.Zero,
    private val screen: DpRect = DpRect(left = 0.dp, top = 0.dp, right = 1920.dp, bottom = 1080.dp),
    private val insets: ScreenInsets = ScreenInsets(left = 0.dp, top = 32.dp, right = 0.dp, bottom = 48.dp),
    private val captionInsets: CaptionInsets = CaptionInsets(left = 0.dp, right = 138.dp),
    private val isSystemMoving: Boolean = false,
) : WindowFrameApi {
    private val tests = mutableListOf<Boolean>()

    /**
     * The smallest size the chrome gave the window, or `null` before it gave one.
     *
     * @since 1.0.0
     */
    public var limited: DpSize? = null
        private set

    /**
     * Whether the chrome let the content reach under the title bar of macOS.
     *
     * @since 1.0.0
     */
    public var isExtended: Boolean = false
        private set

    /**
     * The height of the title bar the chrome asked the system for, or `null` before it decorated the window.
     *
     * @since 1.0.0
     */
    public var decoratedHeight: Dp? = null
        private set

    /**
     * Whether the chrome asked for window buttons that suit a dark title bar.
     *
     * @since 1.0.0
     */
    public var isDecoratedDark: Boolean = false
        private set

    /**
     * The results of the hit tests the chrome reported, in order.
     *
     * @since 1.0.0
     */
    public val hitTests: List<Boolean>
        get() = tests.toList()

    /**
     * How often the chrome handed a move to the system.
     *
     * @since 1.0.0
     */
    public var systemMoves: Int = 0
        private set

    override fun screenBounds(): DpRect = screen

    override fun screenInsets(): ScreenInsets = insets

    override fun pointer(): DpOffset? {
        val current = pointer ?: return null
        pointer = current + step
        return current
    }

    override fun limit(minimum: DpSize) {
        limited = minimum
    }

    override fun extendIntoTitleBar() {
        isExtended = true
    }

    override fun decorate(height: Dp, isDark: Boolean): CaptionInsets {
        decoratedHeight = height
        isDecoratedDark = isDark
        return captionInsets
    }

    override fun hitTest(isClient: Boolean) {
        tests += isClient
    }

    override fun isSystemMoveSupported(): Boolean = isSystemMoving

    override fun startSystemMove() {
        systemMoves += 1
    }
}
