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

package com.davils.kiro.ui.window.splash.split

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import com.davils.kiro.ui.components.text.Text
import com.davils.kiro.ui.window.splash.Splash
import com.davils.kiro.ui.window.splash.SplashBackdrop
import com.davils.kiro.ui.window.splash.SplashFailure
import com.davils.kiro.ui.window.splash.SplashIndicator
import com.davils.kiro.ui.window.splash.SplashLogo
import com.davils.kiro.ui.window.splash.StepLabel
import com.davils.kiro.ui.window.splash.endDivider
import com.davils.kiro.ui.window.splash.stepText

/**
 * Shows the start of an application on two halves: a panel with the logo, the name, the edition, the subtitle, the
 * current step, the version and the copyright, and a generative scene with the vendor standing upright in its top
 * corner, where the scene is calm.
 *
 * The scene of the style plays on the second half and follows the progress of the start.
 *
 * Once the start failed, the panel shows an icon in place of the spinner, the message of the failure, a button
 * that closes and, with [onRetry], a button that starts again.
 *
 * @param splash what the splash shows.
 * @param modifier the modifier of the splash, which fills the space it gets.
 * @param onClose called by the close button of a failed start.
 * @param onRetry called by the retry button of a failed start, or `null` to show no retry button.
 * @param style how the splash looks.
 * @param logo the logo of the application, which receives the modifier that sizes it.
 * @param background the background of the second half, by default the scene of the [style], which receives the
 * modifier that fills it.
 * @param vendorLogo the logo below the vendor, which receives the modifier that sizes and turns it, or `null` for none.
 * @since 1.0.0
 */
@Composable
public fun SplitSplash(
    splash: Splash,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onRetry: (() -> Unit)? = null,
    style: SplitSplashStyle = splitSplashStyle(),
    logo: @Composable (Modifier) -> Unit = { slot -> SplashLogo(modifier = slot) },
    background: @Composable (Modifier) -> Unit = { slot ->
        SplashBackdrop(scene = style.scene, splash = splash, modifier = slot)
    },
    vendorLogo: (@Composable (Modifier) -> Unit)? = null,
) {
    Row(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight().panel(style)) {
            Showcase(splash = splash, logo = logo, onClose = onClose, onRetry = onRetry, style = style)
        }
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            background(Modifier.fillMaxSize())
            splash.vendor?.let { vendor -> Vendor(name = vendor, logo = vendorLogo, style = style) }
        }
    }
}

@Composable
private fun BoxScope.Showcase(
    splash: Splash,
    logo: @Composable (Modifier) -> Unit,
    onClose: () -> Unit,
    onRetry: (() -> Unit)?,
    style: SplitSplashStyle,
) {
    Column(
        modifier = Modifier.align(Alignment.TopStart).padding(style.padding),
        verticalArrangement = Arrangement.spacedBy(style.headerGap),
    ) {
        logo(Modifier.size(style.logoSize))
        Column(verticalArrangement = Arrangement.spacedBy(style.titleGap)) {
            Column {
                Text(text = splash.name, style = style.nameStyle, color = style.content)
                splash.edition?.let { edition ->
                    Text(text = edition, style = style.editionStyle, color = style.content)
                }
            }
            splash.subtitle?.let { subtitle -> Text(text = subtitle, style = style.subtitleStyle, color = style.muted) }
        }
    }
    Column(
        modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().padding(style.padding),
        verticalArrangement = Arrangement.spacedBy(style.gap),
    ) {
        Progress(splash = splash, onClose = onClose, onRetry = onRetry, style = style)
        splash.copyright?.let { copyright -> Text(text = copyright, style = style.footerStyle, color = style.muted) }
    }
}

@Composable
private fun Progress(splash: Splash, onClose: () -> Unit, onRetry: (() -> Unit)?, style: SplitSplashStyle) {
    val labels = style.labels
    val failure = style.failure
    Column(verticalArrangement = Arrangement.spacedBy(style.stepGap)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(style.indicatorGap),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SplashIndicator(isFailed = splash.isFailed, labels = labels, spinner = style.spinner, failure = failure)
            StepLabel(
                text = splash.stepText(labels),
                style = style.stepStyle,
                color = stepColorOf(splash = splash, style = style),
            )
        }
        splash.failure?.let { message ->
            SplashFailure(message = message, onClose = onClose, onRetry = onRetry, labels = labels, style = failure)
        }
        splash.version?.let { version ->
            Text(text = labels.version(version), style = style.versionStyle, color = style.muted)
        }
    }
}

@Composable
private fun BoxScope.Vendor(name: String, logo: (@Composable (Modifier) -> Unit)?, style: SplitSplashStyle) {
    Column(
        modifier = Modifier.align(Alignment.TopEnd).padding(style.padding).alpha(style.vendorAlpha),
        verticalArrangement = Arrangement.spacedBy(style.vendorGap),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = name,
            modifier = Modifier.turned(style.vendorRotation),
            style = style.vendorStyle,
            color = style.vendorColor,
            maxLines = 1,
        )
        logo?.invoke(
            Modifier
                .size(style.vendorLogoSize)
                .rotate(style.vendorRotation)
                .saturation(style.vendorSaturation),
        )
    }
}

private fun stepColorOf(splash: Splash, style: SplitSplashStyle): Color {
    if (splash.isFailed) return style.failure.color
    return style.content
}

private fun Modifier.panel(style: SplitSplashStyle): Modifier = background(style.panel).endDivider(style.divider)

private fun Modifier.turned(degrees: Float): Modifier = layout { measurable, constraints ->
    val placeable = measurable.measure(Constraints(maxWidth = constraints.maxHeight))
    val width = placeable.width
    val height = placeable.height
    layout(width = height, height = width) {
        placeable.placeWithLayer(x = (height - width) / 2, y = (width - height) / 2) { rotationZ = degrees }
    }
}

private fun Modifier.saturation(saturation: Float): Modifier = drawWithCache {
    val matrix = ColorMatrix()
    matrix.setToSaturation(saturation)
    val paint = Paint()
    paint.colorFilter = ColorFilter.colorMatrix(matrix)
    val bounds = Rect(Offset.Zero, size)
    onDrawWithContent {
        drawIntoCanvas { canvas ->
            canvas.saveLayer(bounds, paint)
            drawContent()
            canvas.restore()
        }
    }
}
