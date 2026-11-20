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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val GAP: Int = 4
private const val PADDING: Int = 5
private const val WINDOW_WIDTH: Int = 400
private const val WINDOW_HEIGHT: Int = 300
private const val MENU_WIDTH: Int = 80
private const val MENU_HEIGHT: Int = 100
private const val ANCHOR_WIDTH: Int = 100
private const val ANCHOR_HEIGHT: Int = 30
private const val EDGE: Int = 50

class AnchorPositionProviderTest : FunSpec({
    val window = IntSize(width = WINDOW_WIDTH, height = WINDOW_HEIGHT)
    val menu = IntSize(width = MENU_WIDTH, height = MENU_HEIGHT)

    fun anchorAt(left: Int, top: Int) =
        IntRect(left = left, top = top, right = left + ANCHOR_WIDTH, bottom = top + ANCHOR_HEIGHT)

    fun place(
        anchor: IntRect,
        side: Side = Side.Bottom,
        align: Align = Align.Start,
        direction: LayoutDirection = LayoutDirection.Ltr,
        size: IntSize = menu,
    ): IntOffset = AnchorPositionProvider(
        side = side,
        align = align,
        sideOffset = GAP,
        alignOffset = 0,
        padding = PADDING,
    ).calculatePosition(
        anchorBounds = anchor,
        windowSize = window,
        layoutDirection = direction,
        popupContentSize = size,
    )

    context("vertical sides") {
        test("open below the anchor aligned to its start, end or center") {
            val anchor = anchorAt(left = EDGE, top = EDGE)
            val below = anchor.bottom + GAP

            place(anchor) shouldBe IntOffset(x = EDGE, y = below)
            place(anchor, direction = LayoutDirection.Rtl) shouldBe IntOffset(x = anchor.right - MENU_WIDTH, y = below)
            place(anchor, align = Align.End) shouldBe IntOffset(x = anchor.right - MENU_WIDTH, y = below)
            place(anchor, align = Align.Center) shouldBe IntOffset(x = anchor.center.x - MENU_WIDTH / 2, y = below)
        }

        test("flip to the other side and stay inside the window") {
            val low = anchorAt(left = WINDOW_WIDTH - EDGE, top = WINDOW_HEIGHT - ANCHOR_HEIGHT * 2)
            val high = anchorAt(left = EDGE, top = PADDING)

            place(low) shouldBe IntOffset(x = WINDOW_WIDTH - MENU_WIDTH - PADDING, y = low.top - GAP - MENU_HEIGHT)
            place(high, side = Side.Top) shouldBe IntOffset(x = EDGE, y = high.bottom + GAP)
        }

        test("keep the preferred side when neither side has room") {
            val anchor = anchorAt(left = EDGE, top = EDGE)
            val tall = IntSize(width = MENU_WIDTH, height = WINDOW_HEIGHT)

            place(anchor, size = tall) shouldBe IntOffset(x = EDGE, y = anchor.bottom + GAP)
        }
    }

    context("horizontal sides") {
        test("open beside the anchor and mirror from right to left") {
            val anchor = anchorAt(left = EDGE * 2, top = EDGE)

            place(anchor, side = Side.End) shouldBe IntOffset(x = anchor.right + GAP, y = EDGE)
            place(anchor, side = Side.Start) shouldBe IntOffset(x = anchor.left - GAP - MENU_WIDTH, y = EDGE)
            place(anchor, side = Side.End, direction = LayoutDirection.Rtl) shouldBe
                IntOffset(x = anchor.left - GAP - MENU_WIDTH, y = EDGE)
            place(anchor, side = Side.Start, direction = LayoutDirection.Rtl) shouldBe
                IntOffset(x = anchor.right + GAP, y = EDGE)
        }
    }
})
