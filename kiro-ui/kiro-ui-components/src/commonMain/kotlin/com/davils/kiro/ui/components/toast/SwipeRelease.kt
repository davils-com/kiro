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

import androidx.compose.runtime.Immutable
import kotlin.math.abs
import kotlin.math.sign

private const val FLING: Float = 1200f
private const val FLY_OUT: Float = 1.2f

@Immutable
internal data class SwipeRelease(
    val swiped: Float,
    val shown: Float,
    val velocity: Float,
    val width: Float,
    val threshold: Float,
) {
    val isGone: Boolean
        get() {
            val isFar = abs(shown) > width * threshold
            val isFlung = abs(velocity) > FLING && velocity.sign == shown.sign && shown != 0f
            return isFar || isFlung
        }

    val target: Float
        get() = (swiped.sign * width * FLY_OUT).takeIf { isGone } ?: 0f
}
