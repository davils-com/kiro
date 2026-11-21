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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [FlickeringGridArt] looks: a field of small squares that flicker between a few faint levels of alpha, now and
 * then in an accent color, shaped by slowly drifting clouds that make some areas a little brighter.
 *
 * @property square the size of a square.
 * @property gap the space between two squares.
 * @property levels how many levels of alpha a square flickers between; at least `1`.
 * @property minAlpha the alpha of the faintest level.
 * @property maxAlpha the alpha of the brightest level.
 * @property period how long a square holds its level, on average.
 * @property structure how much the drifting clouds shape the levels against pure flicker, between `0` and `1`.
 * @property fieldSize the size of a cloud.
 * @property drift how long the clouds take to drift by one [fieldSize].
 * @property accentShare the share of squares drawn in the accent color, between `0` and `1`.
 * @property maxCells the most squares at the highest quality, fewer at lower ones; beyond it the squares grow coarser.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property color the color of the squares, or `null` for the ink of the palette.
 * @property accent the color of the accent squares, or `null` for the highlight of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class FlickeringGridArtStyle(
    public val square: Dp,
    public val gap: Dp,
    public val levels: Int,
    public val minAlpha: Float,
    public val maxAlpha: Float,
    public val period: Duration,
    public val structure: Float,
    public val fieldSize: Dp,
    public val drift: Duration,
    public val accentShare: Float,
    public val maxCells: Int,
    public val salt: Int,
    public val color: Color?,
    public val accent: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [FlickeringGridArtStyle] with the given values replaced and every other value kept.
     *
     * @param square the new value of [square].
     * @param gap the new value of [gap].
     * @param levels the new value of [levels].
     * @param minAlpha the new value of [minAlpha].
     * @param maxAlpha the new value of [maxAlpha].
     * @param period the new value of [period].
     * @param structure the new value of [structure].
     * @param fieldSize the new value of [fieldSize].
     * @param drift the new value of [drift].
     * @param accentShare the new value of [accentShare].
     * @param maxCells the new value of [maxCells].
     * @param salt the new value of [salt].
     * @param color the new value of [color].
     * @param accent the new value of [accent].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        square: Dp = this.square,
        gap: Dp = this.gap,
        levels: Int = this.levels,
        minAlpha: Float = this.minAlpha,
        maxAlpha: Float = this.maxAlpha,
        period: Duration = this.period,
        structure: Float = this.structure,
        fieldSize: Dp = this.fieldSize,
        drift: Duration = this.drift,
        accentShare: Float = this.accentShare,
        maxCells: Int = this.maxCells,
        salt: Int = this.salt,
        color: Color? = this.color,
        accent: Color? = this.accent,
        background: Color? = this.background,
    ): FlickeringGridArtStyle = FlickeringGridArtStyle(
        square = square,
        gap = gap,
        levels = levels,
        minAlpha = minAlpha,
        maxAlpha = maxAlpha,
        period = period,
        structure = structure,
        fieldSize = fieldSize,
        drift = drift,
        accentShare = accentShare,
        maxCells = maxCells,
        salt = salt,
        color = color,
        accent = accent,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is FlickeringGridArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FlickeringGridArtStyle(" +
        "square=$square, " +
        "gap=$gap, " +
        "levels=$levels, " +
        "minAlpha=$minAlpha, " +
        "maxAlpha=$maxAlpha, " +
        "period=$period, " +
        "structure=$structure, " +
        "fieldSize=$fieldSize, " +
        "drift=$drift, " +
        "accentShare=$accentShare, " +
        "maxCells=$maxCells, " +
        "salt=$salt, " +
        "color=${color ?: "null"}, " +
        "accent=${accent ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        square,
        gap,
        levels,
        minAlpha,
        maxAlpha,
        period,
        structure,
        fieldSize,
        drift,
        accentShare,
        maxCells,
        salt,
        color,
        accent,
        background,
    )
}
