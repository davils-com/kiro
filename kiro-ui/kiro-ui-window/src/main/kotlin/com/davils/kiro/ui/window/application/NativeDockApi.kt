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

import com.davils.kiro.ui.components.menubar.native.NativeMenuEntry
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import java.awt.Image
import java.awt.Taskbar

private const val NO_PROGRESS: Int = -1

@DavilsPlatformBinding
internal object NativeDockApi : DockApi {
    private var originalIcon: Image? = null

    override fun setBadge(badge: String?) {
        taskbarFor(Taskbar.Feature.ICON_BADGE_TEXT)?.setIconBadge(badge)
    }

    override fun setProgress(percent: Int?) {
        taskbarFor(Taskbar.Feature.PROGRESS_VALUE)?.setProgressValue(percent ?: NO_PROGRESS)
    }

    override fun setIcon(image: Image?) {
        val taskbar = taskbarFor(Taskbar.Feature.ICON_IMAGE) ?: return
        if (image == null) {
            originalIcon?.let(taskbar::setIconImage)
            return
        }
        originalIcon = originalIcon ?: taskbar.iconImage
        taskbar.iconImage = image
    }

    override fun setMenu(entries: List<NativeMenuEntry>?) {
        taskbarFor(Taskbar.Feature.MENU)?.menu = entries?.let(AwtMenus::popupOf)
    }

    override fun requestAttention(isCritical: Boolean) {
        taskbarFor(Taskbar.Feature.USER_ATTENTION)?.requestUserAttention(true, isCritical)
    }

    private fun taskbarFor(feature: Taskbar.Feature): Taskbar? {
        if (!Taskbar.isTaskbarSupported()) return null
        return Taskbar.getTaskbar().takeIf { taskbar -> taskbar.isSupported(feature) }
    }
}
