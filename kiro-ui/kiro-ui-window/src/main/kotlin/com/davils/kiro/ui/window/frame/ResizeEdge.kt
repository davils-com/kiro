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

package com.davils.kiro.ui.window.frame

import androidx.compose.ui.Alignment
import java.awt.Cursor

internal enum class ResizeEdge(
    val isLeft: Boolean,
    val isTop: Boolean,
    val isRight: Boolean,
    val isBottom: Boolean,
    val zone: ResizeZone,
    val cursor: Int,
    val alignment: Alignment,
) {
    North(
        isLeft = false,
        isTop = true,
        isRight = false,
        isBottom = false,
        zone = ResizeZone.Row,
        cursor = Cursor.N_RESIZE_CURSOR,
        alignment = Alignment.TopCenter,
    ),
    South(
        isLeft = false,
        isTop = false,
        isRight = false,
        isBottom = true,
        zone = ResizeZone.Row,
        cursor = Cursor.S_RESIZE_CURSOR,
        alignment = Alignment.BottomCenter,
    ),
    West(
        isLeft = true,
        isTop = false,
        isRight = false,
        isBottom = false,
        zone = ResizeZone.Column,
        cursor = Cursor.W_RESIZE_CURSOR,
        alignment = Alignment.CenterStart,
    ),
    East(
        isLeft = false,
        isTop = false,
        isRight = true,
        isBottom = false,
        zone = ResizeZone.Column,
        cursor = Cursor.E_RESIZE_CURSOR,
        alignment = Alignment.CenterEnd,
    ),
    NorthWest(
        isLeft = true,
        isTop = true,
        isRight = false,
        isBottom = false,
        zone = ResizeZone.Corner,
        cursor = Cursor.NW_RESIZE_CURSOR,
        alignment = Alignment.TopStart,
    ),
    NorthEast(
        isLeft = false,
        isTop = true,
        isRight = true,
        isBottom = false,
        zone = ResizeZone.Corner,
        cursor = Cursor.NE_RESIZE_CURSOR,
        alignment = Alignment.TopEnd,
    ),
    SouthWest(
        isLeft = true,
        isTop = false,
        isRight = false,
        isBottom = true,
        zone = ResizeZone.Corner,
        cursor = Cursor.SW_RESIZE_CURSOR,
        alignment = Alignment.BottomStart,
    ),
    SouthEast(
        isLeft = false,
        isTop = false,
        isRight = true,
        isBottom = true,
        zone = ResizeZone.Corner,
        cursor = Cursor.SE_RESIZE_CURSOR,
        alignment = Alignment.BottomEnd,
    ),
}
