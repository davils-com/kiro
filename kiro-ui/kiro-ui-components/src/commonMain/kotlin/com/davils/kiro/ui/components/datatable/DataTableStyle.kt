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

package com.davils.kiro.ui.components.datatable

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.table.TableStyle
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The look of a [DataTable]: the style of its table, the height of its empty row and the icons of its sortable
 * headers.
 *
 * @property table the style of the table that shows the rows.
 * @property emptyHeight the height of the row that shows the text when there are no rows.
 * @property unsortedIcon the icon of a sortable header whose column does not sort the rows.
 * @property ascendingIcon the icon of the header whose column sorts the rows in ascending order.
 * @property descendingIcon the icon of the header whose column sorts the rows in descending order.
 * @since 1.0.0
 */
@Immutable
public class DataTableStyle(
    public val table: TableStyle,
    public val emptyHeight: Dp,
    public val unsortedIcon: IconGraphic,
    public val ascendingIcon: IconGraphic,
    public val descendingIcon: IconGraphic,
) {
    /**
     * Returns a copy of this [DataTableStyle] with the given values replaced and every other value kept.
     *
     * @since 1.0.0
     */
    public fun copy(
        table: TableStyle = this.table,
        emptyHeight: Dp = this.emptyHeight,
        unsortedIcon: IconGraphic = this.unsortedIcon,
        ascendingIcon: IconGraphic = this.ascendingIcon,
        descendingIcon: IconGraphic = this.descendingIcon,
    ): DataTableStyle = DataTableStyle(
        table = table,
        emptyHeight = emptyHeight,
        unsortedIcon = unsortedIcon,
        ascendingIcon = ascendingIcon,
        descendingIcon = descendingIcon,
    )

    override fun equals(other: Any?): Boolean = other is DataTableStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "DataTableStyle(" +
        "table=$table, " +
        "emptyHeight=$emptyHeight, " +
        "unsortedIcon=$unsortedIcon, " +
        "ascendingIcon=$ascendingIcon, " +
        "descendingIcon=$descendingIcon)"

    private fun values(): List<Any?> = listOf(
        table,
        emptyHeight,
        unsortedIcon,
        ascendingIcon,
        descendingIcon,
    )
}
