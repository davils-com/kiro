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

/**
 * The look of a [Calendar]: its container, the sizes and gaps of its grid, its texts, its days and its navigation.
 *
 * @property container the background color of the calendar.
 * @property padding the padding around the content of the calendar.
 * @property cellSize the width and height of a day, of a weekday name and of a week number.
 * @property cellRadius the corner radius of a day and of the ends of a selected range.
 * @property sectionGap the space between the caption and the grid of days.
 * @property weekGap the space above each week.
 * @property captionStyle the text style of the caption of the month.
 * @property weekdayStyle the text style of the weekday names and week numbers.
 * @property weekdayColor the color of the weekday names and week numbers.
 * @property day the style of the days.
 * @property navButton the style of the buttons to the previous and next month.
 * @property previousIcon the icon of the button to the previous month.
 * @property nextIcon the icon of the button to the next month.
 * @property ringWidth the width of the focus ring of a day.
 * @property colorAnimation the animation of the colors of a day.
 * @since 1.0.0
 */
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
     * @param container the new value of [container].
     * @param padding the new value of [padding].
     * @param cellSize the new value of [cellSize].
     * @param cellRadius the new value of [cellRadius].
     * @param sectionGap the new value of [sectionGap].
     * @param weekGap the new value of [weekGap].
     * @param captionStyle the new value of [captionStyle].
     * @param weekdayStyle the new value of [weekdayStyle].
     * @param weekdayColor the new value of [weekdayColor].
     * @param day the new value of [day].
     * @param navButton the new value of [navButton].
     * @param previousIcon the new value of [previousIcon].
     * @param nextIcon the new value of [nextIcon].
     * @param ringWidth the new value of [ringWidth].
     * @param colorAnimation the new value of [colorAnimation].
     * @return the copy.
     * @since 1.0.0
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
