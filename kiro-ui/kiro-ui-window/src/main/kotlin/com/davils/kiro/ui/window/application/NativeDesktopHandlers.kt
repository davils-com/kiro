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

import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import java.awt.Desktop
import java.awt.desktop.AboutHandler
import java.awt.desktop.AppReopenedListener
import java.awt.desktop.OpenFilesHandler
import java.awt.desktop.OpenURIHandler
import java.awt.desktop.PreferencesHandler
import java.awt.desktop.QuitHandler
import java.io.File
import java.net.URI

@DavilsPlatformBinding
internal object NativeDesktopHandlers : DesktopHandlers {
    private var reopenListener: AppReopenedListener? = null

    override fun setAboutHandler(handler: (() -> Unit)?) {
        val desktop = desktopFor(Desktop.Action.APP_ABOUT) ?: return
        desktop.setAboutHandler(handler?.let { action -> AboutHandler { action() } })
    }

    override fun setSettingsHandler(handler: (() -> Unit)?) {
        val desktop = desktopFor(Desktop.Action.APP_PREFERENCES) ?: return
        desktop.setPreferencesHandler(handler?.let { action -> PreferencesHandler { action() } })
    }

    override fun setQuitHandler(handler: ((QuitRequest) -> Unit)?) {
        val desktop = desktopFor(Desktop.Action.APP_QUIT_HANDLER) ?: return
        val quit = handler?.let { action ->
            QuitHandler { _, response ->
                action(QuitRequest(onConfirm = response::performQuit, onCancel = response::cancelQuit))
            }
        }
        desktop.setQuitHandler(quit)
    }

    override fun setOpenFilesHandler(handler: ((List<File>) -> Unit)?) {
        val desktop = desktopFor(Desktop.Action.APP_OPEN_FILE) ?: return
        desktop.setOpenFileHandler(handler?.let { action -> OpenFilesHandler { event -> action(event.files) } })
    }

    override fun setOpenUriHandler(handler: ((URI) -> Unit)?) {
        val desktop = desktopFor(Desktop.Action.APP_OPEN_URI) ?: return
        desktop.setOpenURIHandler(handler?.let { action -> OpenURIHandler { event -> action(event.uri) } })
    }

    override fun setReopenHandler(handler: (() -> Unit)?) {
        val desktop = desktopFor(Desktop.Action.APP_EVENT_REOPENED) ?: return
        reopenListener?.let(desktop::removeAppEventListener)
        val listener = handler?.let { action -> AppReopenedListener { action() } }
        listener?.let(desktop::addAppEventListener)
        reopenListener = listener
    }

    private fun desktopFor(action: Desktop.Action): Desktop? {
        if (!Desktop.isDesktopSupported()) return null
        return Desktop.getDesktop().takeIf { desktop -> desktop.isSupported(action) }
    }
}
