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

package com.davils.kiro.ui.window

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.style.Border

/**
 * How the frame of a [ChromeWindow] looks and behaves.
 *
 * @property background the color behind the title bar and the content.
 * @property border the border of the window while Kiro draws the frame on its own.
 * @property cornerRadius the radius of the corners while Kiro draws the frame on its own; `0.dp` keeps the window
 * opaque.
 * @property resizeThickness the thickness of the edges that resize the window while Kiro draws the frame on its own.
 * @property minimumSize the smallest size the user can give the window.
 * @property frame whether the window keeps the frame of the operating system where it can.
 * @since 1.0.0
 */
@Immutable
public class ChromeWindowStyle(
    public val background: Color,
    public val border: Border,
    public val cornerRadius: Dp,
    public val resizeThickness: Dp,
    public val minimumSize: DpSize,
    public val frame: FramePreference,
) {
    /**
     * Returns a copy of this [ChromeWindowStyle] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param border the new value of [border].
     * @param cornerRadius the new value of [cornerRadius].
     * @param resizeThickness the new value of [resizeThickness].
     * @param minimumSize the new value of [minimumSize].
     * @param frame the new value of [frame].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        background: Color = this.background,
        border: Border = this.border,
        cornerRadius: Dp = this.cornerRadius,
        resizeThickness: Dp = this.resizeThickness,
        minimumSize: DpSize = this.minimumSize,
        frame: FramePreference = this.frame,
    ): ChromeWindowStyle = ChromeWindowStyle(
        background = background,
        border = border,
        cornerRadius = cornerRadius,
        resizeThickness = resizeThickness,
        minimumSize = minimumSize,
        frame = frame,
    )

    override fun equals(other: Any?): Boolean = other is ChromeWindowStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChromeWindowStyle(" +
        "background=$background, " +
        "border=$border, " +
        "cornerRadius=$cornerRadius, " +
        "resizeThickness=$resizeThickness, " +
        "minimumSize=$minimumSize, " +
        "frame=$frame)"

    private fun values(): List<Any?> = listOf(
        background,
        border,
        cornerRadius,
        resizeThickness,
        minimumSize,
        frame,
    )
}
