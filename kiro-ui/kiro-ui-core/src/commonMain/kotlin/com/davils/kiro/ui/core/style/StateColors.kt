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

package com.davils.kiro.ui.core.style

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.core.state.StateValue

/**
 * The colors of a control in each of its interaction states.
 *
 * @property container the colors of the container.
 * @property content the colors of text and icons.
 * @property border the colors of the border.
 * @property ring the colors of the focus ring.
 * @since 0.1.0
 */
@Immutable
public class StateColors(
    public val container: StateValue<Color>,
    public val content: StateValue<Color>,
    public val border: StateValue<Color>,
    public val ring: StateValue<Color>,
) {
    /**
     * Returns a copy of this [StateColors] with the given values replaced and every other value kept.
     *
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param ring the new value of [ring].
     * @return the copy.
     * @since 0.1.0
     */
    public fun copy(
        container: StateValue<Color> = this.container,
        content: StateValue<Color> = this.content,
        border: StateValue<Color> = this.border,
        ring: StateValue<Color> = this.ring,
    ): StateColors = StateColors(
        container = container,
        content = content,
        border = border,
        ring = ring,
    )

    override fun equals(other: Any?): Boolean = other is StateColors && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "StateColors(" +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "ring=$ring)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        border,
        ring,
    )
}
