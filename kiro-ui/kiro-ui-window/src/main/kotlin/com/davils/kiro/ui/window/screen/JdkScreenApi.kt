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

package com.davils.kiro.ui.window.screen

import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import java.awt.GraphicsDevice
import java.awt.GraphicsEnvironment
import java.awt.Rectangle
import java.awt.Toolkit

@DavilsPlatformBinding
internal object JdkScreenApi : ScreenApi {
    override fun screens(): List<Screen> {
        if (GraphicsEnvironment.isHeadless()) return emptyList()
        val environment = GraphicsEnvironment.getLocalGraphicsEnvironment()
        val primary = environment.defaultScreenDevice
        return environment.screenDevices.map { device -> screenOf(device = device, isPrimary = device == primary) }
    }

    private fun screenOf(device: GraphicsDevice, isPrimary: Boolean): Screen {
        val configuration = device.defaultConfiguration
        val bounds = rectOf(configuration.bounds)
        val insets = Toolkit.getDefaultToolkit().getScreenInsets(configuration)
        val reserved = ScreenInsets(
            left = insets.left.dp,
            top = insets.top.dp,
            right = insets.right.dp,
            bottom = insets.bottom.dp,
        )
        return Screen(
            id = ScreenId(device.iDstring),
            bounds = bounds,
            workArea = workAreaOf(screen = bounds, insets = reserved),
            isPrimary = isPrimary,
        )
    }

    private fun rectOf(area: Rectangle): DpRect = DpRect(
        left = area.x.dp,
        top = area.y.dp,
        right = (area.x + area.width).dp,
        bottom = (area.y + area.height).dp,
    )
}
