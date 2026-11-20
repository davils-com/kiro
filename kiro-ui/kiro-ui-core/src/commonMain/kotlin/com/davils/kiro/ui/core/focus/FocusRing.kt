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

package com.davils.kiro.ui.core.focus

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The focus ring that controls show while they hold the focus of the keyboard.
 *
 * @property width the width of the ring.
 * @property alpha the alpha of the ring color, from 0 for invisible to 1 for opaque.
 * @since 0.1.0
 */
@Immutable
public class FocusRing(
    public val width: Dp = 3.dp,
    public val alpha: Float = 0.5f,
) {
    /**
     * Returns a copy of this [FocusRing] with the given values replaced and every other value kept.
     *
     * @param width the new value of [width].
     * @param alpha the new value of [alpha].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(width: Dp = this.width, alpha: Float = this.alpha): FocusRing = FocusRing(
        width = width,
        alpha = alpha,
    )

    override fun equals(other: Any?): Boolean = other is FocusRing && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FocusRing(" +
        "width=$width, " +
        "alpha=$alpha)"

    private fun values(): List<Any?> = listOf(
        width,
        alpha,
    )
}
