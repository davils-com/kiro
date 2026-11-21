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

package com.davils.kiro.ui.motion.art.field

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [VoronoiArt] looks: shards around slowly drifting seeds, each the area closest to its seed, outlined by
 * hairlines and softly tinted.
 *
 * @property cell the average size of a shard at the highest quality.
 * @property jitter how far a seed strays from the middle of its share of the area, as a fraction of [cell].
 * @property drift how far a seed wanders around its home, as a fraction of [cell].
 * @property period how long a seed takes for one loop of its wandering.
 * @property lineWidth the width of the edges between two shards.
 * @property lineAlpha the alpha of the edges.
 * @property tintAlpha the alpha of the tint of the most strongly tinted shard.
 * @property tintVariance how much fainter the faintest shard is than the strongest, as a fraction of [tintAlpha].
 * @property toneJitter how far the color of a shard strays from the diagonal gradient across the area, as a fraction.
 * @property seedRadius the radius of the dot at each seed, zero for no dots.
 * @property seedAlpha the alpha of the dots at the seeds.
 * @property pointerAlpha the alpha of the tint of the shard under the pointer.
 * @property colors the colors of the tints, as a gradient across the area, or `null` for the accents of the palette of
 * the frame.
 * @property line the color of the edges and dots, or `null` for the ink of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class VoronoiArtStyle(
    public val cell: Dp,
    public val jitter: Float,
    public val drift: Float,
    public val period: Duration,
    public val lineWidth: Dp,
    public val lineAlpha: Float,
    public val tintAlpha: Float,
    public val tintVariance: Float,
    public val toneJitter: Float,
    public val seedRadius: Dp,
    public val seedAlpha: Float,
    public val pointerAlpha: Float,
    public val colors: List<Color>?,
    public val line: Color?,
) {
    /**
     * Returns a copy of this [VoronoiArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param jitter the new value of [jitter].
     * @param drift the new value of [drift].
     * @param period the new value of [period].
     * @param lineWidth the new value of [lineWidth].
     * @param lineAlpha the new value of [lineAlpha].
     * @param tintAlpha the new value of [tintAlpha].
     * @param tintVariance the new value of [tintVariance].
     * @param toneJitter the new value of [toneJitter].
     * @param seedRadius the new value of [seedRadius].
     * @param seedAlpha the new value of [seedAlpha].
     * @param pointerAlpha the new value of [pointerAlpha].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        jitter: Float = this.jitter,
        drift: Float = this.drift,
        period: Duration = this.period,
        lineWidth: Dp = this.lineWidth,
        lineAlpha: Float = this.lineAlpha,
        tintAlpha: Float = this.tintAlpha,
        tintVariance: Float = this.tintVariance,
        toneJitter: Float = this.toneJitter,
        seedRadius: Dp = this.seedRadius,
        seedAlpha: Float = this.seedAlpha,
        pointerAlpha: Float = this.pointerAlpha,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
    ): VoronoiArtStyle = VoronoiArtStyle(
        cell = cell,
        jitter = jitter,
        drift = drift,
        period = period,
        lineWidth = lineWidth,
        lineAlpha = lineAlpha,
        tintAlpha = tintAlpha,
        tintVariance = tintVariance,
        toneJitter = toneJitter,
        seedRadius = seedRadius,
        seedAlpha = seedAlpha,
        pointerAlpha = pointerAlpha,
        colors = colors,
        line = line,
    )

    override fun equals(other: Any?): Boolean = other is VoronoiArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "VoronoiArtStyle(" +
        "cell=$cell, " +
        "jitter=$jitter, " +
        "drift=$drift, " +
        "period=$period, " +
        "lineWidth=$lineWidth, " +
        "lineAlpha=$lineAlpha, " +
        "tintAlpha=$tintAlpha, " +
        "tintVariance=$tintVariance, " +
        "toneJitter=$toneJitter, " +
        "seedRadius=$seedRadius, " +
        "seedAlpha=$seedAlpha, " +
        "pointerAlpha=$pointerAlpha, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        jitter,
        drift,
        period,
        lineWidth,
        lineAlpha,
        tintAlpha,
        tintVariance,
        toneJitter,
        seedRadius,
        seedAlpha,
        pointerAlpha,
        colors,
        line,
    )
}
