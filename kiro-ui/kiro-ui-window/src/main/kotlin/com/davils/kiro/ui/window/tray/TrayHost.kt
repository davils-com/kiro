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

package com.davils.kiro.ui.window.tray

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ApplicationScope
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Shows the tray icons of Kiro. The default host shows them in the tray of the operating system; a test provides
 * another host through [LocalTrayHost] to compose a tray without one.
 *
 * @since 1.0.0
 */
@DavilsInternal
public interface TrayHost {
    /**
     * Whether the operating system has a tray this host can show icons in.
     *
     * @since 1.0.0
     */
    public val isSupported: Boolean

    /**
     * Shows a tray icon for as long as this call stays in the composition.
     *
     * @param scope the application the icon belongs to.
     * @param spec the image, tooltip, menu, state and action of the icon.
     * @since 1.0.0
     */
    @Composable
    public fun Show(scope: ApplicationScope, spec: TraySpec)
}
