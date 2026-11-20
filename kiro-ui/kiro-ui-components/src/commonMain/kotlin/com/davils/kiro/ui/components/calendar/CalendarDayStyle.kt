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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.davils.kiro.ui.core.state.StateValue

/**
 * The look of the days of a [Calendar] in their different roles.
 *
 * @property textStyle the text style of the number of a day.
 * @property container the container color of a day that is not selected, by interaction state.
 * @property content the color of the number of a day that is not selected.
 * @property selectedContainer the container color of a selected day and of the ends of a selected range.
 * @property selectedContent the color of the number of a selected day and of the ends of a selected range.
 * @property todayContainer the container color of today while it is not selected.
 * @property rangeContainer the color of the band behind a selected range and the container color of the days inside it.
 * @property rangeContent the color of the number of a day inside a selected range.
 * @property outsideContent the color of the number of a day of the previous or next month.
 * @property focusBorder the color of the focus border of a day; the calendar currently draws only the focus ring.
 * @property focusRing the color of the ring around a day while its focus is visible.
 * @property disabledAlpha the opacity of a disabled day.
 * @since 1.0.0
 */
@Immutable
public class CalendarDayStyle(
    public val textStyle: TextStyle,
    public val container: StateValue<Color>,
    public val content: Color,
    public val selectedContainer: Color,
    public val selectedContent: Color,
    public val todayContainer: Color,
    public val rangeContainer: Color,
    public val rangeContent: Color,
    public val outsideContent: Color,
    public val focusBorder: Color,
    public val focusRing: Color,
    public val disabledAlpha: Float,
) {
    /**
     * Returns a copy of this [CalendarDayStyle] with the given values replaced and every other value kept.
     *
     * @param textStyle the new value of [textStyle].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param selectedContainer the new value of [selectedContainer].
     * @param selectedContent the new value of [selectedContent].
     * @param todayContainer the new value of [todayContainer].
     * @param rangeContainer the new value of [rangeContainer].
     * @param rangeContent the new value of [rangeContent].
     * @param outsideContent the new value of [outsideContent].
     * @param focusBorder the new value of [focusBorder].
     * @param focusRing the new value of [focusRing].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        textStyle: TextStyle = this.textStyle,
        container: StateValue<Color> = this.container,
        content: Color = this.content,
        selectedContainer: Color = this.selectedContainer,
        selectedContent: Color = this.selectedContent,
        todayContainer: Color = this.todayContainer,
        rangeContainer: Color = this.rangeContainer,
        rangeContent: Color = this.rangeContent,
        outsideContent: Color = this.outsideContent,
        focusBorder: Color = this.focusBorder,
        focusRing: Color = this.focusRing,
        disabledAlpha: Float = this.disabledAlpha,
    ): CalendarDayStyle = CalendarDayStyle(
        textStyle = textStyle,
        container = container,
        content = content,
        selectedContainer = selectedContainer,
        selectedContent = selectedContent,
        todayContainer = todayContainer,
        rangeContainer = rangeContainer,
        rangeContent = rangeContent,
        outsideContent = outsideContent,
        focusBorder = focusBorder,
        focusRing = focusRing,
        disabledAlpha = disabledAlpha,
    )

    override fun equals(other: Any?): Boolean = other is CalendarDayStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CalendarDayStyle(" +
        "textStyle=$textStyle, " +
        "container=$container, " +
        "content=$content, " +
        "selectedContainer=$selectedContainer, " +
        "selectedContent=$selectedContent, " +
        "todayContainer=$todayContainer, " +
        "rangeContainer=$rangeContainer, " +
        "rangeContent=$rangeContent, " +
        "outsideContent=$outsideContent, " +
        "focusBorder=$focusBorder, " +
        "focusRing=$focusRing, " +
        "disabledAlpha=$disabledAlpha)"

    private fun values(): List<Any?> = listOf(
        textStyle,
        container,
        content,
        selectedContainer,
        selectedContent,
        todayContainer,
        rangeContainer,
        rangeContent,
        outsideContent,
        focusBorder,
        focusRing,
        disabledAlpha,
    )
}
