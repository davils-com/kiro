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

package com.davils.kiro.ui.components.menubar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.FrameWindowScope
import com.davils.kiro.ui.components.menubar.native.NativeMenubar
import com.davils.kiro.ui.components.menubar.native.nativeMenusOf

/**
 * The menubar of a window: on macOS the menus go to the menu bar of the system, elsewhere a [Menubar] shows them
 * in the window.
 *
 * @param modifier the modifier of the [Menubar]; not used on macOS.
 * @param platform the platform the application runs on, which decides where the menus go.
 * @param style the look of the [Menubar]; not used on macOS.
 * @param content declares the menus with [MenubarScope.menu].
 * @since 1.0.0
 */
@Composable
public fun FrameWindowScope.WindowMenubar(
    modifier: Modifier = Modifier,
    platform: DesktopPlatform = DesktopPlatform.current(),
    style: MenubarStyle = menubarStyle(),
    content: MenubarScope.() -> Unit,
) {
    if (platform == DesktopPlatform.MacOs) {
        val menus = MenubarScopeImpl().apply(content).menus
        NativeMenubar.Emit(window = this, menus = nativeMenusOf(menus))
        return
    }
    Menubar(modifier = modifier, style = style, content = content)
}
