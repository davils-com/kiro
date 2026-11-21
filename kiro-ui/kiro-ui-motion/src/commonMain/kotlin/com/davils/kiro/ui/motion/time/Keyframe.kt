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

package com.davils.kiro.ui.motion.time

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.runtime.Immutable
import kotlin.time.Duration

/**
 * A value at a point in time of [Keyframes], and how the way to the next keyframe is eased.
 *
 * @param T the type of the value.
 * @property time the point in time of the keyframe.
 * @property value the value at [time].
 * @property easing the easing of the way from this keyframe to the next one.
 * @since 1.0.0
 */
@Immutable
public class Keyframe<T>(
    public val time: Duration,
    public val value: T,
    public val easing: Easing = LinearEasing,
) {
    /**
     * Returns a copy of this [Keyframe] with the given values replaced and every other value kept.
     *
     * @param time the new value of [time].
     * @param value the new value of [value].
     * @param easing the new value of [easing].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(time: Duration = this.time, value: T = this.value, easing: Easing = this.easing): Keyframe<T> =
        Keyframe(time = time, value = value, easing = easing)

    override fun equals(other: Any?): Boolean =
        other is Keyframe<*> && time == other.time && value == other.value && easing == other.easing

    override fun hashCode(): Int = listOf(time, value, easing).hashCode()

    override fun toString(): String = "Keyframe(time=$time, value=${value ?: "null"}, easing=$easing)"
}
