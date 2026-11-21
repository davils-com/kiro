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

package com.davils.kiro.ui.window.tray

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.ApplicationScope
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.native.nativeEntriesOf
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Shows an icon in the tray of the operating system for as long as this call stays in the composition, with a menu
 * declared with the same entries as every other menu of Kiro.
 *
 * Not every desktop has a tray: GNOME without the AppIndicator extension, for one, has none. There [onUnsupported] is
 * called once instead, so that the application can offer another way back to its windows.
 *
 * @param icon the images of the icon, chosen for the platform and the theme of the system.
 * @param tooltip the text shown while the pointer rests on the icon.
 * @param menu declares the entries of the menu of the icon; an empty menu shows none.
 * @param state the state through which the application sends notifications.
 * @param onAction called when the user activates the icon, by a double click on Windows.
 * @param onUnsupported called once when the operating system has no tray.
 * @param platform the platform the application runs on, which decides the image of the icon.
 * @since 1.0.0
 */
@Composable
public fun ApplicationScope.Tray(
    icon: TrayIcon,
    tooltip: String,
    menu: MenuScope.() -> Unit,
    state: TrayState = rememberTrayState(),
    onAction: () -> Unit = {},
    onUnsupported: () -> Unit = {},
    platform: DesktopPlatform = DesktopPlatform.current(),
) {
    val host = LocalTrayHost.current
    val currentOnUnsupported by rememberUpdatedState(onUnsupported)
    if (!host.isSupported) {
        LaunchedEffect(host) { currentOnUnsupported() }
        return
    }
    val spec = TraySpec(
        icon = icon.painterFor(platform = platform, isDark = isSystemInDarkTheme()),
        tooltip = tooltip,
        entries = nativeEntriesOf(menu),
        state = state,
        onAction = onAction,
    )
    host.Show(scope = this, spec = spec)
}

internal fun TrayIcon.painterFor(platform: DesktopPlatform, isDark: Boolean): Painter {
    val themed = dark.takeIf { isDark } ?: light
    return template.takeIf { platform == DesktopPlatform.MacOs } ?: themed
}
