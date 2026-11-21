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

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [GridPatternArt] looks: a quiet grid of hairlines, dots or small crosses, a few cells that softly light up
 * and fade again, and the cell under the pointer lit as well.
 *
 * @property kind whether the grid draws lines, dots or crosses.
 * @property cell the size of a cell.
 * @property lineWidth the width of a line, and of the arms of a cross.
 * @property mark the radius of a dot, or half the length of a cross.
 * @property lineAlpha the alpha of the lines, the dots or the crosses.
 * @property majorEvery every how many lines, counted from the middle, one is drawn stronger; `0` draws none stronger.
 * @property majorAlpha the alpha of the stronger lines, dots or crosses.
 * @property highlights how many cells are lit at the same time at the highest quality.
 * @property highlightAlpha the alpha of a lit cell at its brightest.
 * @property radius the corner radius of a lit cell.
 * @property inset the space between a lit cell and its lines.
 * @property period how long a cell takes to light up and fade again.
 * @property easing how a cell lights up and fades.
 * @property pointerAlpha the alpha of the cell under the pointer.
 * @property pointerOutlineAlpha the alpha of the outline of the cell under the pointer; `0` hides it.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the lit cells, used in turn, or `null` for the accents of the palette of the frame.
 * @property line the color of the lines, the dots or the crosses, or `null` for the ink of the palette.
 * @property pointer the color of the cell under the pointer, or `null` for the highlight of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class GridPatternArtStyle(
    public val kind: GridPatternKind,
    public val cell: Dp,
    public val lineWidth: Dp,
    public val mark: Dp,
    public val lineAlpha: Float,
    public val majorEvery: Int,
    public val majorAlpha: Float,
    public val highlights: Int,
    public val highlightAlpha: Float,
    public val radius: Dp,
    public val inset: Dp,
    public val period: Duration,
    public val easing: Easing,
    public val pointerAlpha: Float,
    public val pointerOutlineAlpha: Float,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val pointer: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [GridPatternArtStyle] with the given values replaced and every other value kept.
     *
     * @param kind the new value of [kind].
     * @param cell the new value of [cell].
     * @param lineWidth the new value of [lineWidth].
     * @param mark the new value of [mark].
     * @param lineAlpha the new value of [lineAlpha].
     * @param majorEvery the new value of [majorEvery].
     * @param majorAlpha the new value of [majorAlpha].
     * @param highlights the new value of [highlights].
     * @param highlightAlpha the new value of [highlightAlpha].
     * @param radius the new value of [radius].
     * @param inset the new value of [inset].
     * @param period the new value of [period].
     * @param easing the new value of [easing].
     * @param pointerAlpha the new value of [pointerAlpha].
     * @param pointerOutlineAlpha the new value of [pointerOutlineAlpha].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param pointer the new value of [pointer].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        kind: GridPatternKind = this.kind,
        cell: Dp = this.cell,
        lineWidth: Dp = this.lineWidth,
        mark: Dp = this.mark,
        lineAlpha: Float = this.lineAlpha,
        majorEvery: Int = this.majorEvery,
        majorAlpha: Float = this.majorAlpha,
        highlights: Int = this.highlights,
        highlightAlpha: Float = this.highlightAlpha,
        radius: Dp = this.radius,
        inset: Dp = this.inset,
        period: Duration = this.period,
        easing: Easing = this.easing,
        pointerAlpha: Float = this.pointerAlpha,
        pointerOutlineAlpha: Float = this.pointerOutlineAlpha,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        pointer: Color? = this.pointer,
        background: Color? = this.background,
    ): GridPatternArtStyle = GridPatternArtStyle(
        kind = kind,
        cell = cell,
        lineWidth = lineWidth,
        mark = mark,
        lineAlpha = lineAlpha,
        majorEvery = majorEvery,
        majorAlpha = majorAlpha,
        highlights = highlights,
        highlightAlpha = highlightAlpha,
        radius = radius,
        inset = inset,
        period = period,
        easing = easing,
        pointerAlpha = pointerAlpha,
        pointerOutlineAlpha = pointerOutlineAlpha,
        salt = salt,
        colors = colors,
        line = line,
        pointer = pointer,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is GridPatternArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GridPatternArtStyle(" +
        "kind=$kind, " +
        "cell=$cell, " +
        "lineWidth=$lineWidth, " +
        "mark=$mark, " +
        "lineAlpha=$lineAlpha, " +
        "majorEvery=$majorEvery, " +
        "majorAlpha=$majorAlpha, " +
        "highlights=$highlights, " +
        "highlightAlpha=$highlightAlpha, " +
        "radius=$radius, " +
        "inset=$inset, " +
        "period=$period, " +
        "easing=$easing, " +
        "pointerAlpha=$pointerAlpha, " +
        "pointerOutlineAlpha=$pointerOutlineAlpha, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "pointer=${pointer ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        kind,
        cell,
        lineWidth,
        mark,
        lineAlpha,
        majorEvery,
        majorAlpha,
        highlights,
        highlightAlpha,
        radius,
        inset,
        period,
        easing,
        pointerAlpha,
        pointerOutlineAlpha,
        salt,
        colors,
        line,
        pointer,
        background,
    )
}
