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

package com.davils.kiro.ui.components.inputotp

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import kotlin.time.Duration

/**
 * How the caret of an [InputOtp] looks: a bar in the empty slot that receives the next character.
 *
 * @property width the width of the caret.
 * @property height the height of the caret.
 * @property color the color of the caret.
 * @property period the time of one blink of the caret, or `null` for a caret that does not blink.
 * @since 1.0.0
 */
@Immutable
public class InputOtpCaret(
    public val width: Dp,
    public val height: Dp,
    public val color: Color,
    public val period: Duration?,
) {
    /**
     * Returns a copy of this [InputOtpCaret] with the given values replaced and every other value kept.
     *
     * @param width the new value of [width].
     * @param height the new value of [height].
     * @param color the new value of [color].
     * @param period the new value of [period].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        width: Dp = this.width,
        height: Dp = this.height,
        color: Color = this.color,
        period: Duration? = this.period,
    ): InputOtpCaret = InputOtpCaret(
        width = width,
        height = height,
        color = color,
        period = period,
    )

    override fun equals(other: Any?): Boolean = other is InputOtpCaret && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "InputOtpCaret(" +
        "width=$width, " +
        "height=$height, " +
        "color=$color, " +
        "period=${period ?: "null"})"

    private fun values(): List<Any?> = listOf(
        width,
        height,
        color,
        period,
    )
}
