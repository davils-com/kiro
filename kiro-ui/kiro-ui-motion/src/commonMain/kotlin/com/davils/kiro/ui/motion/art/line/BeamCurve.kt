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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

private const val FIRST_START: Float = -0.15f
private const val FIRST_END: Float = 0.3f
private const val FAN: Float = 1.25f
private const val BELOW: Float = 1.04f
private const val ABOVE: Float = -0.04f
private const val RISE: Float = 0.7f
private const val CREST: Float = 0.3f
private const val LEAN: Float = 0.5f

internal class BeamCurve(val start: Offset, val first: Offset, val second: Offset, val end: Offset) {
    fun into(path: Path) {
        path.rewind()
        path.moveTo(x = start.x, y = start.y)
        path.cubicTo(x1 = first.x, y1 = first.y, x2 = second.x, y2 = second.y, x3 = end.x, y3 = end.y)
    }
}

internal fun beamCurveOf(position: Float, spread: Float, bend: Float, size: Size): BeamCurve {
    val width = size.width
    val height = size.height
    val startX = width * (FIRST_START + spread * position)
    val endX = width * (FIRST_END + spread * FAN * position)
    return BeamCurve(
        start = Offset(x = startX, y = height * BELOW),
        first = Offset(x = startX + width * bend, y = height * RISE),
        second = Offset(x = endX - width * bend * LEAN, y = height * CREST),
        end = Offset(x = endX + width * bend * LEAN, y = height * ABOVE),
    )
}
