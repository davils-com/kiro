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

package com.davils.kiro.ui.components.progress

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@Immutable
public class ProgressStyle(
    public val gap: Dp,
    public val trackHeight: Dp,
    public val trackShape: Shape,
    public val trackColor: Color,
    public val indicatorColor: Color,
    public val labelStyle: TextStyle,
    public val valueStyle: TextStyle,
    public val valueColor: Color,
    public val animation: AnimationSpec<Float>,
) {
    /**
     * Returns a copy of this [ProgressStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        gap: Dp = this.gap,
        trackHeight: Dp = this.trackHeight,
        trackShape: Shape = this.trackShape,
        trackColor: Color = this.trackColor,
        indicatorColor: Color = this.indicatorColor,
        labelStyle: TextStyle = this.labelStyle,
        valueStyle: TextStyle = this.valueStyle,
        valueColor: Color = this.valueColor,
        animation: AnimationSpec<Float> = this.animation,
    ): ProgressStyle = ProgressStyle(
        gap = gap,
        trackHeight = trackHeight,
        trackShape = trackShape,
        trackColor = trackColor,
        indicatorColor = indicatorColor,
        labelStyle = labelStyle,
        valueStyle = valueStyle,
        valueColor = valueColor,
        animation = animation,
    )

    override fun equals(other: Any?): Boolean = other is ProgressStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ProgressStyle(" +
        "gap=$gap, " +
        "trackHeight=$trackHeight, " +
        "trackShape=$trackShape, " +
        "trackColor=$trackColor, " +
        "indicatorColor=$indicatorColor, " +
        "labelStyle=$labelStyle, " +
        "valueStyle=$valueStyle, " +
        "valueColor=$valueColor, " +
        "animation=$animation)"

    private fun values(): List<Any?> = listOf(
        gap,
        trackHeight,
        trackShape,
        trackColor,
        indicatorColor,
        labelStyle,
        valueStyle,
        valueColor,
        animation,
    )
}
