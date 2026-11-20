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

package com.davils.kiro.ui.components.resizable

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@Immutable
public class ResizableStyle(
    public val thickness: Dp,
    public val hitArea: Dp,
    public val color: Color,
    public val focusColor: Color,
    public val focusWidth: Dp,
    public val gripLength: Dp,
    public val gripThickness: Dp,
    public val gripShape: Shape,
    public val gripColor: Color,
    public val keyboardStep: Float,
    public val junctionLabel: String,
) {
    /**
     * Returns a copy of this [ResizableStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        thickness: Dp = this.thickness,
        hitArea: Dp = this.hitArea,
        color: Color = this.color,
        focusColor: Color = this.focusColor,
        focusWidth: Dp = this.focusWidth,
        gripLength: Dp = this.gripLength,
        gripThickness: Dp = this.gripThickness,
        gripShape: Shape = this.gripShape,
        gripColor: Color = this.gripColor,
        keyboardStep: Float = this.keyboardStep,
        junctionLabel: String = this.junctionLabel,
    ): ResizableStyle = ResizableStyle(
        thickness = thickness,
        hitArea = hitArea,
        color = color,
        focusColor = focusColor,
        focusWidth = focusWidth,
        gripLength = gripLength,
        gripThickness = gripThickness,
        gripShape = gripShape,
        gripColor = gripColor,
        keyboardStep = keyboardStep,
        junctionLabel = junctionLabel,
    )

    override fun equals(other: Any?): Boolean = other is ResizableStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ResizableStyle(" +
        "thickness=$thickness, " +
        "hitArea=$hitArea, " +
        "color=$color, " +
        "focusColor=$focusColor, " +
        "focusWidth=$focusWidth, " +
        "gripLength=$gripLength, " +
        "gripThickness=$gripThickness, " +
        "gripShape=$gripShape, " +
        "gripColor=$gripColor, " +
        "keyboardStep=$keyboardStep, " +
        "junctionLabel=$junctionLabel)"

    private fun values(): List<Any?> = listOf(
        thickness,
        hitArea,
        color,
        focusColor,
        focusWidth,
        gripLength,
        gripThickness,
        gripShape,
        gripColor,
        keyboardStep,
        junctionLabel,
    )
}
