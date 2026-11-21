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

package com.davils.kiro.ui.motion.art.tiling

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [BauhausArt] looks: a grid of colored tiles, each with one bold shape in a contrasting color, such as a
 * quarter circle, a half circle, a ring, a triangle or stripes, and tiles that turn by a quarter now and then.
 *
 * @property cell the size of a tile.
 * @property gap the space between two tiles.
 * @property radius the corner radius of a tile.
 * @property weights how often each shape appears, relative to the others; a missing shape never appears.
 * @property circle the radius of a [BauhausShape.Circle], as a fraction of [cell].
 * @property ring the radius of a [BauhausShape.Ring], as a fraction of [cell].
 * @property ringWidth the width of a [BauhausShape.Ring], as a fraction of [cell].
 * @property dot the radius of a [BauhausShape.Dot], as a fraction of [cell].
 * @property stripes the number of bars of [BauhausShape.Stripes].
 * @property stripeWidth the width of a bar of [BauhausShape.Stripes], as a fraction of [cell].
 * @property tileTint how far a tile leans from the background to its color, between `0` and `1`.
 * @property shapeTint how far a quiet shape leans from the background to its color, between `0` and `1`.
 * @property accentShare the share of tiles whose shape shows its color in full.
 * @property outlineWidth the width of the border of a tile.
 * @property outlineAlpha the alpha of the border of a tile; `0` hides it.
 * @property period the time between two turns of a tile.
 * @property stagger how far apart the turns of the tiles are spread, as a fraction of [period].
 * @property turn how much of [period] a turn takes, between `0` and `1`.
 * @property pop how far a tile shrinks in the middle of a turn, as a fraction of its size.
 * @property easing how a turn speeds up and slows down.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property outline the color of the borders of the tiles, or `null` for the ink of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class BauhausArtStyle(
    public val cell: Dp,
    public val gap: Dp,
    public val radius: Dp,
    public val weights: Map<BauhausShape, Float>,
    public val circle: Float,
    public val ring: Float,
    public val ringWidth: Float,
    public val dot: Float,
    public val stripes: Int,
    public val stripeWidth: Float,
    public val tileTint: Float,
    public val shapeTint: Float,
    public val accentShare: Float,
    public val outlineWidth: Dp,
    public val outlineAlpha: Float,
    public val period: Duration,
    public val stagger: Float,
    public val turn: Float,
    public val pop: Float,
    public val easing: Easing,
    public val salt: Int,
    public val colors: List<Color>?,
    public val outline: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [BauhausArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param gap the new value of [gap].
     * @param radius the new value of [radius].
     * @param weights the new value of [weights].
     * @param circle the new value of [circle].
     * @param ring the new value of [ring].
     * @param ringWidth the new value of [ringWidth].
     * @param dot the new value of [dot].
     * @param stripes the new value of [stripes].
     * @param stripeWidth the new value of [stripeWidth].
     * @param tileTint the new value of [tileTint].
     * @param shapeTint the new value of [shapeTint].
     * @param accentShare the new value of [accentShare].
     * @param outlineWidth the new value of [outlineWidth].
     * @param outlineAlpha the new value of [outlineAlpha].
     * @param period the new value of [period].
     * @param stagger the new value of [stagger].
     * @param turn the new value of [turn].
     * @param pop the new value of [pop].
     * @param easing the new value of [easing].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param outline the new value of [outline].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        gap: Dp = this.gap,
        radius: Dp = this.radius,
        weights: Map<BauhausShape, Float> = this.weights,
        circle: Float = this.circle,
        ring: Float = this.ring,
        ringWidth: Float = this.ringWidth,
        dot: Float = this.dot,
        stripes: Int = this.stripes,
        stripeWidth: Float = this.stripeWidth,
        tileTint: Float = this.tileTint,
        shapeTint: Float = this.shapeTint,
        accentShare: Float = this.accentShare,
        outlineWidth: Dp = this.outlineWidth,
        outlineAlpha: Float = this.outlineAlpha,
        period: Duration = this.period,
        stagger: Float = this.stagger,
        turn: Float = this.turn,
        pop: Float = this.pop,
        easing: Easing = this.easing,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        outline: Color? = this.outline,
        background: Color? = this.background,
    ): BauhausArtStyle = BauhausArtStyle(
        cell = cell,
        gap = gap,
        radius = radius,
        weights = weights,
        circle = circle,
        ring = ring,
        ringWidth = ringWidth,
        dot = dot,
        stripes = stripes,
        stripeWidth = stripeWidth,
        tileTint = tileTint,
        shapeTint = shapeTint,
        accentShare = accentShare,
        outlineWidth = outlineWidth,
        outlineAlpha = outlineAlpha,
        period = period,
        stagger = stagger,
        turn = turn,
        pop = pop,
        easing = easing,
        salt = salt,
        colors = colors,
        outline = outline,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is BauhausArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BauhausArtStyle(" +
        "cell=$cell, " +
        "gap=$gap, " +
        "radius=$radius, " +
        "weights=$weights, " +
        "circle=$circle, " +
        "ring=$ring, " +
        "ringWidth=$ringWidth, " +
        "dot=$dot, " +
        "stripes=$stripes, " +
        "stripeWidth=$stripeWidth, " +
        "tileTint=$tileTint, " +
        "shapeTint=$shapeTint, " +
        "accentShare=$accentShare, " +
        "outlineWidth=$outlineWidth, " +
        "outlineAlpha=$outlineAlpha, " +
        "period=$period, " +
        "stagger=$stagger, " +
        "turn=$turn, " +
        "pop=$pop, " +
        "easing=$easing, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "outline=${outline ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        gap,
        radius,
        weights,
        circle,
        ring,
        ringWidth,
        dot,
        stripes,
        stripeWidth,
        tileTint,
        shapeTint,
        accentShare,
        outlineWidth,
        outlineAlpha,
        period,
        stagger,
        turn,
        pop,
        easing,
        salt,
        colors,
        outline,
        background,
    )
}
