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

package com.davils.kiro.ui.window

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import com.davils.kiro.ui.components.menubar.DesktopPlatform
import com.davils.kiro.ui.window.frame.ChromeContent
import com.davils.kiro.ui.window.frame.ChromeSetup
import com.davils.kiro.ui.window.frame.FrameStrategy
import com.davils.kiro.ui.window.frame.frameStrategyOf
import com.davils.kiro.ui.window.host.LocalWindowHost
import com.davils.kiro.ui.window.host.WindowSpec
import com.davils.kiro.ui.window.titlebar.WindowTitle
import com.davils.kiro.ui.window.titlebar.WindowTitleBarScope
import com.davils.kiro.ui.window.titlebar.titleBarStyle
import com.davils.kore.core.annotation.DavilsInternal

/**
 * A window whose title bar Kiro draws in the theme of the application and that still behaves like a window of the
 * operating system.
 *
 * Where the JetBrains Runtime offers it, the window keeps the frame of Windows or macOS, with its shadow, its
 * resizing, its window buttons and the snap layouts of Windows, and Kiro draws only into its title bar. On macOS
 * without it, the content reaches under the transparent title bar next to the traffic lights. Everywhere else Kiro
 * draws the whole frame: rounded corners, a border, the window buttons and edges that resize the window.
 *
 * The title sits in the middle of the window by default, whatever the start and end slots of the bar hold.
 *
 * @param onCloseRequest called when the user asks to close the window.
 * @param title the title of the window, shown in the title bar and by the operating system in its switcher.
 * @param state the position, size and placement of the window.
 * @param options the icon, the visibility and the keyboard handlers of the window.
 * @param platform the operating system whose windows the window resembles.
 * @param style the style of the frame; the title bar takes the style of [titleBarStyle] for [platform], which a
 * theme changes under [com.davils.kiro.ui.window.titlebar.TitleBarStyleKey].
 * @param titleBar fills the slots of the title bar, the [WindowTitle] in its center by default.
 * @param content the content under the title bar.
 * @since 1.0.0
 */
@Composable
public fun ChromeWindow(
    onCloseRequest: () -> Unit,
    title: String,
    state: ChromeWindowState = rememberChromeWindowState(),
    options: WindowOptions = WindowOptions(),
    platform: DesktopPlatform = DesktopPlatform.current(),
    style: ChromeWindowStyle = chromeWindowStyle(platform),
    titleBar: WindowTitleBarScope.() -> Unit = { center { WindowTitle(title) } },
    content: @Composable FrameWindowScope.() -> Unit,
) {
    val host = LocalWindowHost.current
    val strategy = frameStrategyOf(
        platform = platform,
        preference = style.frame,
        isDecorationSupported = host.isDecorationSupported,
    )
    val setup = ChromeSetup(
        state = state,
        strategy = strategy,
        style = style,
        titleBarStyle = titleBarStyle(platform),
        theme = theme,
    )
    val spec = WindowSpec(
        onCloseRequest = onCloseRequest,
        state = state.window,
        title = title,
        options = options,
        isUndecorated = !strategy.hasSystemFrame,
        isTransparent = isTransparent(strategy = strategy, style = style),
        isResizable = true,
    )
    host.Open(spec) { api ->
        ChromeContent(api = api, setup = setup, onCloseRequest = onCloseRequest, titleBar = titleBar, content = content)
    }
}

internal fun isTransparent(strategy: FrameStrategy, style: ChromeWindowStyle): Boolean =
    strategy == FrameStrategy.Emulated && style.cornerRadius > 0.dp
