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

package com.davils.kiro.ui.audio.meter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import com.davils.kiro.ui.audio.following
import com.davils.kiro.ui.audio.signal.AudioLevels
import com.davils.kiro.ui.audio.signal.ChannelLevel
import com.davils.kiro.ui.components.Orientation
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * A level meter as editing programs and mixing desks show it: a bar per channel that fills from the floor of its
 * range to the peak level, with the root mean square level inside it, colored green, yellow and red by zones. A line
 * marks the held peak, an indicator beside each bar lights when the channel clipped, a scale names the levels, and a
 * readout shows the peak of each channel. A tap on the meter asks to reset the clip indicators.
 *
 * Kiro measures nothing: the caller hands in the levels it measured, held and clipped, as often as it likes, and the
 * bars follow them within the response of the style.
 *
 * @param levels the levels of the channels to show.
 * @param modifier the modifier of the meter.
 * @param orientation whether the bars stand upright or lie from left to right.
 * @param onResetClip called when the meter is tapped, to reset the clip indicators.
 * @param style the style of the meter.
 * @since 1.0.0
 */
@Composable
public fun LevelMeter(
    levels: AudioLevels,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    onResetClip: () -> Unit = {},
    style: LevelMeterStyle = levelMeterStyle(),
) {
    val reset by rememberUpdatedState(onResetClip)
    val count = levels.channels.size
    val shown = levels.channels.mapIndexed { index, level -> key(index) { meterLevelOf(level = level, style = style) } }
    val measurer = rememberTextMeasurer()
    val texts = remember(style, measurer, count) { MeterTexts.of(measurer = measurer, style = style, count = count) }
    val isVertical = orientation == Orientation.Vertical
    val size = with(texts) { LocalDensity.current.sizeOf(isVertical = isVertical, count = count, style = style) }
    val described = levels.channels.mapIndexed { index, level ->
        "${style.channel(index, count)} ${style.readout(level.peak)}"
    }
    Canvas(
        modifier = modifier.then(
            Modifier
                .size(width = size.first, height = size.second)
                .pointerInput(Unit) { detectTapGestures { reset() } }
                .semantics {
                    contentDescription = style.description
                    stateDescription = described.joinToString(separator = ", ")
                    onClick(label = style.resetClip) {
                        reset()
                        true
                    }
                },
        ),
    ) {
        val geometry = geometryOf(isVertical = isVertical, texts = texts, style = style)
        val zones = zonesOf(style)
        shown.forEachIndexed { channel, level ->
            drawBar(geometry = geometry, channel = channel, level = level, zones = zones, style = style)
            drawClip(geometry = geometry, channel = channel, isClipping = level.isClipping, style = style)
            drawTexts(
                geometry = geometry,
                channel = channel,
                peak = levels.channels[channel],
                texts = texts,
                measurer = measurer,
                style = style,
            )
        }
        drawScale(geometry = geometry, texts = texts, channels = count, style = style)
    }
}

@Composable
private fun meterLevelOf(level: ChannelLevel, style: LevelMeterStyle): MeterLevel {
    val peak by animateFloatAsState(
        targetValue = style.range.fractionOf(level.peak),
        animationSpec = style.motion.following(style.response),
    )
    val rms by animateFloatAsState(
        targetValue = style.range.fractionOf(level.rms),
        animationSpec = style.motion.following(style.response),
    )
    val hold = level.hold?.let { held -> style.range.fractionOf(held) }
    return MeterLevel(peak = peak, rms = min(a = rms, b = peak), hold = hold, isClipping = level.isClipping)
}

private fun DrawScope.geometryOf(isVertical: Boolean, texts: MeterTexts, style: LevelMeterStyle): MeterGeometry {
    val thickness = style.thickness.toPx()
    val gap = style.clipGap.toPx()
    val clip = style.clipSize.toPx()
    val slot = max(a = thickness, b = texts.readoutWidth).takeIf { isVertical } ?: thickness
    val start = (texts.lineHeight + gap + clip + gap).takeIf { isVertical } ?: texts.nameWidth + gap
    val end = (size.height - texts.lineHeight - gap).takeIf { isVertical }
        ?: size.width - texts.readoutWidth - gap - clip - gap
    return MeterGeometry(
        isVertical = isVertical,
        start = start,
        length = end - start,
        thickness = thickness,
        slot = slot,
        gap = style.channelGap.toPx(),
    )
}

private fun zonesOf(style: LevelMeterStyle): List<MeterZone> {
    val warning = style.range.fractionOf(style.warning)
    val danger = style.range.fractionOf(style.danger)
    return listOf(
        MeterZone(from = 0f, to = warning, color = style.safeColor),
        MeterZone(from = warning, to = danger, color = style.warningColor),
        MeterZone(from = danger, to = 1f, color = style.dangerColor),
    )
}

private fun colorAt(fraction: Float, zones: List<MeterZone>): Color =
    zones.lastOrNull { zone -> fraction >= zone.from }?.color ?: zones.first().color

private fun DrawScope.drawBar(
    geometry: MeterGeometry,
    channel: Int,
    level: MeterLevel,
    zones: List<MeterZone>,
    style: LevelMeterStyle,
) {
    when (style.mode) {
        MeterMode.Continuous -> {
            val bar = geometry.bar(channel)
            val radius = CornerRadius(style.radius.toPx())
            drawRoundRect(color = style.track, topLeft = bar.topLeft, size = bar.size, cornerRadius = radius)
            drawZones(geometry = geometry, channel = channel, upTo = level.peak, alpha = style.peakAlpha, zones = zones)
            drawZones(geometry = geometry, channel = channel, upTo = level.rms, alpha = 1f, zones = zones)
        }

        MeterMode.Segmented -> {
            drawSegments(geometry = geometry, channel = channel, level = level, zones = zones, style = style)
        }
    }
    val hold = level.hold ?: return
    val half = geometry.fractionOf(style.holdThickness.toPx() / 2f)
    val line = geometry.span(channel = channel, from = hold - half, to = hold + half)
    drawRect(color = colorAt(fraction = hold, zones = zones), topLeft = line.topLeft, size = line.size)
}

private fun DrawScope.drawZones(
    geometry: MeterGeometry,
    channel: Int,
    upTo: Float,
    alpha: Float,
    zones: List<MeterZone>,
) {
    zones.forEach { zone ->
        val to = min(a = zone.to, b = upTo)
        if (to > zone.from) {
            val part = geometry.span(channel = channel, from = zone.from, to = to)
            drawRect(color = zone.color.copy(alpha = alpha), topLeft = part.topLeft, size = part.size)
        }
    }
}

private fun DrawScope.drawSegments(
    geometry: MeterGeometry,
    channel: Int,
    level: MeterLevel,
    zones: List<MeterZone>,
    style: LevelMeterStyle,
) {
    val segment = geometry.fractionOf(style.segment.toPx())
    val gap = geometry.fractionOf(style.segmentGap.toPx())
    val count = floor((1f + gap) / (segment + gap)).toInt()
    val radius = CornerRadius(style.radius.toPx())
    for (index in 0 until count) {
        val from = index * (segment + gap)
        val to = from + segment
        val alpha = 1f.takeIf { from < level.rms } ?: style.peakAlpha
        val zone = colorAt(fraction = (from + to) / 2f, zones = zones)
        val lit = zone.copy(alpha = alpha).takeIf { from < level.peak }
        val part = geometry.span(channel = channel, from = from, to = to)
        drawRoundRect(color = lit ?: style.track, topLeft = part.topLeft, size = part.size, cornerRadius = radius)
    }
}

private fun DrawScope.drawClip(geometry: MeterGeometry, channel: Int, isClipping: Boolean, style: LevelMeterStyle) {
    val bar = geometry.bar(channel)
    val gap = style.clipGap.toPx()
    val clip = style.clipSize.toPx()
    val led = Rect(left = bar.left, top = bar.top - gap - clip, right = bar.right, bottom = bar.top - gap)
        .takeIf { geometry.isVertical }
        ?: Rect(left = bar.right + gap, top = bar.top, right = bar.right + gap + clip, bottom = bar.bottom)
    val color = style.clipColor.takeIf { isClipping } ?: style.clipIdle
    drawRoundRect(
        color = color,
        topLeft = led.topLeft,
        size = led.size,
        cornerRadius = CornerRadius(style.radius.toPx()),
    )
}

private fun DrawScope.drawTexts(
    geometry: MeterGeometry,
    channel: Int,
    peak: ChannelLevel,
    texts: MeterTexts,
    measurer: TextMeasurer,
    style: LevelMeterStyle,
) {
    val bar = geometry.bar(channel)
    val gap = style.clipGap.toPx()
    val beyond = gap + style.clipSize.toPx() + gap
    val name = texts.names[channel]
    val readout = measurer.measure(text = style.readout(peak.hold ?: peak.peak), style = texts.label)
    if (geometry.isVertical) {
        val center = bar.center.x
        val above = bar.top - beyond - readout.size.height
        drawText(readout, topLeft = Offset(x = center - readout.size.width / 2f, y = above))
        drawText(name, topLeft = Offset(x = center - name.size.width / 2f, y = bar.bottom + gap))
        return
    }
    val middle = bar.center.y
    drawText(name, topLeft = Offset(x = 0f, y = middle - name.size.height / 2f))
    drawText(readout, topLeft = Offset(x = bar.right + beyond, y = middle - readout.size.height / 2f))
}

private fun DrawScope.drawScale(geometry: MeterGeometry, texts: MeterTexts, channels: Int, style: LevelMeterStyle) {
    val cross = geometry.extentOf(channels) + style.scaleGap.toPx()
    val tick = style.tickLength.toPx()
    val width = style.holdThickness.toPx() / 2f
    style.marks.forEachIndexed { index, mark ->
        val fraction = style.range.fractionOf(mark)
        val from = geometry.mark(fraction = fraction, cross = cross)
        val to = geometry.mark(fraction = fraction, cross = cross + tick)
        drawLine(color = style.tickColor, start = from, end = to, strokeWidth = width)
        val label = texts.marks[index]
        val beyond = geometry.mark(fraction = fraction, cross = cross + tick + style.scaleGap.toPx())
        val shift = Offset(x = 0f, y = label.size.height / 2f).takeIf { geometry.isVertical }
            ?: Offset(x = label.size.width / 2f, y = 0f)
        drawText(label, topLeft = beyond - shift)
    }
}
