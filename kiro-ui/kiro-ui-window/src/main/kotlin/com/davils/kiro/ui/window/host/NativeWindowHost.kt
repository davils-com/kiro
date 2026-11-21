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

package com.davils.kiro.ui.window.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import com.davils.kiro.ui.window.frame.JdkWindowFrameApi
import com.davils.kiro.ui.window.frame.WindowFrameApi
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import com.jetbrains.JBR

@DavilsPlatformBinding
internal object NativeWindowHost : WindowHost {
    override val isDecorationSupported: Boolean by lazy { JBR.isWindowDecorationsSupported() }

    @Composable
    override fun Open(spec: WindowSpec, content: @Composable FrameWindowScope.(WindowFrameApi) -> Unit) {
        val options = spec.options
        Window(
            onCloseRequest = spec.onCloseRequest,
            state = spec.state,
            visible = options.isVisible,
            title = spec.title,
            icon = options.icon,
            undecorated = spec.isUndecorated,
            transparent = spec.isTransparent,
            resizable = spec.isResizable,
            onPreviewKeyEvent = options.onPreviewKeyEvent,
            onKeyEvent = options.onKeyEvent,
        ) {
            val api = remember(window) { JdkWindowFrameApi(window) }
            content(api)
        }
    }
}
