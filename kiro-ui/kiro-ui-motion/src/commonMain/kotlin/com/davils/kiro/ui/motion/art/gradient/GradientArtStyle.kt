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

package com.davils.kiro.ui.motion.art.gradient

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How a [GradientArt] looks: a smooth gradient through its colors that slowly turns and drifts.
 *
 * @property kind the shape of the gradient.
 * @property strength how far the gradient moves from [background] toward its colors, between `0` and `1`.
 * @property steps the number of stops at the highest quality that carry the perceptual blend between the colors.
 * @property rotation how long a linear or sweep gradient takes for one turn.
 * @property orbit how far the middle of the gradient circles around the middle of the area.
 * @property orbitPeriod how long the middle of the gradient takes for one round.
 * @property reach the radius of a radial gradient, as a fraction of the larger side.
 * @property colors the colors of the gradient, or `null` for the accents of the palette of the frame.
 * @property background the color the gradient is mixed from, or `null` for the background of the palette of the frame.
 * @since 1.0.0
 */
@Immutable
public class GradientArtStyle(
    public val kind: GradientKind,
    public val strength: Float,
    public val steps: Int,
    public val rotation: Duration,
    public val orbit: Dp,
    public val orbitPeriod: Duration,
    public val reach: Float,
    public val colors: List<Color>?,
    public val background: Color?,
) {
    /**
     * Returns a copy of this [GradientArtStyle] with the given values replaced and every other value kept.
     *
     * @param kind the new value of [kind].
     * @param strength the new value of [strength].
     * @param steps the new value of [steps].
     * @param rotation the new value of [rotation].
     * @param orbit the new value of [orbit].
     * @param orbitPeriod the new value of [orbitPeriod].
     * @param reach the new value of [reach].
     * @param colors the new value of [colors].
     * @param background the new value of [background].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        kind: GradientKind = this.kind,
        strength: Float = this.strength,
        steps: Int = this.steps,
        rotation: Duration = this.rotation,
        orbit: Dp = this.orbit,
        orbitPeriod: Duration = this.orbitPeriod,
        reach: Float = this.reach,
        colors: List<Color>? = this.colors,
        background: Color? = this.background,
    ): GradientArtStyle = GradientArtStyle(
        kind = kind,
        strength = strength,
        steps = steps,
        rotation = rotation,
        orbit = orbit,
        orbitPeriod = orbitPeriod,
        reach = reach,
        colors = colors,
        background = background,
    )

    override fun equals(other: Any?): Boolean = other is GradientArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "GradientArtStyle(" +
        "kind=$kind, " +
        "strength=$strength, " +
        "steps=$steps, " +
        "rotation=$rotation, " +
        "orbit=$orbit, " +
        "orbitPeriod=$orbitPeriod, " +
        "reach=$reach, " +
        "colors=${colors ?: "null"}, " +
        "background=${background ?: "null"})"

    private fun values(): List<Any?> = listOf(
        kind,
        strength,
        steps,
        rotation,
        orbit,
        orbitPeriod,
        reach,
        colors,
        background,
    )
}
