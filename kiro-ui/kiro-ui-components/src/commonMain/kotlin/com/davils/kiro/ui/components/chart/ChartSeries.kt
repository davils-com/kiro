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

package com.davils.kiro.ui.components.chart

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.davils.kiro.ui.icons.IconGraphic

/**
 * Gives the series of a [ChartKey] its label, its color and an optional icon.
 *
 * @property key the key of the series.
 * @property label the label of the series in the tooltip and the legend.
 * @property color the color of the series, or [Color.Unspecified] for the color of the palette of the [ChartStyle]
 * at the position of the series.
 * @property icon the icon of the series in the tooltip and the legend, or `null` to show its color instead.
 * @since 1.0.0
 */
@Immutable
public class ChartSeries(
    public val key: ChartKey,
    public val label: String,
    public val color: Color = Color.Unspecified,
    public val icon: IconGraphic? = null,
) {
    /**
     * Returns a copy of this [ChartSeries] with the given values replaced and every other value kept.
     *
     * @param key the new value of [key].
     * @param label the new value of [label].
     * @param color the new value of [color].
     * @param icon the new value of [icon].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        key: ChartKey = this.key,
        label: String = this.label,
        color: Color = this.color,
        icon: IconGraphic? = this.icon,
    ): ChartSeries = ChartSeries(
        key = key,
        label = label,
        color = color,
        icon = icon,
    )

    override fun equals(other: Any?): Boolean = other is ChartSeries && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ChartSeries(" +
        "key=$key, " +
        "label=$label, " +
        "color=$color, " +
        "icon=${icon ?: "null"})"

    private fun values(): List<Any?> = listOf(
        key,
        label,
        color,
        icon,
    )
}
