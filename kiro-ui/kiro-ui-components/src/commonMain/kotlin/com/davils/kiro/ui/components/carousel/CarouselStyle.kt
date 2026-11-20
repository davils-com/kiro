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

package com.davils.kiro.ui.components.carousel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The style of a [Carousel].
 *
 * @property spacing the space between two items.
 * @property buttonOffset how far the navigation buttons sit outside the edges of the carousel.
 * @property buttonShape the shape of the navigation buttons.
 * @property previousIcon the icon of the button that moves to the previous item.
 * @property nextIcon the icon of the button that moves to the next item.
 * @property previousLabel the text that announces the button that moves to the previous item.
 * @property nextLabel the text that announces the button that moves to the next item.
 * @since 1.0.0
 */
@Immutable
public class CarouselStyle(
    public val spacing: Dp,
    public val buttonOffset: Dp,
    public val buttonShape: Shape,
    public val previousIcon: IconGraphic,
    public val nextIcon: IconGraphic,
    public val previousLabel: String,
    public val nextLabel: String,
) {
    /**
     * Returns a copy of this [CarouselStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        spacing: Dp = this.spacing,
        buttonOffset: Dp = this.buttonOffset,
        buttonShape: Shape = this.buttonShape,
        previousIcon: IconGraphic = this.previousIcon,
        nextIcon: IconGraphic = this.nextIcon,
        previousLabel: String = this.previousLabel,
        nextLabel: String = this.nextLabel,
    ): CarouselStyle = CarouselStyle(
        spacing = spacing,
        buttonOffset = buttonOffset,
        buttonShape = buttonShape,
        previousIcon = previousIcon,
        nextIcon = nextIcon,
        previousLabel = previousLabel,
        nextLabel = nextLabel,
    )

    override fun equals(other: Any?): Boolean = other is CarouselStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CarouselStyle(" +
        "spacing=$spacing, " +
        "buttonOffset=$buttonOffset, " +
        "buttonShape=$buttonShape, " +
        "previousIcon=$previousIcon, " +
        "nextIcon=$nextIcon, " +
        "previousLabel=$previousLabel, " +
        "nextLabel=$nextLabel)"

    private fun values(): List<Any?> = listOf(
        spacing,
        buttonOffset,
        buttonShape,
        previousIcon,
        nextIcon,
        previousLabel,
        nextLabel,
    )
}
