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

package com.davils.kiro.ui.components.popover

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle

/**
 * How a popover looks: its panel, its width, the spacing of its content and the text of its header.
 *
 * @property panel the panel the content is drawn on.
 * @property width the width of the panel.
 * @property gap the space between the children of the content.
 * @property textStyle the text style of the content.
 * @property headerGap the space between the children of a [PopoverHeader], such as its title and its description.
 * @property titleStyle the text style of a [PopoverTitle].
 * @property descriptionColor the color of a [PopoverDescription].
 * @since 1.0.0
 */
@Immutable
public class PopoverStyle(
    public val panel: PanelStyle,
    public val width: Dp,
    public val gap: Dp,
    public val textStyle: TextStyle,
    public val headerGap: Dp,
    public val titleStyle: TextStyle,
    public val descriptionColor: Color,
) {
    /**
     * Returns a copy of this [PopoverStyle] with the given values replaced and every other value kept.
     *
     * @param panel the new value of [panel].
     * @param width the new value of [width].
     * @param gap the new value of [gap].
     * @param textStyle the new value of [textStyle].
     * @param headerGap the new value of [headerGap].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionColor the new value of [descriptionColor].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        panel: PanelStyle = this.panel,
        width: Dp = this.width,
        gap: Dp = this.gap,
        textStyle: TextStyle = this.textStyle,
        headerGap: Dp = this.headerGap,
        titleStyle: TextStyle = this.titleStyle,
        descriptionColor: Color = this.descriptionColor,
    ): PopoverStyle = PopoverStyle(
        panel = panel,
        width = width,
        gap = gap,
        textStyle = textStyle,
        headerGap = headerGap,
        titleStyle = titleStyle,
        descriptionColor = descriptionColor,
    )

    override fun equals(other: Any?): Boolean = other is PopoverStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "PopoverStyle(" +
        "panel=$panel, " +
        "width=$width, " +
        "gap=$gap, " +
        "textStyle=$textStyle, " +
        "headerGap=$headerGap, " +
        "titleStyle=$titleStyle, " +
        "descriptionColor=$descriptionColor)"

    private fun values(): List<Any?> = listOf(
        panel,
        width,
        gap,
        textStyle,
        headerGap,
        titleStyle,
        descriptionColor,
    )
}
