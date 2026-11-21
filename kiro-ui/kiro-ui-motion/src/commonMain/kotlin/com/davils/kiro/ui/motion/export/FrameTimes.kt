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

package com.davils.kiro.ui.motion.export

import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds

private const val NANOS_PER_SECOND: Long = 1_000_000_000L

internal fun frameTimes(from: Duration, to: Duration, framesPerSecond: Int): Sequence<Duration> {
    require(framesPerSecond > 0) { "The frame rate must be positive, but was $framesPerSecond." }
    require(to >= from) { "The end $to must not be before the start $from." }
    val start = from.inWholeNanoseconds
    val count = (to - from).inWholeNanoseconds * framesPerSecond / NANOS_PER_SECOND
    return (0L until count).asSequence().map { index ->
        (start + index * NANOS_PER_SECOND / framesPerSecond).nanoseconds
    }
}
