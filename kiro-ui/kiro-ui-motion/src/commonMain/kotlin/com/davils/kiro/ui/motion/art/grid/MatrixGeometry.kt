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

package com.davils.kiro.ui.motion.art.grid

private const val SLOWEST: Float = 0.1f
private const val GOLDEN: Double = 0.618_033_988_749_895

internal fun matrixSpeedOf(unit: Float, spread: Float): Float =
    (1f + spread * (unit * 2f - 1f)).coerceAtLeast(minimumValue = SLOWEST)

internal fun matrixHeadOf(phase: Double, offset: Float, span: Int): Float = ((phase + offset).mod(1.0) * span).toFloat()

internal fun matrixFadeOf(step: Int, trail: Int): Float {
    val rest = 1f - step.toFloat() / (trail + 1).coerceAtLeast(minimumValue = 1)
    return rest * rest
}

internal fun matrixShareOf(column: Int, start: Float): Float = (start + column * GOLDEN).mod(1.0).toFloat()
