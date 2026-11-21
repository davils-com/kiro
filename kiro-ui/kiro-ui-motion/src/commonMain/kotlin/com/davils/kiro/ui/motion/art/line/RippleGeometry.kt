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

internal fun rippleFractionOf(phase: Double, index: Int, count: Int, offset: Float): Float =
    (phase + index.toDouble() / count.coerceAtLeast(minimumValue = 1) + offset).mod(1.0).toFloat()

internal fun rippleOriginOf(base: Offset, pointer: Offset?, follow: Float): Offset {
    if (pointer == null) return base
    val share = follow.coerceIn(minimumValue = 0f, maximumValue = 1f)
    return Offset(x = base.x + (pointer.x - base.x) * share, y = base.y + (pointer.y - base.y) * share)
}
