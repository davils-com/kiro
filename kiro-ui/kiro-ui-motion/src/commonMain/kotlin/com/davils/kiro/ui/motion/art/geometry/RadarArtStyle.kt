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
 * How a [RadarArt] looks: a radar screen: rings, a crosshair and a dial of ticks, with a sweep whose wedge of light
 * reveals blips that ping and fade until the sweep comes round again and finds them somewhere else.
 *
 * @property centerX the horizontal position of the center, as a fraction of the width.
 * @property centerY the vertical position of the center, as a fraction of the height.
 * @property size the radius of the screen, as a fraction of the shorter side of the area.
 * @property rings the number of rings.
 * @property lineWidth the width of the rings, the crosshair, the ticks and the beam.
 * @property ringAlpha the alpha of the rings.
 * @property crossAlpha the alpha of the crosshair.
 * @property crossReach how far the crosshair reaches, as a fraction of the radius.
 * @property ticks the number of ticks around the outer ring.
 * @property majorEvery how many ticks lie between two long ones, or 0 for none.
 * @property tick the length of a short tick.
 * @property majorTick the length of a long tick.
 * @property tickAlpha the alpha of the ticks.
 * @property wedge the angle of the wedge of light behind the beam, in degrees.
 * @property wedgeAlpha the alpha of the wedge next to the beam.
 * @property beamAlpha the alpha of the beam.
 * @property blips the number of blips at the highest quality.
 * @property inner how close to the center a blip may lie, as a fraction of the radius.
 * @property blip the diameter of a blip.
 * @property blipAlpha the alpha of a blip when the beam finds it.
 * @property fade how much of a turn a blip takes to fade.
 * @property halo the radius the ping ring of a blip grows to.
 * @property haloAlpha the alpha of the ping ring of a blip when it starts.
 * @property haloQuality the lowest quality that draws the ping rings.
 * @property period one turn of the sweep.
 * @property salt the number mixed into the seed, so that layers with one seed draw different arrangements.
 * @property colors the colors of the blips, used in turn, or `null` for the accent.
 * @property line the color of the lines, or `null` for the ink of the palette of the frame.
 * @property accent the color of the accent, or `null` for the last accent of the palette of the frame.
 * @property background the color filling the area first, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class RadarArtStyle(
    public val centerX: Float,
    public val centerY: Float,
    public val size: Float,
    public val rings: Int,
    public val lineWidth: Dp,
    public val ringAlpha: Float,
    public val crossAlpha: Float,
    public val crossReach: Float,
    public val ticks: Int,
    public val majorEvery: Int,
    public val tick: Dp,
    public val majorTick: Dp,
    public val tickAlpha: Float,
    public val wedge: Float,
    public val wedgeAlpha: Float,
    public val beamAlpha: Float,
    public val blips: Int,
    public val inner: Float,
    public val blip: Dp,
    public val blipAlpha: Float,
    public val fade: Float,
    public val halo: Dp,
    public val haloAlpha: Float,
    public val haloQuality: QualityLevel,
    public val period: Duration,
    public val salt: Int,
    public val colors: List<Color>?,
    public val line: Color?,
    public val accent: Color?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [RadarArtStyle] with the given values replaced and every other value kept.
     *
     * @param centerX the new value of [centerX].
     * @param centerY the new value of [centerY].
     * @param size the new value of [size].
     * @param rings the new value of [rings].
     * @param lineWidth the new value of [lineWidth].
     * @param ringAlpha the new value of [ringAlpha].
     * @param crossAlpha the new value of [crossAlpha].
     * @param crossReach the new value of [crossReach].
     * @param ticks the new value of [ticks].
     * @param majorEvery the new value of [majorEvery].
     * @param tick the new value of [tick].
     * @param majorTick the new value of [majorTick].
     * @param tickAlpha the new value of [tickAlpha].
     * @param wedge the new value of [wedge].
     * @param wedgeAlpha the new value of [wedgeAlpha].
     * @param beamAlpha the new value of [beamAlpha].
     * @param blips the new value of [blips].
     * @param inner the new value of [inner].
     * @param blip the new value of [blip].
     * @param blipAlpha the new value of [blipAlpha].
     * @param fade the new value of [fade].
     * @param halo the new value of [halo].
     * @param haloAlpha the new value of [haloAlpha].
     * @param haloQuality the new value of [haloQuality].
     * @param period the new value of [period].
     * @param salt the new value of [salt].
     * @param colors the new value of [colors].
     * @param line the new value of [line].
     * @param accent the new value of [accent].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        centerX: Float = this.centerX,
        centerY: Float = this.centerY,
        size: Float = this.size,
        rings: Int = this.rings,
        lineWidth: Dp = this.lineWidth,
        ringAlpha: Float = this.ringAlpha,
        crossAlpha: Float = this.crossAlpha,
        crossReach: Float = this.crossReach,
        ticks: Int = this.ticks,
        majorEvery: Int = this.majorEvery,
        tick: Dp = this.tick,
        majorTick: Dp = this.majorTick,
        tickAlpha: Float = this.tickAlpha,
        wedge: Float = this.wedge,
        wedgeAlpha: Float = this.wedgeAlpha,
        beamAlpha: Float = this.beamAlpha,
        blips: Int = this.blips,
        inner: Float = this.inner,
        blip: Dp = this.blip,
        blipAlpha: Float = this.blipAlpha,
        fade: Float = this.fade,
        halo: Dp = this.halo,
        haloAlpha: Float = this.haloAlpha,
        haloQuality: QualityLevel = this.haloQuality,
        period: Duration = this.period,
        salt: Int = this.salt,
        colors: List<Color>? = this.colors,
        line: Color? = this.line,
        accent: Color? = this.accent,
        background: Color? = this.background,
    ): RadarArtStyle = RadarArtStyle(
        centerX = centerX,
        centerY = centerY,
        size = size,
        rings = rings,
        lineWidth = lineWidth,
        ringAlpha = ringAlpha,
        crossAlpha = crossAlpha,
        crossReach = crossReach,
        ticks = ticks,
        majorEvery = majorEvery,
        tick = tick,
        majorTick = majorTick,
        tickAlpha = tickAlpha,
        wedge = wedge,
        wedgeAlpha = wedgeAlpha,
        beamAlpha = beamAlpha,
        blips = blips,
        inner = inner,
        blip = blip,
        blipAlpha = blipAlpha,
        fade = fade,
        halo = halo,
        haloAlpha = haloAlpha,
        haloQuality = haloQuality,
        period = period,
        salt = salt,
        colors = colors,
        line = line,
        accent = accent,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is RadarArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "RadarArtStyle(" +
        "centerX=$centerX, " +
        "centerY=$centerY, " +
        "size=$size, " +
        "rings=$rings, " +
        "lineWidth=$lineWidth, " +
        "ringAlpha=$ringAlpha, " +
        "crossAlpha=$crossAlpha, " +
        "crossReach=$crossReach, " +
        "ticks=$ticks, " +
        "majorEvery=$majorEvery, " +
        "tick=$tick, " +
        "majorTick=$majorTick, " +
        "tickAlpha=$tickAlpha, " +
        "wedge=$wedge, " +
        "wedgeAlpha=$wedgeAlpha, " +
        "beamAlpha=$beamAlpha, " +
        "blips=$blips, " +
        "inner=$inner, " +
        "blip=$blip, " +
        "blipAlpha=$blipAlpha, " +
        "fade=$fade, " +
        "halo=$halo, " +
        "haloAlpha=$haloAlpha, " +
        "haloQuality=$haloQuality, " +
        "period=$period, " +
        "salt=$salt, " +
        "colors=${colors ?: "null"}, " +
        "line=${line ?: "null"}, " +
        "accent=${accent ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        centerX,
        centerY,
        size,
        rings,
        lineWidth,
        ringAlpha,
        crossAlpha,
        crossReach,
        ticks,
        majorEvery,
        tick,
        majorTick,
        tickAlpha,
        wedge,
        wedgeAlpha,
        beamAlpha,
        blips,
        inner,
        blip,
        blipAlpha,
        fade,
        halo,
        haloAlpha,
        haloQuality,
        period,
        salt,
        colors,
        line,
        accent,
        background,
    )
}
