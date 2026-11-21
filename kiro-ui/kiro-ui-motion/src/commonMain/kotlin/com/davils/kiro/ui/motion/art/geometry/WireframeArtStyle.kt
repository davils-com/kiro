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
 * How a [WireframeArt] looks: a regular solid, such as a cube, an octahedron, an icosahedron or a dodecahedron, as
 * a wireframe that slowly turns in perspective, whose edges fade with their depth from the accent in front to the
 * ink behind.
 *
 * @property solid the solid.
 * @property size the radius of the sphere around the solid, as a fraction of the shorter side of the area.
 * @property centerX the horizontal position of the center, as a fraction of the width.
 * @property centerY the vertical position of the center, as a fraction of the height.
 * @property perspective the distance of the eye from the center, in radii of the solid; larger values look flatter.
 * @property tilt how far the axis of the turn leans towards the eye, in degrees.
 * @property wobble how far the tilt swings back and forth, in degrees.
 * @property lineWidth the width of the edges.
 * @property nearAlpha the alpha of the edges in front.
 * @property farAlpha the alpha of the edges behind.
 * @property dot the diameter of the dot on a corner in front; dots behind are smaller.
 * @property farDot the size of the dot on a corner behind, as a fraction of [dot].
 * @property dotAlpha the alpha of the dot on a corner in front.
 * @property dotQuality the lowest quality that draws the dots.
 * @property guideAlpha the alpha of the circle around the solid, which never moves.
 * @property period one turn of the solid.
 * @property wobblePeriod one swing of the tilt.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property accent the color of the accent, or `null` for the last accent of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class WireframeArtStyle(
    public val solid: WireframeSolid,
    public val size: Float,
    public val centerX: Float,
    public val centerY: Float,
    public val perspective: Float,
    public val tilt: Float,
    public val wobble: Float,
    public val lineWidth: Dp,
    public val nearAlpha: Float,
    public val farAlpha: Float,
    public val dot: Dp,
    public val farDot: Float,
    public val dotAlpha: Float,
    public val dotQuality: QualityLevel,
    public val guideAlpha: Float,
    public val period: Duration,
    public val wobblePeriod: Duration,
    public val salt: Int,
    public val line: Color?,
    public val accent: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [WireframeArtStyle] with the given values replaced and every other value kept.
     *
     * @param solid the new value of [solid].
     * @param size the new value of [size].
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param perspective the new value of [perspective].
     * @param tilt the new value of [tilt].
     * @param wobble the new value of [wobble].
     * @param lineWidth the new value of [lineWidth].
     * @param nearAlpha the new value of [nearAlpha].
     * @param farAlpha the new value of [farAlpha].
     * @param dot the new value of [dot].
     * @param farDot the new value of [farDot].
     * @param dotAlpha the new value of [dotAlpha].
     * @param dotQuality the new value of [dotQuality].
     * @param guideAlpha the new value of [guideAlpha].
     * @param period the new value of [period].
     * @param wobblePeriod the new value of [wobblePeriod].
     * @param salt the new value of [salt].
     * @param line the new value of [line].
     * @param accent the new value of [accent].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        solid: WireframeSolid = this.solid,
        size: Float = this.size,
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        perspective: Float = this.perspective,
        tilt: Float = this.tilt,
        wobble: Float = this.wobble,
        lineWidth: Dp = this.lineWidth,
        nearAlpha: Float = this.nearAlpha,
        farAlpha: Float = this.farAlpha,
        dot: Dp = this.dot,
        farDot: Float = this.farDot,
        dotAlpha: Float = this.dotAlpha,
        dotQuality: QualityLevel = this.dotQuality,
        guideAlpha: Float = this.guideAlpha,
        period: Duration = this.period,
        wobblePeriod: Duration = this.wobblePeriod,
        salt: Int = this.salt,
        line: Color? = this.line,
        accent: Color? = this.accent,
        background: Color? = this.background,
    ): WireframeArtStyle = WireframeArtStyle(
        solid = solid,
        size = size,
        centerX = centerX,
        centerY = centerY,
        perspective = perspective,
        tilt = tilt,
        wobble = wobble,
        lineWidth = lineWidth,
        nearAlpha = nearAlpha,
        farAlpha = farAlpha,
        dot = dot,
        farDot = farDot,
        dotAlpha = dotAlpha,
        dotQuality = dotQuality,
        guideAlpha = guideAlpha,
        period = period,
        wobblePeriod = wobblePeriod,
        salt = salt,
        line = line,
        accent = accent,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is WireframeArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "WireframeArtStyle(" +
        "solid=$solid, " +
        "size=$size, " +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "perspective=$perspective, " +
        "tilt=$tilt, " +
        "wobble=$wobble, " +
        "lineWidth=$lineWidth, " +
        "nearAlpha=$nearAlpha, " +
        "farAlpha=$farAlpha, " +
        "dot=$dot, " +
        "farDot=$farDot, " +
        "dotAlpha=$dotAlpha, " +
        "dotQuality=$dotQuality, " +
        "guideAlpha=$guideAlpha, " +
        "period=$period, " +
        "wobblePeriod=$wobblePeriod, " +
        "salt=$salt, " +
        "line=${line ?: "null"}, " +
        "accent=${accent ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        solid,
        size,
        centerX,
        centerY,
        perspective,
        tilt,
        wobble,
        lineWidth,
        nearAlpha,
        farAlpha,
        dot,
        farDot,
        dotAlpha,
        dotQuality,
        guideAlpha,
        period,
        wobblePeriod,
        salt,
        line,
        accent,
        background,
    )
}
