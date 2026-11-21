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
import com.davils.kiro.ui.motion.quality.QualityLevel
import kotlin.time.Duration

/**
 * How a [ParticlesArt] looks: small dots at different depths that drift, wrap around the edges and twinkle, with
 * hairlines between close neighbors and a shift with the pointer.
 *
 * @property distance the average distance between two particles at the highest quality.
 * @property maxCount the most particles drawn, however large the area.
 * @property minRadius the radius of the farthest particle.
 * @property maxRadius the radius of the nearest particle.
 * @property minAlpha the alpha of the farthest particle.
 * @property maxAlpha the alpha of the nearest particle.
 * @property drift how far the nearest particle drifts in one [period]; farther ones drift less.
 * @property period the time in which the nearest particle drifts by [drift].
 * @property twinkle how deeply a particle dims while it twinkles, between `0` and `1`.
 * @property twinklePeriod the time of one twinkle of a particle at its average pace.
 * @property parallax how far the nearest particle shifts when the pointer moves from the middle to an edge.
 * @property linkDistance the distance up to which two particles are joined by a hairline, zero for none.
 * @property linkWidth the width of a hairline between two particles.
 * @property linkAlpha the alpha of the hairline between two touching particles.
 * @property linkQuality the lowest quality at which the hairlines are drawn.
 * @property colors the colors of the particles from far to near, or `null` for the accents of the palette of the frame.
 * @property link the color of the hairlines, or `null` for the ink of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class ParticlesArtStyle(
    public val distance: Dp,
    public val maxCount: Int,
    public val minRadius: Dp,
    public val maxRadius: Dp,
    public val minAlpha: Float,
    public val maxAlpha: Float,
    public val drift: Dp,
    public val period: Duration,
    public val twinkle: Float,
    public val twinklePeriod: Duration,
    public val parallax: Dp,
    public val linkDistance: Dp,
    public val linkWidth: Dp,
    public val linkAlpha: Float,
    public val linkQuality: QualityLevel,
    public val colors: List<Color>?,
    public val link: Color?,
) {
    /**
     * Returns a copy of this [ParticlesArtStyle] with the given values replaced and every other value kept.
     *
     * @param distance the new value of [distance].
     * @param maxCount the new value of [maxCount].
     * @param minRadius the new value of [minRadius].
     * @param maxRadius the new value of [maxRadius].
     * @param minAlpha the new value of [minAlpha].
     * @param maxAlpha the new value of [maxAlpha].
     * @param drift the new value of [drift].
     * @param period the new value of [period].
     * @param twinkle the new value of [twinkle].
     * @param twinklePeriod the new value of [twinklePeriod].
     * @param parallax the new value of [parallax].
     * @param linkDistance the new value of [linkDistance].
     * @param linkWidth the new value of [linkWidth].
     * @param linkAlpha the new value of [linkAlpha].
     * @param linkQuality the new value of [linkQuality].
     * @param colors the new value of [colors].
     * @param link the new value of [link].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        distance: Dp = this.distance,
        maxCount: Int = this.maxCount,
        minRadius: Dp = this.minRadius,
        maxRadius: Dp = this.maxRadius,
        minAlpha: Float = this.minAlpha,
        maxAlpha: Float = this.maxAlpha,
        drift: Dp = this.drift,
        period: Duration = this.period,
        twinkle: Float = this.twinkle,
        twinklePeriod: Duration = this.twinklePeriod,
        parallax: Dp = this.parallax,
        linkDistance: Dp = this.linkDistance,
        linkWidth: Dp = this.linkWidth,
        linkAlpha: Float = this.linkAlpha,
        linkQuality: QualityLevel = this.linkQuality,
        colors: List<Color>? = this.colors,
        link: Color? = this.link,
    ): ParticlesArtStyle = ParticlesArtStyle(
        distance = distance,
        maxCount = maxCount,
        minRadius = minRadius,
        maxRadius = maxRadius,
        minAlpha = minAlpha,
        maxAlpha = maxAlpha,
        drift = drift,
        period = period,
        twinkle = twinkle,
        twinklePeriod = twinklePeriod,
        parallax = parallax,
        linkDistance = linkDistance,
        linkWidth = linkWidth,
        linkAlpha = linkAlpha,
        linkQuality = linkQuality,
        colors = colors,
        link = link,
    )

    override fun equals(other: Any?): Boolean = other is ParticlesArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ParticlesArtStyle(" +
        "distance=$distance, " +
        "maxCount=$maxCount, " +
        "minRadius=$minRadius, " +
        "maxRadius=$maxRadius, " +
        "minAlpha=$minAlpha, " +
        "maxAlpha=$maxAlpha, " +
        "drift=$drift, " +
        "period=$period, " +
        "twinkle=$twinkle, " +
        "twinklePeriod=$twinklePeriod, " +
        "parallax=$parallax, " +
        "linkDistance=$linkDistance, " +
        "linkWidth=$linkWidth, " +
        "linkAlpha=$linkAlpha, " +
        "linkQuality=$linkQuality, " +
        "colors=${colors ?: "null"}, " +
        "link=${link ?: "null"})"

    private fun values(): List<Any?> = listOf(
        distance,
        maxCount,
        minRadius,
        maxRadius,
        minAlpha,
        maxAlpha,
        drift,
        period,
        twinkle,
        twinklePeriod,
        parallax,
        linkDistance,
        linkWidth,
        linkAlpha,
        linkQuality,
        colors,
        link,
    )
}
