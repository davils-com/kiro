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

package com.davils.kiro.ui.components.skeleton

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.davils.kiro.ui.icons.IconGraphic
import kotlin.time.Duration

@Immutable
public class SkeletonStyle(
    public val radius: Dp,
    public val color: Color,
    public val animation: SkeletonAnimation,
    public val dimmedAlpha: Float,
    public val pulseDuration: Duration,
    public val pulseEasing: Easing,
    public val shimmerColor: Color,
    public val shimmerDuration: Duration,
    public val shimmerWidth: Float,
    public val lineHeight: Dp,
    public val lineGap: Dp,
    public val avatarSize: Dp,
    public val buttonSize: DpSize,
    public val imageIcon: IconGraphic,
    public val imageIconSize: Dp,
    public val gap: Dp,
    public val isAnimated: Boolean,
) {
    /**
     * Returns a copy of this [SkeletonStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        radius: Dp = this.radius,
        color: Color = this.color,
        animation: SkeletonAnimation = this.animation,
        dimmedAlpha: Float = this.dimmedAlpha,
        pulseDuration: Duration = this.pulseDuration,
        pulseEasing: Easing = this.pulseEasing,
        shimmerColor: Color = this.shimmerColor,
        shimmerDuration: Duration = this.shimmerDuration,
        shimmerWidth: Float = this.shimmerWidth,
        lineHeight: Dp = this.lineHeight,
        lineGap: Dp = this.lineGap,
        avatarSize: Dp = this.avatarSize,
        buttonSize: DpSize = this.buttonSize,
        imageIcon: IconGraphic = this.imageIcon,
        imageIconSize: Dp = this.imageIconSize,
        gap: Dp = this.gap,
        isAnimated: Boolean = this.isAnimated,
    ): SkeletonStyle = SkeletonStyle(
        radius = radius,
        color = color,
        animation = animation,
        dimmedAlpha = dimmedAlpha,
        pulseDuration = pulseDuration,
        pulseEasing = pulseEasing,
        shimmerColor = shimmerColor,
        shimmerDuration = shimmerDuration,
        shimmerWidth = shimmerWidth,
        lineHeight = lineHeight,
        lineGap = lineGap,
        avatarSize = avatarSize,
        buttonSize = buttonSize,
        imageIcon = imageIcon,
        imageIconSize = imageIconSize,
        gap = gap,
        isAnimated = isAnimated,
    )

    override fun equals(other: Any?): Boolean = other is SkeletonStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "SkeletonStyle(" +
        "radius=$radius, " +
        "color=$color, " +
        "animation=$animation, " +
        "dimmedAlpha=$dimmedAlpha, " +
        "pulseDuration=$pulseDuration, " +
        "pulseEasing=$pulseEasing, " +
        "shimmerColor=$shimmerColor, " +
        "shimmerDuration=$shimmerDuration, " +
        "shimmerWidth=$shimmerWidth, " +
        "lineHeight=$lineHeight, " +
        "lineGap=$lineGap, " +
        "avatarSize=$avatarSize, " +
        "buttonSize=$buttonSize, " +
        "imageIcon=$imageIcon, " +
        "imageIconSize=$imageIconSize, " +
        "gap=$gap, " +
        "isAnimated=$isAnimated)"

    private fun values(): List<Any?> = listOf(
        radius,
        color,
        animation,
        dimmedAlpha,
        pulseDuration,
        pulseEasing,
        shimmerColor,
        shimmerDuration,
        shimmerWidth,
        lineHeight,
        lineGap,
        avatarSize,
        buttonSize,
        imageIcon,
        imageIconSize,
        gap,
        isAnimated,
    )
}
