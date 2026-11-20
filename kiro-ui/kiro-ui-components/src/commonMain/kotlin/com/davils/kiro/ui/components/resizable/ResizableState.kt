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

package com.davils.kiro.ui.components.resizable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.math.abs

private const val TOTAL: Float = 100f
private const val TOLERANCE: Float = 0.01f

@Stable
public class ResizableState(initialSizes: List<Float>) {
    init {
        require(initialSizes.isNotEmpty()) { "A resizable group needs at least one panel size." }
        val total = initialSizes.sum()
        require(abs(total - TOTAL) < TOLERANCE) { "The panel sizes must add up to 100 percent, not $total." }
    }

    public var sizes: List<Float> by mutableStateOf(initialSizes.toList())
        private set

    internal var isDragging: Boolean by mutableStateOf(false)
        private set

    internal var panels: List<ResizablePanel> = emptyList()

    private var origin: List<Float>? = null
    private var restored: Map<Int, Float> = emptyMap()

    public fun resize(handle: Int, delta: Float) {
        require(handle in 0 until sizes.lastIndex) { "The group has no handle $handle." }
        sizes = resized(sizes = sizes, handle = handle, delta = delta, limits = limitsOf())
    }

    public fun collapse(panel: Int) {
        val target = panels.getOrNull(panel)?.takeIf { it.isCollapsible } ?: return
        restored = restored + (panel to sizes[panel])
        moveTo(panel = panel, size = target.collapsedSize, limits = limitsOf(collapsing = panel))
    }

    public fun expand(panel: Int) {
        val target = panels.getOrNull(panel) ?: return
        val size = restored[panel] ?: target.limits.start
        restored = restored - panel
        moveTo(panel = panel, size = size, limits = limitsOf(collapsing = panel))
    }

    public fun isCollapsed(panel: Int): Boolean {
        val target = panels.getOrNull(panel)?.takeIf { it.isCollapsible } ?: return false
        return abs(sizes[panel] - target.collapsedSize) < TOLERANCE
    }

    internal fun begin() {
        origin = sizes
        isDragging = true
    }

    internal fun dragTo(handle: Int, delta: Float) {
        val start = origin ?: sizes
        sizes = resized(sizes = start, handle = handle, delta = delta, limits = limitsOf())
    }

    internal fun end() {
        origin = null
        isDragging = false
    }

    private fun moveTo(panel: Int, size: Float, limits: List<ClosedFloatingPointRange<Float>>) {
        val isLast = panel == sizes.lastIndex
        val handle = (panel - 1).takeIf { isLast } ?: panel
        val change = size - sizes[panel]
        val delta = (-change).takeIf { isLast } ?: change
        sizes = resized(sizes = sizes, handle = handle, delta = delta, limits = limits)
    }

    private fun limitsOf(collapsing: Int? = null): List<ClosedFloatingPointRange<Float>> =
        sizes.indices.map { index -> limitOf(index = index, collapsing = collapsing) }

    private fun limitOf(index: Int, collapsing: Int?): ClosedFloatingPointRange<Float> {
        val panel = panels.getOrNull(index)
        val limits = panel?.limits ?: 0f..TOTAL
        val floor = floorOf(panel = panel, isCollapsing = index == collapsing) ?: return limits
        return minOf(floor, limits.start)..limits.endInclusive
    }

    private fun floorOf(panel: ResizablePanel?, isCollapsing: Boolean): Float? {
        if (panel == null || !isCollapsing) return null
        return panel.collapsedSize
    }
}

@Composable
public fun rememberResizableState(vararg sizes: Float): ResizableState = remember { ResizableState(sizes.toList()) }
