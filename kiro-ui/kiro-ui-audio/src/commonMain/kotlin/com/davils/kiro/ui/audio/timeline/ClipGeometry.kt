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

package com.davils.kiro.ui.audio.timeline

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import com.davils.kiro.ui.audio.signal.Decibels
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

internal class ClipGeometry(
    private val clip: Clip,
    width: Float,
    height: Float,
    private val perSecond: Float,
    private val style: AudioClipStyle,
    density: Density,
) {
    val header: Float = with(density) { style.headerHeight.toPx() }

    private val edge: Float = with(density) { style.edgeWidth.toPx() }

    val body: Float = (height - header).coerceAtLeast(minimumValue = 0f)

    val fadeIn: Float = pixelsOf(clip.fadeIn)

    val fadeOut: Float = width - pixelsOf(clip.fadeOut)

    val gain: Float = header + body * (1f - style.gainRange.fractionOf(clip.gain))

    private val zones: List<Pair<ClipZone, (Offset) -> Boolean>> = listOf(
        ClipZone.FadeIn to { point -> isNearTop(point) && abs(point.x - fadeIn) <= edge && point.x < width / 2f },
        ClipZone.FadeOut to { point -> isNearTop(point) && abs(point.x - fadeOut) <= edge },
        ClipZone.Start to { point -> point.x <= edge },
        ClipZone.End to { point -> point.x >= width - edge },
        ClipZone.Gain to { point -> point.y > header && abs(point.y - gain) <= edge },
    )

    fun zoneAt(point: Offset): ClipZone = zones.firstOrNull { (_, matches) -> matches(point) }?.first ?: ClipZone.Body

    fun actionOf(zone: ClipZone, drag: Offset): TimelineAction? {
        if (clip.isLocked) return null
        val moved = timeOf(drag.x)
        return when (zone) {
            ClipZone.Body -> TimelineAction.Move(clip = clip.id, start = clip.start + moved)
            ClipZone.Start -> TimelineAction.Trim(clip = clip.id, edge = ClipEdge.Start, at = clip.start + moved)
            ClipZone.End -> TimelineAction.Trim(clip = clip.id, edge = ClipEdge.End, at = clip.end + moved)
            ClipZone.FadeIn -> TimelineAction.Fade(clip = clip.id, edge = ClipEdge.Start, length = clip.fadeIn + moved)
            ClipZone.FadeOut -> TimelineAction.Fade(clip = clip.id, edge = ClipEdge.End, length = clip.fadeOut - moved)
            ClipZone.Gain -> TimelineAction.Gain(clip = clip.id, gain = gainOf(drag.y))
        }
    }

    private fun isNearTop(point: Offset): Boolean = abs(point.y - header) <= edge

    private fun gainOf(dragged: Float): Decibels {
        val range = style.gainRange
        val span = range.ceiling.value - range.floor.value
        val share = dragged / body.coerceAtLeast(minimumValue = 1f)
        return Decibels(clip.gain.value - share * span)
    }

    private fun pixelsOf(time: Duration): Float = (time.toDouble(DurationUnit.SECONDS) * perSecond).toFloat()

    private fun timeOf(pixels: Float): Duration = (pixels / perSecond).toDouble().seconds
}
