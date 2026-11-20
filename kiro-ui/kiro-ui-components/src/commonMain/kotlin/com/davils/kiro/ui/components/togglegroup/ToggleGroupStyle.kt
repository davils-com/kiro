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

package com.davils.kiro.ui.components.togglegroup

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.toggle.ToggleStyle

/**
 * The look of a [ToggleGroup] and of its toggles.
 *
 * @property spacing the space between the toggles; zero joins them.
 * @property item the style of a toggle.
 * @property joinedPadding the horizontal padding of a joined toggle.
 * @property joinedIconEdgePadding the padding between a leading or trailing icon and the edge of a joined toggle.
 * @property radius the corner radius of the outer corners of joined toggles.
 * @since 1.0.0
 */
@Immutable
public class ToggleGroupStyle(
    public val spacing: Dp,
    public val item: ToggleStyle,
    public val joinedPadding: Dp,
    public val joinedIconEdgePadding: Dp,
    public val radius: Dp,
) {
    /**
     * Returns a copy of this [ToggleGroupStyle] with the given values replaced and every other value kept.
     *
     * @param spacing the new value of [spacing].
     * @param item the new value of [item].
     * @param joinedPadding the new value of [joinedPadding].
     * @param joinedIconEdgePadding the new value of [joinedIconEdgePadding].
     * @param radius the new value of [radius].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        spacing: Dp = this.spacing,
        item: ToggleStyle = this.item,
        joinedPadding: Dp = this.joinedPadding,
        joinedIconEdgePadding: Dp = this.joinedIconEdgePadding,
        radius: Dp = this.radius,
    ): ToggleGroupStyle = ToggleGroupStyle(
        spacing = spacing,
        item = item,
        joinedPadding = joinedPadding,
        joinedIconEdgePadding = joinedIconEdgePadding,
        radius = radius,
    )

    override fun equals(other: Any?): Boolean = other is ToggleGroupStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ToggleGroupStyle(" +
        "spacing=$spacing, " +
        "item=$item, " +
        "joinedPadding=$joinedPadding, " +
        "joinedIconEdgePadding=$joinedIconEdgePadding, " +
        "radius=$radius)"

    private fun values(): List<Any?> = listOf(
        spacing,
        item,
        joinedPadding,
        joinedIconEdgePadding,
        radius,
    )
}
