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

package com.davils.kiro.ui.components.avatar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.style.Border

@Immutable
public class AvatarStyle(
    public val size: Dp,
    public val shape: Shape,
    public val outline: Border,
    public val outlineBlendMode: BlendMode,
    public val fallbackContainer: Color,
    public val fallbackContent: Color,
    public val fallbackTextStyle: TextStyle,
    public val badgeSize: Dp,
    public val badgeIconSize: Dp?,
    public val badgeContainer: Color,
    public val badgeContent: Color,
    public val badgeRing: Border,
    public val groupRing: Border,
    public val groupOverlap: Dp,
    public val countIconSize: Dp,
) {
    /**
     * Returns a copy of this [AvatarStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        size: Dp = this.size,
        shape: Shape = this.shape,
        outline: Border = this.outline,
        outlineBlendMode: BlendMode = this.outlineBlendMode,
        fallbackContainer: Color = this.fallbackContainer,
        fallbackContent: Color = this.fallbackContent,
        fallbackTextStyle: TextStyle = this.fallbackTextStyle,
        badgeSize: Dp = this.badgeSize,
        badgeIconSize: Dp? = this.badgeIconSize,
        badgeContainer: Color = this.badgeContainer,
        badgeContent: Color = this.badgeContent,
        badgeRing: Border = this.badgeRing,
        groupRing: Border = this.groupRing,
        groupOverlap: Dp = this.groupOverlap,
        countIconSize: Dp = this.countIconSize,
    ): AvatarStyle = AvatarStyle(
        size = size,
        shape = shape,
        outline = outline,
        outlineBlendMode = outlineBlendMode,
        fallbackContainer = fallbackContainer,
        fallbackContent = fallbackContent,
        fallbackTextStyle = fallbackTextStyle,
        badgeSize = badgeSize,
        badgeIconSize = badgeIconSize,
        badgeContainer = badgeContainer,
        badgeContent = badgeContent,
        badgeRing = badgeRing,
        groupRing = groupRing,
        groupOverlap = groupOverlap,
        countIconSize = countIconSize,
    )

    override fun equals(other: Any?): Boolean = other is AvatarStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "AvatarStyle(" +
        "size=$size, " +
        "shape=$shape, " +
        "outline=$outline, " +
        "outlineBlendMode=$outlineBlendMode, " +
        "fallbackContainer=$fallbackContainer, " +
        "fallbackContent=$fallbackContent, " +
        "fallbackTextStyle=$fallbackTextStyle, " +
        "badgeSize=$badgeSize, " +
        "badgeIconSize=$badgeIconSize, " +
        "badgeContainer=$badgeContainer, " +
        "badgeContent=$badgeContent, " +
        "badgeRing=$badgeRing, " +
        "groupRing=$groupRing, " +
        "groupOverlap=$groupOverlap, " +
        "countIconSize=$countIconSize)"

    private fun values(): List<Any?> = listOf(
        size,
        shape,
        outline,
        outlineBlendMode,
        fallbackContainer,
        fallbackContent,
        fallbackTextStyle,
        badgeSize,
        badgeIconSize,
        badgeContainer,
        badgeContent,
        badgeRing,
        groupRing,
        groupOverlap,
        countIconSize,
    )
}
