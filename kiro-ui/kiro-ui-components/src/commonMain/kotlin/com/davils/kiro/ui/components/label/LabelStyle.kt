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

package com.davils.kiro.ui.components.label

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

/**
 * How a [Label] looks: its text style and its alpha while disabled.
 *
 * @property textStyle the text style of the label.
 * @property disabledAlpha the alpha of the label while it is disabled.
 * @since 1.0.0
 */
@Immutable
public class LabelStyle(
    public val textStyle: TextStyle,
    public val disabledAlpha: Float,
) {
    /**
     * Returns a copy of this [LabelStyle] with the given values replaced and every other value kept.
     *
     * @param textStyle the new value of [textStyle].
     * @param disabledAlpha the new value of [disabledAlpha].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(textStyle: TextStyle = this.textStyle, disabledAlpha: Float = this.disabledAlpha): LabelStyle =
        LabelStyle(
            textStyle = textStyle,
            disabledAlpha = disabledAlpha,
        )

    override fun equals(other: Any?): Boolean = other is LabelStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "LabelStyle(" +
        "textStyle=$textStyle, " +
        "disabledAlpha=$disabledAlpha)"

    private fun values(): List<Any?> = listOf(
        textStyle,
        disabledAlpha,
    )
}
