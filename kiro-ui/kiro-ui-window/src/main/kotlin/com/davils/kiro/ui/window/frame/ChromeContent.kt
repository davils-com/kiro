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

package com.davils.kiro.ui.window.frame

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.window.FrameWindowScope
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.window.titlebar.WindowTitleBarScope
import com.davils.kore.core.annotation.DavilsInternal

@Composable
internal fun FrameWindowScope.ChromeContent(
    api: WindowFrameApi,
    setup: ChromeSetup,
    onCloseRequest: () -> Unit,
    titleBar: WindowTitleBarScope.() -> Unit,
    content: @Composable FrameWindowScope.() -> Unit,
) {
    val close by rememberUpdatedState(onCloseRequest)
    val frame = remember(setup.state, api, setup.strategy) {
        ChromeFrame(
            chrome = setup.state,
            api = api,
            strategy = setup.strategy,
            minimumSize = setup.style.minimumSize,
            onClose = { close() },
        )
    }
    ThemeProvider(theme = setup.theme) {
        ChromeFrameLayout(
            frame = frame,
            style = setup.style,
            titleBarStyle = setup.titleBarStyle,
            titleBar = titleBar,
            content = content,
        )
    }
}
