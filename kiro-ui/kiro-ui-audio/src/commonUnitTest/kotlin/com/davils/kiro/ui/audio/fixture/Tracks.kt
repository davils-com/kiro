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

package com.davils.kiro.ui.audio.fixture

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import com.davils.kiro.ui.audio.Track
import com.davils.kiro.ui.audio.TrackId
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

private const val SIDE: Int = 48

internal const val FIRST: String = "Uplink"
internal const val SECOND: String = "Spanning Tree"
internal const val THIRD: String = "Packet Loss"

internal fun coverOf(background: Color, accent: Color = background, accentShare: Float = 0f): ImageBitmap {
    val image = ImageBitmap(SIDE, SIDE)
    val canvas = Canvas(image)
    val base = Paint()
    base.color = background
    val highlight = Paint()
    highlight.color = accent
    canvas.drawRect(Rect(Offset.Zero, Size(width = SIDE.toFloat(), height = SIDE.toFloat())), base)
    canvas.drawRect(Rect(Offset.Zero, Size(width = SIDE * accentShare, height = SIDE.toFloat())), highlight)
    return image
}

internal val tracks: List<Track> = listOf(
    Track(
        id = TrackId(FIRST),
        title = FIRST,
        artist = "The Switches",
        duration = 3.minutes,
        album = "Layer 2",
        cover = coverOf(Color(0xFF7C3AED)),
    ),
    Track(id = TrackId(SECOND), title = SECOND, artist = "Root Bridge", duration = 2.minutes + 30.seconds),
    Track(
        id = TrackId(THIRD),
        title = THIRD,
        artist = "Jitter",
        duration = 4.minutes,
        cover = coverOf(Color(0xFF0EA5E9)),
    ),
)
