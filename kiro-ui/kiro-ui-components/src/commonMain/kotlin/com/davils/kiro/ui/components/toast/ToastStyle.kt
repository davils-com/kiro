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

/**
 * The style of the toasts of a [Toaster] and of their stack.
 *
 * @property width the width of a toast.
 * @property padding the padding inside a toast; a quarter of it insets the close button.
 * @property gap the gap between the icon, the text and the action of a toast, and between its title, description and
 * progress bar.
 * @property container the background color of a toast without status colors.
 * @property content the content color of a toast without status colors.
 * @property border the border of a toast; a toast with status colors keeps its width.
 * @property shape the shape of a toast.
 * @property shadow the shadow of a toast.
 * @property titleStyle the text style of the title.
 * @property descriptionStyle the text style of the description.
 * @property iconSize the size of the icon of a toast; the close icon is three quarters of it.
 * @property icons the icon of each type; a type without an icon shows none.
 * @property action the style of the action button.
 * @property edgeOffset the distance of the stack from the edges of the window.
 * @property expandedGap the gap between the toasts while the stack is expanded.
 * @property stackGap how far each older toast peeks out behind the newer one while the stack is collapsed.
 * @property scaleStep how much smaller each older toast is drawn while the stack is collapsed, as a fraction of its
 * size.
 * @property visibleCount how many of the newest toasts are shown.
 * @property tick the interval at which the timer of a toast counts down.
 * @property statuses the status colors of each type in a rich toaster; a type without an entry keeps [container],
 * [content] and the color of [border].
 * @property closeIcon the icon of the close button.
 * @property closeLabel the text that accessibility services read for the close button.
 * @property closeSize the width and height of the close button.
 * @property progressHeight the height of the progress bar.
 * @property progressTrack the color of the track of the progress bar.
 * @property progressColor the color of the filled part of the progress bar.
 * @property timerHeight the height of the bar of the remaining time.
 * @property timerColor the color of the bar of the remaining time.
 * @property swipeThreshold the fraction of its width that a toast must be swiped to be dismissed when it is not flung.
 * @since 1.0.0
 */
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
     * @param width the new value of [width].
     * @param padding the new value of [padding].
     * @param gap the new value of [gap].
     * @param container the new value of [container].
     * @param content the new value of [content].
     * @param border the new value of [border].
     * @param shape the new value of [shape].
     * @param shadow the new value of [shadow].
     * @param titleStyle the new value of [titleStyle].
     * @param descriptionStyle the new value of [descriptionStyle].
     * @param iconSize the new value of [iconSize].
     * @param icons the new value of [icons].
     * @param action the new value of [action].
     * @param edgeOffset the new value of [edgeOffset].
     * @param expandedGap the new value of [expandedGap].
     * @param stackGap the new value of [stackGap].
     * @param scaleStep the new value of [scaleStep].
     * @param visibleCount the new value of [visibleCount].
     * @param tick the new value of [tick].
     * @param statuses the new value of [statuses].
     * @param closeIcon the new value of [closeIcon].
     * @param closeLabel the new value of [closeLabel].
     * @param closeSize the new value of [closeSize].
     * @param progressHeight the new value of [progressHeight].
     * @param progressTrack the new value of [progressTrack].
     * @param progressColor the new value of [progressColor].
     * @param timerHeight the new value of [timerHeight].
     * @param timerColor the new value of [timerColor].
     * @param swipeThreshold the new value of [swipeThreshold].
     * @return the copy.
     * @since 1.0.0
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
