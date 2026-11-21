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

package com.davils.kiro.ui.window.splash

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.button.ButtonSize
import com.davils.kiro.ui.components.button.ButtonVariant
import com.davils.kiro.ui.icons.IconGraphic

/**
 * How a splash screen shows a failed start: the message, the icon in place of the spinner and the buttons that
 * close the application or start again.
 *
 * @property icon the icon in place of the spinner.
 * @property color the color of the icon, the message and the name of the failed step.
 * @property textStyle the text style of the message.
 * @property maxLines the most lines of the message before it is cut off.
 * @property gap the space between the message and the buttons.
 * @property actionGap the space between the buttons.
 * @property closeVariant the variant of the button that closes the application.
 * @property retryVariant the variant of the button that starts again.
 * @property buttonSize the size of the buttons.
 * @since 1.0.0
 */
@Immutable
public class SplashFailureStyle(
    public val icon: IconGraphic,
    public val color: Color,
    public val textStyle: TextStyle,
    public val maxLines: Int,
    public val gap: Dp,
    public val actionGap: Dp,
    public val closeVariant: ButtonVariant,
    public val retryVariant: ButtonVariant,
    public val buttonSize: ButtonSize,
) {
    /**
     * Returns a copy of this [SplashFailureStyle] with the given values replaced and every other value kept.
     *
     * @param icon the new value of [icon].
     * @param color the new value of [color].
     * @param textStyle the new value of [textStyle].
     * @param maxLines the new value of [maxLines].
     * @param gap the new value of [gap].
     * @param actionGap the new value of [actionGap].
     * @param closeVariant the new value of [closeVariant].
     * @param retryVariant the new value of [retryVariant].
     * @param buttonSize the new value of [buttonSize].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        icon: IconGraphic = this.icon,
        color: Color = this.color,
        textStyle: TextStyle = this.textStyle,
        maxLines: Int = this.maxLines,
        gap: Dp = this.gap,
        actionGap: Dp = this.actionGap,
        closeVariant: ButtonVariant = this.closeVariant,
        retryVariant: ButtonVariant = this.retryVariant,
        buttonSize: ButtonSize = this.buttonSize,
    ): SplashFailureStyle = SplashFailureStyle(
        icon = icon,
        color = color,
        textStyle = textStyle,
        maxLines = maxLines,
        gap = gap,
        actionGap = actionGap,
        closeVariant = closeVariant,
        retryVariant = retryVariant,
        buttonSize = buttonSize,
    )

    override fun equals(other: Any?): Boolean = other is SplashFailureStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SplashFailureStyle(" +
        "icon=$icon, " +
        "color=$color, " +
        "textStyle=$textStyle, " +
        "maxLines=$maxLines, " +
        "gap=$gap, " +
        "actionGap=$actionGap, " +
        "closeVariant=$closeVariant, " +
        "retryVariant=$retryVariant, " +
        "buttonSize=$buttonSize)"

    private fun values(): List<Any?> = listOf(
        icon,
        color,
        textStyle,
        maxLines,
        gap,
        actionGap,
        closeVariant,
        retryVariant,
        buttonSize,
    )
}
