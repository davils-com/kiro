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

internal fun physicalSideOf(side: Side, isRtl: Boolean): Physical = when (side) {
    Side.Top -> Physical.Top
    Side.Bottom -> Physical.Bottom
    Side.Start -> Physical.Right.takeIf { isRtl } ?: Physical.Left
    Side.End -> Physical.Left.takeIf { isRtl } ?: Physical.Right
}

internal fun mirroredAlignOf(align: Align, isReversed: Boolean): Align {
    if (!isReversed) return align
    return mirrors[align] ?: align
}

private val mirrors: Map<Align, Align> = mapOf(
    Align.Start to Align.End,
    Align.End to Align.Start,
)
