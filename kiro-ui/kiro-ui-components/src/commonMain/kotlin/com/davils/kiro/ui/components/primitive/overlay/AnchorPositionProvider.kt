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

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider

internal class AnchorPositionProvider(
    private val side: Side,
    private val align: Align,
    private val sideOffset: Int,
    private val alignOffset: Int,
    private val padding: Int,
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val physical = physicalSideOf(side = side, isRtl = isRtl)
        val isVertical = physical == Physical.Top || physical == Physical.Bottom
        if (isVertical) {
            return vertical(
                anchor = anchorBounds,
                window = windowSize,
                size = popupContentSize,
                physical = physical,
                isRtl = isRtl,
            )
        }
        return horizontal(anchor = anchorBounds, window = windowSize, size = popupContentSize, physical = physical)
    }

    private fun vertical(
        anchor: IntRect,
        window: IntSize,
        size: IntSize,
        physical: Physical,
        isRtl: Boolean,
    ): IntOffset {
        val below = anchor.bottom + sideOffset
        val above = anchor.top - sideOffset - size.height
        val y = flipped(
            preferred = below.takeIf { physical == Physical.Bottom } ?: above,
            other = below.takeUnless { physical == Physical.Bottom } ?: above,
            extent = size.height,
            limit = window.height,
        )
        val x = alignedStart(start = anchor.left, end = anchor.right, extent = size.width, isReversed = isRtl)
        return IntOffset(x = clamped(x, size.width, window.width), y = y)
    }

    private fun horizontal(anchor: IntRect, window: IntSize, size: IntSize, physical: Physical): IntOffset {
        val right = anchor.right + sideOffset
        val left = anchor.left - sideOffset - size.width
        val x = flipped(
            preferred = right.takeIf { physical == Physical.Right } ?: left,
            other = left.takeIf { physical == Physical.Right } ?: right,
            extent = size.width,
            limit = window.width,
        )
        val y = alignedStart(start = anchor.top, end = anchor.bottom, extent = size.height, isReversed = false)
        return IntOffset(x = x, y = clamped(y, size.height, window.height))
    }

    private fun alignedStart(start: Int, end: Int, extent: Int, isReversed: Boolean): Int {
        val effective = mirroredAlignOf(align = align, isReversed = isReversed)
        return when (effective) {
            Align.Start -> start + alignOffset
            Align.Center -> (start + end - extent) / 2 + alignOffset
            Align.End -> end - extent - alignOffset
        }
    }

    private fun flipped(preferred: Int, other: Int, extent: Int, limit: Int): Int {
        val fits = { position: Int -> position >= padding && position + extent <= limit - padding }
        if (fits(preferred) || !fits(other)) return preferred
        return other
    }

    private fun clamped(position: Int, extent: Int, limit: Int): Int =
        position.coerceIn(padding, maxOf(limit - extent - padding, padding))
}
