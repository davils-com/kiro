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

/**
 * How the tooltip of a chart marks each value with the color of its series.
 *
 * @since 1.0.0
 */
public enum class ChartTooltipIndicator {
    /**
     * A small square with rounded corners before the value.
     *
     * @since 1.0.0
     */
    Dot,

    /**
     * A solid vertical bar along the height of the row.
     *
     * @since 1.0.0
     */
    Line,

    /**
     * A dashed vertical line along the height of the row.
     *
     * @since 1.0.0
     */
    Dashed,
}
