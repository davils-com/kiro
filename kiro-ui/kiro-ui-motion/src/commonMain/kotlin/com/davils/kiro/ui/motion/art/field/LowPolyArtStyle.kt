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
 * How a [LowPolyArt] looks: a surface of flat triangles on a jittered grid, tinted by a gradient across the area and
 * shaded by a light that slowly circles above it.
 *
 * @property cell the size of a square of the grid at the highest quality, which two triangles share.
 * @property jitter how far a corner strays from its place on the grid, as a fraction of [cell].
 * @property drift how far a corner sways around its place, as a fraction of [cell].
 * @property driftPeriod how long a corner takes for one sway.
 * @property relief how high the hills of the surface rise, as a fraction of [cell].
 * @property reliefScale the size of a hill of the surface.
 * @property tint how far the surface moves from the background toward the gradient, between `0` and `1`.
 * @property contrast how strongly the light raises or lowers [tint] on a facet.
 * @property elevation the angle of the light above the surface, in degrees.
 * @property lightPeriod how long the light takes for one round above the surface.
 * @property edgeWidth the width of the seams between the triangles.
 * @property edgeAlpha the alpha of the seams, zero for none.
 * @property colors the colors of the gradient across the surface, or `null` for the accents of the palette of the
 * frame.
 * @property background the color the surface is tinted from, or `null` for the background of the palette of the frame.
 * @property edge the color of the seams, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class LowPolyArtStyle(
    public val cell: Dp,
    public val jitter: Float,
    public val drift: Float,
    public val driftPeriod: Duration,
    public val relief: Float,
    public val reliefScale: Dp,
    public val tint: Float,
    public val contrast: Float,
    public val elevation: Float,
    public val lightPeriod: Duration,
    public val edgeWidth: Dp,
    public val edgeAlpha: Float,
    public val colors: List<Color>?,
    public val background: Color?,
    public val edge: Color?,
) {
    /**
     * Returns a copy of this [LowPolyArtStyle] with the given values replaced and every other value kept.
     *
     * @param cell the new value of [cell].
     * @param jitter the new value of [jitter].
     * @param drift the new value of [drift].
     * @param driftPeriod the new value of [driftPeriod].
     * @param relief the new value of [relief].
     * @param reliefScale the new value of [reliefScale].
     * @param tint the new value of [tint].
     * @param contrast the new value of [contrast].
     * @param elevation the new value of [elevation].
     * @param lightPeriod the new value of [lightPeriod].
     * @param edgeWidth the new value of [edgeWidth].
     * @param edgeAlpha the new value of [edgeAlpha].
     * @param colors the new value of [colors].
     * @param background the new value of [background].
     * @param edge the new value of [edge].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        cell: Dp = this.cell,
        jitter: Float = this.jitter,
        drift: Float = this.drift,
        driftPeriod: Duration = this.driftPeriod,
        relief: Float = this.relief,
        reliefScale: Dp = this.reliefScale,
        tint: Float = this.tint,
        contrast: Float = this.contrast,
        elevation: Float = this.elevation,
        lightPeriod: Duration = this.lightPeriod,
        edgeWidth: Dp = this.edgeWidth,
        edgeAlpha: Float = this.edgeAlpha,
        colors: List<Color>? = this.colors,
        background: Color? = this.background,
        edge: Color? = this.edge,
    ): LowPolyArtStyle = LowPolyArtStyle(
        cell = cell,
        jitter = jitter,
        drift = drift,
        driftPeriod = driftPeriod,
        relief = relief,
        reliefScale = reliefScale,
        tint = tint,
        contrast = contrast,
        elevation = elevation,
        lightPeriod = lightPeriod,
        edgeWidth = edgeWidth,
        edgeAlpha = edgeAlpha,
        colors = colors,
        background = background,
        edge = edge,
    )

    override fun equals(other: Any?): Boolean = other is LowPolyArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "LowPolyArtStyle(" +
        "cell=$cell, " +
        "jitter=$jitter, " +
        "drift=$drift, " +
        "driftPeriod=$driftPeriod, " +
        "relief=$relief, " +
        "reliefScale=$reliefScale, " +
        "tint=$tint, " +
        "contrast=$contrast, " +
        "elevation=$elevation, " +
        "lightPeriod=$lightPeriod, " +
        "edgeWidth=$edgeWidth, " +
        "edgeAlpha=$edgeAlpha, " +
        "colors=${colors ?: "null"}, " +
        "background=${background ?: "null"}, " +
        "edge=${edge ?: "null"})"

    private fun values(): List<Any?> = listOf(
        cell,
        jitter,
        drift,
        driftPeriod,
        relief,
        reliefScale,
        tint,
        contrast,
        elevation,
        lightPeriod,
        edgeWidth,
        edgeAlpha,
        colors,
        background,
        edge,
    )
}
