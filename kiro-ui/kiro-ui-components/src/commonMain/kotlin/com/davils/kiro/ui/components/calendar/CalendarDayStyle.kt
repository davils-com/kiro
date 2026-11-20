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
     * @since 0.1.0
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
