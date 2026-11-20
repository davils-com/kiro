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

package com.davils.kiro.ui.components.primitive.overlay

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Where an overlay, such as a popover or a menu, opens relative to its anchor.
 *
 * When the overlay does not fit on [side] within the window but fits on the opposite side, it opens there instead. It
 * is then moved along the window, so that it keeps [collisionPadding] from the edges of the window where it can.
 *
 * @property side the side of the anchor on which the overlay opens.
 * @property align how the overlay lines up with the anchor along that side.
 * @property sideOffset the distance between the anchor and the overlay.
 * @property alignOffset how far the overlay moves from its aligned position towards the inside of the anchor; a
 * centered overlay moves to the right, or down on the start and end sides.
 * @property collisionPadding the distance the overlay keeps from the edges of the window.
 * @since 1.0.0
 */
@Immutable
public class Placement(
    public val side: Side = Side.Bottom,
    public val align: Align = Align.Center,
    public val sideOffset: Dp = 4.dp,
    public val alignOffset: Dp = 0.dp,
    public val collisionPadding: Dp = 5.dp,
) {
    /**
     * Returns a copy of this [Placement] with the given values replaced and every other value kept.
     *
     * @param side the new value of [side].
     * @param align the new value of [align].
     * @param sideOffset the new value of [sideOffset].
     * @param alignOffset the new value of [alignOffset].
     * @param collisionPadding the new value of [collisionPadding].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        side: Side = this.side,
        align: Align = this.align,
        sideOffset: Dp = this.sideOffset,
        alignOffset: Dp = this.alignOffset,
        collisionPadding: Dp = this.collisionPadding,
    ): Placement = Placement(
        side = side,
        align = align,
        sideOffset = sideOffset,
        alignOffset = alignOffset,
        collisionPadding = collisionPadding,
    )

    override fun equals(other: Any?): Boolean = other is Placement && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Placement(" +
        "side=$side, " +
        "align=$align, " +
        "sideOffset=$sideOffset, " +
        "alignOffset=$alignOffset, " +
        "collisionPadding=$collisionPadding)"

    private fun values(): List<Any?> = listOf(
        side,
        align,
        sideOffset,
        alignOffset,
        collisionPadding,
    )
}
