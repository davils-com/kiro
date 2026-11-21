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

package com.davils.kiro.ui.motion.art.geometry

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [PolygonsArt] looks: nested regular polygons that turn in both directions, with dots on their corners over
 * faint guide circles, one of which at a time is traced by a pen in the accent.
 *
 * @property rings the number of polygons.
 * @property centerX the horizontal position of the center, as a fraction of the width.
 * @property centerY the vertical position of the center, as a fraction of the height.
 * @property firstRadius the distance from the center to the corners of the innermost polygon.
 * @property ringSpacing how much farther the corners of a polygon lie than those of the one inside it.
 * @property minSides the least number of sides of a polygon.
 * @property maxSides the largest number of sides of a polygon.
 * @property lineWidth the width of the sides.
 * @property lineAlpha the alpha of the sides.
 * @property dot the diameter of the dot on a corner.
 * @property dotAlpha the alpha of the dot on a corner.
 * @property dotQuality the lowest quality that draws the dots.
 * @property guideAlpha the alpha of the circles through the corners, which never move.
 * @property period one turn of the innermost polygon.
 * @property slowdown how much longer a polygon takes for a turn than the one inside it, as a fraction of [period].
 * @property isAlternating whether every other polygon turns the other way.
 * @property tracePeriod how long one polygon is traced before the pen moves on to the next.
 * @property drawShare how much of [tracePeriod] the pen takes to go around, before the trace fades.
 * @property traceWidth the width of the trace.
 * @property traceAlpha the alpha of the trace.
 * @property head the diameter of the pen.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property accent the color of the accent, or `null` for the last accent of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class PolygonsArtStyle(
    public val rings: Int,
    public val centerX: Float,
    public val centerY: Float,
    public val firstRadius: Dp,
    public val ringSpacing: Dp,
    public val minSides: Int,
    public val maxSides: Int,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val dot: Dp,
    public val dotAlpha: Float,
    public val dotQuality: QualityLevel,
    public val guideAlpha: Float,
    public val period: Duration,
    public val slowdown: Float,
    public val isAlternating: Boolean,
    public val tracePeriod: Duration,
    public val drawShare: Float,
    public val traceWidth: Dp,
    public val traceAlpha: Float,
    public val head: Dp,
    public val salt: Int,
    public val line: Color?,
    public val accent: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [PolygonsArtStyle] with the given values replaced and every other value kept.
     *
     * @param rings the new value of [rings].
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param firstRadius the new value of [firstRadius].
     * @param ringSpacing the new value of [ringSpacing].
     * @param minSides the new value of [minSides].
     * @param maxSides the new value of [maxSides].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param dot the new value of [dot].
     * @param dotAlpha the new value of [dotAlpha].
     * @param dotQuality the new value of [dotQuality].
     * @param guideAlpha the new value of [guideAlpha].
     * @param period the new value of [period].
     * @param slowdown the new value of [slowdown].
     * @param isAlternating the new value of [isAlternating].
     * @param tracePeriod the new value of [tracePeriod].
     * @param drawShare the new value of [drawShare].
     * @param traceWidth the new value of [traceWidth].
     * @param traceAlpha the new value of [traceAlpha].
     * @param head the new value of [head].
     * @param salt the new value of [salt].
     * @param line the new value of [line].
     * @param accent the new value of [accent].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        rings: Int = this.rings,
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        firstRadius: Dp = this.firstRadius,
        ringSpacing: Dp = this.ringSpacing,
        minSides: Int = this.minSides,
        maxSides: Int = this.maxSides,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        dot: Dp = this.dot,
        dotAlpha: Float = this.dotAlpha,
        dotQuality: QualityLevel = this.dotQuality,
        guideAlpha: Float = this.guideAlpha,
        period: Duration = this.period,
        slowdown: Float = this.slowdown,
        isAlternating: Boolean = this.isAlternating,
        tracePeriod: Duration = this.tracePeriod,
        drawShare: Float = this.drawShare,
        traceWidth: Dp = this.traceWidth,
        traceAlpha: Float = this.traceAlpha,
        head: Dp = this.head,
        salt: Int = this.salt,
        line: Color? = this.line,
        accent: Color? = this.accent,
        background: Color? = this.background,
    ): PolygonsArtStyle = PolygonsArtStyle(
        rings = rings,
        centerX = centerX,
        centerY = centerY,
        firstRadius = firstRadius,
        ringSpacing = ringSpacing,
        minSides = minSides,
        maxSides = maxSides,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        dot = dot,
        dotAlpha = dotAlpha,
        dotQuality = dotQuality,
        guideAlpha = guideAlpha,
        period = period,
        slowdown = slowdown,
        isAlternating = isAlternating,
        tracePeriod = tracePeriod,
        drawShare = drawShare,
        traceWidth = traceWidth,
        traceAlpha = traceAlpha,
        head = head,
        salt = salt,
        line = line,
        accent = accent,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is PolygonsArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "PolygonsArtStyle(" +
        "rings=$rings, " +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "firstRadius=$firstRadius, " +
        "ringSpacing=$ringSpacing, " +
        "minSides=$minSides, " +
        "maxSides=$maxSides, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "dot=$dot, " +
        "dotAlpha=$dotAlpha, " +
        "dotQuality=$dotQuality, " +
        "guideAlpha=$guideAlpha, " +
        "period=$period, " +
        "slowdown=$slowdown, " +
        "isAlternating=$isAlternating, " +
        "tracePeriod=$tracePeriod, " +
        "drawShare=$drawShare, " +
        "traceWidth=$traceWidth, " +
        "traceAlpha=$traceAlpha, " +
        "head=$head, " +
        "salt=$salt, " +
        "line=${line ?: "null"}, " +
        "accent=${accent ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        rings,
        centerX,
        centerY,
        firstRadius,
        ringSpacing,
        minSides,
        maxSides,
        lineWidth,
        lineAlpha,
        dot,
        dotAlpha,
        dotQuality,
        guideAlpha,
        period,
        slowdown,
        isAlternating,
        tracePeriod,
        drawShare,
        traceWidth,
        traceAlpha,
        head,
        salt,
        line,
        accent,
        background,
    )
}
