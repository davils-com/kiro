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

package com.davils.kiro.ui.window.titlebar

/**
 * What the window buttons of a title bar do.
 *
 * @since 1.0.0
 */
public interface WindowActions {
    /**
     * Whether the window is maximized, which turns the maximize button into a restore button.
     *
     * @since 1.0.0
     */
    public val isMaximized: Boolean

    /**
     * Minimizes the window.
     *
     * @since 1.0.0
     */
    public fun minimize()

    /**
     * Maximizes the window, or restores it while it is maximized.
     *
     * @since 1.0.0
     */
    public fun toggleMaximize()

    /**
     * Asks to close the window.
     *
     * @since 1.0.0
     */
    public fun close()
}
