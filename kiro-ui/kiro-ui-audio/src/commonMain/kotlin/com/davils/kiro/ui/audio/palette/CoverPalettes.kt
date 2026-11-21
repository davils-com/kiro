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

package com.davils.kiro.ui.audio.palette

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.lerp
import com.davils.kiro.ui.audio.AudioControlsStyle
import com.davils.kiro.ui.audio.Track
import com.davils.kiro.ui.audio.audioControlsStyle
import com.davils.kiro.ui.audio.fading
import com.davils.kiro.ui.audio.theme
import com.davils.kiro.ui.components.primitive.interaction.animateColor
import com.davils.kiro.ui.core.color.Colors

private const val HALF: Float = 0.5f

/**
 * Remembers the [CoverPalette] of the cover of a track and animates it when the track changes.
 *
 * Without a cover the palette follows the colors of the theme.
 *
 * @param track the track whose cover tints the palette, or `null` for none.
 * @param style the style whose content colors and thresholds the palette follows.
 * @return the animated palette.
 * @since 1.0.0
 */
@Composable
public fun rememberCoverPalette(track: Track?, style: AudioControlsStyle = audioControlsStyle()): CoverPalette {
    val colors = theme.colors
    val cover = track?.cover
    val target = remember(cover, colors, style) {
        cover?.let { image -> coverPaletteOf(cover = image, style = style) } ?: fallbackPaletteOf(colors)
    }
    val spec = style.motion.fading<Color>()
    return CoverPalette(
        dominant = animateColor(targetValue = target.dominant, animationSpec = spec).value,
        vibrant = animateColor(targetValue = target.vibrant, animationSpec = spec).value,
        muted = animateColor(targetValue = target.muted, animationSpec = spec).value,
        onDominant = animateColor(targetValue = target.onDominant, animationSpec = spec).value,
    )
}

internal fun coverPaletteOf(cover: ImageBitmap, style: AudioControlsStyle): CoverPalette {
    val swatches = swatchesOf(cover)
    val content = style.lightContent to style.darkContent
    val fallback = lerp(start = style.lightContent, stop = style.darkContent, fraction = HALF)
    val dominant = swatches.maxByOrNull { swatch -> swatch.count }?.color ?: fallback
    val vivid = swatches.filter { swatch -> swatch.saturation > style.vibrantSaturation }
    val vibrant = vivid.maxByOrNull { swatch -> swatch.count * swatch.saturation }?.color ?: dominant
    return CoverPalette(
        dominant = dominant,
        vibrant = vibrant,
        muted = lerp(start = dominant, stop = style.darkContent, fraction = style.mutedDepth),
        onDominant = contentOn(backgrounds = listOf(dominant), content = content),
    )
}

internal fun fallbackPaletteOf(colors: Colors): CoverPalette = CoverPalette(
    dominant = colors.muted,
    vibrant = colors.primary,
    muted = colors.card,
    onDominant = colors.foreground,
)
