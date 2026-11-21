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

package com.davils.kiro.ui.window.splash.hero

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.FloatState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import com.davils.kiro.ui.components.progress.Progress
import com.davils.kiro.ui.components.progress.ProgressStyle
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashBackdrop
import com.davils.kiro.ui.window.splash.SplashFailure
import com.davils.kiro.ui.window.splash.SplashLogo
import com.davils.kiro.ui.window.splash.StepLabel
import com.davils.kiro.ui.window.splash.stepText
import kotlin.math.max
import kotlin.math.min

/**
 * Shows the start of an application over a generative scene that fills the whole window and fades into the background
 * towards the bottom, where the logo, the name, the subtitle, the current step and a progress bar sit.
 *
 * The progress bar follows [Splash.fraction] and runs empty while it is `null`. Once the start failed, the message of
 * the failure, a button that closes and, with [onRetry], a button that starts again show below the progress bar.
 *
 * @param splash what the splash shows.
 * @param modifier the modifier of the splash, which fills the space it gets.
 * @param onClose called by the close button of a failed start.
 * @param onRetry called by the retry button of a failed start, or `null` to show no retry button.
 * @param style how the splash looks.
 * @param background the background behind the fade, by default the scene of the [style], which receives the modifier
 * that fills it.
 * @param logo the logo of the application, which receives the modifier that sizes it.
 * @since 1.0.0
 */
@Composable
public fun HeroSplash(
    splash: Splash,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onRetry: (() -> Unit)? = null,
    style: HeroSplashStyle = heroSplashStyle(),
    background: @Composable (Modifier) -> Unit = { slot ->
        SplashBackdrop(scene = style.scene, splash = splash, modifier = slot)
    },
    logo: @Composable (Modifier) -> Unit = { slot -> SplashLogo(modifier = slot) },
) {
    val top = remember { mutableFloatStateOf(Float.MAX_VALUE) }
    Box(modifier = modifier.then(Modifier.fillMaxSize().clipToBounds())) {
        background(Modifier.fillMaxSize())
        Box(modifier = Modifier.fillMaxSize().scrim(style = style, top = top))
        Content(
            splash = splash,
            onClose = onClose,
            onRetry = onRetry,
            style = style,
            logo = logo,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(style.padding)
                .onPlaced { coordinates -> top.floatValue = coordinates.positionInParent().y },
        )
    }
}

@Composable
private fun Content(
    splash: Splash,
    onClose: () -> Unit,
    onRetry: (() -> Unit)?,
    style: HeroSplashStyle,
    logo: @Composable (Modifier) -> Unit,
    modifier: Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(style.gap)) {
        logo(Modifier.size(style.logoSize))
        Column(verticalArrangement = Arrangement.spacedBy(style.titleGap)) {
            Text(text = splash.name, style = style.nameStyle, color = style.content)
            splash.subtitle?.let { subtitle -> Text(text = subtitle, style = style.subtitleStyle, color = style.muted) }
        }
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(style.titleGap)) {
            StepLabel(
                text = splash.stepText(style.labels),
                style = style.stepStyle,
                color = stepColorOf(splash = splash, style = style),
            )
            Progress(value = splash.fraction, modifier = Modifier.fillMaxWidth(), style = progressOf(splash, style))
        }
        splash.failure?.let { message ->
            SplashFailure(
                message = message,
                onClose = onClose,
                onRetry = onRetry,
                labels = style.labels,
                style = style.failure,
            )
        }
        Footer(splash = splash, style = style)
    }
}

@Composable
private fun Footer(splash: Splash, style: HeroSplashStyle) {
    Row(horizontalArrangement = Arrangement.spacedBy(style.gap)) {
        splash.version?.let { version ->
            Text(text = style.labels.version(version), style = style.footerStyle, color = style.muted)
        }
        splash.copyright?.let { copyright -> Text(text = copyright, style = style.footerStyle, color = style.muted) }
    }
}

private fun stepColorOf(splash: Splash, style: HeroSplashStyle): Color {
    if (splash.isFailed) return style.failure.color
    return style.content
}

private fun progressOf(splash: Splash, style: HeroSplashStyle): ProgressStyle {
    if (splash.isFailed) return style.progress.copy(indicatorColor = style.failure.color)
    return style.progress
}

private fun Modifier.scrim(style: HeroSplashStyle, top: FloatState): Modifier = drawBehind {
    val end = min(a = size.height * style.scrimEnd, b = top.floatValue)
    val start = min(a = size.height * style.scrimStart, b = end)
    val brush = Brush.verticalGradient(
        0f to style.scrim.copy(alpha = 0f),
        1f to style.scrim,
        startY = start,
        endY = max(a = end, b = start + 1f),
    )
    drawRect(brush = brush)
}
