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

package com.davils.kiro.ui.audio.controls

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.Track
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.fading
import com.davils.kiro.ui.audio.palette.CoverPalette
import com.davils.kiro.ui.audio.palette.contentOn
import com.davils.kiro.ui.audio.palette.rememberCoverPalette
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.core.content.LocalContentColor

/**
 * A background made of the blurred, slowly breathing cover of a track under a shade of its colors, with a content
 * color that reads well on it.
 *
 * @param track the track whose cover shows, or `null` for the colors of the theme.
 * @param modifier the modifier of the backdrop.
 * @param isBreathing whether the cover slowly grows and shrinks; it rests when the motion of the theme is reduced.
 * @param style the style of the audio controls.
 * @param content the content on the backdrop.
 * @since 1.0.0
 */
@Composable
public fun CoverBackdrop(
    track: Track?,
    modifier: Modifier = Modifier,
    isBreathing: Boolean = true,
    style: AudioControlsStyle = audioControlsStyle(),
    content: @Composable BoxScope.() -> Unit,
) {
    val palette = rememberCoverPalette(track = track, style = style)
    val breath = breathOf(isBreathing = isBreathing && !style.motion.isReduced, style = style)
    val shade = Brush.verticalGradient(
        listOf(
            palette.dominant.copy(alpha = style.backdropTopAlpha),
            palette.muted.copy(alpha = style.backdropBottomAlpha),
        ),
    )
    val breathing = Modifier.graphicsLayer {
        scaleX = breath.value
        scaleY = breath.value
    }
    val saturated = ColorFilter.colorMatrix(saturationOf(style.backdropSaturation))
    val target = contentOf(palette = palette, hasCover = track?.cover != null, style = style)
    val foreground = animateColor(targetValue = target, animationSpec = style.motion.fading())
    Box(modifier = modifier.clipToBounds().background(palette.muted)) {
        Crossfade(
            targetState = track?.cover,
            modifier = Modifier.matchParentSize(),
            animationSpec = style.motion.fading(),
        ) { cover ->
            cover?.let { image ->
                Image(
                    bitmap = image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(breathing)
                        .blur(radius = style.backdropBlur, edgeTreatment = BlurredEdgeTreatment.Rectangle),
                    contentScale = ContentScale.Crop,
                    colorFilter = saturated,
                )
            }
        }
        Box(modifier = Modifier.matchParentSize().background(shade))
        Box(modifier = Modifier.matchParentSize().background(palette.muted.copy(alpha = style.backdropScrim)))
        CompositionLocalProvider(LocalContentColor provides foreground.value) { content() }
    }
}

internal fun contentOf(palette: CoverPalette, hasCover: Boolean, style: AudioControlsStyle): Color {
    if (!hasCover) return palette.onDominant
    val top = lerp(start = palette.dominant, stop = palette.muted, fraction = style.backdropScrim)
    val shaded = lerp(start = palette.dominant, stop = palette.muted, fraction = style.backdropBottomAlpha)
    val bottom = lerp(start = shaded, stop = palette.muted, fraction = style.backdropScrim)
    return contentOn(backgrounds = listOf(top, bottom), content = style.lightContent to style.darkContent)
}

private fun saturationOf(saturation: Float): ColorMatrix {
    val matrix = ColorMatrix()
    matrix.setToSaturation(saturation)
    return matrix
}

@Composable
private fun breathOf(isBreathing: Boolean, style: AudioControlsStyle): State<Float> {
    val rest = style.breathScale
    if (!isBreathing) return remember(rest) { mutableFloatStateOf(rest) }
    val transition = rememberInfiniteTransition()
    return transition.animateFloat(
        initialValue = rest,
        targetValue = 1f + (rest - 1f) * 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(style.breathDuration.inWholeMilliseconds.toInt()),
            repeatMode = RepeatMode.Reverse,
        ),
    )
}
