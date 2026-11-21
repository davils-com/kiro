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

import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.MenubarScope

/**
 * Adds the application menu to the menubar of a window on Windows and Linux: About, Settings, a separator and Quit,
 * leaving out the items without an action. On macOS it adds nothing, since the application menu of the system holds
 * these items there; [ApplicationMenu] connects them.
 *
 * @param label the text of the menu, usually the name of the application or File.
 * @param onQuit called when the user chooses Quit.
 * @param onAbout called when the user chooses About, or `null` to leave the item out.
 * @param onSettings called when the user chooses Settings, or `null` to leave the item out.
 * @param platform the platform the application runs on, which decides whether the menu is added.
 * @param labels the texts of the items.
 * @since 1.0.0
 */
public fun MenubarScope.applicationMenu(
    label: String,
    onQuit: () -> Unit,
    onAbout: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
    platform: DesktopPlatform = DesktopPlatform.current(),
    labels: ApplicationMenuLabels = ApplicationMenuLabels(),
) {
    if (platform == DesktopPlatform.MacOs) return
    menu(label = label) {
        onAbout?.let { action -> item(label = labels.about, onClick = action) }
        onSettings?.let { action -> item(label = labels.settings, onClick = action) }
        if (onAbout != null || onSettings != null) separator()
        item(label = labels.quit, onClick = onQuit)
    }
}
