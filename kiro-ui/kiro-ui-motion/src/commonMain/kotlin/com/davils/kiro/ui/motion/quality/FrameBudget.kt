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

package com.davils.kiro.ui.motion.quality

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.time.Duration

internal class FrameBudget(private val policy: QualityPolicy.Adaptive, floor: Duration = Duration.ZERO) {
    private val lowest: QualityLevel = minOf(a = policy.minimum, b = policy.maximum)
    private val highest: QualityLevel = maxOf(a = policy.minimum, b = policy.maximum)
    private val budget: Double = maxOf(a = policy.budget, b = floor).inWholeNanoseconds.toDouble()
    private val patience: Int = policy.patience.coerceAtLeast(minimumValue = 1)
    private val smoothing: Double = policy.smoothing.coerceIn(minimumValue = 0f, maximumValue = 1f).toDouble()
    private var average: Double = Double.NaN
    private var over: Int = 0
    private var under: Int = 0

    var level: QualityLevel by mutableStateOf(policy.initial.coerceIn(minimumValue = lowest, maximumValue = highest))
        private set

    val averageNanos: Double
        get() = average

    fun record(frameNanos: Long): QualityLevel {
        average = averageOf(sample = frameNanos.toDouble())
        over = countOf(isHit = average > budget, count = over)
        under = countOf(isHit = average < budget * policy.headroom, count = under)
        if (over >= patience) step(by = -1)
        if (under >= patience) step(by = 1)
        return level
    }

    private fun averageOf(sample: Double): Double {
        if (average.isNaN()) return sample
        return average + (sample - average) * smoothing
    }

    private fun step(by: Int) {
        over = 0
        under = 0
        val levels = QualityLevel.entries
        val index = (level.ordinal + by).coerceIn(minimumValue = 0, maximumValue = levels.lastIndex)
        level = levels[index].coerceIn(minimumValue = lowest, maximumValue = highest)
    }
}

private fun countOf(isHit: Boolean, count: Int): Int {
    if (!isHit) return 0
    return count + 1
}

internal fun frameBudgetOf(policy: QualityPolicy, floor: Duration): FrameBudget = when (policy) {
    is QualityPolicy.Adaptive -> FrameBudget(policy = policy, floor = floor)
    is QualityPolicy.Fixed -> FrameBudget(policy = fixedPolicyOf(policy.level), floor = floor)
}

private fun fixedPolicyOf(level: QualityLevel): QualityPolicy.Adaptive = QualityPolicy.Adaptive(
    budget = Duration.ZERO,
    initial = level,
    minimum = level,
    maximum = level,
    headroom = 0f,
    smoothing = 0f,
    patience = 1,
)
