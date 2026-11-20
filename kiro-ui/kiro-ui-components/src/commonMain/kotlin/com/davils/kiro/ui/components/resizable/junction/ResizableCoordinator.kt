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

package com.davils.kiro.ui.components.resizable.junction

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.layout.LayoutCoordinates

@Stable
internal class ResizableCoordinator {
    private var root: LayoutCoordinates? = null
    private val lines = mutableMapOf<HandleActions, Line>()
    private val placed = mutableStateMapOf<HandleActions, HandleSpot>()

    val spots: List<HandleSpot>
        get() = placed.values.toList()

    fun anchor(coordinates: LayoutCoordinates) {
        root = coordinates
        refresh()
    }

    fun place(isVerticalLine: Boolean, coordinates: LayoutCoordinates, actions: HandleActions) {
        lines[actions] = Line(isVertical = isVerticalLine, coordinates = coordinates)
        refresh()
    }

    fun remove(actions: HandleActions) {
        lines.remove(actions)
        placed.remove(actions)
    }

    private fun refresh() {
        val origin = root?.takeIf { it.isAttached } ?: return
        lines.forEach { (actions, line) -> update(origin = origin, actions = actions, line = line) }
    }

    private fun update(origin: LayoutCoordinates, actions: HandleActions, line: Line) {
        if (!line.coordinates.isAttached) return
        val bounds = origin.localBoundingBoxOf(line.coordinates, clipBounds = false)
        val spot = HandleSpot(isVerticalLine = line.isVertical, bounds = bounds, actions = actions)
        if (placed[actions] != spot) placed[actions] = spot
    }
}
