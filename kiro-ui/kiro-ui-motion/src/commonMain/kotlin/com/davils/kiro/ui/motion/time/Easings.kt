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

package com.davils.kiro.ui.motion.time

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import kotlin.math.PI
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sin

private const val OVERSHOOT: Float = 1.70158f
private const val IN_OUT_OVERSHOOT: Float = 1.525f
private const val ELASTIC_PERIOD: Float = 0.3f
private const val ELASTIC_DECAY: Float = -10f
private const val QUARTER: Float = 4f
private const val HALF: Float = 0.5f
private const val BOUNCE: Float = 7.5625f
private val BOUNCE_ENDS: FloatArray = floatArrayOf(1f / 2.75f, 2f / 2.75f, 2.5f / 2.75f, 1f)
private val BOUNCE_CENTERS: FloatArray = floatArrayOf(0f, 1.5f / 2.75f, 2.25f / 2.75f, 2.625f / 2.75f)
private val BOUNCE_FLOORS: FloatArray = floatArrayOf(0f, 0.75f, 0.9375f, 0.984375f)

/**
 * Easing curves for art beyond the easings of the theme: curves that overshoot, spring, bounce or step.
 *
 * Every curve is an [Easing], so it fits wherever Compose takes one, such as a [Keyframe] or the style of an art.
 *
 * @since 1.0.0
 */
public object Easings {
    /**
     * Overshoots the end a little and settles back.
     *
     * @since 1.0.0
     */
    public val backOut: Easing = backOut(overshoot = OVERSHOOT)

    /**
     * Pulls back a little at the start, overshoots the end and settles back.
     *
     * @since 1.0.0
     */
    public val backInOut: Easing = backInOut(overshoot = OVERSHOOT * IN_OUT_OVERSHOOT)

    /**
     * Springs past the end and swings out like a released rubber band.
     *
     * @since 1.0.0
     */
    public val elasticOut: Easing = elasticOut(period = ELASTIC_PERIOD)

    /**
     * Falls to the end and bounces off it, lower every time.
     *
     * @since 1.0.0
     */
    public val bounceOut: Easing = Easing(::bounce)

    /**
     * Returns a curve that overshoots the end and settles back.
     *
     * @param overshoot how far the curve overshoots; `0` does not overshoot.
     * @return the curve.
     * @since 1.0.0
     */
    public fun backOut(overshoot: Float): Easing = Easing { fraction ->
        val shifted = fraction - 1f
        shifted * shifted * ((overshoot + 1f) * shifted + overshoot) + 1f
    }

    /**
     * Returns a curve that pulls back at the start, overshoots the end and settles back.
     *
     * @param overshoot how far the curve pulls back and overshoots; `0` does neither.
     * @return the curve.
     * @since 1.0.0
     */
    public fun backInOut(overshoot: Float): Easing = Easing { fraction -> backInOutOf(fraction * 2f, overshoot) }

    /**
     * Returns a curve that springs past the end and swings out.
     *
     * @param period the length of one swing, as a fraction of the whole curve; shorter swings more often.
     * @return the curve.
     * @since 1.0.0
     */
    public fun elasticOut(period: Float): Easing = Easing { fraction -> elastic(fraction, period) }

    /**
     * Returns a curve that jumps in even steps instead of moving smoothly, such as a ticking hand.
     *
     * @param count the number of steps.
     * @param jumpsAtStart whether every step jumps at its start instead of at its end.
     * @return the curve.
     * @throws IllegalArgumentException if [count] is not positive.
     * @since 1.0.0
     */
    public fun steps(count: Int, jumpsAtStart: Boolean = false): Easing {
        require(count > 0) { "Steps need a positive count, but got $count." }
        val rounding: (Float) -> Float = roundingOf(jumpsAtStart)
        return Easing { fraction -> rounding(fraction.coerceIn(minimumValue = 0f, maximumValue = 1f) * count) / count }
    }

    /**
     * Returns a cubic Bézier curve from `(0, 0)` to `(1, 1)` through two control points, as in CSS.
     *
     * @param x1 the horizontal position of the first control point, between `0` and `1`.
     * @param y1 the vertical position of the first control point.
     * @param x2 the horizontal position of the second control point, between `0` and `1`.
     * @param y2 the vertical position of the second control point.
     * @return the curve.
     * @since 1.0.0
     */
    public fun cubicBezier(x1: Float, y1: Float, x2: Float, y2: Float): Easing =
        CubicBezierEasing(a = x1, b = y1, c = x2, d = y2)
}

private fun backInOutOf(doubled: Float, overshoot: Float): Float {
    if (doubled < 1f) return HALF * doubled * doubled * ((overshoot + 1f) * doubled - overshoot)
    val shifted = doubled - 2f
    return HALF * (shifted * shifted * ((overshoot + 1f) * shifted + overshoot) + 2f)
}

private fun elastic(fraction: Float, period: Float): Float {
    if (fraction <= 0f || fraction >= 1f) return fraction.coerceIn(minimumValue = 0f, maximumValue = 1f)
    val decay = 2.0.pow((ELASTIC_DECAY * fraction).toDouble())
    val swing = sin((fraction - period / QUARTER) * (2.0 * PI) / period)
    return (decay * swing + 1.0).toFloat()
}

private fun bounce(fraction: Float): Float {
    val clamped = fraction.coerceIn(minimumValue = 0f, maximumValue = 1f)
    val index = BOUNCE_ENDS.indexOfFirst { end -> clamped <= end }
    val shifted = clamped - BOUNCE_CENTERS[index]
    return BOUNCE * shifted * shifted + BOUNCE_FLOORS[index]
}

private fun roundingOf(jumpsAtStart: Boolean): (Float) -> Float {
    if (jumpsAtStart) return ::ceil
    return ::floor
}
