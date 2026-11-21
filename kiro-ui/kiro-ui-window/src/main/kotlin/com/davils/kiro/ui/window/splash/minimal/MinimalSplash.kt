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

package com.davils.kiro.ui.window.splash.minimal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import com.davils.kiro.ui.components.primitive.surface.boxShadow
import com.davils.kiro.ui.components.primitive.surface.surface
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashBackdrop
import com.davils.kiro.ui.window.splash.SplashFailure
import com.davils.kiro.ui.window.splash.SplashIndicator
import com.davils.kiro.ui.window.splash.SplashLogo

/**
 * Shows the start of an application with as little as possible: a generative scene fills the window, and a card
 * floats in its center with the logo, the name if the [style] wants it and a spinner.
 *
 * Once the start failed, an icon replaces the spinner and the message of the failure, a button that closes and, with
 * [onRetry], a button that starts again show below it in the card.
 *
 * @param splash what the splash shows.
 * @param modifier the modifier of the splash, which fills the space it gets.
 * @param onClose called by the close button of a failed start.
 * @param onRetry called by the retry button of a failed start, or `null` to show no retry button.
 * @param style how the splash looks.
 * @param logo the logo of the application, which receives the modifier that sizes it.
 * @param backdrop what fills the splash behind the card, by default the scene of the [style], which receives the
 * modifier that fills it.
 * @since 1.0.0
 */
@Composable
public fun MinimalSplash(
    splash: Splash,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onRetry: (() -> Unit)? = null,
    style: MinimalSplashStyle = minimalSplashStyle(),
    logo: @Composable (Modifier) -> Unit = { slot -> SplashLogo(modifier = slot) },
    backdrop: @Composable (Modifier) -> Unit = { slot ->
        SplashBackdrop(scene = style.scene, splash = splash, modifier = slot)
    },
) {
    Box(modifier = modifier.then(Modifier.fillMaxSize().clipToBounds()), contentAlignment = Alignment.Center) {
        backdrop(Modifier.fillMaxSize())
        Column(
            modifier = Modifier.padding(style.padding).card(style),
            verticalArrangement = Arrangement.spacedBy(space = style.gap, alignment = Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            logo(Modifier.size(style.logoSize))
            if (style.isNameShown) Text(text = splash.name, style = style.nameStyle, color = style.content)
            SplashIndicator(
                isFailed = splash.isFailed,
                labels = style.labels,
                spinner = style.spinner,
                failure = style.failure,
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
}

private fun Modifier.card(style: MinimalSplashStyle): Modifier {
    val shape = RoundedCornerShape(style.cornerRadius)
    return then(
        Modifier
            .boxShadow(shape = shape, shadow = style.shadow)
            .surface(shape = shape, color = style.card, border = style.border)
            .padding(style.cardPadding),
    )
}
