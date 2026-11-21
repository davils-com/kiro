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

package com.davils.kiro.ui.audio.ambient

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.davils.kiro.ui.audio.signal.AudioSpectrum
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private const val DEGREES: Float = 180f
private const val TURN: Float = 360f

/**
 * A radial spectrum as the visualizers on many desktops show it around a cover or a logo: a bar per band that
 * radiates from a ring and jumps with the music, colored along a gradient around the circle, with a dot on the held
 * peak of each band. The ring and the content in its middle pulse with the lowest band, the beat of the music.
 *
 * @param spectrum the levels and held peaks of the bands to show.
 * @param modifier the modifier of the spectrum.
 * @param style the style of the spectrum.
 * @param center the content in the middle of the ring, clipped to a circle, such as a cover; nothing by default.
 * @since 1.0.0
 */
@Composable
public fun RadialSpectrum(
    spectrum: AudioSpectrum,
    modifier: Modifier = Modifier,
    style: RadialSpectrumStyle = radialSpectrumStyle(),
    center: @Composable BoxScope.() -> Unit = {},
) {
    val beat = spectrum.magnitudes.takeUnless { it.isEmpty() }?.let { bands ->
        style.range.fractionOf(
            level = bands[0],
        )
    } ?: 0f
    val growth = 1f + style.pulse * beat
    Box(
        modifier = modifier.then(
            Modifier
                .size(style.size)
                .semantics { contentDescription = style.description },
        ),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val half = min(a = size.width, b = size.height) / 2f
            val ring = half * style.inner * growth
            drawCircle(
                color = style.ringColor,
                radius = ring,
                center = this.center,
                style = Stroke(width = style.ringWidth.toPx()),
            )
            drawRays(
                spectrum = spectrum,
                ring = ring + style.ringWidth.toPx(),
                reach = half * style.reach,
                style = style,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize(fraction = style.inner.coerceIn(minimumValue = 0f, maximumValue = 1f))
                .aspectRatio(ratio = 1f)
                .graphicsLayer {
                    scaleX = growth
                    scaleY = growth
                    shape = CircleShape
                    clip = true
                },
            contentAlignment = Alignment.Center,
            content = center,
        )
    }
}

private fun DrawScope.drawRays(spectrum: AudioSpectrum, ring: Float, reach: Float, style: RadialSpectrumStyle) {
    val bands = spectrum.magnitudes.size
    if (bands == 0) return
    val isMirrored = style.symmetry == RadialSymmetry.Mirrored
    val rays = bands * (2.takeIf { isMirrored } ?: 1)
    val sweep = TURN / rays
    val slot = 2f * PI.toFloat() * ring / rays
    val width = slot * style.barWidth.coerceIn(minimumValue = 0f, maximumValue = 1f)
    val colors = style.colors + style.colors.first()
    val brush = Brush.sweepGradient(colors = colors, center = center)
    val rest = style.rest.coerceIn(minimumValue = 0f, maximumValue = 1f)
    for (ray in 0 until rays) {
        val band = bandOf(ray = ray, bands = bands, isMirrored = isMirrored)
        val level = rest + (1f - rest) * style.range.fractionOf(level = spectrum.magnitudes[band])
        val angle = (style.startAngle + sweep * ray + sweep / 2f) / DEGREES * PI.toFloat()
        val direction = Offset(x = cos(angle), y = sin(angle))
        val start = center + direction * ring
        drawLine(
            brush = brush,
            start = start,
            end = start + direction * (reach * level),
            strokeWidth = width,
            cap = StrokeCap.Round,
        )
        val hold = spectrum.holds?.get(band) ?: continue
        val peak = rest + (1f - rest) * style.range.fractionOf(level = hold)
        drawCircle(
            color = style.capColor,
            radius = style.capSize.toPx() / 2f,
            center = start + direction * (reach * peak + width),
        )
    }
}

private fun bandOf(ray: Int, bands: Int, isMirrored: Boolean): Int {
    if (!isMirrored || ray < bands) return ray
    return bands * 2 - 1 - ray
}
