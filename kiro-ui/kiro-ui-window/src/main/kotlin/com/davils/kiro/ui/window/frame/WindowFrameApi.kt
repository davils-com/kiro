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

package com.davils.kiro.ui.window.frame

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.window.screen.ScreenInsets
import com.davils.kore.core.annotation.DavilsInternal

/**
 * The parts of a window of the operating system that the chrome of Kiro reads and changes. A test provides its own
 * implementation through a [com.davils.kiro.ui.window.host.WindowHost].
 *
 * @since 1.0.0
 */
@DavilsInternal
public interface WindowFrameApi {
    /**
     * Returns the bounds of the screen the window is on.
     *
     * @return the bounds of the screen.
     * @since 1.0.0
     */
    public fun screenBounds(): DpRect

    /**
     * Returns the parts of the screen the window is on that the operating system reserves.
     *
     * @return the reserved parts of the screen.
     * @since 1.0.0
     */
    public fun screenInsets(): ScreenInsets

    /**
     * Returns the position of the pointer on the desktop, or `null` if the operating system does not tell.
     *
     * @return the position of the pointer.
     * @since 1.0.0
     */
    public fun pointer(): DpOffset?

    /**
     * Keeps the user from making the window smaller than [minimum].
     *
     * @param minimum the smallest size of the window.
     * @since 1.0.0
     */
    public fun limit(minimum: DpSize)

    /**
     * Lets the content of a window with the frame of macOS reach under its transparent title bar.
     *
     * @since 1.0.0
     */
    public fun extendIntoTitleBar()

    /**
     * Keeps the frame of the operating system and gives its title bar to the content of the window.
     *
     * @param height the height of the title bar.
     * @param isDark whether the window buttons the system draws should suit a dark title bar.
     * @return the widths the system draws into at the left and the right of the title bar.
     * @since 1.0.0
     */
    public fun decorate(height: Dp, isDark: Boolean): CaptionInsets

    /**
     * Tells the operating system whether the pointer event it is handling belongs to the content of the window or to
     * the title bar, which the system then moves, maximizes or snaps.
     *
     * @param isClient `true` if a control of the content handles the event.
     * @since 1.0.0
     */
    public fun hitTest(isClient: Boolean)

    /**
     * Returns whether the operating system can move the window together with the pointer.
     *
     * @return `true` if [startSystemMove] works.
     * @since 1.0.0
     */
    public fun isSystemMoveSupported(): Boolean

    /**
     * Lets the operating system move the window together with the pointer, which snaps and tiles it like a native
     * window.
     *
     * @since 1.0.0
     */
    public fun startSystemMove()
}
