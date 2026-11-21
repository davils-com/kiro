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

package com.davils.kiro.ui.window.controls

import androidx.compose.runtime.Composable
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.WindowActions

/**
 * The window buttons a title bar draws, such as [WindowsControls], [LinuxControls] or the room for the traffic lights
 * of [MacOsControls].
 *
 * @since 1.0.0
 */
public interface WindowControls {
    /**
     * The physical side of the bar the buttons sit on, which does not follow the direction of the language.
     *
     * @since 1.0.0
     */
    public val placement: ControlPlacement

    /**
     * Draws the buttons.
     *
     * @param actions what the buttons do.
     * @param style the style of the title bar.
     * @since 1.0.0
     */
    @Composable
    public fun Content(actions: WindowActions, style: TitleBarStyle)
}
