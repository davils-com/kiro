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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.Track
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.fading
import com.davils.kiro.ui.audio.palette.CoverPalette
import com.davils.kiro.ui.audio.palette.rememberCoverPalette
import com.davils.kiro.ui.audio.springing
import com.davils.kiro.ui.icons.Icon
import com.davils.kiro.ui.icons.IconStyle
import com.davils.kiro.ui.icons.LocalIconStyle

/**
 * The cover of a track, shrunk while it is paused and lifted by a shadow in its own color while it plays. Without a
 * cover it shows a gradient of the theme with a placeholder icon.
 *
 * @param track the track whose cover shows, or `null` for the placeholder.
 * @param isPlaying whether the track plays.
 * @param modifier the modifier of the cover; give it a size.
 * @param style the style of the audio controls.
 * @param shape the shape of the cover.
 * @param elevation the elevation of the cover while it plays; it halves while paused.
 * @since 1.0.0
 */
@Composable
public fun AudioCover(
    track: Track?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    style: AudioControlsStyle = audioControlsStyle(),
    shape: Shape = RoundedCornerShape(style.coverRadius),
    elevation: Dp = style.coverElevation,
) {
    val palette = rememberCoverPalette(track = track, style = style)
    val scale = animateFloatAsState(
        targetValue = 1f.takeIf { isPlaying } ?: style.pausedScale,
        animationSpec = style.motion.springing(),
    )
    val glow = palette.dominant.copy(alpha = style.glowAlpha)
    val scaling = Modifier.graphicsLayer {
        scaleX = scale.value
        scaleY = scale.value
    }
    val lift = elevation.takeIf { isPlaying } ?: elevation / 2
    val shadow = Modifier.shadow(elevation = lift, shape = shape, ambientColor = glow, spotColor = glow)
    Box(modifier = modifier.then(Modifier.then(scaling).then(shadow).clip(shape))) {
        Crossfade(targetState = track, animationSpec = style.motion.fading()) { shown ->
            CoverImage(track = shown, palette = palette, style = style)
        }
    }
}

@Composable
private fun CoverImage(track: Track?, palette: CoverPalette, style: AudioControlsStyle) {
    val cover = track?.cover
    if (cover != null) {
        Image(
            bitmap = cover,
            contentDescription = track.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        return
    }
    Placeholder(palette = palette, style = style)
}

@Composable
private fun Placeholder(palette: CoverPalette, style: AudioControlsStyle) {
    val brush = Brush.linearGradient(listOf(palette.vibrant, palette.muted))
    Box(modifier = Modifier.fillMaxSize().background(brush), contentAlignment = Alignment.Center) {
        CompositionLocalProvider(
            LocalIconStyle provides IconStyle(size = style.placeholderIconSize, color = palette.onDominant),
        ) {
            Icon(icon = style.icons.placeholder, contentDescription = null)
        }
    }
}
