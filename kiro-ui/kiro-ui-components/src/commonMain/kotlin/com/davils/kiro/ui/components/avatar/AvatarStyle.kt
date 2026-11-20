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

/**
 * The style of an [Avatar], of an [AvatarGroup] and of its [AvatarGroupCount].
 *
 * @property size the width and height of the avatar.
 * @property shape the shape that the avatar is clipped to.
 * @property outline the outline drawn on top of the avatar.
 * @property outlineBlendMode the blend mode with which the outline is drawn over the image.
 * @property fallbackContainer the background color of the avatar and of the count of a group.
 * @property fallbackContent the content color of the fallback and of the count of a group.
 * @property fallbackTextStyle the text style of the fallback and of the count of a group.
 * @property badgeSize the width and height of the badge.
 * @property badgeIconSize the size of the icon in the badge, or `null` to leave the badge without content.
 * @property badgeContainer the background color of the badge.
 * @property badgeContent the content color of the badge.
 * @property badgeRing the ring around the badge.
 * @property groupRing the ring around each avatar and the count of a group.
 * @property groupOverlap how far the avatars of a group overlap.
 * @property countIconSize the size of an icon in the count of a group.
 * @since 1.0.0
 */
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
     * @param size the new value of [size].
     * @param shape the new value of [shape].
     * @param outline the new value of [outline].
     * @param outlineBlendMode the new value of [outlineBlendMode].
     * @param fallbackContainer the new value of [fallbackContainer].
     * @param fallbackContent the new value of [fallbackContent].
     * @param fallbackTextStyle the new value of [fallbackTextStyle].
     * @param badgeSize the new value of [badgeSize].
     * @param badgeIconSize the new value of [badgeIconSize].
     * @param badgeContainer the new value of [badgeContainer].
     * @param badgeContent the new value of [badgeContent].
     * @param badgeRing the new value of [badgeRing].
     * @param groupRing the new value of [groupRing].
     * @param groupOverlap the new value of [groupOverlap].
     * @param countIconSize the new value of [countIconSize].
     * @return the copy.
     * @since 1.0.0
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
        "badgeIconSize=${badgeIconSize ?: "null"}, " +
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
