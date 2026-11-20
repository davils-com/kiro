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
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.components.menubar.WindowMenubar
import com.davils.kiro.ui.core.ApplicationThemeProvider

private const val TITLE: String = "Kiro UI"

@Composable
internal fun GalleryWindow(onQuit: () -> Unit, platform: DesktopPlatform = DesktopPlatform.current()) {
    var isDark by remember { mutableStateOf(false) }
    val menus = remember { GalleryMenus(onQuit = onQuit) }
    val state = rememberWindowState(size = DpSize(width = 1280.dp, height = 860.dp))
    Window(onCloseRequest = onQuit, title = TITLE, state = state) {
        ApplicationThemeProvider(theme = galleryThemeOf(isDark)) {
            WindowMenubar(platform = platform, content = menus::describe)
            Gallery(isDark = isDark, onDarkChange = { dark -> isDark = dark })
        }
    }
}
