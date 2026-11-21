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

package com.davils.kiro.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.rememberWindowState
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.WindowMenubar
import com.davils.kiro.ui.core.ApplicationThemeProvider
import com.davils.kiro.ui.window.ChromeWindow
import com.davils.kiro.ui.window.WindowOptions
import com.davils.kiro.ui.window.application.ApplicationMenu
import com.davils.kiro.ui.window.rememberChromeWindowState
import com.davils.kiro.ui.window.titlebar.WindowTitle
import com.davils.kiro.ui.window.tray.Tray
import com.davils.kiro.ui.window.tray.TrayIcon
import com.davils.kiro.ui.window.tray.TrayNotification
import com.davils.kiro.ui.window.tray.rememberTrayState

private const val TITLE: String = "Kiro UI"

@Composable
internal fun ApplicationScope.GalleryWindow(onQuit: () -> Unit, platform: DesktopPlatform = DesktopPlatform.current()) {
    var isDark by remember { mutableStateOf(false) }
    var isShown by remember { mutableStateOf(true) }
    val menus = remember { GalleryMenus(onQuit = onQuit) }
    val tray = rememberTrayState()
    val state = rememberChromeWindowState(window = rememberWindowState(size = DpSize(width = 1280.dp, height = 860.dp)))
    val icon = remember { TrayIcon(light = GalleryMark(isDark = false), dark = GalleryMark(isDark = true)) }
    ApplicationThemeProvider(theme = galleryThemeOf(isDark)) {
        ApplicationMenu(
            onQuitRequest = { request -> request.confirm() },
            onAbout = { tray.sendNotification(TrayNotification(title = TITLE, message = "The gallery of Kiro.")) },
            onReopen = { isShown = true },
        )
        Tray(
            icon = icon,
            tooltip = TITLE,
            state = tray,
            onAction = { isShown = true },
            menu = {
                item(label = "Show $TITLE", onClick = { isShown = true })
                checkbox(label = "Dark Theme", checked = isDark, onCheckedChange = { dark -> isDark = dark })
                separator()
                item(label = "Quit", onClick = onQuit)
            },
        )
        ChromeWindow(
            onCloseRequest = onQuit,
            title = TITLE,
            state = state,
            options = WindowOptions(isVisible = isShown),
            platform = platform,
            titleBar = {
                start { WindowMenubar(platform = platform, content = menus::describe) }
                center { WindowTitle(TITLE) }
            },
        ) {
            Gallery(isDark = isDark, onDarkChange = { dark -> isDark = dark })
        }
    }
}
