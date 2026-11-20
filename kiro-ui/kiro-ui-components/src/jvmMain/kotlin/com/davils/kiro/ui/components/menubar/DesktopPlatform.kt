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

private const val OS_NAME_PROPERTY: String = "os.name"

private val prefixes: List<Pair<String, DesktopPlatform>> = listOf(
    "mac" to DesktopPlatform.MacOs,
    "darwin" to DesktopPlatform.MacOs,
    "windows" to DesktopPlatform.Windows,
    "linux" to DesktopPlatform.Linux,
)

public enum class DesktopPlatform {
    MacOs,
    Windows,
    Linux,
    Other,
    ;

    public companion object {
        public fun current(): DesktopPlatform = platformOf(System.getProperty(OS_NAME_PROPERTY).orEmpty())
    }
}

internal fun platformOf(osName: String): DesktopPlatform {
    val name = osName.trim().lowercase()
    val match = prefixes.firstOrNull { (prefix, _) -> name.startsWith(prefix) }
    return match?.second ?: DesktopPlatform.Other
}
