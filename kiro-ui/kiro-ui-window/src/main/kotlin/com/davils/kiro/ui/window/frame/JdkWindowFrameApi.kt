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

package com.davils.kiro.ui.window.frame

import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.davils.kiro.ui.window.screen.ScreenInsets
import com.davils.kore.core.annotation.DavilsInternal
import com.davils.kore.core.annotation.DavilsPlatformBinding
import com.jetbrains.JBR
import com.jetbrains.WindowDecorations
import java.awt.Dimension
import java.awt.MouseInfo
import java.awt.Toolkit
import java.awt.event.MouseEvent

private const val FULL_WINDOW_CONTENT: String = "apple.awt.fullWindowContent"
private const val TRANSPARENT_TITLE_BAR: String = "apple.awt.transparentTitleBar"
private const val WINDOW_TITLE_VISIBLE: String = "apple.awt.windowTitleVisible"
private const val CONTROLS_DARK: String = "controls.dark"

@DavilsPlatformBinding
internal class JdkWindowFrameApi(private val window: ComposeWindow) : WindowFrameApi {
    private var titleBar: WindowDecorations.CustomTitleBar? = null

    override fun screenBounds(): DpRect {
        val bounds = window.graphicsConfiguration.bounds
        return DpRect(
            left = bounds.x.dp,
            top = bounds.y.dp,
            right = (bounds.x + bounds.width).dp,
            bottom = (bounds.y + bounds.height).dp,
        )
    }

    override fun screenInsets(): ScreenInsets {
        val insets = Toolkit.getDefaultToolkit().getScreenInsets(window.graphicsConfiguration)
        return ScreenInsets(
            left = insets.left.dp,
            top = insets.top.dp,
            right = insets.right.dp,
            bottom = insets.bottom.dp,
        )
    }

    override fun pointer(): DpOffset? {
        val location = MouseInfo.getPointerInfo()?.location ?: return null
        return DpOffset(x = location.x.dp, y = location.y.dp)
    }

    override fun limit(minimum: DpSize) {
        window.minimumSize = Dimension(minimum.width.value.toInt(), minimum.height.value.toInt())
    }

    override fun extendIntoTitleBar() {
        val pane = window.rootPane
        pane.putClientProperty(FULL_WINDOW_CONTENT, true)
        pane.putClientProperty(TRANSPARENT_TITLE_BAR, true)
        pane.putClientProperty(WINDOW_TITLE_VISIBLE, false)
    }

    override fun decorate(height: Dp, isDark: Boolean): CaptionInsets {
        val decorations = JBR.getWindowDecorations()
        val bar = titleBar ?: decorations.createCustomTitleBar()
        bar.height = height.value
        bar.putProperty(CONTROLS_DARK, isDark)
        decorations.setCustomTitleBar(window, bar)
        titleBar = bar
        return CaptionInsets(left = bar.leftInset.dp, right = bar.rightInset.dp)
    }

    override fun hitTest(isClient: Boolean) {
        titleBar?.forceHitTest(isClient)
    }

    override fun isSystemMoveSupported(): Boolean = JBR.isWindowMoveSupported()

    override fun startSystemMove() {
        JBR.getWindowMove().startMovingTogetherWithMouse(window, MouseEvent.BUTTON1)
    }
}
