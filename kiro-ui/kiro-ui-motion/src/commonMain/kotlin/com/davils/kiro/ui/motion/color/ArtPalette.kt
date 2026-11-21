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

package com.davils.kiro.ui.motion.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The colors generative art draws with, derived from a theme by an [ArtPalettes] or chosen freely.
 *
 * @property background the color behind the art.
 * @property ink the color of lines and marks that should read clearly against [background].
 * @property accents the colors of shapes, from light to dark or in the order they take turns.
 * @property highlight the color of the brightest details, such as a glow or the head of a trail.
 * @property shade the color that darkens shapes, such as the sides of a block.
 * @since 1.0.0
 */
@Immutable
public class ArtPalette(
    public val background: Color,
    public val ink: Color,
    public val accents: List<Color>,
    public val highlight: Color,
    public val shade: Color,
) {
    /**
     * Returns a copy of this [ArtPalette] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param ink the new value of [ink].
     * @param accents the new value of [accents].
     * @param highlight the new value of [highlight].
     * @param shade the new value of [shade].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        background: Color = this.background,
        ink: Color = this.ink,
        accents: List<Color> = this.accents,
        highlight: Color = this.highlight,
        shade: Color = this.shade,
    ): ArtPalette = ArtPalette(
        background = background,
        ink = ink,
        accents = accents,
        highlight = highlight,
        shade = shade,
    )

    override fun equals(other: Any?): Boolean = other is ArtPalette && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ArtPalette(" +
        "background=$background, " +
        "ink=$ink, " +
        "accents=$accents, " +
        "highlight=$highlight, " +
        "shade=$shade)"

    private fun values(): List<Any?> = listOf(
        background,
        ink,
        accents,
        highlight,
        shade,
    )

    /**
     * Returns the accent at an index, counted around the accents so that every index has one.
     *
     * @param index the index, which may be larger than the number of accents or negative.
     * @return the accent.
     * @since 1.0.0
     */
    public fun accent(index: Int): Color {
        if (accents.isEmpty()) return ink
        return accents[index.mod(accents.size)]
    }
}
