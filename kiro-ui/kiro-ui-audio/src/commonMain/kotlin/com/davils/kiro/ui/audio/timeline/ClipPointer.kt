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

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.drag
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize

internal class ClipPointer(
    private val geometryOf: (IntSize, Density) -> ClipGeometry,
    private val hovered: MutableState<ClipZone?>,
    private val dragged: MutableState<ClipZone?>,
    private val act: (TimelineAction) -> Unit,
) {
    fun modifier(keys: List<Any>, id: () -> ClipId): Modifier {
        val hovering = Modifier.pointerInput(keys) {
            awaitPointerEventScope {
                while (true) {
                    val event = awaitPointerEvent()
                    val position = event.changes.firstOrNull()?.position ?: Offset.Zero
                    val zone = geometryOf(size, this).zoneAt(position)
                    hovered.value = zone.takeIf { event.type != PointerEventType.Exit }
                }
            }
        }
        val tapping = Modifier.pointerInput(keys) { detectTapGestures { act(TimelineAction.Select(id())) } }
        val dragging = Modifier.pointerInput(keys) {
            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                val geometry = geometryOf(size, this)
                val zone = geometry.zoneAt(down.position)
                val slop = awaitTouchSlopOrCancellation(down.id) { change, _ -> change.consume() }
                if (slop != null) {
                    dragged.value = zone
                    act(TimelineAction.Select(id()))
                    var total = slop.position - down.position
                    geometry.actionOf(zone = zone, drag = total)?.let(act)
                    drag(slop.id) { change ->
                        total += change.positionChange()
                        change.consume()
                        geometry.actionOf(zone = zone, drag = total)?.let(act)
                    }
                    dragged.value = null
                }
            }
        }
        return hovering.then(tapping).then(dragging)
    }
}
