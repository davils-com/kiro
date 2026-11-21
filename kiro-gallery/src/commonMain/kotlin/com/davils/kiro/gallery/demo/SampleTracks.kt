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

package com.davils.kiro.gallery.demo

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import com.davils.kiro.ui.audio.Track
import com.davils.kiro.ui.audio.TrackId
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private const val SIDE: Float = 256f
private const val ORB: Float = 0.32f
private const val HALO: Float = 0.55f
private val VIOLET: Color = Color(0xFF7C3AED)
private val PINK: Color = Color(0xFFF472B6)
private val SKY: Color = Color(0xFF0EA5E9)
private val CYAN: Color = Color(0xFF22D3EE)
private val ORANGE: Color = Color(0xFFF97316)
private val YELLOW: Color = Color(0xFFFACC15)
private val GREEN: Color = Color(0xFF16A34A)
private val TEAL: Color = Color(0xFF0F766E)

internal fun sampleTracks(): List<Track> = listOf(
    trackOf(title = "Spanning Tree", artist = "The Root Bridges", length = "3:34", colors = VIOLET to PINK),
    trackOf(title = "Packet Loss", artist = "Jitter", length = "3:07", colors = SKY to CYAN),
    trackOf(title = "Default Gateway", artist = "Subnet Mask", length = "4:03", colors = ORANGE to YELLOW),
    trackOf(title = "Late Night Uplink", artist = "Fiber & Copper", length = "4:28", colors = GREEN to TEAL),
)

private fun trackOf(title: String, artist: String, length: String, colors: Pair<Color, Color>): Track = Track(
    id = TrackId(title),
    title = title,
    artist = artist,
    duration = durationOf(length),
    album = "Layer 2 Sessions",
    cover = coverOf(colors),
)

private fun durationOf(length: String): Duration {
    val (minutes, seconds) = length.split(':').map(String::toInt)
    return minutes.minutes + seconds.seconds
}

private fun coverOf(colors: Pair<Color, Color>): ImageBitmap {
    val (start, end) = colors
    val image = ImageBitmap(SIDE.toInt(), SIDE.toInt())
    val canvas = Canvas(image)
    val gradient = Paint()
    val background = Brush.linearGradient(listOf(start, end), start = Offset.Zero, end = Offset(x = SIDE, y = SIDE))
    background.applyTo(Size(SIDE, SIDE), gradient, 1f)
    canvas.drawRect(Rect(Offset.Zero, Size(SIDE, SIDE)), gradient)
    val orb = Paint()
    orb.color = Color.White.copy(alpha = HALO)
    canvas.drawCircle(center = Offset(x = SIDE * (1f - ORB), y = SIDE * ORB), radius = SIDE * ORB / 2f, paint = orb)
    return image
}
