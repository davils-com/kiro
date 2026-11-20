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

package com.davils.kiro.ui.components.navigationmenu

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.components.primitive.overlay.PanelStyle
import com.davils.kiro.ui.icons.IconGraphic
import kotlin.time.Duration

@Immutable
public class NavigationMenuStyle(
    public val triggerHeight: Dp,
    public val triggerShape: Shape,
    public val triggerHorizontalPadding: Dp,
    public val triggerTextStyle: TextStyle,
    public val hover: Color,
    public val open: Color,
    public val chevron: IconGraphic,
    public val chevronSize: Dp,
    public val chevronGap: Dp,
    public val panel: PanelStyle,
    public val sideOffset: Dp,
    public val linkShape: Shape,
    public val linkPadding: Dp,
    public val linkGap: Dp,
    public val linkTextStyle: TextStyle,
    public val openDelay: Duration,
    public val closeDelay: Duration,
) {
    /**
     * Returns a copy of this [NavigationMenuStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        triggerHeight: Dp = this.triggerHeight,
        triggerShape: Shape = this.triggerShape,
        triggerHorizontalPadding: Dp = this.triggerHorizontalPadding,
        triggerTextStyle: TextStyle = this.triggerTextStyle,
        hover: Color = this.hover,
        open: Color = this.open,
        chevron: IconGraphic = this.chevron,
        chevronSize: Dp = this.chevronSize,
        chevronGap: Dp = this.chevronGap,
        panel: PanelStyle = this.panel,
        sideOffset: Dp = this.sideOffset,
        linkShape: Shape = this.linkShape,
        linkPadding: Dp = this.linkPadding,
        linkGap: Dp = this.linkGap,
        linkTextStyle: TextStyle = this.linkTextStyle,
        openDelay: Duration = this.openDelay,
        closeDelay: Duration = this.closeDelay,
    ): NavigationMenuStyle = NavigationMenuStyle(
        triggerHeight = triggerHeight,
        triggerShape = triggerShape,
        triggerHorizontalPadding = triggerHorizontalPadding,
        triggerTextStyle = triggerTextStyle,
        hover = hover,
        open = open,
        chevron = chevron,
        chevronSize = chevronSize,
        chevronGap = chevronGap,
        panel = panel,
        sideOffset = sideOffset,
        linkShape = linkShape,
        linkPadding = linkPadding,
        linkGap = linkGap,
        linkTextStyle = linkTextStyle,
        openDelay = openDelay,
        closeDelay = closeDelay,
    )

    override fun equals(other: Any?): Boolean = other is NavigationMenuStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "NavigationMenuStyle(" +
        "triggerHeight=$triggerHeight, " +
        "triggerShape=$triggerShape, " +
        "triggerHorizontalPadding=$triggerHorizontalPadding, " +
        "triggerTextStyle=$triggerTextStyle, " +
        "hover=$hover, " +
        "open=$open, " +
        "chevron=$chevron, " +
        "chevronSize=$chevronSize, " +
        "chevronGap=$chevronGap, " +
        "panel=$panel, " +
        "sideOffset=$sideOffset, " +
        "linkShape=$linkShape, " +
        "linkPadding=$linkPadding, " +
        "linkGap=$linkGap, " +
        "linkTextStyle=$linkTextStyle, " +
        "openDelay=$openDelay, " +
        "closeDelay=$closeDelay)"

    private fun values(): List<Any?> = listOf(
        triggerHeight,
        triggerShape,
        triggerHorizontalPadding,
        triggerTextStyle,
        hover,
        open,
        chevron,
        chevronSize,
        chevronGap,
        panel,
        sideOffset,
        linkShape,
        linkPadding,
        linkGap,
        linkTextStyle,
        openDelay,
        closeDelay,
    )
}
