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

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import com.davils.kiro.ui.audio.waveform.Envelope
import com.davils.kiro.ui.audio.waveform.drawEnvelope
import kotlin.math.pow
import kotlin.time.DurationUnit

private const val DECIBELS_PER_DECADE: Float = 20f
private const val DECADE: Float = 10f

internal fun clipDrawing(
    clip: Clip,
    zoom: Float,
    marks: ClipMarks,
    measurer: TextMeasurer,
    style: AudioClipStyle,
): Modifier = Modifier.drawWithCache {
    val geometry = ClipGeometry(
        clip = clip,
        width = size.width,
        height = size.height,
        perSecond = zoom * density,
        style = style,
        density = this,
    )
    val tint = style.tintOf(clip)
    val outline = Path()
    outline.addRoundRect(RoundRect(rect = size.toRect(), cornerRadius = CornerRadius(style.radius.toPx())))
    val body = Rect(left = 0f, top = geometry.header, right = size.width, bottom = size.height)
    val envelope = Envelope.of(
        channels = clip.peaks.channels,
        window = windowOf(clip),
        width = size.width,
        look = Pair(first = 1f, second = 1f),
    )
    val fades = fadesOf(geometry = geometry, body = body)
    val gain = DECADE.pow(clip.gain.value / DECIBELS_PER_DECADE)
    onDrawBehind {
        clipPath(outline) {
            drawRect(color = tint.copy(alpha = style.bodyAlpha), topLeft = body.topLeft, size = body.size)
            drawRect(
                color = tint.copy(alpha = style.headerAlpha),
                size = Size(width = size.width, height = geometry.header),
            )
            drawEnvelope(
                envelope = envelope,
                area = body,
                gain = gain,
                colors = Pair(first = tint.copy(alpha = style.peakAlpha), second = tint),
                isBars = false,
            )
            drawFades(fades = fades, style = style)
            if (marks.hovered.value != null) drawRect(color = style.selectedColor.copy(alpha = style.hoverAlpha))
        }
        val isActive = marks.isSelected || marks.hovered.value != null || marks.dragged != null
        if (isActive && !clip.isLocked) drawHandles(geometry = geometry, style = style)
        if (marks.dragged == ClipZone.Gain) {
            val label = measurer.measure(
                text = style.gainLabel(clip.gain),
                style = style.nameStyle.copy(color = style.gainColor),
            )
            val y = (geometry.gain - label.size.height).coerceAtLeast(minimumValue = geometry.header)
            drawText(label, topLeft = Offset(x = style.padding.toPx(), y = y))
        }
        drawBorder(tint = tint, isSelected = marks.isSelected, style = style)
    }
}

private fun windowOf(clip: Clip): ClosedFloatingPointRange<Float> {
    val total = clip.peaks.duration.toDouble(DurationUnit.SECONDS)
    if (total <= 0.0) return 0f..1f
    val from = clip.sourceOffset.toDouble(DurationUnit.SECONDS) / total
    val to = (clip.sourceOffset + clip.length).toDouble(DurationUnit.SECONDS) / total
    return from.toFloat()..to.toFloat()
}

private fun CacheDrawScope.fadesOf(geometry: ClipGeometry, body: Rect): List<Pair<Path, Path>> {
    val top = body.top
    val bottom = body.bottom
    val shadeIn = Path()
    shadeIn.moveTo(x = 0f, y = top)
    shadeIn.lineTo(x = geometry.fadeIn, y = top)
    shadeIn.quadraticTo(x1 = 0f, y1 = top, x2 = 0f, y2 = bottom)
    shadeIn.close()
    val curveIn = Path()
    curveIn.moveTo(x = 0f, y = bottom)
    curveIn.quadraticTo(x1 = 0f, y1 = top, x2 = geometry.fadeIn, y2 = top)
    val shadeOut = Path()
    shadeOut.moveTo(x = size.width, y = top)
    shadeOut.lineTo(x = geometry.fadeOut, y = top)
    shadeOut.quadraticTo(x1 = size.width, y1 = top, x2 = size.width, y2 = bottom)
    shadeOut.close()
    val curveOut = Path()
    curveOut.moveTo(x = size.width, y = bottom)
    curveOut.quadraticTo(x1 = size.width, y1 = top, x2 = geometry.fadeOut, y2 = top)
    return listOf(Pair(first = shadeIn, second = curveIn), Pair(first = shadeOut, second = curveOut))
}

private fun DrawScope.drawFades(fades: List<Pair<Path, Path>>, style: AudioClipStyle) {
    val line = Stroke(width = style.lineWidth.toPx())
    fades.forEach { (shade, curve) ->
        drawPath(path = shade, color = style.fadeColor.copy(alpha = style.fadeAlpha))
        drawPath(path = curve, color = style.fadeLineColor, style = line)
    }
}

private fun DrawScope.drawHandles(geometry: ClipGeometry, style: AudioClipStyle) {
    val handle = style.handleSize.toPx()
    val half = handle / 2f
    val room = (size.width - handle).coerceAtLeast(minimumValue = 0f)
    listOf(geometry.fadeIn, geometry.fadeOut).forEach { x ->
        val left = (x - half).coerceIn(minimumValue = 0f, maximumValue = room)
        drawRect(
            color = style.handleColor,
            topLeft = Offset(x = left, y = geometry.header - half),
            size = Size(width = handle, height = handle),
        )
    }
    drawLine(
        color = style.gainColor,
        start = Offset(x = 0f, y = geometry.gain),
        end = Offset(x = size.width, y = geometry.gain),
        strokeWidth = style.lineWidth.toPx(),
    )
}

private fun DrawScope.drawBorder(tint: Color, isSelected: Boolean, style: AudioClipStyle) {
    val width = style.selectedWidth.toPx().takeIf { isSelected } ?: style.border.toPx()
    val color = style.selectedColor.takeIf { isSelected } ?: tint.copy(alpha = style.borderAlpha)
    val inset = width / 2f
    drawRoundRect(
        color = color,
        topLeft = Offset(x = inset, y = inset),
        size = Size(width = size.width - width, height = size.height - width),
        cornerRadius = CornerRadius(style.radius.toPx()),
        style = Stroke(width = width),
    )
}
