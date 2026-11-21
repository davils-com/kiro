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

package com.davils.kiro.ui.motion.typewriter

internal class TypewriterTimeline(
    val marks: List<TypewriterMark>,
    val duration: Long,
    val isRepeating: Boolean,
    val resting: String,
) {
    private val times: LongArray = LongArray(marks.size) { index -> marks[index].at }

    fun positionOf(elapsed: Long): Long {
        if (isRepeating && duration > 0L) return elapsed.mod(duration)
        return elapsed.coerceAtLeast(minimumValue = 0L)
    }

    fun markAt(position: Long): TypewriterMark {
        var low = 0
        var high = times.size - 1
        while (low < high) {
            val middle = (low + high + 1) / 2
            if (times[middle] <= position) low = middle
            if (times[middle] > position) high = middle - 1
        }
        return marks[low]
    }

    fun isFinished(elapsed: Long): Boolean = !isRepeating && elapsed >= duration
}
