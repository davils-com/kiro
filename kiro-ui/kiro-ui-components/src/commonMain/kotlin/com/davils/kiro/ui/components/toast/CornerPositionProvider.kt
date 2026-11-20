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

package com.davils.kiro.ui.components.toast

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider

internal class CornerPositionProvider(
    private val position: ToastPosition,
    private val offset: Int,
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val start = offset
        val end = windowSize.width - popupContentSize.width - offset
        val center = (windowSize.width - popupContentSize.width) / 2
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val x = when (position) {
            ToastPosition.TopStart, ToastPosition.BottomStart -> end.takeIf { isRtl } ?: start
            ToastPosition.TopEnd, ToastPosition.BottomEnd -> start.takeIf { isRtl } ?: end
            ToastPosition.TopCenter, ToastPosition.BottomCenter -> center
        }
        val y = offset.takeIf { isTop(position) } ?: windowSize.height - popupContentSize.height - offset
        return IntOffset(x = x, y = y)
    }
}

internal fun isTop(position: ToastPosition): Boolean = position == ToastPosition.TopStart ||
    position == ToastPosition.TopCenter ||
    position == ToastPosition.TopEnd
