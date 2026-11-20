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

package com.davils.kiro.ui.components.calendar

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.button.ButtonStyle
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class CalendarStyle(
    public val container: Color,
    public val padding: Dp,
    public val cellSize: Dp,
    public val cellRadius: Dp,
    public val sectionGap: Dp,
    public val weekGap: Dp,
    public val captionStyle: TextStyle,
    public val weekdayStyle: TextStyle,
    public val weekdayColor: Color,
    public val day: CalendarDayStyle,
    public val navButton: ButtonStyle,
    public val previousIcon: IconGraphic,
    public val nextIcon: IconGraphic,
    public val ringWidth: Dp,
    public val colorAnimation: AnimationSpec<Color>,
) {
    /**
     * Returns a copy of this [CalendarStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        container: Color = this.container,
        padding: Dp = this.padding,
        cellSize: Dp = this.cellSize,
        cellRadius: Dp = this.cellRadius,
        sectionGap: Dp = this.sectionGap,
        weekGap: Dp = this.weekGap,
        captionStyle: TextStyle = this.captionStyle,
        weekdayStyle: TextStyle = this.weekdayStyle,
        weekdayColor: Color = this.weekdayColor,
        day: CalendarDayStyle = this.day,
        navButton: ButtonStyle = this.navButton,
        previousIcon: IconGraphic = this.previousIcon,
        nextIcon: IconGraphic = this.nextIcon,
        ringWidth: Dp = this.ringWidth,
        colorAnimation: AnimationSpec<Color> = this.colorAnimation,
    ): CalendarStyle = CalendarStyle(
        container = container,
        padding = padding,
        cellSize = cellSize,
        cellRadius = cellRadius,
        sectionGap = sectionGap,
        weekGap = weekGap,
        captionStyle = captionStyle,
        weekdayStyle = weekdayStyle,
        weekdayColor = weekdayColor,
        day = day,
        navButton = navButton,
        previousIcon = previousIcon,
        nextIcon = nextIcon,
        ringWidth = ringWidth,
        colorAnimation = colorAnimation,
    )

    override fun equals(other: Any?): Boolean = other is CalendarStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CalendarStyle(" +
        "container=$container, " +
        "padding=$padding, " +
        "cellSize=$cellSize, " +
        "cellRadius=$cellRadius, " +
        "sectionGap=$sectionGap, " +
        "weekGap=$weekGap, " +
        "captionStyle=$captionStyle, " +
        "weekdayStyle=$weekdayStyle, " +
        "weekdayColor=$weekdayColor, " +
        "day=$day, " +
        "navButton=$navButton, " +
        "previousIcon=$previousIcon, " +
        "nextIcon=$nextIcon, " +
        "ringWidth=$ringWidth, " +
        "colorAnimation=$colorAnimation)"

    private fun values(): List<Any?> = listOf(
        container,
        padding,
        cellSize,
        cellRadius,
        sectionGap,
        weekGap,
        captionStyle,
        weekdayStyle,
        weekdayColor,
        day,
        navButton,
        previousIcon,
        nextIcon,
        ringWidth,
        colorAnimation,
    )
}
