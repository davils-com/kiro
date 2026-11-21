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

package com.davils.kiro.ui.window.application

import com.davils.kiro.ui.components.menubar.DesktopPlatform

private const val APPLICATION_NAME: String = "apple.awt.application.name"

private const val SCREEN_MENU_BAR: String = "apple.laf.useScreenMenuBar"

private const val APPEARANCE: String = "apple.awt.application.appearance"

private const val TEMPLATE_IMAGES: String = "apple.awt.enableTemplateImages"

private const val ENABLED: String = "true"

private const val SYSTEM_APPEARANCE: String = "system"

/**
 * Prepares the Java runtime for a desktop application that looks and behaves like a native one.
 *
 * @since 1.0.0
 */
public object DesktopEnvironment {
    /**
     * Prepares the Java runtime for a desktop application. On macOS it names the application in its menu and in the
     * dock, moves the menus of windows to the menu bar of the system, lets windows follow the light or dark appearance
     * of the system and lets [com.davils.kiro.ui.window.tray.TrayIcon.template] images be tinted by the system. On
     * every other platform it changes nothing.
     *
     * The runtime reads these settings once, when AWT starts, so this must be the first call in `main`, before any
     * window, tray, dock or other AWT class is touched, including `application { }` of Compose.
     *
     * @param name the name of the application, as macOS shows it in its menu and in the dock.
     * @param platform the platform the application runs on.
     * @throws IllegalArgumentException if [name] is blank.
     * @since 1.0.0
     */
    public fun prepare(name: String, platform: DesktopPlatform = DesktopPlatform.current()) {
        prepare(name = name, platform = platform, write = ::writeProperty)
    }

    internal fun prepare(name: String, platform: DesktopPlatform, write: (key: String, value: String) -> Unit) {
        require(name.isNotBlank()) { "The name of the application must not be blank." }
        if (platform != DesktopPlatform.MacOs) return
        write(APPLICATION_NAME, name)
        write(SCREEN_MENU_BAR, ENABLED)
        write(APPEARANCE, SYSTEM_APPEARANCE)
        write(TEMPLATE_IMAGES, ENABLED)
    }
}

private fun writeProperty(key: String, value: String) {
    System.setProperty(key, value)
}
