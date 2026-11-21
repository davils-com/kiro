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

package com.davils.kiro.ui.motion.shader.mesh

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.motion.shader.ShaderBackend
import kotlin.time.Duration

/**
 * How a [MeshGradientArt] looks: a few soft anchors of color that drift slowly over the background and blend
 * into each other like a mesh gradient.
 *
 * @property colors the colors of the anchors, used in turn, or `null` for the accents of the palette of the
frame.
 * @property background the color between the anchors, or `null` for the background of the palette of the frame.
 * @property anchors the number of anchors, from `1` to `6`.
 * @property spread how far the anchors rest from the middle, as a fraction of the size.
 * @property radius the reach of an anchor, as a fraction of the longer side.
 * @property softness how quickly an anchor fades toward its reach; higher values give smaller, firmer blooms.
 * @property intensity how strongly the anchors tint [background], from `0` to `1`.
 * @property drift how far an anchor wanders from its rest, as a fraction of the size.
 * @property period how long the anchors take to return to where they started.
 * @property pointerPull how far the anchors near the pointer lean toward it, from `0` to `1`.
 * @property dither the strength of the fine noise that hides the banding of the gradient, in steps of one 8-bit
level.
 * @property backend whether the art draws with its shader where it can or always with its canvas fallback.
 * @since 1.0.0
 */
@Immutable
public class MeshGradientArtStyle(
    public val colors: List<Color>?,
    public val background: Color?,
    public val anchors: Int,
    public val spread: Float,
    public val radius: Float,
    public val softness: Float,
    public val intensity: Float,
    public val drift: Float,
    public val period: Duration,
    public val pointerPull: Float,
    public val dither: Float,
    public val backend: ShaderBackend,
) {
    /**
     * Returns a copy of this [MeshGradientArtStyle] with the given values replaced and every other value kept.
     *
     * @param colors the new value of [colors].
     * @param background the new value of [background].
     * @param anchors the new value of [anchors].
     * @param spread the new value of [spread].
     * @param radius the new value of [radius].
     * @param softness the new value of [softness].
     * @param intensity the new value of [intensity].
     * @param drift the new value of [drift].
     * @param period the new value of [period].
     * @param pointerPull the new value of [pointerPull].
     * @param dither the new value of [dither].
     * @param backend the new value of [backend].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        colors: List<Color>? = this.colors,
        background: Color? = this.background,
        anchors: Int = this.anchors,
        spread: Float = this.spread,
        radius: Float = this.radius,
        softness: Float = this.softness,
        intensity: Float = this.intensity,
        drift: Float = this.drift,
        period: Duration = this.period,
        pointerPull: Float = this.pointerPull,
        dither: Float = this.dither,
        backend: ShaderBackend = this.backend,
    ): MeshGradientArtStyle = MeshGradientArtStyle(
        colors = colors,
        background = background,
        anchors = anchors,
        spread = spread,
        radius = radius,
        softness = softness,
        intensity = intensity,
        drift = drift,
        period = period,
        pointerPull = pointerPull,
        dither = dither,
        backend = backend,
    )

    override fun equals(other: Any?): Boolean = other is MeshGradientArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "MeshGradientArtStyle(" +
        "colors=${colors ?: "null"}, " +
        "background=${background ?: "null"}, " +
        "anchors=$anchors, " +
        "spread=$spread, " +
        "radius=$radius, " +
        "softness=$softness, " +
        "intensity=$intensity, " +
        "drift=$drift, " +
        "period=$period, " +
        "pointerPull=$pointerPull, " +
        "dither=$dither, " +
        "backend=$backend)"

    private fun values(): List<Any?> = listOf(
        colors,
        background,
        anchors,
        spread,
        radius,
        softness,
        intensity,
        drift,
        period,
        pointerPull,
        dither,
        backend,
    )
}
