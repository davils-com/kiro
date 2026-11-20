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

package com.davils.kiro.ui.components.toast

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.davils.kiro.ui.core.color.StatusColor
import com.davils.kiro.ui.core.elevation.BoxShadow
import com.davils.kiro.ui.core.style.Border
import com.davils.kiro.ui.icons.IconGraphic
import kotlin.time.Duration

@Immutable
public class ToastStyle(
    public val width: Dp,
    public val padding: Dp,
    public val gap: Dp,
    public val container: Color,
    public val content: Color,
    public val border: Border,
    public val shape: Shape,
    public val shadow: BoxShadow,
    public val titleStyle: TextStyle,
    public val descriptionStyle: TextStyle,
    public val iconSize: Dp,
    public val icons: Map<ToastType, IconGraphic>,
    public val action: ToastActionStyle,
    public val edgeOffset: Dp,
    public val expandedGap: Dp,
    public val stackGap: Dp,
    public val scaleStep: Float,
    public val visibleCount: Int,
    public val tick: Duration,
    public val statuses: Map<ToastType, StatusColor>,
    public val closeIcon: IconGraphic,
    public val closeLabel: String,
    public val closeSize: Dp,
    public val progressHeight: Dp,
    public val progressTrack: Color,
    public val progressColor: Color,
    public val timerHeight: Dp,
    public val timerColor: Color,
    public val swipeThreshold: Float,
) {
    /**
     * Returns a copy of this [ToastStyle] with the given values replaced and every other value kept.
     *
     * @since 0.1.0
     */
    public fun copy(
        width: Dp = this.width,
        padding: Dp = this.padding,
        gap: Dp = this.gap,
        container: Color = this.container,
        content: Color = this.content,
        border: Border = this.border,
        shape: Shape = this.shape,
        shadow: BoxShadow = this.shadow,
        titleStyle: TextStyle = this.titleStyle,
        descriptionStyle: TextStyle = this.descriptionStyle,
        iconSize: Dp = this.iconSize,
        icons: Map<ToastType, IconGraphic> = this.icons,
        action: ToastActionStyle = this.action,
        edgeOffset: Dp = this.edgeOffset,
        expandedGap: Dp = this.expandedGap,
        stackGap: Dp = this.stackGap,
        scaleStep: Float = this.scaleStep,
        visibleCount: Int = this.visibleCount,
        tick: Duration = this.tick,
        statuses: Map<ToastType, StatusColor> = this.statuses,
        closeIcon: IconGraphic = this.closeIcon,
        closeLabel: String = this.closeLabel,
        closeSize: Dp = this.closeSize,
        progressHeight: Dp = this.progressHeight,
        progressTrack: Color = this.progressTrack,
        progressColor: Color = this.progressColor,
        timerHeight: Dp = this.timerHeight,
        timerColor: Color = this.timerColor,
        swipeThreshold: Float = this.swipeThreshold,
    ): ToastStyle = ToastStyle(
        width = width,
        padding = padding,
        gap = gap,
        container = container,
        content = content,
        border = border,
        shape = shape,
        shadow = shadow,
        titleStyle = titleStyle,
        descriptionStyle = descriptionStyle,
        iconSize = iconSize,
        icons = icons,
        action = action,
        edgeOffset = edgeOffset,
        expandedGap = expandedGap,
        stackGap = stackGap,
        scaleStep = scaleStep,
        visibleCount = visibleCount,
        tick = tick,
        statuses = statuses,
        closeIcon = closeIcon,
        closeLabel = closeLabel,
        closeSize = closeSize,
        progressHeight = progressHeight,
        progressTrack = progressTrack,
        progressColor = progressColor,
        timerHeight = timerHeight,
        timerColor = timerColor,
        swipeThreshold = swipeThreshold,
    )

    override fun equals(other: Any?): Boolean = other is ToastStyle && values() == other.values()

    override fun hashCode(): Int = values().hashCode()

    override fun toString(): String = "ToastStyle(" +
        "width=$width, " +
        "padding=$padding, " +
        "gap=$gap, " +
        "container=$container, " +
        "content=$content, " +
        "border=$border, " +
        "shape=$shape, " +
        "shadow=$shadow, " +
        "titleStyle=$titleStyle, " +
        "descriptionStyle=$descriptionStyle, " +
        "iconSize=$iconSize, " +
        "icons=$icons, " +
        "action=$action, " +
        "edgeOffset=$edgeOffset, " +
        "expandedGap=$expandedGap, " +
        "stackGap=$stackGap, " +
        "scaleStep=$scaleStep, " +
        "visibleCount=$visibleCount, " +
        "tick=$tick, " +
        "statuses=$statuses, " +
        "closeIcon=$closeIcon, " +
        "closeLabel=$closeLabel, " +
        "closeSize=$closeSize, " +
        "progressHeight=$progressHeight, " +
        "progressTrack=$progressTrack, " +
        "progressColor=$progressColor, " +
        "timerHeight=$timerHeight, " +
        "timerColor=$timerColor, " +
        "swipeThreshold=$swipeThreshold)"

    private fun values(): List<Any?> = listOf(
        width,
        padding,
        gap,
        container,
        content,
        border,
        shape,
        shadow,
        titleStyle,
        descriptionStyle,
        iconSize,
        icons,
        action,
        edgeOffset,
        expandedGap,
        stackGap,
        scaleStep,
        visibleCount,
        tick,
        statuses,
        closeIcon,
        closeLabel,
        closeSize,
        progressHeight,
        progressTrack,
        progressColor,
        timerHeight,
        timerColor,
        swipeThreshold,
    )
}
