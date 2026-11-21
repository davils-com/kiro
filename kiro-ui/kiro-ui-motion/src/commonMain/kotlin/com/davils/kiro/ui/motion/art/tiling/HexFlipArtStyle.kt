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
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [HexFlipArt] looks: a field of hexagons that flip like cards in a slow ripple running out from an origin;
 * each one shows a quiet front or a tinted back with a fine inner outline, and every ripple turns over a few of
 * them.
 *
 * @property radius the distance from the middle of a hexagon to its corners.
 * @property gap the space between two hexagons.
 * @property outlineWidth the width of the outlines.
 * @property outlineAlpha the alpha of the outline of the place of every hexagon, which never moves.
 * @property frontAlpha the alpha of the front of a card.
 * @property backAlpha the alpha of the tinted back of a card.
 * @property edgeAlpha the alpha of the outline of the back of a card.
 * @property detail the size of the inner outline on the back of a card, as a fraction of the card, or 0 for none.
 * @property detailAlpha the alpha of the inner outline on the back of a card.
 * @property detailQuality the lowest quality that draws the inner outlines.
 * @property shading how much a card darkens while it stands on its edge.
 * @property share the share of cards that show their back after a ripple.
 * @property shades the number of shades the colors blend into from the origin outwards.
 * @property centerX the horizontal position of the origin of the ripples, as a fraction of the width.
 * @property centerY the vertical position of the origin of the ripples, as a fraction of the height.
 * @property spread how much of [period] a ripple takes from its origin to the farthest card.
 * @property turn how much of [period] the flip of a card takes.
 * @property period the time between two ripples.
 * @property easing the easing of a flip.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the backs, blended from the origin outwards, or `null` for the accents of the palette.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property shade the color that darkens a turning card, or `null` for the shade of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class HexFlipArtStyle(
    public val radius: Dp,
    public val gap: Dp,
    public val outlineWidth: Dp,
    public val outlineAlpha: Float,
    public val frontAlpha: Float,
    public val backAlpha: Float,
    public val edgeAlpha: Float,
    public val detail: Float,
    public val detailAlpha: Float,
    public val detailQuality: QualityLevel,
    public val shading: Float,
    public val share: Float,
    public val shades: Int,
    public val centerX: Float,
    public val centerY: Float,
    public val spread: Float,
    public val turn: Float,
    public val period: Duration,
    public val easing: Easing,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val shade: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [HexFlipArtStyle] with the given values replaced and every other value kept.
     *
     * @param radius the new value of [radius].
     * @param gap the new value of [gap].
     * @param outlineWidth the new value of [outlineWidth].
     * @param outlineAlpha the new value of [outlineAlpha].
     * @param frontAlpha the new value of [frontAlpha].
     * @param backAlpha the new value of [backAlpha].
     * @param edgeAlpha the new value of [edgeAlpha].
     * @param detail the new value of [detail].
     * @param detailAlpha the new value of [detailAlpha].
     * @param detailQuality the new value of [detailQuality].
     * @param shading the new value of [shading].
     * @param share the new value of [share].
     * @param shades the new value of [shades].
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param spread the new value of [spread].
     * @param turn the new value of [turn].
     * @param period the new value of [period].
     * @param easing the new value of [easing].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param shade the new value of [shade].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        radius: Dp = this.radius,
        gap: Dp = this.gap,
        outlineWidth: Dp = this.outlineWidth,
        outlineAlpha: Float = this.outlineAlpha,
        frontAlpha: Float = this.frontAlpha,
        backAlpha: Float = this.backAlpha,
        edgeAlpha: Float = this.edgeAlpha,
        detail: Float = this.detail,
        detailAlpha: Float = this.detailAlpha,
        detailQuality: QualityLevel = this.detailQuality,
        shading: Float = this.shading,
        share: Float = this.share,
        shades: Int = this.shades,
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        spread: Float = this.spread,
        turn: Float = this.turn,
        period: Duration = this.period,
        easing: Easing = this.easing,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        shade: Color? = this.shade,
        background: Color? = this.background,
    ): HexFlipArtStyle = HexFlipArtStyle(
        radius = radius,
        gap = gap,
        outlineWidth = outlineWidth,
        outlineAlpha = outlineAlpha,
        frontAlpha = frontAlpha,
        backAlpha = backAlpha,
        edgeAlpha = edgeAlpha,
        detail = detail,
        detailAlpha = detailAlpha,
        detailQuality = detailQuality,
        shading = shading,
        share = share,
        shades = shades,
        centerX = centerX,
        centerY = centerY,
        spread = spread,
        turn = turn,
        period = period,
        easing = easing,
        salt = salt,
        colors = colors,
        line = line,
        shade = shade,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is HexFlipArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "HexFlipArtStyle(" +
        "radius=$radius, " +
        "gap=$gap, " +
        "outlineWidth=$outlineWidth, " +
        "outlineAlpha=$outlineAlpha, " +
        "frontAlpha=$frontAlpha, " +
        "backAlpha=$backAlpha, " +
        "edgeAlpha=$edgeAlpha, " +
        "detail=$detail, " +
        "detailAlpha=$detailAlpha, " +
        "detailQuality=$detailQuality, " +
        "shading=$shading, " +
        "share=$share, " +
        "shades=$shades, " +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "spread=$spread, " +
        "turn=$turn, " +
        "period=$period, " +
        "easing=$easing, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "shade=${shade ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        radius,
        gap,
        outlineWidth,
        outlineAlpha,
        frontAlpha,
        backAlpha,
        edgeAlpha,
        detail,
        detailAlpha,
        detailQuality,
        shading,
        share,
        shades,
        centerX,
        centerY,
        spread,
        turn,
        period,
        easing,
        salt,
        colors,
        line,
        shade,
        background,
    )
}
