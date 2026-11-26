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

import com.davils.knot.platform.OperatingSystem

private const val OS_NAME_PROPERTY: String = "os.name"

/**
 * The operating system that a desktop application runs on, as far as the menus of a window depend on it.
 *
 * @since 1.0.0
 */
public enum class DesktopPlatform {
    /**
     * macOS, whose windows share the menu bar of the system.
     *
     * @since 1.0.0
     */
    MacOs,

    /**
     * Windows, where a window shows its menus in a [Menubar] of its own.
     *
     * @since 1.0.0
     */
    Windows,

    /**
     * Linux, where a window shows its menus in a [Menubar] of its own.
     *
     * @since 1.0.0
     */
    Linux,

    /**
     * Any other operating system.
     *
     * @since 1.0.0
     */
    Other,
    ;

    /**
     * Finds the platform that the application runs on.
     *
     * @since 1.0.0
     */
    public companion object {
        /**
         * Returns the platform that the application runs on, read from the system property `os.name`.
         *
         * @return the platform that `os.name` names, or [DesktopPlatform.Other] for any other system.
         * @since 1.0.0
         */
        public fun current(): DesktopPlatform = platformOf(System.getProperty(OS_NAME_PROPERTY).orEmpty())
    }
}

internal fun platformOf(osName: String): DesktopPlatform = when (OperatingSystem.resolve(osName.trim())) {
    OperatingSystem.MacOs -> DesktopPlatform.MacOs
    OperatingSystem.Windows -> DesktopPlatform.Windows
    OperatingSystem.Linux -> DesktopPlatform.Linux
    OperatingSystem.Wasm, null -> DesktopPlatform.Other
}
