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

package com.davils.kiro.ui.window.application

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.window.ApplicationScope
import com.davils.kore.core.annotation.DavilsInternal
import java.io.File
import java.net.URI

/**
 * Connects the application to the application menu of the operating system for as long as this call stays in the
 * composition: About, Settings and Quit in the menu that macOS shows with the name of the application, and the files
 * and links the system opens with it. A handler that is `null`, or that the platform does not support, keeps the
 * behavior of the system. Windows and Linux have no such menu; there [applicationMenu] adds the same items to the
 * menubar of a window.
 *
 * @param onQuitRequest called when the user or the system asks the application to quit; the application answers the
 * request, confirming it to quit or cancelling it to keep running, for example while work is unsaved.
 * @param onAbout called when the user chooses About, or `null` for the panel of the system.
 * @param onSettings called when the user chooses Settings, or `null` to leave the item out.
 * @param onOpenFiles called with the files the system opens with the application, or `null` for none.
 * @param onOpenUri called with a link the system opens with the application, or `null` for none.
 * @param onReopen called when the user activates the running application again, such as by a click on its icon in the
 * dock, or `null` for none.
 * @since 1.0.0
 */
@Composable
public fun ApplicationScope.ApplicationMenu(
    onQuitRequest: (QuitRequest) -> Unit,
    onAbout: (() -> Unit)? = null,
    onSettings: (() -> Unit)? = null,
    onOpenFiles: ((List<File>) -> Unit)? = null,
    onOpenUri: ((URI) -> Unit)? = null,
    onReopen: (() -> Unit)? = null,
) {
    val handlers = LocalDesktopHandlers.current
    val quit by rememberUpdatedState(onQuitRequest)
    val about by rememberUpdatedState(onAbout)
    val settings by rememberUpdatedState(onSettings)
    val openFiles by rememberUpdatedState(onOpenFiles)
    val openUri by rememberUpdatedState(onOpenUri)
    val reopen by rememberUpdatedState(onReopen)
    HandlerRegistration(
        handlers = handlers,
        isActive = true,
        install = { setQuitHandler { request -> quit(request) } },
        reset = { setQuitHandler(null) },
    )
    HandlerRegistration(
        handlers = handlers,
        isActive = onAbout != null,
        install = { setAboutHandler { about?.invoke() } },
        reset = { setAboutHandler(null) },
    )
    HandlerRegistration(
        handlers = handlers,
        isActive = onSettings != null,
        install = { setSettingsHandler { settings?.invoke() } },
        reset = { setSettingsHandler(null) },
    )
    HandlerRegistration(
        handlers = handlers,
        isActive = onOpenFiles != null,
        install = { setOpenFilesHandler { files -> openFiles?.invoke(files) } },
        reset = { setOpenFilesHandler(null) },
    )
    HandlerRegistration(
        handlers = handlers,
        isActive = onOpenUri != null,
        install = { setOpenUriHandler { uri -> openUri?.invoke(uri) } },
        reset = { setOpenUriHandler(null) },
    )
    HandlerRegistration(
        handlers = handlers,
        isActive = onReopen != null,
        install = { setReopenHandler { reopen?.invoke() } },
        reset = { setReopenHandler(null) },
    )
}
