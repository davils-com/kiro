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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.window.ApplicationScope
import com.davils.kiro.ui.components.menu.MenuScope
import com.davils.kiro.ui.components.menubar.native.nativeEntriesOf
import com.davils.kore.core.annotation.DavilsInternal
import kotlin.math.roundToInt

private const val PERCENT: Int = 100

/**
 * Shows a badge, a progress, an icon and a menu on the icon of the application in the dock of macOS or the taskbar
 * of Windows for as long as this call stays in the composition, and removes them when it leaves. What the platform
 * does not support is left out.
 *
 * @param badge the text of the badge, such as a count of unread messages, or `null` for none.
 * @param progress the progress from `0` to `1`, or `null` for none.
 * @param icon the icon of the application while this call stays in the composition, or `null` to keep the icon it
 * started with; the painter needs an intrinsic size.
 * @param menu declares the entries of the menu of the icon, or `null` for none.
 * @param state the state through which the application asks the user for attention.
 * @since 1.0.0
 */
@Composable
public fun ApplicationScope.Dock(
    badge: String? = null,
    progress: Float? = null,
    icon: Painter? = null,
    menu: (MenuScope.() -> Unit)? = null,
    state: DockState = rememberDockState(),
) {
    val dock = LocalDockApi.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val image = remember(icon, density, layoutDirection) {
        icon?.toAwtImage(density = density, layoutDirection = layoutDirection)
    }
    val entries = menu?.let { content -> nativeEntriesOf(content) }
    DockValue(dock = dock, value = badge, write = DockApi::setBadge)
    DockValue(dock = dock, value = progress?.let(::percentOf), write = DockApi::setProgress)
    DockValue(dock = dock, value = image, write = DockApi::setIcon)
    DockValue(dock = dock, value = entries, write = DockApi::setMenu)
    DisposableEffect(state, dock) {
        state.attach(dock)
        onDispose { state.detach(dock) }
    }
}

internal fun percentOf(progress: Float): Int {
    val fraction = progress.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return (fraction * PERCENT).roundToInt()
}
