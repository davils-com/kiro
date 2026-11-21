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

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

/**
 * Fills the three slots of a title bar. Each slot may be filled more than once; its parts then show in the order they
 * were added. The start and the end slot follow the direction of the language, the window buttons do not.
 *
 * @since 1.0.0
 */
public interface TitleBarScope {
    /**
     * Adds content to the slot at the start of the bar, such as a menubar or a logo.
     *
     * @param content the content.
     * @since 1.0.0
     */
    public fun start(content: @Composable RowScope.() -> Unit)

    /**
     * Adds content to the slot in the center of the bar, such as the title or a search field.
     *
     * @param content the content.
     * @since 1.0.0
     */
    public fun center(content: @Composable RowScope.() -> Unit)

    /**
     * Adds content to the slot at the end of the bar, such as buttons or a notification bell.
     *
     * @param content the content.
     * @since 1.0.0
     */
    public fun end(content: @Composable RowScope.() -> Unit)
}
