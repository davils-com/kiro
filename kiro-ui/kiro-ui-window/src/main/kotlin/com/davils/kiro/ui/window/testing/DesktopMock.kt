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

import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kiro.ui.window.application.DesktopHandlers
import com.davils.kiro.ui.window.application.DockApi
import com.davils.kiro.ui.window.application.QuitRequest
import com.davils.kore.core.annotation.DavilsInternal
import java.awt.Image
import java.io.File
import java.net.URI

/**
 * A [DesktopHandlers] and [DockApi] for tests that talks to no operating system: it keeps the handlers an application
 * registers, lets a test act as the system and call them, and records what the application shows on its icon in the
 * dock.
 *
 * Provide it through [com.davils.kiro.ui.window.application.LocalDesktopHandlers] and
 * [com.davils.kiro.ui.window.application.LocalDockApi] in a test of a composable that connects to the desktop.
 *
 * @since 1.0.0
 */
@DavilsInternal
public class DesktopMock : DesktopHandlers, DockApi {
    private var about: (() -> Unit)? = null

    private var settings: (() -> Unit)? = null

    private var quit: ((QuitRequest) -> Unit)? = null

    private var openFiles: ((List<File>) -> Unit)? = null

    private var openUri: ((URI) -> Unit)? = null

    private var reopen: (() -> Unit)? = null

    private val attention = mutableListOf<Boolean>()

    private val answers = mutableListOf<Boolean>()

    /**
     * Whether a handler for About is registered.
     *
     * @since 1.0.0
     */
    public val hasAboutHandler: Boolean
        get() = about != null

    /**
     * Whether a handler for Settings is registered.
     *
     * @since 1.0.0
     */
    public val hasSettingsHandler: Boolean
        get() = settings != null

    /**
     * Whether a handler for quitting is registered.
     *
     * @since 1.0.0
     */
    public val hasQuitHandler: Boolean
        get() = quit != null

    /**
     * Whether a handler for opening files is registered.
     *
     * @since 1.0.0
     */
    public val hasOpenFilesHandler: Boolean
        get() = openFiles != null

    /**
     * Whether a handler for opening links is registered.
     *
     * @since 1.0.0
     */
    public val hasOpenUriHandler: Boolean
        get() = openUri != null

    /**
     * Whether a handler for activating the application again is registered.
     *
     * @since 1.0.0
     */
    public val hasReopenHandler: Boolean
        get() = reopen != null

    /**
     * The answers to the requests of [requestQuit], `true` for a confirmed and `false` for a cancelled one.
     *
     * @since 1.0.0
     */
    public val quitAnswers: List<Boolean>
        get() = answers.toList()

    /**
     * The badge on the icon of the application, or `null` for none.
     *
     * @since 1.0.0
     */
    public var badge: String? = null
        private set

    /**
     * The progress on the icon of the application from `0` to `100`, or `null` for none.
     *
     * @since 1.0.0
     */
    public var progress: Int? = null
        private set

    /**
     * The icon the application set, or `null` for the icon it started with.
     *
     * @since 1.0.0
     */
    public var icon: Image? = null
        private set

    /**
     * The entries of the menu of the icon of the application, or `null` for none.
     *
     * @since 1.0.0
     */
    public var menu: List<NativeMenuEntry>? = null
        private set

    /**
     * The requests for attention, `true` for a critical one, in the order they arrived.
     *
     * @since 1.0.0
     */
    public val attentionRequests: List<Boolean>
        get() = attention.toList()

    /**
     * Acts as the user choosing About; nothing happens without a handler.
     *
     * @since 1.0.0
     */
    public fun showAbout() {
        about?.invoke()
    }

    /**
     * Acts as the user choosing Settings; nothing happens without a handler.
     *
     * @since 1.0.0
     */
    public fun showSettings() {
        settings?.invoke()
    }

    /**
     * Acts as the system asking the application to quit and records the answer in [quitAnswers]; nothing happens
     * without a handler.
     *
     * @since 1.0.0
     */
    public fun requestQuit() {
        quit?.invoke(QuitRequest(onConfirm = { answers += true }, onCancel = { answers += false }))
    }

    /**
     * Acts as the system opening files with the application; nothing happens without a handler.
     *
     * @param files the files to open.
     * @since 1.0.0
     */
    public fun open(files: List<File>) {
        openFiles?.invoke(files)
    }

    /**
     * Acts as the system opening a link with the application; nothing happens without a handler.
     *
     * @param uri the link to open.
     * @since 1.0.0
     */
    public fun open(uri: URI) {
        openUri?.invoke(uri)
    }

    /**
     * Acts as the user activating the running application again; nothing happens without a handler.
     *
     * @since 1.0.0
     */
    public fun reopen() {
        reopen?.invoke()
    }

    override fun setAboutHandler(handler: (() -> Unit)?) {
        about = handler
    }

    override fun setSettingsHandler(handler: (() -> Unit)?) {
        settings = handler
    }

    override fun setQuitHandler(handler: ((QuitRequest) -> Unit)?) {
        quit = handler
    }

    override fun setOpenFilesHandler(handler: ((List<File>) -> Unit)?) {
        openFiles = handler
    }

    override fun setOpenUriHandler(handler: ((URI) -> Unit)?) {
        openUri = handler
    }

    override fun setReopenHandler(handler: (() -> Unit)?) {
        reopen = handler
    }

    override fun setBadge(badge: String?) {
        this.badge = badge
    }

    override fun setProgress(percent: Int?) {
        progress = percent
    }

    override fun setIcon(image: Image?) {
        icon = image
    }

    override fun setMenu(entries: List<NativeMenuEntry>?) {
        menu = entries
    }

    override fun requestAttention(isCritical: Boolean) {
        attention += isCritical
    }
}
