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

package com.davils.kiro.ui.components.codeblock

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic
import kotlin.time.Duration

@Immutable
public class CodeBlockStyle(
    public val shape: Shape,
    public val border: Border,
    public val focusBorder: Color,
    public val ring: Border,
    public val container: Color,
    public val content: Color,
    public val muted: Color,
    public val titleStyle: TextStyle,
    public val codeStyle: TextStyle,
    public val padding: Dp,
    public val sectionGap: Dp,
    public val gap: Dp,
    public val gutterMinWidth: Dp,
    public val gutterGap: Dp,
    public val added: Color,
    public val removed: Color,
    public val removedMarker: Color,
    public val highlighted: Color,
    public val actionSurface: Color,
    public val actionRing: Border,
    public val iconSize: Dp,
    public val copyIcon: IconGraphic,
    public val copiedIcon: IconGraphic,
    public val copyLabel: String,
    public val copiedLabel: String,
    public val copiedDuration: Duration,
) {
    /**
     * Returns a copy of this [CodeBlockStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        shape: Shape = this.shape,
        border: Border = this.border,
        focusBorder: Color = this.focusBorder,
        ring: Border = this.ring,
        container: Color = this.container,
        content: Color = this.content,
        muted: Color = this.muted,
        titleStyle: TextStyle = this.titleStyle,
        codeStyle: TextStyle = this.codeStyle,
        padding: Dp = this.padding,
        sectionGap: Dp = this.sectionGap,
        gap: Dp = this.gap,
        gutterMinWidth: Dp = this.gutterMinWidth,
        gutterGap: Dp = this.gutterGap,
        added: Color = this.added,
        removed: Color = this.removed,
        removedMarker: Color = this.removedMarker,
        highlighted: Color = this.highlighted,
        actionSurface: Color = this.actionSurface,
        actionRing: Border = this.actionRing,
        iconSize: Dp = this.iconSize,
        copyIcon: IconGraphic = this.copyIcon,
        copiedIcon: IconGraphic = this.copiedIcon,
        copyLabel: String = this.copyLabel,
        copiedLabel: String = this.copiedLabel,
        copiedDuration: Duration = this.copiedDuration,
    ): CodeBlockStyle = CodeBlockStyle(
        shape = shape,
        border = border,
        focusBorder = focusBorder,
        ring = ring,
        container = container,
        content = content,
        muted = muted,
        titleStyle = titleStyle,
        codeStyle = codeStyle,
        padding = padding,
        sectionGap = sectionGap,
        gap = gap,
        gutterMinWidth = gutterMinWidth,
        gutterGap = gutterGap,
        added = added,
        removed = removed,
        removedMarker = removedMarker,
        highlighted = highlighted,
        actionSurface = actionSurface,
        actionRing = actionRing,
        iconSize = iconSize,
        copyIcon = copyIcon,
        copiedIcon = copiedIcon,
        copyLabel = copyLabel,
        copiedLabel = copiedLabel,
        copiedDuration = copiedDuration,
    )

    override fun equals(other: Any?): Boolean = other is CodeBlockStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "CodeBlockStyle(" +
        "shape=$shape, " +
        "border=$border, " +
        "focusBorder=$focusBorder, " +
        "ring=$ring, " +
        "container=$container, " +
        "content=$content, " +
        "muted=$muted, " +
        "titleStyle=$titleStyle, " +
        "codeStyle=$codeStyle, " +
        "padding=$padding, " +
        "sectionGap=$sectionGap, " +
        "gap=$gap, " +
        "gutterMinWidth=$gutterMinWidth, " +
        "gutterGap=$gutterGap, " +
        "added=$added, " +
        "removed=$removed, " +
        "removedMarker=$removedMarker, " +
        "highlighted=$highlighted, " +
        "actionSurface=$actionSurface, " +
        "actionRing=$actionRing, " +
        "iconSize=$iconSize, " +
        "copyIcon=$copyIcon, " +
        "copiedIcon=$copiedIcon, " +
        "copyLabel=$copyLabel, " +
        "copiedLabel=$copiedLabel, " +
        "copiedDuration=$copiedDuration)"

    private fun values(): List<Any?> = listOf(
        shape,
        border,
        focusBorder,
        ring,
        container,
        content,
        muted,
        titleStyle,
        codeStyle,
        padding,
        sectionGap,
        gap,
        gutterMinWidth,
        gutterGap,
        added,
        removed,
        removedMarker,
        highlighted,
        actionSurface,
        actionRing,
        iconSize,
        copyIcon,
        copiedIcon,
        copyLabel,
        copiedLabel,
        copiedDuration,
    )
}
