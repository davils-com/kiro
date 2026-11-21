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

import com.davils.kore.core.annotation.DavilsInternal
import java.io.File
import java.net.URI

/**
 * Connects an application to the operating system: the handlers of the application menu of macOS and of the files and
 * links the system opens with it. The default implementation uses `java.awt.Desktop` and ignores what the platform
 * does not support; a test provides another one through [LocalDesktopHandlers].
 *
 * Every setter replaces what it set before; `null` removes it and restores the behavior of the system.
 *
 * @since 1.0.0
 */
@DavilsInternal
public interface DesktopHandlers {
    /**
     * Sets what happens when the user chooses About in the application menu.
     *
     * @param handler called when the user asks for information about the application, or `null` for the default.
     * @since 1.0.0
     */
    public fun setAboutHandler(handler: (() -> Unit)?)

    /**
     * Sets what happens when the user chooses Settings in the application menu.
     *
     * @param handler called when the user asks for the settings, or `null` for none.
     * @since 1.0.0
     */
    public fun setSettingsHandler(handler: (() -> Unit)?)

    /**
     * Sets what happens when the user or the system asks the application to quit.
     *
     * @param handler called with the request to answer, or `null` to quit at once.
     * @since 1.0.0
     */
    public fun setQuitHandler(handler: ((QuitRequest) -> Unit)?)

    /**
     * Sets what happens when the system opens files with the application.
     *
     * @param handler called with the files to open, or `null` for none.
     * @since 1.0.0
     */
    public fun setOpenFilesHandler(handler: ((List<File>) -> Unit)?)

    /**
     * Sets what happens when the system opens a link with the application.
     *
     * @param handler called with the link to open, or `null` for none.
     * @since 1.0.0
     */
    public fun setOpenUriHandler(handler: ((URI) -> Unit)?)

    /**
     * Sets what happens when the user activates the application again, such as by a click on its icon in the dock.
     *
     * @param handler called when the application is activated again, or `null` for none.
     * @since 1.0.0
     */
    public fun setReopenHandler(handler: (() -> Unit)?)
}
