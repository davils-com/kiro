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

/**
 * Holds the sizes of the panels of a [ResizablePanelGroup] and changes them.
 *
 * Sizes are in percent of the space that the handles leave and always add up to 100. A move of a handle grows the
 * panels on one side and shrinks those on the other side, each within its limits, nearest panels first.
 *
 * @param initialSizes the sizes of the panels at the start, which must not be empty and must add up to 100.
 * @throws IllegalArgumentException if [initialSizes] is empty or does not add up to 100.
 * @since 1.0.0
 */
@Stable
public class ResizableState(initialSizes: List<Float>) {
    init {
        require(initialSizes.isNotEmpty()) { "A resizable group needs at least one panel size." }
        val total = initialSizes.sum()
        require(abs(total - TOTAL) < TOLERANCE) { "The panel sizes must add up to 100 percent, not $total." }
    }

    /**
     * The current sizes of the panels, in percent and in the order of the panels.
     *
     * @since 1.0.0
     */
    public var sizes: List<Float> by mutableStateOf(initialSizes.toList())
        private set

    internal var isDragging: Boolean by mutableStateOf(false)
        private set

    internal var panels: List<ResizablePanel> = emptyList()

    private var origin: List<Float>? = null
    private var restored: Map<Int, Float> = emptyMap()

    /**
     * Moves a handle, as far as the limits of the panels allow.
     *
     * @param handle the index of the handle, `0` for the handle after the first panel.
     * @param delta how far to move the handle, in percent; a positive value grows the panels before the handle.
     * @throws IllegalArgumentException if the group has no handle with the index [handle].
     * @since 1.0.0
     */
    public fun resize(handle: Int, delta: Float) {
        require(handle in 0 until sizes.lastIndex) { "The group has no handle $handle." }
        sizes = resized(sizes = sizes, handle = handle, delta = delta, limits = limitsOf())
    }

    /**
     * Collapses a panel to its collapsed size and remembers its size for [expand].
     *
     * Nothing happens if the panel is not collapsible or the group is not shown yet.
     *
     * @param panel the index of the panel.
     * @since 1.0.0
     */
    public fun collapse(panel: Int) {
        val target = panels.getOrNull(panel)?.takeIf { it.isCollapsible } ?: return
        restored = restored + (panel to sizes[panel])
        moveTo(panel = panel, size = target.collapsedSize, limits = limitsOf(collapsing = panel))
    }

    /**
     * Expands a panel to the size it had before [collapse], or to its minimum size if there is none.
     *
     * Nothing happens if the group has no such panel or is not shown yet.
     *
     * @param panel the index of the panel.
     * @since 1.0.0
     */
    public fun expand(panel: Int) {
        val target = panels.getOrNull(panel) ?: return
        val size = restored[panel] ?: target.limits.start
        restored = restored - panel
        moveTo(panel = panel, size = size, limits = limitsOf(collapsing = panel))
    }

    /**
     * Returns whether a panel is collapsed.
     *
     * @param panel the index of the panel.
     * @return `true` if the panel is collapsible and has its collapsed size, `false` otherwise.
     * @since 1.0.0
     */
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
        return minOf(a = floor, b = limits.start)..limits.endInclusive
    }

    private fun floorOf(panel: ResizablePanel?, isCollapsing: Boolean): Float? {
        if (panel == null || !isCollapsing) return null
        return panel.collapsedSize
    }
}

/**
 * Creates a [ResizableState] and remembers it across recompositions.
 *
 * @param sizes the sizes of the panels at the start, in percent, which must add up to 100.
 * @return the remembered state.
 * @throws IllegalArgumentException if [sizes] is empty or does not add up to 100.
 * @since 1.0.0
 */
@Composable
public fun rememberResizableState(vararg sizes: Float): ResizableState = remember { ResizableState(sizes.toList()) }
