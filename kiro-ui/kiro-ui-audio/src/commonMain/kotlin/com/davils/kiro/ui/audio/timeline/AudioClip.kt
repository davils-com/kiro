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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.time.DurationUnit

/**
 * A clip as editing programs show it on a track, the bar of a piece of a recording: a tinted header with its name and
 * the icons of a muted or locked clip, the waveform of the part it plays scaled by its gain, the curves of its fades
 * and the line of its gain. A tap selects it; a drag on its body moves it, a drag on an edge trims it, a drag on the
 * handle of a fade changes the fade and a drag on the line of the gain changes the gain. Every change is only asked
 * for through [onAction]; a locked clip asks for none of them.
 *
 * The clip is as wide as its length at [zoom] and fills the height its track gives it.
 *
 * @param clip the clip to show.
 * @param zoom the zoom of the timeline in density-independent pixels per second.
 * @param onAction receives what the user asks the clip to do.
 * @param modifier the modifier of the clip.
 * @param isSelected whether the clip is selected.
 * @param style the style of the clip.
 * @param badges the icons at the end of the header; by default the icons of a muted and of a locked clip.
 * @since 1.0.0
 */
@Composable
public fun AudioClip(
    clip: Clip,
    zoom: Float,
    onAction: (TimelineAction) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    style: AudioClipStyle = audioClipStyle(),
    badges: @Composable RowScope.() -> Unit = { ClipBadges(clip = clip, style = style) },
) {
    val current by rememberUpdatedState(clip)
    val act by rememberUpdatedState(onAction)
    val hovered = remember { mutableStateOf<ClipZone?>(null) }
    val dragged = remember { mutableStateOf<ClipZone?>(null) }
    val measurer = rememberTextMeasurer()
    val width = (clip.length.toDouble(DurationUnit.SECONDS) * zoom).toFloat().dp
    val geometryOf = { size: IntSize, density: Density ->
        ClipGeometry(
            clip = current,
            width = size.width.toFloat(),
            height = size.height.toFloat(),
            perSecond = zoom * density.density,
            style = style,
            density = density,
        )
    }
    val pointer = ClipPointer(geometryOf = geometryOf, hovered = hovered, dragged = dragged) { action -> act(action) }
    val drawing = clipDrawing(
        clip = clip,
        zoom = zoom,
        marks = ClipMarks(isSelected = isSelected, hovered = hovered, dragged = dragged.value),
        measurer = measurer,
        style = style,
    )
    Box(
        modifier = modifier.then(
            Modifier
                .width(width)
                .fillMaxHeight()
                .graphicsLayer { alpha = style.mutedAlpha.takeIf { clip.isMuted } ?: 1f }
                .pointerHoverIcon(iconOf(zone = hovered.value, isLocked = clip.isLocked))
                .then(pointer.modifier(keys = listOf(clip.id, zoom, style)) { current.id })
                .then(drawing)
                .semantics {
                    contentDescription = clip.name
                    role = Role.Button
                    if (isSelected) stateDescription = style.selectedLabel
                    onClick {
                        act(TimelineAction.Select(clip.id))
                        true
                    }
                },
        ),
    ) {
        ClipHeader(name = clip.name, color = style.nameOf(clip), style = style, badges = badges)
    }
}

@Composable
private fun ClipHeader(name: String, color: Color, style: AudioClipStyle, badges: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(style.headerHeight)
            .padding(horizontal = style.padding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicText(
            text = name,
            modifier = Modifier.weight(weight = 1f, fill = true),
            style = style.nameStyle.copy(color = color),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        badges()
    }
}

private fun iconOf(zone: ClipZone?, isLocked: Boolean): PointerIcon {
    if (isLocked) return PointerIcon.Default
    return when (zone) {
        ClipZone.Start, ClipZone.End -> resizeCursor()
        ClipZone.FadeIn, ClipZone.FadeOut, ClipZone.Gain -> PointerIcon.Crosshair
        ClipZone.Body -> moveCursor()
        null -> PointerIcon.Default
    }
}
