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

package com.davils.kiro.ui.window.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.core.ThemeProvider
import com.davils.kiro.ui.window.WindowOptions
import com.davils.kiro.ui.window.host.LocalWindowHost
import com.davils.kiro.ui.window.host.WindowSpec
import com.davils.kiro.ui.window.theme
import com.davils.kore.core.annotation.DavilsInternal

/**
 * Opens the window of a splash screen: without a frame, fixed in size, in the center of the primary screen, with
 * rounded corners and a border from its [style].
 *
 * Once the window drew its first frame, it closes the splash image that the Java runtime shows for the
 * `-splash:` option, so the image hands over to the window without a gap.
 *
 * @param onCloseRequest called when the user or the operating system asks to close the window.
 * @param title the title of the window, which the task bar shows.
 * @param icon the icon of the window, or `null` for the icon of the application.
 * @param style how the window looks.
 * @param size the size the window opens with.
 * @param content the splash screen, such as a [com.davils.kiro.ui.window.splash.split.SplitSplash].
 * @since 1.0.0
 */
@Composable
public fun SplashWindow(
    onCloseRequest: () -> Unit,
    title: String,
    icon: Painter? = null,
    style: SplashWindowStyle = splashWindowStyle(),
    size: DpSize = style.size,
    content: @Composable FrameWindowScope.() -> Unit,
) {
    val current = theme
    val native = LocalNativeSplash.current
    val state = rememberWindowState(position = WindowPosition(Alignment.Center), size = size)
    val spec = WindowSpec(
        onCloseRequest = onCloseRequest,
        state = state,
        title = title,
        options = WindowOptions(icon = icon),
        isUndecorated = true,
        isTransparent = style.cornerRadius > 0.dp,
        isResizable = false,
    )
    LocalWindowHost.current.Open(spec) { _ ->
        val scope = this
        LaunchedEffect(native) {
            withFrameNanos { _ -> }
            native.close()
        }
        ThemeProvider(theme = current) { SplashFrame(style = style) { scope.content() } }
    }
}

@Composable
private fun SplashFrame(style: SplashWindowStyle, content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(style.cornerRadius)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape)
            .surface(shape = shape, color = style.background, border = style.border),
    ) { content() }
}
