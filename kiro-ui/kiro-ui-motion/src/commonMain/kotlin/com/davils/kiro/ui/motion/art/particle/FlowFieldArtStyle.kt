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
import kotlin.time.Duration

/**
 * How a [FlowFieldArt] looks: its lines, the field they follow and how they are colored.
 *
 * @property colors the colors of the lines, or `null` for the accents of the palette of the frame.
 * @property lines the number of lines at the highest quality.
 * @property segment the length of a step of a line.
 * @property steps the number of steps a line takes from its start to its end.
 * @property trail the number of steps behind the head of a line that are visible.
 * @property lineWidth the width of a line.
 * @property alpha the alpha of the head of a line.
 * @property fade the part of the trail that fades out toward its end, as a fraction.
 * @property kind how the field derives its directions from the noise.
 * @property scale the size of a swirl of the field.
 * @property octaves the number of layers of detail of the field.
 * @property turns how many full turns the directions of an angle field span.
 * @property evolution how long the field takes to change completely.
 * @property life how long the head of a line takes from its start to its end.
 * @property colorMode what picks the color of a line.
 * @property pointerStrength how strongly a pointer swirls the lines around it, `0` for not at all.
 * @property pointerRadius how far the swirl of a pointer reaches.
 * @since 1.0.0
 */
@Immutable
public class FlowFieldArtStyle(
    public val colors: List<Color>?,
    public val lines: Int,
    public val segment: Dp,
    public val steps: Int,
    public val trail: Int,
    public val lineWidth: Dp,
    public val alpha: Float,
    public val fade: Float,
    public val kind: FlowFieldKind,
    public val scale: Dp,
    public val octaves: Int,
    public val turns: Float,
    public val evolution: Duration,
    public val life: Duration,
    public val colorMode: FlowColorMode,
    public val pointerStrength: Float,
    public val pointerRadius: Dp,
) {
    /**
     * Returns a copy of this [FlowFieldArtStyle] with the given values replaced and every other value kept.
     *
     * @param colors the new value of [colors].
     * @param lines the new value of [lines].
     * @param segment the new value of [segment].
     * @param steps the new value of [steps].
     * @param trail the new value of [trail].
     * @param lineWidth the new value of [lineWidth].
     * @param alpha the new value of [alpha].
     * @param fade the new value of [fade].
     * @param kind the new value of [kind].
     * @param scale the new value of [scale].
     * @param octaves the new value of [octaves].
     * @param turns the new value of [turns].
     * @param evolution the new value of [evolution].
     * @param life the new value of [life].
     * @param colorMode the new value of [colorMode].
     * @param pointerStrength the new value of [pointerStrength].
     * @param pointerRadius the new value of [pointerRadius].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        colors: List<Color>? = this.colors,
        lines: Int = this.lines,
        segment: Dp = this.segment,
        steps: Int = this.steps,
        trail: Int = this.trail,
        lineWidth: Dp = this.lineWidth,
        alpha: Float = this.alpha,
        fade: Float = this.fade,
        kind: FlowFieldKind = this.kind,
        scale: Dp = this.scale,
        octaves: Int = this.octaves,
        turns: Float = this.turns,
        evolution: Duration = this.evolution,
        life: Duration = this.life,
        colorMode: FlowColorMode = this.colorMode,
        pointerStrength: Float = this.pointerStrength,
        pointerRadius: Dp = this.pointerRadius,
    ): FlowFieldArtStyle = FlowFieldArtStyle(
        colors = colors,
        lines = lines,
        segment = segment,
        steps = steps,
        trail = trail,
        lineWidth = lineWidth,
        alpha = alpha,
        fade = fade,
        kind = kind,
        scale = scale,
        octaves = octaves,
        turns = turns,
        evolution = evolution,
        life = life,
        colorMode = colorMode,
        pointerStrength = pointerStrength,
        pointerRadius = pointerRadius,
    )

    override fun equals(other: Any?): Boolean = other is FlowFieldArtStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "FlowFieldArtStyle(" +
        "colors=${colors ?: "null"}, " +
        "lines=$lines, " +
        "segment=$segment, " +
        "steps=$steps, " +
        "trail=$trail, " +
        "lineWidth=$lineWidth, " +
        "alpha=$alpha, " +
        "fade=$fade, " +
        "kind=$kind, " +
        "scale=$scale, " +
        "octaves=$octaves, " +
        "turns=$turns, " +
        "evolution=$evolution, " +
        "life=$life, " +
        "colorMode=$colorMode, " +
        "pointerStrength=$pointerStrength, " +
        "pointerRadius=$pointerRadius)"

    private fun values(): List<Any?> = listOf(
        colors,
        lines,
        segment,
        steps,
        trail,
        lineWidth,
        alpha,
        fade,
        kind,
        scale,
        octaves,
        turns,
        evolution,
        life,
        colorMode,
        pointerStrength,
        pointerRadius,
    )
}
