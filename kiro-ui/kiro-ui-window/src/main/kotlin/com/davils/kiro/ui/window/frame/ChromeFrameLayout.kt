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

package com.davils.kiro.ui.window.frame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.window.FrameWindowScope
import com.davils.kiro.ui.core.Theme
import com.davils.kiro.ui.window.ChromeWindowStyle
import com.davils.kiro.ui.window.titlebar.TitleBarLayout
import com.davils.kiro.ui.window.titlebar.TitleBarStyle
import com.davils.kiro.ui.window.titlebar.WindowTitleBarScope
import com.davils.kiro.ui.window.titlebar.WindowTitleBarScopeImpl

@Composable
internal fun FrameWindowScope.ChromeFrameLayout(
    frame: ChromeFrame,
    style: ChromeWindowStyle,
    titleBarStyle: TitleBarStyle,
    titleBar: WindowTitleBarScope.() -> Unit,
    content: @Composable FrameWindowScope.() -> Unit,
) {
    val isDark = Theme.current.isDark
    LaunchedEffect(
        frame,
        titleBarStyle.height,
        isDark,
    ) { frame.prepare(titleBarHeight = titleBarStyle.height, isDark = isDark) }
    val shape = shapeOf(frame = frame, style = style)
    val slots = WindowTitleBarScopeImpl(window = this).apply(titleBar).slots
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape)
            .background(color = style.background, shape = shape)
            .outline(frame = frame, style = style, shape = shape),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (!frame.isFullscreen) {
                TitleBarLayout(
                    slots = slots,
                    actions = frame,
                    style = titleBarStyle,
                    modifier = Modifier.titleBarGestures(frame),
                    chrome = frame.titleBarChrome,
                )
            }
            Box(modifier = Modifier.fillMaxWidth().weight(1f)) { content() }
        }
        if (frame.isResizable) ResizeHandles(frame = frame, thickness = style.resizeThickness)
    }
}

private fun Modifier.titleBarGestures(frame: ChromeFrame): Modifier {
    if (frame.strategy == FrameStrategy.Decorated) return captionHitTest(frame)
    return windowDrag(frame)
}

private fun shapeOf(frame: ChromeFrame, style: ChromeWindowStyle): Shape {
    if (!frame.isResizable) return RectangleShape
    return RoundedCornerShape(style.cornerRadius)
}

private fun Modifier.outline(frame: ChromeFrame, style: ChromeWindowStyle, shape: Shape): Modifier {
    if (!frame.isResizable) return this
    val border = style.border
    return border(width = border.width, color = border.color, shape = shape)
}
