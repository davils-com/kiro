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

package com.davils.kiro.ui.motion.shader.silk

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.shader.ShaderBackend
import kotlin.time.Duration

/**
 * How a [SilkArt] looks: a sheet of silk in soft folds that catch the light and slowly roll across the area.
 *
 * @property background the color behind the silk, or `null` for the background of the palette of the frame.
 * @property color the color of the silk, or `null` for the second accent of the palette of the frame.
 * @property shadow the color in the hollows of the folds, or `null` for the shade of the palette of the frame.
 * @property sheen the color of the light on the crests of the folds, or `null` for the highlight of the palette of
the frame.
 * @property intensity how strongly [color] tints [background], from `0` to `1`.
 * @property shadowAlpha how dark the hollows get, from `0` to `1`.
 * @property sheenAlpha how bright the crests get, from `0` to `1`.
 * @property scale the width of a fold, as a fraction of the longer side.
 * @property warp how much the folds bend; `0` keeps them straight.
 * @property relief how deep the folds look.
 * @property shine how tight the sheen on a crest is; higher values give a narrower, glossier line.
 * @property angle the direction the folds run in, in degrees, before the seed turns them a little.
 * @property period how long the folds take to roll back to where they started.
 * @property pointerLight how much the light follows the pointer, from `0` to `1`.
 * @property dither the strength of the fine noise that hides the banding of the gradient, in steps of one 8-bit
level.
 * @property backend whether the art draws with its shader where it can or always with its canvas fallback.
 * @since 1.0.0
 */
@Immutable
public class SilkArtStyle(
    public val background: Color?,
    public val color: Color?,
    public val shadow: Color?,
    public val sheen: Color?,
    public val intensity: Float,
    public val shadowAlpha: Float,
    public val sheenAlpha: Float,
    public val scale: Float,
    public val warp: Float,
    public val relief: Float,
    public val shine: Float,
    public val angle: Float,
    public val period: Duration,
    public val pointerLight: Float,
    public val dither: Float,
    public val backend: ShaderBackend,
) {
    /**
     * Returns a copy of this [SilkArtStyle] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param color the new value of [color].
     * @param shadow the new value of [shadow].
     * @param sheen the new value of [sheen].
     * @param intensity the new value of [intensity].
     * @param shadowAlpha the new value of [shadowAlpha].
     * @param sheenAlpha the new value of [sheenAlpha].
     * @param scale the new value of [scale].
     * @param warp the new value of [warp].
     * @param relief the new value of [relief].
     * @param shine the new value of [shine].
     * @param angle the new value of [angle].
     * @param period the new value of [period].
     * @param pointerLight the new value of [pointerLight].
     * @param dither the new value of [dither].
     * @param backend the new value of [backend].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        background: Color? = this.background,
        color: Color? = this.color,
        shadow: Color? = this.shadow,
        sheen: Color? = this.sheen,
        intensity: Float = this.intensity,
        shadowAlpha: Float = this.shadowAlpha,
        sheenAlpha: Float = this.sheenAlpha,
        scale: Float = this.scale,
        warp: Float = this.warp,
        relief: Float = this.relief,
        shine: Float = this.shine,
        angle: Float = this.angle,
        period: Duration = this.period,
        pointerLight: Float = this.pointerLight,
        dither: Float = this.dither,
        backend: ShaderBackend = this.backend,
    ): SilkArtStyle = SilkArtStyle(
        background = background,
        color = color,
        shadow = shadow,
        sheen = sheen,
        intensity = intensity,
        shadowAlpha = shadowAlpha,
        sheenAlpha = sheenAlpha,
        scale = scale,
        warp = warp,
        relief = relief,
        shine = shine,
        angle = angle,
        period = period,
        pointerLight = pointerLight,
        dither = dither,
        backend = backend,
    )

    override fun equals(other: Any?): Boolean = other is SilkArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SilkArtStyle(" +
        "background=${background ?: "null"}, " +
        "color=${color ?: "null"}, " +
        "shadow=${shadow ?: "null"}, " +
        "sheen=${sheen ?: "null"}, " +
        "intensity=$intensity, " +
        "shadowAlpha=$shadowAlpha, " +
        "sheenAlpha=$sheenAlpha, " +
        "scale=$scale, " +
        "warp=$warp, " +
        "relief=$relief, " +
        "shine=$shine, " +
        "angle=$angle, " +
        "period=$period, " +
        "pointerLight=$pointerLight, " +
        "dither=$dither, " +
        "backend=$backend)"

    private fun values(): List<Any?> = listOf(
        background,
        color,
        shadow,
        sheen,
        intensity,
        shadowAlpha,
        sheenAlpha,
        scale,
        warp,
        relief,
        shine,
        angle,
        period,
        pointerLight,
        dither,
        backend,
    )
}
