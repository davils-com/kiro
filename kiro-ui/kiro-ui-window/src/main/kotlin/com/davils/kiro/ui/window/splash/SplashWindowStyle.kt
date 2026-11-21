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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.core.style.Border

/**
 * How the window of a splash screen looks.
 *
 * @property background the color behind the content.
 * @property border the border of the window.
 * @property cornerRadius the radius of the corners; above `0.dp` the window is transparent outside of them.
 * @property size the size the window opens with.
 * @since 1.0.0
 */
@Immutable
public class SplashWindowStyle(
    public val background: Color,
    public val border: Border,
    public val cornerRadius: Dp,
    public val size: DpSize,
) {
    /**
     * Returns a copy of this [SplashWindowStyle] with the given values replaced and every other value kept.
     *
     * @param background the new value of [background].
     * @param border the new value of [border].
     * @param cornerRadius the new value of [cornerRadius].
     * @param size the new value of [size].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        background: Color = this.background,
        border: Border = this.border,
        cornerRadius: Dp = this.cornerRadius,
        size: DpSize = this.size,
    ): SplashWindowStyle = SplashWindowStyle(
        background = background,
        border = border,
        cornerRadius = cornerRadius,
        size = size,
    )

    override fun equals(other: Any?): Boolean = other is SplashWindowStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SplashWindowStyle(" +
        "background=$background, " +
        "border=$border, " +
        "cornerRadius=$cornerRadius, " +
        "size=$size)"

    private fun values(): List<Any?> = listOf(
        background,
        border,
        cornerRadius,
        size,
    )
}
