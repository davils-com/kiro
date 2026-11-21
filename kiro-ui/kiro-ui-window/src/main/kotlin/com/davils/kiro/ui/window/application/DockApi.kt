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

import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kore.core.annotation.DavilsInternal
import java.awt.Image

/**
 * Changes the icon of the application in the dock of macOS or the taskbar of Windows. The default implementation uses
 * `java.awt.Taskbar` and ignores what the platform does not support; a test provides another one through
 * [LocalDockApi].
 *
 * Every setter replaces what it set before; `null` removes it.
 *
 * @since 1.0.0
 */
@DavilsInternal
public interface DockApi {
    /**
     * Shows a badge on the icon of the application.
     *
     * @param badge the text of the badge, or `null` for none.
     * @since 1.0.0
     */
    public fun setBadge(badge: String?)

    /**
     * Shows a progress on the icon of the application.
     *
     * @param percent the progress from `0` to `100`, or `null` for none.
     * @since 1.0.0
     */
    public fun setProgress(percent: Int?)

    /**
     * Replaces the icon of the application.
     *
     * @param image the new icon, or `null` to restore the icon the application started with.
     * @since 1.0.0
     */
    public fun setIcon(image: Image?)

    /**
     * Sets the menu of the icon of the application.
     *
     * @param entries the entries of the menu, or `null` for none.
     * @since 1.0.0
     */
    public fun setMenu(entries: List<NativeMenuEntry>?)

    /**
     * Asks the user to look at the application, such as by bouncing its icon in the dock.
     *
     * @param isCritical whether the request lasts until the user activates the application.
     * @since 1.0.0
     */
    public fun requestAttention(isCritical: Boolean)
}
