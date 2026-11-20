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

package com.davils.kiro.ui.icons

import androidx.compose.runtime.Immutable

/**
 * One path of an icon, in the path data syntax of SVG on the canvas of its [IconGraphic].
 *
 * @property data the path data, such as `M7 7h10v10`.
 * @property isFilled whether the path is filled as well as stroked.
 * @since 1.0.0
 */
@Immutable
public class IconPath(
    public val data: String,
    public val isFilled: Boolean = false,
) {
    /**
     * Returns a copy of this [IconPath] with the given values replaced and every other value kept.
     *
     * @param data the new value of [data].
     * @param isFilled the new value of [isFilled].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(data: String = this.data, isFilled: Boolean = this.isFilled): IconPath = IconPath(
        data = data,
        isFilled = isFilled,
    )

    override fun equals(other: Any?): Boolean = other is IconPath && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "IconPath(" +
        "data=$data, " +
        "isFilled=$isFilled)"

    private fun values(): List<Any?> = listOf(
        data,
        isFilled,
    )
}
