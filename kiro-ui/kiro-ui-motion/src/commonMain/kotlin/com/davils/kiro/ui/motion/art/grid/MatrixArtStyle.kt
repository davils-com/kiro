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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [MatrixArt] looks: columns of glyphs that rain down slowly, each drop led by a brighter glyph and followed by
 * a trail that fades from the strongest to the lightest color. Without a text measurer, small bars stand in for
 * the glyphs.
 *
 * @property cell the width of a column and the height of a row.
 * @property textStyle the style of the glyphs, whose color the art replaces.
 * @property glyphs the glyphs the rain picks from; an empty text draws bars.
 * @property columnShare the share of columns that carry rain at the highest quality, between `0` and `1`.
 * @property trail how many glyphs follow the head of a drop.
 * @property pause how many empty rows follow a drop before the next one.
 * @property period how long a drop takes to fall by one row at its average speed.
 * @property speedSpread how much the speeds of the columns differ, as a fraction of the average speed.
 * @property changePeriod how long a glyph stays before it changes, on average.
 * @property trailAlpha the alpha of the glyph right behind the head; the trail fades from there.
 * @property headAlpha the alpha of the head of a drop.
 * @property glyphWidth the width of a bar glyph, as a fraction of [cell].
 * @property glyphHeight the height of a bar glyph, as a fraction of [cell].
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of a trail from its tail to its head, or `null` for the accents of the palette.
 * @property head the color of the head of a drop, or `null` for the highlight of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class MatrixArtStyle(
    public val cell: Dp,
    public val textStyle: TextStyle,
    public val glyphs: String,
    public val columnShare: Float,
    public val trail: Int,
    public val pause: Int,
    public val period: Duration,
    public val speedSpread: Float,
    public val changePeriod: Duration,
    public val trailAlpha: Float,
    public val headAlpha: Float,
    public val glyphWidth: Float,
    public val glyphHeight: Float,
    public val salt: Int,
    public val colors: List<Color>?,
    public val head: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [MatrixArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param textStyle the new value of [textStyle].
     * @param glyphs the new value of [glyphs].
     * @param columnShare the new value of [columnShare].
     * @param trail the new value of [trail].
     * @param pause the new value of [pause].
     * @param period the new value of [period].
     * @param speedSpread the new value of [speedSpread].
     * @param changePeriod the new value of [changePeriod].
     * @param trailAlpha the new value of [trailAlpha].
     * @param headAlpha the new value of [headAlpha].
     * @param glyphWidth the new value of [glyphWidth].
     * @param glyphHeight the new value of [glyphHeight].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param head the new value of [head].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        textStyle: TextStyle = this.textStyle,
        glyphs: String = this.glyphs,
        columnShare: Float = this.columnShare,
        trail: Int = this.trail,
        pause: Int = this.pause,
        period: Duration = this.period,
        speedSpread: Float = this.speedSpread,
        changePeriod: Duration = this.changePeriod,
        trailAlpha: Float = this.trailAlpha,
        headAlpha: Float = this.headAlpha,
        glyphWidth: Float = this.glyphWidth,
        glyphHeight: Float = this.glyphHeight,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        head: Color? = this.head,
        background: Color? = this.background,
    ): MatrixArtStyle = MatrixArtStyle(
        cell = cell,
        textStyle = textStyle,
        glyphs = glyphs,
        columnShare = columnShare,
        trail = trail,
        pause = pause,
        period = period,
        speedSpread = speedSpread,
        changePeriod = changePeriod,
        trailAlpha = trailAlpha,
        headAlpha = headAlpha,
        glyphWidth = glyphWidth,
        glyphHeight = glyphHeight,
        salt = salt,
        colors = colors,
        head = head,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is MatrixArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MatrixArtStyle(" +
        "cell=$cell, " +
        "textStyle=$textStyle, " +
        "glyphs=$glyphs, " +
        "columnShare=$columnShare, " +
        "trail=$trail, " +
        "pause=$pause, " +
        "period=$period, " +
        "speedSpread=$speedSpread, " +
        "changePeriod=$changePeriod, " +
        "trailAlpha=$trailAlpha, " +
        "headAlpha=$headAlpha, " +
        "glyphWidth=$glyphWidth, " +
        "glyphHeight=$glyphHeight, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "head=${head ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        textStyle,
        glyphs,
        columnShare,
        trail,
        pause,
        period,
        speedSpread,
        changePeriod,
        trailAlpha,
        headAlpha,
        glyphWidth,
        glyphHeight,
        salt,
        colors,
        head,
        background,
    )
}
