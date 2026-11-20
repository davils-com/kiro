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

package com.davils.kiro.ui.components.drawer

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animate
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import kotlin.math.abs

@Stable
internal class DrawerSnaps(private val points: List<Float>) {
    init {
        require(points.isNotEmpty()) { "A drawer needs at least one snap point." }
    }

    var available: Float by mutableFloatStateOf(0f)
        private set

    private var height: Float by mutableFloatStateOf(Float.NaN)

    val shown: Float
        get() = height.takeUnless { it.isNaN() } ?: points.first() * available

    fun measure(space: Float) {
        if (space != available) available = space
    }

    fun drag(by: Float) {
        height = (shown + by).coerceIn(0f, available)
    }

    fun targetOf(dismissFraction: Float): Float? {
        val first = points.first() * available
        if (shown < first * (1f - dismissFraction)) return null
        return points.map { point -> point * available }.minBy { stop -> abs(stop - shown) }
    }

    suspend fun settle(target: Float, spec: FiniteAnimationSpec<Float>) {
        animate(initialValue = shown, targetValue = target, animationSpec = spec) { value, _ -> height = value }
    }
}
