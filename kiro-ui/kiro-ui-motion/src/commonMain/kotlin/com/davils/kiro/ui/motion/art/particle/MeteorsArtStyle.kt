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

package com.davils.kiro.ui.motion.art.particle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [MeteorsArt] looks: a few shooting stars that now and then cross the area diagonally, each a bright head with
 * a tail that fades out behind it, over a calm field of faint stars.
 *
 * @property count the number of meteors at the highest quality, each of which comes back again and again.
 * @property angle the direction in which the meteors fly, in degrees clockwise from the right.
 * @property period the average time between two flights of one meteor.
 * @property duty the part of [period] that a meteor is in flight, between `0` and `1`.
 * @property travel how far a meteor flies, as a fraction of the larger side.
 * @property tail the length of the tail of a meteor in full flight.
 * @property width the width of the tail at the head.
 * @property headRadius the radius of the head.
 * @property alpha the alpha of the head in full flight.
 * @property toneFrom where on the gradient of [colors] the meteors start to pick their colors, as a fraction.
 * @property stars the number of faint stars at the highest quality.
 * @property starRadius the radius of a faint star.
 * @property starAlpha the alpha of the brightest faint star.
 * @property colors the colors of the meteors, or `null` for the accents of the palette of the frame.
 * @property star the color of the faint stars, or `null` for the ink of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class MeteorsArtStyle(
    public val count: Int,
    public val angle: Float,
    public val period: Duration,
    public val duty: Float,
    public val travel: Float,
    public val tail: Dp,
    public val width: Dp,
    public val headRadius: Dp,
    public val alpha: Float,
    public val toneFrom: Float,
    public val stars: Int,
    public val starRadius: Dp,
    public val starAlpha: Float,
    public val colors: List<Color>?,
    public val star: Color?,
) {
    /**
     * Returns a copy of this [MeteorsArtStyle] with the given values replaced and every other value kept.
     *
     * @param count the new value of [count].
     * @param angle the new value of [angle].
     * @param period the new value of [period].
     * @param duty the new value of [duty].
     * @param travel the new value of [travel].
     * @param tail the new value of [tail].
     * @param width the new value of [width].
     * @param headRadius the new value of [headRadius].
     * @param alpha the new value of [alpha].
     * @param toneFrom the new value of [toneFrom].
     * @param stars the new value of [stars].
     * @param starRadius the new value of [starRadius].
     * @param starAlpha the new value of [starAlpha].
     * @param colors the new value of [colors].
     * @param star the new value of [star].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        count: Int = this.count,
        angle: Float = this.angle,
        period: Duration = this.period,
        duty: Float = this.duty,
        travel: Float = this.travel,
        tail: Dp = this.tail,
        width: Dp = this.width,
        headRadius: Dp = this.headRadius,
        alpha: Float = this.alpha,
        toneFrom: Float = this.toneFrom,
        stars: Int = this.stars,
        starRadius: Dp = this.starRadius,
        starAlpha: Float = this.starAlpha,
        colors: List<Color>? = this.colors,
        star: Color? = this.star,
    ): MeteorsArtStyle = MeteorsArtStyle(
        count = count,
        angle = angle,
        period = period,
        duty = duty,
        travel = travel,
        tail = tail,
        width = width,
        headRadius = headRadius,
        alpha = alpha,
        toneFrom = toneFrom,
        stars = stars,
        starRadius = starRadius,
        starAlpha = starAlpha,
        colors = colors,
        star = star,
    )

    override fun equals(other: Any?): Boolean = other is MeteorsArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MeteorsArtStyle(" +
        "count=$count, " +
        "angle=$angle, " +
        "period=$period, " +
        "duty=$duty, " +
        "travel=$travel, " +
        "tail=$tail, " +
        "width=$width, " +
        "headRadius=$headRadius, " +
        "alpha=$alpha, " +
        "toneFrom=$toneFrom, " +
        "stars=$stars, " +
        "starRadius=$starRadius, " +
        "starAlpha=$starAlpha, " +
        "colors=${colors ?: "null"}, " +
        "star=${star ?: "null"})"

    private fun values(): List<Any?> = listOf(
        count,
        angle,
        period,
        duty,
        travel,
        tail,
        width,
        headRadius,
        alpha,
        toneFrom,
        stars,
        starRadius,
        starAlpha,
        colors,
        star,
    )
}
