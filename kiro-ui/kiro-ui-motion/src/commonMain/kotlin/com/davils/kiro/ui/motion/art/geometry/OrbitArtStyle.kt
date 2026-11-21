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
import kotlin.time.Duration

/**
 * How an [OrbitArt] looks: dashed orbits around a calm, glowing center, with planets that circle at the speed of
 * their orbit, in turns in both directions, with fading trails and now and then a moon.
 *
 * @property centerX the horizontal position of the center, as a fraction of the width.
 * @property centerY the vertical position of the center, as a fraction of the height.
 * @property firstRadius the radius of the innermost orbit, which keeps the center free.
 * @property ringSpacing the space between two orbits.
 * @property rings the number of orbits.
 * @property ringWidth the width of the line of an orbit.
 * @property ringAlpha the alpha of the line of an orbit.
 * @property dash the length of a dash of an orbit.
 * @property dashGap the space between two dashes of an orbit.
 * @property drift how long the dashes of the innermost orbit take for one round.
 * @property minPlanets the least number of planets on an orbit.
 * @property maxPlanets the largest number of planets on an orbit.
 * @property minPlanet the diameter of the smallest planet.
 * @property maxPlanet the diameter of the largest planet.
 * @property shine the alpha of the light spot on a planet.
 * @property shineSize the radius of the light spot on a planet, as a fraction of the radius of the planet.
 * @property trail the length of the trail of a planet, in degrees.
 * @property trailSegments the number of pieces a trail fades in at the highest quality.
 * @property trailAlpha the alpha of a trail next to its planet.
 * @property moonChance the share of planets with a moon.
 * @property moon the diameter of a moon.
 * @property moonDistance the distance of a moon from its planet.
 * @property moonPeriod how long a moon takes for one round.
 * @property glowRadius the radius of the glow in the center.
 * @property glowAlpha the alpha of the glow in its middle.
 * @property period one round on the innermost orbit; the outer ones take longer, as in Kepler's third law.
 * @property isAlternating whether every other orbit turns the other way.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the shapes, used in turn, or `null` for the accents of the palette of the frame.
 * @property glow the color of the glow, or `null` for the first accent of the palette.
 * @property ring the color of the orbits, or `null` for the ink of the palette.
 * @property highlight the color of the light spots on the planets, or `null` for the highlight of the palette.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class OrbitArtStyle(
    public val centerX: Float,
    public val centerY: Float,
    public val firstRadius: Dp,
    public val ringSpacing: Dp,
    public val rings: Int,
    public val ringWidth: Dp,
    public val ringAlpha: Float,
    public val dash: Dp,
    public val dashGap: Dp,
    public val drift: Duration,
    public val minPlanets: Int,
    public val maxPlanets: Int,
    public val minPlanet: Dp,
    public val maxPlanet: Dp,
    public val shine: Float,
    public val shineSize: Float,
    public val trail: Float,
    public val trailSegments: Int,
    public val trailAlpha: Float,
    public val moonChance: Float,
    public val moon: Dp,
    public val moonDistance: Dp,
    public val moonPeriod: Duration,
    public val glowRadius: Dp,
    public val glowAlpha: Float,
    public val period: Duration,
    public val isAlternating: Boolean,
    public val salt: Int,
    public val colors: List<Color>?,
    public val glow: Color?,
    public val ring: Color?,
    public val highlight: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [OrbitArtStyle] with the given values replaced and every other value kept.
     *
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param firstRadius the new value of [firstRadius].
     * @param ringSpacing the new value of [ringSpacing].
     * @param rings the new value of [rings].
     * @param ringWidth the new value of [ringWidth].
     * @param ringAlpha the new value of [ringAlpha].
     * @param dash the new value of [dash].
     * @param dashGap the new value of [dashGap].
     * @param drift the new value of [drift].
     * @param minPlanets the new value of [minPlanets].
     * @param maxPlanets the new value of [maxPlanets].
     * @param minPlanet the new value of [minPlanet].
     * @param maxPlanet the new value of [maxPlanet].
     * @param shine the new value of [shine].
     * @param shineSize the new value of [shineSize].
     * @param trail the new value of [trail].
     * @param trailSegments the new value of [trailSegments].
     * @param trailAlpha the new value of [trailAlpha].
     * @param moonChance the new value of [moonChance].
     * @param moon the new value of [moon].
     * @param moonDistance the new value of [moonDistance].
     * @param moonPeriod the new value of [moonPeriod].
     * @param glowRadius the new value of [glowRadius].
     * @param glowAlpha the new value of [glowAlpha].
     * @param period the new value of [period].
     * @param isAlternating the new value of [isAlternating].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param glow the new value of [glow].
     * @param ring the new value of [ring].
     * @param highlight the new value of [highlight].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        firstRadius: Dp = this.firstRadius,
        ringSpacing: Dp = this.ringSpacing,
        rings: Int = this.rings,
        ringWidth: Dp = this.ringWidth,
        ringAlpha: Float = this.ringAlpha,
        dash: Dp = this.dash,
        dashGap: Dp = this.dashGap,
        drift: Duration = this.drift,
        minPlanets: Int = this.minPlanets,
        maxPlanets: Int = this.maxPlanets,
        minPlanet: Dp = this.minPlanet,
        maxPlanet: Dp = this.maxPlanet,
        shine: Float = this.shine,
        shineSize: Float = this.shineSize,
        trail: Float = this.trail,
        trailSegments: Int = this.trailSegments,
        trailAlpha: Float = this.trailAlpha,
        moonChance: Float = this.moonChance,
        moon: Dp = this.moon,
        moonDistance: Dp = this.moonDistance,
        moonPeriod: Duration = this.moonPeriod,
        glowRadius: Dp = this.glowRadius,
        glowAlpha: Float = this.glowAlpha,
        period: Duration = this.period,
        isAlternating: Boolean = this.isAlternating,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        glow: Color? = this.glow,
        ring: Color? = this.ring,
        highlight: Color? = this.highlight,
        background: Color? = this.background,
    ): OrbitArtStyle = OrbitArtStyle(
        centerX = centerX,
        centerY = centerY,
        firstRadius = firstRadius,
        ringSpacing = ringSpacing,
        rings = rings,
        ringWidth = ringWidth,
        ringAlpha = ringAlpha,
        dash = dash,
        dashGap = dashGap,
        drift = drift,
        minPlanets = minPlanets,
        maxPlanets = maxPlanets,
        minPlanet = minPlanet,
        maxPlanet = maxPlanet,
        shine = shine,
        shineSize = shineSize,
        trail = trail,
        trailSegments = trailSegments,
        trailAlpha = trailAlpha,
        moonChance = moonChance,
        moon = moon,
        moonDistance = moonDistance,
        moonPeriod = moonPeriod,
        glowRadius = glowRadius,
        glowAlpha = glowAlpha,
        period = period,
        isAlternating = isAlternating,
        salt = salt,
        colors = colors,
        glow = glow,
        ring = ring,
        highlight = highlight,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is OrbitArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "OrbitArtStyle(" +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "firstRadius=$firstRadius, " +
        "ringSpacing=$ringSpacing, " +
        "rings=$rings, " +
        "ringWidth=$ringWidth, " +
        "ringAlpha=$ringAlpha, " +
        "dash=$dash, " +
        "dashGap=$dashGap, " +
        "drift=$drift, " +
        "minPlanets=$minPlanets, " +
        "maxPlanets=$maxPlanets, " +
        "minPlanet=$minPlanet, " +
        "maxPlanet=$maxPlanet, " +
        "shine=$shine, " +
        "shineSize=$shineSize, " +
        "trail=$trail, " +
        "trailSegments=$trailSegments, " +
        "trailAlpha=$trailAlpha, " +
        "moonChance=$moonChance, " +
        "moon=$moon, " +
        "moonDistance=$moonDistance, " +
        "moonPeriod=$moonPeriod, " +
        "glowRadius=$glowRadius, " +
        "glowAlpha=$glowAlpha, " +
        "period=$period, " +
        "isAlternating=$isAlternating, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "glow=${glow ?: "null"}, " +
        "ring=${ring ?: "null"}, " +
        "highlight=${highlight ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        centerX,
        centerY,
        firstRadius,
        ringSpacing,
        rings,
        ringWidth,
        ringAlpha,
        dash,
        dashGap,
        drift,
        minPlanets,
        maxPlanets,
        minPlanet,
        maxPlanet,
        shine,
        shineSize,
        trail,
        trailSegments,
        trailAlpha,
        moonChance,
        moon,
        moonDistance,
        moonPeriod,
        glowRadius,
        glowAlpha,
        period,
        isAlternating,
        salt,
        colors,
        glow,
        ring,
        highlight,
        background,
    )
}
