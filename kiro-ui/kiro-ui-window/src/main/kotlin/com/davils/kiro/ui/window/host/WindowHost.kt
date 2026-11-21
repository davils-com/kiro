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

package com.davils.kiro.ui.window.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import com.davils.kiro.ui.window.frame.WindowFrameApi
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Opens the windows of Kiro. The default host opens windows of the operating system; a test provides another host
 * through [LocalWindowHost] to compose a window without opening one.
 *
 * @since 1.0.0
 */
@DavilsInternal
public interface WindowHost {
    /**
     * Whether the windows of this host can keep the frame of the operating system and still draw into its title bar.
     *
     * @since 1.0.0
     */
    public val isDecorationSupported: Boolean

    /**
     * Opens a window and composes [content] in it.
     *
     * @param spec what the window looks like and how it behaves.
     * @param content the content of the window, which receives the frame of the window.
     * @since 1.0.0
     */
    @Composable
    public fun Open(spec: WindowSpec, content: @Composable FrameWindowScope.(WindowFrameApi) -> Unit)
}
