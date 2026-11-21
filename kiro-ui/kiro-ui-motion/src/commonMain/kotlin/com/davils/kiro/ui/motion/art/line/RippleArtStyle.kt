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

package com.davils.kiro.ui.motion.art.line

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [RippleArt] looks: rings that rise from an origin and widen until they fade, over a few faint circles that
 * stay still, with the origin following the pointer if asked to.
 *
 * @property originX the horizontal position of the origin, as a fraction of the width.
 * @property originY the vertical position of the origin, as a fraction of the height.
 * @property rings how many rings widen at the same time at the highest quality.
 * @property reach how far a ring widens, as a fraction of the distance from the middle to a corner.
 * @property lineWidth the width of a ring and of a still circle.
 * @property ringAlpha the alpha of a ring as it rises.
 * @property fillAlpha the alpha of the disc inside a ring as it rises; `0` leaves the rings open.
 * @property period how long a ring takes to widen and fade.
 * @property easing how a ring slows down as it widens.
 * @property guides how many still circles surround the origin.
 * @property guideSpacing the space between two still circles.
 * @property guideAlpha the alpha of the still circles; `0` hides them.
 * @property pointerFollow how far the origin of the rings moves to the pointer, between `0` and `1`.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the rings, used in turn, or `null` for the accents of the palette of the frame.
 * @property guide the color of the still circles, or `null` for the ink of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class RippleArtStyle(
    public val originX: Float,
    public val originY: Float,
    public val rings: Int,
    public val reach: Float,
    public val lineWidth: Dp,
    public val ringAlpha: Float,
    public val fillAlpha: Float,
    public val period: Duration,
    public val easing: Easing,
    public val guides: Int,
    public val guideSpacing: Dp,
    public val guideAlpha: Float,
    public val pointerFollow: Float,
    public val salt: Int,
    public val colors: List<Color>?,
    public val guide: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [RippleArtStyle] with the given values replaced and every other value kept.
     *
     * @param originX the new value of [originX].
     * @param originY the new value of [originY].
     * @param rings the new value of [rings].
     * @param reach the new value of [reach].
     * @param lineWidth the new value of [lineWidth].
     * @param ringAlpha the new value of [ringAlpha].
     * @param fillAlpha the new value of [fillAlpha].
     * @param period the new value of [period].
     * @param easing the new value of [easing].
     * @param guides the new value of [guides].
     * @param guideSpacing the new value of [guideSpacing].
     * @param guideAlpha the new value of [guideAlpha].
     * @param pointerFollow the new value of [pointerFollow].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param guide the new value of [guide].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        originX: Float = this.originX,
        originY: Float = this.originY,
        rings: Int = this.rings,
        reach: Float = this.reach,
        lineWidth: Dp = this.lineWidth,
        ringAlpha: Float = this.ringAlpha,
        fillAlpha: Float = this.fillAlpha,
        period: Duration = this.period,
        easing: Easing = this.easing,
        guides: Int = this.guides,
        guideSpacing: Dp = this.guideSpacing,
        guideAlpha: Float = this.guideAlpha,
        pointerFollow: Float = this.pointerFollow,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        guide: Color? = this.guide,
        background: Color? = this.background,
    ): RippleArtStyle = RippleArtStyle(
        originX = originX,
        originY = originY,
        rings = rings,
        reach = reach,
        lineWidth = lineWidth,
        ringAlpha = ringAlpha,
        fillAlpha = fillAlpha,
        period = period,
        easing = easing,
        guides = guides,
        guideSpacing = guideSpacing,
        guideAlpha = guideAlpha,
        pointerFollow = pointerFollow,
        salt = salt,
        colors = colors,
        guide = guide,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is RippleArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RippleArtStyle(" +
        "originX=$originX, " +
        "originY=$originY, " +
        "rings=$rings, " +
        "reach=$reach, " +
        "lineWidth=$lineWidth, " +
        "ringAlpha=$ringAlpha, " +
        "fillAlpha=$fillAlpha, " +
        "period=$period, " +
        "easing=$easing, " +
        "guides=$guides, " +
        "guideSpacing=$guideSpacing, " +
        "guideAlpha=$guideAlpha, " +
        "pointerFollow=$pointerFollow, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "guide=${guide ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        originX,
        originY,
        rings,
        reach,
        lineWidth,
        ringAlpha,
        fillAlpha,
        period,
        easing,
        guides,
        guideSpacing,
        guideAlpha,
        pointerFollow,
        salt,
        colors,
        guide,
        background,
    )
}
