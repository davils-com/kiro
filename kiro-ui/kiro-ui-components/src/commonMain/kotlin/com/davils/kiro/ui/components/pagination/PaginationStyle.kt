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

package com.davils.kiro.ui.components.pagination

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.icons.IconGraphic

/**
 * The style of a [Pagination] and of its links.
 *
 * @property gap the gap between the links.
 * @property edgePadding the padding at the edge of the previous and the next button on the side of their icon.
 * @property previousIcon the icon of [PaginationPrevious], mirrored in right-to-left layouts.
 * @property nextIcon the icon of [PaginationNext], mirrored in right-to-left layouts.
 * @property ellipsisIcon the icon of [PaginationEllipsis].
 * @property ellipsisBox the width and height of [PaginationEllipsis].
 * @property iconSize the size of the ellipsis icon.
 * @since 1.0.0
 */
@Immutable
public class PaginationStyle(
    public val gap: Dp,
    public val edgePadding: Dp,
    public val previousIcon: IconGraphic,
    public val nextIcon: IconGraphic,
    public val ellipsisIcon: IconGraphic,
    public val ellipsisBox: Dp,
    public val iconSize: Dp,
) {
    /**
     * Returns a copy of this [PaginationStyle] with the given values replaced and every other value kept.
     *
     * @param gap the new value of [gap].
     * @param edgePadding the new value of [edgePadding].
     * @param previousIcon the new value of [previousIcon].
     * @param nextIcon the new value of [nextIcon].
     * @param ellipsisIcon the new value of [ellipsisIcon].
     * @param ellipsisBox the new value of [ellipsisBox].
     * @param iconSize the new value of [iconSize].
     * @return the copy.
     * @since 1.0.0
     */
    public fun copy(
        gap: Dp = this.gap,
        edgePadding: Dp = this.edgePadding,
        previousIcon: IconGraphic = this.previousIcon,
        nextIcon: IconGraphic = this.nextIcon,
        ellipsisIcon: IconGraphic = this.ellipsisIcon,
        ellipsisBox: Dp = this.ellipsisBox,
        iconSize: Dp = this.iconSize,
    ): PaginationStyle = PaginationStyle(
        gap = gap,
        edgePadding = edgePadding,
        previousIcon = previousIcon,
        nextIcon = nextIcon,
        ellipsisIcon = ellipsisIcon,
        ellipsisBox = ellipsisBox,
        iconSize = iconSize,
    )

    override fun equals(other: Any?): Boolean = other is PaginationStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "PaginationStyle(" +
        "gap=$gap, " +
        "edgePadding=$edgePadding, " +
        "previousIcon=$previousIcon, " +
        "nextIcon=$nextIcon, " +
        "ellipsisIcon=$ellipsisIcon, " +
        "ellipsisBox=$ellipsisBox, " +
        "iconSize=$iconSize)"

    private fun values(): List<Any?> = listOf(
        gap,
        edgePadding,
        previousIcon,
        nextIcon,
        ellipsisIcon,
        ellipsisBox,
        iconSize,
    )
}
