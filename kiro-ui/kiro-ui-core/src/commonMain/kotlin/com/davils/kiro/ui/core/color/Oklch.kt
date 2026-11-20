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

package com.davils.kiro.ui.core.color

import androidx.compose.runtime.Immutable

/**
 * A color in the OKLCH color space, in which equal steps of lightness look equally large.
 *
 * @property lightness the perceived lightness, from 0 for black to 1 for white.
 * @property chroma the colorfulness, from 0 for a gray upwards.
 * @property hue the hue angle in degrees, from 0 up to 360.
 * @property alpha the opacity, from 0 for transparent to 1 for opaque.
 * @since 1.0.0
 */
@Immutable
public class Oklch(
    public val lightness: Double,
    public val chroma: Double,
    public val hue: Double,
    public val alpha: Double = 1.0,
) {
    /**
     * Returns a copy of this [Oklch] with the given values replaced and every other value kept.
     *
     * @param lightness the new value of [lightness].
     * @param chroma the new value of [chroma].
     * @param hue the new value of [hue].
     * @param alpha the new value of [alpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        lightness: Double = this.lightness,
        chroma: Double = this.chroma,
        hue: Double = this.hue,
        alpha: Double = this.alpha,
    ): Oklch = Oklch(
        lightness = lightness,
        chroma = chroma,
        hue = hue,
        alpha = alpha,
    )

    override fun equals(other: Any?): Boolean = other is Oklch && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "Oklch(" +
        "lightness=$lightness, " +
        "chroma=$chroma, " +
        "hue=$hue, " +
        "alpha=$alpha)"

    private fun values(): List<Any?> = listOf(
        lightness,
        chroma,
        hue,
        alpha,
    )
}
