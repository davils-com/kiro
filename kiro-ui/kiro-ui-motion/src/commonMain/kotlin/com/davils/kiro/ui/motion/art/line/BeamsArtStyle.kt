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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [BeamsArt] looks: a fan of faint curves that sweep from the bottom left to the top right, along which short
 * beams of light travel, each with a soft glow and a tail that fades.
 *
 * @property beams how many curves the fan has at the highest quality.
 * @property spread how far apart the first and the last curve start, as a fraction of the width.
 * @property bend how far a curve bows, as a fraction of the size of the area.
 * @property lineWidth the width of a curve.
 * @property guideAlpha the alpha of a curve; `0` hides the curves.
 * @property beamWidth the width of a beam.
 * @property beamAlpha the alpha of a beam at its brightest.
 * @property glowWidth the width of the glow around a beam.
 * @property glowAlpha the alpha of the glow; `0` hides it.
 * @property segment the length of a beam, as a fraction of its curve.
 * @property period how long a beam takes to travel its curve, on average.
 * @property periodSpread how much the periods of the beams differ, as a fraction of [period].
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the beams, used in turn, or `null` for the accents of the palette of the frame.
 * @property guide the color of the curves, or `null` for the ink of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class BeamsArtStyle(
    public val beams: Int,
    public val spread: Float,
    public val bend: Float,
    public val lineWidth: Dp,
    public val guideAlpha: Float,
    public val beamWidth: Dp,
    public val beamAlpha: Float,
    public val glowWidth: Dp,
    public val glowAlpha: Float,
    public val segment: Float,
    public val period: Duration,
    public val periodSpread: Float,
    public val salt: Int,
    public val colors: List<Color>?,
    public val guide: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [BeamsArtStyle] with the given values replaced and every other value kept.
     *
     * @param beams the new value of [beams].
     * @param spread the new value of [spread].
     * @param bend the new value of [bend].
     * @param lineWidth the new value of [lineWidth].
     * @param guideAlpha the new value of [guideAlpha].
     * @param beamWidth the new value of [beamWidth].
     * @param beamAlpha the new value of [beamAlpha].
     * @param glowWidth the new value of [glowWidth].
     * @param glowAlpha the new value of [glowAlpha].
     * @param segment the new value of [segment].
     * @param period the new value of [period].
     * @param periodSpread the new value of [periodSpread].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param guide the new value of [guide].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        beams: Int = this.beams,
        spread: Float = this.spread,
        bend: Float = this.bend,
        lineWidth: Dp = this.lineWidth,
        guideAlpha: Float = this.guideAlpha,
        beamWidth: Dp = this.beamWidth,
        beamAlpha: Float = this.beamAlpha,
        glowWidth: Dp = this.glowWidth,
        glowAlpha: Float = this.glowAlpha,
        segment: Float = this.segment,
        period: Duration = this.period,
        periodSpread: Float = this.periodSpread,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        guide: Color? = this.guide,
        background: Color? = this.background,
    ): BeamsArtStyle = BeamsArtStyle(
        beams = beams,
        spread = spread,
        bend = bend,
        lineWidth = lineWidth,
        guideAlpha = guideAlpha,
        beamWidth = beamWidth,
        beamAlpha = beamAlpha,
        glowWidth = glowWidth,
        glowAlpha = glowAlpha,
        segment = segment,
        period = period,
        periodSpread = periodSpread,
        salt = salt,
        colors = colors,
        guide = guide,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is BeamsArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "BeamsArtStyle(" +
        "beams=$beams, " +
        "spread=$spread, " +
        "bend=$bend, " +
        "lineWidth=$lineWidth, " +
        "guideAlpha=$guideAlpha, " +
        "beamWidth=$beamWidth, " +
        "beamAlpha=$beamAlpha, " +
        "glowWidth=$glowWidth, " +
        "glowAlpha=$glowAlpha, " +
        "segment=$segment, " +
        "period=$period, " +
        "periodSpread=$periodSpread, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "guide=${guide ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        beams,
        spread,
        bend,
        lineWidth,
        guideAlpha,
        beamWidth,
        beamAlpha,
        glowWidth,
        glowAlpha,
        segment,
        period,
        periodSpread,
        salt,
        colors,
        guide,
        background,
    )
}
