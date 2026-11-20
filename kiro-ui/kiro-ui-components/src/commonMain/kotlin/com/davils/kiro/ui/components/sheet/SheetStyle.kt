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

package com.davils.kiro.ui.components.sheet

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic

@Immutable
public class SheetStyle(
    public val container: Color,
    public val content: Color,
    public val border: Border,
    public val shadow: BoxShadow,
    public val gap: Dp,
    public val textStyle: TextStyle,
    public val fraction: Float,
    public val minSize: Dp,
    public val sectionPadding: Dp,
    public val headerGap: Dp,
    public val footerGap: Dp,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val descriptionColor: Color,
    public val closeIcon: IconGraphic,
    public val closeOffset: Dp,
    public val closeLabel: String,
    public val scrim: Color,
) {
    /**
     * Returns a copy of this [SheetStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        container: Color = this.container,
        content: Color = this.content,
        border: Border = this.border,
        shadow: BoxShadow = this.shadow,
        gap: Dp = this.gap,
        textStyle: TextStyle = this.textStyle,
        fraction: Float = this.fraction,
        minSize: Dp = this.minSize,
        sectionPadding: Dp = this.sectionPadding,
        headerGap: Dp = this.headerGap,
        footerGap: Dp = this.footerGap,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        descriptionColor: Color = this.descriptionColor,
        closeIcon: IconGraphic = this.closeIcon,
        closeOffset: Dp = this.closeOffset,
        closeLabel: String = this.closeLabel,
        scrim: Color = this.scrim,
    ): SheetStyle = SheetStyle(
        container = container,
        content = content,
        border = border,
        shadow = shadow,
        gap = gap,
        textStyle = textStyle,
        fraction = fraction,
        minSize = minSize,
        sectionPadding = sectionPadding,
        headerGap = headerGap,
        footerGap = footerGap,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        descriptionColor = descriptionColor,
        closeIcon = closeIcon,
        closeOffset = closeOffset,
        closeLabel = closeLabel,
        scrim = scrim,
    )

    override fun equals(other: Any?): Boolean = other is SheetStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SheetStyle(" +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "shadow=$shadow, " +
        "gap=$gap, " +
        "textStyle=$textStyle, " +
        "fraction=$fraction, " +
        "minSize=$minSize, " +
        "sectionPadding=$sectionPadding, " +
        "headerGap=$headerGap, " +
        "footerGap=$footerGap, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "descriptionColor=$descriptionColor, " +
        "closeIcon=$closeIcon, " +
        "closeOffset=$closeOffset, " +
        "closeLabel=$closeLabel, " +
        "scrim=$scrim)"

    private fun values(): List<Any?> = listOf(
        container,
        content,
        border,
        shadow,
        gap,
        textStyle,
        fraction,
        minSize,
        sectionPadding,
        headerGap,
        footerGap,
        titleStyle,
        descriptionStyle,
        descriptionColor,
        closeIcon,
        closeOffset,
        closeLabel,
        scrim,
    )
}
