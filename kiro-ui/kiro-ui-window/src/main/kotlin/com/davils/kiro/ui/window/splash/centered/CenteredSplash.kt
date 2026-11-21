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

package com.davils.kiro.ui.window.splash.centered

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.Layout
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashBackdrop
import com.davils.kiro.ui.window.splash.SplashFailure
import com.davils.kiro.ui.window.splash.SplashLogo
import com.davils.kiro.ui.window.splash.StepLabel
import com.davils.kiro.ui.window.splash.stepText

/**
 * Shows the start of an application around its logo: a generative scene fills the window, and in its center the
 * logo sits inside a ring that shows the progress, with the name, the subtitle and the current step below and the
 * version and the copyright at the bottom.
 *
 * The ring stays in the center of the splash, where the scene of the [style] has its center, and only rises as far
 * as the failure needs room; the backdrop rises with it. Its arc follows [Splash.fraction] and turns while the
 * progress is unknown; while the theme asks for reduced motion, it jumps to every new progress and stands still.
 * Once the start failed, the arc takes the color of the failure, and the message of the failure, a button that
 * closes and, with [onRetry], a button that starts again show below the current step.
 *
 * @param splash what the splash shows.
 * @param modifier the modifier of the splash, which fills the space it gets.
 * @param onClose called by the close button of a failed start.
 * @param onRetry called by the retry button of a failed start, or `null` to show no retry button.
 * @param style how the splash looks.
 * @param logo the logo of the application, which receives the modifier that sizes it.
 * @param backdrop what fills the splash behind the content, by default the scene of the [style], which receives the
 * modifier that fills it.
 * @since 1.0.0
 */
@Composable
public fun CenteredSplash(
    splash: Splash,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onRetry: (() -> Unit)? = null,
    style: CenteredSplashStyle = centeredSplashStyle(),
    logo: @Composable (Modifier) -> Unit = { slot -> SplashLogo(modifier = slot) },
    backdrop: @Composable (Modifier) -> Unit = { slot ->
        SplashBackdrop(scene = style.scene, splash = splash, modifier = slot)
    },
) {
    val padding = style.padding
    val gap = style.gap
    val policy = remember(key1 = padding, key2 = gap) { CenteredLayout(padding = padding, gap = gap) }
    Layout(
        contents = listOf(
            { backdrop(Modifier.fillMaxSize()) },
            { Ring(splash = splash, logo = logo, style = style) },
            { Details(splash = splash, onClose = onClose, onRetry = onRetry, style = style) },
            { Footer(splash = splash, style = style) },
        ),
        modifier = modifier.then(Modifier.fillMaxSize().clipToBounds()),
        measurePolicy = policy,
    )
}

@Composable
private fun Ring(splash: Splash, logo: @Composable (Modifier) -> Unit, style: CenteredSplashStyle) {
    val diameter = style.logoSize + (style.ringGap + style.ringWidth) * 2
    Box(modifier = Modifier.size(diameter), contentAlignment = Alignment.Center) {
        ProgressRing(
            fraction = splash.fraction,
            isFailed = splash.isFailed,
            style = style,
            modifier = Modifier.matchParentSize(),
        )
        logo(Modifier.size(style.logoSize))
    }
}

@Composable
private fun Details(splash: Splash, onClose: () -> Unit, onRetry: (() -> Unit)?, style: CenteredSplashStyle) {
    Column(
        modifier = Modifier.veiled(style),
        verticalArrangement = Arrangement.spacedBy(style.gap),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(style.titleGap),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = splash.name, style = style.nameStyle, color = style.content)
            splash.subtitle?.let { subtitle -> Text(text = subtitle, style = style.subtitleStyle, color = style.muted) }
        }
        StepLabel(
            text = splash.stepText(style.labels),
            style = style.stepStyle,
            color = stepColorOf(splash = splash, style = style),
            alignment = Alignment.Center,
        )
        splash.failure?.let { message ->
            SplashFailure(
                message = message,
                onClose = onClose,
                onRetry = onRetry,
                labels = style.labels,
                style = style.failure,
                alignment = Alignment.CenterHorizontally,
            )
        }
    }
}

@Composable
private fun Footer(splash: Splash, style: CenteredSplashStyle) {
    Row(modifier = Modifier.veiled(style), horizontalArrangement = Arrangement.spacedBy(style.footerGap)) {
        splash.version?.let { version ->
            Text(text = style.labels.version(version), style = style.footerStyle, color = style.muted)
        }
        splash.copyright?.let { copyright -> Text(text = copyright, style = style.footerStyle, color = style.muted) }
    }
}

private fun stepColorOf(splash: Splash, style: CenteredSplashStyle): Color {
    if (splash.isFailed) return style.failure.color
    return style.muted
}

private fun Modifier.veiled(style: CenteredSplashStyle): Modifier = drawBehind {
    val spread = style.veilSpread.toPx()
    val height = size.height + spread * 2
    val radius = height / 2
    val brush = Brush.radialGradient(
        0f to style.veil,
        style.veilHold to style.veil,
        1f to style.veil.copy(alpha = 0f),
        center = center,
        radius = radius,
    )
    scale(scaleX = (size.width + spread * 2) / height, scaleY = 1f, pivot = center) {
        drawCircle(brush = brush, radius = radius, center = center)
    }
}
