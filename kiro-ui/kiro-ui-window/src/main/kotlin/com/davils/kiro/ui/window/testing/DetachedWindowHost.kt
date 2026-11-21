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

package com.davils.kiro.ui.window.testing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.FrameWindowScope
import com.davils.kiro.ui.window.frame.WindowFrameApi
import com.davils.kiro.ui.window.host.WindowHost
import com.davils.kiro.ui.window.host.WindowSpec
import com.davils.kore.core.annotation.DavilsInternal

/**
 * A [WindowHost] for tests that composes the content of a window in place, without a window of the operating system,
 * and records the [WindowSpec] of every window it opened.
 *
 * Provide it through [com.davils.kiro.ui.window.host.LocalWindowHost] in a test of a composable that opens windows.
 *
 * @param api the frame the windows read and change, a [WindowFrameMock] by default.
 * @param isDecorationSupported whether the windows behave as on the JetBrains Runtime with its window decorations.
 * @since 1.0.0
 */
@DavilsInternal
public class DetachedWindowHost(
    private val api: WindowFrameApi = WindowFrameMock(),
    override val isDecorationSupported: Boolean = false,
) : WindowHost {
    private val specs = mutableListOf<WindowSpec>()

    /**
     * The specs of the windows this host opened, in the order they opened.
     *
     * @since 1.0.0
     */
    public val opened: List<WindowSpec>
        get() = specs.toList()

    @Composable
    override fun Open(spec: WindowSpec, content: @Composable FrameWindowScope.(WindowFrameApi) -> Unit) {
        remember(spec) { specs.add(spec) }
        if (!spec.options.isVisible) return
        with(DetachedWindowScope) { content(api) }
    }
}
