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

package com.davils.kiro.ui.motion.mask

import androidx.compose.runtime.Immutable

/**
 * A point along a [Mask] and how much the mask shows there.
 *
 * @property position the position along the mask, between `0` and `1`.
 * @property alpha how much shows at [position], between `0` (hidden) and `1` (shown).
 * @since 1.0.0
 */
@Immutable
public class MaskStop(public val position: Float, public val alpha: Float) {
    /**
     * Returns a copy of this stop with the given values replaced and every other value kept.
     *
     * @param position the new value of [position].
     * @param alpha the new value of [alpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(position: Float = this.position, alpha: Float = this.alpha): MaskStop =
        MaskStop(position = position, alpha = alpha)

    override fun equals(other: Any?): Boolean = other is MaskStop && position == other.position && alpha == other.alpha

    override fun hashCode(): Int = listOf(position, alpha).hashCode()

    override fun toString(): String = "MaskStop(position=$position, alpha=$alpha)"
}
